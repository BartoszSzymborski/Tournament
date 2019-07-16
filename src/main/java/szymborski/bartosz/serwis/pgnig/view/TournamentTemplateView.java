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
import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
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

    public void prevRule() throws ScriptException {
        if (ruleIterator.hasPrevious()) {
            currentValue = null;
            currentRule = ruleIterator.previous();
            if (iteratorMove) {
                iteratorMove = false;
                if (ruleIterator.hasPrevious()) {
                    currentRule = ruleIterator.previous();

                }
            }
            while (ruleIterator.hasPrevious() && !shouldBeShow(currentRule.getDepenceOnCurrentRule())) {
                currentRule = ruleIterator.previous(); //pętla dla Groovy
            }
            currentValue = newValus.get(currentRule.getName());
        }
        isLastStep = false;

    }

    public void nextRule() throws ScriptException {
        boolean validate = validateInputValues();
        if (validate) {
            return;
        }
        if (ruleIterator.hasNext()) {
            newValus.put(currentRule.getName(), currentValue);
            currentValue = null;
            currentRule = ruleIterator.next();
            if (!iteratorMove) {
                iteratorMove = true;
                if (ruleIterator.hasNext()) {
                    currentRule = ruleIterator.next();//przeskok

                }
            }
            while (!shouldBeShow(currentRule.getDepenceOnCurrentRule())) {
                if(!ruleIterator.hasNext()){
                    isLastStep = true;
                    break;//pętla dla groovy - ważna - break = w przypadku gdy już nasz pierwszy warunek liczba_drużyn  jest ustawiony mniej niż 3 - przechodzi od razu do zapisu templata
                }
                currentRule = ruleIterator.next();
            }
        } else {
            isLastStep = true;
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveMyValues() {
        if (templateName.equals("")) {
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
                item.setIntegerValue(((Integer) ent.getValue()).shortValue());
            }
            items.add(item);
        }

        ttid.saveTournamentTemplateItem(items);
        closeDialog();

    }

    public String getValidateFileId() {
        return "daForm:" + (currentRule.getIntegralType() ? "valinp" : "valinp2"); //identyfikacja czy bole wpisane gotowe do zapisu jest Integer czy Boolean
    }

    public static final String BAD_VALUE_TYPE = "bad_value_type";

    //walidacja pola w formularzu
    public boolean validateInputValues() {
        if (currentValue == null) {
            String str = ResourceBundle.getBundle("messages").getString(BAD_VALUE_TYPE);
            final FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, str, str);
            FacesContext.getCurrentInstance().addMessage(getValidateFileId(), facesMessage);
            return true;
        }
        return false;
    }

    public boolean shouldBeShow(String expression) throws ScriptException { // metoda dla scryptu Goovy
        ScriptEngineManager factory = new ScriptEngineManager();
        ScriptEngine engine = factory.getEngineByName("groovy");
        Bindings bindings = engine.createBindings(); //wywołanie
        bindings.put("daMap", newValus); //druga wartość - twoja mapa z wartościami, pierwsza wartości do odczytu w skrypcie
        engine.setBindings(bindings, ScriptContext.GLOBAL_SCOPE);
        String scr = "def rvalue(String key){return daMap.get(key)} \n " + expression; //rvalue - wartość którą nadałem w bazie w kolumnie depenceOnCurrentRule - pracuje na String
        return (Boolean) engine.eval(scr);
    }

    public void closeDialog() {
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
