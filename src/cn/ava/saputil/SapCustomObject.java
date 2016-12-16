package cn.ava.saputil;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import cn.ava.publics.util.EmptyUtil;
import cn.ava.publics.util.XmlUtil;
@XmlRootElement(name="DomainModel")
public class SapCustomObject
{
	public SapCustomObject()
	{}
	/**
	 * 初始化
	 * @param xmlFile 文件路径
	 */
	public SapCustomObject(String xmlFile)
	{
		SapCustomObject sobj=XmlUtil.FileToObject(SapCustomObject.class,xmlFile);
		tablemodels=sobj.getTablemodels();
		objectmodels=sobj.getObjectmodels();
		fields=sobj.getFields();
	}
	/**
	 * 初始化
	 * @param listtable 自定义表
	 * @param listbusinessObj 自定义对象
	 * @param listfield 自定义字段
	 */
	public SapCustomObject(List<SapTableModel> listtable,List<SapBusinessObjectsModel> listbusinessObj,List<SapFieldsModel> listfield)
	{
		tablemodels=listtable;
		objectmodels=listbusinessObj;
		fields=listfield;
	}
	private List<SapTableModel> tablemodels;
	private List<SapBusinessObjectsModel> objectmodels;
	private List<SapFieldsModel> fields;
	@XmlElement(name="Table")
	public List<SapTableModel> getTablemodels()
	{
		if(EmptyUtil.isEmpty(tablemodels))
		{
			tablemodels=new ArrayList<SapTableModel>();
		}
		return tablemodels;
	}
	public void setTablemodels(List<SapTableModel> tablemodels)
	{
		this.tablemodels = tablemodels;
	}
	@XmlElement(name="BusinessObjects")
	public List<SapBusinessObjectsModel> getObjectmodels()
	{
		if(EmptyUtil.isEmpty(objectmodels))
		{
			objectmodels=new ArrayList<SapBusinessObjectsModel>();
		}
		return objectmodels;
	}
	public void setObjectmodels(List<SapBusinessObjectsModel> objectmodels)
	{
		this.objectmodels = objectmodels;
	}
	@XmlElement(name="Fields")
	public List<SapFieldsModel> getFields()
	{
		if(EmptyUtil.isEmpty(fields))
		{
			fields=new ArrayList<SapFieldsModel>();
		}
		return fields;
	}
	public void setFields(List<SapFieldsModel> fields)
	{
		this.fields = fields;
	}
	
	
}
