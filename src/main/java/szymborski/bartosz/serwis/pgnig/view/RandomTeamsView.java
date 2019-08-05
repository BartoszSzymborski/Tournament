/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szymborski.bartosz.serwis.pgnig.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import javax.annotation.PostConstruct;
import org.primefaces.context.PrimeFacesContext;
import org.primefaces.model.DefaultOrganigramNode;
import org.primefaces.model.OrganigramNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import szymborski.bartosz.serwis.pgnig.dao.TorunamentDao;
import szymborski.bartosz.serwis.pgnig.entity.Tournament;
import szymborski.bartosz.serwis.pgnig.entity.TournamentContender;
import szymborski.bartosz.serwis.pgnig.entity.TournamentEncounter;
import szymborski.bartosz.serwis.pgnig.entity.TournamentEncounterTree;
import szymborski.bartosz.serwis.pgnig.entity.TournamentRuleSet;
import szymborski.bartosz.serwis.pgnig.enums.TournamentRuleEnum;
import szymborski.bartosz.serwis.pgnig.service.TournamentContenderService;
import szymborski.bartosz.serwis.pgnig.service.TournamentEncounterService;
import szymborski.bartosz.serwis.pgnig.service.TournamentEncounterTreeService;
import szymborski.bartosz.serwis.pgnig.service.TournamentRuleSetService;
import szymborski.bartosz.serwis.pgnig.service.TournamentService;

/**
 *
 * @author bartosz.szymborski
 */
@Component
@Scope("view")
public class RandomTeamsView {

    @Autowired
    private TournamentEncounterService tes;

    @Autowired
    private TournamentEncounterTreeService tets;

    @Autowired
    private TorunamentDao td;

    @Autowired
    private TournamentService ts;

    @Autowired
    private TournamentRuleSetService trss;

    @Autowired
    private TournamentContenderService tcs;

    public static final String TOURNAMENT_NAME = "TOURNAMENT_NAME";
    private String tournamentName;
    private int leafNodeConnectorHeight = 0;
    private boolean autoScrollSelection = false;
    private boolean zoom = false;
    private String style = "width: 1000px";
    private OrganigramNode selection;
    private OrganigramNode rootNode;
    private Map<Character, Integer> characterSet = new HashMap<>();
    private Tournament tournament;
    private List<TournamentContender> contenders;
    private Map<String, List<TournamentContender>> lastDraw;

    @PostConstruct
    public void init() {

        Map<String, String> requestParameterMap = PrimeFacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        tournamentName = requestParameterMap.get(TOURNAMENT_NAME);
        tournament = td.getTournamentName(tournamentName);
        contenders = tcs.getTournamentContenderById(tournament.getId());

        TournamentEncounter rootEnc = tes.getTournamentEncounterMaxStageForTournament(tournament.getId());
        Map<TournamentEncounter, OrganigramNode> parentNodez = new HashMap<>();

        rootNode = new DefaultOrganigramNode("root", rootEnc.getAddInfo(), null);
        rootNode.setCollapsible(false);
        rootNode.setDraggable(true);
        rootNode.setSelectable(true);

        parentNodez.put(rootEnc, rootNode);

        recurenctionMethodForEncounterTree(rootEnc, parentNodez);

        lastDraw = createGroupOfContenders(contenders);
        selection = rootNode;
    }

    private void recurenctionMethodForEncounterTree(TournamentEncounter rootEnc, Map<TournamentEncounter, OrganigramNode> parentNodez) {
        List<TournamentEncounterTree> childNodez = tets.getTournamenEncouterTreeLowerStage(rootEnc.getId());
        if (childNodez.isEmpty()) {
            return;
        }
        for (TournamentEncounterTree tree : childNodez) {
            OrganigramNode onde = parentNodez.get(tree.getIdtournamentencounternext());
            TournamentEncounter idtournamentencounterprevious = tree.getIdtournamentencounterprevious();
            OrganigramNode childNode = new DefaultOrganigramNode("root", createNameOfEncounter(idtournamentencounterprevious.getAddInfo()), onde);
            parentNodez.put(idtournamentencounterprevious, childNode);
            recurenctionMethodForEncounterTree(idtournamentencounterprevious, parentNodez);
        }
    }

