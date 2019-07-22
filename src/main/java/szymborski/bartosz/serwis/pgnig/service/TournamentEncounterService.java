/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szymborski.bartosz.serwis.pgnig.service;

import java.util.List;
import java.util.Map;
import szymborski.bartosz.serwis.pgnig.entity.Tournament;
import szymborski.bartosz.serwis.pgnig.entity.TournamentRuleSet;

/**
 *
 * @author bartosz.szymborski
 */
public interface TournamentEncounterService {
    
    public List<TournamentRuleSet> getTournamentRules();
    
    public void saveTournamentEncounter(Tournament t, Map<String, Object> rules);
}
