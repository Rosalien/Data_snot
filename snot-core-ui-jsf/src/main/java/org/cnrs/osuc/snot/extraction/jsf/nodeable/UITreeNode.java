/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cnrs.osuc.snot.extraction.jsf.nodeable;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.faces.context.FacesContext;
import org.cnrs.osuc.snot.extraction.jsf.ICollector;
import org.cnrs.osuc.snot.extraction.jsf.IStepBuilder;
import org.cnrs.osuc.snot.extraction.jsf.date.UIDate;
import org.inra.ecoinfo.localization.ILocalizationManager;
import org.inra.ecoinfo.mga.business.composite.INodeable;
import org.inra.ecoinfo.mga.business.composite.Nodeable;
import org.inra.ecoinfo.mga.business.composite.RealNode;
import org.inra.ecoinfo.mga.configuration.PatternConfigurator;
import org.inra.ecoinfo.mga.managedbean.IPolicyManager;
import org.inra.ecoinfo.utils.IntervalDate;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author ptchernia
 * @param <T>
 */
public abstract class UITreeNode<T extends Nodeable> implements IStepBuilder<List<T>> {

    /**
     * The properties names nodeable @link(Properties).
     */
    static Properties propertiesNodeableName;

    /**
     *
     */
    public static final String PARAMETER_CODE = Nodeable.class.getSimpleName();
    IAvailablesSynthesisDepositPlacesDAO availablesSynthesisDepositPlacesDAO;
    private DefaultTreeNode availablesNodes;
    private Class<T> genericClass;

    /**
     * The nodeables nodeables @link(Map<Long,TraitementVO>).
     */
    final Map<Long, NodeableVO> nodeables = new HashMap();

    /**
     * The availables agroecosystemes.
     */
    TreeNode availableNodes = new DefaultTreeNode("Root", null);
    private TreeNode selectedNode;

