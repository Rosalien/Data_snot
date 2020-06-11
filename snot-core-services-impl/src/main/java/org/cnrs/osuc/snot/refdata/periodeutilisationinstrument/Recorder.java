/**
 *
 */
package org.cnrs.osuc.snot.refdata.periodeutilisationinstrument;

import com.Ostermiller.util.CSVParser;
import com.google.common.base.Strings;
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
import org.cnrs.osuc.snot.refdata.instrument.IInstrumentDAO;
import org.cnrs.osuc.snot.refdata.instrument.Instrument;
import org.cnrs.osuc.snot.refdata.variable.VariableSnot;
import org.cnrs.osuc.snot.utils.Constantes;
import org.inra.ecoinfo.mga.business.composite.RealNode;
import org.inra.ecoinfo.mga.enums.WhichTree;
import org.inra.ecoinfo.refdata.AbstractCSVMetadataRecorder;
import org.inra.ecoinfo.refdata.ColumnModelGridMetadata;
import org.inra.ecoinfo.refdata.LineModelGridMetadata;
import org.inra.ecoinfo.utils.DateUtil;
import org.inra.ecoinfo.utils.exceptions.BusinessException;
import org.inra.ecoinfo.utils.exceptions.PersistenceException;
import org.cnrs.osuc.snot.refdata.datatypevariableunite.IDatatypeVariableUniteSnotDAO;
import org.cnrs.osuc.snot.refdata.jeu.IJeuDAO;
import org.cnrs.osuc.snot.refdata.jeu.Jeu;
import org.inra.ecoinfo.refdata.ModelGridMetadata;

/**
 * @author sophie
 *
 */
public class Recorder extends AbstractCSVMetadataRecorder<PeriodeUtilisationInstrument> {

    private static final Logger LOGGER = Logger.getLogger(Recorder.class);

    /**
     *
     */
    protected static final String BUNDLE_SOURCE_PATH = "org.cnrs.osuc.snot.refdata.messages";

    /**
     *
     */
    protected static final String INSTR_NONDEFINI = "INSTR_NONDEFINI";

    /**
     *
     */
    protected static final String PERIODE_INSTRUMENT_NOT_FOUND = "PERIODE_INSTRUMENT_NOT_FOUND";
    private static final String DATEFORMAT_ERROR = "DATEFORMAT_ERROR";
    IInstrumentDAO instrumentDAO;
    IJeuDAO jeuDAO;
    protected IPeriodeUtilisationInstrumentDAO periodeUtilisationInstrumentDAO;
    IDatatypeVariableUniteSnotDAO variableUniteSnotDAO;

    private String[] instrPossibles;
    private String[] stdtVarPossibles;
    private String[] jeuPossibles;

