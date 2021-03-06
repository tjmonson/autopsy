/*
 * Autopsy Forensic Browser
 *
 * Copyright 2011-2016 Basis Technology Corp.
 * Contact: carrier <at> sleuthkit <dot> org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sleuthkit.autopsy.casemodule.services;

import java.util.Set;
import java.util.TreeSet;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import org.netbeans.spi.options.OptionsPanelController;
import org.openide.util.NbBundle;
import org.sleuthkit.autopsy.corecomponents.OptionsPanel;
import org.sleuthkit.datamodel.TagName;

/**
 * A panel to allow the user to create and delete custom tag types.
 */
final class TagOptionsPanel extends javax.swing.JPanel implements OptionsPanel {

    private static final long serialVersionUID = 1L;
    private static final String DEFAULT_DESCRIPTION = "";
    private static final TagName.HTML_COLOR DEFAULT_COLOR = TagName.HTML_COLOR.NONE;
    private final DefaultListModel<TagNameDefiniton> tagTypesListModel;
    private Set<TagNameDefiniton> tagTypes;

    /**
     * Creates new form TagsManagerOptionsPanel
     */
    TagOptionsPanel() {
        tagTypesListModel = new DefaultListModel<>();
        tagTypes = new TreeSet<>(TagNameDefiniton.getTagNameDefinitions());
        initComponents();
        customizeComponents();
    }

