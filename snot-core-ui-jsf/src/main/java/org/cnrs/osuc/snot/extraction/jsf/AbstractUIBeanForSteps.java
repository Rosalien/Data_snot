package org.cnrs.osuc.snot.extraction.jsf;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;
import java.util.stream.Collectors;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.html.HtmlPanelGroup;
import org.inra.ecoinfo.extraction.IParameter;
import org.inra.ecoinfo.localization.ILocalizationManager;
import org.inra.ecoinfo.mga.managedbean.IPolicyManager;
import org.inra.ecoinfo.utils.exceptions.BusinessException;
import org.primefaces.component.accordionpanel.AccordionPanel;
import org.primefaces.component.tabview.Tab;
import org.primefaces.component.wizard.Wizard;
import org.primefaces.event.FlowEvent;

/**
 * The Class AbstractUIBeanForSteps.
 */
public abstract class AbstractUIBeanForSteps implements Serializable {

    /**
     *
     */
    public static final TimeZone TIME_ZONE = TimeZone.getTimeZone("UTC");

    /**
     * The Constant TITLE_STEP.
     */
    private static final String TITLE_STEP = " %d : %s";
    /**
     * The step.
     */
    protected int step = 1;
    /**
     * The toggle panel.
     */
    private Wizard wizard;
    /**
     * The accordion.
     */
    private AccordionPanel accordion;
    /**
     * The panel.
     */
    private HtmlPanelGroup panel;
    
    /**
     *
     */
    @ManagedProperty(value = "#{localizationManager}")
    protected ILocalizationManager localizationManager;
    
    /**
     *
     */
    @ManagedProperty(value = "#{policyManager}")
    protected IPolicyManager policyManager;
    
    /**
     *
     */
    protected ICollector collector;

    /**
     *
     */
    protected List<IStepBuilder> stepBuilders = new LinkedList<>();

    /**
     * Instantiates a new abstract ui bean for steps.
     */
    public AbstractUIBeanForSteps() {
        super();
    }

    /**
     *
     * @param localizationManager
     */
    public void setLocalizationManager(ILocalizationManager localizationManager) {
        this.localizationManager = localizationManager;
    }

    /**
     * Gets the accordion.
     *
     * @return the accordion
     */
    public AccordionPanel getAccordion() {
        return accordion;
    }

    /**
     * Gets the change step.
     *
     * @return the change step
     */
    public String getChangeStep() {
        final Tab currentAccordion = accordion.findTab(accordion.getActiveIndex());
        final int currentAccordionRow = accordion.getChildren().indexOf(currentAccordion);
        // togglePanel.setActiveItem(((Panel) togglePanel.getChildren().get(currentAccordionRow)));
        step = currentAccordionRow + 1;
        return "";
    }

    /**
     * Gets the current toggle item.
     *
     * @return the current toggle item
     */
    public Tab getCurrentToggleItem() {
        return accordion.findTab(accordion.getActiveIndex());
    }

    /**
     * Gets the checks if is step valid.
     *
     * @return the checks if is step valid
     */
    public boolean getIsStepValid(){
        return stepBuilders.stream().skip(step-1).findFirst()
                .map(s->s.isStepValid())
                .orElse(false);
    }

    /**
     * Gets the panel.
     *
     * @return the panel
     */
    public HtmlPanelGroup getPanel() {
        return panel;
    }

    /**
     * Gets the step.
     *
     * @return the step
     */
    public int getStep() {
        return step;
    }

    /**
     * Gets the title for step.
     *
     * @return the title for step
     */
    public String getTitleForStep() {
        List<Tab> tabs = wizard.getChildren().stream()
                .filter(child->child instanceof Tab)
                .map(child->(Tab)child)
                .collect(Collectors.toList());
        Optional<Tab> tab = Optional.of(tabs.get(step-1));
        return String.format(AbstractUIBeanForSteps.TITLE_STEP, step, tab.map(t->t.getTitle()).orElse("no tab"));
    }

    /**
     *
     * @param event
     * @return
     */
    public String onFlowProcess(FlowEvent event) {
        return wizard.getChildren().get(step).getId();
    }

    /**
     * Gets the toggle panel.
     *
     * @return the toggle panel
     */
    public Wizard getWizard() {
        return wizard;
    }

    /**
     * Next step.
     *
     * @return the string
     */
    public String nextStep() {
        if (wizard.getChildren().get(step) != null) {
            wizard.setStep(wizard.getChildren().get(step).getId());
            if (accordion.getChildren().get(step) != null) {
                accordion.setActiveIndex(Integer.toString(step));
            }
            
            final Optional<IStepBuilder> previous = stepBuilders.stream().skip(step-1).findFirst();
            previous
                    .ifPresent(sb->sb.addCollectToParameter(collector));
            final Optional<IStepBuilder> findFirst = stepBuilders.stream().skip(step).findFirst();
            step++;
            findFirst
                    .ifPresent(s->s.init(localizationManager, policyManager, collector));
        }
        return null;
    }

    /**
     * Prev step.
     *
     * @return the string
     */
    public String prevStep() {
        if (wizard.getChildren().get(step - 2) != null) {
            wizard.setStep(wizard.getChildren().get(step - 2).getId());
            if (accordion.getChildren().get(step - 2) != null) {
                accordion.setActiveIndex(Integer.toString(step - 2));
            }
            step--;
        }
        return null;
    }

    /**
     * Sets the accordion.
     *
     * @param accordion the new accordion
     */
    public void setAccordion(AccordionPanel accordion) {
        this.accordion = accordion;
    }

    /**
     * Sets the panel.
     *
     * @param panel the new panel
     */
    public void setPanel(HtmlPanelGroup panel) {
        this.panel = panel;
    }

    /**
     * Sets the step.
     *
     * @param step the new step
     */
    public void setStep(int step) {
        this.step = step;
    }

    /**
     * Sets the toggle panel.
     *
     * @param panel the new toggle panel
     */
    public void setWizard(Wizard panel) {
        wizard = panel;
    }

    /**
     *
     * @return
     */
    public TimeZone getTimeZone() {
        return TIME_ZONE;
    }
    
    /**
     *
     * @param parameter
     */
    public void addParameterCollectionEntry(IParameter parameter){
        collector.setParameters(parameter);
    }
    
    /**
     *
     * @param step
     * @return
     * @throws BusinessException
     */
    public IStepBuilder getStepBuilder(int step) throws BusinessException{
        return stepBuilders.get(step);
    }

    /**
     *
     * @param policyManager
     */
    public void setPolicyManager(IPolicyManager policyManager) {
        this.policyManager = policyManager;
    }

    /**
     *
     * @return
     */
    public ICollector getCollector() {
        return collector;
    }

    /**
     *
     * @return
     */
    public List<IStepBuilder> getStepBuilders() {
        return stepBuilders;
    }
    
    /**
     *
     * @return
     */
    abstract public boolean isFormValid();
    
}
