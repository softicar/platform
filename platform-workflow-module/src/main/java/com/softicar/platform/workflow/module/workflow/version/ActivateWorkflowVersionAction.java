package com.softicar.platform.workflow.module.workflow.version;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.db.core.transaction.DbTransaction;
import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.elements.DomElementsImages;
import com.softicar.platform.emf.action.IEmfManagementAction;
import com.softicar.platform.emf.authorization.IEmfTableRowMapper;
import com.softicar.platform.emf.authorization.role.IEmfRole;
import com.softicar.platform.emf.predicate.IEmfPredicate;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.WorkflowRoles;
import com.softicar.platform.workflow.module.workflow.AGWorkflow;
import com.softicar.platform.workflow.module.workflow.WorkflowPredicates;
import com.softicar.platform.workflow.module.workflow.management.WorkflowVersionManagementPopup;

public class ActivateWorkflowVersionAction implements IEmfManagementAction<AGWorkflowVersion> {

	@Override
	public IEmfPredicate<AGWorkflowVersion> getPrecondition() {

		return WorkflowPredicates.WORKFLOW_VERSION_DRAFT.and(WorkflowPredicates.IS_CONSISTENT);
	}

	@Override
	public IEmfRole<AGWorkflowVersion> getAuthorizedRole() {

		return WorkflowRoles.ADMINISTRATOR
			.of(IEmfTableRowMapper.nonOptional(WorkflowI18n.WORKFLOW_MODULE_INSTANCE, it -> it.getWorkflow().getModuleInstance()));
	}

	@Override
	public IResource getIcon() {

		return DomElementsImages.DIALOG_OKAY.getResource();
	}

	@Override
	public IDisplayString getTitle() {

		return WorkflowI18n.ACTIVATE;
	}

	@Override
	public void handleClick(AGWorkflowVersion tableRow) {

		try (DbTransaction transaction = new DbTransaction()) {
			tableRow//
				.setDraft(false)
				.save();
			tableRow//
				.getWorkflow()
				.setCurrentVersion(tableRow)
				.save();

			transaction.commit();
		}

		CurrentDomDocument//
			.get()
			.getRefreshBus()
			.setAllChanged(AGWorkflow.class)
			.setAllChanged(WorkflowVersionManagementPopup.class);
	}
}
