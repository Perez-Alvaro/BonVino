package utn.frc.dis.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "RegionVitivinicola")
public class RegionVitivinicola {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer idRegionVitivinicola;

    @Column(name = "nombre", nullable = false)
    private String nomeRegionVitivinico;

    @Column(name = "descripcion")
    private String descripsRegionVitivinico;

    @ManyToOne
    @JoinColumn(name = "provincia_id", nullable = true)
    private Provincia provincia;

    public RegionVitivinicola() {}

    public RegionVitivinicola(Integer idRegionVitivinicola, String nomeRegionVitivinico, String descripsRegionVitivinico) {
        this.idRegionVitivinicola = idRegionVitivinicola;
        this.nomeRegionVitivinico = nomeRegionVitivinico;
        this.descripsRegionVitivinico = descripsRegionVitivinico;
    }

    public Integer getIdRegionVitivinicola() {
        return idRegionVitivinicola;
    }

    public String getNomeRegionVitivinico() {
        return nomeRegionVitivinico;
    }

    public void setNomeRegionVitivinico(String nomeRegionVitivinico) {
        this.nomeRegionVitivinico = nomeRegionVitivinico;
    }

    public String getDescripsRegionVitivinico() {
        return descripsRegionVitivinico;
    }

    public void setDescripsRegionVitivinico(String descripsRegionVitivinico) {
        this.descripsRegionVitivinico = descripsRegionVitivinico;
    }

    public Provincia getProvincia() {
        return provincia;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }
}
