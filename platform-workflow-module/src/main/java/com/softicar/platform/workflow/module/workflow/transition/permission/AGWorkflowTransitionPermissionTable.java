package com.softicar.platform.workflow.module.workflow.transition.permission;

import com.softicar.platform.core.module.uuid.AGUuid;
import com.softicar.platform.core.module.uuid.UuidIndirectEntityCollection;
import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.emf.attribute.IEmfAttributeList;
import com.softicar.platform.emf.authorizer.EmfAuthorizer;
import com.softicar.platform.emf.log.EmfChangeLoggerSet;
import com.softicar.platform.emf.mapper.IEmfTableRowMapper;
import com.softicar.platform.emf.module.registry.CurrentEmfModuleRegistry;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.permission.CurrentEmfPermissionRegistry;
import com.softicar.platform.emf.permission.statik.IEmfStaticPermission;
import com.softicar.platform.emf.predicate.EmfPredicates;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.WorkflowPermissions;
import com.softicar.platform.workflow.module.workflow.transition.AGWorkflowTransition;
import com.softicar.platform.workflow.module.workflow.transition.WorkflowTransitionPredicates;
import java.util.Collection;
import java.util.stream.Collectors;

public class AGWorkflowTransitionPermissionTable extends EmfObjectTable<AGWorkflowTransitionPermission, AGWorkflowTransition> {

	public AGWorkflowTransitionPermissionTable(IDbObjectTableBuilder<AGWorkflowTransitionPermission> builder) {

		super(builder);
	}

	@Override
	public void customizeAuthorizer(EmfAuthorizer<AGWorkflowTransitionPermission, AGWorkflowTransition> authorizer) {

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
								it -> it.getTransition().getWorkflowVersion().getWorkflow().getModuleInstance())))
			.setEditPermission(
				WorkflowPermissions.ADMINISTRATION
					.of(
						IEmfTableRowMapper
							.nonOptional(
								WorkflowI18n.WORKFLOW_MODULE_INSTANCE,
								it -> it.getTransition().getWorkflowVersion().getWorkflow().getModuleInstance())));
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<AGWorkflowTransitionPermission, Integer, AGWorkflowTransition> configuration) {

		configuration.setScopeField(AGWorkflowTransitionPermission.TRANSITION);
		configuration.setCreationPredicate(WorkflowTransitionPredicates.AUTO_TRANSITION.not());
	}

	@Override
	public void customizeAttributeProperties(IEmfAttributeList<AGWorkflowTransitionPermission> attributes) {

		attributes//
			.editIndirectEntityAttribute(AGWorkflowTransitionPermission.PERMISSION)
			.setEntityLoader(this::loadIndirectStaticPermissions)
			.setPredicateMandatory(EmfPredicates.always());
	}

	@Override
	public void customizeLoggers(EmfChangeLoggerSet<AGWorkflowTransitionPermission> loggerSet) {

		loggerSet//
			.addPlainChangeLogger(AGWorkflowTransitionPermissionLog.TRANSITION_PERMISSION, AGWorkflowTransitionPermissionLog.TRANSACTION)
			.addMapping(AGWorkflowTransitionPermission.PERMISSION, AGWorkflowTransitionPermissionLog.PERMISSION)
			.addMapping(AGWorkflowTransitionPermission.ACTIVE, AGWorkflowTransitionPermissionLog.ACTIVE);
	}

	private UuidIndirectEntityCollection<IEmfStaticPermission<?>> loadIndirectStaticPermissions() {

		return AGUuid
			.createIndirectEntityCollection(
				CurrentEmfModuleRegistry//
					.get()
					.getAllModules()
					.stream()
					.map(CurrentEmfPermissionRegistry.get()::getStaticPermissions)
					.flatMap(Collection::stream)
					.collect(Collectors.toSet()));
	}
}
