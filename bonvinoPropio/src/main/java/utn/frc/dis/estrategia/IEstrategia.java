package utn.frc.dis.estrategia;

import utn.frc.dis.entity.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface IEstrategia {
    Double calcularPromedioClasificacion(Double sumaCalif, Integer cantidadResenias);
    boolean validarFechaResenia(LocalDateTime fecha, LocalDateTime fechaDesde, LocalDateTime fechaHasta);
    List<Vino> buscarVinos(LocalDateTime fechaDesde, LocalDateTime fechaHasta);

    public List<Vino> ordenarVinosPorCalificacion(Map<Integer, Double> promedioCalificaciones, List<Vino> vinos);
}
