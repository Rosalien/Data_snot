package org.cnrs.osuc.snot;

import org.concordion.api.ExpectedToPass;
import org.inra.ecoinfo.AbstractTestFixture;
import org.inra.ecoinfo.ConcordionSpringJunit4ClassRunner;
import org.inra.ecoinfo.TransactionalTestFixtureExecutionListener;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ptcherniati
 */
@RunWith(ConcordionSpringJunit4ClassRunner.class)
@ContextConfiguration(locations = {"/META-INF/spring/applicationContextTest.xml"})
@Transactional(rollbackFor = Exception.class)
@TestExecutionListeners(listeners = {SnotTransactionalTestFixtureExecutionListener.class})
@ExpectedToPass
public class SnotCoreTestFixture extends AbstractTestFixture {

    /**
     *
     */
    public SnotCoreTestFixture() {
        super();
        TransactionalTestFixtureExecutionListener.startSession();
    }
}
