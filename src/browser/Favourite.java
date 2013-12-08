package browser;

import java.net.URL;

/**
 * @author Alec Hewitt C0704504 
 */

public class Favourite {
    
    private URL url;
    private int folderID;
    private String title;

    
    public Favourite(){}
    
    public Favourite( URL url, int folderID, String title){
        this.url = url;
        this.folderID = folderID;
        this.title = title;
    }
    
    @Override
    public String toString() {
        return (title + " - " + url.toString());
    }
    
    
    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public int getFolderID() {
        return folderID;
    }

    public void setFolderID(int folderID) {
        this.folderID = folderID;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
