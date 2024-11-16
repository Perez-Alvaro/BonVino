package utn.frc.dis.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Varietal")
public class Varietal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "porcentajeComposicion")
    private double porcentajeComposicion;


    public Varietal(Integer id, String descripcion, double porcentajeComposicion) {
        this.id = id;
        this.descripcion = descripcion;
        this.porcentajeComposicion = porcentajeComposicion;
    }

    public Varietal() {
        super();
    }

    public int getId() {
        return id;

    }
    public void setId(int id) {
        this.id = id;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public double getPorcentajeComposicion() {
        return porcentajeComposicion;
    }
    public void setPorcentajeComposicion(double porcentajeComposicion) {
        this.porcentajeComposicion = porcentajeComposicion;
    }




}
