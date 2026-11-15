package sace.vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import sace.servicio.RemuneracionService;
import sace.modelo.Remuneracion;

public class HistorialPanel extends JPanel {
    private final JTextField codigo = new JTextField(12);
    private final JTextField periodo = new JTextField(8);
    private final RemuneracionService service = new RemuneracionService();
    private final DefaultTableModel model = new DefaultTableModel(
            new Object[]{"Empleado", "Período", "Tipo", "Detalle", "Importe", "Fecha"}, 0) {
        @Override public boolean isCellEditable(int r, int c) { return false; }
    };
    private final JTable tabla = new JTable(model);

    public HistorialPanel() {
        setLayout(new BorderLayout(0, 18));
        setBackground(UI.FONDO);
        setBorder(BorderFactory.createEmptyBorder(28, 30, 28, 30));

        JPanel header = new JPanel();
        header.setOpaque(false);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.add(UI.titulo("Historial de cálculos"));
        header.add(Box.createVerticalStrut(4));
        header.add(UI.subtitulo("Consulta remuneraciones registradas por empleado o período"));
        add(header, BorderLayout.NORTH);

        JPanel body = new JPanel(new BorderLayout(0, 14));
        body.setOpaque(false);

        JPanel filtros = UI.tarjeta();
        filtros.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
        filtros.add(new JLabel("Código (opcional):"));
        UI.estilizarCampo(codigo);
        codigo.setPreferredSize(new Dimension(150, 38));
        filtros.add(codigo);
        filtros.add(new JLabel("Período AAAA-MM (opcional):"));
        UI.estilizarCampo(periodo);
        periodo.setPreferredSize(new Dimension(130, 38));
        filtros.add(periodo);
        JButton consultar = UI.boton("Consultar");
        consultar.addActionListener(e -> cargar());
        codigo.addActionListener(e -> cargar());
        periodo.addActionListener(e -> cargar());
        filtros.add(consultar);
        body.add(filtros, BorderLayout.NORTH);

        UI.estilizarTabla(tabla);
        tabla.setAutoCreateRowSorter(true);
        body.add(UI.scroll(tabla), BorderLayout.CENTER);
        add(body, BorderLayout.CENTER);
        SwingUtilities.invokeLater(this::cargar);
    }

    private void cargar() {
        try {
            model.setRowCount(0);
            for (Remuneracion r : service.historial(codigo.getText(), periodo.getText())) {
                model.addRow(new Object[]{r.empleadoCodigo(), r.periodo(), r.tipoEmpleado(), r.detalle(),
                        String.format("$%.2f", r.importe()), r.fechaCalculo()});
            }
        } catch (Exception e) {
            UI.error(this, e);
        }
    }
}
