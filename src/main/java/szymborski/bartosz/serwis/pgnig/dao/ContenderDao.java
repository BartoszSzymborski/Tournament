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
import szymborski.bartosz.serwis.pgnig.entity.Contender;
import szymborski.bartosz.serwis.pgnig.entity.TournamentRuleSet;

/**
 *
 * @author bartosz.szymborski
 */
@Repository
public class ContenderDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<Contender> getContendersList(){
        Session session = sessionFactory.getCurrentSession();
        String queryPSQL = "SELECT c.name FROM Contender c";
        Query query = session.createQuery(queryPSQL);
        final List list = query.list();
        list.forEach(Hibernate::initialize);
        return list;
    }
    
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Contender getContenderName(String contenderName){
        Session session = sessionFactory.getCurrentSession();
        String queryPSQL = "FROM Contender c WHERE c.name = :name";
        Query query = session.createQuery(queryPSQL);
        query.setParameter("name", contenderName);
        return (Contender) query.uniqueResult();
    }
    
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public TournamentRuleSet getRuleValueForTournament(Long idTournament, String ruleName){
        Session session = sessionFactory.getCurrentSession();
        String queryPSQL = "SELECT trs FROM TournamentRuleSet trs WHERE trs.tournamentRule.name = :ruleName AND trs.tournament.id = :idTournament";
        Query query = session.createQuery(queryPSQL);
        query.setParameter("ruleName", ruleName);
        query.setParameter("idTournament", idTournament);
        Object obj = query.uniqueResult();
        Hibernate.initialize(obj);
        return (TournamentRuleSet) obj;
    }
    
    @SuppressWarnings("unchecked")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Contender getContenderId(Long id){
        Session session = sessionFactory.getCurrentSession();
        Contender contender = session.load(Contender.class, id);
        Hibernate.initialize(contender);
        return contender;
    }
    
}
