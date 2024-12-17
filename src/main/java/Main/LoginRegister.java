package Main;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static Interfata.LoginMenu.hideLoginMenu;
import static Interfata.LoginRegisterMenu.showLoginRegisterPanel;
import static Interfata.RegisterMenu.hideRegisterMenu;
import static Interfata.UserMainMenu.showUserMainMenu;
import static Main.Main.connection;
import static Main.UserAccount.loadUser;

public class LoginRegister {
    /**
     * Seaching in the database for the username and password match
     * @param username = account username
     * @param password = account password
     * @author Iulian Sima
     */
    public static void loginMenuButtonAction(String username, String password) throws SQLException {


        PreparedStatement pss = connection.prepareStatement("SELECT COUNT(*) as cnt FROM accounts WHERE username=? AND password=?");
        pss.setString(1, username);
        pss.setString(2, password);
        ResultSet rs1 = pss.executeQuery();
        rs1.next();
        if(rs1.getInt("cnt") == 0) {
            rs1.close();
            hideLoginMenu();
            showLoginRegisterPanel(true, "<html> <p style='Color:red'>! The login details are incorrect.</p></html>");
        }
        else {
            hideLoginMenu();
            rs1.close();
            PreparedStatement ps2 = connection.prepareStatement("SELECT ID, Admin FROM accounts WHERE username=? AND password=?");
            ps2.setString(1, username);
            ps2.setString(2, password);
            ResultSet rs2 = ps2.executeQuery();
            rs2.next();
            loadUser(username, rs2.getInt("ID"), rs2.getInt("Admin"));
            rs2.close();
            ps2.close();
            showUserMainMenu(false, "");
        }
        pss.close();
    }

    /**
     * Searching for the account in the database for registration
     * @param username = desired username
     * @param password = desired password
     * @param email = desired email
     * @author Iulian Sima
     */
    public static void registerMenuButtonAction(String username, String password, String email) throws SQLException {
        if(!email.contains("@")) {
            hideRegisterMenu();
            showLoginRegisterPanel(true, "<html> <p style='Color:red'>! Invalid e-mail adress.</p></html>");
            return;
        }
        if(email.length() < 7 || username.length() < 3 || password.length() < 6) {
            hideRegisterMenu();
            showLoginRegisterPanel(true, "<html> <p style='Color:red'>! The email shoud be at least 7 charcters long.<br>! The username has to be at least 3 characters long. <br>! The password has to be at least 6 characters long.</p></html>");
            return;
        }
        PreparedStatement pss = connection.prepareStatement("SELECT COUNT(*) as cnt FROM accounts WHERE username=? OR email=?");
        pss.setString(1, username);
        pss.setString(2, email);
        ResultSet rs1 = pss.executeQuery();
        rs1.next();
        if(rs1.getInt("cnt") != 0) {
            hideRegisterMenu();
            showLoginRegisterPanel(true, "<html> <p style='Color:red'>! Username / e-mail already exists.</p></html>");
        }
        else{
            hideRegisterMenu();
            PreparedStatement st = connection.prepareStatement("INSERT INTO accounts (`Username`,`Password`,`Email`) VALUES (?, ?, ?)");
            st.setString(1, username);
            st.setString(2, password);
            st.setString(3, email);
            st.executeUpdate();
            st.close();

            hideRegisterMenu();
            showLoginRegisterPanel(true, "<html> <p style='Color:green'>Account has been succesfully created.</p></html>");
        }
        rs1.close();
        pss.close();
    }
}
