package com.softicar.platform.workflow.module.workflow;

import com.softicar.platform.core.module.uuid.AGUuidBasedSourceCodeReferencePoints;
import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.emf.attribute.IEmfAttributeList;
import com.softicar.platform.emf.authorization.IEmfTableRowMapper;
import com.softicar.platform.emf.authorizer.EmfAuthorizer;
import com.softicar.platform.emf.form.tab.factory.EmfFormTabConfiguration;
import com.softicar.platform.emf.log.EmfChangeLoggerSet;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.predicate.EmfPredicates;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;
import com.softicar.platform.workflow.module.AGWorkflowModuleInstance;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.WorkflowRoles;
import com.softicar.platform.workflow.module.workflow.entity.table.IWorkflowTableReferencePoint;
import com.softicar.platform.workflow.module.workflow.version.AGWorkflowVersion;

public class AGWorkflowTable extends EmfObjectTable<AGWorkflow, AGWorkflowModuleInstance> {

	public AGWorkflowTable(IDbObjectTableBuilder<AGWorkflow> builder) {

		super(builder);
	}

	@Override
	public void customizeAuthorizer(EmfAuthorizer<AGWorkflow, AGWorkflowModuleInstance> authorizer) {

		authorizer//
			.setCreationRole(WorkflowRoles.ADMINISTRATOR)
			.setViewRole(WorkflowRoles.VIEWER.of(IEmfTableRowMapper.nonOptional(WorkflowI18n.WORKFLOW_MODULE_INSTANCE, it -> it.getModuleInstance())))
			.setEditRole(WorkflowRoles.ADMINISTRATOR.of(IEmfTableRowMapper.nonOptional(WorkflowI18n.WORKFLOW_MODULE_INSTANCE, it -> it.getModuleInstance())));
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<AGWorkflow, Integer, AGWorkflowModuleInstance> configuration) {

		configuration.setScopeField(AGWorkflow.MODULE_INSTANCE);
	}

	@Override
	public void customizeAttributeProperties(IEmfAttributeList<AGWorkflow> attributes) {

		attributes//
			.editIndirectEntityAttribute(AGWorkflow.ENTITY_TABLE)
			.setEntityLoader(() -> AGUuidBasedSourceCodeReferencePoints.getAll(IWorkflowTableReferencePoint.class))
			.setImmutable(true)
			.setPredicateMandatory(EmfPredicates.always());

		attributes//
			.editAttribute(AGWorkflow.CURRENT_VERSION)
			.setPredicateEditable(EmfPredicates.never());
	}

	@Override
	public void customizeFormTabs(EmfFormTabConfiguration<AGWorkflow> tabConfiguration) {

		tabConfiguration.addManagementTab(AGWorkflowVersion.TABLE, WorkflowI18n.VERSIONS);
	}

	@Override
	public void customizeLoggers(EmfChangeLoggerSet<AGWorkflow> loggerSet) {

		loggerSet
			.addPlainChangeLogger(AGWorkflowLog.WORKFLOW, AGWorkflowLog.TRANSACTION)//
			.addMapping(AGWorkflow.ACTIVE, AGWorkflowLog.ACTIVE)
			.addMapping(AGWorkflow.CURRENT_VERSION, AGWorkflowLog.CURRENT_VERSION)
			.addMapping(AGWorkflow.NAME, AGWorkflowLog.NAME);
	}
}
