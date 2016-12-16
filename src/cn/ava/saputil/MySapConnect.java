package cn.ava.saputil;

import cn.ava.publics.util.JsonUtil;

import com.sap.smb.sbo.api.ICompany;
import com.sap.smb.sbo.api.ICompanyService;
import com.sap.smb.sbo.api.SBOCOMUtil;
import com.sap.smb.sbo.api.SBOErrorMessage;

/**
 * SAP连接类，能够实现B1的连接与断开
 * 
 * @author Rex
 * @version 1.0
 */
public class MySapConnect
{
    public ICompany company;
    public MySapConnectConfig sapconnectconfig;
    public ICompanyService companservice;
    
    /**
     * 默认初始化连接
     * 
     * @author Rex
     */
    public MySapConnect()
    {
        sapconnectconfig = null;
    }
    
    /**
     * 初始化SAP 连接，直接使用对象
     * 
     * @param sapconnconfig
     */
    public MySapConnect(MySapConnectConfig sapconnconfig)
    {
        sapconnectconfig = sapconnconfig;
    }
    
    /**
     * 初始化SAP连接，通过Json字符串进行初始化(Json字符串可以通过 SapConnectConfig的toString()方法获得)
     * 
     * @param fromjson
     */
    public MySapConnect(String fromjson)
    {
        MySapConnectConfig sapconnconfig = new MySapConnectConfig();
        sapconnconfig = (MySapConnectConfig) JsonUtil.getObjFormJsonArrStr(fromjson, MySapConnectConfig.class);
        sapconnectconfig = sapconnconfig;
    }
    
    /**
     * 连接公司信息
     * 
     * @author Rex
     * @return 成功返回 0, 失败返回-1，未初始化返回-2
     */
    public int connect()
    {
        int connectionResult = 0;
        if (sapconnectconfig == null)
        {
            SapLog.WriteLog("未初始化配置信息，不能链接。", null);
            return -2;
        }
        try
        {
            if (company == null)
            {
                // 公司实例初始化
                company = SBOCOMUtil.newCompany();
                // 设置数据库服务器主机
                company.setServer(sapconnectconfig.get_b1ServerName());
                // 设置公司数据库
                company.setCompanyDB(sapconnectconfig.get_b1CompanyDBName());
                // 设置SAP用户名
                company.setUserName(sapconnectconfig.get_b1UserName());
                // 设置SAP用户密码
                company.setPassword(sapconnectconfig.get_b1PassWord());
                // 设置数据库版本
                company.setDbServerType(sapconnectconfig.get_b1DbType());
                // 设置是否使用可信连接到SQL服务器
                company.setUseTrusted(sapconnectconfig.get_isUseTrusted());
                // 设置SAP B1 的语言
                company.setLanguage(sapconnectconfig.get_b1Language());
                // 设置数据库登录名
                company.setDbUserName(sapconnectconfig.get_dbUserName());
                // 设置数据库登录密码
                company.setDbPassword(sapconnectconfig.get_dbPassword());
                // 设置license服务器和端口号
                company.setLicenseServer(sapconnectconfig.get_b1LicenseServer());
                // 初始化连接
                connectionResult = company.connect();
                // 如果连接成功
                if (connectionResult == 0)
                {
                    companservice = company.getCompanyService();
                    SapLog.WriteLog("成功连接到公司：" + company.getCompanyName(), null);
                }
                // 如果连接失败
                else
                {
                    // 从SAP B1 得到一个错误消息
                    SBOErrorMessage errMsg = company.getLastError();
                    SapLog.WriteLog("没有连接到服务，错误信息： " + errMsg.getErrorMessage() + "，错误号：" + errMsg.getErrorCode(), null);
                }
            }
        }
        catch (Exception e)
        {
            SapLog.WriteLog("连接SAP发生了异常：" + e.getMessage() + "||" + e.getLocalizedMessage(), sapconnectconfig);
            return -1;
        }
        return connectionResult;
    }
    
    /**
     * 关闭公司的连接
     * 
     * @author Rex
     */
    public void disconnect()
    {
        try
        {
            company.disconnect();
            SapLog.WriteLog("关闭公司:" + company.getCompanyName(), null);
        }
        catch (Exception ex)
        {
            SapLog.WriteLog("关闭连接发送异常:" + ex.getMessage(), null);
        }
    }
}
