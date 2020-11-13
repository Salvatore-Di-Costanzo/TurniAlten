package com.example.turni.service;

import com.example.turni.client.FeignDependent;
import com.example.turni.controller.Controller;
import com.example.turni.pojo.Response;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Service
public class HTMLService {

    @Autowired
    FeignDependent feignDependent;

    private EntityManager entityManager;

    public HTMLService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    public List<Response> getList(String data) {
        List<Response> list = new ArrayList<>();

        Session currentSession = entityManager.unwrap(Session.class);


        Query theQuery =
                currentSession.createQuery("select idDependent from Turno where date=:dateSet", Integer.class);
        theQuery.setParameter("dateSet", data);

        List<Integer> idDipendenti = theQuery.getResultList();

        for (Integer id : idDipendenti) list.add(feignDependent.getResponse(id));


        return list;
    }
}
