package com.softicar.platform.emf.test.module.beta;

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

public class TestModuleBetaInstance extends AbstractDbObject<TestModuleBetaInstance>
		implements IEmfModuleInstance<TestModuleBetaInstance>, IEmfObject<TestModuleBetaInstance> {

	//@formatter:off
	private static final DbObjectTableBuilder<TestModuleBetaInstance, TestModuleBetaInstance> BUILDER = new DbObjectTableBuilder<>("Core", TestModuleBetaInstance.class.getSimpleName(), TestModuleBetaInstance::new, TestModuleBetaInstance.class);
	public static final IDbIdField<TestModuleBetaInstance> ID = BUILDER.addIdField("id", o->o.id, (o,v)->o.id=v);
	public static final TestModuleBetaInstanceTable TABLE = new TestModuleBetaInstanceTable(BUILDER);
	//@formatter:on

	private Integer id;

	@Override
	public IDisplayString toDisplayWithoutId() {

		throw new UnsupportedOperationException();
	}

	@Override
	public TestModuleBetaInstanceTable table() {

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
	public boolean hasPermission(IEmfModulePermission<TestModuleBetaInstance> permission, IBasicUser user) {

		throw new UnsupportedOperationException();
	}
}
