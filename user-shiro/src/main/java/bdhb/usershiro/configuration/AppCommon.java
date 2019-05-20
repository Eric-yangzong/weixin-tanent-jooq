package bdhb.usershiro.configuration;

/**
 * @ClassName: AppCommon
 * @Description: 存放app的公用信息
 * @author yangxz
 * @date 2018年8月4日 下午2:44:25
 * 
 */
public class AppCommon {

	public static final String scheam = "_mod_login";

	// okapi中tenantId向后台传值的header信息
	public final static String TENANT_ID = "x-okapi-tenant";

	// okapi中tenantId向后台传值的header信息
	// "x-okapi-token";
	public final static String TOKEN = "x-auth-token";
}
