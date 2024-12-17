package Interfata;

import org.apache.commons.codec.digest.DigestUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;

import static Interfata.Menu.getCadru;
import static Main.LoginRegister.registerMenuButtonAction;

public class RegisterMenu {
    private static JPanel rMainGrid = null;
    private static JTextField tarUsername;
    private static JTextField tarEmail;

    /**
     * Creates the registration menu
     *
     * @author Iulian Sima
     */
    public static void createRegisterMenu() {
        rMainGrid = new JPanel();
        rMainGrid.setLayout(new GridLayout(5,1));
        JPanel topText = new JPanel();
        topText.setLayout(new FlowLayout());
        topText.add(new JLabel("Register a new account"));
        rMainGrid.add(topText);
        JPanel rUsernamePanel = new JPanel();
        rUsernamePanel.setLayout(new FlowLayout());
        JLabel rUsernameLabel = new OutlineLabel("Username: ");

        JPasswordField tarPassword = new JPasswordField(24);

        tarUsername = new JTextField(24);
        tarUsername.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        tarUsername.requestFocusInWindow();
        tarUsername.addActionListener(e->tarPassword.requestFocusInWindow());
        tarPassword.addActionListener(e->tarEmail.requestFocusInWindow());
        rUsernamePanel.add(rUsernameLabel);
        rUsernamePanel.add(tarUsername);
        rMainGrid.add(rUsernamePanel);
        JPanel rPasswordPanel = new JPanel();
        rPasswordPanel.setLayout(new FlowLayout());
        JLabel rPasswordLabel = new OutlineLabel("Password: ");
        rPasswordPanel.add(rPasswordLabel);
        rPasswordPanel.add(tarPassword);
        rMainGrid.add(rPasswordPanel);
        JPanel rEmailPanel = new JPanel();
        rEmailPanel.setLayout(new FlowLayout());
        JLabel rEmailLabel = new OutlineLabel("Email: ");
        tarEmail = new JTextField(24);
        tarEmail.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        rEmailPanel.add(rEmailLabel);
        rEmailPanel.add(tarEmail);
        rMainGrid.add(rEmailPanel);
        JPanel bottomRegisterButtonPanel = new JPanel();
        bottomRegisterButtonPanel.setLayout(new FlowLayout());
        JButton registerButton = new JButton("Register");
        registerButton.setBackground(new Color(0x0000ffff));
        tarEmail.addActionListener(e->registerButton.requestFocus());
        registerButton.setForeground(Color.black);
        registerButton.setUI(new StyledButtonUI());

        tarEmail.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    try {
                        String sha256hex = DigestUtils.sha256Hex(String.valueOf(tarPassword.getPassword()));
                        registerMenuButtonAction(tarUsername.getText(), sha256hex, tarEmail.getText());
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        registerButton.addActionListener(e -> {
            try {
                String sha256hex = DigestUtils.sha256Hex(String.valueOf(tarPassword.getPassword()));
                registerMenuButtonAction(tarUsername.getText(), sha256hex, tarEmail.getText());
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        bottomRegisterButtonPanel.add(registerButton);
        rMainGrid.add(bottomRegisterButtonPanel);
        bottomRegisterButtonPanel.setOpaque(false);
        rPasswordPanel.setOpaque(false);
        rUsernamePanel.setOpaque(false);
        rEmailPanel.setOpaque(false);
        topText.setOpaque(false);
        rMainGrid.setOpaque(false);

        getCadru().add(rMainGrid);

        rMainGrid.setVisible(false);
    }

    /**
     * shows the register menu
     * @author Iulian Sima
     */
    public static void showRegisterMenu() {
        createRegisterMenu();
        rMainGrid.setVisible(true);
        getCadru().revalidate();
        getCadru().repaint();
        getCadru().pack();
    }

    /**
     * hides the register menu
     * @author Iulian Sima
     */
    public static void hideRegisterMenu() {
        rMainGrid.setVisible(false);
    }
}
