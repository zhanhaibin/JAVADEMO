package cn.ava.saputil;

import java.util.ArrayList;
import java.util.List;

import cn.ava.publics.util.EmptyUtil;
import cn.ava.saputil.SapBusinessObjectsModel.ChildTable;
import cn.ava.saputil.SapBusinessObjectsModel.FindColumn;
import cn.ava.saputil.SapBusinessObjectsModel.FormColumn;
import cn.ava.saputil.SapFieldsModel.ValidValue;

import com.sap.smb.sbo.api.Recordset;
import com.sap.smb.sbo.api.SBOCOMConstants;
import com.sap.smb.sbo.api.UserFieldsMD;
import com.sap.smb.sbo.api.UserObjectsMD;
import com.sap.smb.sbo.api.UserTablesMD;

public class CreateUserTables
{
	private MySapConnectConfig connconfig;
	private List<String> Errors = new ArrayList<String>();
	
	public CreateUserTables(MySapConnectConfig sapconnconfig)
	{
		connconfig = sapconnconfig;
	}
	
	/**
	 * 创建自定义表和字段
	 * 
	 * @param listTable
	 */
	public void SapCreateUserTables(SapCustomObject sapCustomObj)
	{
		if (EmptyUtil.isNotEmpty(sapCustomObj))
		{
			
			List<SapTableModel> listTable = null;
			List<SapBusinessObjectsModel> listObjects = null;
			List<SapFieldsModel> listField = null;
			
			if (EmptyUtil.isNotEmpty(sapCustomObj.getTablemodels()))
			{
				listTable = sapCustomObj.getTablemodels();
			}
			if (EmptyUtil.isNotEmpty(sapCustomObj.getObjectmodels()))
			{
				listObjects = sapCustomObj.getObjectmodels();
			}
			if (EmptyUtil.isNotEmpty(sapCustomObj.getFields()))
			{
				listField = sapCustomObj.getFields();
			}
			int Result;
			UserTablesMD oUserTableMD = null;
			UserFieldsMD oUserFieldsMD = null;
			UserObjectsMD oUserObjectsMD = null;
			
			if (EmptyUtil.isNotEmpty(listTable))
			{
				// [start] 循环创建每个表和字段
				for (SapTableModel tmodel : listTable)
				{
					
//					// 如果不创建表返回继续下条
//					if (!tmodel.getIsCreateTable())
//					{
//						continue;
//					}
//					// [start] 如果是用户表则创建或更新
//					if (tmodel.getIsUserTable())
//					{
//					
//							
//							MySapConnect sap = new MySapConnect(connconfig);
//							int conn = sap.connect();
//							if (conn != 0)
//							{
//								Errors.add("连接状态为" + conn + ",值-1=失败;值-2=未初始化");
//								return;
//							}
//							
//							// 实例化表
//							oUserTableMD = new UserTablesMD(sap.company.getBusinessObject(SBOCOMConstants.BoObjectTypes_oUserTables));
//							// oUserTableMD = (UserTablesMD) sap.company.getBusinessObject(SBOCOMConstants.BoObjectTypes_oUserTables);
//							// 如果存在则更新
//							if (oUserTableMD.getByKey(tmodel.getTableName()))
//							{
//								// 更新表
//								oUserTableMD.setTableDescription(tmodel.getTableDescription());
//								Result = oUserTableMD.update();
//								if (Result != 0)
//								{
//									String ErrorMsg = "更新结果：" + Result + "；错误代码:" + sap.company.getLastErrorCode() + "错误：" + sap.company.getLastErrorDescription() + "\r\nTable:" + tmodel.getTableName();
//									Errors.add(ErrorMsg);
//								}
//								
//								sap.disconnect();
//							}
//							// 创建表
//							else
//							{
//								oUserTableMD.setTableName(tmodel.getTableName());
//								oUserTableMD.setTableDescription(tmodel.getTableDescription());
//								oUserTableMD.setTableType(tmodel.getTableType());
//								Result = oUserTableMD.add();
//								if (Result != 0)
//								{
//									String ErrorMsg = "添加结果：" + Result + "；错误代码:" + sap.company.getLastErrorCode() + "错误：" + sap.company.getLastErrorDescription() + "\r\nTable:" + tmodel.getTableName();
//									Errors.add(ErrorMsg);
//								}
//								sap.disconnect();
//							}
//							oUserTableMD = null;
//						 
//					}
					// [end]
					
					// [start] 创建自定义字段
					
					String TableName = tmodel.getIsUserTable() ? "@" + tmodel.getTableName() : tmodel.getTableName();
					MySapConnect sap = new MySapConnect(connconfig);
					int conn = sap.connect();
					if (conn != 0)
					{
						Errors.add("连接状态为" + conn + ",值-1=失败;值-2=未初始化");
						return;
					}
					// 循环创建字段
					for (SapFieldsModel fmodel : tmodel.getFiledsList())
					{
						try
						{
							// 如果是系统字段则不创建
							if (fmodel.getIsSystem())
							{
								continue;
							}
							// 实例化，每执行一个都置为null							
							oUserFieldsMD = new UserFieldsMD(sap.company.getBusinessObject(SBOCOMConstants.BoObjectTypes_oUserFields));
							try
							{
								int FieldID = GetFieldID(TableName, fmodel.getName());
								if (FieldID >= 0)
								{
									// 更新
									oUserFieldsMD.getByKey(TableName, FieldID);
									// 更新字段
									oUserFieldsMD.setTableName(TableName);// 表名
									oUserFieldsMD.setDescription(fmodel.getDescription());// 字段描述
									oUserFieldsMD.setType(fmodel.getType());// 数据类型
									if (CanSubType(fmodel))
									{
										oUserFieldsMD.setSubType(fmodel.getSubType());// 结构
									}
									if (CanEditSize(fmodel))
									{
										oUserFieldsMD.setEditSize(fmodel.getEditSize());// 长度
									}
									if (EmptyUtil.isNotEmpty(fmodel.getDefaultValue()))
									{
										oUserFieldsMD.setDefaultValue(fmodel.getDefaultValue());// 默认值
									}
									if (EmptyUtil.isNotEmpty(fmodel.getLinkedTable()))
									{
										oUserFieldsMD.setLinkedTable(fmodel.getLinkedTable());// 连接表
									}
									oUserFieldsMD.setMandatory(fmodel.getMandatory()); // 强制的
									// [start]字段有效值
									// 默认选项
									if (EmptyUtil.isNotEmpty(fmodel.getMyValidValues()))
									{
										for (ValidValue oValidValue : fmodel.getMyValidValues())
										{
											Boolean has = false;// 是否以及存在此可选值
											for (int i = 0; i < oUserFieldsMD.getValidValues().getCount(); i++)
											{
												oUserFieldsMD.getValidValues().setCurrentLine(i);
												if (oUserFieldsMD.getValidValues().getValue().equals(oValidValue.getValue()))
												{
													oUserFieldsMD.getValidValues().setDescription(oValidValue.getDescription());
													has = true;
													break;
												}
											}
											if (has)
											{
												oUserFieldsMD.getValidValues().setValue(oValidValue.getValue());
												oUserFieldsMD.getValidValues().setDescription(oValidValue.getDescription());
												oUserFieldsMD.getValidValues().add();
											}
										}
									}
									// [end]
									Result = oUserFieldsMD.update();
									if (Result != 0)
									{
										Errors.add("更新结果：" + Result + "；错误代码:" + sap.company.getLastErrorCode() + "错误：" + sap.company.getLastErrorDescription() + "\r\nTable:" + TableName + "更新字段:" + fmodel.getName() + "出错！");
									}
								}
								else
								{
									// 添加
									 
								    Errors.add("TableName:"+TableName+"------NAME:"+fmodel.getName()+"-----DESC:"+fmodel.getDescription());
									oUserFieldsMD.setTableName(TableName);// 表名
									oUserFieldsMD.setName(fmodel.getName()); // 字段
									oUserFieldsMD.setDescription(fmodel.getDescription());// 字段描述
								
									oUserFieldsMD.setType(fmodel.getType());// 数据类型
									oUserFieldsMD.setSize(fmodel.getEditSize());
									if (CanSubType(fmodel))
									{
										oUserFieldsMD.setSubType(fmodel.getSubType());// 结构
									}
									if (CanSubType(fmodel))
									{
										oUserFieldsMD.setEditSize(fmodel.getEditSize());// 长度
									}
									oUserFieldsMD.setDefaultValue(fmodel.getDefaultValue());// 默认值
									oUserFieldsMD.setLinkedTable(fmodel.getLinkedTable());// 连接表
									oUserFieldsMD.setMandatory(fmodel.getMandatory()); // 强制的
									// [start]字段有效值
									// 默认选项
									for (ValidValue oValidValue : fmodel.getMyValidValues())
									{
										oUserFieldsMD.getValidValues().setValue(oValidValue.getValue());
										oUserFieldsMD.getValidValues().setDescription(oValidValue.getDescription());
										oUserFieldsMD.getValidValues().add();
									}
									// [end]
									
									Result = oUserFieldsMD.add();
									String ssString= oUserFieldsMD.getAsXML();
									if (Result != 0)
									{
									
										Errors.add("添加结果：" + Result + "；错误代码:" + sap.company.getLastErrorCode() + "；错误：" + sap.company.getLastErrorDescription() + "；Table:" + TableName + "添加字段:" + fmodel.getName() + "出错！");
									}
									else
									{
//										Errors.add(String.valueOf(FieldID));
										Errors.add("成功："+ssString);
									}
								}
								
							}
							catch (Exception ex)
							{
								ex.printStackTrace();
								Errors.add("创建字段发生异常[01]:" + ex.getMessage() + "；" + ex.getLocalizedMessage());
							}
						}
						catch (Exception ex)
						{
							ex.printStackTrace();
							Errors.add("创建字段发生异常[02]:" + ex.getMessage() + "；" + ex.getLocalizedMessage());
						}
						finally
						{
							oUserFieldsMD = null;
							sap.disconnect();
						}
					}
					// [end]
				}
				// [end]
			}
//			if (EmptyUtil.isNotEmpty(listField))
//			{
//				// [start] 创建自定义字段
//				// 循环创建字段
//				for (SapFieldsModel fmodel : listField)
//				{
//					
//					MySapConnect sap = new MySapConnect(connconfig);
//					int conn = sap.connect();
//					if (conn != 0)
//					{
//						Errors.add("连接状态为" + conn + ",值-1=失败;值-2=未初始化");
//						return;
//					}
//					try
//					{
//						// 如果是系统字段则不创建
//						if (fmodel.getIsSystem())
//						{
//							continue;
//						}
//						// 实例化，每执行一个都置为null
//						oUserFieldsMD = new UserFieldsMD(sap.company.getBusinessObject(SBOCOMConstants.BoObjectTypes_oUserFields));
//						// oUserFieldsMD = (UserFieldsMD) sap.company.getBusinessObject(SBOCOMConstants.BoObjectTypes_oUserFields);
//						try
//						{
//							if (GetFieldID(sap, fmodel.getTableName(), fmodel.getName()) > 0)
//							{
//								// 更新
//								oUserFieldsMD.getByKey(fmodel.getTableName(), GetFieldID(sap, fmodel.getTableName(), fmodel.getName()));
//								// 更新字段
//								oUserFieldsMD.setTableName(fmodel.getTableName());// 表名
//								oUserFieldsMD.setDescription(fmodel.getDescription());// 字段描述
//								oUserFieldsMD.setType(fmodel.getType());// 数据类型
//								if (CanSubType(fmodel))
//								{
//									oUserFieldsMD.setSubType(fmodel.getSubType());// 结构\
//								}
//								if (CanEditSize(fmodel))
//								{
//									oUserFieldsMD.setEditSize(fmodel.getEditSize());// 长度
//								}
//								oUserFieldsMD.setDefaultValue(fmodel.getDefaultValue());// 默认值
//								oUserFieldsMD.setLinkedTable(fmodel.getLinkedTable());// 连接表
//								oUserFieldsMD.setMandatory(fmodel.getMandatory()); // 强制的
//								// [start]字段有效值
//								// 默认选项
//								for (ValidValue oValidValue : fmodel.getMyValidValues())
//								{
//									Boolean has = false;// 是否以及存在此可选值
//									for (int i = 0; i < oUserFieldsMD.getValidValues().getCount(); i++)
//									{
//										oUserFieldsMD.getValidValues().setCurrentLine(i);
//										if (oUserFieldsMD.getValidValues().getValue().equals(oValidValue.getValue()))
//										{
//											oUserFieldsMD.getValidValues().setDescription(oValidValue.getDescription());
//											has = true;
//											break;
//										}
//									}
//									if (has)
//									{
//										oUserFieldsMD.getValidValues().setValue(oValidValue.getValue());
//										oUserFieldsMD.getValidValues().setDescription(oValidValue.getDescription());
//										oUserFieldsMD.getValidValues().add();
//									}
//								}
//								
//								// [end]
//								Result = oUserFieldsMD.update();
//								if (Result != 0)
//								{
//									Errors.add(sap.company.getLastErrorDescription() + "\r\nTable:" + fmodel.getTableName() + "更新字段:" + fmodel.getName() + "出错！");
//								}
//								sap.disconnect();
//							}
//							else
//							{
//								// 添加
//								oUserFieldsMD.setTableName(fmodel.getTableName());// 表名
//								oUserFieldsMD.setName(fmodel.getName()); // 字段
//								oUserFieldsMD.setDescription(fmodel.getDescription());// 字段描述
//								oUserFieldsMD.setType(fmodel.getType());// 数据类型
//								if (CanSubType(fmodel))
//								{
//									oUserFieldsMD.setSubType(fmodel.getSubType());// 结构
//								}
//								if (CanSubType(fmodel))
//								{
//									oUserFieldsMD.setEditSize(fmodel.getEditSize());// 长度
//								}
//								oUserFieldsMD.setDefaultValue(fmodel.getDefaultValue());// 默认值
//								oUserFieldsMD.setLinkedTable(fmodel.getLinkedTable());// 连接表
//								oUserFieldsMD.setMandatory(fmodel.getMandatory()); // 强制的
//								// [start]字段有效值
//								// 默认选项
//								for (ValidValue oValidValue : fmodel.getMyValidValues())
//								{
//									oUserFieldsMD.getValidValues().setValue(oValidValue.getValue());
//									oUserFieldsMD.getValidValues().setDescription(oValidValue.getDescription());
//									oUserFieldsMD.getValidValues().add();
//								}
//								// [end]
//								Result = oUserFieldsMD.add();
//								String ssString= oUserFieldsMD.getAsXML();
//								if (Result != 0)
//								{
//									Errors.add(ssString);
//									Errors.add(sap.company.getLastErrorDescription() + "\r\nTable:" + fmodel.getTableName() + "添加字段:" + fmodel.getName() + "出错！");
//								}
//								sap.disconnect();
//							}
//							oUserFieldsMD = null;
//						}
//						catch (Exception ex)
//						{
//							Errors.add(ex.getMessage());
//							if (oUserFieldsMD != null)
//							{
//								oUserFieldsMD = null;
//							}
//							sap.disconnect();
//						}
//					}
//					catch (Exception ex)
//					{
//						Errors.add(ex.getMessage());
//						sap.disconnect();
//					}
//				}
//				// [end]
//			}
//			if (EmptyUtil.isNotEmpty(listObjects))
//			{
//				
//				// [start] 开始创建自定义对象
//				for (SapBusinessObjectsModel bmodel : listObjects)
//				{
//					MySapConnect sap = new MySapConnect(connconfig);
//					int conn = sap.connect();
//					if (conn != 0)
//					{
//						Errors.add("连接状态为" + conn + ",值-1=失败;值-2=未初始化");
//						return;
//					}
//					oUserObjectsMD = new UserObjectsMD(sap.company.getBusinessObject(SBOCOMConstants.BoObjectTypes_oUserObjectsMD));
//					// oUserObjectsMD = (UserObjectsMD) sap.company.getBusinessObject(SBOCOMConstants.BoObjectTypes_oUserObjectsMD);// 实例化
//					if (oUserObjectsMD.getByKey(bmodel.getCode()))
//					{
//						System.out.println("当前对象TableName="+bmodel.getTableName());
//						// 更新
//						oUserObjectsMD.setName(bmodel.getName());// 对象描述
//						oUserObjectsMD.setCanDelete(bmodel.getCanDelete());// 删除
//						oUserObjectsMD.setCanClose(bmodel.getCanClose());// 关闭
//						oUserObjectsMD.setCanCancel(bmodel.getCanCancel());// 取消
//						oUserObjectsMD.setCanYearTransfer(bmodel.getCanYearTransfer());// 年结
//						oUserObjectsMD.setManageSeries(bmodel.getManageSeries());// 管理服务
//						// 判断日志是否存在
//						if (oUserObjectsMD.getCanLog().equals(SBOCOMConstants.BoYesNoEnum_tYES))
//						{
//							oUserObjectsMD.setCanLog(SBOCOMConstants.BoYesNoEnum_tNO);
//						}
//						if (EmptyUtil.isNotEmpty(bmodel.getTableName()))// 日志表
//						{
//							oUserObjectsMD.setCanLog(SBOCOMConstants.BoYesNoEnum_tYES);
//							oUserObjectsMD.setLogTableName(bmodel.getLogTableName());
//						}
//						// 子表
//						for (ChildTable oChildTable : bmodel.getChildTables())
//						{
//							if (GetObjectChildTableLog(bmodel.getCode(), oChildTable.getTableName(), oChildTable.getLogTableName()) > 0)
//								continue;
//							oUserObjectsMD.getChildTables().setTableName(oChildTable.getTableName());
//							oUserObjectsMD.getChildTables().setLogTableName(oChildTable.getLogTableName());
//							oUserObjectsMD.getChildTables().add();
//						}
//						
//						Result = oUserObjectsMD.update();
//						if (Result != 0)
//						{
//							Errors.add("更新结果：" + Result + "；对象名：" + bmodel.getCode() + "错误代码:" + sap.company.getLastErrorCode() + ",错误信息：" + sap.company.getLastErrorDescription());
//						}
//						sap.disconnect();
//					}
//					else
//					{
//						// 创建
//						oUserObjectsMD.setCode(bmodel.getCode());
//						oUserObjectsMD.setTableName(bmodel.getTableName());
//						oUserObjectsMD.setObjectType(bmodel.getObjectType());
//						oUserObjectsMD.setName(bmodel.getName());// 对象描述
//						oUserObjectsMD.setCanDelete(bmodel.getCanDelete());// 删除
//						oUserObjectsMD.setCanClose(bmodel.getCanClose());// 关闭
//						oUserObjectsMD.setCanCancel(bmodel.getCanCancel());// 取消
//						oUserObjectsMD.setCanYearTransfer(bmodel.getCanYearTransfer());// 年结
//						oUserObjectsMD.setManageSeries(bmodel.getManageSeries());// 管理服务
//						if (EmptyUtil.isNotEmpty(bmodel.getLogTableName()))// 日志表
//						{
//							oUserObjectsMD.setCanLog(SBOCOMConstants.BoYesNoEnum_tYES);
//							oUserObjectsMD.setLogTableName(bmodel.getLogTableName());
//						}
//						// 查找字段
//						for (FindColumn oFindColumn : bmodel.getFindColumns())
//						{
//							oUserObjectsMD.getFindColumns().setColumnAlias(oFindColumn.getColumnAlias());
//							oUserObjectsMD.getFindColumns().setColumnDescription(oFindColumn.getColumnDescription());
//							oUserObjectsMD.getFindColumns().add();
//						}
//						// 默认窗体
//						for (FormColumn oFormColumn : bmodel.getFormColumns())
//						{
//							oUserObjectsMD.getFormColumns().setFormColumnAlias(oFormColumn.getFormColumnAlias());
//							oUserObjectsMD.getFormColumns().setFormColumnDescription(oFormColumn.getFormColumnDescription());
//							oUserObjectsMD.getFormColumns().add();
//						}
//						// 子表
//						for (ChildTable oChildTable : bmodel.getChildTables())
//						{
//							oUserObjectsMD.getChildTables().setTableName(oChildTable.getTableName());
//							oUserObjectsMD.getChildTables().setLogTableName(oChildTable.getLogTableName());
//							oUserObjectsMD.getChildTables().add();
//						}
//						Result = oUserObjectsMD.add();
//						if (Result != 0)
//						{
//							Errors.add(oUserObjectsMD.getAsXML());
//							oUserObjectsMD = null;
//							Errors.add("添加结果：" + Result + "；对象名:" + bmodel.getCode() + "错误代码:" + sap.company.getLastErrorCode() + "错误描述:" + sap.company.getLastErrorDescription());
//						}
//						sap.disconnect();
//					}
//					oUserObjectsMD = null;
//				}
//				// [end]
//			}
		}
	}
	
