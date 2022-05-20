package com.softicar.platform.workflow.module.demo;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.core.module.uuid.AGUuid;
import com.softicar.platform.db.core.transaction.DbTransaction;
import com.softicar.platform.dom.elements.dialog.DomModalAlertDialog;
import com.softicar.platform.emf.action.IEmfManagementAction;
import com.softicar.platform.emf.authorization.role.EmfRoles;
import com.softicar.platform.emf.authorization.role.IEmfRole;
import com.softicar.platform.emf.predicate.IEmfPredicate;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.WorkflowImages;
import com.softicar.platform.workflow.module.workflow.AGWorkflow;

class WorkflowDemoObjectStartWorkflowAction implements IEmfManagementAction<AGWorkflowDemoObject> {

	@Override
	public IEmfPredicate<AGWorkflowDemoObject> getPrecondition() {

		return WorkflowDemoObjectPredicates.IS_WORKFLOW_STARTED.not();
	}

	@Override
	public IEmfRole<AGWorkflowDemoObject> getAuthorizedRole() {

		return EmfRoles.anybody();
	}

	@Override
	public IResource getIcon() {

		return WorkflowImages.RIGHT.getResource();
	}

	@Override
	public IDisplayString getTitle() {

		return WorkflowI18n.START_WORKFLOW;
	}

	@Override
	public void handleClick(AGWorkflowDemoObject object) {

		try (DbTransaction transaction = new DbTransaction()) {
			object.reloadForUpdate();
			if (WorkflowDemoObjectPredicates.IS_WORKFLOW_STARTED.test(object)) {
				throw new SofticarUserException(WorkflowI18n.WORKFLOW_ALREADY_STARTED);
			} else {
				startWorkflowIfPossible(getWorkflowOrThrow(), object);
				transaction.commit();
			}
		}
	}

	private AGWorkflow getWorkflowOrThrow() {

		return AGWorkflow//
			.createSelect()
			.where(AGWorkflow.ENTITY_TABLE.equal(AGUuid.getOrCreate(WorkflowDemoObjectTableReferencePoint.class)))
			.getOneAsOptional()
			.orElseThrow(() -> new SofticarUserException(WorkflowI18n.NO_WORKFLOW_FOUND));
	}

	private void startWorkflowIfPossible(AGWorkflow workflow, AGWorkflowDemoObject object) {

		if (workflow.getCurrentVersion() != null) {
			workflow.startWorkflow(object);
			new DomModalAlertDialog(WorkflowI18n.WORKFLOW_STARTED).open();
		} else {
			throw new SofticarUserException(WorkflowI18n.NO_ACTIVE_WORKFLOW_VERSION_FOUND);
		}
	}
}
