/**
 * OREILacs project - see LICENCE.txt for use created: 31 mars 2009 15:27:51
 */
package org.cnrs.osuc.snot.refdata;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


/**
 * @author "Antoine Schellenberger"
 * 
 */
@MappedSuperclass
public class Localisable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    /**
     *
     */
    @Column(nullable = true)
    protected Float latitude;

    /**
     *
     */
    @Column(nullable = true)
    protected Float longitude;

    /**
     *
     */
    @Column(nullable = true)
    protected Float altitude;

    /**
     *
     */
    public Localisable() {}

    /**
     *
     * @param latitude
     * @param longitude
     * @param altitude
     */
    public Localisable(Float latitude, Float longitude, Float altitude) {
        super();
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
    }

    /**
     *
     * @return
     */
    public Float getAltitude() {
        return this.altitude;
    }

    /**
     *
     * @param altitude
     */
    public void setAltitude(Float altitude) {
        this.altitude = altitude;
    }

    /**
     *
     * @return
     */
    public Long getId() {
        return this.id;
    }

    /**
     *
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public Float getLatitude() {
        return this.latitude;
    }

    /**
     *
     * @param latitude
     */
    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    /**
     *
     * @return
     */
    public Float getLongitude() {
        return this.longitude;
    }

    /**
     *
     * @param longitude
     */
    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

}
