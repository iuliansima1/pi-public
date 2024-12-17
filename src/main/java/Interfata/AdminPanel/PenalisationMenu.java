package Interfata.AdminPanel;

import Interfata.StyledButtonUI;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static Interfata.AdminPanel.AdminMainMenu.showAdminMainMenu;
import static Interfata.Menu.getCadru;
import static Main.Main.connection;

public class PenalisationMenu {
    private static JPanel mpMainPanel = null;

    /**
     *
     * used to create the penalisation menu
     * @author Iulian Sima
     */
    public static void createPenalisationMenu() {
        mpMainPanel = new JPanel();
        mpMainPanel.setOpaque(false);
        mpMainPanel.setLayout(new BorderLayout());
        JPanel pMainPanel = new JPanel();
        pMainPanel.setBorder (BorderFactory.createTitledBorder (BorderFactory.createEtchedBorder (), "Penalisations", TitledBorder.CENTER, TitledBorder.TOP));
        DefaultTableModel model = new DefaultTableModel();
        JTable penalisationTable = new JTable(model) {
            public boolean editCellAt(int row, int column, java.util.EventObject e) {
                return false;
            }
        };
        penalisationTable.setSize(getCadru().getWidth()-20, getCadru().getHeight()-40);
        penalisationTable.setOpaque(false);
        pMainPanel.setOpaque(false);
        model.addColumn("Vehicle plate");
        model.addColumn("Vehicle owner");
        model.addColumn("Penalisation/h");
        model.addColumn("Date");
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM penalisation ORDER BY Time DESC");
            while (rs.next()) {
                model.addRow(new Object[]{rs.getString("Vehicle"), rs.getString("Owner"), rs.getInt("Penalisation"), rs.getTimestamp("Time")});
            }
            rs.close();
            st.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        JScrollPane sp = new JScrollPane(penalisationTable);
        sp.setBackground(Color.black);
        pMainPanel.add(sp);
        penalisationTable.setRowSelectionAllowed(false);
        JButton returnToMainMenu = new JButton("Main menu");

        returnToMainMenu.setBackground(new Color(0x0000ffff));
        returnToMainMenu.setForeground(Color.black);
        returnToMainMenu.setUI(new StyledButtonUI());
        returnToMainMenu.addActionListener(e -> {
            hidePenalisationMenu();
            showAdminMainMenu(false, "");
        });
        mpMainPanel.add(pMainPanel);
        mpMainPanel.add(returnToMainMenu, BorderLayout.SOUTH);
        getCadru().add(mpMainPanel);
        mpMainPanel.setVisible(false);
    }

    /**
     * used to show the penalisation menu
     *
     * @author Iulian Sima
     */
    public static void showPenalisationMenu() {
        createPenalisationMenu();
        mpMainPanel.setVisible(true);
    }

    /**
     * used to hide the penalisation menu
     *
     * @author Iulian Sima
     */
    public static void hidePenalisationMenu() {
        mpMainPanel.setVisible(false);
    }
}
