/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szymborski.bartosz.serwis.pgnig.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
import szymborski.bartosz.serwis.pgnig.entity.TournamentEncounterTree;
import szymborski.bartosz.serwis.pgnig.entity.TournamentRuleSet;
import szymborski.bartosz.serwis.pgnig.enums.TournamentRuleEnum;
import szymborski.bartosz.serwis.pgnig.service.resolver.TournamentPhaseResolver;

/**
 *
 * @author bartosz.szymborski
 */
@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class TournamentEncounterServiceImpl implements TournamentEncounterService {

    @Autowired
    private TournamentRuleSetDao trsd;

    @Autowired
    private TournamentEncouterDao ted;

    @Autowired
    private TorunamentDao td;
    
    @Autowired
    private TournamentEncounterTreeService treeService;

    @Override
    public List<TournamentRuleSet> getTournamentRules(Long tournamentId) {
        Tournament tournament = td.getTournamentId(tournamentId);
        return trsd.getTournamentRules(tournamentId);
    }

    @Override
    @Async
    @Transactional(propagation = Propagation.SUPPORTS)
    public void saveTournamentEncounter(Tournament tournament, Map<String, Object> rules) {
        List<TournamentEncounter> encountersToSave = new ArrayList<>();

        short liczbaDruzyn = (short) rules.get(TournamentRuleEnum.LICZBA_DRUZYN.name());
        boolean fazaGrupowa = (boolean) rules.get(TournamentRuleEnum.FAZA_GRUPOWA.name());

        if (fazaGrupowa || liczbaDruzyn < 3) {
            short liczbaGrup = (short) rules.get(TournamentRuleEnum.LICZBA_GRUP.name());
            char gruopSymbol = 'A';
            for (int i = 0; i < liczbaGrup; i++) {
                createEncounter(encountersToSave, 0, String.valueOf(gruopSymbol), fazaGrupowa);
                gruopSymbol = (char) (gruopSymbol + 1);

            }
            boolean drugaFazaTurniejuGrupowa = (boolean) rules.get(TournamentRuleEnum.DRUGA_FAZA_TURNIEJU_GRUPOWA.name());
            if (drugaFazaTurniejuGrupowa) {
                createEncounter(encountersToSave, 1, "Grupa Mistrzowska", drugaFazaTurniejuGrupowa);
            } else {
                short ileDruzynAwansuje = (short) rules.get(TournamentRuleEnum.ILE_DRUZYN_AWANSUJE_Z_GRUPY.name());
                int counter = (ileDruzynAwansuje * liczbaGrup) / 2;
                int currStage = 1;
                while (counter > 1) {
                    for (int i = 0; i < counter; i++) {
                        createEncounter(encountersToSave, currStage, "1/" + counter, fazaGrupowa);
                    }
                    currStage++;
                    counter = counter / 2;
                }
                final Object trz = rules.get(TournamentRuleEnum.MECZ_O_TRZECIE_MIEJSCE.name());
                Boolean meczOTrzecieMiejsce = trz != null && (Boolean) trz;
                if (meczOTrzecieMiejsce) {
                    createEncounter(encountersToSave, currStage++, "Mecz o trzecie miejsce", fazaGrupowa);
                }
                createEncounter(encountersToSave, currStage, "Finał", fazaGrupowa);
            }

        }
        encountersToSave.forEach(enc -> enc.setIdTournament(tournament));
        ted.saveTournamentEncounter(encountersToSave);

        Map<Integer, List<TournamentEncounter>> encounterTreeMap = new HashMap<>();
        for (TournamentEncounter te : encountersToSave) {
            final int stage = (int) te.getStage();
            List<TournamentEncounter> resultList = encounterTreeMap.get(stage);
            if (resultList == null) {
                encounterTreeMap.put(stage, new ArrayList<>());
                resultList = encounterTreeMap.get(stage);
            }
            resultList.add(te);
        }

        Set<Integer> maxValueInteger = encounterTreeMap.keySet();
        Integer lookingMaxValue = 0;

        for (Integer value : maxValueInteger) { //szukanie najwyższego stega z TournamentEncounter
            if (lookingMaxValue < value) {
                lookingMaxValue = value;
            }
        }
        List<TournamentEncounterTree> listTournamentEncTree = new ArrayList<>();
        TournamentPhaseResolver.Param param = new TournamentPhaseResolver.Param(encounterTreeMap);
        for (int i = lookingMaxValue; i >= 0; i--) {
            List<TournamentEncounter> list = encounterTreeMap.get(i);
            for (TournamentEncounter ec : list) {
                final TournamentPhaseResolver reso = TournamentPhaseResolver.getTournamentPhaseResolver(lookingMaxValue, i, rules, param);
                if (reso != null) {
                    List<TournamentEncounterTree> resolved = reso.resolve(ec);
                    listTournamentEncTree.addAll(resolved);
                }
            }
        }
        treeService.saveTournamentEncounterTree(listTournamentEncTree.toArray(new TournamentEncounterTree[0])); //przy zapisie w przypadku z vargs musi być skastowane do Array z indeksem 0

    }

    private void createEncounter(List<TournamentEncounter> encountersToSave, int stage, String info, boolean fazaGrupowa) {
        TournamentEncounter encounter = new TournamentEncounter();
        encountersToSave.add(encounter);
        encounter.setStage(stage);
        encounter.setAddInfo(info);
        encounter.setCzyFazaGrupowa(fazaGrupowa);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Map<Integer, List<TournamentEncounter>> getTournamentEncounter(Long idTournament) {
        Map<Integer, List<TournamentEncounter>> returnMap = new HashMap<>();
        List<TournamentEncounter> listTE = ted.getTournamentEncounter(idTournament);
        for (TournamentEncounter te : listTE) {
            final int stage = (int) te.getStage();
            List<TournamentEncounter> resultList = returnMap.get(stage);
            if (resultList == null) {
                returnMap.put(stage, new ArrayList<>());
                resultList = returnMap.get(stage);
            }
            resultList.add(te);
        }
        return returnMap;
    }

    @Override
    public TournamentEncounter getTournamentEncounterMaxStageForTournament(Long idTournament) {
      return ted.getTournamentEncounterMaxStageForTournament(idTournament);
    }
}
