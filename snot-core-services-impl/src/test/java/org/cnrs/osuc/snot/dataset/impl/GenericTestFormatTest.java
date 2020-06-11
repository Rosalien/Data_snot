/*
 * To change this license header, choose License Headers in Project Properties. To change this template file, choose Tools | Templates and open the template in the editor.
 */
package org.cnrs.osuc.snot.dataset.impl;

import org.cnrs.osuc.snot.dataset.impl.GenericTestFormat;
import com.Ostermiller.util.CSVParser;
import org.cnrs.osuc.snot.dataset.IRequestProperties;
import org.cnrs.osuc.snot.dataset.ITestFormat;
import org.cnrs.osuc.snot.dataset.ITestHeaders;
import org.cnrs.osuc.snot.dataset.ITestValues;
import org.cnrs.osuc.snot.dataset.test.utils.MockUtils;
import org.inra.ecoinfo.utils.DatasetDescriptor;
import org.inra.ecoinfo.utils.exceptions.BadsFormatsReport;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;


/**
 * 
 * @author ptcherniati
 */
public class GenericTestFormatTest {

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

    MockUtils m = MockUtils.getInstance();
    @Mock
    ITestHeaders testHeaders;
    @Mock
    ITestValues testValues;
    @Mock
    CSVParser parser;
    @Mock
    IRequestProperties requestProperties;
    @Mock
    BadsFormatsReport badsFormatsReport;
    @Mock
    DatasetDescriptor datasetDescriptor;
    @Spy
    ITestFormat instance = new GenericTestFormat();

    /**
     *
     */
    public GenericTestFormatTest() {}

    /**
     *
     */
    @Before
    public void setUp() {MockitoAnnotations.initMocks(this);
        this.instance.setTestHeaders(this.testHeaders);
        this.instance.setTestValues(this.testValues);
        this.instance.setDatatypeName("name");
}

    /**
     *
     */
    @After
    public void tearDown() {}

    /**
     * Test of testFormat method, of class GenericTestFormat.
     * @throws java.lang.Exception
     */
    @Test
    public void testTestFormat() throws Exception {
        String encoding = "UTF-8";
        ArgumentCaptor<BadsFormatsReport> bf = ArgumentCaptor.forClass(BadsFormatsReport.class);
        this.instance.testFormat(this.parser, this.m.versionFile, this.requestProperties,encoding, this.datasetDescriptor);
        Mockito.verify(this.testHeaders).testHeaders(Matchers.same(this.parser), Matchers.same(this.m.versionFile), Matchers.same(this.requestProperties), Matchers.same(encoding), bf.capture(), Matchers.same(this.datasetDescriptor));
        Mockito.verify(this.testValues).testValues(Matchers.eq(0L), Matchers.same(this.parser), Matchers.same(this.m.versionFile), Matchers.same(this.requestProperties), Matchers.same(encoding), bf.capture(), Matchers.same(this.datasetDescriptor), Matchers.same("name"));
    }


}
