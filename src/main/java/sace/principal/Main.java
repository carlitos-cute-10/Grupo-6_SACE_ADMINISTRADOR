package sace.principal;

import javax.swing.*;
import sace.vista.LoginFrame;
import sace.vista.UI;
import sace.config.MongoConnection;

public class Main {
    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(MongoConnection::close));
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) { }
            UI.prepararUI();
            new LoginFrame().setVisible(true);
        });
    }
}
