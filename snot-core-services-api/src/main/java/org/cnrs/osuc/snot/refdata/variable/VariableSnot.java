package org.cnrs.osuc.snot.refdata.variable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.inra.ecoinfo.refdata.variable.Variable;

/**
 *
 * @author ptcherniati
 */
@Entity
@Table(name = VariableSnot.NAME_ENTITY_JPA)
public class VariableSnot extends Variable {

    /**
     *
     */
    public static final String NAME_ENTITY_JPA = "variable_snot";

    /**
     *
     */
    public static final String ID_JPA = "id";
    private static final long serialVersionUID = 1L;

    @Column(nullable = true, unique = false, name = "theiacategories", length = 150)
    private String theiacategories;

    /**
     *
     */
    public VariableSnot() {
        super();
    }

    /**

     * @param code
     * @param definition
     * @param theiacategories
     */
    public VariableSnot(String code, String definition, String theiacategories) {
        super();
        this.setCode(code);
        this.setDefinition(definition);
        this.setTheiacategories(theiacategories);
    }

    /**
     *
     * @param id
     * @param code
     * @param definition
     * @param theiacategories
     */
    public VariableSnot(long id, String code, String definition, String theiacategories) {
        this(code, definition, theiacategories);
        setId(id);
    }

    public String getTheiacategories() {
        return theiacategories;
    }

    public void setTheiacategories(String theiacategories) {
        this.theiacategories = theiacategories;
    }

}
