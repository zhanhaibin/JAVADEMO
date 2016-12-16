package cn.ava.saputil.ExcelMapping;

import java.util.ArrayList;

import org.apache.poi.xssf.usermodel.XSSFRow;
import cn.ava.publics.util.EmptyUtil;
import cn.ava.publics.util.EqualsUtil;

/// <summary>
/// excel解析 配置脚本的 区域信息
/// </summary>
public class ObjectArea {
    
	private String _Name;
    public String name() {
    	return _Name;	
	}
    public  void SetName(String Name) {
		this._Name = Name;
	}
    
    private int _RowCount = 1;
    /// <summary>
    /// 区域的行数
    /// </summary>
    public int RowCount() {
         return _RowCount; 
    }
    public  void SetRowCount(int RowCount) {
		this._RowCount = RowCount;
	}

    private Conditions _Conditions = new Conditions();
    /// <summary>
    /// 区域判断条件
    /// </summary>
    public Conditions Conditions() {
       return _Conditions; 
    }
    public  void SetConditions(Conditions Conditions) {
    	this._Conditions = Conditions;
	}

    private Propertys _Propertys = new Propertys();
    /// <summary>
    /// 对象属性映射关系
    /// </summary>
    public Propertys Propertys() {
          return _Propertys;  
    }
    public  void SetPropertys(Propertys Propertys) {
		this._Propertys = Propertys;
	}

    private ValueMaps _ValueMaps = new ValueMaps();
    /// <summary>
    /// 区域内值映射关系
    /// </summary>
    public ValueMaps ValueMaps()    {
         return _ValueMaps;  
    }
    public void SetValueMaps(ValueMaps ValueMaps) {
		this._ValueMaps = ValueMaps;
	}
    /// <summary>
    /// 判断行是否为当前区域
    /// </summary>
    /// <param name="row"></param>
    /// <returns></returns>
    public Boolean CheckAreaType(XSSFRow row)
    {
        try
        {
        	Boolean isHas = false;
        	for (Condition condition : _Conditions) {
        		switch (condition.Operate())
                {
                    case Equal:
                        if (EqualsUtil.StringEquals(String.valueOf(row.getCell(Integer.parseInt(condition.Column()))).trim(), condition.Value(),false))
                        {
                            isHas = true;
                        }
                        else
                        {
                            isHas = false;
                        }
                        break;
                    case NotEqual:
                        if (!EqualsUtil.StringEquals(String.valueOf(row.getCell(Integer.parseInt(condition.Column()))).trim(), condition.Value(),false))
                        {
                            isHas = true;
                        }
                        else
                        {
                            isHas = false;
                        }
                        break;
                    default:
                        break;
                }
                if (isHas == false)
                {
                    return isHas;
                }
            }
            return isHas;
        }
        catch (Exception ex)
        {
            return false;
        }
    }

}
