/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szymborski.bartosz.serwis.pgnig.dao;

import java.util.Collection;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import szymborski.bartosz.serwis.pgnig.entity.TournamentRule;

/**
 *
 * @author bartosz.szymborski
 */
@Repository
public class TournamentRuleDao {
    
    @Autowired
    SessionFactory sessionFactory;
    
    @SuppressWarnings("unchecked")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Collection<TournamentRule> getRules(){
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("Select r FROM TournamentRule r");
        final List list = query.list();
        list.forEach(Hibernate::initialize);
        return list;
    }
}
