/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szymborski.bartosz.serwis.pgnig.dao;

import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import szymborski.bartosz.serwis.pgnig.entity.TournamentEncounter;

/**
 *
 * @author bartosz.szymborski
 */
@Repository
public class TournamentEncouterDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    @Transactional(propagation = Propagation.MANDATORY)
    public void saveTournamentEncounter(List<TournamentEncounter> list) {
        Session session = sessionFactory.getCurrentSession();
        list.forEach(session::persist);
    }
    @Transactional(propagation = Propagation.MANDATORY)
    public List<TournamentEncounter> getTournamentEncounter (Long idTourament){
        Session session = sessionFactory.getCurrentSession();
        String qPSQL = "FROM TournamentEncounter te WHERE te.idTournament = :idTourament ";
        Query query = session.createQuery(qPSQL);
        query.setParameter("idTourament", idTourament);
        List<TournamentEncounter> listTE = query.list();
        listTE.forEach(Hibernate::initialize);
        return listTE;
    }
    
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public TournamentEncounter getTournamentEncounterMaxStageForTournament(Long idTournament){
        Session session = sessionFactory.getCurrentSession();
        String postSQL = "FROM TournamentEncounter te WHERE te.idTournament.id = :idTournament AND te.stage ="
                + " (SELECT MAX(te2.stage) FROM TournamentEncounter te2 WHERE te2.idTournament.id = te.idTournament.id)"; //występuje tu subzapytanie bo inaczej bym wyciągnął max stage dla wszystkich tec a nie o konkretnym idTour
        Query query = session.createQuery(postSQL);
        query.setParameter("idTournament", idTournament);
        return (TournamentEncounter) query.uniqueResult();
    }
    
}
