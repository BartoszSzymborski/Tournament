/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szymborski.bartosz.serwis.pgnig.validator;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import szymborski.bartosz.serwis.pgnig.dao.TournamentEncounterContenderDao;

/**
 *
 * @author bartosz.szymborski
 */
@Component
@FacesComponent
public class TournamentEncounterContenderValidator implements Validator{
    
    private static final String TOUNRMANET_ENCOUNTER_CONTENDER_EXIST = "tournament_encounter_contender_exist";
    
    @Autowired
    private TournamentEncounterContenderDao tecd;

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String prop = null;
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
