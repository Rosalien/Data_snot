/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cnrs.osuc.snot.extraction.jsf.date;

import org.cnrs.osuc.snot.extraction.jsf.date.AbstractDatesFormParam;
import org.cnrs.osuc.snot.extraction.jsf.date.DatesFormParamVO;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.cnrs.osuc.snot.extraction.jsf.ICollector;
import org.cnrs.osuc.snot.extraction.jsf.IStepBuilder;
import org.inra.ecoinfo.localization.ILocalizationManager;
import org.inra.ecoinfo.mga.managedbean.IPolicyManager;

/**
 *
 * @author tcherniatinsky
 */
public class UIDate implements IStepBuilder<AbstractDatesFormParam> {

    /**
     *
     */
    public static final String PARAMETER_CODE = AbstractDatesFormParam.class.getSimpleName();

    /**
     * The helper form @link(HelperForm).
     */
    HelperForm helperForm = new HelperForm();
    /**
     * The dates request param @link(DatesRequestParamVO).
     */
    DatesRequestParamVO datesRequestParam;

    /**
     *
     * @param datesRequestParamVO
     */
    public UIDate(DatesRequestParamVO datesRequestParamVO) {
        this.datesRequestParam = datesRequestParamVO;
    }

    // TODO
    /**
     * Adds the period years continuous.
     *
     * @return the string
     */
    public final String addPeriodYearsContinuous() {
        return null;
    }

    /**
     * @return the helperForm
     */
    public HelperForm getHelperForm() {
        return this.helperForm;
    }

    /**
     * Removes the period years continuous.
     *
     * @return the string
     */
    public final String removePeriodYearsContinuous() {
        if (getDateFormParam().getPeriods().size() <= 1) {
            return null;
        }
        getDateFormParam().getPeriods().remove(getDateFormParam().getPeriods().get(getDateFormParam().getPeriods().size() - 1));
        return null;
    }

    /**
     * @param helperForm the helperForm to set
     */
    public void setHelperForm(HelperForm helperForm) {
        this.helperForm = helperForm;
    }

    /**
     * Update date year continuous.
     *
     * @return the string
     */
    public final String updateDateYearContinuous() {
        final String key = this.helperForm.getKey();
        final Integer index = this.helperForm.getIndex();
        final String value = this.helperForm.getValue();
        getDateFormParam()
                .getPeriods().get(index).put(key, value);
        return null;
    }
    
    private DatesFormParamVO getDateFormParam() {
        return (DatesFormParamVO) datesRequestParam.getDatesFormParam();
    }

    /**
     * Gets the dates form1 param vo.
     *
     * @return the dates form1 param vo
     */
    public AbstractDatesFormParam getDatesForm1ParamVO() {
        return (AbstractDatesFormParam) this.datesRequestParam.getDatesFormParam();
    }

    /**
     * Gets the date step is valid.
     *
     * @return the date step is valid
     */
    public Boolean getDateStepIsValid() {
        return Optional.ofNullable(datesRequestParam)
                .map(dfp->(AbstractDatesFormParam) dfp.getDatesFormParam())
                .map(dfp -> dfp.getIsValid())
                .orElse(false);
    }

    /**
     * Gets the dates request param.
     *
     * @return the dates request param
     */
    public DatesRequestParamVO getDatesRequestParam() {
        return this.datesRequestParam;
    }

    /**
     *
     * @param datesRequestParam
     */
    public void setDatesRequestParam(DatesRequestParamVO datesRequestParam) {
        this.datesRequestParam = datesRequestParam;
    }

    /**
     *
     * @param metadatasMap
     */
    public void addDatestoMap(Map<String, Object> metadatasMap) {
        metadatasMap.put(DatesFormParamVO.class.getSimpleName(), getDatesRequestParam().getDatesFormParam());
    }
    
    /**
     *
     * @param collector
     */
    @Override
    public void addCollectToParameter(ICollector collector) {
        collector.addParameterCollectionEntry(PARAMETER_CODE, getDatesRequestParam().getDatesFormParam().copy());
    }
    
    /**
     *
     * @param localizationManager
     * @param policyManager
     * @param collector
     */
    @Override
    public void init(ILocalizationManager localizationManager, IPolicyManager policyManager, ICollector collector) {
        Map<String, String> frequences = new HashMap();
    }
    
    /**
     *
     * @return
     */
    @Override
    public boolean isStepValid() {
        return getDateStepIsValid();
    }

    /**
     * The Class HelperForm.
     */
    public static class HelperForm {

        /**
         * The index @link(Integer).
         */
        Integer index;
        /**
         * The key @link(String).
         */
        String key;
        /**
         * The value @link(String).
         */
        String value;

        /**
         * Gets the index.
         *
         * @return the index
         */
        public Integer getIndex() {
            return this.index;
        }

        /**
         * Gets the key.
         *
         * @return the key
         */
        public String getKey() {
            return this.key;
        }

        /**
         * Gets the value.
         *
         * @return the value
         */
        public String getValue() {
            return this.value;
        }

        /**
         * Sets the index.
         *
         * @param index the new index
         */
        public final void setIndex(final Integer index) {
            this.index = index;
        }

        /**
         * Sets the key.
         *
         * @param key the new key
         */
        public final void setKey(final String key) {
            this.key = key;
        }

        /**
         * Sets the value.
         *
         * @param value the new value
         */
        public final void setValue(final String value) {
            this.value = value;
        }
    }
    
}
