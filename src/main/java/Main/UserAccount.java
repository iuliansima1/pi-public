package Main;


import java.util.ArrayList;

import static Main.Vehicle.getAllVehicles;

public class UserAccount {
    private static String userName;
    private static int userID;
    private static int adminLevel;

    private final static ArrayList<Vehicle> ownedVehicles = new ArrayList<>();

    /**
     * Used to load the current user account
     * @param user string username
     * @param userIDd integer userID
     * @param admin integer admin level
     * @author Iulian Sima
     */
    public static void loadUser(String user, int userIDd, int admin){
        userName = user;
        userID = userIDd;
        adminLevel = admin;
        for (Vehicle v : getAllVehicles()) {
            if(v.getOwner() == userID) ownedVehicles.add(v);
        }
    }

    /**
     * get current account username
     * @return the account username
     * @author Iulian Sima
     */
    public static String getUserName() {
        return userName;
    }

    /**
     * gets the owned vehicles
     * @return the vector of owned vehicles
     * @author Iulian Sima
     */
    public static ArrayList<Vehicle> getOwnedVehicles() {
        return ownedVehicles;
    }

    /**
     * gets the user id
     * @return the user id
     * @author Iulian Sima
     */
    public static int getUserID() {
        return userID;
    }

    /**
     * gets the admin level
     * @return the admin level
     * @author Iulian Sima
     */
    public static int getAdminLevel() {
        return adminLevel;
    }

}
