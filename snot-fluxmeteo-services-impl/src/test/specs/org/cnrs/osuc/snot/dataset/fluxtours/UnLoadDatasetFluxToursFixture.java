package org.cnrs.osuc.snot.dataset.fluxtours;

import org.inra.ecoinfo.ConcordionSpringJunit4ClassRunner;
import org.cnrs.osuc.snot.SnotTransactionalTestFixtureExecutionListener;
import org.cnrs.osuc.snot.dataset.UnLoadDatasetFixture;
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
@TestExecutionListeners(listeners = {SnotTransactionalTestFixtureExecutionListener.class})
public class UnLoadDatasetFluxToursFixture extends UnLoadDatasetFixture {

    /**
     *
     */
    public UnLoadDatasetFluxToursFixture() {
        super();
        MockitoAnnotations.initMocks(this);
    }
}
