/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szymborski.bartosz.serwis.pgnig.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import szymborski.bartosz.serwis.pgnig.dao.ContenderDao;
import szymborski.bartosz.serwis.pgnig.entity.Contender;

/**
 *
 * @author bartosz.szymborski
 */
@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ContenderServiceImpl implements ContenderService{

    @Autowired
    private ContenderDao cd;
    
    @Override
    public List<Contender> getContenders() {
       List<Contender> contenders = new ArrayList<>(cd.getContendersList());
       return contenders;
    }

    
}
