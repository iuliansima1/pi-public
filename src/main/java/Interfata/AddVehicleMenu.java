package Interfata;

import Main.Vehicle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;

import static Interfata.Menu.getCadru;
import static Interfata.UserMainMenu.showUserMainMenu;
import static Main.UserAccount.getOwnedVehicles;
import static Main.Vehicle.addVehicle;
import static Main.Vehicle.getAllVehicles;

public class AddVehicleMenu {
    private static JPanel avMainMenu = null;

    /**
     * creates the add vehicle menu
     * @author Iulian Sima
     *
     *
     */
    private static void createAVMainMenu() {
        avMainMenu = new JPanel();
        avMainMenu.setLayout(new GridLayout(3,1));

        JPanel avTitle = new JPanel();
        avTitle.setLayout(new FlowLayout());
        avTitle.add(new OutlineLabel("Add a vehicle to your account:"));

        avMainMenu.add(avTitle);
        avTitle.setOpaque(false);

        JPanel avAddVehicle = new JPanel();
        avAddVehicle.setLayout(new FlowLayout());
        avAddVehicle.add(new OutlineLabel("Vehicle plate: "));
        JTextField avJTA = new JTextField(24);
        avJTA.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        avAddVehicle.add(avJTA);
        avMainMenu.add(avAddVehicle);
        avAddVehicle.setOpaque(false);

        JPanel avActionButtons = new JPanel();
        avActionButtons.setLayout(new FlowLayout());
        avActionButtons.setOpaque(false);
        avMainMenu.setOpaque(false);
        JButton avAddButton = new JButton("Add vehicle");
        avAddButton.setBackground(new Color(0x0000ffff));
        avAddButton.setForeground(Color.black);
        avAddButton.setUI(new StyledButtonUI());
        avJTA.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    hideAVMenu();
                    if(avJTA.getText().length() < 6) {
                        showUserMainMenu(true, "<p style='Color:red'>! The plate is incorrect.");
                        return;
                    }
                    for(Vehicle v : getOwnedVehicles()) {
                        if(v.getPlate().equals(avJTA.getText())) {
                            showUserMainMenu(true, "<p style='Color:red'>! You already have this car on your account.");
                            return;
                        }
                    }
                    try {
                        for(Vehicle v : getAllVehicles()) {
                            if(v.getPlate().equals(avJTA.getText())) {
                                showUserMainMenu(true, "<p style='Color:red'>! This car is already registered on an account.");
                                return;
                            }
                        }
                        addVehicle(avJTA.getText());
                        showUserMainMenu(true, "<p style='Color:green'>Vehicle has been succesfully added.");
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        avActionButtons.add(avAddButton);
        JButton avReturnButton = new JButton("Main menu");
        avReturnButton.setBackground(new Color(0x0000ffff));
        avReturnButton.setForeground(Color.black);
        avReturnButton.setUI(new StyledButtonUI());
        avActionButtons.add(avReturnButton);

        avMainMenu.add(avActionButtons);
        getCadru().add(avMainMenu);
        avMainMenu.setVisible(false);

        avAddButton.addActionListener(e -> {
            hideAVMenu();
            if(avJTA.getText().length() < 6) {
                showUserMainMenu(true, "<p style='Color:red'>! The plate is incorrect.");
                return;
            }
            for(Vehicle v : getOwnedVehicles()) {
                if(v.getPlate().equals(avJTA.getText())) {
                    showUserMainMenu(true, "<p style='Color:red'>! You already have this car on your account.");
                    return;
                }
            }
            try {
                for(Vehicle v : getAllVehicles()) {
                    if(v.getPlate().equals(avJTA.getText())) {
                        showUserMainMenu(true, "<p style='Color:red'>! This car is already registered on an account.");
                        return;
                    }
                }
                addVehicle(avJTA.getText());
                showUserMainMenu(true, "<p style='Color:green'>Vehicle has been succesfully added.");
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        avReturnButton.addActionListener(e -> {
            hideAVMenu();
            showUserMainMenu(false, "");
        });


    }

    /**
     * shows the add vehicle menu
     * @author Iulian Sima
     *
     */
    public static void showAVMenu() {
        if(avMainMenu == null) createAVMainMenu();
        avMainMenu.setVisible(true);
        getCadru().revalidate();
        getCadru().repaint();
    }

    /**
     * hides the add vehicle menu
     * @author Iulian Sima
     */
    public static void hideAVMenu() {
        avMainMenu.setVisible(false);
    }
}
