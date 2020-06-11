/**
 *
 */
package org.cnrs.osuc.snot.refdata.periodeapplicationmethode;

import com.Ostermiller.util.CSVParser;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.cnrs.osuc.snot.refdata.RefDataConstantes;
import org.cnrs.osuc.snot.refdata.datatypevariableunite.DatatypeVariableUniteSnot;
import org.cnrs.osuc.snot.refdata.datatypevariableunite.SRDV;
import org.cnrs.osuc.snot.refdata.methodecalcul.IMethodeCalculDAO;
import org.cnrs.osuc.snot.refdata.methodecalcul.MethodeCalcul;
import org.cnrs.osuc.snot.refdata.variable.VariableSnot;
import org.cnrs.osuc.snot.utils.Constantes;
import org.inra.ecoinfo.mga.business.composite.RealNode;
import org.inra.ecoinfo.mga.enums.WhichTree;
import org.inra.ecoinfo.refdata.AbstractCSVMetadataRecorder;
import org.inra.ecoinfo.refdata.ColumnModelGridMetadata;
import org.inra.ecoinfo.refdata.LineModelGridMetadata;
import org.inra.ecoinfo.refdata.ModelGridMetadata;
import org.inra.ecoinfo.utils.DateUtil;
import org.inra.ecoinfo.utils.exceptions.BusinessException;
import org.inra.ecoinfo.utils.exceptions.PersistenceException;
import org.cnrs.osuc.snot.refdata.datatypevariableunite.IDatatypeVariableUniteSnotDAO;
import org.cnrs.osuc.snot.refdata.jeu.IJeuDAO;
import org.cnrs.osuc.snot.refdata.jeu.Jeu;

/**
 * @author sophie
 *
 */
public class Recorder extends AbstractCSVMetadataRecorder<PeriodeApplicationMethode> {

    private static final Logger LOGGER = Logger.getLogger(Recorder.class);

    /**
     *
     */
    protected static final String BUNDLE_SOURCE_PATH = "org.cnrs.osuc.snot.refdata.messages";

    /**
     *
     */
    protected static final String MCALC_NONDEFINI = "MCALC_NONDEFINI";

    /**
     *
     */
    protected static final String PERIODE_METHOD_NOT_FOUND = "PERIODE_METHOD_NOT_FOUND";
    private static final String DATEFORMAT_ERROR = "DATEFORMAT_ERROR";
    IMethodeCalculDAO methodeCalculDAO;
    IPeriodeApplicationMethodeDAO periodeApplicationMethodeDAO;
    IDatatypeVariableUniteSnotDAO variableUniteSnotDAO;
    IJeuDAO jeuDAO;

    private String[] stdtVarPossibles;
    private String[] calcPossibles;
    private String[] jeuPossibles;

    /**
     * @return the jeuDAO
     */
    public IJeuDAO getJeuDAO() {
        return this.jeuDAO;
    }

    /**
     * @param jeuDAO the jeuDAO to set
     */
    public void setJeuDAO(IJeuDAO jeuDAO) {
        this.jeuDAO = jeuDAO;
    }

