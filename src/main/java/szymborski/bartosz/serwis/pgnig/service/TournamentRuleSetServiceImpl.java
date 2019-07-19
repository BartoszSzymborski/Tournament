/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szymborski.bartosz.serwis.pgnig.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import szymborski.bartosz.serwis.pgnig.dao.ContenderDao;
import szymborski.bartosz.serwis.pgnig.entity.TournamentRuleSet;
import szymborski.bartosz.serwis.pgnig.enums.TournamentRuleEnum;

/**
 *
 * @author bartosz.szymborski
 */
@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class TournamentRuleSetServiceImpl implements  TournamentRuleSetService{
    
    @Autowired
    private ContenderDao contenderDao;

    @Override
    public TournamentRuleSet getRuleValueForTournament(Long idTournament, TournamentRuleEnum  ruleName) {
       return contenderDao.getRuleValueForTournament(idTournament, ruleName.getMessKey().toUpperCase());
    }

    
    
}
