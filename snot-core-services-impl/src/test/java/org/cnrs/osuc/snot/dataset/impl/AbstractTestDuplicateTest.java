/*
 * To change this license header, choose License Headers in Project Properties. To change this template file, choose Tools | Templates and open the template in the editor.
 */
package org.cnrs.osuc.snot.dataset.impl;

import org.cnrs.osuc.snot.dataset.impl.AbstractTestDuplicate;
import org.inra.ecoinfo.dataset.versioning.entity.VersionFile;
import org.cnrs.osuc.snot.dataset.ErrorsReport;
import org.inra.ecoinfo.utils.exceptions.BadsFormatsReport;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author ptcherniati
 */
public class AbstractTestDuplicateTest {

    /**
     *
     */
    @BeforeClass
    public static void setUpClass() {
    }

    /**
     *
     */
    @AfterClass
    public static void tearDownClass() {
    }

    AbstractTestDuplicate instance = new AbstractTestDuplicateImpl();

    /**
     *
     */
    public AbstractTestDuplicateTest() {
    }

    /**
     *
     */
    @Before
    public void setUp() {
        this.instance.setErrorsReport(new ErrorsReport());
    }

    /**
     *
     */
    @After
    public void tearDown() {
    }

    /**
     * Test of addErrors method, of class AbstractTestDuplicate.
     */
    @Test
    public void testAddErrors() {
        BadsFormatsReport badsFormatsReport = new BadsFormatsReport("msg");
        Assert.assertFalse(badsFormatsReport.hasErrors());
        this.instance.addErrors(badsFormatsReport);
        Assert.assertTrue(badsFormatsReport.hasErrors());
    }

    /**
     * Test of getKey method, of class AbstractTestDuplicate.
     */
    @Test
    public void testGetKey() {
        String[] args = new String[]{"1", "2", "3"};
        AbstractTestDuplicate instance = new AbstractTestDuplicateImpl();
        String expResult = "";
        String result = instance.getKey(args);
        Assert.assertEquals("1_2_3", result);
    }

    /**
     * Test of hasError method, of class AbstractTestDuplicate.
     */
    @Test
    public void testHasError() {
        BadsFormatsReport badsFormatsReport = new BadsFormatsReport("msg");
        Assert.assertFalse(badsFormatsReport.hasErrors());
        this.instance.addErrors(badsFormatsReport);
        Assert.assertFalse(this.instance.hasError());
        this.instance.errorsReport.addErrorMessage("error");
        Assert.assertTrue(this.instance.hasError());
    }

    /**
     * Test of testLineForDuplicatesDateInDB method, of class
     * AbstractTestDuplicate.
     */
    @Test
    public void testTestLineForDuplicatesDateInDB() {
        String[] dates = null;
        String dateString = "";
        String timeString = "";
        long lineNumber = 0L;
        VersionFile versionFile = null;
        AbstractTestDuplicate instance = new AbstractTestDuplicateImpl();
        instance.testLineForDuplicatesDateInDB(dates, dateString, timeString, lineNumber, versionFile);
    }

    /**
     * Test of isLimitDate method, of class AbstractTestDuplicate.
     */
    @Test
    public void testIsLimitDate() {
        String datedbString = "25/12/1965";
        String timedbString = "01:25";
        String dateString = "25/12/1965";
        String timeString = "01:25:00";
        AbstractTestDuplicate instance = new AbstractTestDuplicateImpl();
        boolean expResult = false;
        boolean result = instance.isLimitDate(datedbString, timedbString, dateString, timeString);
        Assert.assertTrue(result);
        result = instance.isLimitDate(datedbString, timedbString, dateString, "01:25:14");
        Assert.assertFalse(result);
    }

    /**
     * Test of getLocalValue method, of class AbstractTestDuplicate.
     */
    @Test
    public void testGetLocalValue() {
        String dateString = "25-12-1965";
        String timeString = "01:25";
        AbstractTestDuplicate instance = new AbstractTestDuplicateImpl();
        String expResult = "25-12-196501:25:00";
        String result = instance.getLocalValue(dateString, timeString);
        Assert.assertEquals(expResult, result);
        timeString = "01:25:12";
        expResult = "25-12-196501:25:00";
        Assert.assertEquals(expResult, result);
    }

    /**
     *
     */
    public class AbstractTestDuplicateImpl extends AbstractTestDuplicate {

        String[] values;
        long lineNumber;
        String[] dates;
        VersionFile versionFile;

        @Override
        public void addLine(String[] values, long lineNumber) {
            this.values = values;
            this.lineNumber = lineNumber;
        }
    }

}
