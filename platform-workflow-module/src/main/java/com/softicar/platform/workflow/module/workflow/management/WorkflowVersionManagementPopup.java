package com.softicar.platform.workflow.module.workflow.management;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.dom.refresh.bus.IDomRefreshBusEvent;
import com.softicar.platform.dom.refresh.bus.IDomRefreshBusListener;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.workflow.version.AGWorkflowVersion;

public class WorkflowVersionManagementPopup extends DomPopup implements IDomRefreshBusListener {

	private final AGWorkflowVersion workflowVersion;

	public WorkflowVersionManagementPopup(AGWorkflowVersion workflowVersion) {

		this.workflowVersion = workflowVersion;
		setCaption(WorkflowI18n.MANAGE_WORKFLOW);

		if (workflowVersion.isDraft()) {
			setSubCaptionWithVersionType(WorkflowI18n.DRAFT_VERSION);
		} else if (workflowVersion.isCurrentVersion()) {
			setSubCaptionWithVersionType(WorkflowI18n.CURRENT_VERSION);
		} else {
			setSubCaptionWithVersionType(WorkflowI18n.OBSOLETE_VERSION);
		}

		appendChild(new WorkflowVersionManagementDiv(workflowVersion));
	}

	private void setSubCaptionWithVersionType(IDisplayString versionType) {

		setSubCaption(
			workflowVersion//
				.toDisplay()
				.concatColon()
				.concatSpace()
				.concat(versionType));
	}

	@Override
	public void refresh(IDomRefreshBusEvent event) {

		removeChildren();
		appendChild(new WorkflowVersionManagementDiv(workflowVersion));
	}
}
