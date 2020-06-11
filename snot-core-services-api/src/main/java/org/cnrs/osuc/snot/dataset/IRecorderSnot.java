package org.cnrs.osuc.snot.dataset;

/**
 * @author philippe
 * 
 */
public interface IRecorderSnot {

    /**
     * Sets the delete record.
     * 
     * @param deleteRecord
     *            the new delete record {@link IDeleteRecord} setter for an object use to delete a
     *            version of file
     */
    void setDeleteRecord(IDeleteRecord deleteRecord);

    /**
     * Sets the process record.
     * 
     * @param processRecord
     *            the new process record {@link IProcessRecord} setter for an object use to process
     *            the record of file
     */
    void setProcessRecord(IProcessRecord processRecord);

    /**
     * Sets the session properties name.
     * 
     * @param requestPropertiesName
     *            the new session properties name {@link IRequestPropertiesACBB} use by
     *            getRequestProperties() to get a new instance of IRequestProperties
     */
    void setRequestProperties(String requestPropertiesName);

    /**
     * Sets the test format.
     * 
     * @param testFormat
     *            the new test format {@link ITestFormat} setter for an object use to test ht format
     *            of file
     */
    void setTestFormat(ITestFormat testFormat);
}
