/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szymborski.bartosz.serwis.pgnig.view;

import java.util.Map;
import javax.annotation.PostConstruct;
import org.primefaces.context.PrimeFacesContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author bartosz.szymborski
 */
@Scope("view")
@Component
public class HandTeamsView {
    
   public static final String TOURNAMENT_NAME = "TOURNAMENT_NAME";
    private String tournamentName;
    
    @PostConstruct
    public void init(){
       Map<String, String> requestParameterMap = PrimeFacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
       tournamentName = requestParameterMap.get(TOURNAMENT_NAME);
    }

    public String getTournamentName() {
        return tournamentName;
    }

    public void setTournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
    }
    
}
