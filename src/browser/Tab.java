package browser;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

/**
 * @author Alec Hewitt C0704504
 */
public class Tab extends JPanel implements HyperlinkListener {

    private String title;
    private URL url;
    private JEditorPane viewer;
    private TabbedPanel tabPanel;
    private ArrayList <URL> tempPagesVisited;
    private int currentIndex = 0;
    private PopUpMenu popMenu;
    private boolean currentFavourite;
    
    
    public Tab(){}
    
    public Tab( TabbedPanel tabPanel, String urlString) {
        
        // The Tab panel in which this instance of a tab resides within.
        this.tabPanel = tabPanel;
        
        // List of pages visited within this tab
        tempPagesVisited = new ArrayList<URL>();
              
        URL verifiedUrl = verifyUrl( urlString );
        if (verifiedUrl != null) {
            url = verifiedUrl;
         }
        
        //Initialise butttons within jpanel (components generated with netbeans)
        initComponents();

        //Create JEditPane that will display the html
        viewer = new JEditorPane();
        try{
            viewer.setPage(url);
        } 
        catch (IOException ex){
            System.out.println( "There is an IOException: " + ex );
        }
        viewer.setContentType("text/html");
        viewer.setEditable(false);
        viewer.addHyperlinkListener(this);
        
        
        // add viewer to the tab within a JScrollPane
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.getViewport().add( viewer, BorderLayout.CENTER );
        this.add( scrollPane, BorderLayout.CENTER );

        
        // Set title of tab to title of web page
        setTitle();
        
        //  update address field      
        addressTextField.setText(url.toString());
        
        //Update back and forward buttons
        tempPagesVisited.add(url);
        updateNavigation();
        
        //Update whether url is in favourites
        updateCurrentFavourite();
        
        //Add popupmenu to the JEditPane
        popMenu = new PopUpMenu();
        viewer.setComponentPopupMenu(popMenu);
        
        
        //Add mouse click event to all items identify tab panel(used to identify side of split pane in use when split)
        TabbedPanelIdentifier mouseListener = new TabbedPanelIdentifier();
        viewer.addMouseListener(mouseListener);
        addressGoButton.addMouseListener(mouseListener);
        addressTextField.addMouseListener(mouseListener);
        backButton.addMouseListener(mouseListener);
        forwardButton.addMouseListener(mouseListener);
        quickFavouriteButton.addMouseListener(mouseListener);
        jPanel1.addMouseListener(mouseListener);
        scrollPane.getVerticalScrollBar().addMouseListener(mouseListener);
        scrollPane.getHorizontalScrollBar().addMouseListener(mouseListener);
        
        
        
    }
    
    public void setURL(URL address){
        url = address;
        showPage(true);
    }
    
    public URL getURL(){
        return url;
    }
    public PopUpMenu getPopUpMenu(){
        return popMenu;
    }
    
    
    
    public void setTitle(){
        String htmlTitle = (String) viewer.getDocument().getProperty("title");
        if (htmlTitle != null){
            this.title = htmlTitle;
        }
        else{
            this.title = "Alec's Web Browser";
        }
        
    }
    public String getTitle(){
        return this.title;
    }
    
        
    @Override
    public void hyperlinkUpdate( HyperlinkEvent event ){
       if( event.getEventType() == HyperlinkEvent.EventType.ACTIVATED ){
            url = event.getURL();
            showPage(true);
       }
    }
    
    private void showPage(boolean addToPagesVisited){
        // Load cursors
        Cursor cursor = viewer.getCursor();
        Cursor waitCursor = Cursor.getPredefinedCursor( Cursor.WAIT_CURSOR );
        viewer.setCursor( waitCursor );

        SwingUtilities.invokeLater( new PageLoader( viewer, url, cursor) );
        
        //Update tab title 
        setTitle();
        tabPanel.setTabTitle(this);

        //Update address bar
        addressTextField.setText(url.toString());
        
        //Update whather its a favourite
        updateCurrentFavourite();
                
        //Update navigation if it is new page
        if (addToPagesVisited) {
            tempPagesVisited.add(url);
            currentIndex = tempPagesVisited.size();
            updateNavigation();
        }
        
        
    }
    
    private void updateNavigation(){
        
        if (currentIndex <= 0){
            //add some code to change look of buttons
            backButton.setEnabled(false);

        }
        else{
            backButton.setEnabled(true);
        
        }
        if (currentIndex >= tempPagesVisited.size() - 1 ){
            forwardButton.setEnabled(false);

        }
        else{
            forwardButton.setEnabled(true);

        }
    }
    
    public void updateCurrentFavourite(){
        DatabaseConection database = MainFrame.getDatabase();
        
        currentFavourite = database.checkIfFavourite(url);
        
        if(currentFavourite == true){
            //Set favourite button's icon to yellow star
            quickFavouriteButton.setIcon(new ImageIcon(getClass().getResource("/imagesAndfiles/colouredStar.gif")));
        }
        else{
            quickFavouriteButton.setIcon(new ImageIcon(getClass().getResource("/imagesAndfiles/uncolouredStar.gif")));
        }
    }
    

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        backButton = new javax.swing.JButton();
        forwardButton = new javax.swing.JButton();
        addressTextField = new javax.swing.JTextField();
        addressGoButton = new javax.swing.JButton();
        quickFavouriteButton = new javax.swing.JButton();

