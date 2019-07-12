/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szymborski.bartosz.serwis.pgnig.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import szymborski.bartosz.serwis.pgnig.dao.TournamentTemplateDao;
import szymborski.bartosz.serwis.pgnig.dao.TournamentTemplateItemDao;
import szymborski.bartosz.serwis.pgnig.entity.TorunamentTemplateItem;
import szymborski.bartosz.serwis.pgnig.entity.TournamentRule;
import szymborski.bartosz.serwis.pgnig.entity.TournamentTemplate;
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
    private boolean isLastStep;
    private String templateName;
    private Map<String, TournamentRule> ruleMap;

    @Autowired
    private TourmentRuleServiceImpl trsi;

    @Autowired
    private TournamentTemplateDao ttd;

    @Autowired
    private TournamentTemplateItemDao ttid;

    @PostConstruct
    public void init() {

        List<TournamentRule> ruleList = trsi.gerRules();
        iteratorMove = true;
        ruleIterator = ruleList.listIterator();
        currentRule = ruleIterator.next();
        ruleMap = ruleList.stream()
                .collect(Collectors.toMap(TournamentRule::getName, Function.identity()));

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
        isLastStep = false;

    }

    public void nextRule() {
        boolean validate = validateInputValues();
        if(validate){
            return;
        }
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
        } else {
            isLastStep = true;
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveMyValues() {
        if(templateName.equals("")){
            return;
        }
        TournamentTemplate temp = ttd.saveTemplate(templateName);
        List<TorunamentTemplateItem> items = new ArrayList<>();
        for (Map.Entry<String, Object> ent : newValus.entrySet()) {
            TorunamentTemplateItem item = new TorunamentTemplateItem();
            item.setIdTournamentRule(ruleMap.get(ent.getKey()));
            item.setIdTournamentTemplate(temp);
            if (ent.getValue() instanceof Boolean) {
                item.setBooleanValue((Boolean) ent.getValue());
            } else {
                item.setIntegerValue(Short.valueOf((String) ent.getValue()));
            }
            items.add(item);
        }

        ttid.saveTournamentTemplateItem(items);
        closeDialog();

    }

    public String getValidateFileId(){
        return "daForm:" + (currentRule.getIntegralType() ? "valinp" : "valinp2"); //identyfikacja czy bole wpisane gotowe do zapisu jest Integer czy Boolean
    }

    public static final String BAD_VALUE_TYPE = "bad_value_type";
    //walidacja pola
    public boolean validateInputValues() {
        if (currentValue == null) {
            String str = ResourceBundle.getBundle("messages").getString(BAD_VALUE_TYPE);
            final FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, str, str);
            FacesContext.getCurrentInstance().addMessage(getValidateFileId(), facesMessage);
            return true;
        }
        return false;
    }
    
    public void closeDialog(){
        PrimeFaces.current().dialog().closeDynamic(Boolean.TRUE);
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

    public boolean isIsLastStep() {
        return isLastStep;
    }

    public void setIsLastStep(boolean isLastStep) {
        this.isLastStep = isLastStep;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }
}
