package browser;

/**
 * @author Alec Hewitt C0704504 
 */
import java.io.*;
import java.net.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.text.*;

class PageLoader implements Runnable{
    private JEditorPane html;
    private URL         url;
    private Cursor      cursor;
    
    

    PageLoader( JEditorPane html, URL url, Cursor cursor){
        this.html = html;
        this.url = url;
        this.cursor = cursor;
        
        
    }

    @Override
    public void run(){
	 if( url == null ){
            // restore the original cursor
            html.setCursor( cursor );

            Container parent = html.getParent();
            parent.repaint();
                
        }
        else{
            Document doc = html.getDocument();
            try{
                html.setPage( url );
            }
            catch( IOException ioe ){
                html.setDocument( doc );
            }
            finally{
                // schedule the cursor to revert after
                // the paint has happended.
                url = null;
                SwingUtilities.invokeLater( this );
            }
         }
    }
}