///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package org.cnrs.osuc.snot.synthesis.jsf;
//
//import java.util.Optional;
//import org.inra.ecoinfo.mga.business.composite.IFlatNode;
//import org.inra.ecoinfo.mga.business.composite.ITreeNode;
//import org.inra.ecoinfo.synthesis.ISynthesisManager;
//import org.inra.ecoinfo.synthesis.jsf.UIBeanSynthesis;
//
///**
// *
// * @author jbparoissien
// */
//public class UIBeanSynthesisSnot extends UIBeanSynthesis {
//
//    public UIBeanSynthesisSnot() {
//        super();
//
//    }
//
//    /**
//     * Sets the current selection.
//     *
//     * @param currentSelection the new current selection
//     */
//    public void setCurrentSelection(final IFlatNode currentSelection) {
//        this.currentSelection = currentSelection;
//    }
//
//    /**
//     * Gets the current selection.
//     *
//     * @return the current selection
//     */
//    public IFlatNode getCurrentSelection() {
//        return currentSelection;
//    }
//    
//    /**
//     * Sets the synthesis manager.
//     *
//     * @param synthesisManager the new synthesis manager
//     */
//    public void setSynthesisManager(final ISynthesisManager synthesisManager) {
//        this.synthesisManager = synthesisManager;
//    }
//    
//    public void selectionChanged(ITreeNode<IFlatNode> selectedTreeNode) {
//        currentSelection = selectedTreeNode.getData();
//////        if (selectedTreeNode.getChildren().isEmpty() && policyManager.getCurrentUser() != null)
//        if (selectedTreeNode.getChildren().isEmpty()) {
//            synthesisManager.getSynthesisPath(currentSelection.getLeafNodePath())
//                    .flatMap(path -> Optional.ofNullable(path.split("@@")))
//                    .ifPresent((split) -> {
//                        currentContext = split[0];
//                        currentDatatypeName = split[1];
//                        buildCurrentItemsDatatableVariablesSynthesis(selectedTreeNode.getData().getRealNodeLeafId());
//                    });
//        }
//    }
//}
