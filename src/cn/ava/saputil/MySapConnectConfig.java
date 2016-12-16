package cn.ava.saputil;

import cn.ava.publics.util.JsonUtil;

import com.sap.smb.sbo.api.SBOCOMConstants;

/**
 * Sap B1的链接配置
 * 
 * @author Rex
 * @version 1.0
 *
 */
public class MySapConnectConfig
{
    /* 设置本连接的需要的属性，初始化的时候赋值 */
    // B1服务器地址，IP或计算机名
    private String _b1ServerName;
    // B1公司名称
    private String _b1CompanyDBName;
    // B1用户登录名
    private String _b1UserName;
    // B1用户登录密码
    private String _b1PassWord;
    // 数据库类型【需要使用SBOCOMConstants 下的数据类型】
    private Integer _b1DbType;
    // 是否使用可信
    private Boolean _isUseTrusted = false;
    // B1语言【需要使用SBOCOMConstants 下的数据类型】
    private Integer _b1Language = SBOCOMConstants.BoSuppLangs_ln_English;
    // /数据库登录名
    private String _dbUserName;
    // 数据库登录密码
    private String _dbPassword;
    // 许可证服务器地址
    private String _b1LicenseServer;
    
    /**
     * 获取B1服务器地址【IP或计算机名】
     * 
     * @return B1服务器地址【IP或计算机名】
     */
    public String get_b1ServerName()
    {
        return _b1ServerName;
    }
    
    /**
     * 设置B1服务器地址【IP或计算机名】
     * 
     * @param _b1ServerName
     *            B1服务器地址【IP或计算机名】
     */
    public void set_b1ServerName(String _b1ServerName)
    {
        this._b1ServerName = _b1ServerName;
    }
    
    /**
     * 获取B1公司的数据库名称
     * 
     * @return B1公司的数据库名称
     */
    public String get_b1CompanyDBName()
    {
        return _b1CompanyDBName;
    }
    
    /**
     * 设置B1公司的数据库名称
     * 
     * @param _b1CompanyDBName
     *            B1公司的数据库名称
     */
    public void set_b1CompanyDBName(String _b1CompanyDBName)
    {
        this._b1CompanyDBName = _b1CompanyDBName;
    }
    
    /**
     * 获取B1的登录名
     * 
     * @return B1的登录名
     */
    public String get_b1UserName()
    {
        return _b1UserName;
    }
    
    /**
     * 设置B1的登录名
     * 
     * @param _b1UserName
     *            B1的登录名
     */
    public void set_b1UserName(String _b1UserName)
    {
        this._b1UserName = _b1UserName;
    }
    
    /**
     * 获取B1的登录密码
     * 
     * @return B1的登录密码
     */
    public String get_b1PassWord()
    {
        return _b1PassWord;
    }
    
    /**
     * 设置B1的登录密码
     * 
     * @param _b1PassWord
     *            B1的登录密码
     */
    public void set_b1PassWord(String _b1PassWord)
    {
        this._b1PassWord = _b1PassWord;
    }
    
    /**
     * 获取B1数据库的类型
     * 
     * @return B1数据库类型
     */
    public Integer get_b1DbType()
    {
        return _b1DbType;
    }
    
    /**
     * 设置B1数据库类型[需要使用(SBOCOMConstants)下的对象获取]
     * 
     * @param _b1DbType
     *            B1数据库类型
     */
    public void set_b1DbType(Integer _b1DbType)
    {
        this._b1DbType = _b1DbType;
    }
    
    /**
     * 获取B1的是否使用信任许可
     * 
     * @return B1是否使用信任许可
     */
    public Boolean get_isUseTrusted()
    {
        return _isUseTrusted;
    }
    
    /**
     * 设置B1是否使用信任许可
     * 
     * @param _isUseTrusted
     *            B1是否使用信任许可
     */
    public void set_isUseTrusted(Boolean _isUseTrusted)
    {
        this._isUseTrusted = _isUseTrusted;
    }
    
    /**
     * 获取B1的语言类型
     * 
     * @return B1的语言类型
     */
    public Integer get_b1Language()
    {
        return _b1Language;
    }
    
    /**
     * 设置B1的语言类型[需要使用(SBOCOMConstants)下的对象获取]
     * 
     * @param _b1Language
     *            设置B1的语言类型
     */
    public void set_b1Language(Integer _b1Language)
    {
        this._b1Language = _b1Language;
    }
    
    /**
     * 获取数据库登录名
     * 
     * @return 数据库登录名
     */
    public String get_dbUserName()
    {
        return _dbUserName;
    }
    
    /**
     * 设置数据库登录名
     * 
     * @param _dbUserName
     *            数据库登录名
     */
    public void set_dbUserName(String _dbUserName)
    {
        this._dbUserName = _dbUserName;
    }
    
    /**
     * 获取数据库登录密码
     * 
     * @return 数据库登录密码
     */
    public String get_dbPassword()
    {
        return _dbPassword;
    }
    
    /**
     * 设置数据库登录密码
     * 
     * @param _dbPassword
     *            数据库登录密码
     */
    public void set_dbPassword(String _dbPassword)
    {
        this._dbPassword = _dbPassword;
    }
    
    /**
     * 获取B1的Licence服务器地址
     * 
     * @return B1的Licence服务器地址
     */
    public String get_b1LicenseServer()
    {
        return _b1LicenseServer;
    }
    
    /**
     * 设置B1的Licence服务器地址
     * 
     * @param _b1LicenseServer
     *            B1的Licence服务器地址
     */
    public void set_b1LicenseServer(String _b1LicenseServer)
    {
        this._b1LicenseServer = _b1LicenseServer;
    }
    
    @Override
    public String toString()
    {
        return JsonUtil.getJsonArrStrFromList(this);
        // return "SapConnectConfig {_b1ServerName=" + _b1ServerName +
        // ", _b1CompanyDBName=" + _b1CompanyDBName + ", _b1UserName=" +
        // _b1UserName + ", _b1PassWord=" + _b1PassWord + ", _b1DbType=" +
        // _b1DbType + ", _isUseTrusted=" + _isUseTrusted + ", _b1Language=" +
        // _b1Language + ", _dbUserName=" + _dbUserName + ", _dbPassword=" +
        // _dbPassword + ", _b1LicenseServer=" + _b1LicenseServer + "}";
    }
}
