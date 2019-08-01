/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szymborski.bartosz.serwis.pgnig.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.primefaces.context.PrimeFacesContext;
import org.primefaces.model.DefaultOrganigramNode;
import org.primefaces.model.OrganigramNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import szymborski.bartosz.serwis.pgnig.dao.TorunamentDao;
import szymborski.bartosz.serwis.pgnig.entity.Tournament;
import szymborski.bartosz.serwis.pgnig.entity.TournamentEncounter;
import szymborski.bartosz.serwis.pgnig.entity.TournamentEncounterTree;
import szymborski.bartosz.serwis.pgnig.service.TournamentEncounterService;
import szymborski.bartosz.serwis.pgnig.service.TournamentEncounterTreeService;

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

    public static final String TOURNAMENT_NAME = "TOURNAMENT_NAME";
    private String tournamentName;
    private int leafNodeConnectorHeight = 0;
    private boolean autoScrollSelection = false;
    private boolean zoom = false;
    private String style = "width: 800px";
    private OrganigramNode selection;
    private OrganigramNode rootNode;

    @PostConstruct
    public void init() {

        Map<String, String> requestParameterMap = PrimeFacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        tournamentName = requestParameterMap.get(TOURNAMENT_NAME);
        Tournament tournament = td.getTournamentName(tournamentName);
        TournamentEncounter rootEnc = tes.getTournamentEncounterMaxStageForTournament(tournament.getId());
        Map<TournamentEncounter, OrganigramNode> parentNodez = new HashMap<>();

        rootNode = new DefaultOrganigramNode("final", rootEnc.getAddInfo(), null);
        rootNode.setCollapsible(false);
        rootNode.setDraggable(true);

        parentNodez.put(rootEnc, rootNode);

        List<TournamentEncounterTree> childNodez = tets.getTournamenEncouterTreeLowerStage(rootEnc.getId());
        for (TournamentEncounterTree tree : childNodez) {
            OrganigramNode onde = parentNodez.get(tree.getIdtournamentencounternext());
            OrganigramNode childNode = new DefaultOrganigramNode("semif", tree.getIdtournamentencounterprevious().getAddInfo(), onde);
            parentNodez.put(tree.getIdtournamentencounterprevious(), childNode);
            List<TournamentEncounterTree> childNodeSe = tets.getTournamenEncouterTreeLowerStage(tree.getIdtournamentencounterprevious().getId());
            for (TournamentEncounterTree tree1 : childNodeSe) {
                OrganigramNode ondeSemi = parentNodez.get(tree1.getIdtournamentencounternext());
                OrganigramNode childNodeSemi = new DefaultOrganigramNode("nextStage", tree1.getIdtournamentencounterprevious().getAddInfo(), ondeSemi);
                parentNodez.put(tree1.getIdtournamentencounterprevious(), childNodeSemi);
            }
        }
        // OrganigramNode onode = parentNodez.get(tr.idtournamentencounternext)
        // OrganigramNode chnode = new DefaOrNod(null, cos, onode);
        // parentNodez.put(tr.idtournamentencounterprevious, chnode)

        selection = rootNode;
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

}
