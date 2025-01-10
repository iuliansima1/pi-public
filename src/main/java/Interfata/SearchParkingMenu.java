package Interfata;

import Main.ParkingSpot;
import Main.Vehicle;
import Main.Zone;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import static Interfata.Menu.getCadru;
import static Interfata.UserMainMenu.showUserMainMenu;
import static Main.Main.connection;
import static Main.UserAccount.getOwnedVehicles;
import static Main.Vehicle.searchVehicleByPlate;
import static Main.Zone.*;

public class SearchParkingMenu {
    /**
     * verifies if a string is numeric
     * @param strNum - string to be verified
     * @return true if is a number, false otherwise
     */
    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return true;
        }
        try {
           Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return true;
        }
        return false;
    }
    private static JPanel sbMainMenu = null;

    /**
     *
     * Creates the shearch for parking menu
     * @author Iulian Sima
     *
     */
    private static void createsbMainMenu() {
        sbMainMenu = new JPanel();
        sbMainMenu.setLayout(new GridLayout(7,1));

        JPanel sbTitle = new JPanel();
        sbTitle.setLayout(new FlowLayout());
        sbTitle.add(new OutlineLabel("Search for parking:"));

        sbMainMenu.add(sbTitle);
        sbTitle.setOpaque(false);

        JPanel sbAvailableZonesPanel = new JPanel();
        sbAvailableZonesPanel.setLayout(new WrapLayout());
        JLabel sbAvailableZonesLabel = new JLabel("");
        sbAvailableZonesLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        StringBuilder zones_text = new StringBuilder();
        if(getZones().isEmpty())zones_text.append("<html><center>Available zones for parking: <p style='Color:red'>none</p></center></html>");
        else {
            zones_text.append("<html><center>Available zones for parking: <br>");
            for(Zone z : getZones()) {
                boolean available = false;
                if(z.getParkingSpots() != null) {
                    for (ParkingSpot p : z.getParkingSpots()) {
                        if (!p.getStatus()) {
                            available = true;
                            break;
                        }
                    }
                }
                if(available) zones_text.append("<cite style='Color:green;font-style: bold;'>");
                else zones_text.append("<cite style='Color:red'>");
                zones_text.append(z.getName());
                zones_text.append(" ");
                zones_text.append("</cite>");
            }
            zones_text.append("</center></html>");
            sbAvailableZonesLabel.setText(zones_text.toString());
        }
        sbAvailableZonesLabel.setBackground(Color.cyan);
        sbAvailableZonesLabel.setOpaque(true);
        sbAvailableZonesPanel.add(sbAvailableZonesLabel);
        sbAvailableZonesPanel.setOpaque(false);
        sbMainMenu.add(sbAvailableZonesPanel);

        JPanel sbChooseZonePanel = new JPanel();
        sbChooseZonePanel.setLayout(new FlowLayout());
        sbChooseZonePanel.setOpaque(false);
        JLabel sbChooseZoneLabel = new OutlineLabel("Name of the zone: ");
        sbChooseZoneLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        sbChooseZonePanel.add(sbChooseZoneLabel);
        JTextField sbChooseZoneText = new JTextField(10);
        sbChooseZoneText.setFont(new Font("Serif", Font.PLAIN, 20));
        sbChooseZoneText.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        sbChooseZonePanel.add(sbChooseZoneText);
        sbMainMenu.add(sbChooseZonePanel);

        JPanel sbAvailableCarsPanel = new JPanel();
        sbAvailableCarsPanel.setLayout(new WrapLayout());
        JLabel sbAvailableCarsLabel = new JLabel("");
        sbAvailableCarsLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        if(getOwnedVehicles() == null) sbAvailableCarsLabel.setText("<html><center>Available cars for parking: <p style='Color:red'>none</p></center></html>");
        else {
            boolean carNorParked = false;
            for (Vehicle v : getOwnedVehicles()) {
                if (!v.getParkingStatus()) {
                    carNorParked = true;
                    break;
                }
            }
            if (!carNorParked)
                sbAvailableCarsLabel.setText("<html><center>Available cars for parking: <p style='Color:red'>none</p></center></html>");
            else  {
                StringBuilder cars_text = new StringBuilder();
                cars_text.append("Available cars for parking:");
                for (Vehicle v : getOwnedVehicles()) {
                    if (!v.getParkingStatus()) {
                        cars_text.append(v.getPlate());
                        cars_text.append(", ");
                    }
                }
                sbAvailableCarsLabel.setText(cars_text.toString());
            }
        }
        sbAvailableCarsLabel.setBackground(Color.cyan);
        sbAvailableCarsLabel.setOpaque(true);
        sbAvailableCarsPanel.add(sbAvailableCarsLabel);
        sbAvailableCarsPanel.setOpaque(false);
        sbMainMenu.add(sbAvailableCarsPanel);

        JPanel sbChooseCarPanel = new JPanel();
        sbChooseCarPanel.setLayout(new FlowLayout());
        sbChooseCarPanel.setOpaque(false);
        JLabel sbChooseCarLabel = new OutlineLabel("Plate of the car: ");
        sbChooseCarLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        sbChooseCarPanel.add(sbChooseCarLabel);
        JTextField sbChooseCarText = new JTextField(10);
        sbChooseCarText.setFont(new Font("Serif", Font.PLAIN, 20));
        sbChooseCarText.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        sbChooseCarPanel.add(sbChooseCarText);
        sbChooseZoneText.addActionListener(e->sbChooseCarText.requestFocusInWindow());
        sbMainMenu.add(sbChooseCarPanel);

        JPanel sbChooseHourPanel = new JPanel();
        sbChooseHourPanel.setLayout(new FlowLayout());
        sbChooseHourPanel.setOpaque(false);
        JLabel sbChooseHourLabel = new OutlineLabel("How many hours to park?: ");
        sbChooseHourLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        sbChooseHourPanel.add(sbChooseHourLabel);
        JTextField sbChooseHourText = new JTextField(10);
        sbChooseHourText.setFont(new Font("Serif", Font.PLAIN, 20));
        sbChooseCarText.addActionListener(e->sbChooseHourText.requestFocusInWindow());
        sbChooseHourText.setBorder(BorderFactory.createLineBorder(Color.black, 1));

        sbChooseHourPanel.add(sbChooseHourText);
        sbMainMenu.add(sbChooseHourPanel);

        JPanel sbActionButtonsPanel = new JPanel();
        sbActionButtonsPanel.setLayout(new FlowLayout());
        sbActionButtonsPanel.setOpaque(false);
        JButton sbParkButton = new JButton("Park car");
        sbParkButton.setBackground(new Color(0x0000ffff));
        sbParkButton.setForeground(Color.black);
        sbParkButton.setUI(new StyledButtonUI());
        sbActionButtonsPanel.add(sbParkButton);

        sbChooseHourText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    SearchParkingAction(sbChooseZoneText, sbChooseCarText, sbChooseHourText);
                }
            }
        });

        JButton sbMainMenuButton = new JButton("Main menu");
        sbMainMenuButton.setBackground(new Color(0x0000ffff));
        sbMainMenuButton.setForeground(Color.black);
        sbMainMenuButton.setUI(new StyledButtonUI());
        sbActionButtonsPanel.add(sbMainMenuButton);
        sbMainMenu.add(sbActionButtonsPanel);

        sbParkButton.addActionListener(e -> SearchParkingAction(sbChooseZoneText, sbChooseCarText, sbChooseHourText));

        sbMainMenuButton.addActionListener(e->{
            hidesbMenu();
            showUserMainMenu(false, "");
        });
        sbMainMenu.setOpaque(false);
        getCadru().add(sbMainMenu);
        getCadru().revalidate();
        getCadru().repaint();
        sbMainMenu.setVisible(false);

    }

    private static void SearchParkingAction(JTextField sbChooseZoneText, JTextField sbChooseCarText, JTextField sbChooseHourText) {
        hidesbMenu();
        if(sbChooseZoneText.getText().length() < 4 || findZoneByName(sbChooseZoneText.getText()) == null) {
            showUserMainMenu(true, "<p style='Color:red'>! The parking zone is invalid. </p>");
            return;
        }
        ParkingSpot available = null;
        Zone z = findZoneByName(sbChooseZoneText.getText());
        if (z != null && z.getParkingSpots() != null) {
            for (ParkingSpot p : z.getParkingSpots()) {
                if (!p.getStatus()) {
                    available = p;
                    break;
                }
            }
        }
        if(available == null) {
            showUserMainMenu(true, "<p style='Color:red'>! The spot has been occupied</p>");
            return;
        }
        Vehicle carToPark = searchVehicleByPlate(sbChooseCarText.getText());
        if(carToPark == null || carToPark.getParkingStatus()) {
            showUserMainMenu(true, "<p style='Color:red'>! Invalid vehicle plate</p>");
            return;
        }
        if(isNumeric(sbChooseHourText.getText())) {
            showUserMainMenu(true, "<p style='Color:red'>! Invalid time</p>");
            return;
        }
        Date until = new Date();
        until =new Date(until.getTime()+ (long)Integer.parseInt(sbChooseHourText.getText())*3600*1000);
        available.parkVehicle(carToPark, until);
        PreparedStatement st;
        try {
            st = connection.prepareStatement("UPDATE parkingspots SET Ocuppied=?, OccupiedByCar=?,OccupiedUntil=? WHERE ID=?");
            st.setBoolean(1, true);
            st.setInt(2, carToPark.getID());
            st.setLong(3, until.getTime()/1000);
            st.setInt(4, available.getID());
            st.executeUpdate();
            st.close();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        showUserMainMenu(true, "<p style='Color: green'>You can park your vehicle on parking spot #" + available.getID() + " for the next " + sbChooseHourText.getText() + " hours.<br>You have been charged $"+ Integer.parseInt(sbChooseHourText.getText())*carToPark.getPenalisation() + ".");
        carToPark.setPenalisation(1);
    }

    /**
     * Shows the search for parking menu
     * @author Iulian Sima
     */
    public static void showsbMenu() {
        if(sbMainMenu == null) createsbMainMenu();
        sbMainMenu.setVisible(true);
        getCadru().revalidate();
        getCadru().repaint();
    }

    /**
     * Hides the search for parking menu
     * @author Iulian Sima
     *
     */
    public static void hidesbMenu() {
        sbMainMenu.setVisible(false);
        sbMainMenu = null;
    }
}
