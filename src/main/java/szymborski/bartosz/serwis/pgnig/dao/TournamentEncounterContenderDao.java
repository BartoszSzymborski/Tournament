/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szymborski.bartosz.serwis.pgnig.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import szymborski.bartosz.serwis.pgnig.entity.TournamentContender;
import szymborski.bartosz.serwis.pgnig.entity.TournamentEncounterContender;

/**
 *
 * @author bartosz.szymborski
 */
@Repository
public class TournamentEncounterContenderDao {

    @Autowired
    private SessionFactory sessionFactory;

    @SuppressWarnings("unchecked")
    @Transactional(propagation = Propagation.MANDATORY)
    public void saveTournamentEncounterContender(TournamentEncounterContender... vargs) {
        Session session = sessionFactory.getCurrentSession();
        Stream.of(vargs).forEach(session::persist);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Map<String, List<TournamentContender>> getTournamentEncounterContender(Long idTournament) {
        Map<String, List<TournamentContender>> retVal = new HashMap<>();
        Session session = sessionFactory.getCurrentSession();
        String psgSQL = "SELECT te.addInfo, tc FROM TournamentEncounterContender tec "
                + " JOIN tec.idTournamentEncounter te"
                + " JOIN tec.idTournamentContender tc" //tutaj nie ma ON po JOIN bo Hibernate domyślnie wie że to jest łączenie, naszym zadaniem jest wskazanie mu co ma łączyć 
                + " JOIN tc.idContender c "
                + " JOIN te.idTournament t WHERE t.id = :idTournament"; //where to filtrowanie zestawu
        Query query = session.createQuery(psgSQL);
        query.setParameter("idTournament", idTournament);
        final List<Object[]> listTourEncCon = query.list();
        for (Object[] array : listTourEncCon) {
            String str = (String) array[0];
            TournamentContender cont = (TournamentContender) array[1];
            List<TournamentContender> list = retVal.get(str);
            if (list == null) {
                retVal.put(str, new ArrayList<>());
                list = retVal.get(str);
            }
            list.add(cont);
        }
        return retVal;
    }

    @SuppressWarnings("unckecked")
    @Transactional(propagation = Propagation.MANDATORY)
    public void deleteFromTournamentEncounterContender(TournamentEncounterContender... tec) {
        Session session = sessionFactory.getCurrentSession();
        Stream.of(tec).forEach(session::delete);
    }

    @SuppressWarnings("unchecked")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<TournamentEncounterContender> getTournamentEncounterContenderForStage(Long idTournament, Long... stages) {
        Session session = sessionFactory.getCurrentSession();
        String psgSQL = "SELECT tec FROM TournamentEncounterContender tec "
                + " JOIN tec.idTournamentEncounter te"
                + " JOIN te.idTournament t WHERE t.id = :idTournament AND te.stage IN :stages"; //jest ponieważ dotyczy wielu argumentów
        Query query = session.createQuery(psgSQL);
        query.setParameter("idTournament", idTournament);
        query.setParameterList("stages", stages);
        final List<TournamentEncounterContender> list = query.list();
        return list;
    }
    
    @Transactional(propagation = Propagation.MANDATORY)
    public void deleteFromTournamentEncounterContenders(Long idTournament, Long... stages){
       getTournamentEncounterContenderForStage(idTournament,stages).forEach(this::deleteFromTournamentEncounterContender);
       Session session = sessionFactory.getCurrentSession();
       session.flush();//wymuszenie żeby najpierw zrzucił delete, nie należy tego nadużywać, Hibernate sam to robi, ale ta sytuacja to wymusza
    }
    
}
