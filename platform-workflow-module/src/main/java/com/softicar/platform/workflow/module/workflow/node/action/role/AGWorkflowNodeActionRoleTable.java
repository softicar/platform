package com.softicar.platform.workflow.module.workflow.node.action.role;

import com.softicar.platform.core.module.uuid.AGUuid;
import com.softicar.platform.core.module.uuid.UuidIndirectEntityCollection;
import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.emf.attribute.IEmfAttributeList;
import com.softicar.platform.emf.authorization.role.CurrentEmfRoleRegistry;
import com.softicar.platform.emf.authorization.role.statik.IEmfStaticRole;
import com.softicar.platform.emf.log.EmfChangeLoggerSet;
import com.softicar.platform.emf.module.registry.CurrentEmfModuleRegistry;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.predicate.EmfPredicates;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;
import com.softicar.platform.workflow.module.workflow.node.action.AGWorkflowNodeAction;
import java.util.Collection;
import java.util.stream.Collectors;

public class AGWorkflowNodeActionRoleTable extends EmfObjectTable<AGWorkflowNodeActionRole, AGWorkflowNodeAction> {

	public AGWorkflowNodeActionRoleTable(IDbObjectTableBuilder<AGWorkflowNodeActionRole> builder) {

		super(builder);
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<AGWorkflowNodeActionRole, Integer, AGWorkflowNodeAction> configuration) {

		configuration.setScopeField(AGWorkflowNodeActionRole.WORKFLOW_NODE_ACTION);
	}

	@Override
	public void customizeAttributeProperties(IEmfAttributeList<AGWorkflowNodeActionRole> attributes) {

		attributes//
			.editIndirectEntityAttribute(AGWorkflowNodeActionRole.ROLE_UUID)
			.setEntityLoader(this::loadIndirectStaticRoles)
			.setPredicateMandatory(EmfPredicates.always());
	}

	@Override
	public void customizeLoggers(EmfChangeLoggerSet<AGWorkflowNodeActionRole> loggerSet) {

		loggerSet
			.addPlainChangeLogger(AGWorkflowNodeActionRoleLog.WORKFLOW_NODE_ACTION_ROLE, AGWorkflowNodeActionRoleLog.TRANSACTION)//
			.addMapping(AGWorkflowNodeActionRole.ACTIVE, AGWorkflowNodeActionRoleLog.ACTIVE)
			.addMapping(AGWorkflowNodeActionRole.ROLE_UUID, AGWorkflowNodeActionRoleLog.ROLE_UUID);
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
