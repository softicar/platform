package com.softicar.platform.core.module.test.module.standard.alpha;

import com.softicar.platform.common.core.i18n.DisplayString;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.access.module.instance.AGModuleInstance;
import com.softicar.platform.db.runtime.field.IDbForeignRowField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.emf.object.AbstractEmfObject;

public class TestStandardModuleAlphaEntity extends AbstractEmfObject<TestStandardModuleAlphaEntity> {

	// @formatter:off
	public static final DbObjectTableBuilder<TestStandardModuleAlphaEntity, TestStandardModuleAlphaEntity> BUILDER = new DbObjectTableBuilder<>("Core", TestStandardModuleAlphaEntity.class.getSimpleName(), TestStandardModuleAlphaEntity::new, TestStandardModuleAlphaEntity.class);
	public static final IDbIdField<TestStandardModuleAlphaEntity> ID = BUILDER.addIdField("id", o->o.id, (o,v)->o.id=v);
	public static final IDbForeignRowField<TestStandardModuleAlphaEntity, TestStandardModuleAlphaInstance, AGModuleInstance> MODULE_INSTANCE = BUILDER.addForeignRowField("moduleInstance", o->o.moduleInstance, (o,v)->o.moduleInstance=v, TestStandardModuleAlphaInstance.MODULE_INSTANCE).setNullable().setDefault(null);
	public static final IDbStringField<TestStandardModuleAlphaEntity> NAME = BUILDER.addStringField("name", o->o.name, (o,v)->o.name=v).setMaximumLength(255).setNullable().setDefault(null);
	public static final TestStandardModuleAlphaEntityTable TABLE = new TestStandardModuleAlphaEntityTable(BUILDER);
	// @formatter:on

	private Integer id;
	private TestStandardModuleAlphaInstance moduleInstance;
	private String name;

	@Override
	public IDisplayString toDisplayWithoutId() {

		return new DisplayString().append(getValue(NAME));
	}

	@Override
	public TestStandardModuleAlphaEntityTable table() {

		return TABLE;
	}
}
