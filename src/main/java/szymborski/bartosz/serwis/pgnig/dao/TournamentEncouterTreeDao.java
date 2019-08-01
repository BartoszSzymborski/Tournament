/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szymborski.bartosz.serwis.pgnig.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import szymborski.bartosz.serwis.pgnig.entity.TournamentEncounterTree;

/**
 *
 * @author bartosz.szymborski
 */
@Repository
public class TournamentEncouterTreeDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Transactional(propagation = Propagation.MANDATORY)
    public void saveTournamentEncounterTree(TournamentEncounterTree... tet) { // vargs wiele elementów
        Session session = sessionFactory.getCurrentSession();
        Stream.of(tet).forEach(session::persist); //bo vargs to tablica trzeba użyć strumienia
    }
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<TournamentEncounterTree> getTournamenEncouterTreeLowerStage(Long idTournamentEncounter){
        Session session = sessionFactory.getCurrentSession();
        String pSQL = "FROM TournamentEncounterTree tet WHERE tet.idtournamentencounternext.id = :idTournamentEncounter";
        Query query = session.createQuery(pSQL);
        query.setParameter("idTournamentEncounter", idTournamentEncounter);
        List<TournamentEncounterTree> listTree = query.list();
        listTree.forEach(Hibernate::initialize);
        return listTree;
    }

}
