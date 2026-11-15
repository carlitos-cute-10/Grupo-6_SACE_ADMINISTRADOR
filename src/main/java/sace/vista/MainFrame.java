package sace.vista;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;
import sace.modelo.Usuario;
import sace.config.MongoConnection;

public class MainFrame extends JFrame {
    private final CardLayout cards = new CardLayout();
    private final JPanel contenido = new JPanel(cards);
    private final Map<String, JButton> navButtons = new LinkedHashMap<>();

    public MainFrame(Usuario usuario) {
        setTitle("SACE - Sistema de Administración de Empleados");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1120, 700));
        setSize(1280, 800);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(UI.FONDO);
        setContentPane(root);

        JPanel side = new JPanel();
        side.setPreferredSize(new Dimension(210, 0));
        side.setBackground(UI.AZUL_OSCURO);
        side.setBorder(BorderFactory.createEmptyBorder(26, 14, 22, 14));
        side.setLayout(new BoxLayout(side, BoxLayout.Y_AXIS));

        JLabel logo = new JLabel("SACE");
        logo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        logo.setForeground(Color.WHITE);
        logo.setAlignmentX(Component.LEFT_ALIGNMENT);
        side.add(logo);

        JLabel desc = new JLabel("Administración de empleados");
        desc.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        desc.setForeground(new Color(190, 202, 217));
        side.add(desc);
        side.add(Box.createVerticalStrut(16));

        JLabel user = new JLabel("Sesión: " + usuario.usuario());
        user.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        user.setForeground(new Color(210, 220, 232));
        side.add(user);
        side.add(Box.createVerticalStrut(28));

        addNav(side, "Dashboard", "DASH");
        addNav(side, "Empleados", "EMP");
        addNav(side, "Remuneraciones", "REM");
        addNav(side, "Reportes", "REP");
        addNav(side, "Historial", "HIS");

        side.add(Box.createVerticalGlue());
        JButton salir = nav("Cerrar sesión");
        salir.setBackground(new Color(54, 68, 88));
        salir.addActionListener(e -> {
            MongoConnection.close();
            dispose();
            new LoginFrame().setVisible(true);
        });
        side.add(salir);

        contenido.setBackground(UI.FONDO);
        contenido.add(new DashboardPanel(), "DASH");
        contenido.add(new EmpleadosPanel(), "EMP");
        contenido.add(new RemuneracionesPanel(), "REM");
        contenido.add(new ReportesPanel(), "REP");
        contenido.add(new HistorialPanel(), "HIS");

        root.add(side, BorderLayout.WEST);
        root.add(contenido, BorderLayout.CENTER);
        mostrar("DASH");
    }

    private void addNav(JPanel p, String text, String key) {
        JButton b = nav(text);
        navButtons.put(key, b);
        b.addActionListener(e -> mostrar(key));
        p.add(b);
        p.add(Box.createVerticalStrut(7));
    }

    private void mostrar(String key) {
        cards.show(contenido, key);
        for (Map.Entry<String, JButton> e : navButtons.entrySet()) {
            e.getValue().setBackground(e.getKey().equals(key) ? UI.AZUL : new Color(46, 62, 84));
        }
        contenido.revalidate();
        contenido.repaint();
    }

    private JButton nav(String t) {
        JButton b = new JButton(t);
        b.setAlignmentX(Component.LEFT_ALIGNMENT);
        b.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        b.setHorizontalAlignment(SwingConstants.LEFT);
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setForeground(Color.WHITE);
        b.setBackground(new Color(46, 62, 84));
        b.setFont(new Font("Segoe UI", Font.BOLD, 13));
        b.setBorder(BorderFactory.createEmptyBorder(10, 14, 10, 14));
        b.setOpaque(true);
        b.setContentAreaFilled(true);
        return b;
    }
}
