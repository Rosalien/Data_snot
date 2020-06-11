
package org.cnrs.osuc.snot.identification.impl;

import org.cnrs.osuc.snot.identification.impl.RequestManager;
import java.util.Arrays;
import java.util.List;
import org.inra.ecoinfo.config.ICoreConfiguration;
import org.cnrs.osuc.snot.dataset.test.utils.MockUtils;
import org.cnrs.osuc.snot.identification.entity.RightsRequest;
import org.inra.ecoinfo.identification.IRightRequestDAO;
import org.inra.ecoinfo.identification.entity.Utilisateur;
import org.inra.ecoinfo.mailsender.IMailSender;
import org.inra.ecoinfo.notifications.INotificationDAO;
import org.inra.ecoinfo.notifications.INotificationsManager;
import org.inra.ecoinfo.notifications.entity.Notification;
import org.inra.ecoinfo.utils.exceptions.PersistenceException;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author ptcherniati
 */
public class RequestManagerTest {

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

    private List<Utilisateur> admins;

    MockUtils m = MockUtils.getInstance();
    RequestManager instance;
    @Mock
    RightsRequest rightRequest;
    @Mock
    ICoreConfiguration configuration;
    @Mock
    IMailSender mailSender;
    @Mock
    INotificationDAO notificationDAO;
    @Mock
    INotificationsManager notificationsManager;
    @Mock
    IRightRequestDAO<RightsRequest> rightRequestDAO;
    ;
    @Mock
    Utilisateur admin;

    /**
     *
     */
    public RequestManagerTest() {
    }

    /**
     *
     * @throws PersistenceException
     */
    @Before
    public void setUp() throws PersistenceException {
        instance = new RequestManager();
        MockitoAnnotations.initMocks(this);
        instance.setConfiguration(configuration);
        instance.setLocalizationManager(m.localizationManager);
        instance.setMailSender(mailSender);
        instance.setNotificationDAO(notificationDAO);
        instance.setNotificationsManager(notificationsManager);
        instance.setRightRequestDAO(rightRequestDAO);
        instance.setPolicyManager(m.policyManager);
        instance.setUtilisateurDAO(m.utilisateurDAO);
        when(configuration.getMailAdmin()).thenReturn("mailAdmin");
        when(m.utilisateurDAO.getAdmins()).thenReturn(admins);
        admins = Arrays.asList(new Utilisateur[]{admin});
    }

    /**
     *
     */
    @After
    public void tearDown() {
    }
    /**
     * Test of addNewRequest method, of class RequestManager.
     * @throws java.lang.Exception
     */
    @Test
    @Ignore
    public void testAddNewRequest() throws Exception {
        instance = spy(instance);
        when(admin.getPrenom()).thenReturn("prenomAdmin");
        when(admin.getNom()).thenReturn("nomAdmin");
        when(admin.getEmail()).thenReturn("emailAdmin");
        when(rightRequest.getUtilisateur()).thenReturn(m.utilisateur);
        when(m.utilisateurDAO.getAdmins()).thenReturn(admins);
        when(rightRequest.toString()).thenReturn("request to string");
        when(m.utilisateurDAO.getAdmins()).thenReturn(admins);
        when(rightRequest.getTitle()).thenReturn("titre");
        when(m.utilisateurDAO.getAdmins()).thenReturn(admins);
        when(rightRequest.getPublications()).thenReturn("reférences");
        when(rightRequest.getAdditionalsInformations()).thenReturn("informations");
        when(m.utilisateur.getEmail()).thenReturn("mailUtilisateur");
        ArgumentCaptor<String> from = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> to = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> subject = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> message = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Notification> notification = ArgumentCaptor.forClass(Notification.class);
        //instance.addNewRequest(rightRequest);
        verify(rightRequest).setUtilisateur(m.utilisateur);
        verify(rightRequestDAO).saveOrUpdate(rightRequest);
        verify(mailSender).sendMail(from.capture(), to.capture(), subject.capture(), message.capture());
        verify(notificationsManager).addNotification(notification.capture(), eq("utilisateur"));
        String fromValue = from.getValue();
        assertEquals("mailAdmin", fromValue);
        String toValue = to.getValue();
        assertEquals("emailAdmin", toValue);
        String subjectValue = subject.getValue();
        assertEquals("Un utilisateur demande de nouveaux droits", subjectValue);
        String messageValue = message.getValue();
        assertEquals("Bonjour prenomAdmin nomAdmin\n"
                + "L'utilisateur utilisateur a fait une demande de droits, avec les informations suivantes :\n"
                + "request to string\n"
                + "Pour l'étude suivante :\n"
                + "titre\n"
                + "Ses références sont :\n"
                + "reférences\n"
                + "Et il a ajouté ces informations :\n"
                + "informations\n"
                + "Vous pouvez lui répondre à cette adresse pour plus de précisions : mailUtilisateur ", messageValue);
        Notification notificationValue = notification.getValue();
        assertEquals("<p>La demande a été transmise aux administrateurs et sera traitée dans les plus brefs délais.</p>", notificationValue.getBody());
        assertEquals("Vous avez demandé de nouveaux accès.", notificationValue.getMessage());
    }

}
