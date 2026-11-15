package sace.vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;
import sace.modelo.*;
import sace.servicio.EmpleadoService;

public class EmpleadosPanel extends JPanel {
    private final EmpleadoService service = new EmpleadoService();

    private final JTextField codigo = new JTextField();
    private final JTextField nombre = new JTextField();
    private final JTextField documento = new JTextField();
    private final JTextField cargo = new JTextField();
    private final JTextField fecha = new JTextField(LocalDate.now().toString());
    private final JTextField v1 = new JTextField("0");
    private final JTextField v2 = new JTextField("0");
    private final JTextField buscar = new JTextField(18);
    private final JComboBox<String> tipo = new JComboBox<>(new String[]{"Administrativo", "Por hora", "Por comisión"});

    private final JPanel campoV1 = new JPanel(new BorderLayout(0, 6));
    private final JPanel campoV2 = new JPanel(new BorderLayout(0, 6));
    private final JLabel l1 = new JLabel();
    private final JLabel l2 = new JLabel();

    private final DefaultTableModel model = new DefaultTableModel(
            new Object[]{"Código", "Nombre", "Documento", "Cargo", "Ingreso", "Tipo", "Estado", "Remuneración"}, 0) {
        @Override public boolean isCellEditable(int r, int c) { return false; }
    };
    private final JTable tabla = new JTable(model);

    public EmpleadosPanel() {
        setLayout(new BorderLayout(0, 18));
        setBackground(UI.FONDO);
        setBorder(BorderFactory.createEmptyBorder(24, 26, 24, 26));

        add(crearCabecera(), BorderLayout.NORTH);

        JPanel centro = new JPanel(new BorderLayout(0, 14));
        centro.setOpaque(false);
        centro.add(crearFormulario(), BorderLayout.NORTH);
        centro.add(crearTabla(), BorderLayout.CENTER);
        add(centro, BorderLayout.CENTER);

        tipo.addActionListener(e -> actualizarEtiquetas());
        actualizarEtiquetas();
        SwingUtilities.invokeLater(() -> cargar(service.listar()));
    }

    private JPanel crearCabecera() {
        JPanel top = new JPanel(new BorderLayout(16, 0));
        top.setOpaque(false);

        JPanel titles = new JPanel();
        titles.setOpaque(false);
        titles.setLayout(new BoxLayout(titles, BoxLayout.Y_AXIS));
        titles.add(UI.titulo("Gestión de empleados"));
        titles.add(Box.createVerticalStrut(3));
        titles.add(UI.subtitulo("Registro, consulta, modificación e inactivación de empleados"));
        top.add(titles, BorderLayout.WEST);

        JPanel search = new JPanel(new FlowLayout(FlowLayout.RIGHT, 7, 0));
        search.setOpaque(false);
        UI.estilizarCampo(buscar);
        buscar.setPreferredSize(new Dimension(220, 38));
        JButton b = UI.botonSecundario("Buscar");
        b.addActionListener(e -> cargar(service.buscar(buscar.getText())));
        buscar.addActionListener(e -> cargar(service.buscar(buscar.getText())));
        JButton todos = UI.botonSecundario("Mostrar todos");
        todos.addActionListener(e -> {
            buscar.setText("");
            cargar(service.listar());
        });
        search.add(buscar);
        search.add(b);
        search.add(todos);
        top.add(search, BorderLayout.EAST);
        return top;
    }

    private JPanel crearFormulario() {
        JPanel card = UI.tarjeta();
        card.setLayout(new BorderLayout(0, 14));

        JPanel form = new JPanel(new GridLayout(2, 4, 12, 12));
        form.setOpaque(false);
        form.add(UI.campo("Código", codigo));
        form.add(UI.campo("Nombre completo", nombre));
        form.add(UI.campo("Documento", documento));
        form.add(UI.campo("Cargo", cargo));
        form.add(UI.campo("Fecha de ingreso (AAAA-MM-DD)", fecha));
        form.add(UI.campo("Tipo de empleado", tipo));

        configurarCampoEspecifico(campoV1, l1, v1);
        configurarCampoEspecifico(campoV2, l2, v2);
        form.add(campoV1);
        form.add(campoV2);
        card.add(form, BorderLayout.CENTER);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        actions.setOpaque(false);
        JButton nuevo = UI.botonSecundario("Nuevo");
        JButton guardar = UI.boton("Registrar");
        JButton actualizar = UI.botonSecundario("Modificar");
        JButton inactivar = UI.botonPeligro("Inactivar");
        nuevo.addActionListener(e -> limpiar());
        guardar.addActionListener(e -> guardar(false));
        actualizar.addActionListener(e -> guardar(true));
        inactivar.addActionListener(e -> inactivar());
        actions.add(nuevo);
        actions.add(guardar);
        actions.add(actualizar);
        actions.add(inactivar);
        card.add(actions, BorderLayout.SOUTH);
        return card;
    }

    private void configurarCampoEspecifico(JPanel panel, JLabel label, JTextField campo) {
        panel.setOpaque(false);
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        label.setForeground(UI.TEXTO);
        UI.estilizarCampo(campo);
        panel.add(label, BorderLayout.NORTH);
        panel.add(campo, BorderLayout.CENTER);
    }

