/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szymborski.bartosz.serwis.pgnig.service.resolver;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import szymborski.bartosz.serwis.pgnig.entity.TournamentEncounter;
import szymborski.bartosz.serwis.pgnig.entity.TournamentEncounterTree;

/**
 *
 * @author bartosz.szymborski
 */
public abstract class AbstractReslover implements TournamentPhaseResolver {

     final int tournamentStage;
     protected final Param p;

    public AbstractReslover(int tournamentStage, Param p) {
        this.tournamentStage = tournamentStage;
        this.p = p;
    }
     
    
    @Override
    public List<TournamentEncounterTree> resolve(TournamentEncounter enc) {
        List<TournamentEncounterTree> tetList = new ArrayList<>();
        ListIterator<TournamentEncounter> listIterator = getListIterator(p);
        for (int y = 0; y < 2; y++) {
            TournamentEncounterTree tet = new TournamentEncounterTree();
            tet.setIdtournamentencounternext(enc);
            tet.setIdtournamentencounterprevious(listIterator.next());
            listIterator.remove();
            tetList.add(tet);
        }
        return tetList;
    }
    abstract ListIterator<TournamentEncounter> getListIterator(Param p);

}
