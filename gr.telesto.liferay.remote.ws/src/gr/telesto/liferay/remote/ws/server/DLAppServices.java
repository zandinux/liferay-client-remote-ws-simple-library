package gr.telesto.liferay.remote.ws.server;


import gr.telesto.liferay.Util;
import gr.telesto.liferay.pojo.File;
import gr.telesto.liferay.pojo.Folder;
import gr.telesto.liferay.remote.ws.LiferayProperties;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.liferay.client.soap.portal.kernel.repository.model.FileEntrySoap;
import com.liferay.client.soap.portal.kernel.repository.model.FolderSoap;
import com.liferay.client.soap.portal.service.ServiceContext;

/**
 * 
 * @author gperreas (aka Zandinux)
 *
 */
public class DLAppServices {
	
	static final Logger LOGGER = LoggerFactory.getLogger(DLAppServices.class);
	
	static FolderSoap _folder;
	
	static final long LIFERAY_DEFAULT_FOLDER_ID = 0L;
	
	static ServiceContext serviceContext = new ServiceContext();
	
	static long userId = 0;
	static long groupId = 0;

	
	/**
	 * Test
	 * @param args
	 * @throws RemoteException
	 */
	public static void main(String[] args) throws RemoteException {
		
		ResourceBundle resourceBundle = ResourceBundle.getBundle("liferay");
		
		LiferayProperties.initResourceBundle(resourceBundle);
		
		Services._initServices();
		
		Services._startUserService();
		Services._startCompanyService();
		Services._startGroupService();
		Services._startDLAppService();
		
		userId = Services.getUserId();
		groupId = Services.getGroupId();
		
		System.out.println(groupId);
		
	}
	
	
	public static void createFolder(long folderId, String name, String description){
		
		try {
			
			// ACTION_PUBLISH 1
			
			serviceContext.setWorkflowAction(1);
			
			_folder = Services.getDLAppServiceSoap().addFolder(Services.getGroupId(), folderId, name, description, serviceContext);
			 
			System.out.println("Folder Id:" + _folder.getFolderId());
			System.out.println("Folder Name: " + _folder.getName());
		}
		catch (RemoteException re) {
			LOGGER.error(re.getClass().getCanonicalName()
			+ re.getMessage());
		}
		
	}
	
	
	public static void updateFolder(long folderId, String name, String description){
		
		try {
			
			 _folder = Services.getDLAppServiceSoap().updateFolder(folderId, name, description, serviceContext);
			 System.out.println("Folder Id:" + _folder.getFolderId());
			 System.out.println("Folder Name: " + _folder.getName());
		}
		catch (RemoteException re) {
			LOGGER.error(re.getClass().getCanonicalName()
			+ re.getMessage());
		}
		
	}
	
	
	public static List<FileEntrySoap> getFileEntries(long foldeId){
		
		try {
			return Arrays.asList(Services.getDLAppServiceSoap().getFileEntries(Services.getGroupId(), foldeId));
		} catch (RemoteException re) {
			LOGGER.error(re.getClass().getCanonicalName()
			+ re.getMessage());
		}
	
		return null;
	}
	
	
	public static List<FolderSoap> getFolders(long rootFolderId){

		try {
			return Arrays.asList(Services.getDLAppServiceSoap().getFolders(Services.getGroupId(), rootFolderId));
		} catch (RemoteException re) {
			LOGGER.error(re.getClass().getCanonicalName()
			+ re.getMessage());
		}
		
		return null;
	}
	
	
	public static List<Folder> getFolderList(long rootFolderId){

		List<Folder> folder_list = new ArrayList<Folder>();
		List<FolderSoap> folderSoap_list;
		
		try {
			
			folderSoap_list = Arrays.asList(Services.getDLAppServiceSoap().getFolders(Services.getGroupId(), rootFolderId));
			
			for(FolderSoap f:folderSoap_list){
				
				Folder myFolder = new Folder();
				
				myFolder.setFolderId(f.getFolderId());
				myFolder.setGroupId(f.getGroupId());
				myFolder.setName(f.getName());
				
				folder_list.add(myFolder);
				
			}
			
			return folder_list;
			
		} catch (RemoteException re) {
			LOGGER.error(re.getClass().getCanonicalName()
			+ re.getMessage());
		}
		
		return null;
	}
	
	
	public static Folder getFolder(long rootFolderId, String name){

		Folder myFolder;
		
		try {
			
			FolderSoap f = Services.getDLAppServiceSoap().getFolder(Services.getGroupId(), rootFolderId, name);
			
			if(!f.equals(null)){
				
				myFolder = new Folder();
				
				myFolder.setFolderId(f.getFolderId());
				myFolder.setGroupId(f.getGroupId());
				myFolder.setName(f.getName());
				
				return myFolder;
				
			}else return null;
			
		} catch (RemoteException re) {
			LOGGER.error(re.getClass().getCanonicalName()
			+ re.getMessage());
		}
		
		return null;
	}
	
	
	public static boolean folderExist(long folderId, String name){
		
		if(!getFolder(folderId, name).equals(null)) return true;
		else return false;
	}
	
	
	public static List<File> getFileList(long folderId){
		
		List<File> file_list = new ArrayList<File>();
		
		List<FileEntrySoap> entries = getFileEntries(folderId);
		for(FileEntrySoap f:entries){
			
			
			File file = new File();
			file.setName(f.getTitle());
			file.setMimeType(f.getMimeType());
			file.setDl_link(Util._getFileDLUrl(f));
			
			file_list.add(file);
			
			//System.out.println("folder: " + file.getName() + "\t" + file.getDl_link());
			
		}
		
		return file_list;
	}
	
	
	public static long getDefaultFolderId(){
		return LIFERAY_DEFAULT_FOLDER_ID;
	}
	

}
