package gr.telesto.liferay.remote.ws;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * 
 * @author gperreas (aka Zandinux)
 *
 */
public class LiferayProperties {

	private static ResourceBundle properties;
	private static Map<String,String> map;
	
	public static void initResourceBundle(ResourceBundle resourceBundle)
	{
		properties = resourceBundle;
	}

	public static String[] getArrayProperties(String path)
	{
		return properties.getObject(path).toString().split(",");
	}
	
	public static String getStringProperty(String path)
	{	
		if( properties.containsKey(path) ) return properties.getObject(path).toString();
		
		return "";
	}
	
	public static Map<String, String> getPropertiesMap()
	{
		return convertResourceBundleToMap(properties);
	}
	
	
	public static ResourceBundle getResourceBundle()
	{
		return properties;
	}
	
	public static void setPropertiesMap(Map<String, String> map)
	{
		LiferayProperties.map = map;
	}
	
	public static Map<String, String> getMap()
	{
		return LiferayProperties.map;
	}
	
	private static Map<String, String> convertResourceBundleToMap(ResourceBundle resource) {
        Map<String, String> map = new HashMap<String, String>();
 
        Enumeration<String> keys = resource.getKeys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            map.put(key, resource.getString(key));            
        }
        
        return map;
    }
	
}
