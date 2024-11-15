package utn.frc.dis.entity;

import jakarta.persistence.*;

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
