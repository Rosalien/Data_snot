package org.cnrs.osuc.snot.identification.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;
import org.inra.ecoinfo.identification.entity.Utilisateur;

/**
 *
 * @author ptcherniati
 */
@Entity(name = RightsRequest.NAME_ENTITY)
public class RightsRequest implements Serializable {

    /**
     *
     */
    public static final String ID_JPA = "rightsrequest";

    /**
     *
     */
    public static final String NAME_ENTITY = "snot_rightsrequest";
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = RightsRequest.ID_JPA)
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    private LocalDateTime createDate = LocalDateTime.now();
    private boolean validated;

    @Column(nullable = false)
    @Size(min = 1)
    @NotEmpty
    private String nameSurname;
    @Column(nullable = false)
    @Size(min = 1)
    @NotEmpty
    private String status;
    @Column(nullable = false)
    @Size(min = 1)
    @NotEmpty
    private String institution;
    @Column(nullable = false)
    @Size(min = 1)
    @NotEmpty
    private String email;
    @Column(nullable = false)
    @Size(min = 1)
    @NotEmpty
    private String phone;
    @Column(nullable = false)
    @Size(min = 1)
    @NotEmpty
    private String relation;
    @Column(nullable = false)
    @Size(min = 1)
    @NotEmpty
    private String title;
    @Column(nullable = false)
    @Size(min = 1)
    @NotEmpty
    private String objectives;
    @Column(nullable = false)
    @Size(min = 1)
    @NotEmpty
    private String fundings;
    @Column(nullable = false)
    @NotNull
    private LocalDate deliveryDate = null;
    @Column(nullable = false)
    @Size(min = 1)
    @NotEmpty
    private String requiredData;
    @Column(nullable = true)
    @Size(min = 1)
    @NotEmpty
    private String publications;
    @Column(nullable = false)
    private String additionalsInformations;
    @Column(nullable = false)

    private String sites;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, optional = false)
    @JoinColumn(name = "usr_id", referencedColumnName = Utilisateur.UTILISATEUR_NAME_ID, nullable = false)
    private Utilisateur utilisateur;

    /**
     *
     */
    public RightsRequest() {
    }

    /**
     *
     * @return
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     *
     * @param createDate
     */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /**
     *
     * @return
     */
    public String getSites() {
        return sites;
    }

    /**
     *
     * @param sites
     */
    public void setSites(String sites) {
        this.sites = sites;
    }

    /**
     *
     * @return
     */
    public String getNameSurname() {
        return nameSurname;
    }

    /**
     *
     * @param nameSurname
     */
    public void setNameSurname(String nameSurname) {
        this.nameSurname = nameSurname;
    }

    /**
     *
     * @return
     */
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     *
     * @return
     */
    public String getInstitution() {
        return institution;
    }

    /**
     *
     * @param institution
     */
    public void setInstitution(String institution) {
        this.institution = institution;
    }

    /**
     *
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     */
    public String getPhone() {
        return phone;
    }

    /**
     *
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     *
     * @return
     */
    public String getRelation() {
        return relation;
    }

    /**
     *
     * @param relation
     */
    public void setRelation(String relation) {
        this.relation = relation;
    }

    /**
     *
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return
     */
    public String getObjectives() {
        return objectives;
    }

    /**
     *
     * @param objectives
     */
    public void setObjectives(String objectives) {
        this.objectives = objectives;
    }

    /**
     *
     * @return
     */
    public String getFundings() {
        return fundings;
    }

    /**
     *
     * @param fundings
     */
    public void setFundings(String fundings) {
        this.fundings = fundings;
    }

    /**
     *
     * @return
     */
    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    /**
     *
     * @param deliveryDate
     */
    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    /**
     *
     * @return
     */
    public String getRequiredData() {
        return requiredData;
    }

    /**
     *
     * @param requiredData
     */
    public void setRequiredData(String requiredData) {
        this.requiredData = requiredData;
    }

    /**
     *
     * @return
     */
    public String getPublications() {
        return publications;
    }

    /**
     *
     * @param publications
     */
    public void setPublications(String publications) {
        this.publications = publications;
    }

    /**
     *
     * @return
     */
    public String getAdditionalsInformations() {
        return additionalsInformations;
    }

    /**
     *
     * @param additionalsInformations
     */
    public void setAdditionalsInformations(String additionalsInformations) {
        this.additionalsInformations = additionalsInformations;
    }

    /**
     *
     * @return
     */
    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    /**
     *
     * @param utilisateur
     */
    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    /**
     *
     * @return
     */
    public Long getId() {
        return id;
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
    public boolean isValidated() {
        return validated;
    }

    /**
     *
     * @param validated
     */
    public void setValidated(boolean validated) {
        this.validated = validated;
    }

}
