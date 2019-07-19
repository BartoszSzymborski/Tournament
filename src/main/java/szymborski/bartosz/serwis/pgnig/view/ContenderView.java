/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szymborski.bartosz.serwis.pgnig.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import org.primefaces.PrimeFaces;
import org.primefaces.context.PrimeFacesContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TransferEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DualListModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import szymborski.bartosz.serwis.pgnig.dao.ContenderDao;
import szymborski.bartosz.serwis.pgnig.entity.Contender;
import szymborski.bartosz.serwis.pgnig.entity.Tournament;
import szymborski.bartosz.serwis.pgnig.entity.TournamentContender;
import szymborski.bartosz.serwis.pgnig.enums.TournamentRuleEnum;
import szymborski.bartosz.serwis.pgnig.service.ContenderService;
import szymborski.bartosz.serwis.pgnig.service.TournamentContenderService;
import szymborski.bartosz.serwis.pgnig.service.TournamentRuleSetService;
import szymborski.bartosz.serwis.pgnig.service.TournamentService;
import static szymborski.bartosz.serwis.pgnig.view.TournamentView.TOURNAMENT_ID;

/**
 *
 * @author bartosz.szymborski
 */
@Component
@Scope("view")
public class ContenderView {

    private List<Contender> contenders;
    private List<Contender> contendersChoosen = new ArrayList<>();
    private Tournament tournament;
    private Long idTournament;
    private Long idContender;
    private String ruleName;
    private Short teamsToChoose;
    private Short teamsToChooseLeft;
    private boolean showSaveButton;
    private List<TournamentContender> tournamentContenders;
    private Contender contender;
    private String nameOfTeamLookUp;
    private List<Contender> filterContenders;
    private DualListModel<Contender> dualContenders;

    @Autowired
    private ContenderService contenderService;

    @Autowired
    private TournamentRuleSetService trss;

    @Autowired
    private TournamentService tsi;

    @Autowired
    private TournamentContenderService tcs;

    @Autowired
    private ContenderDao cd;

    @PostConstruct
    public void init() {
        contenders = contenderService.getContenders();
        filterContenders = new ArrayList<>(contenders);
        dualContenders = new DualListModel<>(filterContenders, contendersChoosen);
        String tournamentIdParam = PrimeFacesContext.getCurrentInstance()
                .getExternalContext()
                .getRequestParameterMap().get(TOURNAMENT_ID);
        if (tournamentIdParam == null) {
            return;
        }

        tournament = tsi.getTournamentId(Long.valueOf(tournamentIdParam));
        idTournament = tournament.getId();
        teamsToChoose = teamsToChooseLeft = trss.getRuleValueForTournament(idTournament, TournamentRuleEnum.LICZBA_DRUZYN).getIntegerValue();

//        pfScript();
    }

    public void saveTournamentContender() {
        tournamentContenders = tcs.saveTournamentContender(idTournament, contendersChoosen.stream()
                .map(Contender::getId).collect(Collectors.toList()).toArray(new Long[0]));
        closeDialog();
    }

    public void teamClicked(ValueChangeEvent vce) {
        short chosenNo = (short) ((DualListModel) vce.getNewValue()).getTarget().size();
        teamsToChooseLeft = (short) (teamsToChoose - chosenNo);
        showSaveButton = teamsToChooseLeft.intValue() == 0;
    }

    public void closeDialog() {
        PrimeFaces.current().dialog().closeDynamic(Boolean.TRUE);
    }

//    public void applyFilter(AjaxBehaviorEvent event) throws AbortProcessingException {
//        applyFilter();
//    }
//
//    public void applyFilter() {
//        if (getNameOfTeamLookUp() != null) {
//            this.filterContenders = contenders.stream()
//                    .filter(t -> t.getName().contains(getNameOfTeamLookUp())
//                    || t.getName().contains(getNameOfTeamLookUp().toUpperCase()))
//                    .collect(Collectors.toList());
//        } else {
//            filterContenders = new ArrayList<>(contenders);
//        }
//    } do filtrowania bez komponentu PickList
    ////getter i setter
    public List<Contender> getContenders() {
        return contenders;
    }

    public void setContenders(List<Contender> contenders) {
        this.contenders = contenders;
    }

    public List<Contender> getContendersChoosen() {
        return contendersChoosen;
    }

    public void setContendersChoosen(List<Contender> contendersChoosen) {
        this.contendersChoosen = contendersChoosen;
    }

    public Long getIdTournament() {
        return idTournament;
    }

    public void setIdTournament(Long idTournament) {
        this.idTournament = idTournament;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public Short getTeamsToChooseLeft() {
        return teamsToChooseLeft;
    }

    public void setTeamsToChooseLeft(Short teamsToChooseLeft) {
        this.teamsToChooseLeft = teamsToChooseLeft;
    }

    public Short getTeamsToChoose() {
        return teamsToChoose;
    }

    public void setTeamsToChoose(Short teamsToChoose) {
        this.teamsToChoose = teamsToChoose;
    }

    public boolean isShowSaveButton() {
        return showSaveButton;
    }

    public void setShowSaveButton(boolean showSaveButton) {
        this.showSaveButton = showSaveButton;
    }

    public List<Contender> getFilterContenders() {
        return filterContenders;
    }

    public void setFilterContenders(List<Contender> filterContenders) {
        this.filterContenders = filterContenders;
    }

    public String getNameOfTeamLookUp() {
        return nameOfTeamLookUp;
    }

    public void setNameOfTeamLookUp(String nameOfTeamLookUp) {
        this.nameOfTeamLookUp = nameOfTeamLookUp;
    }

    public DualListModel<Contender> getDualContenders() {
        return dualContenders;
    }

    public void setDualContenders(DualListModel<Contender> dualContenders) {
        this.dualContenders = dualContenders;
    }

}
