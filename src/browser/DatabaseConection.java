package browser;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;


/**
 * @author Alec Hewitt C0704504 
 */
public class DatabaseConection {
    private String database;
    
    
    public DatabaseConection(String databaseS) {
        database = databaseS;
        initDatabaseTables();
    }
    
    private void initDatabaseTables() 
    {   
        File databaseFile = new File(".\\"+database);
        Boolean databaseExists = databaseFile.exists();
        Connection conn = null;
        
        try{
        
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:"+database);
            Statement stat = conn.createStatement();

            try
            { 
                stat.executeUpdate("CREATE TABLE folders (folderID INTEGER PRIMARY KEY AUTOINCREMENT, "
                                                            + "folderName VARCHAR UNIQUE, "
                                                            + "parentFolderID INTEGER);");
                
                stat.executeUpdate("CREATE TABLE favourites (url VARCHAR PRIMARY KEY UNIQUE, "
                                                            + "title VARCHAR, "
                                                            + "folderID VARCHAR);");
                
                //Tables did not exist but dont fear I have just made them
            }
        
            catch(SQLException sqle)
            { 
                //Catches tables already existing excpetion.
            }
        } 
        catch(SQLException e){}
        catch(Exception e){}
        
        finally
        {
            try
            {
              if(conn != null){conn.close();}
            }
            catch(SQLException e)
            {
              // connection close failed.
              System.err.println(e);
            }
          
        }
    }
    
    
    public ArrayList retrieveFavourites(){
        
        Connection conn = null;
        ArrayList <Favourite> favourites = new ArrayList<Favourite>();
        
        try{
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:"+database);
            Statement stat = conn.createStatement();
            
            ResultSet rs = stat.executeQuery("SELECT * FROM favourites ORDER BY folderID;");
                        
            while (rs.next()) {
                               
                //Get URL, folder ID and title
                String urlString = rs.getString("url");
                int folderID = Integer.parseInt(rs.getString("folderID"));
                String title = rs.getString("title");
                
                //convert string url into URL type
                URL url;
                try {
                    url = new URL(urlString);
                    //Add favourit to array list
                    Favourite aFavourite = new Favourite( url, folderID, title);  
                    favourites.add(aFavourite);
                } 
                catch (MalformedURLException e) {
                    System.out.println( "Malformed URL coming out of the database: " + e );
                }
           }
            
            rs.close();
        
        }
        catch(SQLException sqle){
            System.err.println(sqle);
        }
        catch(Exception e){
            System.err.println(e);
        }
        
        finally{
            try{
              if(conn != null){conn.close();}
            }
            catch(SQLException e){
              // connection close failed.
              System.err.println(e);
            }
          
        }
        
        return favourites;
    }
    
    public ArrayList retrieveFolders(){
        
        Connection conn = null;
        ArrayList <FavouriteFolder> folders = new ArrayList<FavouriteFolder>();
        
        try{
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:"+database);
            Statement stat = conn.createStatement();
            
            ResultSet rs = stat.executeQuery("SELECT * FROM folders ORDER BY folderName;");
                        
            while (rs.next()) {
                               
                //Get URL and folder ID
                int folderID = Integer.parseInt(rs.getString("folderID"));
                String folderName = rs.getString("folderName");
                int parentFolderID = Integer.parseInt(rs.getString("parentFolderID"));
                
                //Add folder to array list to array list
                FavouriteFolder aFavourite = new FavouriteFolder( folderID, folderName, parentFolderID );  
                folders.add(aFavourite);
                
           }
           
            rs.close();
        
        }
        catch(SQLException sqle){
            System.err.println(sqle);
        }
        catch(Exception e){
            System.err.println(e);
        }
        
        finally{
            try{
              if(conn != null){conn.close();}
            }
            catch(SQLException e){
              // connection close failed.
              System.err.println(e);
            }
          
        }
        
        return folders;
    }
    
    public void addFavourite(Favourite favourite){   
        Connection conn = null;
        try
        {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:"+database);
            
            PreparedStatement prep = conn.prepareStatement("INSERT INTO favourites(url, title, folderID) VALUES(?,?,?);" );
                        
            //Add the url in String format
            prep.setString(1, favourite.getUrl().toString() );
            //Add the title as a string
            prep.setString(2, favourite.getTitle().toString() ); 
            //Add the folder id as a string(for sql)
            prep.setString(3,Integer.toString( favourite.getFolderID()) );
            
            
            prep.addBatch();

            conn.setAutoCommit(false);
            prep.executeBatch();
            conn.setAutoCommit(true);
        }

        catch(SQLException e){}
        catch(Exception e){System.err.println(e);}

        finally
        {
            try
            {
              if(conn != null){conn.close();}
            }
            catch(SQLException e)
            {
              // connection close failed.
              System.err.println(e);
            }

        }
    }
    
