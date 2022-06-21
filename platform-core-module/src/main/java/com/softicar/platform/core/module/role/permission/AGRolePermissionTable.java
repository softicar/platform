package com.softicar.platform.core.module.role.permission;

import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.permission.ModulePermissionInput;
import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.emf.attribute.IEmfAttributeList;
import com.softicar.platform.emf.log.EmfChangeLoggerSet;
import com.softicar.platform.emf.object.table.EmfObjectTable;

public class AGRolePermissionTable extends EmfObjectTable<AGRolePermission, AGCoreModuleInstance> {

	public AGRolePermissionTable(IDbObjectTableBuilder<AGRolePermission> builder) {

		super(builder);
	}

	@Override
	public void customizeLoggers(EmfChangeLoggerSet<AGRolePermission> loggerSet) {

		loggerSet//
			.addPlainChangeLogger(AGRolePermissionLog.ROLE_USER, AGRolePermissionLog.TRANSACTION)
			.addMapping(AGRolePermission.ACTIVE, AGRolePermissionLog.ACTIVE)
			.addMapping(AGRolePermission.ROLE, AGRolePermissionLog.ROLE)
			.addMapping(AGRolePermission.MODULE_INSTANCE, AGRolePermissionLog.MODULE_INSTANCE)
			.addMapping(AGRolePermission.PERMISSION_UUID, AGRolePermissionLog.PERMISSION_UUID);
	}

	@Override
	public void customizeAttributeProperties(IEmfAttributeList<AGRolePermission> attributes) {

		attributes//
			.editAttribute(AGRolePermission.PERMISSION_UUID)
			.setTitle(CoreI18n.PERMISSION)
			.setInputFactoryByEntity(ModulePermissionInput::new);
	}
}
