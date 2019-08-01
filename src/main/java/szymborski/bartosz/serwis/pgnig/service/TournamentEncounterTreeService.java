/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szymborski.bartosz.serwis.pgnig.service;

import java.util.List;
import szymborski.bartosz.serwis.pgnig.entity.TournamentEncounterTree;

/**
 *
 * @author bartosz.szymborski
 */
public interface TournamentEncounterTreeService {
    
    public void saveTournamentEncounterTree(TournamentEncounterTree... tet);
    
    public List<TournamentEncounterTree> getTournamenEncouterTreeLowerStage(Long idTournament);
}
