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
    
}
