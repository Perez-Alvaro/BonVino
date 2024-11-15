package utn.frc.dis.controllers;

import utn.frc.dis.Interface.PantallaGRRV;
import utn.frc.dis.entity.Bodega;
import utn.frc.dis.entity.Resenia;
import utn.frc.dis.entity.Vino;

import java.time.LocalDateTime;
import java.util.*;



import java.util.Map;
import java.util.stream.Collectors;

import utn.frc.dis.service.VinoService;


public class GestorGRRV {

    private LocalDateTime fechadesde;
    private LocalDateTime fechahasta;
    private String tipoDeResenia;
    private String formaVisualizacion;
    private List<Resenia> resenias;
    private List<Vino> vinos;

    //private VinoService vinoService;

    private PantallaGRRV pantalla;

    public GestorGRRV() {
        //this.pantalla = pantalla;
    }



    public void procesarFechas(LocalDateTime fechaDesde, LocalDateTime fechaHasta){
        this.fechadesde = fechaDesde;
        this.fechahasta = fechaHasta;
    }

    public void buscarVinos() {
        VinoService vinoService = new VinoService();
        vinos = vinoService.ConseguirVinos();
        //vinoService sería como un esquema de persistencia que se encarga de la comunicacion con la bd (todos los service hacen lo mismo)
        //no se si es correcto pero como no hay grafico sobre como se maneja la persistencia en el diagrama de clases o de interaccion es lo mejor que se me ocurrió
        //su metodo ConseguirVinos() me devuelve una lista con todos los vinos
        Map<Integer, Double> promedioCalificaciones = new HashMap<>();
        //promedioCalificaciones es un diccionario que guarda el promedio de las calificaciones y tiene como llave el id del vino

        for (Vino vino : vinos) {
            System.out.println(vino.getName());
            Double sumaCalif = 0.0;
            int cantidadResenias = 0;
            for (Resenia resenia : vino.getResenias()) {
                sumaCalif += resenia.getPuntaje();
                cantidadResenias++;
                System.out.println(resenia.sosDeSomelier());
            }
            //creo que el calculo de el promedio se debería cambiar para que lo haga como metodo del vino
            //falta aplicar los filtros para el tipo de resenia y las estrategias
            double promedio = (cantidadResenias > 0) ? sumaCalif / cantidadResenias : 0.0;

            promedioCalificaciones.put(vino.getId(), promedio);
            System.out.println("Promedio: " + promedio);
        }

        ordenarVinosPorCalificacion(promedioCalificaciones);
        seleccionar10MejoresCalificados();
        System.out.println("\n=== datos de los vinos ===");
        obtenerDatosVinos();
        //para ver si funciona el gestor de forma rapida puse los otros métodos acá, pero creo que se deberían cambiar
    }


    public void ordenarVinosPorCalificacion(Map<Integer, Double> promedioCalificaciones) {
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

    }

    public void seleccionar10MejoresCalificados() {
        // Asegurarse de que la lista esté ordenada antes de filtrar
        // creo que puede dar error si le damos una lista con menos de 10 vinos (que puede pasar si le aplicamos los filtros a la lista actual)
        if (vinos != null && vinos.size() > 10) {
            vinos = vinos.subList(0, 10); // Mantener solo los primeros 10 elementos
        }
        //esto solo está para ver como es la lista de vinos cuando se la recorta a solo 10 elementos
        System.out.println("\n=== Top 10 Vinos ===");
        for (Vino vino : vinos) {
            System.out.println("Vino: " + vino.getName());
        }
    }

    public void obtenerDatosVinos() {
        String vinoNombre = "";
        Integer vinoPrecio = 0;
        //cambiar luego a double el precio (no se como se me pasó)
        Bodega vinoBodega = null;
        String bodegaRegion = "";
        String regionPais = "";

        for (Vino vino : vinos) {
            vinoNombre = vino.getName();
            vinoPrecio = vino.getPrecioARS();
            vinoBodega = vino.getBodega();
            bodegaRegion = vinoBodega.getRegion().getNomeRegionVitivinico();
            regionPais = vinoBodega.getRegion().getProvincia().getPais().getName();

            //acá imprime todos los datos de vinos como demostración

            System.out.println(
                    "Vino: " + vinoNombre +
                            ", Precio: " + vinoPrecio + " ARS" +
                            ", Bodega: " + vinoBodega.getNombre() +
                            ", Región: " + bodegaRegion +
                            ", País: " + regionPais
            );
            //faltaria la logica para que pueda guardar los datos en el archivo excel o las otras funciones

        }


    }




}
