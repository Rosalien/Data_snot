package org.cnrs.osuc.menu;

import org.inra.ecoinfo.ConcordionSpringJunit4ClassRunner;
import org.inra.ecoinfo.TransactionalTestFixtureExecutionListener;
import org.inra.ecoinfo.menu.AbstractMenuFixture;
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
@Transactional(rollbackFor = Exception.class, readOnly = false, transactionManager = "transactionManager")
@TestExecutionListeners(listeners = {TransactionalTestFixtureExecutionListener.class})
public class CheckUserMenuFixture extends
        AbstractMenuFixture {

    /**
     *
     */
    public CheckUserMenuFixture() {
        super();
    }
}
