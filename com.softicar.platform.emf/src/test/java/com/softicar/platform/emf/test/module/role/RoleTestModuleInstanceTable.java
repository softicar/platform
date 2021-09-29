package com.softicar.platform.emf.test.module.role;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.emf.authorization.role.statik.EmfStaticRoleBuilder;
import com.softicar.platform.emf.authorization.role.statik.IEmfStaticRole;
import com.softicar.platform.emf.object.table.EmfObjectTable;

public class RoleTestModuleInstanceTable extends EmfObjectTable<RoleTestModuleInstance, Void> {

	public static final IEmfStaticRole<RoleTestModuleInstance> STATIC_TEST_ROLE = new EmfStaticRoleBuilder<RoleTestModuleInstance>((x, y) -> true)
		.setTitle(IDisplayString.create("Static Test Role"))
		.setUuid("b5b83f86-47c7-4c68-9cb5-1582996c6f85")
		.build();

	public RoleTestModuleInstanceTable(IDbObjectTableBuilder<RoleTestModuleInstance> builder) {

		super(builder);
	}
}
