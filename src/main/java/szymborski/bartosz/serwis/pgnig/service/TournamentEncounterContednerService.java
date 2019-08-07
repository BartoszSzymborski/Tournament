/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szymborski.bartosz.serwis.pgnig.service;

import java.util.List;
import java.util.Map;
import szymborski.bartosz.serwis.pgnig.entity.TournamentContender;
import szymborski.bartosz.serwis.pgnig.entity.TournamentEncounter;
import szymborski.bartosz.serwis.pgnig.entity.TournamentEncounterContender;

/**
 *
 * @author bartosz.szymborski
 */
public interface TournamentEncounterContednerService {

    public void saveTournamentEncounterContender(Map<TournamentEncounter, List<TournamentContender>> tourEncConMap);
    
    public Map<String, List<TournamentContender>> getContenderByGroup(Long idTournament);
    
    public void deleteTournamentEncounterContender(Long idTournament, Long... stages);
    
}
