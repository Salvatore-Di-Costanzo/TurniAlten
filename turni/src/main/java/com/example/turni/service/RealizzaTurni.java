package com.example.turni.service;

import com.example.turni.client.FeignDependent;
import com.example.turni.pojo.Response;
import com.example.turni.pojo.Turno;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Configuration
@EnableFeignClients
@Slf4j
public class RealizzaTurni {

    @Autowired
    FeignDependent feignDependent;

    private EntityManager entityManager;

    public RealizzaTurni(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    int countDays = 0;

    public String calcolaTurni(int numGiorni) {

        countDays = 0;
        boolean check = true;


        LocalDate baseDate = LocalDate.parse(getMaxDate());
        LocalDate setDate;

        log.info("\n\nData del db: " + baseDate.toString() + "\n\n");

        /// Importo gli Id dei dipendenti
        List<Integer> idDependent = feignDependent.getIds();

        List<Integer> idGiornata = new ArrayList<>();

        StringBuilder dataFine = new StringBuilder();

        /// Algoritmo generazione turni
        for (int i = 1; i <= numGiorni * 4; i++) {

            // Calcolo la data
            if (i == 1 && baseDate.isEqual(LocalDate.parse("1900-01-01"))) {
                baseDate = LocalDate.now();
                check = false;
            }


            // Se il giorno successivo è Sabato o Domenica saltalo
            if (baseDate.plusDays(countDays).getDayOfWeek() == DayOfWeek.SATURDAY) countDays += 2;
            if (baseDate.plusDays(countDays).getDayOfWeek() == DayOfWeek.SUNDAY) countDays++;

            // Se la data rilevata inizialmente non è la data di default
            if (check) {
                // Se la data odierna è precedenta all'ultima data inserita nel db allora incrementala poichè
                // la data estratta è già stata calcolata
                if (i == 1 && LocalDate.now().isBefore(baseDate)) countDays++;
                // Se la nuova data è di domenica aggiungi due giorni, se è di sabato aggiungi un giorno
                if (baseDate.plusDays(countDays).getDayOfWeek() == DayOfWeek.SATURDAY) countDays += 2;
                if (baseDate.plusDays(countDays).getDayOfWeek() == DayOfWeek.SUNDAY) countDays++;
                // Se la data odierna coincide con la data estatta dal DB ( caso in cui al primo avvio sia satato calcolato 1 solo giorno )
                if (i == 1 && LocalDate.now().isEqual(baseDate)) {
                    // Aggiungi 1 giorno portando così la data al giorno successivo e verifica che questa non sia sabato a domenica
                    countDays++;
                    if (baseDate.plusDays(countDays).getDayOfWeek() == DayOfWeek.SATURDAY) countDays += 2;
                    if (baseDate.plusDays(countDays).getDayOfWeek() == DayOfWeek.SUNDAY) countDays++;
                }
            }
            setDate = baseDate.plusDays(countDays);

            // Verifico se devo avanzare con la data, poichè il numero max di dipendenti è 4 per ogni giorno
            // ogni qualvolta elaboro 4 dipendenti passo al giorno dopo.
            if (i % 4 == 0) {
                countDays++;
                idGiornata.clear();
            }


            // Mappo il turno da inserire
            Turno turno = new Turno();
            StringBuilder data = new StringBuilder();
            data.append(setDate.getYear());
            data.append("-");
            if (setDate.getMonthValue() >= 1 && setDate.getMonthValue() <= 9)
                data.append("0" + setDate.getMonthValue());
            else
                data.append(setDate.getMonthValue());
            data.append("-");
            if (setDate.getDayOfMonth() >= 1 && setDate.getDayOfMonth() <= 9)
                data.append("0" + setDate.getDayOfMonth());
            else
                data.append(+setDate.getDayOfMonth());
            log.info("Data caricata: " + data.toString());
            turno.setDate(data.toString());
            int randomIdex = ThreadLocalRandom.current().nextInt(0, idDependent.size());
            while (idGiornata.contains(idDependent.get(randomIdex)))
                randomIdex = ThreadLocalRandom.current().nextInt(0, idDependent.size());

            turno.setIdDependent(idDependent.get(randomIdex));

            idGiornata.add(idDependent.get(randomIdex));


            /// Rimuovo dalla lista l'ID dell'utente in modo che non venga ripescato
            idDependent.remove(randomIdex);


            /// Verifico che la lista non debba essere ricaricata
            if (idDependent.isEmpty()) {
                idDependent.addAll(feignDependent.getIds());
                idDependent.removeAll(idGiornata);
            }

            /// Inserisco il turno del DB

            Session currentSession = entityManager.unwrap(Session.class);

            currentSession.beginTransaction();
            currentSession.save(turno);
            currentSession.getTransaction().commit();

            if (i == numGiorni * 4) {
                dataFine.append(setDate.getYear());
                dataFine.append("-");
                dataFine.append(setDate.getMonthValue());
                dataFine.append("-");
                dataFine.append(setDate.getDayOfMonth());
            }

        }

        //LocalDate setDate = LocalDate.now();

        StringBuilder dataInizio = new StringBuilder();

        dataInizio.append(baseDate.getYear());
        dataInizio.append("-");
        dataInizio.append(baseDate.getMonthValue());
        dataInizio.append("-");
        dataInizio.append(baseDate.getDayOfMonth());


        return "Calcolo turni effettuato dal: " + dataInizio.toString() + " al: " + dataFine.toString();

    }

    public String getMaxDate() {

        Session currentSession = entityManager.unwrap(Session.class);

        Query idMax =
                currentSession.createQuery("select max(id) from Turno", Integer.class);

        if (idMax.getSingleResult() == null)
            return "1900-01-01";

        Query theQuery =
                currentSession.createQuery("from Turno where id = (select max(id) from Turno)", Turno.class);


        List<Turno> turno = theQuery.getResultList();

        return turno.get(0).getDate();


    }


}
