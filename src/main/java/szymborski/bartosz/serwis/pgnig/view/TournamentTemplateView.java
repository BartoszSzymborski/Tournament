/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szymborski.bartosz.serwis.pgnig.view;

import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.primefaces.context.PrimeFacesContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import szymborski.bartosz.serwis.pgnig.service.RuleService;

/**
 *
 * @author bartosz.szymborski
 */
@Component
@Scope("view")
public class TournamentTemplateView {

    public static final String NAME = "NAME";

    private String name;
    ListIterator<String> ruleIterator;
    private boolean iteratorMove;

    @Autowired
    private RuleService rs;

    @PostConstruct
    public void init() {
        final Map<String, String> requestParameterMap = PrimeFacesContext.getCurrentInstance()
                .getExternalContext()
                .getRequestParameterMap();
        name = requestParameterMap.get("name");
        if (name == null) {
            return;
        }
        // List<String> ruleList = rs;
        iteratorMove = true;
        //ruleIterator = ruleList.listIterator();

    }

    public void prevRule() {
        String rule = null;
        if (ruleIterator.hasPrevious()) {
            rule = ruleIterator.previous();
            if (iteratorMove) {
                iteratorMove = false;
                prevRule();
                return;
            }
        }else{
            while(ruleIterator.hasNext()){
                rule = ruleIterator.next();
            }
            ruleIterator.previous();
        }
       // rule = rs.
       iteratorMove = false;
    }
    
    public void nextRule() {
        String rule = null;
        if (ruleIterator.hasNext()) {
            rule = ruleIterator.next();
            if (iteratorMove) {
                iteratorMove = false;
                nextRule();
                return;
            }
        }else{
            while(ruleIterator.hasPrevious()){
                rule = ruleIterator.previous();
            }
            ruleIterator.next();
        }
       // rule = rs.
       iteratorMove = true;
    }
    
    

    public boolean isIteratorMove() {
        return iteratorMove;
    }

    public void setIteratorMove(boolean iteratorMove) {
        this.iteratorMove = iteratorMove;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ListIterator<String> getRuleIterator() {
        return ruleIterator;
    }

    public void setRuleIterator(ListIterator<String> ruleIterator) {
        this.ruleIterator = ruleIterator;
    }

}
