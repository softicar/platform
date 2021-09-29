package com.softicar.platform.workflow.module.workflow.version;

import com.softicar.platform.db.runtime.table.listener.IDbTableRowNotificationSet;
import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.document.IDomDocument;
import com.softicar.platform.emf.table.listener.IEmfCommitHook;
import com.softicar.platform.workflow.module.workflow.AGWorkflow;
import com.softicar.platform.workflow.module.workflow.management.WorkflowVersionManagementPopup;
import com.softicar.platform.workflow.module.workflow.node.AGWorkflowNode;
import java.util.Optional;

public class WorkflowVersionCommitHook implements IEmfCommitHook<AGWorkflowVersion> {

	@Override
	public void beforeCommit(IDbTableRowNotificationSet<AGWorkflowVersion> notificationSet) {

		// ignored
	}

	@Override
	public void afterCommit(IDbTableRowNotificationSet<AGWorkflowVersion> notificationSet) {

		Optional//
			.ofNullable(CurrentDomDocument.get())
			.map(IDomDocument::getRefreshBus)
			.ifPresent(bus -> {
				bus.setAllChanged(AGWorkflow.class);
				bus.setAllChanged(AGWorkflowNode.class);
				bus.setAllChanged(WorkflowVersionManagementPopup.class);
			});
	}
}
