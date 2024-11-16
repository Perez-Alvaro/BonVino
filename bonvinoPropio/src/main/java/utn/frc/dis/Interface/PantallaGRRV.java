package utn.frc.dis.Interface;

import utn.frc.dis.controllers.GestorGRRV;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PantallaGRRV extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;
    private GestorGRRV gestor;

    private JTextField fechaDesdeField;
    private JTextField fechaHastaField;
    private JComboBox<String> tipoReseniaComboBox;
    private JComboBox<String> visualizacionComboBox;

    public PantallaGRRV() {
        gestor = new GestorGRRV();

        setTitle("Generar Ranking de Vinos");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Configuración principal del panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));

        // Panel de configuración
        JPanel configPanel = new JPanel(new GridBagLayout());
        configPanel.setBorder(BorderFactory.createTitledBorder("Configuración de búsqueda"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        fechaDesdeField = new JTextField("16/11/2020");
        fechaHastaField = new JTextField("16/11/2024");
        tipoReseniaComboBox = new JComboBox<>(new String[]{"Reseñas de Sommelier", "Reseñas Normales", "Reseñas de Amigos"});
        visualizacionComboBox = new JComboBox<>(new String[]{"Pantalla", "Excel"});

        gbc.gridx = 0;
        gbc.gridy = 0;
        configPanel.add(new JLabel("Fecha Desde:"), gbc);
        gbc.gridx = 1;
        configPanel.add(fechaDesdeField, gbc);

        gbc.gridx = 2;
        configPanel.add(new JLabel("Fecha Hasta:"), gbc);
        gbc.gridx = 3;
        configPanel.add(fechaHastaField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        configPanel.add(new JLabel("Tipo de Reseña:"), gbc);
        gbc.gridx = 1;
        configPanel.add(tipoReseniaComboBox, gbc);

        gbc.gridx = 2;
        configPanel.add(new JLabel("Visualización:"), gbc);
        gbc.gridx = 3;
        configPanel.add(visualizacionComboBox, gbc);

        // Botones agrupados
        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 15, 15));
        JButton consultarBtn = new JButton("Consultar Periodo");
        JButton confirmarReseniaBtn = new JButton("Confirmar Tipo Reseña");
        JButton confirmarVisualizacionBtn = new JButton("Confirmar Visualización");
        JButton generarExcelBtn = new JButton("Generar Excel");
        JButton cancelarBtn = new JButton("Cancelar");

        buttonPanel.add(consultarBtn);
        buttonPanel.add(confirmarReseniaBtn);
        buttonPanel.add(confirmarVisualizacionBtn);
        buttonPanel.add(generarExcelBtn);
        buttonPanel.add(cancelarBtn);

        // Tabla
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Vinos encontrados dentro del periodo:"));

        String[] columnNames = {
                "Nombre",
                "Promedio General",
                "Promedio Sommeliers",
                "Precio Sugerido",
                "Varietal",
                "Bodega",
                "Región",
                "País"
        };
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Eventos de botones
        consultarBtn.addActionListener(e -> consultarPeriodo());
        confirmarReseniaBtn.addActionListener(e -> confirmarResenia());
        confirmarVisualizacionBtn.addActionListener(e -> confirmarVisualizacion());
        generarExcelBtn.addActionListener(e -> generarExcel());
        cancelarBtn.addActionListener(e -> dispose());

        // Organizar paneles
        mainPanel.add(configPanel, BorderLayout.NORTH);
        mainPanel.add(tablePanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void consultarPeriodo() {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDateTime fechaDesde = LocalDate.parse(fechaDesdeField.getText(), formatter).atStartOfDay();
            LocalDateTime fechaHasta = LocalDate.parse(fechaHastaField.getText(), formatter).atStartOfDay();

            gestor.procesarFechas(fechaDesde, fechaHasta);

            if (gestor.consultarPeriodo()) {
                JOptionPane.showMessageDialog(this, "Vinos encontrados en el periodo.");
            } else {
                JOptionPane.showMessageDialog(this, "No se encontraron vinos en el periodo.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al procesar las fechas. Por favor, utilice el formato dd/MM/yyyy.");
        }
    }

    private void confirmarResenia() {
        try {
            String tipoResenia = (String) tipoReseniaComboBox.getSelectedItem();
            if (gestor.confirmarTipoResenia(tipoResenia)) {
                JOptionPane.showMessageDialog(this, "Tipo de reseña confirmado.");
            }
        } catch (UnsupportedOperationException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void confirmarVisualizacion() {
        try {
            List<String[]> datos = gestor.generarReporte();

            // Limpiar tabla
            tableModel.setRowCount(0);

            // Agregar datos a la tabla
            for (String[] fila : datos) {
                tableModel.addRow(fila);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al generar el reporte: " + ex.getMessage());
        }
    }

    private void generarExcel() {
        try {
            String rutaArchivo = "ranking_vinos.xlsx";
            gestor.generarExcel(rutaArchivo);
            JOptionPane.showMessageDialog(this, "Archivo Excel generado en: " + rutaArchivo);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al generar el archivo Excel: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PantallaGRRV frame = new PantallaGRRV();
            frame.setVisible(true);
        });
    }
}