	/**
	 * 获取字段ID
	 * 
	 * @param sap
	 * @param TableName 表名
	 * @param FieldName 字段名
	 * @return 字段ID
	 */
	private int GetFieldID( String TableName, String FieldName)
	{
		Recordset ors;
		String SQL;
		int Result;
		MySapConnect sapconnect = new MySapConnect(connconfig);
		try{
			
			int conn = sapconnect.connect();
			if (conn != 0)
			{
				Errors.add("连接状态为" + conn + ",值-1=失败;值-2=未初始化");
				Result= -1;
			}
			// ors = (Recordset) sap.company.getBusinessObject(SBOCOMConstants.BoObjectTypes_BoRecordset);
			ors = new Recordset(sapconnect.company.getBusinessObject(SBOCOMConstants.BoObjectTypes_BoRecordset));
			List<String> params = new ArrayList<String>();
			params.add(TableName);
			params.add(FieldName);
			if (sapconnect.company.getDbServerType().equals(SBOCOMConstants.BoDataServerTypes_dst_HANADB))
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
		}
		catch(Exception ex)		{
			Errors.add(ex.getMessage());
			Result = -1;
		}
		finally{
			ors = null;
			sapconnect.disconnect();
			Result = -1;
		}
		
		return Result;
	}
	
	/**
	 * 获取对象子表日志是否存在
	 * 
	 * @param ObjectName
	 * @param TableName
	 * @param TableNameLog
	 * @return
	 */
	private int GetObjectChildTableLog(String ObjectName, String TableName, String TableNameLog)
	{
		Recordset ors;
		String SQL;
		int Result;
		MySapConnect sap = new MySapConnect(connconfig);
		ors = (Recordset) sap.company.getBusinessObject(SBOCOMConstants.BoObjectTypes_BoRecordset);
		List<String> params = new ArrayList<String>();
		params.add(ObjectName);
		params.add(TableName);
		params.add(TableNameLog);
		SQL = "SELECT COUNT(0) FROM \"UDO1\" WHERE \"Code\" = '" + ObjectName + "' AND \"TableName\" = '" + TableName + "' AND \"LogName\" = '" + TableNameLog + "'";
		ors.doQuery(SQL);
		if (ors.getRecordCount() > 0)
		{
			Result = Integer.parseInt(ors.getFields().item(0).getValue().toString());
		}
		else
		{
			Result = -1;
		}
		ors = null;
		return Result;
	}
	
	/**
	 * 检查是否展示字段长度
	 * 
	 * @param sapfields
	 * @return
	 */
	private Boolean CanEditSize(SapFieldsModel sapfields)
	{
		Boolean result = false;
		Integer type = sapfields.getType();
		Integer subtype = sapfields.getSubType();
		if (type.equals(SBOCOMConstants.BoFieldTypes_db_Memo) && subtype.equals(SBOCOMConstants.BoFldSubTypes_st_None))
		{
			result = true;
		}
		if (type.equals(SBOCOMConstants.BoFieldTypes_db_Numeric))
		{
			result = true;
		}
		return result;
	}
	
	/**
	 * 检查是否展示结构
	 * 
	 * @param sapfields
	 * @return
	 */
	private Boolean CanSubType(SapFieldsModel sapfields)
	{
		Boolean result = true;
		Integer type = sapfields.getType();
		if (type.equals(SBOCOMConstants.BoFieldTypes_db_Numeric))
		{
			result = false;
		}
		return result;
	}
	
	public List<String> getErrors()
	{
		return Errors;
	}
}
