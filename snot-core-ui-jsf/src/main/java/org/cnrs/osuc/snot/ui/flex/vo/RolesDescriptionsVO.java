/**
 * OREILacs project - see LICENCE.txt for use created: 26 avr. 2010 15:01:10
 */
package org.cnrs.osuc.snot.ui.flex.vo;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;


/**
 * @author "Antoine Schellenberger"
 * 
 */
public class RolesDescriptionsVO implements Serializable {

    private static final long serialVersionUID = 1L;
    private String pluginId;
    private List<String> roles = new LinkedList<>();

    /**
     *
     * @param pluginId
     */
    public RolesDescriptionsVO(String pluginId) {
        super();
        this.pluginId = pluginId;
    }

    /**
     *
     * @return
     */
    public String getPluginId() {
        return this.pluginId;
    }

    /**
     *
     * @param pluginId
     */
    public void setPluginId(String pluginId) {
        this.pluginId = pluginId;
    }

    /**
     *
     * @return
     */
    public List<String> getRoles() {
        return this.roles;
    }

    /**
     *
     * @param roles
     */
    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
