package cn.ava.saputil;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import cn.ava.publics.util.EmptyUtil;

import com.sap.smb.sbo.api.SBOCOMConstants;

@XmlRootElement(name = "BusinessObject")
public class SapBusinessObjectsModel
{
	public SapBusinessObjectsModel()
	{
	}
	
	// [start] 字段取值
	public static class ObjectsDataType
	{
		// 对象类型
		public static final Integer ObjectType_Document = SBOCOMConstants.BoUDOObjType_boud_Document;
		public static final Integer ObjectType_MasterData = SBOCOMConstants.BoUDOObjType_boud_MasterData;
		// 能否删除
		public static final Integer CanDelete_Yes = SBOCOMConstants.BoYesNoEnum_tYES;
		public static final Integer CanDelete_No = SBOCOMConstants.BoYesNoEnum_tNO;
		// 能否关闭
		public static final Integer CanClose_Yes = SBOCOMConstants.BoYesNoEnum_tYES;
		public static final Integer CanClose_No = SBOCOMConstants.BoYesNoEnum_tNO;
		// 能否取消
		public static final Integer CanCancel_Yes = SBOCOMConstants.BoYesNoEnum_tYES;
		public static final Integer CanCancel_No = SBOCOMConstants.BoYesNoEnum_tNO;
		// 能否年结
		public static final Integer CanYearTransfer_Yes = SBOCOMConstants.BoYesNoEnum_tYES;
		public static final Integer CanYearTransfer_No = SBOCOMConstants.BoYesNoEnum_tNO;
		// 能否管理服务
		public static final Integer ManageSeries_Yes = SBOCOMConstants.BoYesNoEnum_tYES;
		public static final Integer ManageSeries_No = SBOCOMConstants.BoYesNoEnum_tNO;
		// 是否有日志表
		public static final Integer CanLog_Yes = SBOCOMConstants.BoYesNoEnum_tYES;
		public static final Integer CanLog_No = SBOCOMConstants.BoYesNoEnum_tNO;
		
	}
	
	// [end]
	
	private String Code;
	private String TableName;
	private Integer ObjectType;
	private String Name;
	private Integer CanDelete;
	private Integer CanClose;
	private Integer CanCancel;
	private Integer CanYearTransfer;
	private Integer ManageSeries;
	private Integer CanLog;
	private String LogTableName;
	private List<FindColumn> FindColumns;
	private List<FormColumn> FormColumns;
	private List<ChildTable> ChildTables;
	
	// [start] 查找字段
	public static class FindColumn
	{
		public FindColumn()
		{
		}
		
		public FindColumn(String cAlias, String cDescription)
		{
			ColumnAlias = cAlias;
			ColumnDescription = cDescription;
		}
		
		private String ColumnAlias;
		private String ColumnDescription;
		
		@XmlAttribute(name = "ColumnAlias")
		public String getColumnAlias()
		{
			return ColumnAlias;
		}
		
		public void setColumnAlias(String columnAlias)
		{
			ColumnAlias = columnAlias;
		}
		
		@XmlAttribute(name = "ColumnDescription")
		public String getColumnDescription()
		{
			return ColumnDescription;
		}
		
		public void setColumnDescription(String columnDescription)
		{
			ColumnDescription = columnDescription;
		}
	}
	
	// [end]
	// [start] 默认窗体
	public static class FormColumn
	{
		public FormColumn()
		{
		}
		
		public FormColumn(String fAlias, String fDescription)
		{
			FormColumnAlias = fAlias;
			FormColumnDescription = fDescription;
		}
		
		private String FormColumnAlias;
		private String FormColumnDescription;
		
		@XmlAttribute(name = "FormColumnAlias")
		public String getFormColumnAlias()
		{
			return FormColumnAlias;
		}
		
		public void setFormColumnAlias(String formColumnAlias)
		{
			FormColumnAlias = formColumnAlias;
		}
		
		@XmlAttribute(name = "FormColumnDescription")
		public String getFormColumnDescription()
		{
			return FormColumnDescription;
		}
		
		public void setFormColumnDescription(String formColumnDescription)
		{
			FormColumnDescription = formColumnDescription;
		}
	}
	
	// [end]
	// [start] 子表
	public static class ChildTable
	{
		public ChildTable()
		{
		}
		
