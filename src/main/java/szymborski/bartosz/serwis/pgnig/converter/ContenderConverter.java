/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szymborski.bartosz.serwis.pgnig.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import szymborski.bartosz.serwis.pgnig.dao.ContenderDao;

/**
 *
 * @author bartosz.szymborski
 */
@Component
@FacesConverter("contenderConverter")
public class ContenderConverter implements Converter{

    @Autowired
    private ContenderDao contenderDao;
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return contenderDao.getContenderName(value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
       return String.valueOf(value);
    }
    
}
