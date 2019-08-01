/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szymborski.bartosz.serwis.pgnig.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import szymborski.bartosz.serwis.pgnig.dao.TournamentEncouterTreeDao;
import szymborski.bartosz.serwis.pgnig.entity.TournamentEncounterTree;

/**
 *
 * @author bartosz.szymborski
 */
@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class TournamentEncounterTreeServiceImpl implements TournamentEncounterTreeService {

    @Autowired
    private TournamentEncouterTreeDao treeDao;

    @Override
    public void saveTournamentEncounterTree(TournamentEncounterTree... tet) {
        treeDao.saveTournamentEncounterTree(tet);
    }

    @Override
    public List<TournamentEncounterTree> getTournamenEncouterTreeLowerStage(Long idTournament) {
        List<TournamentEncounterTree> list = treeDao.getTournamenEncouterTreeLowerStage(idTournament);
        return list;
    }

   

}
