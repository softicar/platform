package com.softicar.platform.core.module.access.role.assignment.module.system;

import com.softicar.platform.core.module.CoreRoles;
import com.softicar.platform.core.module.module.instance.system.SystemModuleInstance;
import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.emf.EmfImages;
import com.softicar.platform.emf.attribute.IEmfAttributeList;
import com.softicar.platform.emf.authorizer.EmfAuthorizer;
import com.softicar.platform.emf.log.EmfChangeLoggerSet;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.predicate.EmfPredicates;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;

public class AGSystemModuleRoleAssignmentTable extends EmfObjectTable<AGSystemModuleRoleAssignment, SystemModuleInstance> {

	public AGSystemModuleRoleAssignmentTable(IDbObjectTableBuilder<AGSystemModuleRoleAssignment> builder) {

		super(builder);
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<AGSystemModuleRoleAssignment, Integer, SystemModuleInstance> configuration) {

		configuration.setIcon(EmfImages.USER_ROLE_ASSIGNMENT);
	}

	@Override
	public void customizeAuthorizer(EmfAuthorizer<AGSystemModuleRoleAssignment, SystemModuleInstance> authorizer) {

		authorizer.setCreationRole(CoreRoles.ACCESS_MANAGER);
		authorizer.setEditRole(CoreRoles.ACCESS_MANAGER.toOtherEntityRole());
		authorizer.setViewRole(CoreRoles.ACCESS_MANAGER.toOtherEntityRole());
	}

	@Override
	public void customizeAttributeProperties(IEmfAttributeList<AGSystemModuleRoleAssignment> attributes) {

		attributes//
			.editAttribute(AGSystemModuleRoleAssignment.USER)
			.setImmutable(true)
			.setPredicateMandatory(EmfPredicates.always())
			.setInputFactory(SystemModuleRoleAssignmentUserInput::new);

		attributes//
			.editAttribute(AGSystemModuleRoleAssignment.MODULE)
			.setImmutable(true)
			.setPredicateMandatory(EmfPredicates.always())
			.setInputFactory(SystemModuleRoleAssignmentSystemModuleInput::new);

		attributes//
			.editAttribute(AGSystemModuleRoleAssignment.ROLE)
			.setImmutable(true)
			.setPredicateMandatory(EmfPredicates.always())
			.setInputFactory(SystemModuleRoleAssignmentRoleInput::new);
	}

	@Override
	public void customizeLoggers(EmfChangeLoggerSet<AGSystemModuleRoleAssignment> loggerSet) {

		loggerSet//
			.addPlainChangeLogger(AGSystemModuleRoleAssignmentLog.ASSIGNMENT, AGSystemModuleRoleAssignmentLog.TRANSACTION)
			.addMapping(AGSystemModuleRoleAssignment.ACTIVE, AGSystemModuleRoleAssignmentLog.ACTIVE);
	}
}
