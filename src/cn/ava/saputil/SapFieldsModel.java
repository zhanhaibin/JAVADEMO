package cn.ava.saputil;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import cn.ava.publics.util.EmptyUtil;

import com.sap.smb.sbo.api.SBOCOMConstants;

@XmlRootElement(name="Field")
public class SapFieldsModel
{
	public SapFieldsModel()
	{
	}
	
	// [start] 字段取值枚举
	public static class FiledDataType
	{
		// Type字段值
		public static final Integer Type_Alpha = SBOCOMConstants.BoFieldTypes_db_Alpha;
		public static final Integer Type_Date = SBOCOMConstants.BoFieldTypes_db_Date;
		public static final Integer Type_Float = SBOCOMConstants.BoFieldTypes_db_Float;
		public static final Integer Type_Memo = SBOCOMConstants.BoFieldTypes_db_Memo;
		public static final Integer Type_Numeric = SBOCOMConstants.BoFieldTypes_db_Numeric;
		// SubType字段值
		public static final Integer SubType_Address = SBOCOMConstants.BoFldSubTypes_st_Address;
		public static final Integer SubType_Image = SBOCOMConstants.BoFldSubTypes_st_Image;
		public static final Integer SubType_Link = SBOCOMConstants.BoFldSubTypes_st_Link;
		public static final Integer SubType_Measurement = SBOCOMConstants.BoFldSubTypes_st_Measurement;
		public static final Integer SubType_None = SBOCOMConstants.BoFldSubTypes_st_None;
		public static final Integer SubType_Percentage = SBOCOMConstants.BoFldSubTypes_st_Percentage;
		public static final Integer SubType_Phone = SBOCOMConstants.BoFldSubTypes_st_Phone;
		public static final Integer SubType_Price = SBOCOMConstants.BoFldSubTypes_st_Price;
		public static final Integer SubType_Quantity = SBOCOMConstants.BoFldSubTypes_st_Quantity;
		public static final Integer SubType_Rate = SBOCOMConstants.BoFldSubTypes_st_Rate;
		public static final Integer SubType_Sum = SBOCOMConstants.BoFldSubTypes_st_Sum;
		public static final Integer SubType_Time = SBOCOMConstants.BoFldSubTypes_st_Time;
		// Mandatory字段值
		public static final Integer Mandatory_tYES = SBOCOMConstants.BoYesNoEnum_tYES;
		public static final Integer Mandatory_tNO = SBOCOMConstants.BoYesNoEnum_tNO;
	}
	
	// [end]
	
	/**
	 * 是否为系统字段
	 */
	private Boolean isSystem;
	private String TableName;// 表名
	private String Name;// 字段
	private String Description;// 字段描述
	private Integer Type;// 数据类型(SAPbobsCOM.BoFieldTypes)oField.DataType;//数据类型
	private Integer SubType;// 结构 = (SAPbobsCOM.BoFldSubTypes)oField.SubType;//结构
	private Integer EditSize;// = oField.EditSize;//长度
	private String DefaultValue="";// = oField.DefaultValue;//默认值
	private String LinkedTable="";// = oField.LinkTable;//连接表
	private Integer Mandatory;// = (SAPbobsCOM.BoYesNoEnum)oField.IsMandatory; //强制的
	private List<ValidValue> MyValidValues;
	
	@XmlAttribute(name="IsSystem")
	public Boolean getIsSystem()
	{
		return isSystem;
	}
	
	public void setIsSystem(Boolean isSystem)
	{
		this.isSystem = isSystem;
	}
	@XmlAttribute(name="TableName")
	public String getTableName()
	{
		return TableName;
	}
	
	public void setTableName(String tableName)
	{
		TableName = tableName;
	}
	@XmlAttribute(name="Name")
	public String getName()
	{
		return Name;
	}
	
	public void setName(String name)
	{
		Name = name;
	}
	@XmlAttribute(name="Description")
	public String getDescription()
	{
		return Description;
	}
	
	public void setDescription(String description)
	{
		Description = description;
	}
	@XmlAttribute(name="Type")
	public Integer getType()
	{
		return Type;
	}
	
	public void setType(Integer type)
	{
		Type = type;
	}
	@XmlAttribute(name="SubType")
	public Integer getSubType()
	{
		return SubType;
	}
	
	public void setSubType(Integer subType)
	{
		SubType = subType;
	}
	@XmlAttribute(name="EditSize")
	public Integer getEditSize()
	{
		return EditSize;
	}
	
	public void setEditSize(Integer editSize)
	{
		EditSize = editSize;
	}
	@XmlAttribute(name="DefaultValue")
	public String getDefaultValue()
	{
		return DefaultValue;
	}
	
	public void setDefaultValue(String defaultValue)
	{
		DefaultValue = defaultValue;
	}
	@XmlAttribute(name="LinkedTable")
	public String getLinkedTable()
	{
		return LinkedTable;
	}
	
	public void setLinkedTable(String linkedTable)
	{
		LinkedTable = linkedTable;
	}
	@XmlAttribute(name="Mandatory")
	public Integer getMandatory()
	{
		return Mandatory;
	}
	
	public void setMandatory(Integer mandatory)
	{
		Mandatory = mandatory;
	}
	@XmlElement(name="ValidValues")
	public List<ValidValue> getMyValidValues()
	{
		if (EmptyUtil.isEmpty(MyValidValues))
		{
			MyValidValues = new ArrayList<SapFieldsModel.ValidValue>();
		}
		
		return MyValidValues;
	}
	
	public void setMyValidValues(List<ValidValue> myValidValues)
	{
		MyValidValues = myValidValues;
	}
	
	// [start] 自定义字段取值范围
	@XmlRootElement(name="ValidValue")
	public static class ValidValue
	{
		public ValidValue()
		{
		}
		public ValidValue(String mvalue,String mDescription)
		{
			Value=mvalue;
			Description=mDescription;
		}
		private String Value;
		private String Description;
		@XmlAttribute(name="Value")
		public String getValue()
		{
			return Value;
		}
		
		public void setValue(String value)
		{
			Value = value;
		}
		@XmlAttribute(name="Description")
		public String getDescription()
		{
			return Description;
		}
		
		public void setDescription(String description)
		{
			Description = description;
		}
	}
	// [end]
}
