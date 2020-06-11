package org.cnrs.osuc.snot.ui.flex.vo;

import java.util.Objects;
import org.cnrs.osuc.snot.refdata.site.SiteSnot;

/**
 *
 * @author ptcherniati
 */
public class SiteSnotVO {

    /**
     *
     */
    public static final String TYPE ="site";
    private SiteSnot site;
    private Boolean   selected;

    /**
     *
     * @param siteSnot
     */
    public SiteSnotVO(SiteSnot siteSnot) {
        this.site = siteSnot;
        this.selected = Boolean.FALSE;
    }

    /**
     *
     * @return
     */
    public Boolean getSelected() {
        return this.selected;
    }

    /**
     *
     * @param selected
     */
    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    /**
     *
     * @return
     */
    public SiteSnot getSite() {
        return this.site;
    }

    /**
     *
     * @param site
     */
    public void setSite(SiteSnot site) {
        this.site = site;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return this.getSite().getId().intValue();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SiteSnotVO other = (SiteSnotVO) obj;
        return Objects.equals(this.site.getId(), other.site.getId());
    }

    /**
     *
     * @return
     */
    public String getType(){
        return TYPE;
    }
}
