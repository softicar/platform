package com.softicar.platform.workflow.module.workflow.node.items.move;

import com.softicar.platform.common.container.comparator.OrderDirection;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.dom.elements.AbstractDomValueSelect;
import com.softicar.platform.dom.elements.DomRow;
import com.softicar.platform.dom.elements.DomTable;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.dom.event.DomEventType;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.event.IDomEventHandler;
import com.softicar.platform.emf.EmfCssClasses;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.workflow.AGWorkflow;
import com.softicar.platform.workflow.module.workflow.item.move.WorkflowItemsMover;
import com.softicar.platform.workflow.module.workflow.node.AGWorkflowNode;
import com.softicar.platform.workflow.module.workflow.version.AGWorkflowVersion;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

class WorkflowNodeMoveItemsPopup extends DomPopup {

	private final AGWorkflowNode sourceNode;
	private final WorkflowNodeSelect nodeSelect;

	public WorkflowNodeMoveItemsPopup(AGWorkflowNode sourceNode) {

		this.sourceNode = sourceNode;
		this.nodeSelect = new WorkflowNodeSelect();

		setup();
	}

	private void setup() {

		setCaption(WorkflowI18n.MOVE_WORKFLOW_ITEMS_TO_ANOTHER_NODE);
		setSubCaption(WorkflowI18n.WORKFLOW_NODE.concatColon().concatSpace().concat(sourceNode.getName()));

		WorkflowVersionSelect versionSelect = new WorkflowVersionSelect(sourceNode.getWorkflow());

		appendText(WorkflowI18n.PLEASE_SELECT_THE_TARGET_WORKFLOW_NODE);

		DomTable table = appendChild(new DomTable());
		table.setCssClass(EmfCssClasses.EMF_FORM);

		DomRow firstRow = table.appendChild(new DomRow());
		firstRow.appendCell().appendText(WorkflowI18n.WORKFLOW_VERSION);
		firstRow.appendCell().appendChild(versionSelect);

		DomRow secondRow = table.appendChild(new DomRow());
		secondRow.appendCell().appendText(WorkflowI18n.WORKFLOW_NODE);
		secondRow.appendCell().appendChild(nodeSelect);

		if (!versionSelect.getValueList().isEmpty()) {
			appendActionNode(
				new DomButton()//
					.setLabel(WorkflowI18n.OK)
					.setIcon(CoreImages.DIALOG_OKAY.getResource())
					.setClickCallback(this::moveWorkflowItems));
		}
		appendCancelButton();
	}

	private void moveWorkflowItems() {

		AGWorkflowNode targetNode = nodeSelect.getSelectedValue();
		if (targetNode != null) {
			new WorkflowItemsMover(sourceNode).moveItemsToNode(targetNode);
			close();
		}
	}

	private void refreshNodeSelect(AGWorkflowVersion selectedVersion) {

		nodeSelect.removeValues();

		List<AGWorkflowNode> workflowNodes = selectedVersion//
			.getAllActiveWorkflowNodes()
			.stream()
			.filter(node -> !node.equals(sourceNode))
			.sorted(Comparator.comparingInt(AGWorkflowNode::getId).reversed())
			.collect(Collectors.toList());
		nodeSelect.addValuesSortedByDisplayString(workflowNodes);

		if (!workflowNodes.isEmpty()) {
			nodeSelect.setSelectedValue(workflowNodes.get(0));
		}
	}

	private class WorkflowVersionSelect extends AbstractDomValueSelect<AGWorkflowVersion> implements IDomEventHandler {

		public WorkflowVersionSelect(AGWorkflow workflow) {

			addValuesSortedByDisplayString(workflow.getWorkflowVersions(), OrderDirection.DESCENDING);
			AGWorkflowVersion preselectedVersion = determinePreselectedVersion(workflow);

			if (preselectedVersion != null) {
				refreshNodeSelect(preselectedVersion);
				listenToEvent(DomEventType.CHANGE);
			}
		}

		private AGWorkflowVersion determinePreselectedVersion(AGWorkflow workflow) {

			AGWorkflowVersion preselectedVersion = workflow.getCurrentVersion();
			if (preselectedVersion == null) {
				preselectedVersion = fetchHighestVersionOrNull(workflow.getWorkflowVersions());
			}
			return preselectedVersion;
		}

		private AGWorkflowVersion fetchHighestVersionOrNull(Collection<AGWorkflowVersion> versions) {

			return versions.stream().max(Comparator.comparing(AGWorkflowVersion::getId)).orElse(null);
		}

		@Override
		protected Integer getValueId(AGWorkflowVersion version) {

			return version.getId();
		}

		@Override
		protected IDisplayString getValueDisplayString(AGWorkflowVersion version) {

			return version.toDisplay();
		}

		@Override
		public void handleDOMEvent(IDomEvent event) {

			refreshNodeSelect(getSelectedValue());
		}
	}

	private class WorkflowNodeSelect extends AbstractDomValueSelect<AGWorkflowNode> {

		@Override
		protected Integer getValueId(AGWorkflowNode node) {

			return node.getId();
		}

		@Override
		protected IDisplayString getValueDisplayString(AGWorkflowNode node) {

			return node.toDisplay();
		}
	}
}
