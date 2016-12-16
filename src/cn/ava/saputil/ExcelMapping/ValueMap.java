package cn.ava.saputil.ExcelMapping;

/// <summary>
/// excel值 映射关系
/// </summary>
public class ValueMap {
	
	private String _Source ;
    public String Source(){
    	return _Source; 
    }
    public void SetSource(String Source){
		this._Source = Source;
	}
    
    private String _Target ;
    public String Target(){
    	return _Target;  
    }
    public  void SetTarget(String Target) {
		this._Target = Target;
	}
}
