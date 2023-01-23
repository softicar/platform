package com.softicar.platform.workflow.module.demo;

import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.emf.action.EmfActionSet;
import com.softicar.platform.emf.attribute.IEmfAttributeList;
import com.softicar.platform.emf.form.section.EmfFormSectionConfiguration;
import com.softicar.platform.emf.form.tab.factory.EmfFormTabConfiguration;
import com.softicar.platform.emf.log.EmfChangeLoggerSet;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.permission.statik.EmfStaticPermissionBuilder;
import com.softicar.platform.emf.permission.statik.IEmfStaticPermission;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;
import com.softicar.platform.workflow.module.AGWorkflowModuleInstance;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.demo.approver.AGWorkflowDemoObjectApprover;
import com.softicar.platform.workflow.module.workflow.item.history.section.WorkflowItemHistorySectionDiv;
import com.softicar.platform.workflow.module.workflow.item.message.ShowWorkflowItemMessageAction;
import com.softicar.platform.workflow.module.workflow.transients.field.display.WorkflowNodeFieldDisplay;
import com.softicar.platform.workflow.module.workflow.transition.WorkflowTransitionActionFactory;

public class AGWorkflowDemoObjectTable extends EmfObjectTable<AGWorkflowDemoObject, AGWorkflowModuleInstance> {

	public final static IEmfStaticPermission<AGWorkflowDemoObject> APPROVER =//
			new EmfStaticPermissionBuilder<AGWorkflowDemoObject>((object, user) -> object.isApprover(user))//
				.setTitle(WorkflowI18n.APPROVER)
				.setUuid("e20b819f-1c0c-43af-a433-68d15556ac80")
				.build();

	public AGWorkflowDemoObjectTable(IDbObjectTableBuilder<AGWorkflowDemoObject> builder) {

		super(builder);
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<AGWorkflowDemoObject, Integer, AGWorkflowModuleInstance> configuration) {

		configuration.setScopeField(AGWorkflowDemoObject.MODULE_INSTANCE);
	}

	@Override
	public void customizeAttributeProperties(IEmfAttributeList<AGWorkflowDemoObject> attributes) {

		attributes//
			.editAttribute(AGWorkflowDemoObject.WORKFLOW_ITEM)
			.setConcealed(true);
		attributes//
			.addTransientAttribute(AGWorkflowDemoObject.WORKFLOW_STATUS)
			.setDisplayFactory(WorkflowNodeFieldDisplay::new);
	}

	@Override
	public void customizeActionSet(EmfActionSet<AGWorkflowDemoObject, AGWorkflowModuleInstance> actionSet) {

		actionSet.addPrimaryActionFactory(new WorkflowTransitionActionFactory<>(WorkflowDemoObjectTableReferencePoint.class));
		actionSet.addManagementAction(new ShowWorkflowItemMessageAction<>());
		actionSet.addManagementAction(new WorkflowDemoObjectStartWorkflowAction());
	}

	@Override
	public void customizeFormTabs(EmfFormTabConfiguration<AGWorkflowDemoObject> tabConfiguration) {

		tabConfiguration.addManagementTab(AGWorkflowDemoObjectApprover.TABLE);
	}

	@Override
	public void customizeFormSections(EmfFormSectionConfiguration<AGWorkflowDemoObject> sectionConfiguration) {

		sectionConfiguration.addSection(WorkflowItemHistorySectionDiv::new);
	}

	@Override
	public void customizeLoggers(EmfChangeLoggerSet<AGWorkflowDemoObject> loggerSet) {

		loggerSet
			.addPlainChangeLogger(AGWorkflowDemoObjectLog.WORKFLOW_DEMO_OBJECT, AGWorkflowDemoObjectLog.TRANSACTION)//
			.addMapping(AGWorkflowDemoObject.NAME, AGWorkflowDemoObjectLog.NAME)
			.addMapping(AGWorkflowDemoObject.ACTIVE, AGWorkflowDemoObjectLog.ACTIVE)
			.addMapping(AGWorkflowDemoObject.WORKFLOW_ITEM, AGWorkflowDemoObjectLog.WORKFLOW_ITEM);
	}
}