    public void addFolder(FavouriteFolder folder){   
        Connection conn = null;
        try
        {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:"+database);
            
            PreparedStatement prep = conn.prepareStatement("INSERT INTO folders(folderName, parentFolderID) VALUES(?,?);" );
            
            //Add the folder Name
            prep.setString(1, folder.getFolderName() );
            
            //Add the prentfolder id as a string(for sql)
            prep.setString(2,Integer.toString( folder.getParentFolderID()) );
            
            prep.addBatch();

            conn.setAutoCommit(false);
            prep.executeBatch();
            conn.setAutoCommit(true);
        }

        catch(SQLException e){
            System.out.println("this is an sql exception");
        }
        catch(Exception e){System.err.println(e);}

        finally
        {
            try
            {
              if(conn != null){conn.close();}
            }
            catch(SQLException e)
            {
              // connection close failed.
              System.err.println(e);
            }

        }
    }
    
    public int findFolderID(String folderName){
        Connection conn = null;
        int folderID = 0;
        
        try{
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:"+database);
            Statement stat = conn.createStatement();
            
            ResultSet rs = stat.executeQuery("SELECT folderID FROM folders "
                                              + "WHERE folderName = '"
                                              + folderName+ "';");
                        
            while ( rs.next() ) {
                //Get folder ID
                folderID = Integer.parseInt(rs.getString("folderID"));
           }
            
            rs.close();
            
        
        }
        catch(SQLException sqle){
            System.err.println(sqle);
        }
        catch(Exception e){
            System.err.println(e);
        }
        
        finally{
            try{
              if(conn != null){conn.close();}
            }
            catch(SQLException e){
              // connection close failed.
              System.err.println(e);
            }
          
        }
        return folderID;
        
    }
    
    public void deleteFavourite(URL url){
        String urlString = url.toString();
        
        Connection conn = null;
               
        String query = "DELETE FROM favourites WHERE url = '" + urlString + "';";
        
        try{
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:"+database);
            Statement stat = conn.createStatement();
            
            //Execute the query
            stat.execute(query);
            
            
        
        }
        catch(SQLException sqle){
            System.err.println(sqle);
        }
        catch(Exception e){
            System.err.println(e);
        }
        
        finally{
            try{
              if(conn != null){conn.close();}
            }
            catch(SQLException e){
              // connection close failed.
              System.err.println(e);
            }
          
        }
        
        
    }
    
    public void deleteFolder(int folderID){
        
        Connection conn = null;
               
        String deleteFavourites = "DELETE FROM favourites WHERE folderID = '" + folderID + "';";
        String deleteFolders = "DELETE FROM folders WHERE folderID = '" + folderID + "' "
                                                  + "OR parentFolderID = '"+folderID+"' ;";
        try{
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:"+database);
            Statement stat = conn.createStatement();
            
            //Execute the query
            stat.execute(deleteFavourites);
            stat.execute(deleteFolders);
            
            
        
        }
        catch(SQLException sqle){
            System.err.println(sqle);
        }
        catch(Exception e){
            System.err.println(e);
        }
        
        finally{
            try{
              if(conn != null){conn.close();}
            }
            catch(SQLException e){
              // connection close failed.
              System.err.println(e);
            }
          
        }
        
        
    }
    
    public boolean checkIfFavourite(URL url){
        Connection conn = null;
        String urlString = url.toString();
        
        int count = 0;
        try{
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:"+database);
            Statement stat = conn.createStatement();
            
            ResultSet rs = stat.executeQuery("SELECT COUNT(*) FROM favourites "
                                              + "WHERE url = '"
                                              + urlString + "';");
                        
            while ( rs.next() ) {
                //Get folder ID
                    count = Integer.parseInt(rs.getString("COUNT(*)"));
           }
            
            rs.close();
            
        
        }
        catch(SQLException sqle){
            System.err.println(sqle);
        }
        catch(Exception e){
            System.err.println(e);
        }
        
        finally{
            try{
              if(conn != null){conn.close();}
            }
            catch(SQLException e){
              // connection close failed.
              System.err.println(e);
            }
          
        }
        
        if(count == 0){
            return false;
        }
        else{
            return true;
        }
        
        
        
    }
}
