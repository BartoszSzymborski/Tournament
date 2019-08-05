/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szymborski.bartosz.serwis.pgnig.service.resolver;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import szymborski.bartosz.serwis.pgnig.entity.TournamentEncounter;
import szymborski.bartosz.serwis.pgnig.entity.TournamentEncounterTree;
import szymborski.bartosz.serwis.pgnig.enums.TournamentRuleEnum;

/**
 *
 * @author bartosz.szymborski
 */
public class ResolverTreeChacheImpl implements TournamentPhaseResolver {

    private final Param p;
    private final Map<String, Object> rulez;

    public ResolverTreeChacheImpl(Param p, Map<String, Object> rulez) {
        this.p = p;
        this.rulez = rulez;
    }

    @Override
    public List<TournamentEncounterTree> resolve(TournamentEncounter enc) {
        int stg = (int) enc.getStage() - 1; //stage
        List<TournamentEncounterTree> trees = p.getTreeCache().get(stg); //stages 
        if(trees == null){
            p.getTreeCache().put(stg, new ArrayList<>());
            trees = p.getTreeCache().get(stg);
        }
        List<TournamentEncounter> brandNewSubEncounters = p.getBrandNewSubEncounters(stg);
        Short howMany = (Short) rulez.get(TournamentRuleEnum.ILE_DRUZYN_AWANSUJE_Z_GRUPY.name());
        ListIterator<TournamentEncounter> listIterator = brandNewSubEncounters.listIterator();
        while (listIterator.hasNext()) {
            TournamentEncounter next = listIterator.next();
            long ileJushJest = trees.stream().map(tre -> tre.getIdtournamentencounterprevious()).filter(te -> te == next).count();
            if (howMany == (int) ileJushJest) {
                listIterator.remove();
            }
        }
        listIterator = brandNewSubEncounters.listIterator();
        List<TournamentEncounterTree> retVal = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            TournamentEncounterTree tet = new TournamentEncounterTree();
            tet.setIdtournamentencounternext(enc);
            tet.setIdtournamentencounterprevious(listIterator.next());
            listIterator.remove();
            retVal.add(tet);
        }
        trees.addAll(retVal);
        return retVal;
    }

}