    private String createNameOfEncounter(String addInfo) {
        char charAt = addInfo.charAt(0);
        if (addInfo.length() == 1 && Character.isLetter(charAt)) {
            Integer value = characterSet.get(charAt);
            if (value == null) {
                characterSet.put(charAt, 1);
                return "" + charAt + 1;
            } else {
                characterSet.put(charAt, value + 1);
                return "" + charAt + (value + 1);
            }
        }
        return addInfo;
    }

    private Map<String, List<TournamentContender>> createGroupOfContenders(List<TournamentContender> contenders) {
        contenders = new ArrayList<>(contenders);//Defence Code - kopia defensywna
        Map<String, List<TournamentContender>> retVal = new HashMap<>(); //zwraca wartość
        TournamentRuleSet ruleValueForTournament = trss.getRuleValueForTournament(tournament.getId(), TournamentRuleEnum.LICZBA_GRUP); //pobieranie idTurnieju i jego zasady LICBA GRUP
        int howManyTeamsInGroup = contenders.size() / ruleValueForTournament.getIntegerValue(); //zmienna do określienie ile będzie druzyn w 1 grupie (rozmiar listy uczestników turnieju / LICZBA GRUP z zasady turnieju
        ThreadLocalRandom rand = ThreadLocalRandom.current(); //zmienna random do losowania przypadkowego, obiekt do pracy z wieloma wątkami
        for (int i = 0; i < ruleValueForTournament.getIntegerValue(); i++) {//pierwsza pętla
            List<TournamentContender> newList = new ArrayList<>();//tworzenie listy aby ją dodać do mapy
            for (int y = 0; y < howManyTeamsInGroup; y++) {//druga iteracja
                int r = rand.nextInt(0, contenders.size());//zmienna liczbowa do losowania
                TournamentContender draw = contenders.get(r);//pojedyczńy contender do dodania
                newList.add(draw);
                contenders.remove(r);//czyszczenie listy z dodanego wczesniej contendera
            }
            retVal.put(String.valueOf((char)('A'+i)), newList);//zwraca mapa //wazne plus jest przed castowaniem wiec musza byc dwa nawiasy zeby był efekt w postaci char
        }

        return retVal;
    }
    
    public void showCreateGroupOfContenders(){
        lastDraw = createGroupOfContenders(contenders);
    }

    public String getTournamentName() {
        return tournamentName;
    }

    public void setTournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
    }

    public int getLeafNodeConnectorHeight() {
        return leafNodeConnectorHeight;
    }

    public void setLeafNodeConnectorHeight(int leafNodeConnectorHeight) {
        this.leafNodeConnectorHeight = leafNodeConnectorHeight;
    }

    public boolean isAutoScrollSelection() {
        return autoScrollSelection;
    }

    public void setAutoScrollSelection(boolean autoScrollSelection) {
        this.autoScrollSelection = autoScrollSelection;
    }

    public boolean isZoom() {
        return zoom;
    }

    public void setZoom(boolean zoom) {
        this.zoom = zoom;
    }

    public OrganigramNode getSelection() {
        return selection;
    }

    public void setSelection(OrganigramNode selection) {
        this.selection = selection;
    }

    public OrganigramNode getRootNode() {
        return rootNode;
    }

    public void setRootNode(OrganigramNode rootNode) {
        this.rootNode = rootNode;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public Map<Character, Integer> getCharacterSet() {
        return characterSet;
    }

    public void setCharacterSet(Map<Character, Integer> characterSet) {
        this.characterSet = characterSet;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public List<TournamentContender> getContenders() {
        return contenders;
    }

    public void setContenders(List<TournamentContender> contenders) {
        this.contenders = contenders;
    }

    public Map<String, List<TournamentContender>> getLastDraw() {
        return lastDraw;
    }

    public void setLastDraw(Map<String, List<TournamentContender>> lastDraw) {
        this.lastDraw = lastDraw;
    }
    
    

}
