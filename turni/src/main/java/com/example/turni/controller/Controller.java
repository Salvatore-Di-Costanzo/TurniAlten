package com.example.turni.controller;

import com.example.turni.client.FeignDependent;
import com.example.turni.pojo.Response;
import com.example.turni.pojo.Turno;
import com.example.turni.repository.TurnoRepo;
import com.example.turni.service.RealizzaTurni;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;


@RestController
public class Controller {


    private EntityManager entityManager;

    public Controller(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Autowired
    RealizzaTurni realizzaTurni;

    @Autowired
    TurnoRepo turnoRepo;

    @Autowired
    FeignDependent feignDependent;

    @GetMapping("/setTurni/{numGiorni}")
    public String setTurni(@PathVariable String numGiorni) {

        return realizzaTurni.calcolaTurni(Integer.parseInt(numGiorni));

    }

    @GetMapping("/getTurni")
    public List<Turno> getTurni() {

        return turnoRepo.findAll();

    }

    @GetMapping("/getDependentTurni/{data}")
    public String getDependentTurni(@PathVariable String data) {

        StringBuilder result = new StringBuilder();

        Session currentSession = entityManager.unwrap(Session.class);

        Query theQuery =
                currentSession.createQuery("select idDependent from Turno where date=:dateSet", Integer.class);
        theQuery.setParameter("dateSet", data);


        List<Integer> idDipendenti = theQuery.getResultList();

        for (Integer id : idDipendenti) {
            result.append(feignDependent.getDateDependent(id.toString()));
            result.append("\n");
        }

        return result.toString();
    }


}
