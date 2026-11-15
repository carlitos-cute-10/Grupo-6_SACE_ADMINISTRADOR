package sace.vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import sace.modelo.Empleado;
import sace.servicio.EmpleadoService;

public class ReportesPanel extends JPanel {
    private final EmpleadoService service = new EmpleadoService();
    private final JComboBox<String> tipo = new JComboBox<>(new String[]{"Todos", "Administrativo", "Por hora", "Por comisión"});
    private final JLabel total = new JLabel("$0.00");
    private final DefaultTableModel model = new DefaultTableModel(
            new Object[]{"Código", "Nombre", "Tipo", "Estado", "Remuneración"}, 0) {
        @Override public boolean isCellEditable(int r, int c) { return false; }
    };
    private final JTable tabla = new JTable(model);

    public ReportesPanel() {
        setLayout(new BorderLayout(0, 18));
        setBackground(UI.FONDO);
        setBorder(BorderFactory.createEmptyBorder(28, 30, 28, 30));

        JPanel header = new JPanel();
        header.setOpaque(false);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.add(UI.titulo("Reportes y resumen"));
        header.add(Box.createVerticalStrut(4));
        header.add(UI.subtitulo("Consulta empleados por tipo y revisa el total de remuneraciones activas"));
        add(header, BorderLayout.NORTH);

        JPanel body = new JPanel(new BorderLayout(0, 14));
        body.setOpaque(false);

        JPanel filtros = UI.tarjeta();
        filtros.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
        filtros.add(new JLabel("Tipo de empleado:"));
        UI.estilizarCampo(tipo);
        tipo.setPreferredSize(new Dimension(180, 38));
        filtros.add(tipo);
        JButton generar = UI.boton("Generar");
        generar.addActionListener(e -> generar());
        filtros.add(generar);
        filtros.add(Box.createHorizontalStrut(24));
        JLabel totalLabel = new JLabel("Total de empleados activos:");
        totalLabel.setForeground(UI.TEXTO_SEC);
        filtros.add(totalLabel);
        total.setFont(new Font("Segoe UI", Font.BOLD, 20));
        total.setForeground(UI.AZUL_OSCURO);
        filtros.add(total);
        body.add(filtros, BorderLayout.NORTH);

        UI.estilizarTabla(tabla);
        tabla.setAutoCreateRowSorter(true);
        body.add(UI.scroll(tabla), BorderLayout.CENTER);
        add(body, BorderLayout.CENTER);
        SwingUtilities.invokeLater(this::generar);
    }

    private void generar() {
        try {
            var lista = service.porTipo((String) tipo.getSelectedItem());
            model.setRowCount(0);
            double suma = 0;
            for (Empleado e : lista) {
                double r = e.calcularRemuneracion();
                model.addRow(new Object[]{e.getCodigo(), e.getNombre(), e.getTipoEmpleado(),
                        e.isActivo() ? "Activo" : "Inactivo", String.format("$%.2f", r)});
                if (e.isActivo()) suma += r;
            }
            total.setText(String.format("$%.2f", suma));
        } catch (Exception e) {
            UI.error(this, e);
        }
    }
}
