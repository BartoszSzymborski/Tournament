/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szymborski.bartosz.serwis.pgnig.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import szymborski.bartosz.serwis.pgnig.entity.TorunamentTemplateItem;
import szymborski.bartosz.serwis.pgnig.entity.TournamentRule;

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

    @Transactional(propagation = Propagation.MANDATORY)
    public Map<TournamentRule,Object> readItemValues(Long templateId){
        Session session = sessionFactory.getCurrentSession();
        String query = "SELECT tti.idTournamentRule, tti.integerValue, tti.booleanValue"
                + " FROM TorunamentTemplateItem tti WHERE tti.idTournamentTemplate.id = :id";
        Query q = session.createQuery(query);
        q.setParameter("id", templateId);
        Map<TournamentRule,Object> retVal = new HashMap<>();
        List<Object[]> list = q.list();
        for(Object[] arr : list){
            retVal.put((TournamentRule) arr[0], arr[1] !=null ? arr[1] : arr[2]);
        }
        return retVal;
    }

}