        setOpaque(false);
        setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(214, 217, 223));
        jPanel1.setOpaque(false);

        backButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagesAndfiles/backArrowUncoloured.gif"))); // NOI18N
        backButton.setMaximumSize(new java.awt.Dimension(57, 23));
        backButton.setMinimumSize(new java.awt.Dimension(57, 23));
        backButton.setPreferredSize(new java.awt.Dimension(57, 23));
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });

        forwardButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagesAndfiles/forwardArrowUncoloured.gif"))); // NOI18N
        forwardButton.setMaximumSize(new java.awt.Dimension(57, 33));
        forwardButton.setMinimumSize(new java.awt.Dimension(57, 23));
        forwardButton.setOpaque(false);
        forwardButton.setPreferredSize(new java.awt.Dimension(57, 23));
        forwardButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                forwardButtonActionPerformed(evt);
            }
        });

        addressTextField.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        addressTextField.setText("jTextField1");
        addressTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addressTextFieldActionPerformed(evt);
            }
        });

        addressGoButton.setText("GO");
        addressGoButton.setMaximumSize(new java.awt.Dimension(57, 33));
        addressGoButton.setMinimumSize(new java.awt.Dimension(57, 23));
        addressGoButton.setPreferredSize(new java.awt.Dimension(57, 23));
        addressGoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addressGoButtonActionPerformed(evt);
            }
        });

        quickFavouriteButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagesAndfiles/uncolouredStar.gif"))); // NOI18N
        quickFavouriteButton.setToolTipText("Add to Quick Favourites");
        quickFavouriteButton.setAlignmentY(0.0F);
        quickFavouriteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quickFavouriteButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(backButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(forwardButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addressTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(addressGoButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(quickFavouriteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(addressTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(addressGoButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(forwardButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(backButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(quickFavouriteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 3, Short.MAX_VALUE))
        );

        add(jPanel1, java.awt.BorderLayout.NORTH);
    }// </editor-fold>//GEN-END:initComponents

    private void addressGoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addressGoButtonActionPerformed
        URL verifiedUrl = verifyUrl( addressTextField.getText() );
        
        if (verifiedUrl != null) {
            url = verifiedUrl;
            showPage(true);
        } 
        else {
            showError("Sorry you have entered an invalid URL. Please ensure you put 'http://' at the start of the address");
        }   
        
    }//GEN-LAST:event_addressGoButtonActionPerformed

    private void forwardButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_forwardButtonActionPerformed
        int index = currentIndex+1;
        url = tempPagesVisited.get(index);
        showPage(false);
        
        //increment currentIndex and update navigation
        currentIndex ++;
        updateNavigation();
        
        
    }//GEN-LAST:event_forwardButtonActionPerformed

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        int index = currentIndex -1;
        
        //set url and show page
        url = tempPagesVisited.get(index);
        showPage(false);
        
        //decrement currentIndex
        currentIndex --;
        updateNavigation();
        
    }//GEN-LAST:event_backButtonActionPerformed

    private void addressTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addressTextFieldActionPerformed
        // Enter being pressed in text field triggers same method as go button beiing pressed
        addressGoButtonActionPerformed(evt);
    }//GEN-LAST:event_addressTextFieldActionPerformed

    private void quickFavouriteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quickFavouriteButtonActionPerformed
        DatabaseConection database = MainFrame.getDatabase();
        
        if(currentFavourite ==false){
            
            //Create quick favourites folder
            FavouriteFolder folder = new FavouriteFolder("Quick Favourites", 0);

            //Add folder to database
            database.addFolder(folder);

            //find Favouritefolders ID
            int folderID = folder.getID();

            //A new favourite to be stored within the quick favourite folder.
            Favourite favourite = new Favourite(url, folderID, title);

            //Add the favourite to the database
            database.addFavourite(favourite);

            //update favourite button image
            quickFavouriteButton.setIcon(new ImageIcon(getClass().getResource("/imagesAndfiles/colouredStar.gif")));
            
            currentFavourite = true;
        }
        else{
            database.deleteFavourite(url);
            quickFavouriteButton.setIcon(new ImageIcon(getClass().getResource("/imagesAndfiles/uncolouredStar.gif")));
            currentFavourite = false;
        }
        
        FavouritesFrame.updateTabsCurrentFavourite();
        
    
    }//GEN-LAST:event_quickFavouriteButtonActionPerformed
    
    private URL verifyUrl(String url) {
        // Verify format of URL.
        URL verifiedUrl = null;
        try {
            verifiedUrl = new URL(url);
        } 
        catch (MalformedURLException e) {
            System.out.println( "Malformed URL: " + e );
        }
        
        return verifiedUrl;
    }
    
    class TabbedPanelIdentifier extends MouseAdapter{
        @Override
         public void mouseClicked(MouseEvent e){
             MainFrame.setCurrentTabbedPanel(tabPanel);
          }
         public void mousePressed(MouseEvent e){
             MainFrame.setCurrentTabbedPanel(tabPanel);
        }
     }
    
    
    
    private void showError(String errorMessage) {
        
        JOptionPane.showMessageDialog(this, errorMessage,
        "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addressGoButton;
    private javax.swing.JTextField addressTextField;
    private javax.swing.JButton backButton;
    private javax.swing.JButton forwardButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton quickFavouriteButton;
    // End of variables declaration//GEN-END:variables
}
