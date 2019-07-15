/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szymborski.bartosz.serwis.pgnig.dao;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import szymborski.bartosz.serwis.pgnig.entity.TorunamentTemplateItem;

/**
 *
 * @author bartosz.szymborski
 */
@Repository
public class TournamentTemplateItemDao {

    @Autowired
    SessionFactory sessionFactory;

    @Transactional(propagation = Propagation.MANDATORY)
    public List<TorunamentTemplateItem> saveTournamentTemplateItem(List<TorunamentTemplateItem> item) {
        Session session = sessionFactory.getCurrentSession();
        item.forEach(session::persist); //dla każdego elementu z listy zapisuje wartość
        return item;
    }

//    @Transactional(propagation = Propagation.REQUIRES_NEW)
//    public void deleteTournamentTemplateItem(TorunamentTemplateItem item) {
//        Session session = sessionFactory.getCurrentSession();
//        session.delete(item);
//    }

}
