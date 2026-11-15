package sace.vista;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public final class UI {
    public static final Color AZUL_OSCURO = new Color(38, 55, 78);
    public static final Color AZUL = new Color(55, 101, 150);
    public static final Color AZUL_SUAVE = new Color(230, 238, 247);
    public static final Color FONDO = new Color(246, 248, 251);
    public static final Color TARJETA = Color.WHITE;
    public static final Color BORDE = new Color(221, 226, 232);
    public static final Color TEXTO = new Color(45, 51, 59);
    public static final Color TEXTO_SEC = new Color(102, 112, 124);
    public static final Color VERDE = new Color(50, 132, 93);
    public static final Color ROJO = new Color(170, 71, 71);
    public static final Color GRIS_BOTON = new Color(232, 236, 241);

    public static final Font FUENTE = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font FUENTE_NEGRITA = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font FUENTE_TITULO = new Font("Segoe UI", Font.BOLD, 25);

    private UI() {}

    public static void prepararUI() {
        UIManager.put("Label.font", FUENTE);
        UIManager.put("Button.font", FUENTE_NEGRITA);
        UIManager.put("TextField.font", FUENTE);
        UIManager.put("PasswordField.font", FUENTE);
        UIManager.put("ComboBox.font", FUENTE);
        UIManager.put("Table.font", FUENTE);
        UIManager.put("TableHeader.font", FUENTE_NEGRITA);
        UIManager.put("OptionPane.messageFont", FUENTE);
        UIManager.put("OptionPane.buttonFont", FUENTE_NEGRITA);
    }

    public static JButton boton(String texto) {
        JButton b = new JButton(texto);
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setBackground(AZUL);
        b.setForeground(Color.WHITE);
        b.setFont(FUENTE_NEGRITA);
        b.setBorder(new EmptyBorder(10, 16, 10, 16));
        b.setOpaque(true);
        b.setContentAreaFilled(true);
        return b;
    }

    public static JButton botonSecundario(String texto) {
        JButton b = new JButton(texto);
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setBackground(GRIS_BOTON);
        b.setForeground(TEXTO);
        b.setFont(FUENTE_NEGRITA);
        b.setBorder(new CompoundBorder(new LineBorder(BORDE), new EmptyBorder(9, 15, 9, 15)));
        b.setOpaque(true);
        b.setContentAreaFilled(true);
        return b;
    }

    public static JButton botonPeligro(String texto) {
        JButton b = boton(texto);
        b.setBackground(ROJO);
        return b;
    }

    public static JLabel titulo(String texto) {
        JLabel l = new JLabel(texto);
        l.setFont(FUENTE_TITULO);
        l.setForeground(AZUL_OSCURO);
        return l;
    }

    public static JLabel subtitulo(String texto) {
        JLabel l = new JLabel(texto);
        l.setFont(FUENTE);
        l.setForeground(TEXTO_SEC);
        return l;
    }

    public static JPanel tarjeta() {
        JPanel p = new JPanel();
        p.setBackground(TARJETA);
        p.setBorder(new CompoundBorder(new LineBorder(BORDE), new EmptyBorder(18, 18, 18, 18)));
        return p;
    }

    public static JPanel campo(String etiqueta, JComponent componente) {
        JPanel p = new JPanel(new BorderLayout(0, 6));
        p.setOpaque(false);
        JLabel l = new JLabel(etiqueta);
        l.setFont(new Font("Segoe UI", Font.BOLD, 13));
        l.setForeground(TEXTO);
        p.add(l, BorderLayout.NORTH);
        estilizarCampo(componente);
        p.add(componente, BorderLayout.CENTER);
        return p;
    }

    public static void estilizarCampo(JComponent componente) {
        componente.setFont(FUENTE);
        componente.setPreferredSize(new Dimension(componente.getPreferredSize().width, 38));
        if (componente instanceof JTextField || componente instanceof JComboBox<?>) {
            componente.setBorder(new CompoundBorder(new LineBorder(BORDE), new EmptyBorder(6, 9, 6, 9)));
            componente.setBackground(Color.WHITE);
        }
    }

    public static void estilizarTabla(JTable tabla) {
        tabla.setRowHeight(30);
        tabla.setShowVerticalLines(false);
        tabla.setShowHorizontalLines(true);
        tabla.setGridColor(new Color(235, 238, 242));
        tabla.setSelectionBackground(AZUL_SUAVE);
        tabla.setSelectionForeground(TEXTO);
        tabla.setIntercellSpacing(new Dimension(0, 1));
        tabla.setFillsViewportHeight(true);
        tabla.getTableHeader().setBackground(new Color(238, 242, 247));
        tabla.getTableHeader().setForeground(AZUL_OSCURO);
        tabla.getTableHeader().setPreferredSize(new Dimension(0, 34));
        if (tabla.getTableHeader().getDefaultRenderer() instanceof DefaultTableCellRenderer renderer) {
            renderer.setHorizontalAlignment(SwingConstants.LEFT);
        }
    }

    public static JScrollPane scroll(JComponent componente) {
        JScrollPane sp = new JScrollPane(componente);
        sp.setBorder(new LineBorder(BORDE));
        sp.getViewport().setBackground(Color.WHITE);
        return sp;
    }

    public static void error(Component c, Exception e) {
        String mensaje = e.getMessage();
        if (mensaje == null || mensaje.isBlank()) mensaje = "Ocurrió un error inesperado.";
        JOptionPane.showMessageDialog(c, mensaje, "SACE - Error", JOptionPane.ERROR_MESSAGE);
    }
}
