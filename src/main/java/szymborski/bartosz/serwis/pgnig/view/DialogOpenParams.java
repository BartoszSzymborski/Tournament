/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szymborski.bartosz.serwis.pgnig.view;

/**
 *
 * @author bartosz.szymborski
 */
public class DialogOpenParams {
    
    private String viewToOpenId;
    
    private Long tournamentId;

    public DialogOpenParams(String viewToOpenId, Long tournamentId) {
        this.viewToOpenId = viewToOpenId;
        this.tournamentId = tournamentId;
    }

    public String getViewToOpenId() {
        return viewToOpenId;
    }


    public Long getTournamentId() {
        return tournamentId;
    }

    
    
    
}
