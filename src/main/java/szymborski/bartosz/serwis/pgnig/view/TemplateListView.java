/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szymborski.bartosz.serwis.pgnig.view;

import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import szymborski.bartosz.serwis.pgnig.entity.TournamentTemplate;
import szymborski.bartosz.serwis.pgnig.service.TournamentTemplateService;

/**
 *
 * @author bartosz.szymborski
 */
@Component
@Scope("view")
public class TemplateListView {

    @Autowired
    private TournamentTemplateService ttsi;

    List<TournamentTemplate> templates;
    TournamentTemplate choosenTemplate;

    @PostConstruct
    public void init() {
        templates = ttsi.getTemplates();
    }

    public void closeDialog() {
        PrimeFaces.current().dialog().closeDynamic(Boolean.TRUE);
    }

    public List<TournamentTemplate> getTemplates() {
        return templates;
    }

    public TournamentTemplate getChoosenTemplate() {
        return choosenTemplate;
    }

    public void setChoosenTemplate(TournamentTemplate choosenTemplate) {
        this.choosenTemplate = choosenTemplate;
    }

}
