package sace.vista;

import javax.swing.*;
import java.awt.*;
import sace.servicio.EmpleadoService;

public class DashboardPanel extends JPanel {
    private final EmpleadoService service = new EmpleadoService();
    private final JLabel act = new JLabel("-");
    private final JLabel adm = new JLabel("-");
    private final JLabel hora = new JLabel("-");
    private final JLabel com = new JLabel("-");
    private final JLabel nomina = new JLabel("-");

    public DashboardPanel() {
        setLayout(new BorderLayout(0, 22));
        setBackground(UI.FONDO);
        setBorder(BorderFactory.createEmptyBorder(28, 30, 28, 30));

        JPanel head = new JPanel(new BorderLayout());
        head.setOpaque(false);
        JPanel titles = new JPanel();
        titles.setOpaque(false);
        titles.setLayout(new BoxLayout(titles, BoxLayout.Y_AXIS));
        titles.add(UI.titulo("Dashboard"));
        titles.add(Box.createVerticalStrut(4));
        titles.add(UI.subtitulo("Resumen general de la información almacenada en MongoDB"));
        head.add(titles, BorderLayout.WEST);
        JButton actualizar = UI.botonSecundario("Actualizar");
        actualizar.addActionListener(e -> cargar());
        head.add(actualizar, BorderLayout.EAST);
        add(head, BorderLayout.NORTH);

        JPanel grid = new JPanel(new GridLayout(2, 3, 16, 16));
        grid.setOpaque(false);
        grid.add(card("Empleados activos", act));
        grid.add(card("Administrativos", adm));
        grid.add(card("Por hora", hora));
        grid.add(card("Por comisión", com));
        grid.add(card("Nómina actual", nomina));

        JPanel info = UI.tarjeta();
        info.setLayout(new BorderLayout());
        JLabel texto = new JLabel("Los indicadores se actualizan con los registros vigentes del sistema.");
        texto.setForeground(UI.TEXTO_SEC);
        info.add(texto, BorderLayout.CENTER);
        grid.add(info);

        add(grid, BorderLayout.CENTER);
        SwingUtilities.invokeLater(this::cargar);
    }

    private JPanel card(String titulo, JLabel valor) {
        JPanel p = UI.tarjeta();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        JLabel l = new JLabel(titulo);
        l.setForeground(UI.TEXTO_SEC);
        l.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        valor.setFont(new Font("Segoe UI", Font.BOLD, 28));
        valor.setForeground(UI.AZUL_OSCURO);
        p.add(l);
        p.add(Box.createVerticalStrut(12));
        p.add(valor);
        p.add(Box.createVerticalGlue());
        return p;
    }

    private void cargar() {
        try {
            act.setText(String.valueOf(service.activos()));
            adm.setText(String.valueOf(service.contarTipo("Administrativo")));
            hora.setText(String.valueOf(service.contarTipo("Por hora")));
            com.setText(String.valueOf(service.contarTipo("Por comisión")));
            nomina.setText(String.format("$%.2f", service.nominaActual()));
        } catch (Exception e) {
            UI.error(this, e);
        }
    }
}
