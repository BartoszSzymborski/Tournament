/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szymborski.bartosz.serwis.pgnig.service.resolver;

import java.util.ListIterator;
import szymborski.bartosz.serwis.pgnig.entity.TournamentEncounter;

/**
 *
 * @author bartosz.szymborski
 */
public class ResolverBrandNewImpl extends AbstractReslover{

    public ResolverBrandNewImpl(int tournamentStage, Param p) {
        super(tournamentStage, p);
    }
    
    @Override
    ListIterator<TournamentEncounter> getListIterator(Param p) {
        return p.getBrandNewSubEncounters(tournamentStage).listIterator();
    }

  
}
