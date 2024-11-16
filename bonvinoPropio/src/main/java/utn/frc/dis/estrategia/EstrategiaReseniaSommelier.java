package utn.frc.dis.estrategia;
import utn.frc.dis.entity.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import utn.frc.dis.estrategia.IEstrategia;
import utn.frc.dis.service.VinoService;

public class EstrategiaReseniaSommelier implements IEstrategia {
    @Override
    public Double calcularPromedioClasificacion(Double sumaCalif, Integer cantidadResenias) {
        double promedio = (cantidadResenias > 0) ? sumaCalif / cantidadResenias : 0.0;
        return promedio;

        // Implementar la lógica para calcular el promedio según la clasificación de Sommeliers
    }

    @Override
    public boolean validarFechaResenia(LocalDateTime fecha, LocalDateTime fechaDesde, LocalDateTime fechaHasta) {
        return (fecha.isAfter(fechaDesde) || fecha.isEqual(fechaDesde)) && (fecha.isBefore(fechaHasta) || fecha.isEqual(fechaHasta));
    }

    @Override
    public List<Vino> buscarVinos(LocalDateTime fechaDesde, LocalDateTime fechaHasta){
        VinoService vinoService = new VinoService();
        List<Vino> vinosRep = vinoService.ConseguirVinos();
        List<Vino> vinos  = new ArrayList<>();


        Map<Integer, Double> promedioCalificaciones = new HashMap<>();

        for (Vino vino : vinosRep) {



            System.out.println(vino.getName());
            Double sumaCalif = 0.0;
            int cantidadResenias = 0;
            for (Resenia resenia : vino.getResenias()) {
                if (vino.esTipoDeResenia(fechaDesde,fechaHasta, true)){
                    vinos.add(vino);
                }
                sumaCalif += resenia.getPuntaje();
                cantidadResenias++;
                System.out.println(resenia.sosDeSomelier());
            }

            //double promedio = (cantidadResenias > 0) ? sumaCalif / cantidadResenias : 0.0;
            double promedio = calcularPromedioClasificacion(sumaCalif, cantidadResenias);

            promedioCalificaciones.put(vino.getId(), promedio);
            System.out.println("Promedio: " + promedio);
        }

        vinos = ordenarVinosPorCalificacion(promedioCalificaciones, vinos);




        return vinos;

    }
    @Override
    public List<Vino> ordenarVinosPorCalificacion(Map<Integer, Double> promedioCalificaciones, List<Vino> vinos){
        // Ordenar la lista de vinos según el promedio de calificación en orden descendente
        vinos = vinos.stream()
                .sorted((v1, v2) -> Double.compare(
                        promedioCalificaciones.getOrDefault(v2.getId(), 0.0),
                        promedioCalificaciones.getOrDefault(v1.getId(), 0.0)
                ))
                .collect(Collectors.toList());

        //esto solo está para ver como es la lista de vinos una vez ordenada
        System.out.println("\n=== Vinos ordenados por calificación ===");
        for (Vino vino : vinos) {
            System.out.println("Vino: " + vino.getName() + " - Promedio: " + promedioCalificaciones.getOrDefault(vino.getId(), 0.0));
        }
        return vinos;
    }

}
