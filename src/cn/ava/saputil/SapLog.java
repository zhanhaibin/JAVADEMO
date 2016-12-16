package cn.ava.saputil;

import cn.ava.publics.util.*;

/**
 * 自定义工具类
 * 
 * @author Rex
 *
 */
public class SapLog
{
    /**
     * 写日志
     * 
     * @param LogMassage
     *            日志内容
     * @param connconfig
     *            连接信息【由于提供连接不一定是正确的，所以可能不到公司名】
     */
    public static void WriteLog(String LogMassage, MySapConnectConfig connconfig)
    {
        String filepath = "";
        try
        {
            filepath = SapLog.class.getClassLoader().getResource("/").getPath() + "/log/" + FileWriteAndReadUtil.getDateString() + "_SAP.log";
        }
        catch (Exception ex)
        {
            filepath = JarToolUtil.getJarDir() + "\\log\\" + FileWriteAndReadUtil.getDateString() + "_SAP.log";
        }
        FileWriteAndReadUtil.writeFile(filepath, FileWriteAndReadUtil.getDateTimeString() + ":" + LogMassage, true);
        if (connconfig != null)
        {
            FileWriteAndReadUtil.writeFile(filepath, "\r\n[" + connconfig.toString() + "]\r\n", true);
        }
        System.out.println(FileWriteAndReadUtil.getDateTimeString() + ":" + LogMassage);
    }
}