    /**
     *
     * @param parser
     * @param file
     * @param encoding
     * @throws BusinessException
     */
    @Override
    public void deleteRecord(CSVParser parser, File file, String encoding) throws BusinessException {
        try {
            String[] values = null;
            int lineNumber = 0;
            while ((values = parser.getLine()) != null) {
                TokenizerValues tokenizerValues = new TokenizerValues(values);
                String stdtVarDisplay = tokenizerValues.nextToken();
                String codeJeu = tokenizerValues.nextToken();
                String codeMCalc = tokenizerValues.nextToken();
                String dateDebut = tokenizerValues.nextToken();
                String dateFin = tokenizerValues.nextToken();
                SRDV srdv = new SRDV(stdtVarDisplay, localizationManager, policyManager.getMgaServiceBuilder().getRecorder(), variableUniteSnotDAO);
                if (srdv.getRealNode() == null) {
                    throw new BusinessException(srdv.getErrorMessage());
                }
                Optional<MethodeCalcul> methodeCalcul = this.methodeCalculDAO.getByCode(codeMCalc);
                Optional<Jeu> jeu = this.jeuDAO.getByCode(codeJeu);

                LocalDate debutPeriode = DateUtil.readLocalDateTimeFromText(DateUtil.DD_MM_YYYY, dateDebut).toLocalDate();
                LocalDate finPeriode;
                if (StringUtils.isEmpty(dateFin)) {
                    finPeriode = null;
                } else {
                    try {
                        finPeriode = DateUtil.readLocalDateTimeFromText(DateUtil.DD_MM_YYYY, dateFin).toLocalDate();
                    } catch (DateTimeParseException e) {
                        throw new BusinessException(String.format(DATEFORMAT_ERROR, dateFin, lineNumber));
                    }
                }
                Optional<PeriodeApplicationMethode> periodeApplicationMethode = this.periodeApplicationMethodeDAO.getByStdtVarMCalcAndDates(srdv.getRealNode(), jeu.get(), methodeCalcul.orElseThrow(() -> new BusinessException(getMessageMethode(codeMCalc))), debutPeriode);
                this.periodeApplicationMethodeDAO.remove(periodeApplicationMethode.orElseThrow(() -> new BusinessException(getMessagePeriode(stdtVarDisplay, codeJeu, codeMCalc, dateDebut))));
            }
        } catch (IOException | PersistenceException | DateTimeParseException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see org.inra.ecoinfo.refdata.IMetadataRecorder#getNewLineModelGridMetadata(java.lang.Object)
     */
    /**
     *
     * @param periodeApplicationMethode
     * @return
     * @throws BusinessException
     */
    @Override
    public LineModelGridMetadata getNewLineModelGridMetadata(PeriodeApplicationMethode periodeApplicationMethode) throws BusinessException {
        LineModelGridMetadata lineModelGridMetadata = new LineModelGridMetadata();
        String dateDebut = "";
//        String dateFin = "";
        String dateFin = this.dateFin(periodeApplicationMethode);
        SRDV srdv = null;
        if (periodeApplicationMethode != null) {
            RealNode rn = periodeApplicationMethode.getRealNode();
            srdv = new SRDV(rn, localizationManager, policyManager.getMgaServiceBuilder().getRecorder(), variableUniteSnotDAO);

            dateDebut = DateUtil.getUTCDateTextFromLocalDateTime(periodeApplicationMethode.getDateDebut(), DateUtil.DD_MM_YYYY);
//            dateFin = DateUtil.getUTCDateTextFromLocalDateTime(periodeApplicationMethode.getDateFin(), DateUtil.DD_MM_YYYY);
        }
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(periodeApplicationMethode == null ? Constantes.CST_STRING_EMPTY : srdv.getCode(), stdtVarPossibles, null, true, false, true));
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(periodeApplicationMethode == null ? Constantes.CST_STRING_EMPTY : periodeApplicationMethode.getJeu().getCodeJeu(), jeuPossibles, RefDataConstantes.COLUMN_CODE_JEU, true, false, true));
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(periodeApplicationMethode == null ? Constantes.CST_STRING_EMPTY : periodeApplicationMethode.getMethodeCalcul().getCode(), calcPossibles, null, true, false, true));
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(periodeApplicationMethode == null ? Constantes.CST_STRING_EMPTY : dateDebut, ColumnModelGridMetadata.NULL_MAP_POSSIBLES, null, true, false, true));
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(periodeApplicationMethode == null ? Constantes.CST_STRING_EMPTY : dateFin, ColumnModelGridMetadata.NULL_MAP_POSSIBLES, null, false, false, false));
        return lineModelGridMetadata;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.inra.ecoinfo.refdata.impl.AbstractCSVMetadataRecorder#processRecord(com.Ostermiller.util.CSVParser, java.io.File, java.lang.String)
     */
    /**
     *
     * @param parser
     * @param file
     * @param encoding
     * @throws BusinessException
     */
    @Override
    public void processRecord(CSVParser parser, File file, String encoding) throws BusinessException {
        ErrorsReport errorsReport = new ErrorsReport();
        try {
            this.skipHeader(parser);
            String[] values = null;
            int lineNumber = 0;
            while ((values = parser.getLine()) != null) {

                TokenizerValues tokenizerValues = new TokenizerValues(values, PeriodeApplicationMethode.TABLE_NAME);
                String stdtVarDisplay = tokenizerValues.nextToken();
                SRDV srdv = new SRDV(stdtVarDisplay, localizationManager, policyManager.getMgaServiceBuilder().getRecorder(), variableUniteSnotDAO);
                if (srdv.getRealNode() == null) {
                    errorsReport.addErrorMessage(srdv.getErrorMessage());
                    continue;
                }
                String codeJeu = tokenizerValues.nextToken();
                String codeMCalc = tokenizerValues.nextToken();
                String dateDebut = tokenizerValues.nextToken();
                String dateFin = tokenizerValues.nextToken();
                RealNode realNode = srdv.getRealNode();
                Optional<MethodeCalcul> methodeCalcul = this.methodeCalculDAO.getByCode(codeMCalc);
                Optional<Jeu> jeu = this.jeuDAO.getByCode(codeJeu);
                if (!methodeCalcul.isPresent()) {
                    errorsReport.addErrorMessage(getMessageMethode(codeMCalc));
                    continue;
                }
                LocalDate debutPeriode = DateUtil.readLocalDateTimeFromText(DateUtil.DD_MM_YYYY, dateDebut).toLocalDate();
                LocalDate finPeriode;
                if (StringUtils.isEmpty(dateFin)) {
                    finPeriode = null;
                } else {
                    try {
                        finPeriode = DateUtil.readLocalDateTimeFromText(DateUtil.DD_MM_YYYY, dateFin).toLocalDate();
                    } catch (DateTimeParseException e) {
                        throw new BusinessException(String.format(org.cnrs.osuc.snot.refdata.periodeapplicationmethode.Recorder.DATEFORMAT_ERROR, dateFin, lineNumber));
                    }
                }

                PeriodeApplicationMethode dbPeriodeApplicationMethode = this.periodeApplicationMethodeDAO.getByStdtVarMCalcAndDates(realNode, jeu.get(), methodeCalcul.get(), debutPeriode)
                        .orElseGet(() -> new PeriodeApplicationMethode(methodeCalcul.get(), jeu.get(), realNode, debutPeriode, finPeriode));

                if (!errorsReport.hasErrors()) {
                    this._createOrUpdate(methodeCalcul.get(), jeu.get(), realNode, debutPeriode, finPeriode, dbPeriodeApplicationMethode);
                }

//                if (dbPeriodeApplicationMethode.getId() == null) {
//                    this.periodeApplicationMethodeDAO.saveOrUpdate(dbPeriodeApplicationMethode);
//                    this.periodeApplicationMethodeDAO.flush();
//                }
            }
        } catch (IOException | PersistenceException e1) {
            throw new BusinessException(e1.getMessage(), e1);
        } catch (DateTimeParseException e) {
            LOGGER.debug(e.getMessage());
            errorsReport.addErrorMessage(String.format(this.localizationManager.getMessage(Recorder.BUNDLE_SOURCE_PATH, DATEFORMAT_ERROR)));
            throw new BusinessException(errorsReport.getErrorsMessages());
        }
    }

    /**
     *
     *
     **
     * @param codeMCalc
     * @param codeJeu
     * @param realNode
     * @param debutPeriode
     * @param finPeriode
     * @param dbPeriodeApplicationMethode
     * @throws PersistenceException
     */
    private void _createOrUpdate(MethodeCalcul codeMCalc, Jeu codeJeu, RealNode realNode, LocalDate debutPeriode, LocalDate finPeriode, PeriodeApplicationMethode dbPeriodeApplicationMethode) throws PersistenceException {
        if (dbPeriodeApplicationMethode.getId() == null) {
            this.periodeApplicationMethodeDAO.saveOrUpdate(dbPeriodeApplicationMethode);
        } else {
            dbPeriodeApplicationMethode.setMethodeCalcul(codeMCalc);
            dbPeriodeApplicationMethode.setJeu(codeJeu);
            dbPeriodeApplicationMethode.setRealNode(realNode);
            dbPeriodeApplicationMethode.setDateDebut(debutPeriode);
            dbPeriodeApplicationMethode.setDateFin(finPeriode);
            this.periodeApplicationMethodeDAO.saveOrUpdate(dbPeriodeApplicationMethode);
        }
    }

    /**
     * @param methodeCalculDAO the methodeCalculDAO to set
     */
    public void setMethodeCalculDAO(IMethodeCalculDAO methodeCalculDAO) {
        this.methodeCalculDAO = methodeCalculDAO;
    }

    /**
     * @param periodeApplicationMethodeDAO the periodeApplicationMethodeDAO to
     * set
     */
    public void setPeriodeApplicationMethodeDAO(IPeriodeApplicationMethodeDAO periodeApplicationMethodeDAO) {
        this.periodeApplicationMethodeDAO = periodeApplicationMethodeDAO;
    }

    /**
     * @return @throws PersistenceException
     */
    private String[] _getMCalcPossibles() {
        List<MethodeCalcul> lstMethodeCalculs = this.methodeCalculDAO.getAll(MethodeCalcul.class);
        String[] codeMCalcPossibles = new String[lstMethodeCalculs.size()];
        int index = 0;
        for (MethodeCalcul mCalc : lstMethodeCalculs) {
            codeMCalcPossibles[index++] = mCalc.getCode();
        }
        return codeMCalcPossibles;
    }

    /**
     * @return @throws PersistenceException
     */
    private String[] _getJeuPossibles() {
        List<Jeu> lstJeus = this.jeuDAO.getAll(Jeu.class);
        String[] codeJeuPossibles = new String[lstJeus.size()];
        int index = 0;
        for (Jeu stat : lstJeus) {
            codeJeuPossibles[index++] = stat.getCodeJeu();
        }
        return codeJeuPossibles;
    }

    /**
     * @return @throws PersistenceException
     */
    private String[] _getStdtVarPossibles() {
        return policyManager.getMgaServiceBuilder().getRecorder().getNodesByTypeResource(WhichTree.TREEDATASET, DatatypeVariableUniteSnot.class)
                .map(n -> n.getRealNode())
                .map(rn -> new SRDV(rn, localizationManager, policyManager.getMgaServiceBuilder().getRecorder(), variableUniteSnotDAO))
                .map(srdv -> srdv.getCode())
                .collect(Collectors.toSet())
                .toArray(new String[0]);
    }

    /*
     * (non-Javadoc)
     *
    * @see org.inra.ecoinfo.refdata.impl.AbstractCSVMetadataRecorder#getAllElements()
     */
    /**
     *
     * @return @throws BusinessException
     */
    @Override
    protected List<PeriodeApplicationMethode> getAllElements() throws BusinessException {
        return this.periodeApplicationMethodeDAO.getAll(PeriodeApplicationMethode.class);
    }

    /**
     *
     * @return
     */
    @Override
    protected ModelGridMetadata<PeriodeApplicationMethode> initModelGridMetadata() {
        this.stdtVarPossibles = this._getStdtVarPossibles();
        this.jeuPossibles = this._getJeuPossibles();
        this.calcPossibles = this._getMCalcPossibles();
        return super.initModelGridMetadata();
    }

    private String getMessageMethode(String code) {
        return String.format(localizationManager.getMessage(BUNDLE_SOURCE_PATH, MCALC_NONDEFINI), code);
    }

    private String getMessagePeriode(String stdtVarDisplay, String code_jeu, String codeMCalc, String dateDebut) {
        return String.format(localizationManager.getMessage(BUNDLE_SOURCE_PATH, PERIODE_METHOD_NOT_FOUND), stdtVarDisplay, codeMCalc, dateDebut);
    }

    private String dateFin(PeriodeApplicationMethode periodeApplicationMethode) {
        return Optional.ofNullable(periodeApplicationMethode)
                .map(s -> s.getDateFin())
                .map(d -> DateUtil.getUTCDateTextFromLocalDateTime(d, DateUtil.DD_MM_YYYY))
                .orElseGet(String::new);
    }

    /**
     *
     * @param variableUniteSnotDAO
     */
    public void setDatatypeVariableUniteSnotDAO(IDatatypeVariableUniteSnotDAO variableUniteSnotDAO) {
        this.variableUniteSnotDAO = variableUniteSnotDAO;
    }

}
