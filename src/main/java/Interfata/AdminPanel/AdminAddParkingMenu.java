package Interfata.AdminPanel;

import Interfata.OutlineLabel;
import Interfata.StyledButtonUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import static Interfata.AdminPanel.AdminMainMenu.showAdminMainMenu;
import static Interfata.Menu.getCadru;
import static Main.Main.connection;
import static Main.ParkingSpot.createParkingSpot;
import static Main.Zone.findZoneByName;


public class AdminAddParkingMenu {
    private static JPanel admAPMenu = null;

    /**
     *
     * generates the admin add parking spot menu
     * @author Iulian Sima
     */
    private static void createadmAPMenu() {
        admAPMenu = new JPanel();
        admAPMenu.setLayout(new GridLayout(3,1));

        JPanel admAPTitle = new JPanel();
        admAPTitle.setLayout(new FlowLayout());
        admAPTitle.add(new OutlineLabel("Add a new parking spot:"));

        admAPMenu.add(admAPTitle);
        admAPTitle.setOpaque(false);

        JPanel admAPAddVehicle = new JPanel();
        admAPAddVehicle.setLayout(new FlowLayout());
        OutlineLabel admAPJF=new OutlineLabel("Parking zone name: ");
        admAPAddVehicle.add(admAPJF);
        admAPJF.setFont(new Font("Serif", Font.PLAIN, 20));
        JTextField admAPJTA = new JTextField(24);
        admAPJTA.setFont(new Font("Serif", Font.PLAIN, 20));
        admAPJTA.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        admAPAddVehicle.add(admAPJTA);
        admAPMenu.add(admAPAddVehicle);
        admAPAddVehicle.setOpaque(false);

        JPanel admAPActionButtons = new JPanel();
        admAPActionButtons.setLayout(new FlowLayout());
        admAPActionButtons.setOpaque(false);
        admAPMenu.setOpaque(false);
        JButton admAPAddButton = new JButton("Add spot");
        admAPAddButton.setBackground(new Color(0x0000ffff));
        admAPAddButton.setForeground(Color.black);
        admAPAddButton.setUI(new StyledButtonUI());
        admAPActionButtons.add(admAPAddButton);
        JButton admAPReturnButton = new JButton("Main menu");
        admAPReturnButton.setBackground(new Color(0x0000ffff));
        admAPReturnButton.setForeground(Color.black);
        admAPReturnButton.setUI(new StyledButtonUI());
        admAPActionButtons.add(admAPReturnButton);
        admAPJTA.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    hideadmAPMenu();
                    if(admAPJTA.getText().length() < 6) {
                        showAdminMainMenu(true, "<p style='Color:red'>! Invalid zone name.");
                        return;
                    }
                    if(findZoneByName(admAPJTA.getText()) == null) {
                        showAdminMainMenu(true, "<p style='Color:red'>! That zone does not exist.");
                        return;
                    }
                    try {
                        PreparedStatement st = connection.prepareStatement("INSERT INTO parkingspots (Zone) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
                        st.setString(1, admAPJTA.getText());
                        st.executeUpdate();
                        ResultSet generatedKeys = st.getGeneratedKeys();
                        if (generatedKeys.next()) {
                            int insertId = generatedKeys.getInt(1);
                            createParkingSpot(insertId, findZoneByName(admAPJTA.getText()), false, new Date(), null);
                        }
                        st.close();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    showAdminMainMenu(true, "<p style='Color:green'>The parking spot has been succefully added.");
                }
            }
        });
        admAPMenu.add(admAPActionButtons);
        getCadru().add(admAPMenu);
        admAPMenu.setVisible(false);

        admAPAddButton.addActionListener(e -> {
            hideadmAPMenu();
            if(admAPJTA.getText().length() < 6) {
                showAdminMainMenu(true, "<p style='Color:red'>! Invalid zone name.");
                return;
            }
            if(findZoneByName(admAPJTA.getText()) == null) {
                showAdminMainMenu(true, "<p style='Color:red'>! That zone does not exist.");
                return;
            }
            try {
                PreparedStatement st = connection.prepareStatement("INSERT INTO parkingspots (Zone) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
                st.setString(1, admAPJTA.getText());
                st.executeUpdate();
                ResultSet generatedKeys = st.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int insertId = generatedKeys.getInt(1);
                    createParkingSpot(insertId, findZoneByName(admAPJTA.getText()), false, new Date(), null);
                }
                st.close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            showAdminMainMenu(true, "<p style='Color:green'>The parking spot has been succefully added.");
        });
        admAPReturnButton.addActionListener(e -> {
            hideadmAPMenu();
            showAdminMainMenu(false, "");
        });


    }

    /**
     * shows the admin add spot menu
     * @author Iulian Sima
     */
    public static void showadmAPMenu() {
        if(admAPMenu == null) createadmAPMenu();
        admAPMenu.setVisible(true);
        getCadru().revalidate();
        getCadru().repaint();
    }

    /**
     * hides the admin add spot menu
     * @author Iulian Sima
     *
     */
    public static void hideadmAPMenu() {
        admAPMenu.setVisible(false);
    }
}

