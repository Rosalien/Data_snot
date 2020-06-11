/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cnrs.osuc.snot.identification.impl;

import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import org.inra.ecoinfo.config.ICoreConfiguration;
import org.cnrs.osuc.snot.identification.IUtilisateurSnotDAO;
import org.cnrs.osuc.snot.identification.entity.RightsRequest;
import org.inra.ecoinfo.identification.IRequestManager;
import org.inra.ecoinfo.identification.IRightRequestDAO;
import org.inra.ecoinfo.identification.entity.Utilisateur;
import org.inra.ecoinfo.identification.impl.AbstractRequestManager;
import org.inra.ecoinfo.localization.entity.Localization;
import org.inra.ecoinfo.mailsender.IMailSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author ptcherniati
 */
public class RequestManager extends AbstractRequestManager<RightsRequest> implements IRequestManager<RightsRequest> {

    /**
     * The Constant PROPERTY_MSG_RIGHT_DEMAND.
     */
    public static final String PROPERTY_MSG_RIGHT_DEMAND = "PROPERTY_MSG_RIGHT_DEMAND";

    /**
     *
     */
    public static final String BUNDLE_NAME = "org.inra.ecoinfo.identification.impl.messages";
    /**
     * The Constant PROPERTY_MSG_NEW_RIGHT_REQUEST_MAIL_CONTENT.
     */
    public static final String PROPERTY_MSG_NEW_RIGHT_REQUEST_MAIL_CONTENT = "PROPERTY_MSG_NEW_RIGHT_REQUEST_MAIL_CONTENT";
    /**
     * The Constant PROPERTY_MSG_NEW_RIGHT_REQUEST
     */
    public static final String PROPERTY_MSG_NEW_RIGHT_REQUEST = "PROPERTY_MSG_NEW_RIGHT_REQUEST";
     /**
     * The Constant PROPERTY_MSG_NEW_RIGHT_REQUEST
     */
    public static final String PROPERTY_MSG_NEW_RIGHT_REQUEST_BODY = "PROPERTY_MSG_NEW_RIGHT_REQUEST_BODY";
     /**
     * The Constant PROPERTY_MSG_NEW_USER_MAIL_CONTENT.
     */
    private static final String PROPERTY_MSG_NEW_USER_MAIL_CONTENT = "PROPERTY_MSG_NEW_USER_MAIL_CONTENT";

    /**
     *
     */
    protected static final Logger LOGGER = LoggerFactory.getLogger(RequestManager.class);

    private IUtilisateurSnotDAO utilisateurDAO;
    private IRightRequestDAO<RightsRequest> rightRequestDAO;
    private ICoreConfiguration configuration;
    private IMailSender mailSender;

    /**
     *
     * @param admin
     * @param utilisateur
     * @param rightRequest
     * @return
     */
    @Override
    public String getMailMessageForRequest(Utilisateur admin, Utilisateur utilisateur, RightsRequest rightRequest) {
        final ResourceBundle resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME, utilisateur.getLanguage() == null ? new Locale(Localization.getDefaultLocalisation()) : new Locale(utilisateur.getLanguage()));
        String message = resourceBundle.getString(PROPERTY_MSG_NEW_RIGHT_REQUEST_MAIL_CONTENT);
        message = String.format(message,
                                admin.getPrenom(), admin.getNom(),
                                utilisateur.getLogin(), rightRequest.toString(),
                                rightRequest.getTitle(), rightRequest.getPublications(), rightRequest.getAdditionalsInformations(),
                                utilisateur.getEmail());
        return message;

    }

    /**
     *
     * @param rightRequest
     * @param utilisateur
     */
    @Override
    protected void setRightRequestUser(RightsRequest rightRequest, Utilisateur utilisateur) {
        rightRequest.setUtilisateur(utilisateur);
    }

    /**
     *
     * @param rightRequest
     * @return
     */
    @Override
    protected Optional<Utilisateur> getUtilisateurForRequest(RightsRequest rightRequest){
        return Optional.ofNullable(rightRequest.getUtilisateur());
    }

}
