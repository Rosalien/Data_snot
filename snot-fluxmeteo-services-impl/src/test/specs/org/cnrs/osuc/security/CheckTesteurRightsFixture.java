package org.cnrs.osuc.security;

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
@TestExecutionListeners(listeners = {TransactionalTestFixtureExecutionListener.class})

public class CheckTesteurRightsFixture extends org.inra.ecoinfo.security.CheckRightsFixture {

    /**
     *
     */
    public CheckTesteurRightsFixture() {
        super();
    }
}
