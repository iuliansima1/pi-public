package Interfata;

import javax.swing.*;
import java.awt.*;

import static Interfata.LoginMenu.*;
import static Interfata.Menu.getCadru;
import static Interfata.RegisterMenu.createRegisterMenu;
import static Interfata.RegisterMenu.showRegisterMenu;

public class LoginRegisterMenu {
    private static JPanel lrMainPanel;
    private static JLabel errorLabel;

    /**
     * Creates the login and register panel
     *
     * @author Iulian Sima
     */
    public static void createLoginRegisterPanel() {
        lrMainPanel = new JPanel();
        GridLayout layout = new GridLayout(3,1);
        layout.setHgap(10);
        lrMainPanel.setLayout(layout);

        JPanel lrTopPanel = new JPanel();
        lrTopPanel.setLayout(new FlowLayout());
        lrTopPanel.add(new OutlineLabel("Menu"));
        lrMainPanel.add(lrTopPanel);
        JPanel lrMidPanel = new JPanel();
        lrMidPanel.setLayout(new FlowLayout());
        JButton register = new JButton("Register");
        JButton login = new JButton("Log in");
        register.setBackground(new Color(0x0000ffff));
        register.setForeground(Color.black);
        register.setUI(new StyledButtonUI());
        login.setBackground(new Color(0x0000ffff));
        login.setForeground(Color.black);
        login.setUI(new StyledButtonUI());
        lrMidPanel.add(register);
        lrMidPanel.add(login);
        lrMainPanel.add(lrMidPanel);
        lrMainPanel.setOpaque(false);
        lrMidPanel.setOpaque(false);
        lrTopPanel.setOpaque(false);
        JPanel lrBottomPanel = new JPanel();
        lrBottomPanel.setLayout(new FlowLayout());
        lrBottomPanel.setOpaque(false);
        errorLabel = new JLabel();
        errorLabel.setText("TEST");
        lrBottomPanel.add(errorLabel);
        lrMainPanel.add(lrBottomPanel);
        lrMainPanel.setOpaque(false);
        getCadru().add(lrMainPanel);
        showLoginRegisterPanel(false, "");

        login.addActionListener(e -> {
            if(getlPanel() == null)
                createLoginMenu();
            hideLoginRegisterPanel();
            showLoginMenu();
        });
        register.addActionListener(e -> {
            if(getlPanel() == null)
                createRegisterMenu();
            hideLoginRegisterPanel();
            showRegisterMenu();
        });
    }

    /**
     * shows the login register panel
     * @param error = true if you want to show a message on the panel, false otherwise
     * @param err_text = the message
     * @author Iulian Sima
     */
    public static void showLoginRegisterPanel(Boolean error, String err_text) {
        lrMainPanel.setVisible(true);

        if(error) {
            errorLabel.setVisible(true);
            errorLabel.setText(err_text);
        }
        else errorLabel.setVisible(false);
        getCadru().revalidate();
        getCadru().repaint();
        getCadru().pack();
    }

    /**
     * hides the login register menu
     * @author Iulian Sima
     */
    public static void hideLoginRegisterPanel() {
        lrMainPanel.setVisible(false);
    }
}
