package Interfata;

import org.apache.commons.codec.digest.DigestUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;

import static Interfata.Menu.getCadru;
import static Main.LoginRegister.loginMenuButtonAction;

public class LoginMenu {
    private static JPanel lMainGrid = null;
    private static JTextField taUsername;
    private static JPasswordField taPassword;

    /**
     * Creates the login menu
     * @author Iulian Sima
     *
     */
    public static void createLoginMenu() {
        lMainGrid = new JPanel();
        lMainGrid.setLayout(new GridLayout(4,1));
        JPanel topText = new JPanel();
        topText.setLayout(new FlowLayout());
        OutlineLabel topTextLabel = new OutlineLabel("Login to your account");
        topText.add(topTextLabel);
        topTextLabel.setFont(new Font("Serif", Font.BOLD, 16));
        lMainGrid.add(topText);
        JPanel lUsernamePanel = new JPanel();
        lUsernamePanel.setLayout(new FlowLayout());
        JLabel lUsernameLabel = new OutlineLabel("Username: ");
        lUsernameLabel.setFont(new Font("Serif", Font.PLAIN, 20));

        taUsername = new JTextField(24);
        taUsername.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        taUsername.setFont(new Font("Serif", Font.PLAIN, 20));
        taPassword = new JPasswordField("", 24);
        taPassword.setFont(new Font("Serif", Font.PLAIN, 20));
        JButton loginButton = new JButton("Login");

        taUsername.addActionListener(e -> taPassword.requestFocusInWindow());

        lUsernamePanel.add(lUsernameLabel);
        lUsernamePanel.add(taUsername);
        lMainGrid.add(lUsernamePanel);
        JPanel lPasswordPanel = new JPanel();
        lPasswordPanel.setLayout(new FlowLayout());
        JLabel lPasswordLabel = new OutlineLabel("Password: ");

        lPasswordLabel.setFont(new Font("Serif", Font.PLAIN, 20));

        lPasswordPanel.add(lPasswordLabel);
        lPasswordPanel.add(taPassword);
        lMainGrid.add(lPasswordPanel);
        JPanel bottomLoginButtonPanel = new JPanel();
        bottomLoginButtonPanel.setLayout(new FlowLayout());

        loginButton.setBackground(new Color(0x0000ffff));
        loginButton.setForeground(Color.black);
        loginButton.setUI(new StyledButtonUI());
        bottomLoginButtonPanel.add(loginButton);
        lMainGrid.add(bottomLoginButtonPanel);
        bottomLoginButtonPanel.setOpaque(false);
        lPasswordPanel.setOpaque(false);
        lUsernamePanel.setOpaque(false);
        topText.setOpaque(false);
        lMainGrid.setOpaque(false);
        getCadru().add(lMainGrid);
        lMainGrid.setVisible(false);

        taPassword.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    try {
                        String shahex = DigestUtils.sha256Hex(new String(taPassword.getPassword()));
                        loginMenuButtonAction(taUsername.getText(), shahex);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

                }
            }
        });

        loginButton.addActionListener(e -> {
            try {
                String shahex = DigestUtils.sha256Hex(new String(taPassword.getPassword()));
                loginMenuButtonAction(taUsername.getText(), shahex);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    /**
     * gets the login main panel
     * @return the login main panel
     * @author Iulian Sima
     */
    public static JPanel getlPanel() {
        return  lMainGrid;
    }

    /**
     * shows the login menu
     * @author Iulian Sima
     */
    public static void showLoginMenu() {
        lMainGrid.setVisible(true);
        taPassword.setText("");
        getCadru().revalidate();
        getCadru().repaint();
        getCadru().pack();

    }

    /**
     * hides the login menu
     * @author Iulian Sima
     */
    public static void hideLoginMenu() {
        lMainGrid.setVisible(false);
    }

}