    /*
     * (non-Javadoc)
     * 
     * @see org.inra.ecoinfo.refdata.impl.AbstractCSVMetadataRecorder#deleteRecord(com.Ostermiller.util.CSVParser, java.io.File, java.lang.String)
     */
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
                String codeInstr = tokenizerValues.nextToken();
                String dateDebut = tokenizerValues.nextToken();
                String dateFin = tokenizerValues.nextToken();
                SRDV srdv = new SRDV(stdtVarDisplay, localizationManager, policyManager.getMgaServiceBuilder().getRecorder(), variableUniteSnotDAO);
                if (srdv.getRealNode() == null) {
                    throw new BusinessException(srdv.getErrorMessage());
                }
                Optional<Instrument> instrument = this.instrumentDAO.getByCode(codeInstr);
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
                Optional<PeriodeUtilisationInstrument> periodeUtilisationInstrument = this.periodeUtilisationInstrumentDAO.getByStdtVarInstrAndDates(srdv.getRealNode(), jeu.get(), instrument.orElseThrow(() -> new BusinessException(getMessageInstrument(codeInstr))), debutPeriode);
                this.periodeUtilisationInstrumentDAO.remove(periodeUtilisationInstrument.orElseThrow(() -> new BusinessException(getMessagePeriode(stdtVarDisplay, codeJeu, codeInstr, dateDebut))));
            }
        } catch (IOException | PersistenceException | DateTimeParseException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    /**
     * @return the instrumentDAO
     */
    public IInstrumentDAO getInstrumentDAO() {
        return this.instrumentDAO;
    }

    /**
     * @param instrumentDAO the instrumentDAO to set
     */
    public void setInstrumentDAO(IInstrumentDAO instrumentDAO) {
        this.instrumentDAO = instrumentDAO;
    }

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
     * @param periodeUtilisationInstrument
     * @return
     * @throws BusinessException
     */
    @Override
    public LineModelGridMetadata getNewLineModelGridMetadata(PeriodeUtilisationInstrument periodeUtilisationInstrument) throws BusinessException {
        LineModelGridMetadata lineModelGridMetadata = new LineModelGridMetadata();
        String dateDebut = "";
//        String dateFin = "";
        String dateFin = this.dateFin(periodeUtilisationInstrument);
        SRDV srdv = null;
        if (periodeUtilisationInstrument != null) {
            RealNode rn = periodeUtilisationInstrument.getRealNode();
            srdv = new SRDV(rn, localizationManager, policyManager.getMgaServiceBuilder().getRecorder(), variableUniteSnotDAO);

            dateDebut = DateUtil.getUTCDateTextFromLocalDateTime(periodeUtilisationInstrument.getDateDebut().atStartOfDay(), DateUtil.DD_MM_YYYY);
//            dateFin = DateUtil.getUTCDateTextFromLocalDateTime(periodeUtilisationInstrument.getDateFin().atStartOfDay(), DateUtil.DD_MM_YYYY);
        }
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(periodeUtilisationInstrument == null ? Constantes.CST_STRING_EMPTY : srdv.getCode(), stdtVarPossibles, null, true, false, true));
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(periodeUtilisationInstrument == null ? Constantes.CST_STRING_EMPTY : periodeUtilisationInstrument.getJeu().getCodeJeu(), jeuPossibles, RefDataConstantes.COLUMN_CODE_JEU, true, false, true));
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(periodeUtilisationInstrument == null ? Constantes.CST_STRING_EMPTY : periodeUtilisationInstrument.getInstrument().getCode(), instrPossibles, null, true, false, true));
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(periodeUtilisationInstrument == null ? Constantes.CST_STRING_EMPTY : dateDebut, ColumnModelGridMetadata.NULL_MAP_POSSIBLES, null, true, false, true));
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(periodeUtilisationInstrument == null ? Constantes.CST_STRING_EMPTY : dateFin, ColumnModelGridMetadata.NULL_MAP_POSSIBLES, null, false, false, false));
        return lineModelGridMetadata;
    }

    /**
     * @return the periodeUtilisationInstrumentDAO
     */
    public IPeriodeUtilisationInstrumentDAO getPeriodeUtilisationInstrumentDAO() {
        return this.periodeUtilisationInstrumentDAO;
    }

    /**
     * @param periodeUtilisationInstrumentDAO the
     * periodeUtilisationInstrumentDAO to set
     */
    public void setPeriodeUtilisationInstrumentDAO(IPeriodeUtilisationInstrumentDAO periodeUtilisationInstrumentDAO) {
        this.periodeUtilisationInstrumentDAO = periodeUtilisationInstrumentDAO;
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
                TokenizerValues tokenizerValues = new TokenizerValues(values, PeriodeUtilisationInstrument.TABLE_NAME);
                String stdtVarDisplay = tokenizerValues.nextToken();
                SRDV srdv = new SRDV(stdtVarDisplay, localizationManager, policyManager.getMgaServiceBuilder().getRecorder(), variableUniteSnotDAO);
                if (srdv.getRealNode() == null) {
                    errorsReport.addErrorMessage(srdv.getErrorMessage());
                    continue;
                }
                String codeJeu = tokenizerValues.nextToken();
                String codeInstr = tokenizerValues.nextToken();
                String dateDebut = tokenizerValues.nextToken();
                String dateFin = tokenizerValues.nextToken();
                Optional<Instrument> instrument = this.instrumentDAO.getByCode(codeInstr);
                Optional<Jeu> jeu = this.jeuDAO.getByCode(codeJeu);
                RealNode realNode = srdv.getRealNode();
                if (!instrument.isPresent()) {
                    errorsReport.addErrorMessage(getMessageInstrument(codeInstr));
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
                        throw new BusinessException(String.format(DATEFORMAT_ERROR, dateFin, lineNumber));
                    }
                }
                PeriodeUtilisationInstrument dbPeriodeUtilisationInstrument;
                dbPeriodeUtilisationInstrument = this.periodeUtilisationInstrumentDAO.getByStdtVarInstrAndDates(realNode, jeu.get(), instrument.get(), debutPeriode)
                        .orElseGet(() -> new PeriodeUtilisationInstrument(instrument.get(), jeu.get(), realNode, debutPeriode, finPeriode));
                if (!errorsReport.hasErrors()) {
                    this._createOrUpdate(instrument.get(), jeu.get(), realNode, debutPeriode, finPeriode, dbPeriodeUtilisationInstrument);
                }
