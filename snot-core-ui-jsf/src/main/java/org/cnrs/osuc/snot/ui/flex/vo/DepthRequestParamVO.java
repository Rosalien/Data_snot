package org.cnrs.osuc.snot.ui.flex.vo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.faces.application.FacesMessage;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.ValueHolder;
import javax.faces.context.FacesContext;
import javax.xml.bind.ValidationException;
import org.inra.ecoinfo.localization.ILocalizationManager;

/**
 *
 * @author ptcherniati
 */
public class DepthRequestParamVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String PATTERN_STRING_DEPTH_SUMMARY = "   %s: %d";

    /**
     *
     */
    protected static final String BUNDLE_SOURCE_PATH = "org.cnrs.osuc.snot.extraction.meteosol.jsf.extraction-meteosol-jsf";
    private Integer depthMin = 0;

    private Integer depthMax = 0;
    private Boolean choiceDepth = true;

    private Boolean validMinMaxDepht = false;
    private SortedSet<ProfondeurJSF> availablesDepth = new TreeSet<>();

    private SortedSet<Integer> selectedsDepths = new TreeSet<>();

    private List<Integer> lstSelectedDepthDisplay;

    private int selectedDepth;

    private ILocalizationManager localizationManager;

    /**
     *
     */
    public DepthRequestParamVO() {
        super();
    }

    /**
     *
     * @param localizationManager
     */
    public DepthRequestParamVO(ILocalizationManager localizationManager) {
        super();
        this.localizationManager = localizationManager;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DepthRequestParamVO other = (DepthRequestParamVO) obj;
        return Objects.equals(this.availablesDepth, other.availablesDepth);
    }

    /**
     *
     * @param profondeur
     */
    public void addProfondeur(Integer profondeur) {
        this.availablesDepth.add(new ProfondeurJSF(profondeur));
    }

    /**
     *
     * @return
     */
    public SortedSet<ProfondeurJSF> getAvailablesDepth() {
        return this.availablesDepth;
    }

    /**
     *
     * @return
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public List<ProfondeurJSF> getAvailablesDepthList() {
        return new LinkedList(this.availablesDepth);
    }

    /**
     *
     * @return
     */
    public Boolean getChoiceDepth() {
        return this.choiceDepth;
    }

    /**
     *
     * @param allDepth
     */
    public void setChoiceDepth(Boolean allDepth) {
        this.choiceDepth = allDepth;
    }

    /**
     *
     * @return
     */
    public Integer getDepthMax() {
        return this.depthMax;
    }

    /**
     *
     * @param depthMax
     */
    public void setDepthMax(Integer depthMax) {
        this.depthMax = depthMax;
    }

    /**
     *
     * @return
     */
    public Integer getDepthMin() {
        return this.depthMin;
    }

    /**
     *
     * @param depthMin
     */
    public void setDepthMin(Integer depthMin) {
        this.depthMin = depthMin;
    }

    /**
     *
     * @return
     */
    public boolean getIsDepthStepValid() {
        return !this.choiceDepth && this.validMinMaxDepht || this.choiceDepth && !this.availablesDepth.isEmpty();
    }

    /**
     * @return the lstSelectedDepthDisplay
     */
    public List<Integer> getLstSelectedDepthDisplay() {
        this.lstSelectedDepthDisplay = new ArrayList<>();
        this.lstSelectedDepthDisplay.addAll(this.selectedsDepths);
        return this.lstSelectedDepthDisplay;
    }

    /**
     * @param lstSelectedDepthDisplay the lstSelectedDepthDisplay to set
     */
    public void setLstSelectedDepthDisplay(List<Integer> lstSelectedDepthDisplay) {
        this.lstSelectedDepthDisplay = lstSelectedDepthDisplay;
    }

    /**
     *
     * @return
     */
    public int getSelectedDepth() {
        return this.selectedDepth;
    }

    /**
     *
     * @param selectedDepth
     */
    public void setSelectedDepth(int selectedDepth) {
        this.selectedDepth = selectedDepth;
    }

    /**
     *
     * @return
     */
    public SortedSet<Integer> getSelectedsDepths() {
        return this.selectedsDepths;
    }

    /**
     *
     * @return
     */
    public String getSummaryHTML() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(bos);
        printStream.println(String.format("<div style='display:block'>%s</div>", String.format(this.localizationManager.getMessage(DepthRequestParamVO.BUNDLE_SOURCE_PATH, "MSG_PROFONDEUR_ACCORDIONITEM_INTERVAL_SELECT"))));
        printStream.println(String.format("<ul><li>".concat(DepthRequestParamVO.PATTERN_STRING_DEPTH_SUMMARY).concat("</li></ul>"), String.format(this.localizationManager.getMessage(DepthRequestParamVO.BUNDLE_SOURCE_PATH, "MSG_PROFONDEUR_ACCORDIONITEM_MIN")), this.getDepthMin()));
        printStream.println(String.format("<ul><li>".concat(DepthRequestParamVO.PATTERN_STRING_DEPTH_SUMMARY).concat("</li></ul>"), String.format(this.localizationManager.getMessage(DepthRequestParamVO.BUNDLE_SOURCE_PATH, "MSG_PROFONDEUR_ACCORDIONITEM_MAX")), this.getDepthMax()));
        printStream.println();
        try {
            bos.flush();
        } catch (IOException e) {
            e.getMessage();
        }
        return bos.toString();
    }

    /**
     *
     * @return
     */
    public Boolean getValidMinMaxDepht() {
        return this.validMinMaxDepht;
    }

    /**
     *
     * @param validMinMaxDepht
     */
    public void setValidMinMaxDepht(Boolean validMinMaxDepht) {
        this.validMinMaxDepht = validMinMaxDepht;
    }

    /**
     *
     * @return
     */
    public Boolean isValidDepht() {
        return this.choiceDepth && !this.selectedsDepths.isEmpty() || this.validMinMaxDepht;
    }

    /**
     *
     * @param context
     * @param component
     * @param value
     * @throws ValidationException
     */
    public void validateDepth(FacesContext context, UIComponent component, Object value) throws ValidationException {
        if (this.getAvailablesDepth().isEmpty()) {
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_WARN, "", this.localizationManager.getMessage(DepthRequestParamVO.BUNDLE_SOURCE_PATH, "MSG_PROFONDEUR_NO_DEPHT")));
            this.validMinMaxDepht = false;
            this.validMinMaxDepht = false;
            return;
        }
        int depthMin = 0;
        int depthMax = 0;

        int depthAvailableMin = this.getAvailablesDepth().first().getProfondeur();
        int depthAvailableMax = this.getAvailablesDepth().last().getProfondeur();

        String depthMinString = ((ValueHolder) component.getParent().getChildren().get(1)).getValue().toString();
        String depthMaxString = ((ValueHolder) component.getParent().getChildren().get(2)).getValue().toString();

        try {
            depthMin = Integer.parseInt(depthMinString);
            depthMax = Integer.parseInt(depthMaxString);
        } catch (NumberFormatException e) {
            ((EditableValueHolder) component).setValid(false);
            this.validMinMaxDepht = false;
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_WARN, "", this.localizationManager.getMessage(DepthRequestParamVO.BUNDLE_SOURCE_PATH, "MSG_PROFONDEUR_ACCORDIONITEM_ERROR")));
        }

        if (depthMin < 0 || depthMax < 0) {
            ((EditableValueHolder) component).setValid(false);
            this.validMinMaxDepht = false;
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_WARN, "", this.localizationManager.getMessage(DepthRequestParamVO.BUNDLE_SOURCE_PATH, "MSG_PROFONDEUR_ACCORDIONITEM_ERROR")));
        } else if (depthMin == 0 && depthMax == 0) {
            ((EditableValueHolder) component).setValid(false);
            this.validMinMaxDepht = false;

            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_WARN, "", this.localizationManager.getMessage(DepthRequestParamVO.BUNDLE_SOURCE_PATH, "MSG_PROFONDEUR_ACCORDIONITEM_INTERVAL")));
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_INFO, "", this.localizationManager.getMessage(DepthRequestParamVO.BUNDLE_SOURCE_PATH, "MSG_PROFONDEUR_ACCORDIONITEM_INTERVAL_MIN") + depthAvailableMin));
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_INFO, "", this.localizationManager.getMessage(DepthRequestParamVO.BUNDLE_SOURCE_PATH, "MSG_PROFONDEUR_ACCORDIONITEM_INTERVAL_MAX") + depthAvailableMax));
        } else if (depthMin >= depthAvailableMax && depthMin < depthMax) {
            ((EditableValueHolder) component).setValid(true);
            this.validMinMaxDepht = true;
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_WARN, "", this.localizationManager.getMessage(DepthRequestParamVO.BUNDLE_SOURCE_PATH, "MSG_PROFONDEUR_ACCORDIONITEM_INTERVAL_ERROR1")));
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_INFO, "", this.localizationManager.getMessage(DepthRequestParamVO.BUNDLE_SOURCE_PATH, "MSG_PROFONDEUR_ACCORDIONITEM_INTERVAL_ERROR2")
                    + depthAvailableMin));
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_INFO, "", this.localizationManager.getMessage(DepthRequestParamVO.BUNDLE_SOURCE_PATH, "MSG_PROFONDEUR_ACCORDIONITEM_INTERVAL_ERROR3")
                    + depthAvailableMax));
        } else if (depthMax <= depthAvailableMin && depthMin < depthMax) {
            ((EditableValueHolder) component).setValid(true);
            this.validMinMaxDepht = true;
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_WARN, "", this.localizationManager.getMessage(DepthRequestParamVO.BUNDLE_SOURCE_PATH, "MSG_PROFONDEUR_ACCORDIONITEM_INTERVAL_ERROR1")));
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_INFO, "", this.localizationManager.getMessage(DepthRequestParamVO.BUNDLE_SOURCE_PATH, "MSG_PROFONDEUR_ACCORDIONITEM_INTERVAL_ERROR2")
                    + depthAvailableMin));
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_INFO, "", this.localizationManager.getMessage(DepthRequestParamVO.BUNDLE_SOURCE_PATH, "MSG_PROFONDEUR_ACCORDIONITEM_INTERVAL_ERROR3")
                    + depthAvailableMax));
        } else if (depthMin > depthMax) {
            ((EditableValueHolder) component).setValid(false);
            this.validMinMaxDepht = false;
            context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_WARN, "", this.localizationManager.getMessage(DepthRequestParamVO.BUNDLE_SOURCE_PATH, "MSG_PROFONDEUR_ACCORDIONITEM_ERROR_INF")));
        } else {
            this.validMinMaxDepht = true;
            ((EditableValueHolder) component).setValid(true);
        }

    }

    /**
     *
     */
    public class ProfondeurJSF implements Comparable<ProfondeurJSF> {


        private Integer profondeur;
        private boolean selected = false;

        /**
         *
         * @param profondeur
         */
        public ProfondeurJSF(Integer profondeur) {
            super();
            this.profondeur = profondeur;
        }
        @Override
        public int hashCode() {
            int hash = 7;
            hash = 67 * hash + Objects.hashCode(this.profondeur);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final ProfondeurJSF other = (ProfondeurJSF) obj;
            return Objects.equals(this.profondeur, other.profondeur);
        }

        @Override
        public int compareTo(ProfondeurJSF o) {
            if (this.getProfondeur() == o.getProfondeur()) {
                return 0;
            }
            return this.getProfondeur() < o.getProfondeur() ? -1 : 1;
        }

        /**
         *
         * @return
         */
        public Integer getProfondeur() {
            return this.profondeur;
        }

        /**
         *
         * @param profondeur
         */
        public void setProfondeur(Integer profondeur) {
            this.profondeur = profondeur;
        }

        /**
         *
         * @return
         */
        public boolean getSelected() {
            return this.selected;
        }

        /**
         *
         * @param selected
         */
        public void setSelected(boolean selected) {
            this.selected = selected;
        }
    }
}
