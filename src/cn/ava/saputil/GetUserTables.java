package cn.ava.saputil;
import cn.ava.publics.util.EmptyUtil;
import cn.ava.saputil.SapBusinessObjectsModel.ChildTable;
import cn.ava.saputil.SapBusinessObjectsModel.FindColumn;
import cn.ava.saputil.SapBusinessObjectsModel.FormColumn;
import cn.ava.saputil.SapFieldsModel.ValidValue;

import java.util.ArrayList;
import java.util.List;

import com.sap.smb.sbo.api.Recordset;
import com.sap.smb.sbo.api.SBOCOMConstants;
import com.sap.smb.sbo.api.UserFieldsMD;
import com.sap.smb.sbo.api.UserObjectsMD;
import com.sap.smb.sbo.api.UserTablesMD;

public class GetUserTables {
	
	private MySapConnectConfig connconfig;
	private List<String> Errors = new ArrayList<String>();
	
	public GetUserTables(MySapConnectConfig sapconnconfig)
	{
		connconfig = sapconnconfig;
	}
	
	public void getSapUserTables()
	{
		
		UserFieldsMD oUserFieldsMD = null;
		MySapConnect sap = new MySapConnect(connconfig);
		
	 
		int conn = sap.connect();
		if (conn != 0)
		{
			Errors.add("连接状态为" + conn + ",值-1=失败;值-2=未初始化");
			return;
		}
		oUserFieldsMD = new UserFieldsMD(sap.company.getBusinessObject(SBOCOMConstants.BoObjectTypes_oUserFields));
		String TableName = "@COR_COR_FS";
	
		
		int FieldID = GetFieldID(sap, TableName, "FormType");
		 
			// 更新
			oUserFieldsMD.getByKey(TableName, FieldID);
			String getfieldstr =	oUserFieldsMD.getAsXML();
			// [end]
		 
				Errors.add(getfieldstr);
			 
		
	}
	
	public void createSapUserFields()
	{
		
		UserFieldsMD oUserFieldsMD = null;
		MySapConnect sap = new MySapConnect(connconfig);
		int conn = sap.connect();
		if (conn != 0)
		{
			Errors.add("连接状态为" + conn + ",值-1=失败;值-2=未初始化");
			return;
		}
		oUserFieldsMD = new UserFieldsMD(sap.company.getBusinessObject(SBOCOMConstants.BoObjectTypes_oUserFields));
		
		oUserFieldsMD.setTableName("@AVA_TEST");
			    oUserFieldsMD.setName("test");
			    oUserFieldsMD.setDescription("test");
			    int Result = oUserFieldsMD.add();
			    if (Result != 0)
				{
					String ErrorMsg = "更新结果：" + Result + "；错误代码:" + sap.company.getLastErrorCode() + "错误：" + sap.company.getLastErrorDescription() ;
					Errors.add(ErrorMsg);
				}
			    else
			    {
			    	Errors.add(oUserFieldsMD.getAsXML());			    }
				 
	}
	
	/**
	 * 获取字段ID
	 * 
	 * @param sap
	 * @param TableName 表名
	 * @param FieldName 字段名
	 * @return 字段ID
	 */
	private int GetFieldID(MySapConnect sap, String TableName, String FieldName)
	{
		Recordset ors;
		String SQL;
		int Result;
		// ors = (Recordset) sap.company.getBusinessObject(SBOCOMConstants.BoObjectTypes_BoRecordset);
		ors = new Recordset(sap.company.getBusinessObject(SBOCOMConstants.BoObjectTypes_BoRecordset));
		List<String> params = new ArrayList<String>();
		params.add(TableName);
		params.add(FieldName);
		if (sap.company.getDbServerType().equals(SBOCOMConstants.BoDataServerTypes_dst_HANADB))
			SQL = "SELECT IFNULL(\"FieldID\",-1) \"FieldID\",\"TableID\",\"AliasID\" FROM \"CUFD\" WHERE \"TableID\" = '" + TableName + "' AND \"AliasID\" = '" + FieldName + "'";
		else SQL = "SELECT ISNULL(\"FieldID\",-1) \"FieldID\",\"TableID\",\"AliasID\" FROM \"CUFD\" WHERE \"TableID\" = '" + TableName + "' AND \"AliasID\" = '" + FieldName + "'";
		
		ors.doQuery(SQL);
		if (ors.getRecordCount() > 0)
		{
			Result = Integer.parseInt(ors.getFields().item("FieldID").getValue().toString());
		}
		else
		{
			Result = -1;
		}
		ors = null;
		return Result;
	}
	

	public List<String> getErrors()
	{
		return Errors;
	}
}
