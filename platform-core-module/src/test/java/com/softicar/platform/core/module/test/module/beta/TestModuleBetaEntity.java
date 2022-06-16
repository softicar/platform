package com.softicar.platform.core.module.test.module.beta;

import com.softicar.platform.common.core.i18n.DisplayString;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.module.instance.AGModuleInstance;
import com.softicar.platform.db.runtime.field.IDbForeignRowField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.emf.object.AbstractEmfObject;

public class TestModuleBetaEntity extends AbstractEmfObject<TestModuleBetaEntity> {

	// @formatter:off
	public static final DbObjectTableBuilder<TestModuleBetaEntity, TestModuleBetaEntity> BUILDER = new DbObjectTableBuilder<>("Core", TestModuleBetaEntity.class.getSimpleName(), TestModuleBetaEntity::new, TestModuleBetaEntity.class);
	public static final IDbIdField<TestModuleBetaEntity> ID = BUILDER.addIdField("id", o->o.id, (o,v)->o.id=v);
	public static final IDbForeignRowField<TestModuleBetaEntity, TestModuleBetaInstance, AGModuleInstance> MODULE_INSTANCE = BUILDER.addForeignRowField("moduleInstance", o->o.moduleInstance, (o,v)->o.moduleInstance=v, TestModuleBetaInstance.ID).setNullable().setDefault(null);
	public static final IDbStringField<TestModuleBetaEntity> NAME = BUILDER.addStringField("name", o->o.name, (o,v)->o.name=v).setMaximumLength(255).setNullable().setDefault(null);
	public static final TestModuleBetaEntityTable TABLE = new TestModuleBetaEntityTable(BUILDER);
	// @formatter:on

	private Integer id;
	private TestModuleBetaInstance moduleInstance;
	private String name;

	@Override
	public IDisplayString toDisplayWithoutId() {

		return new DisplayString().append(getValue(NAME));
	}

	@Override
	public TestModuleBetaEntityTable table() {

		return TABLE;
	}
}
