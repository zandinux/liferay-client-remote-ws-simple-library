package gr.liferay;

import gr.liferay.remote.ws.LiferayProperties;
import gr.liferay.remote.ws.StringPool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.liferay.client.soap.portal.kernel.repository.model.FileEntrySoap;

/**
 * 
 * @author gperreas (aka Zandinux)
 *
 */
public class Util {
	
	static final Logger LOGGER = LoggerFactory.getLogger(Util.class);
	
	static String LIFERAY_PROTOCOL = LiferayProperties.getMap().get("LIFERAY_PROTOCOL").toString();
	static String LIFERAY_TCP_PORT = LiferayProperties.getMap().get("LIFERAY_TCP_PORT").toString();
	static String LIFERAY_DN = LiferayProperties.getMap().get("LIFERAY_DN").toString();
	static String LIFERAY_AXIS_PATH = LiferayProperties.getMap().get("LIFERAY_AXIS_PATH").toString();
	
	static int MAX_FOLDER_NAME_LEGTH = 100;
	
	public static URL _getURL(String remoteUser, String password, String serviceName) {	
			
		try {
			
			return new URL(LIFERAY_PROTOCOL
				+ URLEncoder.encode(remoteUser, "UTF-8") + StringPool.COLON
				+ URLEncoder.encode(password, "UTF-8") + StringPool.AT + LIFERAY_DN
				+ StringPool.COLON + LIFERAY_TCP_PORT + LIFERAY_AXIS_PATH + serviceName);
		} catch (MalformedURLException e) {
			LOGGER.error(e.getMessage());
			return null;
		} catch (UnsupportedEncodingException e) {
			LOGGER.error(e.getMessage());
			return null;
		}
	}
	
	public static String _getFileDLUrl(FileEntrySoap f){
			
		StringBuilder sb = new StringBuilder();

		sb.append("documents/");
		sb.append(f.getRepositoryId());
		sb.append(StringPool.SLASH);
		sb.append(f.getFolderId());
		sb.append(StringPool.SLASH);
		sb.append(_getEncondedString(f.getTitle()));
		sb.append(StringPool.SLASH);
		sb.append(f.getUuid());
		sb.append("?version=");
		sb.append(f.getVersion());
			
		return sb.toString();
			
	}
		
	public static byte[] _getFileAsByte(String path) {
			File file = new File(path);
		
		try {
			FileInputStream fin = new FileInputStream(file);
			byte fileContent[] = new byte[(int) file.length()];
			fin.read(fileContent);
			fin.close();
			
			return fileContent;
		} catch (FileNotFoundException e) {
			LOGGER.error("File not found: " + e);
		} catch (IOException e) {
			LOGGER.error("Exception while reading the file: " + e);
		}
		return null;
	}
	
	public static String _getEncondedString(String text){
		
		try {
			text = URLEncoder.encode(text, "UTF-8");
		} catch (UnsupportedEncodingException ue) {
			// TODO Auto-generated catch block
			LOGGER.error(ue.getMessage());
		}
			
		return text;		
	}


	/**
	 * 
	 * @param folder_name
	 * @return String
	 * 
	 * <p>Return a validated folder name for liferay</p>
	 * 
	 * <p>Acceptable symbols: -_!$^.[]΄¨«»</p>
	 * 
	 * <p>Non Acceptable symbols: @#%&*(),<>/?'":;+=\</p>
	 * 
	 */
	public static String _getValidFolderName(String name){

		if(name.length()>MAX_FOLDER_NAME_LEGTH){
			name = name.substring(0, MAX_FOLDER_NAME_LEGTH);
		}
		
		if ( !name.matches("[=+|[{]};:'\",<>%/@#&*()?/]*") ) {
			name = name.replaceAll("[=+|[{]};:'\",<>%/@#&*()?/]", "_");
			
			if(name.contains("\\")){
				
				return name.replace("\\", "_");
				
			}else return name;
				
		}
		else if(name.contains("\\")){
			
			return name.replace("\\", "_");
		}
		else return name;
	}
	
}
