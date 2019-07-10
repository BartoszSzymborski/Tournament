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
import static org.primefaces.component.feedreader.FeedReader.PropertyKeys.value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author bartosz.szymborski
 */
@Component
@Scope("view")
public class TemplateView {

    Map<String, Object> options = new HashMap<>();

    public void openTemplateDialog(String name) {
        options.put("draggable", Boolean.TRUE);
        options.put("resizable", Boolean.TRUE);
        options.put("responsive", Boolean.TRUE);
         Map<String, List<String>> params = new HashMap<>();
         params.put(TournamentTemplateView.NAME, Arrays.asList(name));
         PrimeFaces.current().dialog().openDynamic("template", options, params);
    }
}
