package Main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import static Main.Main.connection;

public class ParkingSpot {
    private final int ID;
    private Boolean occupied;
    private Date occupiedUntil;
    private Vehicle occupiedByCar;

    private final static ArrayList<ParkingSpot> allParkingSpots = new ArrayList<>();

    /**
     * loads the parking spots
     * @throws SQLException - if connection is invalid
     * @author Iulian Sima
     */
    public static void loadParkingSpots() throws SQLException {
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM `parkingspots`");
        while(rs.next()) {
            Date ocuUntil;
            int ocuID;
            boolean ocu;
            Zone ocuZone = Zone.findZoneByName(rs.getString("Zone"));
            Vehicle ocuVeh = Vehicle.searchVehicleByID(rs.getInt("OccupiedByCar"));
            ocuUntil = new Date((long)rs.getInt("OccupiedUntil")*1000);
            ocu = rs.getBoolean("Ocuppied");
            ocuID = rs.getInt("ID");
            if (ocuZone != null) {
                new ParkingSpot(ocuID, ocuZone, ocu, ocuUntil, ocuVeh);
            }
        }
    }

    /**
     * Constructor of parkingSpot class
     * @param id - database id of the parking spot
     * @param z - parking spot zone
     * @param oc - parking spot status
     * @param d - ocuppied until the date
     * @param v - vehicle witch occupies the spot
     * @author Iulian Sima
     */
    ParkingSpot(int id, Zone z, Boolean oc, Date d, Vehicle v) {
        this.ID = id;
        this.occupied = oc;
        this.occupiedUntil = d;
        this.occupiedByCar = v;
        this.parkVehicle(v, d);
        z.addParkingSpot(this);
        allParkingSpots.add(this);
    }

    /**
     * used to park a vehicle in the parking spot
     * @param v - vehicle
     * @param until - until when
     */
    public void parkVehicle(Vehicle v, Date until) {
        if(this.occupiedByCar != null) {
            if(!occupiedByCar.getPlate().equals(v.getPlate())) {
                occupiedByCar.setPenalisation(occupiedByCar.getPenalisation()+1);
                try {
                    PreparedStatement st = connection.prepareStatement("INSERT INTO penalisation (Vehicle, Owner, Penalisation) VALUES (?, ?, ?)");
                    st.setString(1, occupiedByCar.getPlate());
                    Statement st_SearchOwnerName = connection.createStatement();
                    ResultSet rs = st_SearchOwnerName.executeQuery("SELECT username FROM accounts WHERE ID=" + occupiedByCar.getOwner() + " LIMIT 1");
                    rs.next();
                    st.setString(2, rs.getString("username"));
                    rs.close();
                    st_SearchOwnerName.close();
                    st.setInt(3, occupiedByCar.getPenalisation());
                    st.executeUpdate();
                    st.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            occupiedByCar.setParkingSpot(null);
            occupiedByCar.setParkingStatus(false);
        }
        this.occupiedByCar = v;
        this.occupied = true;
        this.occupiedUntil = until;
        if(v!= null) {
            v.setParkingStatus(true);
            v.setParkingSpot(this);
        }
    }

    /**
     * used to create a parkingspot outside the package
     * @param id - id
     * @param z - zone
     * @param oc - status
     * @param d - until when
     * @param v - vehicle
     * @author Iulian Sima
     */
    public static void createParkingSpot(int id, Zone z, Boolean oc, Date d, Vehicle v) {
        new ParkingSpot(id, z, oc, d, v);
    }

    /**
     * gets the parking spots internal id
     * @return parking spot id
     * @author Iulian Sima
     */
    public int getID() {
        return this.ID;
    }

    /**
     * gets the parked vehicle
     * @return parked vehicle
     * @author Iulian Sima
     */
    public Vehicle getVehicle() {
        return this.occupiedByCar;
    }

    /**
     * resets the parking spot
     * @throws SQLException if the spot does not exist
     * @author Iulian Sima
     */
    public void resetSpot() throws SQLException {
        this.occupiedByCar = null;
        this.occupied = false;
        this.occupiedUntil = new Date(new Date().getTime()-100000);
        PreparedStatement st = connection.prepareStatement("UPDATE parkingspots SET OccupiedByCar=NULL, Ocuppied=false, OccupiedUntil=? WHERE ID=?");
        st.setLong(1, this.occupiedUntil.getTime()/1000);
        st.setInt(2, this.ID);
        st.executeUpdate();
        st.close();
    }

    /**
     *
     * gets the vector of parking spots
     * @return the vector of parking spots
     * @author Iulian Sima
     */
    public static ArrayList<ParkingSpot> getParkingSpots() {
        return allParkingSpots;
    }

    /**
     * gets the date until a parking spot is occupied
     * @return the date
     *
     * @author Iulian Sima
     */
    public Date getUntil() {
        return this.occupiedUntil;
    }

    /**
     * gets the status of a parking spot
     * @return true if it is occupied, false otherwise
     * @author Iulian Sima
     */
    public Boolean getStatus() {
        this.occupied = this.occupiedUntil.getTime() >= new Date().getTime();
        return this.occupied;
    }
}
