package sace.vista;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import sace.servicio.LoginService;
import sace.modelo.Usuario;

public class LoginFrame extends JFrame {
    private final JTextField txtUsuario = new JTextField();
    private final JPasswordField txtClave = new JPasswordField();
    private final JLabel estado = new JLabel("Verificando conexión...");
    private final LoginService servicio = new LoginService();

    public LoginFrame() {
        setTitle("SACE - Inicio de sesión");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(520, 500);
        setMinimumSize(new Dimension(500, 470));
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel root = new JPanel(new GridBagLayout());
        root.setBackground(UI.FONDO);
        root.setBorder(BorderFactory.createEmptyBorder(32, 32, 32, 32));
        setContentPane(root);

        JPanel card = UI.tarjeta();
        card.setPreferredSize(new Dimension(390, 360));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        JLabel t = UI.titulo("SACE");
        t.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel s = new JLabel("Sistema de Administración de Empleados");
        s.setFont(UI.FUENTE);
        s.setForeground(UI.TEXTO_SEC);
        s.setAlignmentX(Component.CENTER_ALIGNMENT);

        txtUsuario.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        txtClave.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        JButton entrar = UI.boton("Iniciar sesión");
        entrar.setAlignmentX(Component.CENTER_ALIGNMENT);
        entrar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        entrar.addActionListener(e -> login());
        txtClave.addActionListener(e -> login());

        estado.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        estado.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(Box.createVerticalStrut(4));
        card.add(t);
        card.add(Box.createVerticalStrut(5));
        card.add(s);
        card.add(Box.createVerticalStrut(28));
        card.add(UI.campo("Usuario o correo", txtUsuario));
        card.add(Box.createVerticalStrut(14));
        card.add(UI.campo("Contraseña", txtClave));
        card.add(Box.createVerticalStrut(22));
        card.add(entrar);
        card.add(Box.createVerticalStrut(16));
        card.add(estado);

        root.add(card);
        SwingUtilities.invokeLater(this::preparar);
    }

    private void preparar() {
        try {
            servicio.preparar();
            estado.setText("● MongoDB conectado");
            estado.setForeground(UI.VERDE);
        } catch (Exception e) {
            estado.setText("● MongoDB no disponible. Revise la configuración.");
            estado.setForeground(UI.ROJO);
        }
    }

    private void login() {
        char[] clave = txtClave.getPassword();
        try {
            Usuario u = servicio.login(txtUsuario.getText(), clave);
            if (u == null) throw new IllegalArgumentException("Credenciales incorrectas.");
            dispose();
            new MainFrame(u).setVisible(true);
        } catch (Exception e) {
            UI.error(this, e);
        } finally {
            Arrays.fill(clave, '\0');
        }
    }
}
