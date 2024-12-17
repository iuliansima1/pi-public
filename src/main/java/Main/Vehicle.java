package Main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static Main.Main.connection;
import static Main.UserAccount.getOwnedVehicles;
import static Main.UserAccount.getUserID;

public class Vehicle {
    private final int ID;
    private final int owner;
    private final String number;
    private Boolean parkingStatus;
    private int penalisation;
    private ParkingSpot parkingSpot = null;

    private final static ArrayList<Vehicle> allVehicles = new ArrayList<>();

    /**
     * Constructor for vehicles
     * @param id internal database id
     * @param nr registration number
     * @author Iulian Sima
     */
    Vehicle(int id, String nr, int owner, int p) {
        this.ID=id;
        this.number=nr;
        this.owner = owner;
        this.parkingStatus = false;
        this.penalisation = p;
        allVehicles.add(this);
    }

    /**
     * adds a vehicle to the current account
     * @param number - number plate
     * @throws SQLException - if invalid connection
     * @author Iulian Sima
     */
    public static void addVehicle(String number) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO vehicles (Plate, Owner) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, number);
        statement.setInt(2, getUserID());
        statement.executeUpdate();
        ResultSet generatedKeys = statement.getGeneratedKeys();
        if (generatedKeys.next()) {
            int insertId = generatedKeys.getInt(1);
            getOwnedVehicles().add(new Vehicle(insertId, number, getUserID(), 1));
        }
    }

    /**
     * loads all vehicles from database
     * @throws SQLException if connection is invalid
     * @author Iulian Sima
     */
    public static void loadAllVehicles() throws SQLException {
        ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM vehicles WHERE ID>0");
        while(resultSet.next()) {
            new Vehicle(resultSet.getInt("ID"), resultSet.getString("Plate"), resultSet.getInt("Owner"), resultSet.getInt("Pen"));
        }
    }

    /**
     * Gets all loaded vehicles
     * @return the list of vehicles
     * @author Iulian Sima
     */
    public static ArrayList<Vehicle> getAllVehicles() {
        return allVehicles;
    }

    /**
     * search for a vehicle
     * @param searchID vehicle id
     * @return vehicle if found otherwise null
     * @author Iulian Sima
     */
    public static Vehicle searchVehicleByID(int searchID) {
        Vehicle v = null;
        for(Vehicle veh : allVehicles) if(veh.getID() == searchID) {
            v = veh;
            break;
        }
        return v;
    }

    /**
     * search a vehicle by its number plate
     * @param searchPlate plate number
     * @return vehicle if found, null otherwise
     * @author Iulian Sima
     */
    public static Vehicle searchVehicleByPlate(String searchPlate) {
        Vehicle v = null;
        for(Vehicle veh : allVehicles) if(veh.getPlate().equals(searchPlate)) {
            v = veh;
            break;
        }
        return v;
    }

    /**
     * gets the owner of the vehicle
     * @return the owner of the vehicle
     * @author Iulian Sima
     */
    public int getOwner() {
        return this.owner;
    }

    /**
     * gets the id of the vehicle
     * @return the id of the vehicle
     * @author Iulian Sima
     */
    public int getID() {
        return this.ID;
    }

    /**
     * gets the plate of the vehicle
     * @return the plate of the vehicle
     * @author Iulian Sima
     */
    public String getPlate() {
        return this.number;
    }

    /**
     * sets the parking status of the vehicle
     * @param s status true if parked, false otherwise
     * @author Iulian Sima
     */
    public void setParkingStatus(Boolean s) {
        this.parkingStatus = s;
    }

    /**
     * gets the penalisation miltiplier for a vehicle
     *
     * @return the penalisation multiplier
     * @author Iulian Sima
     */
    public int getPenalisation() {
        return this.penalisation;
    }

    /**
     * used to set the penalisation multiplier for a vehicle
     *
     * @param p multiplier 1, 2, 3
     * @author Iulian Sima
     */
    public void setPenalisation(int p) {
        this.penalisation = p;
        try {
            PreparedStatement st = connection.prepareStatement("UPDATE vehicles SET `Pen`=? WHERE ID=?");
            st.setInt(1, p);
            st.setInt(2, this.getID());
            st.executeUpdate();
            st.close();
            if(p == 1) {
                PreparedStatement del = connection.prepareStatement("DELETE FROM penalisation WHERE Vehicle=?");
                del.setInt(1, this.getID());
                del.executeUpdate();
                del.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * used to get the parking status of a vehicle
     * @return true if its parked, false otherwise
     * @author Iulian Sima
     */
    public Boolean getParkingStatus() {
        return this.parkingStatus;
    }
    /**
     * used to set the parking spot of a vehicle
     *
     * @param p parking spot
     * @author Iulian Sima
     */
    public void setParkingSpot(ParkingSpot p) {
        this.parkingSpot = p;
    }

    /**
     * uset to get the current parking spot of a vehicle
     * @return the parking spot if parked, null otherwise
     * @author Iulian Sima
     */
    public ParkingSpot getParkingSpot() {
        return this.parkingSpot;
    }
}