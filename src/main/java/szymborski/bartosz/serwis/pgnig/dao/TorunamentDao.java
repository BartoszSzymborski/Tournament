/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szymborski.bartosz.serwis.pgnig.dao;

import java.util.Date;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import szymborski.bartosz.serwis.pgnig.entity.Tournament;

/**
 *
 * @author bartosz.szymborski
 */
@Repository
public class TorunamentDao {

    @Autowired
    SessionFactory sessionFactory;

    @SuppressWarnings("unchecked")
    @Transactional(propagation = Propagation.MANDATORY)
    public Tournament saveTournament(String name) {
        Session session = sessionFactory.getCurrentSession();
        Tournament tournament = new Tournament();
        tournament.setName(name);
        Date date = new Date();
        tournament.setCreationTime(date);
        session.persist(tournament);
        return tournament;
    }

    @SuppressWarnings("unchecked")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Tournament getTournamentId(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Tournament obj = session.load(Tournament.class, id);
        Hibernate.initialize(obj);
        return obj;
    }
    
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Tournament getTournamentName(String tournamentName) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM Tournament t WHERE t.name = :name");
        query.setParameter("name", tournamentName);
        final Tournament retval = (Tournament) query.uniqueResult();
        Hibernate.initialize(retval);
        return retval;
    }
    
     @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<Tournament> getTournamentList() {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("SELECT t.name FROM Tournament t");
        final List list = query.list();
        list.forEach(Hibernate::initialize);
        return list;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Tournament getTournamentById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.load(Tournament.class, id);
    }

}
