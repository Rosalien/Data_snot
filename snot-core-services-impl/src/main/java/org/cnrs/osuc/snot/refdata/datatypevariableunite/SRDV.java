/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cnrs.osuc.snot.refdata.datatypevariableunite;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.cnrs.osuc.snot.refdata.site.SiteSnot;
import static org.cnrs.osuc.snot.utils.Constantes.CST_HYPHEN;
import org.inra.ecoinfo.localization.ILocalizationManager;
import org.inra.ecoinfo.mga.business.composite.RealNode;
import org.inra.ecoinfo.mga.middleware.IMgaRecorder;
import org.inra.ecoinfo.refdata.datatype.DataType;
import org.inra.ecoinfo.refdata.theme.Theme;
import org.inra.ecoinfo.utils.Utils;
import static org.inra.ecoinfo.utils.exceptions.BadsFormatsReport.BUNDLE_SOURCE_PATH;

/**
 *
 * @author ptchernia
 */
public class SRDV {

    /**
     *
     */
//    protected static final String BUNDLE_SOURCE_PATH = "org.cnrs.osuc.snot.refdata.messages";
    private static final String MISSING_SITE_THEME_DATATYPE_VARIABLE_REALNODE = "MISSING_SITE_THEME_DATATYPE_VARIABLE_REALNODE";

    /**
     *
     */
    public static final String PATTERN_REALNODE_PATH = "%s,%s,%s,%s";
    
    /**
     *
     * @param siteCode
     * @param themeCode
     * @param datatypeCode
     * @param variableCode
     * @return
     */
    public static String getCodeFromSiteCodeThemeCodeDatatypeCodeVariableCode(String siteCode, String themeCode, String datatypeCode, String variableCode){
        return String.format(PATTERN_REALNODE_PATH, siteCode, themeCode, datatypeCode, variableCode);
    }

    List<String> values = new LinkedList();
    String code;
    RealNode realNode;
    String errorMessage;
    ILocalizationManager localizationManager;
    IMgaRecorder mgaRecorder;
    IDatatypeVariableUniteSnotDAO datatypeVariableUniteSnotDAO;

    /**
     *
     * @param nomStdtVar
     * @param localizationManager
     * @param mgaRecorder
     * @param datatypeVariableUniteSnotDAO
     */
    public SRDV(String nomStdtVar, ILocalizationManager localizationManager, IMgaRecorder mgaRecorder, IDatatypeVariableUniteSnotDAO datatypeVariableUniteSnotDAO) {
        this.localizationManager = localizationManager;
        this.mgaRecorder = mgaRecorder;
        this.datatypeVariableUniteSnotDAO = datatypeVariableUniteSnotDAO;
        this.code = nomStdtVar;
        this.values = Stream.of(nomStdtVar.split(CST_HYPHEN))
                .map(String::trim)
                .collect(Collectors.toList());
        Optional<RealNode> rnOpt = getRealNodeFromCodes(nomStdtVar);
        //Optional<RealNode> rnOpt = getRealNodeFromCodes(nomStdtVar);
        if (rnOpt.isPresent()) {
            this.realNode = rnOpt.get();
        } else {
            errorMessage = String.format(
                    localizationManager.getMessage(BUNDLE_SOURCE_PATH, MISSING_SITE_THEME_DATATYPE_VARIABLE_REALNODE),
                    getStringValue(0, true),
                    getStringValue(1, true),
                    getStringValue(2, true),
                    getStringValue(3, false)
            );
        }
    }

    /**
     *
     * @param realNode
     * @param localizationManager
     * @param mgaRecorder
     * @param datatypeVariableUniteSnotDAO
     */
    public SRDV(RealNode realNode, ILocalizationManager localizationManager, IMgaRecorder mgaRecorder, IDatatypeVariableUniteSnotDAO datatypeVariableUniteSnotDAO) {
        this.localizationManager = localizationManager;
        this.mgaRecorder = mgaRecorder;
        this.datatypeVariableUniteSnotDAO = datatypeVariableUniteSnotDAO;
        Optional<RealNode> rnOpt = Optional.ofNullable(realNode);
        if (rnOpt.isPresent()) {
            this.realNode = rnOpt.get();
            this.code = getCodeForRealNode(realNode);
            this.values = Stream.of(code.split(CST_HYPHEN))
                    .map(Utils::createCodeFromString)
                    .map(String::trim)
                    .collect(Collectors.toList());
        } else {
            this.errorMessage = String.format(
                    localizationManager.getMessage(BUNDLE_SOURCE_PATH, MISSING_SITE_THEME_DATATYPE_VARIABLE_REALNODE),
                    getStringValue(0, true),
                    getStringValue(1, true),
                    getStringValue(2, true),
                    getStringValue(3, false)
            );
        }
    }

    /**
     *
     * @param index
     * @param codifie
     * @return
     */
    public  String getStringValue(int index, boolean codifie) {
        String value = values.get(index);
        if(codifie){
            value = Utils.createCodeFromString(value);
        }
        return value == null ? "" : value;
    }

    /**
     *
     * @param nomStdtVar
     * @return
     */
    public  Optional<RealNode> getRealNodeFromCodes(String nomStdtVar) {;
        final String siteCode = getStringValue(0, true);
        final String themeCode = getStringValue(1, true);
        final String datatypeCode = getStringValue(2, true);
        final String variableCode = getStringValue(3, false);
        String dvuCode = datatypeVariableUniteSnotDAO.getBySiteDatatype(siteCode,datatypeCode)
                .stream()
                .filter(
                        dvu->dvu.getVariable().getCode().equals(variableCode)
                )
                .map(
                        dvu->dvu.getCode()
                )
                .findFirst()
                .orElseGet(String::new);
        return mgaRecorder.getRealNodeByNKey(String.format(PATTERN_REALNODE_PATH, siteCode, themeCode, datatypeCode, dvuCode));
    }

    /**
     *
     * @param realNode
     * @return
     */
    public  String getCodeForRealNode(RealNode realNode) {
        return realNode.getNodeByNodeableTypeResource(SiteSnot.class).getCode() + CST_HYPHEN + 
                realNode.getNodeByNodeableTypeResource(Theme.class).getCode() + CST_HYPHEN + 
                realNode.getNodeByNodeableTypeResource(DataType.class).getCode() + CST_HYPHEN + 
                ((DatatypeVariableUniteSnot)realNode.getNodeByNodeableTypeResource(DatatypeVariableUniteSnot.class).getNodeable())
                        .getVariable().getCode();
    }

    /**
     *
     * @return
     */
    public  boolean hasError() {
        return realNode == null;
    }

    /**
     *
     * @return
     */
    public List<String> getValues() {
        return values;
    }

    /**
     *
     * @return
     */
    public String getCode() {
        return code;
    }

    /**
     *
     * @return
     */
    public RealNode getRealNode() {
        return realNode;
    }

    /**
     *
     * @return
     */
    public String getErrorMessage() {
        return errorMessage;
    }
}
