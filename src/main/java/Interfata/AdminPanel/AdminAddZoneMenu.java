package Interfata.AdminPanel;


import Interfata.OutlineLabel;
import Interfata.StyledButtonUI;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static Interfata.AdminPanel.AdminMainMenu.showAdminMainMenu;
import static Interfata.Menu.getCadru;
import static Main.Zone.createZone;
import static Main.Zone.findZoneByName;


public class AdminAddZoneMenu {
    private static JPanel admAZMenu = null;
    /**
     *
     * generates the admin add zone menu
     * @author Iulian Sima
     */
    private static void createadmAZMenu() {
        admAZMenu = new JPanel();
        admAZMenu.setLayout(new GridLayout(3,1));

        JPanel admAZTitle = new JPanel();
        admAZTitle.setLayout(new FlowLayout());
        admAZTitle.add(new OutlineLabel("Add a new parking zone:"));

        admAZMenu.add(admAZTitle);
        admAZTitle.setOpaque(false);

        JPanel admAZAddVehicle = new JPanel();
        admAZAddVehicle.setLayout(new FlowLayout());
        OutlineLabel pzn = new OutlineLabel("Parking zone name: ");
        admAZAddVehicle.add(pzn);
        JTextField admAZJTA = new JTextField(24);
        admAZJTA.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        admAZAddVehicle.add(admAZJTA);
        admAZMenu.add(admAZAddVehicle);
        admAZAddVehicle.setOpaque(false);

        JPanel admAZActionButtons = new JPanel();
        admAZActionButtons.setLayout(new FlowLayout());
        admAZActionButtons.setOpaque(false);
        admAZMenu.setOpaque(false);
        JButton admAZAddButton = new JButton("Add zone");
        admAZAddButton.setBackground(new Color(0x0000ffff));
        admAZAddButton.setForeground(Color.black);
        admAZAddButton.setUI(new StyledButtonUI());
        admAZActionButtons.add(admAZAddButton);
        JButton admAZReturnButton = new JButton("Main menu");
        admAZReturnButton.setBackground(new Color(0x0000ffff));
        admAZReturnButton.setForeground(Color.black);
        admAZReturnButton.setUI(new StyledButtonUI());
        admAZActionButtons.add(admAZReturnButton);
        admAZJTA.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                    hideadmAZMenu();
                    if(admAZJTA.getText().length() < 6) {
                        showAdminMainMenu(true, "<p style='Color:red'>! Numele zonei este invalid.");
                        return;
                    }
                    if(findZoneByName(admAZJTA.getText()) != null) {
                        showAdminMainMenu(true, "<p style='Color:red'>! Aceasta zona exista deja.");
                        return;
                    }
                    createZone(admAZJTA.getText());
                    showAdminMainMenu(true, "<p style='Color:green'>Zona a fost adaugata cu succes.");
                }
            }
        });

        admAZMenu.add(admAZActionButtons);
        getCadru().add(admAZMenu);
        admAZMenu.setVisible(false);

        admAZAddButton.addActionListener(e -> {
            hideadmAZMenu();
            if(admAZJTA.getText().length() < 6) {
                showAdminMainMenu(true, "<p style='Color:red'>! Numele zonei este invalid.");
                return;
            }
            if(findZoneByName(admAZJTA.getText()) != null) {
                showAdminMainMenu(true, "<p style='Color:red'>! Aceasta zona exista deja.");
                return;
            }
            createZone(admAZJTA.getText());
            showAdminMainMenu(true, "<p style='Color:green'>Zona a fost adaugata cu succes.");
        });
        admAZReturnButton.addActionListener(e -> {
            hideadmAZMenu();
            showAdminMainMenu(false, "");
        });


    }

    /**
     * shows the admin add zone menu
     * @author Iulian Sima
     */
    public static void showadmAZMenu() {
        if(admAZMenu == null) createadmAZMenu();
        admAZMenu.setVisible(true);
        getCadru().revalidate();
        getCadru().repaint();
    }

    /**
     * hides the admin add zone menu
     * @author Iulian Sima
     */
    public static void hideadmAZMenu() {
        admAZMenu.setVisible(false);
    }
}
