package com.yzd.web.util.settingExt;



import java.io.InputStream;
import java.util.Properties;
import java.util.ResourceBundle;


/***
 *
 *
 * @author yzd
 * @date 2018/9/13 16:40.
 */
public class ProjectSetting {
    private static final String DEFAULT_PROJECT_PROPERTIES =getPropertiesFileFullName("project") ;
    private static ResourceBundle PROJECT_CONFIG = ResourceBundle.getBundle(DEFAULT_PROJECT_PROPERTIES);

    public static String getConfigProperty(String key) {
        if(!PROJECT_CONFIG.containsKey(key)){
            return "";
        }
        return PROJECT_CONFIG.getString(key);
    }
    private static String getPropertiesFileFullName(String fileName)
    {
        return fileName+getActiveProperties();
    }
    private static String getActiveProperties()
    {
        Properties prop = new Properties();
        try {
            InputStream is =new Resource().getInputStream();
            if(is==null) {
                return "";
            }
            prop.load(is);
            if (is != null) {
                is.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String result=prop.getProperty("spring.profiles.active");
        return result==null?"":"-"+result;
    }
    static class Resource
    {
        public InputStream getInputStream()
        {
            return getClass().getResourceAsStream("/dev/application.properties");
        }
    }
}

