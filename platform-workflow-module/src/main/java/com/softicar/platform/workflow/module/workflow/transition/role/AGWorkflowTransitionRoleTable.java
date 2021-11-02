package com.softicar.platform.workflow.module.workflow.transition.role;

import com.softicar.platform.core.module.uuid.AGUuid;
import com.softicar.platform.core.module.uuid.UuidIndirectEntityCollection;
import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.emf.attribute.IEmfAttributeList;
import com.softicar.platform.emf.authorization.IEmfTableRowMapper;
import com.softicar.platform.emf.authorization.role.CurrentEmfRoleRegistry;
import com.softicar.platform.emf.authorization.role.statik.IEmfStaticRole;
import com.softicar.platform.emf.authorizer.EmfAuthorizer;
import com.softicar.platform.emf.log.EmfChangeLoggerSet;
import com.softicar.platform.emf.module.registry.CurrentEmfModuleRegistry;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.predicate.EmfPredicates;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.WorkflowRoles;
import com.softicar.platform.workflow.module.workflow.WorkflowPredicates;
import com.softicar.platform.workflow.module.workflow.transition.AGWorkflowTransition;
import com.softicar.platform.workflow.module.workflow.transition.WorkflowTransitionPredicates;
import java.util.Collection;
import java.util.stream.Collectors;

public class AGWorkflowTransitionRoleTable extends EmfObjectTable<AGWorkflowTransitionRole, AGWorkflowTransition> {

	public AGWorkflowTransitionRoleTable(IDbObjectTableBuilder<AGWorkflowTransitionRole> builder) {

		super(builder);
	}

	@Override
	public void customizeAuthorizer(EmfAuthorizer<AGWorkflowTransitionRole, AGWorkflowTransition> authorizer) {

		authorizer//
			.setCreationRole(
				WorkflowRoles.ADMINISTRATOR
					.of(IEmfTableRowMapper.nonOptional(WorkflowI18n.WORKFLOW_MODULE_INSTANCE, it -> it.getWorkflowVersion().getWorkflow().getModuleInstance())))
			.setViewRole(
				WorkflowRoles.VIEWER
					.of(
						IEmfTableRowMapper
							.nonOptional(
								WorkflowI18n.WORKFLOW_MODULE_INSTANCE,
								it -> it.getTransition().getWorkflowVersion().getWorkflow().getModuleInstance())))
			.setEditRole(
				WorkflowRoles.ADMINISTRATOR
					.of(
						IEmfTableRowMapper
							.nonOptional(
								WorkflowI18n.WORKFLOW_MODULE_INSTANCE,
								it -> it.getTransition().getWorkflowVersion().getWorkflow().getModuleInstance())));
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<AGWorkflowTransitionRole, Integer, AGWorkflowTransition> configuration) {

		configuration.setScopeField(AGWorkflowTransitionRole.TRANSITION);
		//TODO .not() wouldn't work on predicate WORKFLOW_VERSION_FINALIZED, because it then returns predicate of type object when using a IEmfTableRowMapper
		configuration
			.setEditPredicate(
				WorkflowPredicates.WORKFLOW_VERSION_DRAFT
					.of(IEmfTableRowMapper.nonOptional(WorkflowI18n.WORKFLOW_VERSION, it -> it.getTransition().getWorkflowVersion())));
		configuration//
			.setCreationPredicate(
				WorkflowPredicates.WORKFLOW_VERSION_FINALIZED
					.of(AGWorkflowTransition.WORKFLOW_VERSION)
					.not()
					.and(WorkflowTransitionPredicates.AUTO_TRANSITION.not()));
	}

	@Override
	public void customizeAttributeProperties(IEmfAttributeList<AGWorkflowTransitionRole> attributes) {

		attributes//
			.editIndirectEntityAttribute(AGWorkflowTransitionRole.ROLE)
			.setEntityLoader(this::loadIndirectStaticRoles)
			.setPredicateMandatory(EmfPredicates.always());
	}

	@Override
	public void customizeLoggers(EmfChangeLoggerSet<AGWorkflowTransitionRole> loggerSet) {

		loggerSet//
			.addPlainChangeLogger(AGWorkflowTransitionRoleLog.TRANSITION_ROLE, AGWorkflowTransitionRoleLog.TRANSACTION)
			.addMapping(AGWorkflowTransitionRole.ROLE, AGWorkflowTransitionRoleLog.ROLE)
			.addMapping(AGWorkflowTransitionRole.ACTIVE, AGWorkflowTransitionRoleLog.ACTIVE);
	}

	private UuidIndirectEntityCollection<IEmfStaticRole<?>> loadIndirectStaticRoles() {

		return AGUuid
			.createIndirectEntityCollection(
				CurrentEmfModuleRegistry//
					.get()
					.getAllModules()
					.stream()
					.map(CurrentEmfRoleRegistry.get()::getStaticRoles)
					.flatMap(Collection::stream)
					.collect(Collectors.toSet()));
	}
}
