package com.softicar.platform.workflow.module.workflow.node;

import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.emf.attribute.IEmfAttributeList;
import com.softicar.platform.emf.authorization.IEmfTableRowMapper;
import com.softicar.platform.emf.authorizer.EmfAuthorizer;
import com.softicar.platform.emf.form.tab.factory.EmfFormTabConfiguration;
import com.softicar.platform.emf.log.EmfChangeLoggerSet;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.predicate.EmfPredicates;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.WorkflowImages;
import com.softicar.platform.workflow.module.WorkflowRoles;
import com.softicar.platform.workflow.module.workflow.WorkflowPredicates;
import com.softicar.platform.workflow.module.workflow.node.action.AGWorkflowNodeAction;
import com.softicar.platform.workflow.module.workflow.node.precondition.AGWorkflowNodePrecondition;
import com.softicar.platform.workflow.module.workflow.version.AGWorkflowVersion;

public class AGWorkflowNodeTable extends EmfObjectTable<AGWorkflowNode, AGWorkflowVersion> {

	public AGWorkflowNodeTable(IDbObjectTableBuilder<AGWorkflowNode> builder) {

		super(builder);
	}

	@Override
	public void customizeAuthorizer(EmfAuthorizer<AGWorkflowNode, AGWorkflowVersion> authorizer) {

		authorizer//
			.setCreationRole(
				WorkflowRoles.ADMINISTRATOR
					.of(IEmfTableRowMapper.nonOptional(WorkflowI18n.WORKFLOW_MODULE_INSTANCE, it -> it.getWorkflow().getModuleInstance())))
			.setViewRole(
				WorkflowRoles.VIEWER
					.of(IEmfTableRowMapper.nonOptional(WorkflowI18n.WORKFLOW_MODULE_INSTANCE, it -> it.getWorkflowVersion().getWorkflow().getModuleInstance())))
			.setEditRole(
				WorkflowRoles.ADMINISTRATOR
					.of(
						IEmfTableRowMapper
							.nonOptional(WorkflowI18n.WORKFLOW_MODULE_INSTANCE, it -> it.getWorkflowVersion().getWorkflow().getModuleInstance())));
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<AGWorkflowNode, Integer, AGWorkflowVersion> configuration) {

		configuration.setScopeField(AGWorkflowNode.WORKFLOW_VERSION);
		configuration.setCreationPredicate(WorkflowPredicates.WORKFLOW_VERSION_FINALIZED.not());
		configuration.setEditPredicate(WorkflowPredicates.WORKFLOW_VERSION_FINALIZED.of(AGWorkflowNode.WORKFLOW_VERSION).not());
		configuration.addValidator(WorkflowNodeValidator::new);
		configuration.setIcon(WorkflowImages.WORKFLOW_NODE);
	}

	@Override
	public void customizeAttributeProperties(IEmfAttributeList<AGWorkflowNode> attributes) {

		attributes//
			.editAttribute(AGWorkflowNode.NAME)
			.setPredicateMandatory(EmfPredicates.always());
		attributes//
			.editAttribute(AGWorkflowNode.X_COORDINATE)
			.setPredicateMandatory(EmfPredicates.always());
		attributes//
			.editAttribute(AGWorkflowNode.Y_COORDINATE)
			.setPredicateMandatory(EmfPredicates.always());
		attributes//
			.editAttribute(AGWorkflowNode.ACTIVE)
			.setPredicateEditable(WorkflowPredicates.WORKFLOW_VERSION_FINALIZED.of(AGWorkflowNode.WORKFLOW_VERSION).not());
	}

	@Override
	public void customizeFormTabs(EmfFormTabConfiguration<AGWorkflowNode> tabConfiguration) {

		tabConfiguration.addManagementTab(AGWorkflowNodePrecondition.TABLE, WorkflowI18n.PRECONDITIONS);
		tabConfiguration.addManagementTab(AGWorkflowNodeAction.TABLE, WorkflowI18n.ACTIONS);
	}

	@Override
	public void customizeLoggers(EmfChangeLoggerSet<AGWorkflowNode> loggerSet) {

		loggerSet
			.addPlainChangeLogger(AGWorkflowNodeLog.WORKFLOW_NODE, AGWorkflowNodeLog.TRANSACTION)//
			.addMapping(AGWorkflowNode.NAME, AGWorkflowNodeLog.NAME)
			.addMapping(AGWorkflowNode.X_COORDINATE, AGWorkflowNodeLog.X_COORDINATE)
			.addMapping(AGWorkflowNode.Y_COORDINATE, AGWorkflowNodeLog.Y_COORDINATE)
			.addMapping(AGWorkflowNode.ACTIVE, AGWorkflowNodeLog.ACTIVE);
	}
}
