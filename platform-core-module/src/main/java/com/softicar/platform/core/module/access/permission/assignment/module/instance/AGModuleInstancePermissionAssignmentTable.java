package com.softicar.platform.core.module.access.permission.assignment.module.instance;

import com.softicar.platform.core.module.CorePermissions;
import com.softicar.platform.core.module.access.permission.assignment.module.system.SystemModulePermissionAssignmentUserInput;
import com.softicar.platform.core.module.module.instance.system.SystemModuleInstance;
import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.emf.EmfImages;
import com.softicar.platform.emf.attribute.IEmfAttributeList;
import com.softicar.platform.emf.attribute.dependency.EmfAttributeDependencyMap;
import com.softicar.platform.emf.authorizer.EmfAuthorizer;
import com.softicar.platform.emf.log.EmfChangeLoggerSet;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.predicate.EmfPredicates;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;

public class AGModuleInstancePermissionAssignmentTable extends EmfObjectTable<AGModuleInstancePermissionAssignment, SystemModuleInstance> {

	public AGModuleInstancePermissionAssignmentTable(IDbObjectTableBuilder<AGModuleInstancePermissionAssignment> builder) {

		super(builder);
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<AGModuleInstancePermissionAssignment, Integer, SystemModuleInstance> configuration) {

		configuration.setIcon(EmfImages.USER_PERMISSION_ASSIGNMENT);
	}

	@Override
	public void customizeAuthorizer(EmfAuthorizer<AGModuleInstancePermissionAssignment, SystemModuleInstance> authorizer) {

		authorizer.setCreationPermission(CorePermissions.ACCESS_MANAGER);
		authorizer.setEditPermission(CorePermissions.ACCESS_MANAGER.toOtherEntityPermission());
		authorizer.setViewPermission(CorePermissions.ACCESS_MANAGER.toOtherEntityPermission());
	}

	@Override
	public void customizeAttributeProperties(IEmfAttributeList<AGModuleInstancePermissionAssignment> attributes) {

		attributes//
			.editAttribute(AGModuleInstancePermissionAssignment.USER)
			.setImmutable(true)
			.setPredicateMandatory(EmfPredicates.always())
			.setInputFactory(SystemModulePermissionAssignmentUserInput::new);

		attributes//
			.editAttribute(AGModuleInstancePermissionAssignment.MODULE_INSTANCE)
			.setImmutable(true)
			.setPredicateMandatory(EmfPredicates.always());

		attributes//
			.editAttribute(AGModuleInstancePermissionAssignment.PERMISSION)
			.setPredicateMandatory(EmfPredicates.always())
			.setInputFactoryByEntity(ModuleInstancePermissionAssignmentPermissionInput::new);
	}

	@Override
	public void customizeAttributeDependencies(EmfAttributeDependencyMap<AGModuleInstancePermissionAssignment> dependencyMap) {

		dependencyMap//
			.editAttribute(AGModuleInstancePermissionAssignment.PERMISSION)
			.setDependsOn(AGModuleInstancePermissionAssignment.MODULE_INSTANCE);
	}

	@Override
	public void customizeLoggers(EmfChangeLoggerSet<AGModuleInstancePermissionAssignment> loggerSet) {

		loggerSet//
			.addPlainChangeLogger(AGModuleInstancePermissionAssignmentLog.ASSIGNMENT, AGModuleInstancePermissionAssignmentLog.TRANSACTION)
			.addMapping(AGModuleInstancePermissionAssignment.ACTIVE, AGModuleInstancePermissionAssignmentLog.ACTIVE)
			.addMapping(AGModuleInstancePermissionAssignment.PERMISSION, AGModuleInstancePermissionAssignmentLog.PERMISSION);

	}
}
