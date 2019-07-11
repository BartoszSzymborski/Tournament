/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szymborski.bartosz.serwis.pgnig.view;

import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import szymborski.bartosz.serwis.pgnig.entity.TournamentRule;
import szymborski.bartosz.serwis.pgnig.service.TourmentRuleServiceImpl;

/**
 *
 * @author bartosz.szymborski
 */
@Component
@Scope("view")
public class TournamentTemplateView {

    public static final String NAME = "NAME";

    private TournamentRule currentRule;
    private Object currentValue;
    ListIterator<TournamentRule> ruleIterator;
    private boolean iteratorMove;
    Map<String, Object> newValus = new HashMap<>();

    @Autowired
    private TourmentRuleServiceImpl trsi;

    @PostConstruct
    public void init() {

        List<TournamentRule> ruleList = trsi.gerRules();
        iteratorMove = true;
        ruleIterator = ruleList.listIterator();
        currentRule = ruleIterator.next();
    }

    public void prevRule() {
        if (ruleIterator.hasPrevious()) {
            currentValue = null;
            currentRule = ruleIterator.previous();
            if (iteratorMove) {
                iteratorMove = false;
                if (ruleIterator.hasPrevious()) {
                    currentRule = ruleIterator.previous();
                }
            }
            currentValue = newValus.get(currentRule.getName());
            System.out.println("currentValue::get " + currentValue);
        }

    }

    public void nextRule() {

        if (ruleIterator.hasNext()) {
            newValus.put(currentRule.getName(), currentValue);
            System.out.println("currentValue::put " + currentValue);
            currentValue = null;
            currentRule = ruleIterator.next();
            if (!iteratorMove) {
                iteratorMove = true;
                if (ruleIterator.hasNext()) {
                    currentRule = ruleIterator.next();//przeskok
                }
            }
        }
    }

    public boolean isIteratorMove() {
        return iteratorMove;
    }

    public void setIteratorMove(boolean iteratorMove) {
        this.iteratorMove = iteratorMove;
    }

    public TournamentRule getCurrentRule() {
        return currentRule;
    }

    public void setCurrentRule(TournamentRule currentRule) {
        this.currentRule = currentRule;
    }

    public ListIterator<TournamentRule> getRuleIterator() {
        return ruleIterator;
    }

    public void setRuleIterator(ListIterator<TournamentRule> ruleIterator) {
        this.ruleIterator = ruleIterator;
    }

    public Object getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(Object currentValue) {
        this.currentValue = currentValue;
    }

}
