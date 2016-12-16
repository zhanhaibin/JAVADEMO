
import java.io.IOException;

import cn.ava.publics.util.XmlUtil;
import cn.ava.saputil.CreateUserTables;
import cn.ava.saputil.GetUserTables;
import cn.ava.saputil.MyCustomExcelToXml;
import cn.ava.saputil.MySapConnectConfig;
import cn.ava.saputil.SapLog;

public class GetXml
{
	
	public static void main(String args[]) throws IOException
	{
		MySapConnectConfig sapconnconfig = new MySapConnectConfig();
		sapconnconfig.set_b1CompanyDBName("SBODEMOCN");
		sapconnconfig.set_b1DbType(8);
		sapconnconfig.set_b1Language(15);
		sapconnconfig.set_b1LicenseServer("ZHANHB:30000");
		sapconnconfig.set_b1PassWord("avatech");
		sapconnconfig.set_b1ServerName("ZHANHB");
		sapconnconfig.set_b1UserName("manager");
		sapconnconfig.set_dbPassword("avatech");
		sapconnconfig.set_dbUserName("sa");
		sapconnconfig.set_isUseTrusted(false);
		
		GetUserTables gettable = new GetUserTables(sapconnconfig);
		gettable.createSapUserFields();
		
		int i = 1;
		for (String strErr : gettable.getErrors())
		{
			SapLog.WriteLog("错误提示[" + i + "]:" + strErr, null);
			i++;
		}
		
	}
	
}
