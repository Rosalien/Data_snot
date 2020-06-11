package org.cnrs.osuc.snot.ui.flex.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.inra.ecoinfo.refdata.variable.Variable;

/**
 *
 * @author ptcherniati
 */
public class GroupeVariableVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String type;
    private Map<Long, Variable> variables = new HashMap<>();
    private List<GroupeVariableVO> children = new LinkedList<>();
    private GroupeVariableVO parent;

    /**
     *
     */
    public GroupeVariableVO() {
        super();
    }

    /**
     *
     * @param groupeVariable
     * @param type
     */
    public GroupeVariableVO(Map<Long, Variable> groupeVariable, String type) {
        this.type = type;
        this.variables = groupeVariable;
    }

    /**
     *
     * @return
     */
    public List<GroupeVariableVO> getChildren() {
        return this.children;
    }

    /**
     *
     * @param children
     */
    public void setChildren(List<GroupeVariableVO> children) {
        this.children = children;
    }

    /**
     *
     * @return
     */
    public GroupeVariableVO getParent() {
        return this.parent;
    }

    /**
     *
     * @param parent
     */
    public void setParent(GroupeVariableVO parent) {
        this.parent = parent;
    }

    /**
     *
     * @return
     */
    public String getType() {
        return this.type;
    }

    /**
     *
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return
     */
    public Map<Long, Variable> getVariables() {
        return this.variables;
    }

    /**
     *
     * @param variables
     */
    public void setVariables(Map<Long, Variable> variables) {
        this.variables = variables;
    }

}
