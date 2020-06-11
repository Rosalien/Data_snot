package org.cnrs.osuc.refdata;

import org.inra.ecoinfo.ConcordionSpringJunit4ClassRunner;
import org.inra.ecoinfo.TransactionalTestFixtureExecutionListener;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
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
public class LoadRefdataFastFixture extends LoadRefdataFixture {

    /**
     *
     */
    public LoadRefdataFastFixture() {
        super();
        MockitoAnnotations.initMocks(this);
    }
}
