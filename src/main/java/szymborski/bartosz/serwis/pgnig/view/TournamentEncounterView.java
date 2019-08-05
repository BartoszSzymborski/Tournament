/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szymborski.bartosz.serwis.pgnig.view;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import szymborski.bartosz.serwis.pgnig.entity.Tournament;
import szymborski.bartosz.serwis.pgnig.service.TournamentEncounterService;
import szymborski.bartosz.serwis.pgnig.service.TournamentService;

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
    public void init() {
        tournaments = ts.getTournamentList();
//        rules = tes.getTournamentRules(idTournament).stream().collect(Collectors.toMap(tr -> tr.getTournamentRule().getName(),
//                tr -> tr.getTournamentRule().getIntegralType() ? tr.getIntegerValue() : tr.getBooleanValue() ));

    }

    Map<String, Object> options = new HashMap<>();

    public void openRandomTeamsDialog() {
        Map<String, List<String>> params = introduceTournamentNameToDialog();
        options.put("draggable", Boolean.TRUE);
        options.put("resizable", Boolean.TRUE);
        options.put("responsive", Boolean.TRUE);
        options.put("contentWidth", "1000px");
        options.put("contentHeight", "700px");
        PrimeFaces.current().dialog().openDynamic("randomTeams", options, params);
    }


    public void openHandTeamsDialog() {
        Map<String, List<String>> params = introduceTournamentNameToDialog();
        options.put("draggable", Boolean.FALSE);
        options.put("resizable", Boolean.FALSE);
        options.put("responsive", Boolean.TRUE);
        options.put("contentWidth", "900px");
        options.put("contentHeight", "500px");
        PrimeFaces.current().dialog().openDynamic("handTeams", options, params);
    }
    
      private Map<String, List<String>> introduceTournamentNameToDialog() {
        Map<String, List<String>> params = new HashMap<>();
        params.put(RandomTeamsView.TOURNAMENT_NAME, Arrays.asList(tournament));
        return params;
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
