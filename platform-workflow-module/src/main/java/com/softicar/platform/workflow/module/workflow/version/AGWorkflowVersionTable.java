package com.softicar.platform.workflow.module.workflow.version;

import com.softicar.platform.common.container.comparator.OrderDirection;
import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.dom.DomCssPseudoClasses;
import com.softicar.platform.emf.action.EmfActionSet;
import com.softicar.platform.emf.attribute.EmfAttributeReorderer;
import com.softicar.platform.emf.attribute.IEmfAttributeList;
import com.softicar.platform.emf.attribute.field.bool.EmfBooleanDisplay;
import com.softicar.platform.emf.authorizer.EmfAuthorizer;
import com.softicar.platform.emf.data.table.IEmfDataTableRow;
import com.softicar.platform.emf.form.tab.factory.EmfFormTabConfiguration;
import com.softicar.platform.emf.log.EmfChangeLoggerSet;
import com.softicar.platform.emf.management.EmfManagementConfiguration;
import com.softicar.platform.emf.mapper.IEmfTableRowMapper;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.predicate.EmfPredicates;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;
import com.softicar.platform.workflow.module.WorkflowCssClasses;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.WorkflowImages;
import com.softicar.platform.workflow.module.WorkflowPermissions;
import com.softicar.platform.workflow.module.workflow.AGWorkflow;
import com.softicar.platform.workflow.module.workflow.WorkflowPredicates;
import com.softicar.platform.workflow.module.workflow.management.WorkflowVersionManagementAction;
import com.softicar.platform.workflow.module.workflow.node.AGWorkflowNode;
import com.softicar.platform.workflow.module.workflow.transition.AGWorkflowTransition;
import com.softicar.platform.workflow.module.workflow.version.export.WorkflowVersionExportAction;

public class AGWorkflowVersionTable extends EmfObjectTable<AGWorkflowVersion, AGWorkflow> {

	public AGWorkflowVersionTable(IDbObjectTableBuilder<AGWorkflowVersion> builder) {

		super(builder);
	}

	@Override
	public void customizeAuthorizer(EmfAuthorizer<AGWorkflowVersion, AGWorkflow> authorizer) {

		authorizer//
			.setCreationPermission(
				WorkflowPermissions.ADMINISTRATION.of(IEmfTableRowMapper.nonOptional(WorkflowI18n.WORKFLOW_MODULE_INSTANCE, it -> it.getModuleInstance())))
			.setViewPermission(
				WorkflowPermissions.VIEW.of(IEmfTableRowMapper.nonOptional(WorkflowI18n.WORKFLOW_MODULE_INSTANCE, it -> it.getWorkflow().getModuleInstance())))
			.setEditPermission(
				WorkflowPermissions.ADMINISTRATION
					.of(IEmfTableRowMapper.nonOptional(WorkflowI18n.WORKFLOW_MODULE_INSTANCE, it -> it.getWorkflow().getModuleInstance())));
	}

	@Override
	public void customizeManagementConfiguraton(EmfManagementConfiguration<AGWorkflowVersion> configuration) {

		configuration.addOrderBy(AGWorkflowVersion.ID, OrderDirection.DESCENDING);
		configuration.setRowCustomizer(this::customizeRow);
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<AGWorkflowVersion, Integer, AGWorkflow> configuration) {

		configuration.setScopeField(AGWorkflowVersion.WORKFLOW);
		configuration.setCreationPredicate(WorkflowPredicates.WORKFLOW_VERSION_PRESENT.not());
		configuration.setIcon(WorkflowImages.WORKFLOW);
		configuration.addCommitHook(WorkflowVersionCommitHook::new);
	}

	@Override
	public void customizeAttributeProperties(IEmfAttributeList<AGWorkflowVersion> attributes) {

		attributes.addTransientAttribute(AGWorkflowVersion.HASH_FIELD);
		attributes.addTransientAttribute(AGWorkflowVersion.ITEM_COUNT_FIELD);
		attributes//
			.addTransientAttribute(AGWorkflowVersion.CURRENT_VERSION_FIELD)
			.setDisplayFactory(EmfBooleanDisplay::new);
		attributes//
			.editAttribute(AGWorkflowVersion.DRAFT)
			.setPredicateEditable(EmfPredicates.never());
		attributes//
			.editAttribute(AGWorkflowVersion.ROOT_NODE)
			.setPredicateEditable(WorkflowPredicates.WORKFLOW_VERSION_DRAFT);
	}

	@Override
	public void customizeAttributeOrdering(EmfAttributeReorderer<AGWorkflowVersion> reorderer) {

		reorderer//
			.moveAttribute(AGWorkflowVersion.CURRENT_VERSION_FIELD)
			.inFrontOf(AGWorkflowVersion.DRAFT);
		reorderer//
			.moveAttribute(AGWorkflowVersion.ROOT_NODE)
			.toBack();
	}

	@Override
	public void customizeFormTabs(EmfFormTabConfiguration<AGWorkflowVersion> tabConfiguration) {

		tabConfiguration.addManagementTab(AGWorkflowNode.TABLE, WorkflowI18n.NODES);
		tabConfiguration.addManagementTab(AGWorkflowTransition.TABLE, WorkflowI18n.TRANSITIONS);
	}

	@Override
	public void customizeActionSet(EmfActionSet<AGWorkflowVersion, AGWorkflow> actionSet) {

		actionSet.addManagementAction(new WorkflowVersionManagementAction());
		actionSet.addManagementAction(new CreateNewWorkflowVersionAction());
		actionSet.addManagementAction(new ActivateWorkflowVersionAction());
		actionSet.addManagementAction(new WorkflowVersionExportAction());
	}

	@Override
	public void customizeLoggers(EmfChangeLoggerSet<AGWorkflowVersion> loggerSet) {

		loggerSet
			.addPlainChangeLogger(AGWorkflowVersionLog.WORKFLOW_VERSION, AGWorkflowVersionLog.TRANSACTION)//
			.addMapping(AGWorkflowVersion.ROOT_NODE, AGWorkflowVersionLog.ROOT_NODE)
			.addMapping(AGWorkflowVersion.DRAFT, AGWorkflowVersionLog.DRAFT);
	}

	private void customizeRow(IEmfDataTableRow<AGWorkflowVersion> row) {

		row.addCssClass(WorkflowCssClasses.WORKFLOW_VERSION_ROW);
		if (row.getDataRow().isCurrentVersion()) {
			row.addCssClass(DomCssPseudoClasses.ACTIVE);
		}
	}
}