    private JComponent crearTabla() {
        UI.estilizarTabla(tabla);
        tabla.setAutoCreateRowSorter(true);
        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) cargarSeleccion();
        });
        JScrollPane scroll = UI.scroll(tabla);
        scroll.setPreferredSize(new Dimension(0, 310));
        return scroll;
    }

    private void actualizarEtiquetas() {
        String t = (String) tipo.getSelectedItem();
        if ("Administrativo".equals(t)) {
            l1.setText("Salario mensual");
            l2.setText("No aplica");
            v1.setToolTipText("Salario mensual del empleado");
            v2.setText("0");
            v2.setEnabled(false);
            v2.setBackground(new Color(244, 246, 248));
        } else if ("Por hora".equals(t)) {
            l1.setText("Tarifa por hora");
            l2.setText("Horas trabajadas");
            v1.setToolTipText("Tarifa pagada por cada hora trabajada");
            v2.setToolTipText("Horas trabajadas en el período");
            v2.setEnabled(true);
            v2.setBackground(Color.WHITE);
        } else {
            l1.setText("Porcentaje de comisión (0-100)");
            l2.setText("Monto de ventas");
            v1.setToolTipText("Porcentaje de comisión entre 0 y 100");
            v2.setToolTipText("Monto de ventas del período");
            v2.setEnabled(true);
            v2.setBackground(Color.WHITE);
        }
        campoV1.revalidate();
        campoV2.revalidate();
        repaint();
    }

    private void guardar(boolean editar) {
        try {
            double a = num(v1.getText());
            double b = v2.isEnabled() ? num(v2.getText()) : 0;
            LocalDate f = LocalDate.parse(fecha.getText().trim());
            if (editar) {
                service.actualizar(codigo.getText(), nombre.getText(), documento.getText(), cargo.getText(), f,
                        (String) tipo.getSelectedItem(), a, b);
            } else {
                service.registrar(codigo.getText(), nombre.getText(), documento.getText(), cargo.getText(), f,
                        (String) tipo.getSelectedItem(), a, b);
            }
            JOptionPane.showMessageDialog(this, editar ? "Empleado actualizado correctamente." : "Empleado registrado correctamente.",
                    "SACE", JOptionPane.INFORMATION_MESSAGE);
            limpiar();
            cargar(service.listar());
        } catch (Exception e) {
            UI.error(this, e);
        }
    }

    private void inactivar() {
        try {
            if (codigo.getText().isBlank()) throw new IllegalArgumentException("Seleccione o indique un empleado.");
            if (JOptionPane.showConfirmDialog(this, "¿Desea inactivar al empleado seleccionado?", "Confirmación",
                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
                service.inactivar(codigo.getText());
                cargar(service.listar());
                limpiar();
            }
        } catch (Exception e) {
            UI.error(this, e);
        }
    }

    private void cargar(List<Empleado> lista) {
        model.setRowCount(0);
        for (Empleado e : lista) {
            model.addRow(new Object[]{e.getCodigo(), e.getNombre(), e.getDocumento(), e.getCargo(), e.getFechaIngreso(),
                    e.getTipoEmpleado(), e.isActivo() ? "Activo" : "Inactivo", String.format("$%.2f", e.calcularRemuneracion())});
        }
    }

    private void cargarSeleccion() {
        int r = tabla.getSelectedRow();
        if (r < 0) return;
        int m = tabla.convertRowIndexToModel(r);
        Empleado e = service.buscarCodigo(String.valueOf(model.getValueAt(m, 0)));
        if (e == null) return;
        codigo.setText(e.getCodigo());
        codigo.setEditable(false);
        nombre.setText(e.getNombre());
        documento.setText(e.getDocumento());
        cargo.setText(e.getCargo());
        fecha.setText(e.getFechaIngreso().toString());
        tipo.setSelectedItem(e.getTipoEmpleado());
        if (e instanceof EmpleadoAdministrativo a) {
            v1.setText(String.valueOf(a.getSalarioMensual()));
            v2.setText("0");
        } else if (e instanceof EmpleadoPorHora h) {
            v1.setText(String.valueOf(h.getTarifaHora()));
            v2.setText(String.valueOf(h.getHorasTrabajadas()));
        } else if (e instanceof EmpleadoPorComision c) {
            v1.setText(String.valueOf(c.getPorcentajeComision()));
            v2.setText(String.valueOf(c.getVentas()));
        }
    }

    private void limpiar() {
        codigo.setEditable(true);
        codigo.setText("");
        nombre.setText("");
        documento.setText("");
        cargo.setText("");
        fecha.setText(LocalDate.now().toString());
        tipo.setSelectedIndex(0);
        v1.setText("0");
        v2.setText("0");
        tabla.clearSelection();
        codigo.requestFocusInWindow();
    }

    private double num(String s) {
        try {
            double d = Double.parseDouble(s.trim().replace(',', '.'));
            if (d < 0) throw new NumberFormatException();
            return d;
        } catch (Exception e) {
            throw new IllegalArgumentException("Los valores específicos deben ser numéricos y no negativos.");
        }
    }
}
