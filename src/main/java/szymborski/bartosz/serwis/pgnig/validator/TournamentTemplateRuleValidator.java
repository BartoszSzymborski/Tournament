/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szymborski.bartosz.serwis.pgnig.validator;

import java.util.Map;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import szymborski.bartosz.serwis.pgnig.enums.TournamentRuleEnum;

/**
 *
 * @author bartosz.szymborski
 */
@FacesValidator
public class TournamentTemplateRuleValidator implements Validator {

    private static final String NUMBER_OF_TEAMS_AFTER_PHASE = "number_of_teams_after_phase";
    private static final String NUMBER_OF_GROUPS = "number_of_groups";
    private static final String NUMBER_OF_TEAMS_PROMOTE = "number_of_teams_promote";

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (value == null) {
            return;
        }
        Map<String, Object> newValues = (Map<String, Object>) component.getAttributes().get("ruleMap");
        String param = (String) component.getAttributes().get("ruleId");
        String prop = null;
        if (param.equals(TournamentRuleEnum.ILE_DRUZYN_AWANSUJE_Z_GRUPY.name())) {
            short ileDruzynAwansuje = Short.valueOf(value.toString());
            Integer numberOfTeams = (Integer) newValues.get(TournamentRuleEnum.LICZBA_DRUZYN.name());
            Integer numberOfGroups = (Integer) newValues.get(TournamentRuleEnum.LICZBA_GRUP.name());
            if ((numberOfTeams / numberOfGroups) < ileDruzynAwansuje) {
                prop = ResourceBundle.getBundle("messages").getString(NUMBER_OF_TEAMS_PROMOTE);
                throw new ValidatorException(new FacesMessage(prop));
            }
            ileDruzynAwansuje = Integer.valueOf(numberOfGroups * ileDruzynAwansuje).shortValue();
            if (!isPowerOf2(ileDruzynAwansuje)) {
                prop = ResourceBundle.getBundle("messages").getString(NUMBER_OF_TEAMS_AFTER_PHASE);
                throw new ValidatorException(new FacesMessage(prop));
            }

        }
        if (param.equals(TournamentRuleEnum.LICZBA_GRUP.name())) {
            short liczbaGrup = Short.valueOf(value.toString());
            if (!isPowerOf2(liczbaGrup)) {
                prop = ResourceBundle.getBundle("messages").getString(NUMBER_OF_GROUPS);
                throw new ValidatorException(new FacesMessage(prop));
            }
        }

    }

    private static boolean isPowerOf2(short value) {
        if (value <= 0) {
            return false;
        }
        while (value > 1) {
            if (value % 2 != 0) {
                return false;
            }
            value = (short) (value / 2);
        }
        return true;
    }

}
