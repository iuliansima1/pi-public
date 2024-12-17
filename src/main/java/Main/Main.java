package Main;


import Interfata.Menu;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static Main.ParkingSpot.loadParkingSpots;
import static Main.Vehicle.loadAllVehicles;
import static Main.Zone.loadZones;

public class Main{
    public static Connection connection = null;

    /**
     * Database connection setup
     * @param args args
     */
    public static void main(String[] args) throws SQLException, ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        Class.forName("com.mysql.cj.jdbc.Driver");

        connection = DriverManager.getConnection(
                "jdbc:mysql://gp.ligahosting.ro:3306/s40_simpark",
                "u40_Y3bkbhCt1A", "NaY.Iw5iXD@UQB.My=BXLoAF");
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }));
        loadAllVehicles();
        loadZones();
        loadParkingSpots();
        Menu.createPrimaryFrame();

    }
}