package snake_multi;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
/**
 * Lấy thông tin từ properties để chuyển đổi thành thông tin trên source
 * @author TranCamTu
 */
public class Resource {
    
    protected static ResourceBundle resources;
    static{
        try{
            resources = ResourceBundle.getBundle("snake_multi/SnakeProperties",Locale.getDefault());
        }catch(Exception e){
            javax.swing.JOptionPane.showMessageDialog(null,
                    "Snake properties not found",
                    "Error",javax.swing.JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }
    /***
     * Lấy định nghĩa trong properties dạng string
     * @param key
     * @return 
     */
    public String getResourceString(String key){
        String str;
        try{
            str = resources.getString(key);
        }catch(Exception e){
            str = null;
        }
        return str;
    }
    /**
     * Lấy định nghĩa trong properties dạng URL
     * @param key
     * @return 
     */
    public URL getResource(String key){
        String name = getResourceString(key);
        if(name != null){
            URL url = this.getClass().getResource(name);
            return url;
        }
        return null;
    }
}
