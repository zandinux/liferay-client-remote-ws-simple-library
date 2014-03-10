package gr.telesto.liferay.remote.ws.server;

import gr.telesto.liferay.Util;
import gr.telesto.liferay.remote.ws.LiferayProperties;

import java.net.URL;

/**
 * 
 * @author gperreas (aka Zandinux)
 *
 */
public class ConnectionController {
	
	private static String LIFERAY_USER_NAME;
	private static String LIFERAY_USER_PASSWORD;
	
	static final String DL_DLAPP_SERVICE = "Portlet_DL_DLAppService";
	static final String USER_SERVICE = "Portal_UserService";
	static final String COMPANY_SERVICE = "Portal_CompanyService";
	static final String GROUP_SERVICE = "Portal_GroupService";

	static URL userServiceEndPoint;
	static URL dlAppServiceEndPoint;	
	static URL companyServiceEndPoint;	
	static URL groupServiceEndPoint;	
	
	public static void _setUserCredential()
	{
		ConnectionController.LIFERAY_USER_NAME = LiferayProperties.getMap().get("LIFERAY_USER_NAME").toString();
		ConnectionController.LIFERAY_USER_PASSWORD = LiferayProperties.getMap().get("LIFERAY_USER_PASSWORD").toString();
	}
	
	public static void initUrls(){
		userServiceEndPoint = Util._getURL(LIFERAY_USER_NAME, LIFERAY_USER_PASSWORD, USER_SERVICE);
		dlAppServiceEndPoint = Util._getURL(LIFERAY_USER_NAME, LIFERAY_USER_PASSWORD, DL_DLAPP_SERVICE);
		companyServiceEndPoint = Util._getURL(LIFERAY_USER_NAME, LIFERAY_USER_PASSWORD, COMPANY_SERVICE);
		groupServiceEndPoint = Util._getURL(LIFERAY_USER_NAME, LIFERAY_USER_PASSWORD, GROUP_SERVICE);
	}
	
	public URL _getUserServiceEndPoint(){
		return userServiceEndPoint;
	}
	
	public URL _getDLAppServiceEndPoint(){
		return dlAppServiceEndPoint;
	}

	public URL _getCompanyServiceEndPoint(){
		return companyServiceEndPoint;
	}

	public URL _getGroupServiceEndPoint(){
		return groupServiceEndPoint;
	}


}
