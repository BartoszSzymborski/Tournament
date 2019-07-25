/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szymborski.bartosz.serwis.pgnig.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.primefaces.PrimeFaces;
import org.primefaces.context.PrimeFacesContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import szymborski.bartosz.serwis.pgnig.entity.Tournament;
import szymborski.bartosz.serwis.pgnig.service.TournamentEncounterService;
import szymborski.bartosz.serwis.pgnig.service.TournamentService;
import static szymborski.bartosz.serwis.pgnig.view.TournamentView.TOURNAMENT_ID;

/**
 *
 * @author bartosz.szymborski
 */
@Component
@Scope("view")
public class TournamentEncounterView {
    
    @Autowired
    private TournamentEncounterService tes;
    
    @Autowired
    private TournamentService ts;
    
    private String tournament;
    private List<Tournament> tournaments;
   
    
    @PostConstruct
    public void init(){
         tournaments = ts.getTournamentList();
//        rules = tes.getTournamentRules(idTournament).stream().collect(Collectors.toMap(tr -> tr.getTournamentRule().getName(),
//                tr -> tr.getTournamentRule().getIntegralType() ? tr.getIntegerValue() : tr.getBooleanValue() ));
       
    }
    
    Map<String, Object> options = new HashMap<>();
    
    public void openRandomTeamsDialog() {
        options.put("draggable", Boolean.FALSE);
        options.put("resizable", Boolean.FALSE);
        options.put("responsive", Boolean.TRUE);
        options.put("contentWidth", "900px");
        options.put("contentHeight", "500px");
        PrimeFaces.current().dialog().openDynamic("randomTeams", options, null);
    }
    
    public void openHandTeamsDialog(){
        options.put("draggable", Boolean.FALSE);
        options.put("resizable", Boolean.FALSE);
        options.put("responsive", Boolean.TRUE);
        options.put("contentWidth", "900px");
        options.put("contentHeight", "500px");
        PrimeFaces.current().dialog().openDynamic("handTeams", options, null);
    }


    public List<Tournament> getTournaments() {
        return tournaments;
    }

    public void setTournaments(List<Tournament> tournaments) {
        this.tournaments = tournaments;
    }
    

    public String getTournament() {
        return tournament;
    }

    public void setTournament(String tournament) {
        this.tournament = tournament;
    }
    
    
    
    
    
    
    
    
}
