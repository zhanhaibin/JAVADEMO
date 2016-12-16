package cn.ava.saputil;

import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import cn.ava.publics.util.EmptyUtil;
import cn.ava.publics.util.EqualsUtil;
import cn.ava.publics.util.XmlUtil;
import cn.ava.saputil.SapBusinessObjectsModel.ChildTable;
import cn.ava.saputil.SapBusinessObjectsModel.FindColumn;
import cn.ava.saputil.SapBusinessObjectsModel.FormColumn;
import cn.ava.saputil.SapFieldsModel.ValidValue;

import com.sap.smb.sbo.api.SBOCOMConstants;

public class MyCustomExcelToXml2
{
	private String xmlpath;
	private String excelpath;
	private String errMessage;
	private SapCustomObject custemObject;
	
	public MyCustomExcelToXml2(String xml, String excel)
	{
		xmlpath = xml;
		excelpath = excel;
	}
	
	public Boolean CreateXML()
	{
		try
		{
			// 读取excel文件
			String filePath = excelpath;
			FileInputStream in = new FileInputStream(filePath);
			XSSFWorkbook wb = new XSSFWorkbook(in);
			
			// 循环读每个sheet的数据及处理
			int sheetCnt = wb.getNumberOfSheets();
			System.out.println("sheet数量为：" + sheetCnt);
			SapCustomObject sapCustom = new SapCustomObject();
			
			for (int numSheet = 0; numSheet < sheetCnt; numSheet++)
			{
				XSSFSheet sheet = wb.getSheetAt(numSheet);
				List<SapTableModel> tablemodels = new ArrayList<SapTableModel>();
				List<SapBusinessObjectsModel> objectmodels = new ArrayList<SapBusinessObjectsModel>();
				if (sheet == null)
				{
					continue;
				}
				XSSFRow nameRow = sheet.getRow(0);
				if (EmptyUtil.isEmpty(nameRow))
				{
					continue;
				}
				// int colNum = nameRow.getPhysicalNumberOfCells(); //总列数
				int lastRowNum = sheet.getLastRowNum();
				
				XSSFRow titleRow = sheet.getRow(2);
				if (EqualsUtil.StringEquals("Table-CUSTOM", sheet.getSheetName(), false))
				{
					SapTableModel saptable = null;
					SapFieldsModel sapfields = null;
					ValidValue validvalue = null;
					for (int i = 3; i <= lastRowNum; i++)
					{
						// Table对象 定义每个对象的起始列
						int Table_Table = 0;
						int Table_Field = 5;
						int Table_FieldValue = 15;
						XSSFRow row = sheet.getRow(i);
						String t0 = EmptyUtil.isNotEmpty(row.getCell(Table_Table)) ? getCellValue(row.getCell(Table_Table)) : "";
						String t1 = EmptyUtil.isNotEmpty(row.getCell(Table_Field)) ? getCellValue(row.getCell(Table_Field)) : "";
						String t2 = EmptyUtil.isNotEmpty(row.getCell(Table_FieldValue)) ? getCellValue(row.getCell(Table_FieldValue)) : "";
						// 当前行数据的内容 0 table 1 Field 2 valid
						Integer thisRowType = EmptyUtil.isNotEmpty(t0) ? 0 : EmptyUtil.isNotEmpty(t1) ? 1 : EmptyUtil.isNotEmpty(t2) ? 2 : -1;
						if (thisRowType.equals(0))
						{
							// 如果不为空先添加到列表中
							saptable = new SapTableModel();
							// [start] Table对象中的Table
							for (int j = Table_Table; j < Table_Field; j++)
							{
								if (EmptyUtil.isEmpty(row.getCell(j)))
								{
									continue;
								}
								String cellValue = getCellValue(row.getCell(j));
								String title = getCellValue(titleRow.getCell(j));
								if (EqualsUtil.StringEquals("isCreateTable", title, false))
									saptable.setIsCreateTable(BoolYesAndNo(cellValue));
								if (EqualsUtil.StringEquals("TableName", title, false))
									saptable.setTableName(cellValue);
								if (EqualsUtil.StringEquals("TableType", title, false))
									saptable.setTableType(Table_TableType(cellValue));
								if (EqualsUtil.StringEquals("TableDescription", title, false))
									saptable.setTableDescription(cellValue);
								if (EqualsUtil.StringEquals("IsUserTable", title, false))
									saptable.setIsUserTable(BoolYesAndNo(cellValue));
							}
							
							tablemodels.add(saptable);
							// [end]
						}
						else if (thisRowType.equals(1))
						{
							sapfields = new SapFieldsModel();
							// [start] Table对象中的字段
						for (int j = Table_Field; j < Table_FieldValue; j++)
							{
								if (EmptyUtil.isEmpty(row.getCell(j)))
								{
									continue;
								}
								String cellValue = getCellValue(row.getCell(j));
								String title = getCellValue(titleRow.getCell(j));
								if (EqualsUtil.StringEquals("Name", title, false))
									sapfields.setName(cellValue);
								if (EqualsUtil.StringEquals("TableName", title, false))
									sapfields.setTableName(cellValue);
								if (EqualsUtil.StringEquals("Type", title, false))
									sapfields.setType(Field_Type(cellValue));
								if (EqualsUtil.StringEquals("SubType", title, false))
									sapfields.setSubType(Field_SubType(cellValue));
								if (EqualsUtil.StringEquals("Mandatory", title, false))
									sapfields.setMandatory(YesAndNo(cellValue));
								if (EqualsUtil.StringEquals("LinkedTable", title, false))
									sapfields.setLinkedTable(cellValue);
								if (EqualsUtil.StringEquals("IsSystem", title, false))
									sapfields.setIsSystem(BoolYesAndNo(cellValue));
								if (EqualsUtil.StringEquals("EditSize", title, false))
									sapfields.setEditSize(Integer.parseInt(cellValue));
								if (EqualsUtil.StringEquals("Description", title, false))
									sapfields.setDescription(cellValue);
								if (EqualsUtil.StringEquals("DefaultValue", title, false))
									sapfields.setDefaultValue(cellValue);
							}
							// [end]
							int last = tablemodels.size() > 0 ? tablemodels.size() - 1 : 0;
							tablemodels.get(last).getFiledsList().add(sapfields);
						}
						else if (thisRowType.equals(2))
						{
							// [start] Table对象中的字段取值
							validvalue = new ValidValue();
							for (int j = Table_FieldValue; j < Table_FieldValue + 2; j++)
							{
								if (EmptyUtil.isEmpty(row.getCell(j)))
								{
									continue;
								}
								String cellValue = getCellValue(row.getCell(j));
								String title = getCellValue(titleRow.getCell(j));
								if (EqualsUtil.StringEquals("Description", title, false))
									validvalue.setDescription(cellValue);
								if (EqualsUtil.StringEquals("Value", title, false))
									validvalue.setValue(cellValue);
							}
							// [end]
							int last = saptable.getFiledsList().size() > 0 ? saptable.getFiledsList().size() - 1 : 0;
							saptable.getFiledsList().get(last).getMyValidValues().add(validvalue);
						}
					}
					sapCustom.setTablemodels(tablemodels);
				}
				else if (EqualsUtil.StringEquals("Object-CUSTOM", sheet.getSheetName(), false))
				{
					SapBusinessObjectsModel sapbusobj = null;
					for (int i = 3; i <= lastRowNum; i++)
					{
						int Object_Main = 0;
						int Object_Child = 10;
						int Object_Find = 12;
						int Object_Form = 14;
						XSSFRow row = sheet.getRow(i);
						String t0 = EmptyUtil.isNotEmpty(row.getCell(Object_Main)) ? getCellValue(row.getCell(Object_Main)) : "";
						String t1 = EmptyUtil.isNotEmpty(row.getCell(Object_Child)) ? getCellValue(row.getCell(Object_Child)) : "";
						String t2 = EmptyUtil.isNotEmpty(row.getCell(Object_Find)) ? getCellValue(row.getCell(Object_Find)) : "";
						String t3 = EmptyUtil.isNotEmpty(row.getCell(Object_Form)) ? getCellValue(row.getCell(Object_Form)) : "";
						// 当前行数据的内容 0 main 1 child 2 find 3 form
						Integer thisRowType = EmptyUtil.isNotEmpty(t0) ? 0 : EmptyUtil.isNotEmpty(t1) ? 1 : EmptyUtil.isNotEmpty(t2) ? 2 : EmptyUtil.isNotEmpty(t3) ? 3 : -1;
						if (thisRowType.equals(0))
						{
							// 如果不为空先添加到列表中
							sapbusobj = new SapBusinessObjectsModel();
							// [start] Table对象中的Table
							for (int j = Object_Main; j < Object_Child; j++)
							{
								if (EmptyUtil.isEmpty(row.getCell(j)))
								{
									continue;
								}
								String cellValue = getCellValue(row.getCell(j));
								String title = getCellValue(titleRow.getCell(j));
								if (EqualsUtil.StringEquals("ObjectType", title, false))
									sapbusobj.setObjectType(OBJ_ObjectType(cellValue));
								if (EqualsUtil.StringEquals("Name", title, false))
									sapbusobj.setName(cellValue);
								if (EqualsUtil.StringEquals("ManageSeries", title, false))
									sapbusobj.setManageSeries(YesAndNo(cellValue));
								if (EqualsUtil.StringEquals("Code", title, false))
									sapbusobj.setCode(cellValue);
								if (EqualsUtil.StringEquals("CanYearTransfer", title, false))
									sapbusobj.setCanYearTransfer(YesAndNo(cellValue));
								if (EqualsUtil.StringEquals("CanLog", title, false))
									sapbusobj.setCanLog(YesAndNo(cellValue));
								if (EqualsUtil.StringEquals("TableName", title, false))
									sapbusobj.setTableName(cellValue);
								if (EqualsUtil.StringEquals("CanDelete", title, false))
									sapbusobj.setCanDelete(YesAndNo(cellValue));
								if (EqualsUtil.StringEquals("CanClose", title, false))
									sapbusobj.setCanClose(YesAndNo(cellValue));
								if (EqualsUtil.StringEquals("CanCancel", title, false))
									sapbusobj.setCanCancel(YesAndNo(cellValue));
							}
							
							objectmodels.add(sapbusobj);
							// [end]
						}
						if (thisRowType.equals(1))
						{
							ChildTable childtable = new ChildTable();
							for (int j = Object_Child; j < Object_Find; j++)
							{
								if (EmptyUtil.isEmpty(row.getCell(j)))
								{
									continue;
								}
								String cellValue = getCellValue(row.getCell(j));
								String title = getCellValue(titleRow.getCell(j));
								if (EqualsUtil.StringEquals("TableName", title, false))
									childtable.setTableName(cellValue);
								if (EqualsUtil.StringEquals("LogTableName", title, false))
									childtable.setLogTableName(cellValue);
								
							}
							int last = objectmodels.size() > 0 ? objectmodels.size() - 1 : 0;
							objectmodels.get(last).getChildTables().add(childtable);
						}
						if (thisRowType.equals(2))
						{
							FindColumn findcolumn = new FindColumn();
							for (int j = Object_Find; j < Object_Form; j++)
							{
								if (EmptyUtil.isEmpty(row.getCell(j)))
								{
									continue;
								}
								String cellValue = getCellValue(row.getCell(j));
								String title = getCellValue(titleRow.getCell(j));
								if (EqualsUtil.StringEquals("ColumnDescription", title, false))
									findcolumn.setColumnDescription(cellValue);
								if (EqualsUtil.StringEquals("ColumnAlias", title, false))
									findcolumn.setColumnAlias(cellValue);
								
							}
							int last = objectmodels.size() > 0 ? objectmodels.size() - 1 : 0;
							objectmodels.get(last).getFindColumns().add(findcolumn);
						}
						if (thisRowType.equals(3))
						{
							FormColumn formcolumn = new FormColumn();
							for (int j = Object_Form; j < Object_Form+2; j++)
							{
								if (EmptyUtil.isEmpty(row.getCell(j)))
								{
									continue;
								}
								String cellValue = getCellValue(row.getCell(j));
								String title = getCellValue(titleRow.getCell(j));
								if (EqualsUtil.StringEquals("FormColumnDescription", title, false))
									formcolumn.setFormColumnDescription(cellValue);
								if (EqualsUtil.StringEquals("FormColumnAlias", title, false))
									formcolumn.setFormColumnAlias(cellValue);
								
							}
							int last = objectmodels.size() > 0 ? objectmodels.size() - 1 : 0;
							objectmodels.get(last).getFormColumns().add(formcolumn);
						}
					}
					sapCustom.setObjectmodels(objectmodels);
				}
			}
			custemObject=sapCustom;
			XmlUtil.ObjectToFile(sapCustom, xmlpath.replace("/", "\\"));
			return true;
		}
		catch (Exception ex)
		{
			errMessage = ex.getMessage();
			ex.printStackTrace();
			return false;
		}
	}
	
