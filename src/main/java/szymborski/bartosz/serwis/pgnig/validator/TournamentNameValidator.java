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
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 *
 * @author bartosz.szymborski
 */
@Component
@FacesValidator
public class TournamentNameValidator implements Validator {


    private static final String TOURNAMENT_NAME_NOT_EMPTY = "tournament_name_not_empty";

    @Override
    public void validate(FacesContext context, UIComponent component, Object object) throws ValidatorException {
        String prop = null;
        if (component.isRendered()) {
            String tournamentName = String.valueOf(object);
            if (StringUtils.isEmpty(tournamentName)) {
                prop = ResourceBundle.getBundle("messages").getString(TOURNAMENT_NAME_NOT_EMPTY);
            }
            if (prop != null) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, prop, prop));
            }
        }
    }

}
