package cn.ava.saputil.ExcelMapping;

public interface ITable {
	   String getName();
	   void SetName(String value);
	    
	   String getDescription();
	   void SetDescription(String value);
	 
	   String getForeignDescription();
	   void setForeignDescription(String value);
	   
	   String getPropertyName();
	   void setPropertyName(String value); 
	   
       Enumerations.TableType getType();
       void setType(Enumerations.TableType value);
       
       Enumerations.TableState getState();
       void setState(Enumerations.TableState value);
       
       IFields getFields();
        
       void CheckValues();
       
       String getBOCode();
       void setBOCode(String value);
}
