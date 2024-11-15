package utn.frc.dis.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Bodega")
public class Bodega {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "coordenadas")
    private String coordenadas;

    @Column(name = "historia")
    private String historia;

    @Column(name = "periodo_actualizacion")
    private String periodoActualizacion;

    @ManyToOne
    @JoinColumn(name = "region_id", nullable = true)
    private RegionVitivinicola region;

    public Bodega() {}

    public Bodega(Integer id, String nombre, String descripcion, String coordenadas, String historia) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.coordenadas = coordenadas;
        this.historia = historia;
        this.periodoActualizacion = historia;
    }

    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(String coordenadas) {
        this.coordenadas = coordenadas;
    }

    public String getHistoria() {
        return historia;
    }

    public void setHistoria(String historia) {
        this.historia = historia;
    }

    public String getPeriodoActualizacion() {
        return periodoActualizacion;
    }

    public void setPeriodoActualizacion(String periodoActualizacion) {
        this.periodoActualizacion = periodoActualizacion;
    }

    public RegionVitivinicola getRegion() {
        return region;
    }

    public void setRegion(RegionVitivinicola region) {
        this.region = region;
    }
}