	public String getXmlpath()
	{
		return xmlpath;
	}
	
	public void setXmlpath(String xmlpath)
	{
		this.xmlpath = xmlpath;
	}
	
	public String getExcelpath()
	{
		return excelpath;
	}
	
	public void setExcelpath(String excelpath)
	{
		this.excelpath = excelpath;
	}

	public String getErrMessage()
	{
		return errMessage;
	}

	public void setErrMessage(String errMessage)
	{
		this.errMessage = errMessage;
	}
	
	// [start] 根据界面值转换为B1需要的值
	private  Boolean BoolYesAndNo(String value)
	{
		Boolean result = false;
		switch (value)
		{
			case "Y":
				result = true;
				break;
			
			default:
				break;
		}
		return result;
	}
	
	private  Integer Table_TableType(String value)
	{
		Integer result = 0;
		String[] v=value.split("-");
		if(v.length>1)
		{
			value=v[0];
		}
		switch (value)
		{
			case "Document":
				result = SBOCOMConstants.BoUTBTableType_bott_Document;
				break;
			case "DocumentLines":
				result = SBOCOMConstants.BoUTBTableType_bott_DocumentLines;
				break;
			case "MasterData":
				result = SBOCOMConstants.BoUTBTableType_bott_MasterData;
				break;
			case "MasterDataLines":
				result = SBOCOMConstants.BoUTBTableType_bott_MasterDataLines;
				break;
			case "NoObject":
				result = SBOCOMConstants.BoUTBTableType_bott_NoObject;
				break;
			case "NoObjectAutoIncrement":
				result = SBOCOMConstants.BoUTBTableType_bott_NoObjectAutoIncrement;
				break;
			default:
				break;
		}
		return result;
	}
	
