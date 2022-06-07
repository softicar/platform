package com.softicar.platform.workflow.module.workflow.node.action.permission;

import com.softicar.platform.core.module.uuid.AGUuid;
import com.softicar.platform.core.module.uuid.UuidIndirectEntityCollection;
import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.emf.attribute.IEmfAttributeList;
import com.softicar.platform.emf.log.EmfChangeLoggerSet;
import com.softicar.platform.emf.module.registry.CurrentEmfModuleRegistry;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.permission.CurrentEmfPermissionRegistry;
import com.softicar.platform.emf.permission.statik.IEmfStaticPermission;
import com.softicar.platform.emf.predicate.EmfPredicates;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;
import com.softicar.platform.workflow.module.workflow.node.action.AGWorkflowNodeAction;
import java.util.Collection;
import java.util.stream.Collectors;

public class AGWorkflowNodeActionPermissionTable extends EmfObjectTable<AGWorkflowNodeActionPermission, AGWorkflowNodeAction> {

	public AGWorkflowNodeActionPermissionTable(IDbObjectTableBuilder<AGWorkflowNodeActionPermission> builder) {

		super(builder);
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<AGWorkflowNodeActionPermission, Integer, AGWorkflowNodeAction> configuration) {

		configuration.setScopeField(AGWorkflowNodeActionPermission.WORKFLOW_NODE_ACTION);
	}

	@Override
	public void customizeAttributeProperties(IEmfAttributeList<AGWorkflowNodeActionPermission> attributes) {

		attributes//
			.editIndirectEntityAttribute(AGWorkflowNodeActionPermission.PERMISSION_UUID)
			.setEntityLoader(this::loadIndirectStaticPermissions)
			.setPredicateMandatory(EmfPredicates.always());
	}

	@Override
	public void customizeLoggers(EmfChangeLoggerSet<AGWorkflowNodeActionPermission> loggerSet) {

		loggerSet
			.addPlainChangeLogger(AGWorkflowNodeActionPermissionLog.WORKFLOW_NODE_ACTION_PERMISSION, AGWorkflowNodeActionPermissionLog.TRANSACTION)//
			.addMapping(AGWorkflowNodeActionPermission.ACTIVE, AGWorkflowNodeActionPermissionLog.ACTIVE)
			.addMapping(AGWorkflowNodeActionPermission.PERMISSION_UUID, AGWorkflowNodeActionPermissionLog.PERMISSION_UUID);
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
