package Main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static Main.Main.connection;

public class Zone {
    private final String name;
    private final static ArrayList<Zone> zones = new ArrayList<>();

    private final ArrayList<ParkingSpot> parking_spots = new ArrayList<>();

    /**
     * Constructor of zone class
     * @param nume name of the zone
     * @author Iulian Sima
     */
    Zone(String nume) {
        this.name=nume;
        zones.add(this);
        this.parking_spots.clear();
    }

    /**
     * used to create a zone
     *
     * @param name zone name
     * @author Iulian Sima
     */
    public static void createZone(String name) {
        new Zone(name);
        try {
            PreparedStatement st = connection.prepareStatement("INSERT INTO zones (Name) VALUES (?)");
            st.setString(1, name);
            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * loads the zones
     * @throws SQLException if table not found or connection invalid
     * @author Iulian Sima
     */
    public static void loadZones() throws SQLException {
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM `zones`");
        while(rs.next()){
            new Zone(rs.getString("Name"));
        }
        rs.close();
        st.close();
    }
    /**
     * finds a zone by it's name
     * @param searchName - the name that you search
     * @return the zone if found otherwise null
     * @author Iulian Sima
     */
    public static Zone findZoneByName(String searchName) {
        for(Zone z : zones) if(z.getName().equals(searchName)) return z;
        return null;
    }

    /**
     * adds a parking spot to a zone
     * @param p parking spot
     * @author Iulian Sima
     */
    public void addParkingSpot(ParkingSpot p) {
        this.parking_spots.add(p);
    }

    /**
     * gets the list of parking spots
     * @return the list of parking spots
     * @author Iulian Sima
     */
    public ArrayList<ParkingSpot> getParkingSpots() {
        return this.parking_spots;
    }

    /**
     * gets the zones
     * @return list of zones
     * @author Iulian Sima
     */
    public static ArrayList<Zone> getZones() {
        return zones;
    }
    /**
     *
     * @return the name of the zone
     * @author Iulian Sima
     */
    public String getName() {
        return this.name;
    }
}
