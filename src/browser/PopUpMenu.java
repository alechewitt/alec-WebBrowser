package browser;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 * @author Alec Hewitt C0704504
 */
public class PopUpMenu extends JPopupMenu {
    private JMenuItem saveItem;
    public JMenuItem closeTabPopup;
    private TabbedPanel currentTabbedPanel;   
        
    public PopUpMenu(){
        
        saveItem = new JMenuItem("Save Page");
        ActionListener saveListener = new SaveActionListener();
        saveItem.addActionListener(saveListener);
        add(saveItem);
        
        closeTabPopup = new JMenuItem("Close Tab");
        ActionListener closeListener = new CloseActionListener();
        closeTabPopup.addActionListener(closeListener);
        add(closeTabPopup);
    }
    
    
    
    
    class SaveActionListener implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {
            currentTabbedPanel = MainFrame.getCurrentTabbedPanel();
            SavePage saveCurrent = new SavePage(currentTabbedPanel); 
        }
    }
    
    class CloseActionListener implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {
            currentTabbedPanel = MainFrame.getCurrentTabbedPanel();
            
            currentTabbedPanel.closeTab();
            currentTabbedPanel.updateCloseButtons();
            
        }
    }
}

