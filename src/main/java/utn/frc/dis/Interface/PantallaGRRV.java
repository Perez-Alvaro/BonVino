package utn.frc.dis.Interface;

import utn.frc.dis.controllers.GestorGRRV;
import org.jdatepicker.impl.*;
import javax.swing.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Properties;


import javax.swing.*;
import javax.swing.text.DateFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;


public class PantallaGRRV extends JFrame {
    private JComboBox<String> comboBoxBodegas;
    private JTextArea textAreaResumen;
    private JButton buttonImportar;

    private JButton buttonSolicitarFechas;  // Nuevo botón para solicitar fechas
    private JDatePickerImpl datePickerDesde;
    private JDatePickerImpl datePickerHasta;
    //botones para pedir la fecha

    private GestorGRRV gestorGRRV;

//    public PantallaGRRV() {
//        gestorGRRV = new GestorGRRV(this);
//
//
//    }

















    public void SolicitarFechaDesdeHasta(){
        JPanel panelFechas = new JPanel(new GridLayout(3, 2, 10, 10));

        // Crear los componentes para seleccionar fechas
        datePickerDesde = crearDatePicker();
        datePickerHasta = crearDatePicker();
        buttonSolicitarFechas = new JButton("Confirmar Fechas");

        panelFechas.add(new JLabel("Fecha Desde:"));
        panelFechas.add(datePickerDesde);
        panelFechas.add(new JLabel("Fecha Hasta:"));
        panelFechas.add(datePickerHasta);
        panelFechas.add(buttonSolicitarFechas);

        // Agregar panel de fechas al final de la ventana
        add(panelFechas, BorderLayout.SOUTH);

        // Acción del botón para confirmar fechas
        buttonSolicitarFechas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                solicitarFechaDesdeHasta();
            }
        });

    }

    public void solicitarFechaDesdeHasta() {
        Date fechaDesdeSeleccionada = (Date) datePickerDesde.getModel().getValue();
        Date fechaHastaSeleccionada = (Date) datePickerHasta.getModel().getValue();

        if (fechaDesdeSeleccionada != null && fechaHastaSeleccionada != null) {
            LocalDateTime fechaDesde = fechaDesdeSeleccionada.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            LocalDateTime fechaHasta = fechaHastaSeleccionada.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

            // Llamada al gestor con las fechas seleccionadas
            gestorGRRV.procesarFechas(fechaDesde, fechaHasta);
            JOptionPane.showMessageDialog(this, "Fechas seleccionadas:\nDesde: " + fechaDesde + "\nHasta: " + fechaHasta);
        } else {
            JOptionPane.showMessageDialog(this, "Por favor seleccione ambas fechas", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JDatePickerImpl crearDatePicker() {
        UtilDateModel model = new UtilDateModel();
        Properties properties = new Properties();
        properties.put("text.today", "Hoy");
        properties.put("text.month", "Mes");
        properties.put("text.year", "Año");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, properties);

        SimpleDateFormat dateFormat =  new SimpleDateFormat("yyyy-MM-dd");
        return new JDatePickerImpl(datePanel,  new DateFormatter(dateFormat));
    }


}
