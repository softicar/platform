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

		setCaption(WorkflowI18n.MOVE_WORKFLOW_ITEMS_TO_ANOTHER_WORKFLOW_NODE);
		setSubCaption(WorkflowI18n.ARG1_ITEM_S_OF_SOURCE_WORKFLOW_NODE_ARG2.toDisplay(sourceNode.getAllWorkflowItems().size(), sourceNode.toDisplay()));

		WorkflowVersionSelect versionSelect = new WorkflowVersionSelect(sourceNode.getWorkflow());

		appendChild(buildTargetWorkflowNodeSelectionTable(versionSelect));

		if (!versionSelect.getValueList().isEmpty()) {
			appendActionNode(
				new DomButton()//
					.setLabel(WorkflowI18n.OK)
					.setIcon(CoreImages.DIALOG_OKAY.getResource())
					.setClickCallback(this::moveWorkflowItems));
		}
		appendCancelButton();
	}

	private DomTable buildTargetWorkflowNodeSelectionTable(WorkflowVersionSelect versionSelect) {

		DomTable table = new DomTable();
		table.setCssClass(EmfCssClasses.EMF_FORM);
		table.appendChild(buildTargetWorkflowVersionRow(versionSelect));
		table.appendChild(buildTargetWorkflowNodeRow());
		return table;
	}

	private DomRow buildTargetWorkflowVersionRow(WorkflowVersionSelect versionSelect) {

		DomRow row = new DomRow();
		row.appendCell().appendText(WorkflowI18n.TARGET_WORKFLOW_VERSION.concatColon());
		row.appendCell().appendChild(versionSelect);
		return row;
	}

	private DomRow buildTargetWorkflowNodeRow() {

		DomRow row = new DomRow();
		row.appendCell().appendText(WorkflowI18n.TARGET_WORKFLOW_NODE.concatColon());
		row.appendCell().appendChild(nodeSelect);
		return row;
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
				setSelectedValue(preselectedVersion);
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

			return version.toDisplay().concatSpace().concatInParentheses(version.getVersionType());
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
