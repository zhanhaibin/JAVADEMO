package cn.ava.saputil.ExcelMapping;

public class Enumerations {
	  public enum FieldDataType
      {
		  db_Alpha ,       //Sets field to alphanumeric. 
		  db_Memo ,        //Sets field to memo. 
		  db_Numeric ,     //Sets field to numeric. 
		 db_Date,        //Sets field to date. 
		  db_Float ,        //Sets field field to floating point. 
		  db_Bytes;
      }

      public enum FieldSubType
      {
        
          st_None  ,            //No special sub-type. 
          
          st_Address ,        // Address format. 
       
          st_Phone  ,          //Phone format.  
           
          st_Time  ,           //Time format. 
           
          st_Rate  ,           //Double format with the system's rate accuracy. 
          
          st_Sum  ,            //Double format with the system's summery accuracy. 
           
          st_Price ,          //Double format with the system's price accuracy. 
      
          st_Quantity  ,       //Double format with the system's quantity accuracy.  
          
          st_Percentage ,     //Double format with the system's percentage accuracy. 
         
          st_Measurement ,    //Double format with the system's measurement accuracy. 
         
          st_Link ,           //Link format (mostly used for a web site links). 
         
          st_Image  ,           //Image format. 
        
          st_Email ,           //Email url.
         
          st_Website ;           //web site url.
      }

      public enum TableType
      {
          
          bott_NoObject,           //(Default) A No Object type refers to a user table that cannot be linked to a user defined object. The default table includes Code and Name fields only. 
         
          bott_MasterData,         //A Master Data type table refers to a collection of information about a person or an object, such as a cost object, business partner, or G/L account. For example, a business partner master record contains not only general information such as the business partner's name and address, but also specific information, such as payment terms and delivery instructions. Generally for end-users, master data is reference data that you will look up and use, but not create or change.
        
          bott_MasterDataLines ,   //A Master Data Lines type refers as a child of Master Data type. For example, list of addresses related to a business partner. 
         
          bott_Document,          //A Document type table refers to transactional data, which is data related to a single business event such as a purchase requisition or a request for payment. When you create a requisition, for example, SAP creates an electronic document for that particular transaction. SAP gives the transaction a document number and adds the document to the transaction data that is already in the system. Whenever you complete a transaction in SAP, that is, when you create, change, or print a document in SAP, this document number appears at the bottom of the screen. 
          
          bott_DocumentLines,   //A Document Lines type refers as a child of Document type. For example, Content tab in Invoice document. 
      
          bott_SimpleObject,
          
          bott_SimpleObjectLines ,
          
          bott_SimpleBusinessObject ,
        
          bott_SimpleBusinessObjectLines ;
      }

      public enum TableState
      {
          bots_User ,              //User define table 
          bots_System ,         //System table
          bots_BoModelOnly; //仅为对象模型
      }

      public enum ObjectType
      {
        
          boud_MasterData ,        //A Master Data type object 
          
          boud_Document,         //A Document type object 
         
          boud_SimpleObject,
         
          boud_SimpleBusinessObject;
      }

      public enum YesNoEnum
      {
         
          No ,                    //Sets the answer to negative
         
          Yes ;                   //Sets the answer to positive
      }
      public enum ObjectAreaType
      {
          NotDefind, TableArea, FieldTitleArea, FieldArea, ValidValueArea, BusinessObjectArea, BusinessObjectChildTableArea;
      }
}
