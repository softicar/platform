package com.softicar.platform.core.module.test.module.alpha;

import com.softicar.platform.common.core.i18n.DisplayString;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.module.instance.AGModuleInstance;
import com.softicar.platform.db.runtime.field.IDbForeignRowField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.emf.object.AbstractEmfObject;

public class TestModuleAlphaEntity extends AbstractEmfObject<TestModuleAlphaEntity> {

	// @formatter:off
	public static final DbObjectTableBuilder<TestModuleAlphaEntity, TestModuleAlphaEntity> BUILDER = new DbObjectTableBuilder<>("Core", TestModuleAlphaEntity.class.getSimpleName(), TestModuleAlphaEntity::new, TestModuleAlphaEntity.class);
	public static final IDbIdField<TestModuleAlphaEntity> ID = BUILDER.addIdField("id", o->o.id, (o,v)->o.id=v);
	public static final IDbForeignRowField<TestModuleAlphaEntity, TestModuleAlphaInstance, AGModuleInstance> MODULE_INSTANCE = BUILDER.addForeignRowField("moduleInstance", o->o.moduleInstance, (o,v)->o.moduleInstance=v, TestModuleAlphaInstance.MODULE_INSTANCE).setNullable().setDefault(null);
	public static final IDbStringField<TestModuleAlphaEntity> NAME = BUILDER.addStringField("name", o->o.name, (o,v)->o.name=v).setMaximumLength(255).setNullable().setDefault(null);
	public static final TestModuleAlphaEntityTable TABLE = new TestModuleAlphaEntityTable(BUILDER);
	// @formatter:on

	private Integer id;
	private TestModuleAlphaInstance moduleInstance;
	private String name;

	@Override
	public IDisplayString toDisplayWithoutId() {

		return new DisplayString().append(getValue(NAME));
	}

	@Override
	public TestModuleAlphaEntityTable table() {

		return TABLE;
	}
}
