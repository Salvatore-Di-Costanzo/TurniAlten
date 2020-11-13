package org.example.dependent.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="dependent")
public class Dependent  {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer id;

    @Column(name = "cognome")
    public String cognome;

    @Column(name ="nome")
    public String nome;


    @Column(name = "salary")
    public Float salary;

    public Dependent(String nome, String cognome, Float salary) {
        this.nome = nome;
        this.cognome = cognome;
        this.salary = salary;
    }

    public Dependent() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public Float getSalary() {
        return salary;
    }

    public void setSalary(Float salary) {
        this.salary = salary;
    }
}
