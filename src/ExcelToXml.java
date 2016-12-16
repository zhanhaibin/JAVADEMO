
import java.io.IOException;

import cn.ava.publics.util.XmlUtil;
import cn.ava.saputil.CreateUserTables;
import cn.ava.saputil.MyCustomExcelToXml;
import cn.ava.saputil.MySapConnectConfig;
import cn.ava.saputil.SapLog;

public class ExcelToXml
{
	
	public static void main(String args[]) throws IOException
	{
		// 读取excel文件
		String filePath = "D:/1/1.xlsx";
		MyCustomExcelToXml customtoxml = new MyCustomExcelToXml("D:/1/1.xml", filePath);
		if (customtoxml.CreateXML())
		{
			System.out.println(XmlUtil.ObjectToString(customtoxml.getCustemObject()));
		}
		else
		{
			System.out.println(customtoxml.getErrMessage());
		}
		
//		customtoxml.GetMappingXML();
		
//				dst_MSSQL = 1,
//		        dst_DB_2 = 2,
//		        dst_SYBASE = 3,
//		        dst_MSSQL2005 = 4,
//		        dst_MAXDB = 5,
//		        dst_MSSQL2008 = 6,
//		        dst_MSSQL2012 = 7,
//		        dst_MSSQL2014 = 8,
//		        dst_HANADB = 9
//		MySapConnectConfig sapconnconfig = new MySapConnectConfig();
//		sapconnconfig.set_b1CompanyDBName("SBODEMOCN");
//		sapconnconfig.set_b1DbType(8);
//		sapconnconfig.set_b1Language(15);
//		sapconnconfig.set_b1LicenseServer("ZHANHB:30000");
//		sapconnconfig.set_b1PassWord("avatech");
//		sapconnconfig.set_b1ServerName("ZHANHB");
//		sapconnconfig.set_b1UserName("manager");
//		sapconnconfig.set_dbPassword("avatech");
//		sapconnconfig.set_dbUserName("sa");
//		sapconnconfig.set_isUseTrusted(false);
//		
//		CreateUserTables createtable = new CreateUserTables(sapconnconfig);
//		createtable.SapCreateUserTables(customtoxml.getCustemObject());
//		
//		int i = 1;
//		for (String strErr : createtable.getErrors())
//		{
//			SapLog.WriteLog("错误提示[" + i + "]:" + strErr, null);
//			i++;
//		}
		
	}
	
}
