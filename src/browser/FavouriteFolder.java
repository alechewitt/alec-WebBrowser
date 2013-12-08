package browser;

/**
 * @author Alec Hewitt C0704504 
 */
public class FavouriteFolder {

    private int id;
    private int parentFolderID;
    private String folderName;
    
    public FavouriteFolder(String folderName, int parentFolderID){
        this.folderName = folderName;
        this.parentFolderID = parentFolderID;
    }
    
    public FavouriteFolder(int folderID, String folderName, int parentFolderID){
        this.id = folderID;
        this.folderName = folderName;
        this.parentFolderID = parentFolderID;
    }
    
    @Override
    public String toString() {
        return folderName;
    }
    
    public int getID() {
        DatabaseConection database = MainFrame.getDatabase();
        id = database.findFolderID(this.getFolderName());
        return id;
    }

    public int getParentFolderID() {
        return parentFolderID;
    }

    public void setParentFolderID(int parentFolderID) {
        this.parentFolderID = parentFolderID;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }
    
}