//
//                if (dbPeriodeUtilisationInstrument.getId() == null) {
//                    this.periodeUtilisationInstrumentDAO.saveOrUpdate(dbPeriodeUtilisationInstrument);
//                    this.periodeUtilisationInstrumentDAO.flush();
//                }
            }
        } catch (IOException | PersistenceException e1) {
            throw new BusinessException(e1.getMessage(), e1);
        } catch (DateTimeParseException e) {
            LOGGER.debug(e.getMessage());
            errorsReport.addErrorMessage(String.format(this.localizationManager.getMessage(Recorder.BUNDLE_SOURCE_PATH, "DATEFORMAT_ERROR")));
            throw new BusinessException(errorsReport.getErrorsMessages());
        }
    }

    /**
     *
     *
     **
     * @param codeInstr
     * @param codeJeu
     * @param realNode
     * @param debutPeriode
     * @param finPeriode
     * @param dbPeriodeUtilisationInstrument
     * @throws PersistenceException
     */
    private void _createOrUpdate(Instrument codeInstr, Jeu codeJeu, RealNode realNode, LocalDate debutPeriode, LocalDate finPeriode, PeriodeUtilisationInstrument dbPeriodeUtilisationInstrument) throws PersistenceException {
        if (dbPeriodeUtilisationInstrument.getId() == null) {
            this.periodeUtilisationInstrumentDAO.saveOrUpdate(dbPeriodeUtilisationInstrument);
        } else {
            dbPeriodeUtilisationInstrument.setInstrument(codeInstr);
            dbPeriodeUtilisationInstrument.setJeu(codeJeu);
            dbPeriodeUtilisationInstrument.setRealNode(realNode);
            dbPeriodeUtilisationInstrument.setDateDebut(debutPeriode);
            dbPeriodeUtilisationInstrument.setDateFin(finPeriode);
            this.periodeUtilisationInstrumentDAO.saveOrUpdate(dbPeriodeUtilisationInstrument);
        }
    }

    /**
     * @return @throws PersistenceException
     */
    private String[] _getInstrPossibles() {
        List<Instrument> lstInstruments = this.instrumentDAO.getAll(Instrument.class);
        String[] codeInstrPossibles = new String[lstInstruments.size()];
        int index = 0;
        for (Instrument instr : lstInstruments) {
            codeInstrPossibles[index++] = instr.getCode();
        }
        return codeInstrPossibles;
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
    protected List<PeriodeUtilisationInstrument> getAllElements() throws BusinessException {
        return this.periodeUtilisationInstrumentDAO.getAll(PeriodeUtilisationInstrument.class);
    }

    private String getMessageInstrument(String code) {
        return String.format(localizationManager.getMessage(BUNDLE_SOURCE_PATH, INSTR_NONDEFINI), code);
    }

    private String getMessagePeriode(String stdtVarDisplay, String code_jeu, String codeMCalc, String dateDebut) {
        return String.format(localizationManager.getMessage(BUNDLE_SOURCE_PATH, PERIODE_INSTRUMENT_NOT_FOUND), stdtVarDisplay, code_jeu, codeMCalc, dateDebut);
    }

    /**
     *
     * @param variableUniteSnotDAO
     */
    public void setDatatypeVariableUniteSnotDAO(IDatatypeVariableUniteSnotDAO variableUniteSnotDAO) {
        this.variableUniteSnotDAO = variableUniteSnotDAO;
    }

    @Override
    protected ModelGridMetadata<PeriodeUtilisationInstrument> initModelGridMetadata() {
        this.stdtVarPossibles = this._getStdtVarPossibles();
        this.jeuPossibles = this._getJeuPossibles();
        this.instrPossibles = this._getInstrPossibles();
        return super.initModelGridMetadata(); //To change body of generated methods, choose Tools | Templates.
    }

    private String dateFin(PeriodeUtilisationInstrument periodeUtilisationInstrument) {
        return Optional.ofNullable(periodeUtilisationInstrument)
                .map(s -> s.getDateFin())
                .map(d -> DateUtil.getUTCDateTextFromLocalDateTime(d, DateUtil.DD_MM_YYYY))
                .orElseGet(String::new);
    }

}
