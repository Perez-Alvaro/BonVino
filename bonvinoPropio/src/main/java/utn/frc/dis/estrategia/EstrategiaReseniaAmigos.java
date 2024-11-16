package utn.frc.dis.estrategia;

import utn.frc.dis.entity.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EstrategiaReseniaAmigos implements IEstrategia {
    @Override
    public Double calcularPromedioClasificacion(Double sumaCalif, Integer cantidadResenias) {
        // Implementar la lógica para calcular el promedio según la clasificación de Amigos
        return null;
    }
    @Override
    public List<Vino> buscarVinos(LocalDateTime fechaDesde, LocalDateTime fechaHasta){
        return null;
    }

    @Override
    public boolean validarFechaResenia(LocalDateTime fecha, LocalDateTime fechaDesde, LocalDateTime fechaHasta) {
        return (fecha.isAfter(fechaDesde) || fecha.isEqual(fechaDesde)) && (fecha.isBefore(fechaHasta) || fecha.isEqual(fechaHasta));
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
