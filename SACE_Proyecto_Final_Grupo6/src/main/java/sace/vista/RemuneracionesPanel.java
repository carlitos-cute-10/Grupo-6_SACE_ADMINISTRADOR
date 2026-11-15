package sace.vista;

import javax.swing.*;
import java.awt.*;
import java.time.YearMonth;
import sace.modelo.Remuneracion;
import sace.servicio.RemuneracionService;

public class RemuneracionesPanel extends JPanel {
    private final JTextField codigo = new JTextField(14);
    private final JTextField periodo = new JTextField(YearMonth.now().toString(), 8);
    private final JTextArea resultado = new JTextArea();
    private final RemuneracionService service = new RemuneracionService();

    public RemuneracionesPanel() {
        setLayout(new BorderLayout(0, 18));
        setBackground(UI.FONDO);
        setBorder(BorderFactory.createEmptyBorder(28, 30, 28, 30));

        JPanel header = new JPanel();
        header.setOpaque(false);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.add(UI.titulo("Cálculo de remuneraciones"));
        header.add(Box.createVerticalStrut(4));
        header.add(UI.subtitulo("Calcula y registra la remuneración de un empleado para un período determinado"));
        add(header, BorderLayout.NORTH);

        JPanel body = new JPanel(new BorderLayout(0, 14));
        body.setOpaque(false);

        JPanel form = UI.tarjeta();
        form.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(0, 0, 0, 12);
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.35;
        c.gridx = 0;
        form.add(UI.campo("Código del empleado", codigo), c);
        c.weightx = 0.25;
        c.gridx = 1;
        form.add(UI.campo("Período (AAAA-MM)", periodo), c);
        c.weightx = 0;
        c.gridx = 2;
        c.anchor = GridBagConstraints.SOUTH;
        JButton calc = UI.boton("Calcular y registrar");
        calc.addActionListener(e -> calcular());
        form.add(calc, c);
        c.weightx = 1;
        c.gridx = 3;
        form.add(Box.createHorizontalGlue(), c);
        body.add(form, BorderLayout.NORTH);

        JPanel resultCard = UI.tarjeta();
        resultCard.setLayout(new BorderLayout(0, 10));
        JLabel rt = new JLabel("Resultado");
        rt.setFont(new Font("Segoe UI", Font.BOLD, 15));
        rt.setForeground(UI.AZUL_OSCURO);
        resultCard.add(rt, BorderLayout.NORTH);
        resultado.setEditable(false);
        resultado.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        resultado.setForeground(UI.TEXTO);
        resultado.setBackground(Color.WHITE);
        resultado.setLineWrap(true);
        resultado.setWrapStyleWord(true);
        resultado.setText("Ingrese el código del empleado y el período para realizar un cálculo.");
        JScrollPane sp = UI.scroll(resultado);
        sp.setPreferredSize(new Dimension(0, 260));
        resultCard.add(sp, BorderLayout.CENTER);
        body.add(resultCard, BorderLayout.CENTER);

        add(body, BorderLayout.CENTER);
    }

    private void calcular() {
        try {
            Remuneracion r = service.calcularYGuardar(codigo.getText(), periodo.getText());
            resultado.setText(
                    "Cálculo registrado correctamente\n\n" +
                    "Empleado: " + r.empleadoCodigo() + "\n" +
                    "Tipo: " + r.tipoEmpleado() + "\n" +
                    "Período: " + r.periodo() + "\n" +
                    "Datos utilizados: " + r.detalle() + "\n" +
                    String.format("Remuneración: $%.2f%n", r.importe()) +
                    "Fecha de cálculo: " + r.fechaCalculo());
        } catch (Exception e) {
            UI.error(this, e);
        }
    }
}
