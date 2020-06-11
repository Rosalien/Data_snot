package org.cnrs.osuc.snot.identification;

import com.google.common.base.Strings;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.cnrs.osuc.snot.identification.entity.RightsRequest;
import org.inra.ecoinfo.identification.entity.Utilisateur;
import org.inra.ecoinfo.identification.jsf.AbstractUIBeanRequestRights;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author ptcherniati
 */
@ManagedBean(name = "uiRequestRightsSnot")
@ViewScoped
public class UIBeanRequestRights extends AbstractUIBeanRequestRights<RightRequestVO, RightsRequest> implements Serializable {

    /**
     * The Constant BUNDLE_SOURCE_PATH @link(String).
     */
    private static final String BUNDLE_SOURCE_PATH = "org.cnrs.osuc.snot.identification.messages";

    /**
     *
     */
    protected static final Logger LOGGER = LoggerFactory.getLogger(UIBeanRequestRights.class);

    RightsRequest request = new RightsRequest();
    List<UtilisateurItem> requests;
    Map<Utilisateur, UtilisateurItem> items = new HashMap();

    /**
     *
     */
    public UIBeanRequestRights() {
    }

    /**
     *
     * @return
     */
    @Override
    public String navigate() {
        return "requestRights";
    }

    /**
     *
     * @return
     */
    @Override
    public String userNavigate() {
        return "requestUsersRights";
    }

    /**
     *
     * @return
     */
    public String getRequiredData() {
        return request.getRequiredData();
    }

    /**
     *
     * @param dbRequest
     * @return
     */
    @Override
    protected RightRequestVO newRequestVO(RightsRequest dbRequest) {
        return new RightRequestVO(dbRequest);
    }

    /**
     *
     * @return
     */
    @Override
    protected RightRequestVO getRequestVOInstance() {
        return new RightRequestVO().getInstance();
    }

    /**
     *
     * @return
     */
    public String getNameSurname() {
        if(Strings.isNullOrEmpty(getRequest()._request.getNameSurname())){
            getRequest().setUtilisateur((Utilisateur) policyManager.getCurrentUser());
        }
        return getRequest()._request.getNameSurname();
    }

    /**
     *
     * @param nameSurname
     */
    public void setNameSurname(String nameSurname) {
        getRequest()._request.setNameSurname(nameSurname);
    }

    /**
     *
     * @return
     */
    public String getEmail() {
        return getRequest()._request.getEmail();
    }

    /**
     *
     * @param email
     */
    public void setEmail(String email) {
        getRequest()._request.setEmail(email);
    }
}
