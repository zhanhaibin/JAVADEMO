package cn.ava.saputil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.tree.AbstractAttribute; 
import cn.ava.publics.util.EmptyUtil;
import cn.ava.publics.util.EqualsUtil;
import cn.ava.publics.util.XmlUtil;
import cn.ava.saputil.SapFieldsModel.ValidValue;

import com.sap.smb.sbo.api.SBOCOMConstants;

public class MyCustomExcelToXml
{
	private String xmlpath;
	private String excelpath;
	private String errMessage;
	private SapCustomObject custemObject;
	
	public MyCustomExcelToXml(String xml, String excel)
	{
		xmlpath = xml;
		excelpath = excel;
	}
	
	public void GetMappingXML(){
		  
			File xmlFile = new File("D:/1/excelmapping.xml");  
	        FileInputStream fis = null;  
	        try {  
	            fis = new FileInputStream(xmlFile);  
	        } catch (FileNotFoundException e) {  
	            e.printStackTrace();  
	            System.err.println("File is not exsit!");  
	        }  
	        SAXReader saxReader = new SAXReader();  
	        List rowList = null;  
	        try {  
	            //生成文档对应实体  
	            Document doc = saxReader.read(fis);  
	            //获取指定路径下的元素列表,这里指获取所有的data下的row元素  
	            rowList = doc.selectNodes("//ModelMapping/ObjectArea");  
	        } catch (DocumentException e) {  
	            e.printStackTrace();  
	        }  
	        for(Iterator iter = rowList.iterator();iter.hasNext();){  
	            //获得具体的row元素   
	            Element element = (Element)iter.next();  
	            //获得row元素的所有属性列表  
	            List elementList = element.attributes();  
	            for(Iterator iter1 = elementList.iterator();iter1.hasNext();){  
	                //将每个属性转化为一个抽象属性，然后获取其名字和值  
	                AbstractAttribute aa = (AbstractAttribute)iter1.next();  
	                System.out.println("Name:"+aa.getName()+";Value:"+aa.getValue());
	                 
	            }  
	                            //输出：  
	                            //Name:queryDTO.enterpriseId;Value:gfd  
	                            //Name:queryDTO.loginName;Value:gdfg  
	                            //Name:queryDTO.state;Value:0  
	            System.out.println(element.getName());  
	                            //输出：  
	                            //row  
	         
	            // 取得row元素的queryDTO.enterpriseId属性的值  
	            System.out.println(element.attributeValue("Conditions"));  
	                            //输出：  
	                            //gfd  
	            //如果element下有子元素，(类似width="**")，要想获得该子元素的值，可以用如下方法  
	            System.out.println(element.elementText("Propertys"));//因为没有，所以输出为null。  
	        }  
		     
		     
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
				XSSFRow XSSTableRow = sheet.getRow(0);
			
				SapTableModel saptable = null;
				saptable = new SapTableModel();
				saptable.setTableName(XSSTableRow.getCell(1).toString()); 			//表名
				saptable.setTableDescription(XSSTableRow.getCell(4).toString());	//表描述
				saptable.setTableType(Table_TableType(XSSTableRow.getCell(9).toString()));			//表类型
				tablemodels.add(saptable);
				System.out.println("TableName：" + XSSTableRow.getCell(1).toString());
				System.out.println("TableType：" + XSSTableRow.getCell(9).toString());
				
				XSSFRow titleRow = sheet.getRow(2);
				SapFieldsModel sapfields = null;
				ValidValue validvalue = null;
				 
				if (EmptyUtil.isEmpty(titleRow))
				{
					continue;
				}
				
				
				int lastRowNum = sheet.getLastRowNum();
				for (int i = 3; i <= 28; i++)
				{
						// Table对象 定义每个对象的起始列
					int Table_Table = 0;
						 
					XSSFRow row = sheet.getRow(i);
					String t0 = EmptyUtil.isNotEmpty(row.getCell(Table_Table)) ? getCellValue(row.getCell(Table_Table)) : "";

					sapfields = new SapFieldsModel();
					// [start] Table对象中的字段
					for (int j = 0; j < row.getLastCellNum(); j++)
					{
						if (EmptyUtil.isEmpty(row.getCell(j)))
						{
							continue;
						}
						String cellValue = getCellValue(row.getCell(j));
						String title = getCellValue(titleRow.getCell(j));
						
						if (EqualsUtil.StringEquals("字段名称", title, false))
							sapfields.setName(cellValue);
						if (EqualsUtil.StringEquals("字段描述", title, false))
							sapfields.setDescription(cellValue);
						if (EqualsUtil.StringEquals("模型属性名", title, false))
							sapfields.setIsSystem(BoolYesAndNo(cellValue));
						if (EqualsUtil.StringEquals("类型", title, false))
							sapfields.setType(Field_Type(cellValue));
						if (EqualsUtil.StringEquals("结构", title, false))
							sapfields.setSubType(Field_SubType(cellValue));
						if (EqualsUtil.StringEquals("数据长度", title, false))
							sapfields.setEditSize(Integer.parseInt(cellValue));
						if (EqualsUtil.StringEquals("默认值", title, false))
							sapfields.setDefaultValue(cellValue);
//									if (EqualsUtil.StringEquals("Mandatory", title, false))
//										sapfields.setMandatory(YesAndNo(cellValue));
						if (EqualsUtil.StringEquals("连接表", title, false))
							sapfields.setLinkedTable(cellValue);
					}
					// [end]
					int last = tablemodels.size() > 0 ? tablemodels.size() - 1 : 0;
					tablemodels.get(last).getFiledsList().add(sapfields);
							
				}
				sapCustom.setTablemodels(tablemodels);
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
