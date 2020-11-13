package org.example.dependent.service;




import org.example.dependent.pojo.Dependent;
import org.example.dependent.pojo.Response;
import org.example.dependent.repository.DependentRepo;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.persistence.EntityManager;
import java.util.*;

@Service
public class DependentService {

    @Autowired
    DependentRepo repository;

    EntityManager entityManager;

    public DependentService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    public Dependent getDependent(int id) {
        return repository.getOne(id);
    }


    public List<Dependent> getDependents() {
        return repository.findAll();
    }

    public List<Integer> getAllIds() {
        List<Integer> listId = new ArrayList<>();

        for (int i = 1; i < repository.findAll().size() + 1; i++) {

            listId.add(i);
        }
        return listId;

    }

    public void postDependent(Dependent dependent) {

        repository.save(dependent);
    }

    public void deleteDependentById(int id) {

        repository.deleteById(id);
    }

    public String getStringDependent(int id){

        Session currentSession = entityManager.unwrap(Session.class);

        Query theQuery =
                currentSession.createQuery("from Dependent where id=:idUtente",Dependent.class);
        theQuery.setParameter("idUtente",id);

        List<Dependent> dipendenti = theQuery.getResultList();

        StringBuilder uscita = new StringBuilder();


        uscita.append("Id: " + dipendenti.get(0).getId());
        uscita.append(" - Cognome: " + dipendenti.get(0).getCognome());
        uscita.append(" - Nome: " + dipendenti.get(0).getNome());

        return uscita.toString();


    }

    public Response getResponse(int id){
        Session currentSession = entityManager.unwrap(Session.class);

        Query theQuery =
                currentSession.createQuery("from Dependent where id=:idUtente",Dependent.class);
        theQuery.setParameter("idUtente",id);

        List<Dependent> dipendenti = theQuery.getResultList();


        Response response = new Response(dipendenti.get(0).getId(),dipendenti.get(0).getCognome(),dipendenti.get(0).getNome());


        return response;
    }
}
