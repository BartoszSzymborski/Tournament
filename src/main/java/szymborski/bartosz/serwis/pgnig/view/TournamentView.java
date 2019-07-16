/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szymborski.bartosz.serwis.pgnig.view;

import javax.annotation.PostConstruct;
import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import szymborski.bartosz.serwis.pgnig.entity.Tournament;
import szymborski.bartosz.serwis.pgnig.entity.TournamentTemplate;
import szymborski.bartosz.serwis.pgnig.service.TournamentService;

/**
 *
 * @author bartosz.szymborski
 */
@Component
@Scope("view")
public class TournamentView {

    @Autowired
    private TournamentService tsi;

    private Tournament tournament;
    private String name;
    private TournamentTemplate choosenTemplate;

    @PostConstruct
    public void init() {

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveTournament() {
        tournament = tsi.saveTournament(name, choosenTemplate.getName());
        closeDialog();
    }

    public void closeDialog() {
        PrimeFaces.current().dialog().closeDynamic(Boolean.TRUE);
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

}
