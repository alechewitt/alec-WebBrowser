package browser;

import java.net.URL;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

/**
 * @author Alec Hewitt C0704504
 */
public class FavouritesFrame extends JFrame implements TreeSelectionListener {
    
    private ArrayList <Favourite> favourites; 
    private ArrayList <FavouriteFolder> folders;
    private Object selection;
    private DefaultTreeModel treeModel;
    private DatabaseConection database;
    private TreePath path;
    private DefaultMutableTreeNode node;
    
    
    public FavouritesFrame() {
        database = MainFrame.getDatabase();
        
        initComponents();
        favouritesTree.addTreeSelectionListener( this );
        
        treeModel = constructTreeModel();
        favouritesTree.setModel(treeModel);
        
        this.setAlwaysOnTop( true );
        
        
    }

    /* Netbeans Generated code */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        favouritesTree = new javax.swing.JTree();
        jPanel1 = new javax.swing.JPanel();
        deleteFavourite = new javax.swing.JButton();
        openFavourite = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(300, 280));

        favouritesTree.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("root");
        favouritesTree.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jScrollPane1.setViewportView(favouritesTree);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        deleteFavourite.setText("Delete");
        deleteFavourite.setEnabled(false);
        deleteFavourite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteFavouriteActionPerformed(evt);
            }
        });

        openFavourite.setText("Open");
        openFavourite.setEnabled(false);
        openFavourite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openFavouriteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(192, Short.MAX_VALUE)
                .addComponent(deleteFavourite, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(openFavourite, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(openFavourite, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(deleteFavourite))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void openFavouriteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openFavouriteActionPerformed
        if (selection != null){
            try{
               //cast selection to favourite
                Favourite favourite = (Favourite) selection;
                //get the favourites url
                URL url = favourite.getUrl(); 

                //open up web page here!
                //get current tabbed panel
                TabbedPanel currentTabbbedPanel = MainFrame.getCurrentTabbedPanel();

                //current tab
                Tab tab = currentTabbbedPanel.getSelectedTab();

                //set the current tab to the favourites url
                tab.setURL(url);

                //Close the frame
                this.dispose();
            }
            catch(Exception e){
                //Favourite not selected
            }
            
        }
        else{
            //Nothing within the jtree is selected
        }
        

    }//GEN-LAST:event_openFavouriteActionPerformed

    private void deleteFavouriteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteFavouriteActionPerformed
        try{
             
            //cast selection to favourite
            Favourite favourite = (Favourite) selection;
            URL url = favourite.getUrl(); 
            database = MainFrame.getDatabase();
            
            //Delete favourite from database
            database.deleteFavourite(url);
            
            //Update the tree model
            treeModel.removeNodeFromParent(node);
            openFavourite.setEnabled(false);
            deleteFavourite.setEnabled(false);
         
        }
        catch(ClassCastException e){
            try{
                //cast selection to folder
                FavouriteFolder folder = (FavouriteFolder) selection;
                int folderID = folder.getID();
                
                database = MainFrame.getDatabase();

                //Delete favourite from database
                database.deleteFolder(folderID);

                //Update the tree model
                treeModel.removeNodeFromParent(node);
                openFavourite.setEnabled(false);
                deleteFavourite.setEnabled(false);
            }
            catch(ClassCastException ex){
                //Trying to delete favourites folder - not allowed
            }
            catch(Exception ex){
                System.out.println(e);
            }
           
        }
        catch(Exception e){
            System.out.println(e);
        }
        
        finally{
            updateTabsCurrentFavourite();
        }
    }//GEN-LAST:event_deleteFavouriteActionPerformed
    
    
    public static void updateTabsCurrentFavourite(){
        TabbedPanel tabPanel1 = MainFrame.getTabPanel1(); 
            ArrayList<Tab> tabs = tabPanel1.getAllTabs();
            for( Tab tab: tabs){
                tab.updateCurrentFavourite();
            }
            try{
                TabbedPanel tabPanel2 = MainFrame.getTabPanel2(); 
                ArrayList<Tab> tabs2 = tabPanel2.getAllTabs();
                for( Tab tab: tabs2){
                    tab.updateCurrentFavourite();
                }
            }           
            catch(NullPointerException e){
                //Layout is in single mode.
            }
    }
    
    private DefaultTreeModel constructTreeModel(){
        //Asssign favourites and folders to an array list
        favourites = database.retrieveFavourites();
        folders = database.retrieveFolders();
       
        DefaultMutableTreeNode root = new DefaultMutableTreeNode( "Favourites" );

        for (FavouriteFolder folder: folders){
            DefaultMutableTreeNode folderNode = new DefaultMutableTreeNode( folder );
            for (Favourite f : favourites){
                if(f.getFolderID() == folder.getID()){
                    //String nodeName = f.getUrl();
                    folderNode.add( new DefaultMutableTreeNode( f ) );
                }
            }
            root.add(folderNode);
        }
        DefaultTreeModel model = new DefaultTreeModel(root); 

        return model;
  } 
    
   public void valueChanged( TreeSelectionEvent event ){
        if( event.getSource() == favouritesTree ){
            path = event.getNewLeadSelectionPath();
            
            if (path != null) {
                node = (DefaultMutableTreeNode) path.getLastPathComponent();
                selection = node.getUserObject();
                if(node.isLeaf()){
                    openFavourite.setEnabled(true);
                    deleteFavourite.setEnabled(true);
                }
                else if( node != favouritesTree.getModel().getRoot() ){
                    openFavourite.setEnabled(false);
                    deleteFavourite.setEnabled(true);
                }
                else{
                    openFavourite.setEnabled(false);
                    deleteFavourite.setEnabled(false);
                }
            }
        }
        
   }
            
    
    //** Netbeans generated variabels
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton deleteFavourite;
    private javax.swing.JTree favouritesTree;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton openFavourite;
    // End of variables declaration//GEN-END:variables

    private void CreateSuit(DefaultMutableTreeNode root, String clubs) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
