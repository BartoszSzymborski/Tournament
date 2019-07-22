/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szymborski.bartosz.serwis.pgnig.dao;

import java.util.List;
import java.util.Map;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import szymborski.bartosz.serwis.pgnig.entity.Tournament;
import szymborski.bartosz.serwis.pgnig.entity.TournamentRule;
import szymborski.bartosz.serwis.pgnig.entity.TournamentRuleSet;

/**
 *
 * @author bartosz.szymborski
 */
@Repository
public class TournamentRuleSetDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Transactional(propagation = Propagation.MANDATORY)
    public void saveTournamentRules(Map<TournamentRule, Object> map, Tournament tournament) {
        Session session = sessionFactory.getCurrentSession();
        for (TournamentRule rule : map.keySet()) {
            Object obj = map.get(rule);
            TournamentRuleSet tournamentRuleSet = new TournamentRuleSet();
            tournamentRuleSet.setTournament(tournament);
            tournamentRuleSet.setTournamentRule(rule);
            if (obj instanceof Boolean) {
                tournamentRuleSet.setBooleanValue((Boolean) obj);
            } else {
                tournamentRuleSet.setIntegerValue((Short) obj);
            }
            session.persist(tournamentRuleSet);
        }

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<TournamentRuleSet> getTournamentRules() {
        Session session = sessionFactory.getCurrentSession();
        String psqlQuery = "FROM TournamentRuleSet";
        Query query = session.createQuery(psqlQuery);
        final List list = query.list();
        list.forEach(Hibernate::initialize);
        return list;
    }
}
