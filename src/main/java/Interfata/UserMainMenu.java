package Interfata;

import javax.swing.*;
import java.awt.*;

import static Interfata.AddVehicleMenu.showAVMenu;
import static Interfata.AdminPanel.AdminMainMenu.showAdminMainMenu;
import static Interfata.Menu.getCadru;
import static Interfata.SearchParkingMenu.showsbMenu;
import static Interfata.ViewVehiclesMenu.showVVMainMenu;
import static Main.UserAccount.getAdminLevel;
import static Main.UserAccount.getUserName;

public class UserMainMenu {
    private static JPanel userMainPanel = null;
    private static JPanel userSTAPanel = null;

    private static JLabel userTitleLabel = null;

    /**
     * Creates the user main menu
     * @author Iulian Sima
     */
    private static void createUserMainMenu() {

        userMainPanel = new JPanel();
        userMainPanel.setLayout(new GridLayout(6, 1));
        JPanel userTitlePanel = new JPanel();
        userTitlePanel.setLayout(new FlowLayout());
        userTitleLabel = new JLabel("<html><center>Hello, "+ getUserName() +"!<br>User menu<center></html>");
        userTitlePanel.add(userTitleLabel);
        JPanel userSearchPanel = new JPanel();
        userSearchPanel.setLayout(new FlowLayout());
        JButton userSearchButton = new JButton("Search for parking spots");
        userMainPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        userSearchButton.setBackground(new Color(0x0000ffff));
        userSearchButton.setForeground(Color.black);
        userSearchButton.setUI(new StyledButtonUI());
        userSearchPanel.add(userSearchButton);
        userSearchButton.setText("<html> <p style='Color:black;'>Search for parking spots</p></html>");
        userMainPanel.add(userTitlePanel);
        userMainPanel.add(userSearchPanel);

        JPanel userAddCarPanel = new JPanel();
        userAddCarPanel.setLayout(new FlowLayout());
        JButton userAddCarButton = new JButton("Add a vehicle to your account");

        userAddCarButton.setBackground(new Color(0x0000ffff));
        userAddCarButton.setForeground(Color.black);
        userAddCarButton.setUI(new StyledButtonUI());
        userAddCarPanel.add(userAddCarButton);
        userAddCarButton.setText("<html> <p style='Color:black;'>Add a vehicle to your account</p></html>");

        JPanel userViewCarsPanel = new JPanel();
        userViewCarsPanel.setLayout(new FlowLayout());
        JButton userViewCarsButton = new JButton("View your vehicles");
        userViewCarsButton.setBackground(new Color(0x0000ffff));
        userViewCarsButton.setForeground(Color.black);
        userViewCarsButton.setUI(new StyledButtonUI());
        userViewCarsPanel.add(userViewCarsButton);
        userViewCarsButton.setText("<html> <p style='Color:black;'>View your vehicles</p></html>");

        userSTAPanel = new JPanel();
        userSTAPanel.setLayout(new FlowLayout());
        JButton userSTAButton = new JButton("Switch to admin menu");
        userSTAButton.setBackground(new Color(0x0000ffff));
        userSTAButton.setForeground(Color.black);
        userSTAButton.setUI(new StyledButtonUI());
        userSTAPanel.add(userSTAButton);
        userSTAButton.setText("<html> <p style='Color:black;'>Switch to admin panel</p></html>");

        userAddCarPanel.setOpaque(false);
        userSTAPanel.setOpaque(false);
        userTitlePanel.setOpaque(false);
        userViewCarsPanel.setOpaque(false);
        userSearchPanel.setOpaque(false);
        userMainPanel.add(userAddCarPanel);
        userMainPanel.add(userViewCarsPanel);
        userMainPanel.add(userSTAPanel);
        userMainPanel.setOpaque(false);
        getCadru().add(userMainPanel, BorderLayout.CENTER);
        userMainPanel.setVisible(false);
        userSTAPanel.setVisible(false);

        userSearchButton.addActionListener(e -> {
            hideUserMainMenu();
            showsbMenu();
        });

        userAddCarButton.addActionListener(e -> {
            hideUserMainMenu();
            showAVMenu();
        });
        userViewCarsButton.addActionListener(e -> {
            hideUserMainMenu();
            showVVMainMenu();
        });
        userSTAButton.addActionListener(e -> {
            hideUserMainMenu();
            showAdminMainMenu(false, "");
        });
    }

    /**
     * shows the user main menu
     * @param msg - boolean true if you want to show a message
     * @param msg_text - string message
     * @author Iulian Sima
     */
    public static void showUserMainMenu(Boolean msg, String msg_text) {
        if(userMainPanel == null) createUserMainMenu();
        if(msg) {
            userTitleLabel.setText("<html><center>Hello, "+ getUserName() +"!<br>User Menu<br>" + msg_text + "</center></html>");
        }
        else userTitleLabel.setText("<html><center>Hello, "+ getUserName() +"!");
        getCadru().add(userMainPanel);

        userMainPanel.setVisible(true);
        getCadru().revalidate();
        getCadru().repaint();
        if(getAdminLevel() != 0) userSTAPanel.setVisible(true);
    }

    /**
     * hides the user main menu
     * @author Iulian Sima
     */
    public static void hideUserMainMenu() {
        getCadru().remove(userMainPanel);
    }
}

/*
* Admin menu
* Adauga zone
* Adauga parcari in zone
* Citeste reclamatii
* Switch to user
* */
