package cn.ava.saputil.ExcelMapping;

/// <summary>
/// 区域判断的条件
/// </summary>
public class Condition {
	private String _Column;
    public String Column()    {
         return _Column;  
    }
    public void SetColumn(String Column) {
		this._Column = Column;
	}

    private enumOperate _Operate;
    public enumOperate Operate()    {
       return _Operate; 
    }
    public void SetOperate(enumOperate Operate) {
		this._Operate = Operate;
	}

    private String _Value;
    public String Value()    {
        return _Value; 
    }
    public  void SetValue(String Value) {
		this._Value = Value;
	}
   
}