	private  Integer Field_Type(String value)
	{
		Integer result = 0;
		String[] v=value.split("-");
		if(v.length>1)
		{
			value=v[0];
		}
		switch (value)
		{
			case "Alpha":
				result = SBOCOMConstants.BoFieldTypes_db_Alpha;
				break;
			case "Date":
				result = SBOCOMConstants.BoFieldTypes_db_Date;
				break;
			case "Float":
				result = SBOCOMConstants.BoFieldTypes_db_Float;
				break;
			case "Memo":
				result = SBOCOMConstants.BoFieldTypes_db_Memo;
				break;
			case "Numeric":
				result = SBOCOMConstants.BoFieldTypes_db_Numeric;
				break;
			default:
				break;
		}
		return result;
	}
	
	private  Integer Field_SubType(String value)
	{
		Integer result = 0;
		String[] v=value.split("-");
		if(v.length>1)
		{
			value=v[0];
		}
		switch (value)
		{
			case "Address":
				result = SBOCOMConstants.BoFldSubTypes_st_Address;
				break;
			case "Image":
				result = SBOCOMConstants.BoFldSubTypes_st_Image;
				break;
			case "Link":
				result = SBOCOMConstants.BoFldSubTypes_st_Link;
				break;
			case "Measurement":
				result = SBOCOMConstants.BoFldSubTypes_st_Measurement;
				break;
			case "None":
				result = SBOCOMConstants.BoFldSubTypes_st_None;
				break;
			case "Percentage":
				result = SBOCOMConstants.BoFldSubTypes_st_Percentage;
				break;
			case "Phone":
				result = SBOCOMConstants.BoFldSubTypes_st_Phone;
				break;
			case "Price":
				result = SBOCOMConstants.BoFldSubTypes_st_Price;
				break;
			case "Quantity":
				result = SBOCOMConstants.BoFldSubTypes_st_Quantity;
				break;
			case "Rate":
				result = SBOCOMConstants.BoFldSubTypes_st_Rate;
				break;
			case "Sum":
				result = SBOCOMConstants.BoFldSubTypes_st_Sum;
				break;
			case "Time":
				result = SBOCOMConstants.BoFldSubTypes_st_Time;
				break;
			default:
				break;
		}
		return result;
	}
	
