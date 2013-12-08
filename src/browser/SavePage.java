package browser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;

/**
 * @author Alec Hewitt C0704504
 */
public class SavePage {
    
    private JFileChooser chooser;
    private String fileName;
    private Tab selectedTab;
    private URL currentURL;
    
    
    
    public SavePage(TabbedPanel currentTabbedPanel){
        //Create a file chooser 
        chooser = new JFileChooser();
        chooser.showSaveDialog(null);
        try{
        //get name entered by user
        File file = chooser.getSelectedFile();
        fileName = file.toString() + ".html";

        //Currently selected tab
        selectedTab = currentTabbedPanel.getSelectedTab();
        //The current URL within this tab
        currentURL = selectedTab.getURL();
        
        //Save the page
        save();
        }
        
        catch(NullPointerException e){
            //No file selected therfore no save.
        }
        
        
    }
    
    private void save(){
        try
        {                                             
            // Input stream from the url
            InputStream input = currentURL.openStream();

            //Outputstream
            FileOutputStream output = new FileOutputStream( fileName );

            int nextByte;
            StringBuffer buffer = new StringBuffer();
            nextByte = input.read();

            while ( nextByte != -1) {
                buffer.append( (char)nextByte );
                output.write( (char)nextByte );
                nextByte = input.read();
            }

            input.close();
            output.close();
            

        }
        catch (IOException ex)
        {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
