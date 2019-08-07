/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szymborski.bartosz.serwis.pgnig.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import szymborski.bartosz.serwis.pgnig.dao.TorunamentDao;
import szymborski.bartosz.serwis.pgnig.dao.TournamentEncounterContenderDao;
import szymborski.bartosz.serwis.pgnig.entity.Tournament;
import szymborski.bartosz.serwis.pgnig.entity.TournamentContender;
import szymborski.bartosz.serwis.pgnig.entity.TournamentEncounter;
import szymborski.bartosz.serwis.pgnig.entity.TournamentEncounterContender;

/**
 *
 * @author bartosz.szymborski
 */
@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class TournamentEncounterContenderServiceImpl implements TournamentEncounterContednerService {

    @Autowired
    private TournamentEncounterContenderDao tecd;
    
    @Autowired
    private TorunamentDao td;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public void saveTournamentEncounterContender(Map<TournamentEncounter, List<TournamentContender>> tourEncConMap) {
        List<TournamentEncounterContender> list = new ArrayList<>();
       for(TournamentEncounter enc : tourEncConMap.keySet()){
           for(TournamentContender cont : tourEncConMap.get(enc)){
               TournamentEncounterContender tec = new TournamentEncounterContender();
               tec.setIdTournamentContender(cont);
               tec.setIdTournamentEncounter(enc);
               list.add(tec);
           }
       }
       tecd.saveTournamentEncounterContender(list.toArray(new TournamentEncounterContender[0]));
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public void deleteTournamentEncounterContender(Long idTournament, Long... stages) {
       tecd.deleteFromTournamentEncounterContenders(idTournament,stages);
            
    }

    @Override
    public Map<String, List<TournamentContender>> getContenderByGroup(Long idTournament) {
        return tecd.getTournamentEncounterContender(idTournament);
    }

  

}
