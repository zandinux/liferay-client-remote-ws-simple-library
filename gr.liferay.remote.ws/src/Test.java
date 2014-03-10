import gr.liferay.remote.ws.LiferayProperties;
import gr.liferay.remote.ws.server.Services;

import java.rmi.RemoteException;
import java.util.ResourceBundle;

/**
 * 
 * @author gperreas (aka Zandinux)
 *
 */
public class Test {

	static long userId = 0;
	static long groupId = 0;

	
	/**
	 * Test
	 * @param args
	 * @throws RemoteException
	 */
	public static void main(String[] args){
		
		ResourceBundle resourceBundle = ResourceBundle.getBundle("liferay");
		
		LiferayProperties.initResourceBundle(resourceBundle);
		
		Services._initServices();
		
		Services._startUserService();
		Services._startCompanyService();
		Services._startGroupService();
		Services._startDLAppService();
		
		userId = Services.getUserId();
		groupId = Services.getGroupId();
		
		
		System.out.println("UserID:" + userId);

		System.out.println("GroupID:" + groupId);
		
	}

}
