/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szymborski.bartosz.serwis.pgnig.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import szymborski.bartosz.serwis.pgnig.dao.TournamentRuleDao;
import szymborski.bartosz.serwis.pgnig.entity.TournamentRule;

/**
 *
 * @author bartosz.szymborski
 */
public class TourmentRuleServiceImpl implements TourmentRuleService{

    @Autowired
    private TournamentRuleDao ruleDao;
    
    @Override
    public List<TournamentRule> gerRules() {
        List<TournamentRule> rules = new ArrayList<>(ruleDao.getRules());
        return rules;
    }
    
}
