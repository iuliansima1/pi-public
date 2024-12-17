package Interfata.AdminPanel;

import Interfata.StyledButtonUI;

import javax.swing.*;
import java.awt.*;

import static Interfata.AdminPanel.AdminAddParkingMenu.showadmAPMenu;
import static Interfata.AdminPanel.AdminAddZoneMenu.showadmAZMenu;
import static Interfata.AdminPanel.PenalisationMenu.showPenalisationMenu;
import static Interfata.Menu.getCadru;
import static Interfata.UserMainMenu.showUserMainMenu;
import static Main.UserAccount.getUserName;

public class AdminMainMenu {

    private static JPanel adminMainPanel = null;
    private static JLabel adminTitleLabel = null;

    /**
     * Creates the admin main menu
     * @author Iulian Sima
     */
    private static void createAdminMainMenu() {
        adminMainPanel = new JPanel();
        adminMainPanel.setLayout(new GridLayout(5, 1));
        JPanel adminTitlePanel = new JPanel();
        adminTitlePanel.setLayout(new FlowLayout());
        adminTitleLabel = new JLabel("<html><center>Hello, "+ getUserName() +"!<br>Admin menu<center></html>");
        adminTitlePanel.add(adminTitleLabel);
        adminMainPanel.add(adminTitlePanel);
        adminTitlePanel.setOpaque(false);

        JPanel adminAddZonePanel = new JPanel();
        adminAddZonePanel.setLayout(new FlowLayout());
        JButton adminAddZoneMainButton = new JButton("<html> <p style='Color:black;'>Add a new parking zone</p></html>");

        adminAddZoneMainButton.setBackground(new Color(0x0000ffff));
        adminAddZoneMainButton.setForeground(Color.black);
        adminAddZoneMainButton.setUI(new StyledButtonUI());
        adminAddZonePanel.add(adminAddZoneMainButton);
        adminAddZonePanel.setOpaque(false);
        adminMainPanel.add(adminAddZonePanel);

        JPanel adminAddParkingPanel = new JPanel();
        adminAddParkingPanel.setLayout(new FlowLayout());
        JButton adminAddParkingButton = new JButton("<html> <p style='Color:black;'>Add a new parking spot</p></html>");
        adminAddParkingButton.setBackground(new Color(0x0000ffff));
        adminAddParkingButton.setForeground(Color.black);
        adminAddParkingButton.setUI(new StyledButtonUI());
        adminAddParkingPanel.add(adminAddParkingButton);
        adminAddParkingPanel.setOpaque(false);
        adminMainPanel.add(adminAddParkingPanel);

        JPanel adminReadComplaints = new JPanel();
        adminReadComplaints.setLayout(new FlowLayout());
        JButton adminReadComplaintsButton = new JButton("<html> <p style='Color:black;'>Penalisations</p></html>");
        adminReadComplaintsButton.setBackground(new Color(0x0000ffff));
        adminReadComplaintsButton.setForeground(Color.black);
        adminReadComplaintsButton.setUI(new StyledButtonUI());
        adminReadComplaints.add(adminReadComplaintsButton);
        adminReadComplaints.setOpaque(false);
        adminMainPanel.add(adminReadComplaints);

        JPanel adminSwitchToUserPanel = new JPanel();
        adminSwitchToUserPanel.setLayout(new FlowLayout());
        JButton adminSwitchToUserButton = new JButton("<html> <p style='Color:black;'>Switch to user menu</p></html>");
        adminSwitchToUserButton.setBackground(new Color(0x0000ffff));
        adminSwitchToUserButton.setForeground(Color.black);
        adminSwitchToUserButton.setUI(new StyledButtonUI());
        adminSwitchToUserPanel.add(adminSwitchToUserButton);
        adminSwitchToUserPanel.setOpaque(false);
        adminMainPanel.add(adminSwitchToUserPanel);

        adminAddZoneMainButton.addActionListener(e->{
            hideAdminMainMenu();
            showadmAZMenu();
        });
        adminAddParkingButton.addActionListener(e->{
            hideAdminMainMenu();
            showadmAPMenu();
        });
        adminReadComplaintsButton.addActionListener(e->{
            hideAdminMainMenu();
            showPenalisationMenu();
        });
        adminSwitchToUserButton.addActionListener(e->{
            hideAdminMainMenu();
            showUserMainMenu(false, "");
        });
        adminMainPanel.setOpaque(false);
        getCadru().add(adminMainPanel);
        adminMainPanel.setVisible(false);
    }

    /**
     * shows the admin main menu
     * @param msg - boolean true if you want to show a message
     * @param msg_text - string message
     * @author Iulian Sima
     */
    public static void showAdminMainMenu(Boolean msg, String msg_text) {

        if(adminMainPanel == null) createAdminMainMenu();
        if(msg) {
            adminTitleLabel.setText("<html><center>Hello, "+ getUserName() +"!<br>Admin Menu<br>" + msg_text + "</center></html>");
        }
        else adminTitleLabel.setText("<html><center>Hello, "+ getUserName() +"!");
        getCadru().add(adminMainPanel);
        adminMainPanel.setVisible(true);
        getCadru().revalidate();
        getCadru().repaint();
    }

    /**
     * hides the admin menu
     * @author Iulian Sima
     */
    public static void hideAdminMainMenu() {
        adminMainPanel.setVisible(false);
        getCadru().remove(adminMainPanel);
    }
}


