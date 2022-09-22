package club.fuwenhao.constant;

public class CommonConstant {

    private CommonConstant() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 新增标识
     * add
     */
    public static final String ADD_TYPE = "add";

    /**
     * 删除标识
     */
    public static final String DELETE_TYPE = "delete";

    /**
     * 修改标识
     */
    public static final String UPDATE_TYPE = "update";

    /**
     * 新增标识
     * add
     */
    public static final int ADD_TYPE_CLIENT = 1;

    /**
     * 删除标识
     */
    public static final int DELETE_TYPE_CLIENT = 0;

    /**
     * 修改标识
     */
    public static final int UPDATE_TYPE_CLIENT = 2;

    /**
     * JSON处理日期返回格式 ""
     */
    public static final String JSON_DATE_RETURN_TYPE_NULL = "";
    /**
     * JSON处理日期返回格式 -1
     */
    public static final String JSON_DATE_RETURN_TYPE_1 = "-1";
    /**
     * 返回码  成功
     */
    public static final int OK = 20000;  //成功
    /**
     * 返回码  失败
     */
    public static final int ERROR = 20001; //失败
    /**
     * 返回码  未登录
     */
    public static final int NO_LOGIN = 20002; // 未登录 20002
    /**
     * 返回码  无权限
     */
    public static final int NO_AUTH = 20003; // 无权限 20003
    /**
     * sso认证失败。
     */
    public static final int SSO_NO_AUTH = 20004;
    /**
     * 账户密码失效标识
     */
    public static final int FAILURE_ACCOUNT_AUTH = 20006;

    /**
     * 账户密码有效期标识
     */
    public final static String EFFECTIVE_ACCOUNT = "effective_account_";

    /**
     * 零
     */
    public static final String ZERO = "0";
    /**
     * http正确响应code
     */
    public static final String HTTP_SUCCESS = "200";

    /**
     * 注册标识-redisKey
     */
    public static final String REGISTER_FLAG_REDIS_KEY = "hardware-gateway::instance";

    /**
     * 注册标识-redisKey
     */
    public static final String DATA_CENTER_REGISTER_FLAG_REDIS_KEY = "hardware-gateway::instance::date-center";

    /**
     * socket方式标识-redisKey
     */
    public static final String SOCKET_FLAG_REDIS_KEY = "hardware-gateway::instance::socket";

    /**
     * 接口URL标识-redisKey
     */
    public static final String INTERFACE_URL_REDIS_KEY = "interface_url_";

    /**
     * AppId验证标识-redisKey
     */
    public static final String APP_ID_REDIS_KEY = "appId_";
    /**
     * type-method关联标识-redisKey
     */
    public static final String TYPE_METHOD_REDIS_KEY = "type_method_";

}
