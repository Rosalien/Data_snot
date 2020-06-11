/*
 *
 */
package org.cnrs.osuc.snot.dataset.impl;

import com.Ostermiller.util.CSVParser;
import org.inra.ecoinfo.dataset.versioning.entity.VersionFile;
import org.cnrs.osuc.snot.dataset.IRequestProperties;
import org.cnrs.osuc.snot.dataset.IRequestPropertiesSnot;
import org.cnrs.osuc.snot.dataset.ITestFormat;
import org.cnrs.osuc.snot.dataset.ITestHeaders;
import org.cnrs.osuc.snot.dataset.ITestValues;
import org.inra.ecoinfo.utils.DatasetDescriptor;
import org.inra.ecoinfo.utils.exceptions.BadFormatException;
import org.inra.ecoinfo.utils.exceptions.BadsFormatsReport;
import org.inra.ecoinfo.utils.exceptions.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class AbstractGenericTestFormat.
 * <p>
 * an abstract implementation of {@link ITestFormat}
 *
 * @see org.inra.ecoinfo.Snot.dataset.ITestFormat
 */
public class GenericTestFormat implements ITestFormat {

    static final Logger LOGGER = LoggerFactory.getLogger(GenericTestFormat.class);

    /**
     * The Constant serialVersionUID @link(long).
     */
    static final long serialVersionUID = 1L;

    /**
     * The test headers @link(ITestHeaders).
     */
    ITestHeaders testHeaders;

    /**
     * The test values @link(ITestValues).
     */
    ITestValues testValues;

    /**
     * The datatype name @link(String).
     */
    String datatypeName;

    /**
     * Instantiates a new abstract generic test format.
     */
    public GenericTestFormat() {
        super();
    }

    /**
     * Sets the test headers.
     *
     * @param testHeaders the new test headers
     * @see
     * org.inra.ecoinfo.Snot.dataset.ITestFormat#setTestHeaders(org.inra.ecoinfo.Snot.dataset.ITestHeaders)
     */
    @Override
    public final void setTestHeaders(final ITestHeaders testHeaders) {
        this.testHeaders = testHeaders;
    }

    /**
     * Sets the test values.
     *
     * @param testValues the new test values
     * @see
     * org.inra.ecoinfo.Snot.dataset.ITestFormat#setTestValues(org.inra.ecoinfo.Snot.dataset.ITestValues)
     */
    @Override
    public final void setTestValues(final ITestValues testValues) {
        this.testValues = testValues;
    }

    /**
     * Test format.
     * <p>
     * call (ITestheader)testHeaders.testHeaders then call (ITestValues)
     * testValues.testValues </p>
     *
     * @param parser
     * @link(CSVParser) the parser
     * @param versionFile
     * @link(VersionFile) the version file
     * @param requestProperties
     * @link(IRequestPropertiesSnot) the session properties
     * @param encoding
     * @link(String) the encoding
     * @param datasetDescriptor
     * @link(DatasetDescriptor) the dataset descriptor
     * @throws BadFormatException the bad format exception
     * @link(CSVParser) the parser
     * @link(VersionFile) the version file
     * @link(IRequestPropertiesSnot) the session properties
     * @link(String) the encoding
     * @link(DatasetDescriptor) the dataset descriptor
     * @see org.inra.ecoinfo.Snot.dataset.ITestFormat#testFormat(com.Ostermiller.util.CSVParser, org.inra.ecoinfo.dataset.versioning.entity.VersionFile, org.inra.ecoinfo.Snot.dataset.IRequestPropertiesSnot, java.lang.String,
     *      impl.DatasetDescriptor)
     */
    @Override
    public void testFormat(final CSVParser parser, final VersionFile versionFile, final IRequestProperties requestProperties, final String encoding, final DatasetDescriptor datasetDescriptor) throws BadFormatException {
        final BadsFormatsReport badsFormatsReport = new BadsFormatsReport(SNOTRecorder.getSnotMessage(SNOTRecorder.PROPERTY_MSG_ERROR_BAD_FORMAT));
        LOGGER.debug(SNOTRecorder.getSnotMessage(SNOTRecorder.PROPERTY_MSG_CHECKING_FORMAT_FILE));
        long lineNumber = -1;
        try {
            lineNumber = this.testHeaders.testHeaders(parser, versionFile, requestProperties, encoding, badsFormatsReport, datasetDescriptor);
            if (badsFormatsReport.hasErrors()) {
                LOGGER.debug(badsFormatsReport.getMessages());
                throw new BadFormatException(badsFormatsReport);
            }
            this.testValues.testValues(lineNumber, parser, versionFile, requestProperties, encoding, badsFormatsReport, datasetDescriptor, this.getDatatypeName());
        } catch (final BusinessException e) {
            badsFormatsReport.addException(e);
            throw new BadFormatException(badsFormatsReport);
        }
        if (badsFormatsReport.hasErrors()) {
            LOGGER.debug(badsFormatsReport.getMessages());
            throw new BadFormatException(badsFormatsReport);
        }
    }

    /**
     * Gets the datatype name.
     *
     * @return {@link String} the datatype name
     */
    protected String getDatatypeName() {
        return this.datatypeName;
    }

    /**
     * @see
     * org.inra.ecoinfo.Snot.dataset.ITestFormat#setDatatypeName(java.lang.String)
     */
    @Override
    public final void setDatatypeName(final String datatypeName) {
        this.datatypeName = datatypeName;
    }
}
