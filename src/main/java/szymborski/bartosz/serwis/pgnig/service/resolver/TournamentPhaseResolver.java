/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szymborski.bartosz.serwis.pgnig.service.resolver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import szymborski.bartosz.serwis.pgnig.entity.TournamentEncounter;
import szymborski.bartosz.serwis.pgnig.entity.TournamentEncounterTree;
import szymborski.bartosz.serwis.pgnig.enums.TournamentRuleEnum;

/**
 *
 * @author bartosz.szymborski
 */
public interface TournamentPhaseResolver {

    public static TournamentPhaseResolver getTournamentPhaseResolver(int startIndex, int currentIndex, Map<String, Object> rules, Param param) {
        if (currentIndex == 0) {
            return null;
        }
        Boolean isMecz3 = (Boolean) rules.get(TournamentRuleEnum.MECZ_O_TRZECIE_MIEJSCE.name());
        Boolean isFazaGrupowaNext = (Boolean) rules.get(TournamentRuleEnum.DRUGA_FAZA_TURNIEJU_GRUPOWA.name());
        if (!isFazaGrupowaNext) {
            if (isMecz3 && startIndex == currentIndex) { //finał
                return new ResolverImpl(currentIndex - 2, param);
            } else if (isMecz3 && currentIndex == startIndex - 1) {//trzecie miejsce
                return new ResolverBrandNewImpl(currentIndex - 1, param);
            } else if (param.countEncounters(currentIndex) == param.countEncounters(currentIndex - 1)) {
                return new ResolverTreeChacheImpl(param, rules);
            } else {
                return new ResolverImpl(currentIndex - 1, param); //tutaj mamy błąd
            }
        } else {
            return new TournamentPhaseResolver() {
                @Override
                public List<TournamentEncounterTree> resolve(TournamentEncounter enc) {
                    List<TournamentEncounterTree> ent = new ArrayList<>();
                    for (TournamentEncounter en : param.getSubEncounters(0)) {
                        TournamentEncounterTree tet = new TournamentEncounterTree();
                        tet.setIdtournamentencounternext(enc);
                        tet.setIdtournamentencounterprevious(en);
                        ent.add(tet);
                    }
                    return ent;

                }

            };
        }
    }

    List<TournamentEncounterTree> resolve(TournamentEncounter enc);

    public static class Param { //klasa anonimowa

        private final Map<Integer, List<TournamentEncounter>> subEncounters = new HashMap<>();
        private final Map<Integer, List<TournamentEncounter>> encounterTreeMap;
        private final Map<Integer, List<TournamentEncounterTree>> treeCache = new HashMap<>();

        public Param(Map<Integer, List<TournamentEncounter>> encounterTreeMap) {
            this.encounterTreeMap = encounterTreeMap;
        }

        public List<TournamentEncounter> getSubEncounters(int stage) {
            List<TournamentEncounter> subs = subEncounters.get(stage);
            if (subs == null) {
                subEncounters.put(stage, new ArrayList<>(encounterTreeMap.get(stage)));
                subs = subEncounters.get(stage);
            }
            return subs;
        }

        public List<TournamentEncounter> getBrandNewSubEncounters(int stage) {
            return new ArrayList<>(encounterTreeMap.get(stage));
        }

        public int countEncounters(int stage) {
            return (int) Optional.ofNullable(encounterTreeMap.get(stage)).orElse(new ArrayList<>()).stream().count();
        }

        public Map<Integer, List<TournamentEncounterTree>> getTreeCache() {
            return treeCache;
        }

    }

}
