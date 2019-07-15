/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szymborski.bartosz.serwis.pgnig.service;

import java.awt.geom.Arc2D;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import szymborski.bartosz.serwis.pgnig.dao.TournamentTemplateDao;
import szymborski.bartosz.serwis.pgnig.entity.TournamentTemplate;

/**
 *
 * @author bartosz.szymborski
 */
@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class TournamentTemplateServiceImpl implements TournamentTemplateService {

    @Autowired
    private TournamentTemplateDao ttd;

    @Override
    public List<TournamentTemplate> getTemplates() {
        List<TournamentTemplate> templates = new ArrayList<>(ttd.getTournamentTemplateList());
        return templates;
    }

   

}
