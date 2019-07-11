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
import org.primefaces.PrimeFaces;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import szymborski.bartosz.serwis.pgnig.entity.TournamentRule;

/**
 *
 * @author bartosz.szymborski
 */
@Component
@Scope("view")
public class TemplateView {

    Map<String, Object> options = new HashMap<>();

    public void openTemplateDialog() {
        options.put("draggable", Boolean.TRUE);
        options.put("resizable", Boolean.TRUE);
        options.put("responsive", Boolean.TRUE);
        options.put("contentWidth","500px");
        options.put("contentHeight","200px");
        PrimeFaces.current().dialog().openDynamic("template", options, null);
    }
    
    
}
