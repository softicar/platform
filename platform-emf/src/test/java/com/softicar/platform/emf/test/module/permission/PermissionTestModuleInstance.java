package com.softicar.platform.emf.test.module.permission;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.item.ItemId;
import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.emf.module.IEmfModuleInstance;
import com.softicar.platform.emf.module.IUuid;
import com.softicar.platform.emf.module.permission.IEmfModulePermission;
import com.softicar.platform.emf.object.IEmfObject;

public class PermissionTestModuleInstance extends AbstractDbObject<PermissionTestModuleInstance>
		implements IEmfModuleInstance<PermissionTestModuleInstance>, IEmfObject<PermissionTestModuleInstance> {

	private static final DbObjectTableBuilder<PermissionTestModuleInstance, PermissionTestModuleInstance> BUILDER = new DbObjectTableBuilder<>(
		"Core",
		PermissionTestModuleInstance.class.getSimpleName(),
		PermissionTestModuleInstance::new,
		PermissionTestModuleInstance.class);
	public static final IDbIdField<PermissionTestModuleInstance> ID = BUILDER.addIdField("id", o -> o.id, (o, v) -> o.id = v);
	public static final PermissionTestModuleInstanceTable TABLE = new PermissionTestModuleInstanceTable(BUILDER);

	private Integer id;

	@Override
	public ItemId getItemId() {

		return new ItemId(getId());
	}

	@Override
	public IUuid getModuleUuid() {

		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasPermission(IEmfModulePermission<PermissionTestModuleInstance> permission, IBasicUser user) {

		throw new UnsupportedOperationException();
	}

	@Override
	public IDisplayString toDisplayWithoutId() {

		throw new UnsupportedOperationException();
	}

	@Override
	public PermissionTestModuleInstanceTable table() {

		return TABLE;
	}
}
