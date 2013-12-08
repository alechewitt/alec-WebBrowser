package browser;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @author Alec Hewitt C0704504 
 */
public class TabbedPanel extends JTabbedPane{


    public ArrayList <Tab> allTabs;
    private String homePage;
    
    
    
    public TabbedPanel(){
       allTabs = new ArrayList<Tab>();
       homePage = "file:///" + System.getProperty("user.dir") + "/src/browser/homepage.html";
       
       Tab tab1 = new Tab(this, homePage);
       Tab tab2 = new Tab(this, homePage);
       
       allTabs.add(tab1);
       allTabs.add(tab2);
              
       this.setPreferredSize(new Dimension(300, 400));      
       this.setMinimumSize(new Dimension(300, 400));      
       
       //this.addTab(tab1.getTitle(), tab1);
       this.addTab(null, tab1);
       this.setTabTitle(tab1);
       
       this.addTab("", tab2);
       this.setOpaque(false);
              
       //Listener for last tab (tab2) being pressed - to create a new tab
       addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                if( getSelectedIndex() == (allTabs.size() -1)){
                    addNewTab();
                }
            }
        });
    }
    
    
    
    public void addNewTab(){
        
        int indexOfTab = allTabs.size() - 1;
        Tab currentTab = allTabs.get(indexOfTab);
        this.setTabTitle(currentTab);
                
        Tab newTab = new Tab(this, homePage);
        this.addTab("", newTab);
        allTabs.add(newTab);
    }
    
    public void openAddressNewTab(String address){
        int indexOfTab = allTabs.size() - 1;
        Tab currentTab = allTabs.get(indexOfTab);
        this.setTitleAt( indexOfTab, currentTab.getTitle() );
        
        Tab newTab = new Tab(this, address);
        this.addTab("", newTab);
        allTabs.add(newTab);
    }
    
    
    public void closeTab(){
        int index = getSelectedIndex();
        remove(index);
        allTabs.remove(index);
        if(index==0){
            this.setSelectedIndex(0);
        }
        else{
            this.setSelectedIndex(index-1);
        }
    }
    
    public void closeTab(Tab aTab, int index){
        int selectedIndex = getSelectedIndex();
        remove(index);
        allTabs.remove(index);
        if(index == selectedIndex && index==0){
            this.setSelectedIndex(0);
        }
        else if( index == selectedIndex){
            this.setSelectedIndex(index-1);
        }
        
    }
    
    public void updateCloseButtons(){
        PopUpMenu popMenu = getSelectedTab().getPopUpMenu();
        
        if ( allTabs.size() <= 2 ){
               //Disable close button on the file menu
                MainFrame.closeTabFile.setEnabled(false);
               //Disable close button on popupmenu
               popMenu.closeTabPopup.setEnabled(false);
               //Disable close button on tab header
               Tab tab1 = allTabs.get(0);
               setTabTitle(tab1);
               
            }
        else if (allTabs.size() == 3){
            //Enable close button on the file menu
            MainFrame.closeTabFile.setEnabled(true);
            //Enable close button on popupmenu
            popMenu.closeTabPopup.setEnabled(true);
            
            //Enable close button on both current tab and previous tab
            Tab tab1 = allTabs.get(0);
            Tab tab2 = allTabs.get(1);
            setTabTitle(tab1);
            setTabTitle(tab2);
        }
        else{
            //Enable close button on the file menu
            MainFrame.closeTabFile.setEnabled(true);
            //Enable close button on popupmenu
            popMenu.closeTabPopup.setEnabled(true);
            //Enable close button on tab header
            setTabTitle(getSelectedTab());
        }
    }
    
    public Tab getSelectedTab(){
        int index = getSelectedIndex();
        Tab selectedTab = allTabs.get(index);
        return selectedTab;
    }
    
    
    public ArrayList getAllTabs(){
        return allTabs;
    }
    
    
    
    public void setTabTitle(Tab aTab){
        int i=0;
        while ( i< allTabs.size() ){
            if ( allTabs.get(i) == aTab){
                break;
            }
            else{
                i++;
            }
        }
        int indexOfTab = i;
        
        if(allTabs.size()<=2){
            this.setTabComponentAt(indexOfTab, null);
            //Set title of first and only title
            this.setTitleAt(0, aTab.getTitle());
           
        }
        else{
            //Add title with close button
            
            ImageIcon closeRed = new ImageIcon(getClass().getResource("/imagesAndfiles/closeTabButton.gif"));
            ImageIcon closeGrey = new ImageIcon(getClass().getResource("/imagesAndfiles/closeTabButtonGrey.gif"));

            FlowLayout layout = new FlowLayout(FlowLayout.CENTER, 5, 0);

            // Make a small JPanel with the layout
            JPanel titlePanel = new JPanel(layout);
            titlePanel.setOpaque(false);

            // Create a Jlabel to be inserted into titlPanel
            JLabel lblTitle = new JLabel(aTab.getTitle());

            // Create a JButton
            JButton closeButton = new JButton();
            closeButton.setOpaque(false);

            // Set rollover icon to closeRed and all other times to closeGrey
            closeButton.setRolloverIcon(closeRed);
            closeButton.setRolloverEnabled(true);
            closeButton.setIcon(closeGrey);

            closeButton.setBorder(null);
            closeButton.setFocusable(false);

            // Add JLabel and button to titlePanel
            titlePanel.add(lblTitle);
            titlePanel.add(closeButton);

            titlePanel.setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));

            this.setTabComponentAt(indexOfTab, titlePanel);

            ActionListener close = new CloseActionListener(this, aTab, indexOfTab);

            closeButton.addActionListener(close); 
        }
        
        this.validate();
        this.repaint();
    }
    
    
    class CloseActionListener implements ActionListener{
        
        private TabbedPanel tabbedPanel;
        private Tab tab;
        private int index;
        
        
        public CloseActionListener(TabbedPanel tabbedPanel, Tab tab, int index){
            this.tabbedPanel = tabbedPanel;
            this.tab = tab;
            this.index = index;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
           tabbedPanel.closeTab(tab, index);
           tabbedPanel.updateCloseButtons();
           MainFrame.setCurrentTabbedPanel(tabbedPanel);
        }
        
    }
    
     
    
    
}
