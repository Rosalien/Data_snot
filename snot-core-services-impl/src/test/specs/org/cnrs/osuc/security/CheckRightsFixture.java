package org.cnrs.osuc.security;

import org.inra.ecoinfo.ConcordionSpringJunit4ClassRunner;
import org.inra.ecoinfo.TransactionalTestFixtureExecutionListener;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author tcherniatinsky
 */
@RunWith(ConcordionSpringJunit4ClassRunner.class)
@ContextConfiguration(locations = {"/META-INF/spring/applicationContextTest.xml"})
@Transactional(rollbackFor = Exception.class, readOnly = false, transactionManager = "transactionManager")
@TestExecutionListeners(listeners = {TransactionalTestFixtureExecutionListener.class})
public class CheckRightsFixture extends org.inra.ecoinfo.security.CheckRightsFixture {

    /**
     *
     */
    public CheckRightsFixture() {
        super();
    }
}
/**
 *
 * @author ptcherniati
 */
