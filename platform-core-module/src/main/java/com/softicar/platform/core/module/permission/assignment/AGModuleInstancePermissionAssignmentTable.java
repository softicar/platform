package com.softicar.platform.core.module.permission.assignment;

import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.CoreModule;
import com.softicar.platform.core.module.CorePermissions;
import com.softicar.platform.core.module.permission.ModulePermissionInput;
import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.emf.EmfImages;
import com.softicar.platform.emf.attribute.IEmfAttributeList;
import com.softicar.platform.emf.authorizer.EmfAuthorizer;
import com.softicar.platform.emf.log.EmfChangeLoggerSet;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.predicate.EmfPredicates;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;

public class AGModuleInstancePermissionAssignmentTable extends EmfObjectTable<AGModuleInstancePermissionAssignment, AGCoreModuleInstance> {

	public AGModuleInstancePermissionAssignmentTable(IDbObjectTableBuilder<AGModuleInstancePermissionAssignment> builder) {

		super(builder);
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<AGModuleInstancePermissionAssignment, Integer, AGCoreModuleInstance> configuration) {

		configuration.setIcon(EmfImages.USER_PERMISSION_ASSIGNMENT);
	}

	@Override
	public void customizeAuthorizer(EmfAuthorizer<AGModuleInstancePermissionAssignment, AGCoreModuleInstance> authorizer) {

		authorizer.setCreationPermission(CorePermissions.ADMINISTRATION);
		authorizer.setEditPermission(CoreModule.getAdministationPermission());
		authorizer.setViewPermission(CoreModule.getAdministationPermission());
	}

	@Override
	public void customizeAttributeProperties(IEmfAttributeList<AGModuleInstancePermissionAssignment> attributes) {

		attributes//
			.editAttribute(AGModuleInstancePermissionAssignment.USER)
			.setImmutable(true)
			.setPredicateMandatory(EmfPredicates.always());

		attributes//
			.editAttribute(AGModuleInstancePermissionAssignment.MODULE_INSTANCE_BASE)
			.setImmutable(true)
			.setPredicateMandatory(EmfPredicates.always());

		attributes//
			.editAttribute(AGModuleInstancePermissionAssignment.PERMISSION)
			.setPredicateMandatory(EmfPredicates.always())
			.setInputFactoryByEntity(ModulePermissionInput::new);
	}

	@Override
	public void customizeLoggers(EmfChangeLoggerSet<AGModuleInstancePermissionAssignment> loggerSet) {

		loggerSet//
			.addPlainChangeLogger(AGModuleInstancePermissionAssignmentLog.ASSIGNMENT, AGModuleInstancePermissionAssignmentLog.TRANSACTION)
			.addMapping(AGModuleInstancePermissionAssignment.ACTIVE, AGModuleInstancePermissionAssignmentLog.ACTIVE)
			.addMapping(AGModuleInstancePermissionAssignment.PERMISSION, AGModuleInstancePermissionAssignmentLog.PERMISSION);

	}
}
