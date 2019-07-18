/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szymborski.bartosz.serwis.pgnig.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import szymborski.bartosz.serwis.pgnig.entity.Contender;
import szymborski.bartosz.serwis.pgnig.entity.Tournament;
import szymborski.bartosz.serwis.pgnig.entity.TournamentContender;

/**
 *
 * @author bartosz.szymborski
 */
@Repository
public class TournamentContenderDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    @SuppressWarnings("unchecked")
    @Transactional(propagation = Propagation.MANDATORY)
    public TournamentContender saveTournamentContender(Contender contender, Tournament tournament){
        Session session = sessionFactory.getCurrentSession();
        TournamentContender tournamentContender = new TournamentContender();
        tournamentContender.setIdContender(contender);
        tournamentContender.setTournament(tournament);
        session.persist(tournamentContender);
        return tournamentContender;
        
    }
    
}
