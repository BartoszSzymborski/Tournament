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
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import szymborski.bartosz.serwis.pgnig.dao.TorunamentDao;
import szymborski.bartosz.serwis.pgnig.dao.TournamentEncouterDao;
import szymborski.bartosz.serwis.pgnig.dao.TournamentRuleSetDao;
import szymborski.bartosz.serwis.pgnig.entity.Tournament;
import szymborski.bartosz.serwis.pgnig.entity.TournamentEncounter;
import szymborski.bartosz.serwis.pgnig.entity.TournamentRuleSet;
import szymborski.bartosz.serwis.pgnig.enums.TournamentRuleEnum;

/**
 *
 * @author bartosz.szymborski
 */

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class TournamentEncounterServiceImpl implements TournamentEncounterService{

    @Autowired
    private TournamentRuleSetDao trsd;
    
    @Autowired
    private TournamentEncouterDao ted;
    
    @Autowired
    private TorunamentDao td;
    
    @Override
    public List<TournamentRuleSet> getTournamentRules() {
       return trsd.getTournamentRules();
    }

    @Override
    @Async
    @Transactional(propagation = Propagation.SUPPORTS)
    public void saveTournamentEncounter(Tournament tournament, Map<String, Object> rules) {
        short liczbaDruzyn = (short) rules.get(TournamentRuleEnum.LICZBA_DRUZYN.name());
        boolean fazaGrupowa = (boolean) rules.get(TournamentRuleEnum.FAZA_GRUPOWA.name());
        
        List<TournamentEncounter> encountersToSave = new ArrayList<>();
        if(fazaGrupowa){
           short liczbaGrup = (short) rules.get(TournamentRuleEnum.LICZBA_GRUP.name());
           char gruopSymbol = 'A';
            for (int i = 0; i < liczbaGrup; i++) {
                TournamentEncounter encounter = new TournamentEncounter();
                encountersToSave.add(encounter);
                encounter.setStage(0);
                encounter.setAddInfo(String.valueOf(gruopSymbol));
                encounter.setCzyFazaGrupowa(fazaGrupowa);
                gruopSymbol = (char) (gruopSymbol + 1);
                
            }
        }
        encountersToSave.forEach(enc -> enc.setIdTournament(tournament));
        ted.saveTournamentEncounter(encountersToSave);
    }
    
}