		public ChildTable(String tabname, String logtablenmae)
		{
			TableName = tabname;
			LogTableName = logtablenmae;
		}
		
		private String TableName;
		private String LogTableName;
		
		@XmlAttribute(name = "TableName")
		public String getTableName()
		{
			return TableName;
		}
		
		public void setTableName(String tableName)
		{
			TableName = tableName;
		}
		
		@XmlAttribute(name = "LogTableName")
		public String getLogTableName()
		{
			return LogTableName;
		}
		
		public void setLogTableName(String logTableName)
		{
			LogTableName = logTableName;
		}
	}
	
	// [end]
	@XmlAttribute(name = "Code")
	public String getCode()
	{
		return Code;
	}
	
	public void setCode(String code)
	{
		Code = code;
	}
	
	@XmlAttribute(name = "TableName")
	public String getTableName()
	{
		return TableName;
	}
	
	public void setTableName(String tableName)
	{
		TableName = tableName;
	}
	
	@XmlAttribute(name = "ObjectType")
	public Integer getObjectType()
	{
		return ObjectType;
	}
	
	public void setObjectType(Integer objectType)
	{
		ObjectType = objectType;
	}
	
	@XmlAttribute(name = "Name")
	public String getName()
	{
		return Name;
	}
	
	public void setName(String name)
	{
		Name = name;
	}
	
	@XmlAttribute(name = "CanDelete")
	public Integer getCanDelete()
	{
		return CanDelete;
	}
	
	public void setCanDelete(Integer canDelete)
	{
		CanDelete = canDelete;
	}
	
	@XmlAttribute(name = "CanClose")
	public Integer getCanClose()
	{
		return CanClose;
	}
	
	public void setCanClose(Integer canClose)
	{
		CanClose = canClose;
	}
	
	@XmlAttribute(name = "CanCancel")
	public Integer getCanCancel()
	{
		return CanCancel;
	}
	
	public void setCanCancel(Integer canCancel)
	{
		CanCancel = canCancel;
	}
	
	@XmlAttribute(name = "CanYearTransfer")
	public Integer getCanYearTransfer()
	{
		return CanYearTransfer;
	}
	
	public void setCanYearTransfer(Integer canYearTransfer)
	{
		CanYearTransfer = canYearTransfer;
	}
	
	@XmlAttribute(name = "ManageSeries")
	public Integer getManageSeries()
	{
		return ManageSeries;
	}
	
	public void setManageSeries(Integer manageSeries)
	{
		ManageSeries = manageSeries;
	}
	
	@XmlAttribute(name = "CanLog")
	public Integer getCanLog()
	{
		return CanLog;
	}
	
	public void setCanLog(Integer canLog)
	{
		CanLog = canLog;
	}
	
	@XmlAttribute(name = "LogTableName")
	public String getLogTableName()
	{
		return LogTableName;
	}
	
	public void setLogTableName(String logTableName)
	{
		LogTableName = logTableName;
	}
	
	@XmlElement(name = "FindColumns")
	public List<FindColumn> getFindColumns()
	{
		if (EmptyUtil.isEmpty(FindColumns))
		{
			FindColumns = new ArrayList<SapBusinessObjectsModel.FindColumn>();
		}
		return FindColumns;
	}
	
	public void setFindColumns(List<FindColumn> findColumns)
	{
		FindColumns = findColumns;
	}
	
	@XmlElement(name = "FormColumns")
	public List<FormColumn> getFormColumns()
	{
		if (EmptyUtil.isEmpty(FormColumns))
		{
			FormColumns = new ArrayList<SapBusinessObjectsModel.FormColumn>();
		}
		return FormColumns;
	}
	
	public void setFormColumns(List<FormColumn> formColumns)
	{
		FormColumns = formColumns;
	}
	
	@XmlElement(name = "ChildTables")
	public List<ChildTable> getChildTables()
	{
		if (EmptyUtil.isEmpty(ChildTables))
		{
			ChildTables = new ArrayList<SapBusinessObjectsModel.ChildTable>();
		}
		return ChildTables;
	}
	
	public void setChildTables(List<ChildTable> childTables)
	{
		ChildTables = childTables;
	}
}
