package Main;

import Interfata.StyledButtonUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static Interfata.ViewVehiclesMenu.hideVVMainMenu;
import static Interfata.ViewVehiclesMenu.showVVMainMenu;
import static Main.Main.connection;
import static Main.ParkingSpot.getParkingSpots;
import static Main.UserAccount.getOwnedVehicles;
import static Main.Vehicle.getAllVehicles;

public class ViewVehicleButton extends JButton implements ActionListener {
    private final Vehicle v;

    /**
     * Constructor of view vehicle button
     * @param id = vehicle id
     * @param text = text on the button
     */
    public ViewVehicleButton(Vehicle id, String text) {
        this.v = id;
        this.setText(text);
        this.addActionListener(this);
        super.setBackground(new Color(0xf5b507));
        super.setForeground(Color.black);
        super.setUI(new StyledButtonUI());
        if(text.contains("Parked until")) {
            super.setPreferredSize(new Dimension(400, 40));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if(!v.getParkingStatus()) {
                PreparedStatement statement = connection.prepareStatement("DELETE FROM vehicles WHERE ID='" + v.getID() + "';");
                statement.execute();
                getOwnedVehicles().remove(v);
                getAllVehicles().remove(v);
                if (v.getParkingStatus()) {
                    for (ParkingSpot p : getParkingSpots()) {
                        if (p.getVehicle() == v) {
                            p.resetSpot();
                            break;
                        }
                    }
                }
                this.setVisible(false);
            }
            else {
                v.getParkingSpot().resetSpot();
                v.setParkingSpot(null);
                v.setParkingStatus(false);
                this.setText(v.getPlate() + " - Not parked");
            }
            hideVVMainMenu();
            showVVMainMenu();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
