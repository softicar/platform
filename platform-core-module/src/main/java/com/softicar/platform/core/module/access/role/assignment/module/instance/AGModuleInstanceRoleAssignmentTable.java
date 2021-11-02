package com.softicar.platform.core.module.access.role.assignment.module.instance;

import com.softicar.platform.core.module.CoreRoles;
import com.softicar.platform.core.module.access.role.assignment.module.system.SystemModuleRoleAssignmentUserInput;
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

public class AGModuleInstanceRoleAssignmentTable extends EmfObjectTable<AGModuleInstanceRoleAssignment, SystemModuleInstance> {

	public AGModuleInstanceRoleAssignmentTable(IDbObjectTableBuilder<AGModuleInstanceRoleAssignment> builder) {

		super(builder);
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<AGModuleInstanceRoleAssignment, Integer, SystemModuleInstance> configuration) {

		configuration.setIcon(EmfImages.USER_ROLE_ASSIGNMENT);
	}

	@Override
	public void customizeAuthorizer(EmfAuthorizer<AGModuleInstanceRoleAssignment, SystemModuleInstance> authorizer) {

		authorizer.setCreationRole(CoreRoles.ACCESS_MANAGER);
		authorizer.setEditRole(CoreRoles.ACCESS_MANAGER.toOtherEntityRole());
		authorizer.setViewRole(CoreRoles.ACCESS_MANAGER.toOtherEntityRole());
	}

	@Override
	public void customizeAttributeProperties(IEmfAttributeList<AGModuleInstanceRoleAssignment> attributes) {

		attributes//
			.editAttribute(AGModuleInstanceRoleAssignment.USER)
			.setImmutable(true)
			.setPredicateMandatory(EmfPredicates.always())
			.setInputFactory(SystemModuleRoleAssignmentUserInput::new);

		attributes//
			.editAttribute(AGModuleInstanceRoleAssignment.MODULE_INSTANCE)
			.setImmutable(true)
			.setPredicateMandatory(EmfPredicates.always());

		attributes//
			.editAttribute(AGModuleInstanceRoleAssignment.ROLE)
			.setPredicateMandatory(EmfPredicates.always())
			.setInputFactoryByEntity(ModuleInstanceRoleAssignmentRoleInput::new);
	}

	@Override
	public void customizeAttributeDependencies(EmfAttributeDependencyMap<AGModuleInstanceRoleAssignment> dependencyMap) {

		dependencyMap//
			.editAttribute(AGModuleInstanceRoleAssignment.ROLE)
			.setDependsOn(AGModuleInstanceRoleAssignment.MODULE_INSTANCE);
	}

	@Override
	public void customizeLoggers(EmfChangeLoggerSet<AGModuleInstanceRoleAssignment> loggerSet) {

		loggerSet//
			.addPlainChangeLogger(AGModuleInstanceRoleAssignmentLog.ASSIGNMENT, AGModuleInstanceRoleAssignmentLog.TRANSACTION)
			.addMapping(AGModuleInstanceRoleAssignment.ACTIVE, AGModuleInstanceRoleAssignmentLog.ACTIVE)
			.addMapping(AGModuleInstanceRoleAssignment.ROLE, AGModuleInstanceRoleAssignmentLog.ROLE);

	}
}
