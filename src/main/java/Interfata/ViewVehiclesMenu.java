package Interfata;

import Main.Vehicle;
import Main.ViewVehicleButton;

import javax.swing.*;
import java.awt.*;

import static Interfata.Menu.getCadru;
import static Interfata.UserMainMenu.showUserMainMenu;
import static Main.UserAccount.getOwnedVehicles;

public class ViewVehiclesMenu {
    public static JPanel vvMainMenu = null;

    /**
     * Creates the vehicle view menu
     * @author Iulian Sima
     */
    public static void createVVMainMenu() {
        vvMainMenu = new JPanel();
        vvMainMenu.setLayout(new GridLayout(4,1));
        JPanel vvTitle = new JPanel();
        vvTitle.setLayout(new FlowLayout());
        vvTitle.add(new OutlineLabel("Your current vehicles:"));

        JPanel vvVehicles = new JPanel();
        if(!getOwnedVehicles().isEmpty()) {
            vvVehicles.setLayout(new WrapLayout());
            for (Vehicle v : getOwnedVehicles()) {
                StringBuilder vehicleInfo = new StringBuilder();
                vehicleInfo.append(v.getPlate());
                vehicleInfo.append(" - ");
                if(v.getParkingStatus()) {
                    vehicleInfo.append("Parked until ");
                    vehicleInfo.append(v.getParkingSpot().getUntil());
                }
                else vehicleInfo.append("Not parked");
                ViewVehicleButton button = new ViewVehicleButton(v, vehicleInfo.toString());
                vvVehicles.add(button);
            }
        }
        else {
            vvVehicles.setLayout(new FlowLayout());
            vvVehicles.add(new OutlineLabel("You don't have any vehicles."));
        }
        vvMainMenu.add(vvTitle);
        vvMainMenu.add(vvVehicles);

        JPanel vvReturn = new JPanel();
        vvReturn.setLayout(new FlowLayout());
        JButton vvReturnButton = new JButton("Return to main menu");

        vvReturnButton.setBackground(new Color(0x0000ffff));
        vvReturnButton.setForeground(Color.black);
        vvReturnButton.setUI(new StyledButtonUI());
        vvReturn.add(vvReturnButton);
        vvReturnButton.addActionListener(e -> {
            hideVVMainMenu();
            showUserMainMenu(false, "");
        });
        vvMainMenu.add(vvReturn);
        JPanel vvBottomInfo = new JPanel();
        vvBottomInfo.setLayout(new FlowLayout());
        JLabel vvBottomInfoLabel = new JLabel("<html><center><p style='Color: orange;'>Information<br>* Clicking on the vehicle deletes it from the account if it's not parked.<br>If the vehicle it's parked, clicking it will remove it from the parking spot.</p></center></html>");
        vvBottomInfoLabel.setFont(vvBottomInfoLabel.getFont().deriveFont(Font.BOLD));
        vvBottomInfo.add(vvBottomInfoLabel);
        vvMainMenu.add(vvBottomInfo);
        vvMainMenu.setOpaque(false);
        vvTitle.setOpaque(false);
        vvVehicles.setOpaque(false);
        vvReturn.setOpaque(false);
        vvBottomInfo.setOpaque(false);

        getCadru().add(vvMainMenu);
        vvMainMenu.setVisible(false);
    }

    /**
     * shows the Vehicle View menu
     * @author Iulian Sima
     */
    public static void showVVMainMenu() {
        createVVMainMenu();
        vvMainMenu.setVisible(true);
        getCadru().revalidate();
        getCadru().repaint();
    }

    /**
     * hides the vehicle view menu
     * @author Iulian Sima
     *
     */
    public static void hideVVMainMenu() {
        vvMainMenu.setVisible(false);
    }
}
