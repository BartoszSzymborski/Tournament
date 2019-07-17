/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szymborski.bartosz.serwis.pgnig.service;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import szymborski.bartosz.serwis.pgnig.dao.TorunamentDao;
import szymborski.bartosz.serwis.pgnig.dao.TournamentRuleSetDao;
import szymborski.bartosz.serwis.pgnig.dao.TournamentTemplateDao;
import szymborski.bartosz.serwis.pgnig.dao.TournamentTemplateItemDao;
import szymborski.bartosz.serwis.pgnig.entity.Tournament;
import szymborski.bartosz.serwis.pgnig.entity.TournamentRule;

/**
 *
 * @author bartosz.szymborski
 */
@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class TournamentServiceImpl implements TournamentService{

    @Autowired
    private TorunamentDao td;
    
    @Autowired
    private TournamentTemplateItemDao ttid;
    
    @Autowired
    private TournamentRuleSetDao trsd;
    
    @Autowired
    TournamentTemplateDao templateDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Tournament saveTournament(String name, String templateName) {
        Long templateId = templateDao.getTournamentTemplateName(templateName).getId();
        Tournament tournament = td.saveTournament(name);
        Map<TournamentRule,Object>map = ttid.readItemValues(templateId);
        trsd.saveTournamentRules(map, tournament);
        return tournament;
    }

    @Override
    public Tournament getTournamentId(Long valueOf) {
        return td.getTournamentId(valueOf);
    }
    
}
