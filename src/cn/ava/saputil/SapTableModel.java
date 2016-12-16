package cn.ava.saputil;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import cn.ava.publics.util.EmptyUtil;

import com.sap.smb.sbo.api.SBOCOMConstants;

@XmlRootElement(name="Table")
public class SapTableModel
{
	public SapTableModel(){}
	/**
	 * 是否需要创建表
	 */	
	private Boolean isCreateTable;
	/**
	 * 是否为用户表
	 */
	private Boolean isUserTable;
	/**
	 * 表名
	 */
	private String TableName;
	/**
	 * 表描述
	 */
	private String TableDescription;
	/**
	 * 表类型
	 */
	private Integer TableType;
	/**
	 * 字段列表
	 */

	private List<SapFieldsModel> FiledsList;
	
	public static class SapTableType
	{
		public static final Integer Document=SBOCOMConstants.BoUTBTableType_bott_Document;
		public static final Integer DocumentLines=SBOCOMConstants.BoUTBTableType_bott_DocumentLines;
		public static final Integer MasterData=SBOCOMConstants.BoUTBTableType_bott_MasterData;
		public static final Integer MasterDataLines=SBOCOMConstants.BoUTBTableType_bott_MasterDataLines;
		public static final Integer NoObject=SBOCOMConstants.BoUTBTableType_bott_NoObject;
		public static final Integer NoObjectAutoIncrement=SBOCOMConstants.BoUTBTableType_bott_NoObjectAutoIncrement;
	}
	@XmlAttribute(name="IsCreateTable")
	public Boolean getIsCreateTable()
	{
		return isCreateTable;
	}
	public void setIsCreateTable(Boolean isCreateTable)
	{
		this.isCreateTable = isCreateTable;
	}

	@XmlAttribute(name="IsUserTable")
	public Boolean getIsUserTable()
	{
		return isUserTable;
	}

	public void setIsUserTable(Boolean isUserTable)
	{
		this.isUserTable = isUserTable;
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
	@XmlAttribute(name="TableDescription")
	public String getTableDescription()
	{
		return TableDescription;
	}

	public void setTableDescription(String tableDescription)
	{
		TableDescription = tableDescription;
	}
	@XmlAttribute(name="TableType")
	public Integer getTableType()
	{
		return TableType;
	}

	public void setTableType(Integer tableType)
	{
		TableType = tableType;
	}
	@XmlElement(name="Fileds")
	public List<SapFieldsModel> getFiledsList()
	{
		if(EmptyUtil.isEmpty(FiledsList))
		{
			FiledsList=new ArrayList<SapFieldsModel>();
		}
		return FiledsList;
	}

	public void setFiledsList(List<SapFieldsModel> filedsList)
	{
		FiledsList = filedsList;
	}

}