    private void customizeComponents() {
        tagNamesList.setModel(tagTypesListModel);
        tagNamesList.addListSelectionListener((ListSelectionEvent event) -> {
            enableButtons();
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        panelDescriptionLabel = new javax.swing.JLabel();
        jSplitPane1 = new javax.swing.JSplitPane();
        modifyTagTypesListPanel = new javax.swing.JPanel();
        tagTypesListLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tagNamesList = new javax.swing.JList<>();
        newTagNameButton = new javax.swing.JButton();
        deleteTagNameButton = new javax.swing.JButton();
        tagTypesAdditionalPanel = new javax.swing.JPanel();

        jPanel1.setPreferredSize(new java.awt.Dimension(750, 500));

        org.openide.awt.Mnemonics.setLocalizedText(panelDescriptionLabel, org.openide.util.NbBundle.getMessage(TagOptionsPanel.class, "TagOptionsPanel.panelDescriptionLabel.text")); // NOI18N

        jSplitPane1.setDividerLocation(400);
        jSplitPane1.setDividerSize(1);

        org.openide.awt.Mnemonics.setLocalizedText(tagTypesListLabel, org.openide.util.NbBundle.getMessage(TagOptionsPanel.class, "TagOptionsPanel.tagTypesListLabel.text")); // NOI18N

        tagNamesList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tagNamesList);

        newTagNameButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/sleuthkit/autopsy/images/add-tag.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(newTagNameButton, org.openide.util.NbBundle.getMessage(TagOptionsPanel.class, "TagOptionsPanel.newTagNameButton.text")); // NOI18N
        newTagNameButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newTagNameButtonActionPerformed(evt);
            }
        });

        deleteTagNameButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/sleuthkit/autopsy/images/delete-tag.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(deleteTagNameButton, org.openide.util.NbBundle.getMessage(TagOptionsPanel.class, "TagOptionsPanel.deleteTagNameButton.text")); // NOI18N
        deleteTagNameButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteTagNameButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout modifyTagTypesListPanelLayout = new javax.swing.GroupLayout(modifyTagTypesListPanel);
        modifyTagTypesListPanel.setLayout(modifyTagTypesListPanelLayout);
        modifyTagTypesListPanelLayout.setHorizontalGroup(
            modifyTagTypesListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(modifyTagTypesListPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(modifyTagTypesListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tagTypesListLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(modifyTagTypesListPanelLayout.createSequentialGroup()
                        .addComponent(newTagNameButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteTagNameButton)
                        .addGap(0, 113, Short.MAX_VALUE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        modifyTagTypesListPanelLayout.setVerticalGroup(
            modifyTagTypesListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(modifyTagTypesListPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tagTypesListLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 383, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(modifyTagTypesListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(newTagNameButton)
                    .addComponent(deleteTagNameButton))
                .addContainerGap())
        );

        jSplitPane1.setLeftComponent(modifyTagTypesListPanel);

        javax.swing.GroupLayout tagTypesAdditionalPanelLayout = new javax.swing.GroupLayout(tagTypesAdditionalPanel);
        tagTypesAdditionalPanel.setLayout(tagTypesAdditionalPanelLayout);
        tagTypesAdditionalPanelLayout.setHorizontalGroup(
            tagTypesAdditionalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 356, Short.MAX_VALUE)
        );
        tagTypesAdditionalPanelLayout.setVerticalGroup(
            tagTypesAdditionalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 456, Short.MAX_VALUE)
        );

        jSplitPane1.setRightComponent(tagTypesAdditionalPanel);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSplitPane1)
                    .addComponent(panelDescriptionLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelDescriptionLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSplitPane1)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 778, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void newTagNameButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newTagNameButtonActionPerformed
        NewTagNameDialog dialog = new NewTagNameDialog();
        NewTagNameDialog.BUTTON_PRESSED result = dialog.getResult();
        if (result == NewTagNameDialog.BUTTON_PRESSED.OK) {
            String newTagDisplayName = dialog.getTagName();
            TagNameDefiniton newTagType = new TagNameDefiniton(newTagDisplayName, DEFAULT_DESCRIPTION, DEFAULT_COLOR);
            /*
             * If tag name already exists, don't add the tag name.
             */
            if (!tagTypes.contains(newTagType)) {
                tagTypes.add(newTagType);
                updateTagNamesListModel();
                tagNamesList.setSelectedValue(newTagType, true);
                enableButtons();
                firePropertyChange(OptionsPanelController.PROP_CHANGED, null, null);
            } else {
                JOptionPane.showMessageDialog(null,
                        NbBundle.getMessage(TagOptionsPanel.class, "TagNamesSettingsPanel.JOptionPane.tagNameAlreadyExists.message"),
                        NbBundle.getMessage(TagOptionsPanel.class, "TagNamesSettingsPanel.JOptionPane.tagNameAlreadyExists.title"),
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_newTagNameButtonActionPerformed

    private void deleteTagNameButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteTagNameButtonActionPerformed
        TagNameDefiniton tagName = tagNamesList.getSelectedValue();
        tagTypes.remove(tagName);
        updateTagNamesListModel();
        enableButtons();
        firePropertyChange(OptionsPanelController.PROP_CHANGED, null, null);
    }//GEN-LAST:event_deleteTagNameButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton deleteTagNameButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JPanel modifyTagTypesListPanel;
    private javax.swing.JButton newTagNameButton;
    private javax.swing.JLabel panelDescriptionLabel;
    private javax.swing.JList<org.sleuthkit.autopsy.casemodule.services.TagNameDefiniton> tagNamesList;
    private javax.swing.JPanel tagTypesAdditionalPanel;
    private javax.swing.JLabel tagTypesListLabel;
    // End of variables declaration//GEN-END:variables

    /**
     * Updates the tag names model for the tag names list component.
     */
    private void updateTagNamesListModel() {
        tagTypesListModel.clear();
        for (TagNameDefiniton tagName : tagTypes) {
            tagTypesListModel.addElement(tagName);
        }
    }

    /**
     * Loads the stored custom tag types.
     */
    @Override
    public void load() {
        tagTypes = new TreeSet<>(TagNameDefiniton.getTagNameDefinitions());
        updateTagNamesListModel();
        enableButtons();
    }

    /**
     * Stores the custom tag types.
     */
    @Override
    public void store() {
        TagNameDefiniton.setTagNameDefinitions(tagTypes);
    }

    /**
     * Enables the button components based on the state of the tag types list
     * component.
     */
    private void enableButtons() {
        /*
         * Only enable the delete button when there is a tag type selected in
         * the tag types JList.
         */
        deleteTagNameButton.setEnabled(tagNamesList.getSelectedIndex() != -1);
    }

}
