package utn.frc.dis.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Resenia")
public class Resenia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "comentario")
    private String comentario;

    @Column(name = "premium")
    private boolean premium;

    @Column(name = "fecha_resenia")
    private LocalDateTime fechaResenia;

    @Column(name = "puntaje")
    private Double puntaje;

    @ManyToOne
    @JoinColumn(name = "vino_id", nullable = false)
    private Vino vino;

    public Resenia() {}

    public Resenia(int id, String comentario, boolean premium, LocalDateTime fechaResenia, Double puntaje) {
        this.id = id;
        this.comentario = comentario;
        this.premium = premium;
        this.fechaResenia = fechaResenia;
        this.puntaje = puntaje;
    }

    public int getId() {
        return id;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public boolean isPremium() {
        return premium;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }

    public boolean sosDeSomelier(){
        if (this.premium = true) {
            return true;
        }
        return false;
    }

    public LocalDateTime getFechaResenia() {
        return fechaResenia;
    }

    public void setFechaResenia(LocalDateTime fechaResenia) {
    this.fechaResenia = fechaResenia;}

    public Double getPuntaje() {
        return puntaje;
    }
    public void setPuntaje(Double puntaje) {
        this.puntaje = puntaje;
    }
    public Vino getVino() {
        return vino;
    }
    public void setVino(Vino vino) {
        this.vino = vino;
    }

    }


