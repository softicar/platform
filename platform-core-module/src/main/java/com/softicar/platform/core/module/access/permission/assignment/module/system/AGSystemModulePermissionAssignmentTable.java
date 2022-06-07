package com.softicar.platform.core.module.access.permission.assignment.module.system;

import com.softicar.platform.core.module.CorePermissions;
import com.softicar.platform.core.module.module.instance.system.SystemModuleInstance;
import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.emf.EmfImages;
import com.softicar.platform.emf.attribute.IEmfAttributeList;
import com.softicar.platform.emf.authorizer.EmfAuthorizer;
import com.softicar.platform.emf.log.EmfChangeLoggerSet;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.predicate.EmfPredicates;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;

public class AGSystemModulePermissionAssignmentTable extends EmfObjectTable<AGSystemModulePermissionAssignment, SystemModuleInstance> {

	public AGSystemModulePermissionAssignmentTable(IDbObjectTableBuilder<AGSystemModulePermissionAssignment> builder) {

		super(builder);
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<AGSystemModulePermissionAssignment, Integer, SystemModuleInstance> configuration) {

		configuration.setIcon(EmfImages.USER_PERMISSION_ASSIGNMENT);
	}

	@Override
	public void customizeAuthorizer(EmfAuthorizer<AGSystemModulePermissionAssignment, SystemModuleInstance> authorizer) {

		authorizer.setCreationPermission(CorePermissions.ACCESS_MANAGEMENT);
		authorizer.setEditPermission(CorePermissions.ACCESS_MANAGEMENT.toOtherEntityPermission());
		authorizer.setViewPermission(CorePermissions.ACCESS_MANAGEMENT.toOtherEntityPermission());
	}

	@Override
	public void customizeAttributeProperties(IEmfAttributeList<AGSystemModulePermissionAssignment> attributes) {

		attributes//
			.editAttribute(AGSystemModulePermissionAssignment.USER)
			.setImmutable(true)
			.setPredicateMandatory(EmfPredicates.always())
			.setInputFactory(SystemModulePermissionAssignmentUserInput::new);

		attributes//
			.editAttribute(AGSystemModulePermissionAssignment.MODULE)
			.setImmutable(true)
			.setPredicateMandatory(EmfPredicates.always())
			.setInputFactory(SystemModulePermissionAssignmentSystemModuleInput::new);

		attributes//
			.editAttribute(AGSystemModulePermissionAssignment.PERMISSION)
			.setImmutable(true)
			.setPredicateMandatory(EmfPredicates.always())
			.setInputFactory(SystemModulePermissionAssignmentPermissionInput::new);
	}

	@Override
	public void customizeLoggers(EmfChangeLoggerSet<AGSystemModulePermissionAssignment> loggerSet) {

		loggerSet//
			.addPlainChangeLogger(AGSystemModulePermissionAssignmentLog.ASSIGNMENT, AGSystemModulePermissionAssignmentLog.TRANSACTION)
			.addMapping(AGSystemModulePermissionAssignment.ACTIVE, AGSystemModulePermissionAssignmentLog.ACTIVE);
	}
}
