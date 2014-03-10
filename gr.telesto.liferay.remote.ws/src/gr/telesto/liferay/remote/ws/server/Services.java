package gr.telesto.liferay.remote.ws.server;

import gr.telesto.liferay.remote.ws.LiferayProperties;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.liferay.client.soap.portal.model.CompanySoap;
import com.liferay.client.soap.portal.model.GroupSoap;
import com.liferay.client.soap.portal.service.http.CompanyServiceSoap;
import com.liferay.client.soap.portal.service.http.CompanyServiceSoapServiceLocator;
import com.liferay.client.soap.portal.service.http.GroupServiceSoap;
import com.liferay.client.soap.portal.service.http.GroupServiceSoapService;
import com.liferay.client.soap.portal.service.http.GroupServiceSoapServiceLocator;
import com.liferay.client.soap.portal.service.http.Portal_UserServiceSoapBindingStub;
import com.liferay.client.soap.portal.service.http.UserServiceSoap;
import com.liferay.client.soap.portal.service.http.UserServiceSoapServiceLocator;
import com.liferay.client.soap.portlet.documentlibrary.service.http.DLAppServiceSoap;
import com.liferay.client.soap.portlet.documentlibrary.service.http.DLAppServiceSoapServiceLocator;

/**
 * 
 * @author gperreas (aka Zandinux)
 *
 */
public class Services {
	
	private static String LIFERAY_USER_NAME;
	private static String LIFERAY_USER_PASSWORD;
	
	static final Logger LOGGER = LoggerFactory.getLogger(Services.class);
	
	static UserServiceSoap userService;
	static CompanySoap companySoap;
	static GroupServiceSoap groupService;
	static DLAppServiceSoap dlAppService;
	
	static GroupSoap[] usergroups;
	
	public static void _initServices()
	{
		Services.LIFERAY_USER_NAME = LiferayProperties.getMap().get("LIFERAY_USER_NAME").toString();
		Services.LIFERAY_USER_PASSWORD = LiferayProperties.getMap().get("LIFERAY_USER_PASSWORD").toString();
		
		ConnectionController._setUserCredential();
		ConnectionController.initUrls();
	}
	
	
	public static void _startUserService(){
		
		System.out.println("Try lookup User Service by End Point: " + ConnectionController.userServiceEndPoint + "...");
		
		UserServiceSoapServiceLocator locatorUser = new UserServiceSoapServiceLocator();
		try {
			Services.userService = locatorUser.getPortal_UserService(ConnectionController.userServiceEndPoint);
			
			((Portal_UserServiceSoapBindingStub) userService).setUsername(LIFERAY_USER_NAME);			
			((Portal_UserServiceSoapBindingStub) userService).setPassword(LIFERAY_USER_PASSWORD);
			
		} catch (ServiceException se) {
			LOGGER.error(se.getClass().getCanonicalName()
			+ se.getMessage());
		}	
		
	}
	
	public static void _startCompanyService(){
		
		System.out.println("Try lookup Company Service by End Point: " + ConnectionController.companyServiceEndPoint + "...");
		
		CompanyServiceSoapServiceLocator locatorCompany = new CompanyServiceSoapServiceLocator();
		CompanyServiceSoap companyService;
		
		try {
			companyService = locatorCompany.getPortal_CompanyService(ConnectionController.companyServiceEndPoint);
			Services.companySoap = companyService.getCompanyByVirtualHost("localhost");
		} catch (ServiceException se) {
			LOGGER.error(se.getClass().getCanonicalName()
			+ se.getMessage());
		} catch (RemoteException re) {
			LOGGER.error(re.getClass().getCanonicalName()
			+ re.getMessage());
		}	
		
		
	}

	public static void _startGroupService(){
		
		System.out.println("Try lookup Group Service by End Point: " + ConnectionController.groupServiceEndPoint + "...");
		
		GroupServiceSoapService locatorGroup = new GroupServiceSoapServiceLocator();
		try {
			Services.groupService = locatorGroup.getPortal_GroupService(ConnectionController.groupServiceEndPoint);
		} catch (ServiceException se) {
			LOGGER.error(se.getClass().getCanonicalName()
			+ se.getMessage());
		}	
	}

	public static void _startDLAppService(){
		
		System.out.println("Try lookup DL App Service by End Point: " + ConnectionController.dlAppServiceEndPoint + "...");
		
		DLAppServiceSoapServiceLocator locatorDLApp = new DLAppServiceSoapServiceLocator();
		try {
			Services.dlAppService = locatorDLApp.getPortlet_DL_DLAppService(ConnectionController.dlAppServiceEndPoint);
		} catch (ServiceException se) {
			LOGGER.error(se.getClass().getCanonicalName()
			+ se.getMessage());
		}
	}
	
	
	public static UserServiceSoap getUserServiceSoap(){
		return userService;
	}
	
	public static GroupServiceSoap getGroupServiceSoap(){
		return groupService;
	}
	
	public static DLAppServiceSoap getDLAppServiceSoap(){
		return dlAppService;
	}
	
	public static CompanySoap getCompanySoap(){
		return companySoap;
	}
	
	public static long getUserId(){
		try {
			return userService.getUserIdByScreenName(companySoap.getCompanyId(), LIFERAY_USER_PASSWORD);
		} catch (RemoteException re) {
			LOGGER.error(re.getClass().getCanonicalName()
			+ re.getMessage());
		}
		
		return 0;
	}
	
	public static long getGroupId(){
		
		long groupId = 0;
		
		try {
			usergroups = groupService.getUserSites();
			
			for (int i = 0; i < usergroups.length; i++) {
				
				if (usergroups[i].getName().equalsIgnoreCase(
					LiferayProperties.getMap().get("LIFERAY_PUBLISH_SITE").toString())) {
					
					groupId = usergroups[i].getGroupId();
					
					System.out.println("Found the group "
					+ " (GroupId: " + groupId
					+ ") to publish the document");
				}
			}
		} catch (RemoteException re) {
			LOGGER.error(re.getClass().getCanonicalName()
			+ re.getMessage());
		}

		return groupId;
	}
}
