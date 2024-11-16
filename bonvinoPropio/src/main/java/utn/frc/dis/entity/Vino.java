package utn.frc.dis.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Vino")
public class Vino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "nombre", nullable = false)
    private String name;

    @Column(name = "precio_ars")
    private Integer precioARS;

    @Column(name = "nota_categoria")
    private String notaDeCategoria;

    @Column(name = "imagen")
    private String imagen;

    @ManyToOne
    @JoinColumn(name = "bodega_id", nullable = true)
    private Bodega bodega;

    @OneToMany(mappedBy = "vino", fetch = FetchType.EAGER)
    private List<Resenia> resenias;

    @OneToOne
    @JoinColumn(name = "varietal_id", referencedColumnName = "id", nullable = false)
    private Varietal varietal;


    public Vino() {
        super();
    }

    public Vino(Integer id, String name, Integer precioARS, String notaDeCategoria, String imagen) {
        this.id = id;
        this.name = name;
        this.precioARS = precioARS;
        this.notaDeCategoria = notaDeCategoria;
        this.imagen = imagen;
    }

    public Boolean esTipoDeResenia(LocalDateTime fechadesde, LocalDateTime fechahasta, Boolean esPremium) {
        // Verificar que la lista de reseñas no sea nula
        if (this.resenias == null || this.resenias.isEmpty()) {
            return false;
        }

        // Recorrer todas las reseñas
        for (Resenia resenia : resenias) {
            // Verificar si la reseña es del tipo especificado (premium o no)

            if (esPremium.equals(resenia.isPremium())) {
                // Obtener la fecha de la reseña
                LocalDateTime fechaResenia = resenia.getFechaResenia();

                // Verificar si la fecha está dentro del rango especificado
                if ((fechaResenia.isEqual(fechadesde) || fechaResenia.isAfter(fechadesde)) &&
                        (fechaResenia.isEqual(fechahasta) || fechaResenia.isBefore(fechahasta))) {
                    // Si encuentra una reseña válida, devolver true
                    return true;
                }
            }
        }

        // Si no se encuentra ninguna reseña válida, devolver false
        return false;
    }
    public Varietal getVarietal() {
        return varietal;
    }

    public Double obtenerPromedioPuntajeResenias(){
        Double puntajeResenias = 0.0;
        for (Resenia resenia : resenias) {
            puntajeResenias += resenia.getPuntaje();
        }
        puntajeResenias = puntajeResenias / resenias.size();
        return puntajeResenias;


    }

    public Double obtenerPromedioSommelierPuntajeResenias(){
        Double puntajeResenias = 0.0;
        for (Resenia resenia : resenias) {
            System.out.println(resenia.isPremium());
            if (resenia.isPremium()) {
                puntajeResenias += resenia.getPuntaje();
            }

        }
        puntajeResenias = puntajeResenias / resenias.size();
        return puntajeResenias;

    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrecioARS() {
        return precioARS;
    }

    public void setPrecioARS(Integer precioARS) {
        this.precioARS = precioARS;
    }

    public String getNotaDeCategoria() {
        return notaDeCategoria;
    }

    public void setNotaDeCategoria(String notaDeCategoria) {
        this.notaDeCategoria = notaDeCategoria;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Bodega getBodega() {
        return bodega;
    }

    public void setBodega(Bodega bodega) {
        this.bodega = bodega;
    }

    public List<Resenia> getResenias() {
        return resenias;
    }
}
