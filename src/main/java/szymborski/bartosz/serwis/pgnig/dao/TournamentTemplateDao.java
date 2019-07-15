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
import szymborski.bartosz.serwis.pgnig.entity.TournamentTemplate;

/**
 *
 * @author bartosz.szymborski
 */
@Repository
public class TournamentTemplateDao {

    @Autowired
    SessionFactory sessionFactory;

    @Transactional(propagation = Propagation.MANDATORY)
    public TournamentTemplate saveTemplate(String template) {
        Session session = sessionFactory.getCurrentSession();
        TournamentTemplate tournamentTemplate = new TournamentTemplate();
        tournamentTemplate.setName(template);
        session.persist(tournamentTemplate);
        return tournamentTemplate;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public String getTemplateName(String templateName) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("SELECT t.name FROM TournamentTemplate t WHERE t.name = :name");
        query.setParameter("name", templateName);
        return (String) query.uniqueResult();

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<TournamentTemplate> getTournamentTemplateList() {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("SELECT t.name FROM TournamentTemplate t");
        final List list = query.list();
        list.forEach(Hibernate::initialize);
        return list;
    }
    
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public TournamentTemplate getTournamentTemplateName(String templateName){
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM TournamentTemplate t WHERE t.name = :name");
        query.setParameter("name", templateName);
        return (TournamentTemplate) query.uniqueResult();
    }
    

}
