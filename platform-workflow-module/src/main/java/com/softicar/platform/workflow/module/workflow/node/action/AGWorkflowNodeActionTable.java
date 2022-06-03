package com.softicar.platform.workflow.module.workflow.node.action;

import com.softicar.platform.core.module.uuid.AGUuidBasedSourceCodeReferencePoints;
import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.emf.attribute.IEmfAttributeList;
import com.softicar.platform.emf.authorizer.EmfAuthorizer;
import com.softicar.platform.emf.form.tab.factory.EmfFormTabConfiguration;
import com.softicar.platform.emf.log.EmfChangeLoggerSet;
import com.softicar.platform.emf.mapper.IEmfTableRowMapper;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.predicate.EmfPredicates;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.WorkflowPermissions;
import com.softicar.platform.workflow.module.workflow.WorkflowPredicates;
import com.softicar.platform.workflow.module.workflow.node.AGWorkflowNode;
import com.softicar.platform.workflow.module.workflow.node.action.permission.AGWorkflowNodeActionPermission;

public class AGWorkflowNodeActionTable extends EmfObjectTable<AGWorkflowNodeAction, AGWorkflowNode> {

	public AGWorkflowNodeActionTable(IDbObjectTableBuilder<AGWorkflowNodeAction> builder) {

		super(builder);
	}

	@Override
	public void customizeAuthorizer(EmfAuthorizer<AGWorkflowNodeAction, AGWorkflowNode> authorizer) {

		authorizer//
			.setCreationPermission(
				WorkflowPermissions.ADMINISTRATOR
					.of(IEmfTableRowMapper.nonOptional(WorkflowI18n.WORKFLOW_MODULE_INSTANCE, it -> it.getWorkflowVersion().getWorkflow().getModuleInstance())))
			.setViewPermission(
				WorkflowPermissions.VIEWER
					.of(
						IEmfTableRowMapper
							.nonOptional(
								WorkflowI18n.WORKFLOW_MODULE_INSTANCE,
								it -> it.getWorkflowNode().getWorkflowVersion().getWorkflow().getModuleInstance())))
			.setEditPermission(
				WorkflowPermissions.ADMINISTRATOR
					.of(
						IEmfTableRowMapper
							.nonOptional(
								WorkflowI18n.WORKFLOW_MODULE_INSTANCE,
								it -> it.getWorkflowNode().getWorkflowVersion().getWorkflow().getModuleInstance())));
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<AGWorkflowNodeAction, Integer, AGWorkflowNode> configuration) {

		configuration.setScopeField(AGWorkflowNodeAction.WORKFLOW_NODE);
		configuration
			.setCreationPredicate(
				WorkflowPredicates.WORKFLOW_VERSION_FINALIZED
					.not()
					.of(IEmfTableRowMapper.nonOptional(WorkflowI18n.WORKFLOW_VERSION, AGWorkflowNode::getWorkflowVersion)));
		configuration
			.setEditPredicate(
				WorkflowPredicates.WORKFLOW_VERSION_FINALIZED
					.not()
					.of(IEmfTableRowMapper.nonOptional(WorkflowI18n.WORKFLOW_VERSION, it -> it.getWorkflowNode().getWorkflowVersion())));

		configuration.addValidator(WorkflowNodeActionValidator::new);
	}

	@Override
	public void customizeAttributeProperties(IEmfAttributeList<AGWorkflowNodeAction> attributes) {

		attributes//
			.editIndirectEntityAttribute(AGWorkflowNodeAction.ACTION)
			.setEntityLoader(() -> AGUuidBasedSourceCodeReferencePoints.getAll(IWorkflowAction.class))
			.setTitle(WorkflowI18n.WORKFLOW_NODE_ACTION)
			.setPredicateMandatory(EmfPredicates.always());
	}

	@Override
	public void customizeLoggers(EmfChangeLoggerSet<AGWorkflowNodeAction> loggerSet) {

		loggerSet
			.addPlainChangeLogger(AGWorkflowNodeActionLog.WORKFLOW_NODE_ACTION, AGWorkflowNodeActionLog.TRANSACTION)//
			.addMapping(AGWorkflowNodeAction.ACTIVE, AGWorkflowNodeActionLog.ACTIVE)
			.addMapping(AGWorkflowNodeAction.ACTION, AGWorkflowNodeActionLog.ACTION);
	}

	@Override
	public void customizeFormTabs(EmfFormTabConfiguration<AGWorkflowNodeAction> tabConfiguration) {

		tabConfiguration.addManagementTab(AGWorkflowNodeActionPermission.TABLE, WorkflowI18n.PERMISSIONS);
	}
}
