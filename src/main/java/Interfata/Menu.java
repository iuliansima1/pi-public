package Interfata;
import javax.swing.*;

import java.awt.*;

import static Interfata.LoginRegisterMenu.createLoginRegisterPanel;
import static Interfata.RegisterMenu.createRegisterMenu;

public class Menu {
    private static JFrame cadru;
    /**
     *
     * Shows / creates the primary registration menu.
     * @author Iulian Sima
     */
    public static JFrame getCadru() {
        return cadru;
    }
    public static void createPrimaryFrame() {
        cadru = new JFrame("SimPARK");
        cadru.setSize(1080,720);
        cadru.setVisible(true);
        ImageIcon img = new ImageIcon("src/main/java/Interfata/simpark.png");
        cadru.setIconImage(img.getImage());
        cadru.setLayout(new BorderLayout());
        cadru.setContentPane(new JLabel(new ImageIcon("src/main/java/Interfata/simpark_bg.jpg")));
        cadru.setLayout(new BorderLayout());
        getCadru().setLocationRelativeTo(null);
        createLoginRegisterPanel();
        createRegisterMenu();
        cadru.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
