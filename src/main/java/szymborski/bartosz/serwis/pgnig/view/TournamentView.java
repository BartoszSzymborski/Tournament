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
import javax.annotation.PostConstruct;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import szymborski.bartosz.serwis.pgnig.entity.Tournament;
import szymborski.bartosz.serwis.pgnig.entity.TournamentTemplate;
import szymborski.bartosz.serwis.pgnig.service.TournamentService;
import szymborski.bartosz.serwis.pgnig.service.TournamentTemplateService;

/**
 *
 * @author bartosz.szymborski
 */
@Component
@Scope("view")
public class TournamentView {

    @Autowired
    private TournamentService tsi;

    @Autowired
    private TournamentTemplateService ttsi;

    private Tournament tournament;
    private String name;
    private TournamentTemplate choosenTemplate;
    private Long id;
    public static final String TOURNAMENT_ID = "tournamentId";
    private List<TournamentTemplate> templates;
    private boolean isShownDataTable;

    @PostConstruct
    public void init() {

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveTournament() {
        templates = ttsi.getTemplates();
        tournament = tsi.saveTournament(name, choosenTemplate.getName());
        nextDialog();
    }

    public void nextDialog() {
        Map<String, List<String>> params = new HashMap<>();
        params.put(TOURNAMENT_ID, Arrays.asList(tournament.getId().toString()));

        Map<String, Object> options = new HashMap<>();
        options.put("draggable", Boolean.FALSE);
        options.put("resizable", Boolean.FALSE);
        options.put("responsive", Boolean.TRUE);
        options.put("contentWidth", "900px");
        options.put("contentHeight", "750px");

        PrimeFaces.current().dialog().openDynamic("teams", options, params);
    }

    public void onDialogReturn(SelectEvent event) {
        PrimeFaces.current().dialog().closeDynamic(event.getObject());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TournamentTemplate getChoosenTemplate() {
        return choosenTemplate;
    }

    public void setChoosenTemplate(TournamentTemplate choosenTemplate) {
        this.choosenTemplate = choosenTemplate;
    }

    public Long getId() {
        return id;
    }

    public List<TournamentTemplate> getTemplates() {
        return templates;
    }

    public void setTemplates(List<TournamentTemplate> templates) {
        this.templates = templates;
    }

    public boolean isIsShownDataTable() {
        return isShownDataTable;
    }

    public void setIsShownDataTable(boolean isShownDataTable) {
        this.isShownDataTable = isShownDataTable;
    }

}
