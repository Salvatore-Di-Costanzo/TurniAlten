package com.example.turni.pojo;



import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="turno")
public class Turno implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "data")
    private String date;

    @Column(name = "id_dependent")
    private Integer idDependent;

    public Turno() {
        
    }

    public Turno(String date, Integer idDependent) {
        this.date = date;
        this.idDependent = idDependent;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getIdDependent() {
        return idDependent;
    }

    public void setIdDependent(Integer idDependent) {
        this.idDependent = idDependent;
    }

    @Override
    public String toString() {
        return "Turni{" +
                "id=" + id +
                ", date=" + date +
                ", id_dependent=" + idDependent +
                '}';
    }
}
