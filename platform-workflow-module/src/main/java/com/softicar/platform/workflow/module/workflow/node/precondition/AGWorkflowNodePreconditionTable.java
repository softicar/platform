package com.softicar.platform.workflow.module.workflow.node.precondition;

import com.softicar.platform.core.module.uuid.AGUuidBasedSourceCodeReferencePoints;
import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.emf.attribute.IEmfAttributeList;
import com.softicar.platform.emf.authorizer.EmfAuthorizer;
import com.softicar.platform.emf.log.EmfChangeLoggerSet;
import com.softicar.platform.emf.mapper.IEmfTableRowMapper;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.predicate.EmfPredicates;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.WorkflowPermissions;
import com.softicar.platform.workflow.module.workflow.node.AGWorkflowNode;

public class AGWorkflowNodePreconditionTable extends EmfObjectTable<AGWorkflowNodePrecondition, AGWorkflowNode> {

	public AGWorkflowNodePreconditionTable(IDbObjectTableBuilder<AGWorkflowNodePrecondition> builder) {

		super(builder);
	}

	@Override
	public void customizeAuthorizer(EmfAuthorizer<AGWorkflowNodePrecondition, AGWorkflowNode> authorizer) {

		authorizer//
			.setCreationPermission(
				WorkflowPermissions.ADMINISTRATION
					.of(IEmfTableRowMapper.nonOptional(WorkflowI18n.WORKFLOW_MODULE_INSTANCE, it -> it.getWorkflowVersion().getWorkflow().getModuleInstance())))
			.setViewPermission(
				WorkflowPermissions.VIEW
					.of(
						IEmfTableRowMapper
							.nonOptional(
								WorkflowI18n.WORKFLOW_MODULE_INSTANCE,
								it -> it.getWorkflowNode().getWorkflowVersion().getWorkflow().getModuleInstance())))
			.setEditPermission(
				WorkflowPermissions.ADMINISTRATION
					.of(
						IEmfTableRowMapper
							.nonOptional(
								WorkflowI18n.WORKFLOW_MODULE_INSTANCE,
								it -> it.getWorkflowNode().getWorkflowVersion().getWorkflow().getModuleInstance())));
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<AGWorkflowNodePrecondition, Integer, AGWorkflowNode> configuration) {

		configuration.setScopeField(AGWorkflowNodePrecondition.WORKFLOW_NODE);
	}

	@Override
	public void customizeAttributeProperties(IEmfAttributeList<AGWorkflowNodePrecondition> attributes) {

		attributes//
			.editIndirectEntityAttribute(AGWorkflowNodePrecondition.FUNCTION)
			.setEntityLoader(() -> AGUuidBasedSourceCodeReferencePoints.getAll(IWorkflowPrecondition.class))
			.setTitle(WorkflowI18n.WORKFLOW_NODE_PRECONDITION)
			.setPredicateMandatory(EmfPredicates.always());
	}

	@Override
	public void customizeLoggers(EmfChangeLoggerSet<AGWorkflowNodePrecondition> loggerSet) {

		loggerSet
			.addPlainChangeLogger(AGWorkflowNodePreconditionLog.WORKFLOW_NODE_PRECONDITION, AGWorkflowNodePreconditionLog.TRANSACTION)//
			.addMapping(AGWorkflowNodePrecondition.ACTIVE, AGWorkflowNodePreconditionLog.ACTIVE)
			.addMapping(AGWorkflowNodePrecondition.FUNCTION, AGWorkflowNodePreconditionLog.FUNCTION);
	}
}
