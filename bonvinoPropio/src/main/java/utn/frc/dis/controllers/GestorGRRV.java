package utn.frc.dis.controllers;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import utn.frc.dis.entity.Vino;
import utn.frc.dis.estrategia.EstrategiaReseniaSommelier;
import utn.frc.dis.estrategia.IEstrategia;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class GestorGRRV {

    private LocalDateTime fechaDesde;
    private LocalDateTime fechaHasta;
    private String tipoDeResenia;
    private List<Vino> vinos;
    private IEstrategia estrategia;

    public GestorGRRV() {
        // Inicializa con la estrategia por defecto (Sommelier)
        this.estrategia = new EstrategiaReseniaSommelier();
    }

    /**
     * Configura las fechas seleccionadas por el usuario.
     */
    public void procesarFechas(LocalDateTime fechaDesde, LocalDateTime fechaHasta) {
        if (fechaDesde == null || fechaHasta == null || fechaDesde.isAfter(fechaHasta)) {
            throw new IllegalArgumentException("Fechas inválidas. Verifique que la fecha desde no sea posterior a la fecha hasta.");
        }
        this.fechaDesde = fechaDesde;
        this.fechaHasta = fechaHasta;
    }

    /**
     * Consulta si hay vinos disponibles dentro del periodo indicado.
     */
    public boolean consultarPeriodo() {
        if (fechaDesde == null || fechaHasta == null) {
            throw new IllegalStateException("Debe configurar las fechas antes de consultar.");
        }

        // Busca los vinos utilizando la estrategia
        vinos = estrategia.buscarVinos(fechaDesde, fechaHasta);
        return !vinos.isEmpty();
    }

    /**
     * Confirma si el tipo de reseña seleccionado tiene lógica implementada y vinos disponibles.
     */
    public boolean confirmarTipoResenia(String tipoDeResenia) {
        this.tipoDeResenia = tipoDeResenia;

        if ("Reseñas de Sommelier".equals(tipoDeResenia)) {
            return !vinos.isEmpty();
        } else {
            throw new UnsupportedOperationException("Lógica no implementada para el tipo de reseña: " + tipoDeResenia);
        }
    }

    /**
     * Genera el reporte con los 10 mejores vinos, incluyendo promedios de calificaciones.
     */
    public List<String[]> generarReporte() {
        if (vinos == null || vinos.isEmpty()) {
            throw new IllegalStateException("No se encontraron vinos en el periodo o no se confirmó el tipo de reseña.");
        }

        // La estrategia ya calcula el promedio y ordena los vinos. Seleccionamos los 10 mejores.
        List<Vino> top10Vinos = vinos.subList(0, Math.min(10, vinos.size()));

        // Formatear los datos para la pantalla
        return top10Vinos.stream().map(vino -> new String[]{
                vino.getName(), // Nombre
                vino.obtenerPromedioPuntajeResenias().toString(), // Promedio General
                vino.obtenerPromedioSommelierPuntajeResenias().toString(), // Promedio Sommeliers
                vino.getPrecioARS() + " ARS", // Precio
                vino.getVarietal().getDescripcion(), // Varietal
                vino.getBodega().getNombre(), // Bodega
                vino.getBodega().getRegion().getNomeRegionVitivinico(), // Región
                vino.getBodega().getRegion().getProvincia().getPais().getName() // País
        }).toList();
    }

    /**
     * Genera un archivo Excel con los 10 mejores vinos.
     */
    public void generarExcel(String rutaArchivo) throws IOException {
        if (vinos == null || vinos.isEmpty()) {
            throw new IllegalStateException("No se encontraron vinos en el periodo o no se confirmó el tipo de reseña.");
        }

        List<Vino> top10Vinos = vinos.subList(0, Math.min(10, vinos.size()));

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Ranking de Vinos");

        // Crear encabezados
        Row headerRow = sheet.createRow(0);
        String[] columnNames = {"Nombre", "Promedio General", "Promedio Sommeliers", "Precio Sugerido", "Varietal", "Bodega", "Región", "País"};

        for (int i = 0; i < columnNames.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columnNames[i]);
            cell.setCellStyle(crearEstiloEncabezado(workbook));
        }

        // Llenar datos
        int rowNum = 1;
        for (Vino vino : top10Vinos) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(vino.getName());
            row.createCell(1).setCellValue(vino.obtenerPromedioPuntajeResenias());
            row.createCell(2).setCellValue(vino.obtenerPromedioSommelierPuntajeResenias());
            row.createCell(3).setCellValue(vino.getPrecioARS() + " ARS");
            row.createCell(4).setCellValue(vino.getVarietal().getDescripcion());
            row.createCell(5).setCellValue(vino.getBodega().getNombre());
            row.createCell(6).setCellValue(vino.getBodega().getRegion().getNomeRegionVitivinico());
            row.createCell(7).setCellValue(vino.getBodega().getRegion().getProvincia().getPais().getName());
        }

        // Ajustar tamaño de las columnas
        for (int i = 0; i < columnNames.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Escribir archivo
        try (FileOutputStream fileOut = new FileOutputStream(rutaArchivo)) {
            workbook.write(fileOut);
        } finally {
            workbook.close();
        }
    }

    private CellStyle crearEstiloEncabezado(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        return style;
    }
}
