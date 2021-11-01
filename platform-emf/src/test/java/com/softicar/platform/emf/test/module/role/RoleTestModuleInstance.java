package com.softicar.platform.emf.test.module.role;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.item.ItemId;
import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.emf.module.IEmfModuleInstance;
import com.softicar.platform.emf.module.IUuid;
import com.softicar.platform.emf.module.role.IEmfModuleRole;
import com.softicar.platform.emf.object.IEmfObject;

public class RoleTestModuleInstance extends AbstractDbObject<RoleTestModuleInstance>
		implements IEmfModuleInstance<RoleTestModuleInstance>, IEmfObject<RoleTestModuleInstance> {

	private static final DbObjectTableBuilder<RoleTestModuleInstance, RoleTestModuleInstance> BUILDER =
			new DbObjectTableBuilder<>("Core", RoleTestModuleInstance.class.getSimpleName(), RoleTestModuleInstance::new, RoleTestModuleInstance.class);
	public static final IDbIdField<RoleTestModuleInstance> ID = BUILDER.addIdField("id", o -> o.id, (o, v) -> o.id = v);
	public static final RoleTestModuleInstanceTable TABLE = new RoleTestModuleInstanceTable(BUILDER);

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
	public boolean hasRole(IEmfModuleRole<RoleTestModuleInstance> role, IBasicUser user) {

		throw new UnsupportedOperationException();
	}

	@Override
	public IDisplayString toDisplayWithoutId() {

		throw new UnsupportedOperationException();
	}

	@Override
	public RoleTestModuleInstanceTable table() {

		return TABLE;
	}
}
