/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szymborski.bartosz.serwis.pgnig.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import szymborski.bartosz.serwis.pgnig.dao.ContenderDao;
import szymborski.bartosz.serwis.pgnig.dao.TorunamentDao;
import szymborski.bartosz.serwis.pgnig.dao.TournamentContenderDao;
import szymborski.bartosz.serwis.pgnig.entity.Contender;
import szymborski.bartosz.serwis.pgnig.entity.Tournament;
import szymborski.bartosz.serwis.pgnig.entity.TournamentContender;

/**
 *
 * @author bartosz.szymborski
 */
@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class TournamentContenderServiceImpl implements TournamentContenderService {

    @Autowired
    private TournamentContenderDao tcd;

    @Autowired
    private ContenderDao contenderDao;

    @Autowired
    private TorunamentDao tournamentDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<TournamentContender> saveTournamentContender(Long idTorunament, Long... contenders) {
        List<Contender> conte = Stream.of(contenders).map(contenderDao::getContenderId).collect(Collectors.toList());
        Tournament tournament = tournamentDao.getTournamentId(idTorunament);
        return conte.stream().map(c -> tcd.saveTournamentContender(c, tournament)).collect(Collectors.toList());
    }

    @Override
    public List<TournamentContender> getTournamentContenderById(Long idTournament) {
       List<TournamentContender> contenders = tcd.getTournamentContendersById(idTournament);
       return contenders;
    }
    

}
