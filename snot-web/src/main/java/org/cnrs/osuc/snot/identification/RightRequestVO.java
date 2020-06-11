/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cnrs.osuc.snot.identification;


import java.time.LocalDateTime;
import org.cnrs.osuc.snot.identification.entity.RightsRequest;
import org.inra.ecoinfo.identification.entity.Utilisateur;
import org.inra.ecoinfo.identification.jsf.AbstractRightRequestVO;
import org.inra.ecoinfo.identification.jsf.UtilisateurVO;

/**
 *
 * @author ptcherniati
 */
class RightRequestVO extends AbstractRightRequestVO<RightRequestVO, RightsRequest> {
    private static RightRequestVO _getInstance() {
        return new RightRequestVO();
    }

    RightsRequest _request;
    UtilisateurVO utilisateurVO;

    public RightRequestVO(RightsRequest request) {
        super();
        this._request = request;
        this.utilisateurVO = new UtilisateurVO(request.getUtilisateur());
    }

    public RightRequestVO() {
        super();
    }

    @Override
    public RightRequestVO getInstance(RightsRequest req) {
        RightRequestVO instance = _getInstance();
        instance._request = req;
        return instance;
    }


    @Override
    public RightRequestVO getInstance() {
        this.utilisateurVO = utilisateurVO;
        RightRequestVO instance = _getInstance();
        instance._request = new RightsRequest();
        return instance;
    }

    @Override
    public void setUtilisateur(Utilisateur utilisateur) {
        _request.setUtilisateur(utilisateur);
        _request.setNameSurname(String.format("%s %s", utilisateur.getNom(), utilisateur.getPrenom()));
        _request.setEmail(utilisateur.getEmail());
    }

    @Override
    public Utilisateur getUtilisateur() {
        return _request.getUtilisateur();
    }

    @Override
    public RightsRequest getRightRequest() {
        return _request;
    }

    @Override
    public LocalDateTime getCreateDate() {
        return _request.getCreateDate();
    }

    @Override
    public boolean isValidated() {
        return _request.isValidated();
    }

    @Override
    public void setValidated(boolean validated) {
        _request.setValidated(validated);
    }
}
