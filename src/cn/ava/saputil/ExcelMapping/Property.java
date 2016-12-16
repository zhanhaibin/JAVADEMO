package cn.ava.saputil.ExcelMapping;

/// <summary>
/// 对象属性 与单元格的映射
/// </summary>
public class Property {
	private String _Name ;
    public String Name(){
      return _Name;  
    }
    public void SetName(String Name) {
		this._Name = Name;
	}
    private String _Column ;
    public String Column(){
       return _Column;  
    }
    public void SetColumn(String Column) {
		this._Column = Column;
	}
    private int _Offset = 0;
    public int Offset(){
         return _Offset;  
    }
    public  void SetOffset(int Offset) {
		this._Offset = Offset;
	}
}
