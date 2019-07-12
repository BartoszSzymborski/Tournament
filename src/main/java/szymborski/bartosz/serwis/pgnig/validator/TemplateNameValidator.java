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
import szymborski.bartosz.serwis.pgnig.dao.TournamentTemplateDao;

/**
 *
 * @author bartosz.szymborski
 */
@Component
@FacesValidator
public class TemplateNameValidator implements Validator {

    @Autowired
    private TournamentTemplateDao ttd;
    

    private static final String TEMPLATE_NAME_EXIST = "template_name_exist";
    private static final String TEMPLATE_NAME_STRING = "template_name_string";
    

    @Override
    public void validate(FacesContext context, UIComponent component, Object object) throws ValidatorException {
        String prop = null;
        if (component.isRendered()) {
            String templateName = String.valueOf(object);
            if (object.equals(ttd.getTemplateName(templateName))) {
                prop = ResourceBundle.getBundle("messages").getString(TEMPLATE_NAME_EXIST);
            }
            if(templateName.equals(""))
                 prop = ResourceBundle.getBundle("messages").getString(TEMPLATE_NAME_STRING);
            if(prop !=null){
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, prop, prop));
            }
        }

    }

}