    /**
     *
     * @param availablesSynthesisDepositPlacesDAO
     */
    public UITreeNode(IAvailablesSynthesisDepositPlacesDAO availablesSynthesisDepositPlacesDAO) {
        this.availablesSynthesisDepositPlacesDAO = availablesSynthesisDepositPlacesDAO;
        this.genericClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     *
     * @param availablesSynthesisDepositPlacesDAO
     */
    public void setAvailablesSynthesisDepositPlacesDAO(IAvailablesSynthesisDepositPlacesDAO availablesSynthesisDepositPlacesDAO) {
        this.availablesSynthesisDepositPlacesDAO = availablesSynthesisDepositPlacesDAO;
    }

    /**
     * Gets the nodeables nodeables.
     *
     * @return the nodeables nodeables
     */
    public Map<Long, NodeableVO> getNodeables() {
        return this.nodeables;
    }

    /**
     * Adds the all nodeables.
     *
     * @return the string
     */
    public final String addAllNodeables() {
        nodeables.clear();
        for (final TreeNode node : this.availableNodes.getChildren()) {
            for (final TreeNode leafNode : getLeaves(node)) {
                NodeableVO nodeableVO = (NodeableVO) leafNode.getData();
                nodeableVO.setSelected(true);
                nodeables.put(nodeableVO.nodeable.getId(), nodeableVO);
                setExpanded(leafNode, true);
            }
        }
        return null;
    }

    private List<TreeNode> getLeaves(TreeNode node) {
        if (NodeType.LEAF.equals(node.getType())) {
            return Stream.of(node).collect(Collectors.toList());
        } else {
            return node.getChildren().stream()
                    .map(n -> getLeaves(n))
                    .flatMap(l -> l.stream())
                    .collect(Collectors.toList());
        }
    }

    /**
     * Removes the all nodeables.
     *
     * @return the string
     */
    public final String removeAllNodeables() {
        nodeables.clear();
        this.availableNodes.getChildren().forEach((node) -> {
            getLeaves(node).forEach((leafNode) -> {
                NodeableVO nodeableVO = (NodeableVO) leafNode.getData();
                nodeableVO.setSelected(false);
                setExpanded(leafNode, false);
            });
        });
        return null;
    }

    /**
     * Select nodeable.
     *
     * @param event
     */
    public final void selectNodeable(NodeSelectEvent event) {
        TreeNode nodeableLeaf = event.getTreeNode();
        if (NodeType.LEAF.equals(nodeableLeaf.getType())) {
            NodeableVO nodeableVO = (NodeableVO) nodeableLeaf.getData();
            if (nodeableVO.isSelected()) {
                nodeables.remove(nodeableVO.getNodeable().getId());
                nodeableVO.setSelected(false);
            } else {
                nodeables.put(nodeableVO.getNodeable().getId(), nodeableVO);
                nodeableVO.setSelected(true);
                setExpanded(nodeableLeaf, true);
            }
        }
    }
    
    /**
     *
     * @param id
     */
    public void unSelectNodeable(Long id){
        NodeableVO nodeable = nodeables.get(id);
        nodeable.setSelected(false);
        nodeables.remove(id);
    }

    /**
     * Gets the nodeable step is valid.
     *
     * @return the nodeable step is valid
     */
    public Boolean getNodeableStepIsValid() {
        return !nodeables.isEmpty();
    }

    /**
     *
     * @param node
     * @param expanded
     */
    public void setExpanded(TreeNode node, boolean expanded) {
        node.setExpanded(expanded);
        if (node.getParent() != null) {
            setExpanded(node.getParent(), expanded);
        }
    }

    /**
     * Gets the availables agroecosystemes.
     *
     * @return the availables agroecosystemes
     */
    public final TreeNode getAvailableNodes() {
        return this.availableNodes;
    }

    /**
     *
     * @return
     */
    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    /**
     *
     * @param selectedNode
     */
    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    /**
     *
     * @param collector
     */
    @Override
    public void addCollectToParameter(ICollector collector) {
        List<T> collect = nodeables.entrySet().stream()
                .map(n -> (T) n.getValue().nodeable)
                .collect(Collectors.toList());
        collector.addParameterCollectionEntry(PARAMETER_CODE, collect);
    }

    /**
     *
     * @param localizationManager
     * @param policyManager
     * @param collector
     */
    @Override
    public void init(ILocalizationManager localizationManager, IPolicyManager policyManager, ICollector collector) {
        Optional<IntervalDate> intervalDate = collector.getParametersCollectionEntry(UIDate.PARAMETER_CODE);
        List<RealNode> realNodes = availablesSynthesisDepositPlacesDAO.getRealNodes(policyManager.getCurrentUser(), intervalDate.orElse(null));
        this.availablesNodes = new DefaultTreeNode("Root", null);
        Map<RealNode, TreeNode> nodes = new HashMap();
        realNodes.stream()
                .forEach(rn -> buildTreeNode(rn, nodes, true));
        propertiesNodeableName = localizationManager.newProperties(
                Nodeable.getLocalisationEntite(genericClass),
                Nodeable.ENTITE_COLUMN_NAME,
                FacesContext.getCurrentInstance().getViewRoot().getLocale()
        );
    }

    private TreeNode buildTreeNode(RealNode realNode, Map<RealNode, TreeNode> nodes, boolean isLeaf) {
        if (nodes.containsKey(realNode)) {
            return nodes.get(realNode);
        }
        if (realNode == null) {
            return availableNodes;
        } else {
            final TreeNode parentNode = buildTreeNode(realNode.getParent() == null ? realNode.getAncestor() : realNode.getParent(), nodes, false);
            DefaultTreeNode treeNode = new DefaultTreeNode(isLeaf ? NodeType.LEAF : NodeType.OTHER, new NodeableVO(realNode), parentNode);
            nodes.put(realNode, treeNode);
            return treeNode;
        }
    }

    /**
     *
     * @return
     */
    @Override
    public boolean isStepValid() {
        return !nodeables.isEmpty();
    }

    /**
     * The Class NodeType.
     */
    public static class NodeType {

        /**
         * The agroecosysteme.
         */
        public static final String OTHER = "other";
        /**
         * The leaf.
         */
        public static final String LEAF = "leaf";

        private NodeType() {
        }
    }

    /**
     *
     */
    public class NodeableNode {

        /**
         * The nodeable.
         */
        INodeable nodeable;

        boolean nodeableNodeSelected;

        /**
         *
         * @param nodeable
         */
        public NodeableNode(INodeable nodeable) {
            this.nodeable = nodeable;
        }

        /**
         *
         * @return
         */
        public boolean isSelected() {
            return nodeableNodeSelected;
        }

        /**
         *
         * @param selected
         */
        public void setSelected(boolean selected) {
            this.nodeableNodeSelected = selected;
        }

        /**
         *
         * @return
         */
        public String getName() {
            return nodeable.getName();
        }
    }

    /**
     *
     */
    public class NodeableVO extends NodeableNode {

        /**
         * The nodeable.
         */
        RealNode realNode;
        INodeable nodeable;

        /**
         * Instantiates a new nodeable vo.
         *
         * @param realNode
         * @param nodeable the nodeable
         */
        public NodeableVO(final RealNode realNode) {
            super(null);
            this.realNode = realNode;
            this.nodeable = realNode.getNodeable();
        }

        /**
         * Gets the localized affichage.
         *
         * @return the localized affichage
         */
        public String getLocalizedDisplayPath() {
            return getLocalizedDisplayPath(realNode);
        }

        private String getLocalizedDisplayPath(RealNode realNode) {
            if (realNode.getAncestor() != null) {
                return getLocalizedDisplayPath(realNode.getAncestor())
                        .concat(PatternConfigurator.ANCESTOR_SEPARATOR)
                        .concat(propertiesNodeableName.getProperty(realNode.getName(), realNode.getName()));
            } else if (realNode.getParent() != null) {
                return getLocalizedDisplayPath(realNode.getParent())
                        .concat(PatternConfigurator.ANCESTOR_SEPARATOR)
                        .concat(propertiesNodeableName.getProperty(realNode.getName(), realNode.getName()));
            } else {
                return propertiesNodeableName.getProperty(realNode.getName(), realNode.getName());
            }
        }

        /**
         * Gets the localized affichage.
         *
         * @return the localized affichage
         */
        public String getLocalizedAffichage() {
            return propertiesNodeableName.getProperty(this.nodeable.getName(), this.nodeable.getName());
        }

        /**
         * Gets the nom.
         *
         * @return the nom
         */
        public String getNom() {
            return this.nodeable.getName();
        }

        /**
         * Gets the nodeable.
         *
         * @return the nodeable
         */
        public INodeable getNodeable() {
            return this.nodeable;
        }
    }

}
