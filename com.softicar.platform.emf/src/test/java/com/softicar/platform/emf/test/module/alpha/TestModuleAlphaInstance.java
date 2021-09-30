package com.softicar.platform.emf.test.module.alpha;

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

public class TestModuleAlphaInstance extends AbstractDbObject<TestModuleAlphaInstance>
		implements IEmfModuleInstance<TestModuleAlphaInstance>, IEmfObject<TestModuleAlphaInstance> {

	//@formatter:off
	private static final DbObjectTableBuilder<TestModuleAlphaInstance, TestModuleAlphaInstance> BUILDER = new DbObjectTableBuilder<>("Core", TestModuleAlphaInstance.class.getSimpleName(), TestModuleAlphaInstance::new, TestModuleAlphaInstance.class);
	public static final IDbIdField<TestModuleAlphaInstance> ID = BUILDER.addIdField("id", o->o.id, (o,v)->o.id=v);
	public static final TestModuleAlphaInstanceTable TABLE = new TestModuleAlphaInstanceTable(BUILDER);
	//@formatter:on

	private Integer id;

	@Override
	public IDisplayString toDisplayWithoutId() {

		throw new UnsupportedOperationException();
	}

	@Override
	public TestModuleAlphaInstanceTable table() {

		return TABLE;
	}

	@Override
	public IUuid getModuleUuid() {

		throw new UnsupportedOperationException();
	}

	@Override
	public ItemId getItemId() {

		return new ItemId(getId());
	}

	@Override
	public boolean hasRole(IEmfModuleRole<TestModuleAlphaInstance> role, IBasicUser user) {

		throw new UnsupportedOperationException();
	}
}
