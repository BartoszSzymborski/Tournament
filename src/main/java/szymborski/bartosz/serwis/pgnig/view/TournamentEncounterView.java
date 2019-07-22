/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szymborski.bartosz.serwis.pgnig.view;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import szymborski.bartosz.serwis.pgnig.entity.TournamentEncounter;
import szymborski.bartosz.serwis.pgnig.service.TournamentEncounterService;

/**
 *
 * @author bartosz.szymborski
 */
@Component
@Scope("view")
public class TournamentEncounterView {
    
    @Autowired
    private TournamentEncounterService tes;
    
    private Map<String, Object> rules;
    private List<TournamentEncounter> encounters;
    
    @PostConstruct
    public void init(){
        rules = tes.getTournamentRules().stream().collect(Collectors.toMap(tr -> tr.getTournamentRule().getName(),
                tr -> tr.getTournamentRule().getIntegralType() ? tr.getIntegerValue() : tr.getBooleanValue() ));
        
        
    }
}
