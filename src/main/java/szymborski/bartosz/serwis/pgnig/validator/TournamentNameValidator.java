/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szymborski.bartosz.serwis.pgnig.validator;

import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import szymborski.bartosz.serwis.pgnig.dao.TorunamentDao;

/**
 *
 * @author bartosz.szymborski
 */
@Component
@FacesValidator
public class TournamentNameValidator implements Validator {


    private static final String TOURNAMENT_NAME_NOT_EMPTY = "tournament_name_not_empty";
    private static final String TOURNAMENT_NAME_EXIST = "tournament_name_exist";
    
    @Autowired
    private TorunamentDao td;

    @Override
    public void validate(FacesContext context, UIComponent component, Object object) throws ValidatorException {
        String prop = null;
        if (component.isRendered()) {
            String tournamentName = String.valueOf(object);
            if(object.equals(td.getTournamentName(tournamentName))){
                  prop = ResourceBundle.getBundle("messages").getString(TOURNAMENT_NAME_EXIST);
            }
            if (StringUtils.isEmpty(tournamentName)) {
                prop = ResourceBundle.getBundle("messages").getString(TOURNAMENT_NAME_NOT_EMPTY);
            }
            if (prop != null) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, prop, prop));
            }
        }
    }

}