	private  Integer YesAndNo(String value)
	{
		Integer result = 0;
		switch (value)
		{
			case "Y":
				result = SBOCOMConstants.BoYesNoEnum_tYES;
				break;
			case "N":
				result = SBOCOMConstants.BoYesNoEnum_tNO;
				break;
			default:
				break;
		}
		return result;
	}
	
	private static Integer OBJ_ObjectType(String value)
	{
		Integer result = 0;
		String[] v=value.split("-");
		if(v.length>1)
		{
			value=v[0];
		}
		switch (value)
		{
			case "Document":
				result = SBOCOMConstants.BoUDOObjType_boud_Document;
				break;
			case "MasterData":
				result = SBOCOMConstants.BoUDOObjType_boud_MasterData;
				break;
			default:
				break;
		}
		return result;
	}
	
	// [end]
	
	private String getCellValue(XSSFCell cell)
	{
		String cellValue = "";
		DecimalFormat df = new DecimalFormat("#");
		switch (cell.getCellType())
		{
			case XSSFCell.CELL_TYPE_STRING:
				cellValue = cell.getRichStringCellValue().getString().trim();
				break;
			case XSSFCell.CELL_TYPE_NUMERIC:
				cellValue = df.format(cell.getNumericCellValue()).toString();
				break;
			case XSSFCell.CELL_TYPE_BOOLEAN:
				cellValue = String.valueOf(cell.getBooleanCellValue()).trim();
				break;
			case XSSFCell.CELL_TYPE_FORMULA:
				cellValue = cell.getCellFormula();
				break;
			default:
				cellValue = "";
		}
		return cellValue;
	}

	public SapCustomObject getCustemObject()
	{
		return custemObject;
	}

	public void setCustemObject(SapCustomObject custemObject)
	{
		this.custemObject = custemObject;
	}

}
