package com.softicar.platform.emf.test.module.permission;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.permission.statik.EmfStaticPermissionBuilder;
import com.softicar.platform.emf.permission.statik.IEmfStaticPermission;

public class PermissionTestModuleInstanceTable extends EmfObjectTable<PermissionTestModuleInstance, Void> {

	public static final IEmfStaticPermission<PermissionTestModuleInstance> STATIC_TEST_PERMISSION =
			new EmfStaticPermissionBuilder<PermissionTestModuleInstance>((x, y) -> true)
				.setTitle(IDisplayString.create("Static Test Permission"))
				.setUuid("b5b83f86-47c7-4c68-9cb5-1582996c6f85")
				.build();

	public PermissionTestModuleInstanceTable(IDbObjectTableBuilder<PermissionTestModuleInstance> builder) {

		super(builder);
	}
}
