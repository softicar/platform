package com.softicar.platform.core.module.test.module.standard.beta;

import com.softicar.platform.common.core.i18n.DisplayString;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.access.module.instance.AGModuleInstance;
import com.softicar.platform.db.runtime.field.IDbForeignRowField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.emf.object.AbstractEmfObject;

public class TestStandardModuleBetaEntity extends AbstractEmfObject<TestStandardModuleBetaEntity> {

	// @formatter:off
	public static final DbObjectTableBuilder<TestStandardModuleBetaEntity, TestStandardModuleBetaEntity> BUILDER = new DbObjectTableBuilder<>("Core", TestStandardModuleBetaEntity.class.getSimpleName(), TestStandardModuleBetaEntity::new, TestStandardModuleBetaEntity.class);
	public static final IDbIdField<TestStandardModuleBetaEntity> ID = BUILDER.addIdField("id", o->o.id, (o,v)->o.id=v);
	public static final IDbForeignRowField<TestStandardModuleBetaEntity, TestStandardModuleBetaInstance, AGModuleInstance> MODULE_INSTANCE = BUILDER.addForeignRowField("moduleInstance", o->o.moduleInstance, (o,v)->o.moduleInstance=v, TestStandardModuleBetaInstance.ID).setNullable().setDefault(null);
	public static final IDbStringField<TestStandardModuleBetaEntity> NAME = BUILDER.addStringField("name", o->o.name, (o,v)->o.name=v).setMaximumLength(255).setNullable().setDefault(null);
	public static final TestStandardModuleBetaEntityTable TABLE = new TestStandardModuleBetaEntityTable(BUILDER);
	// @formatter:on

	private Integer id;
	private TestStandardModuleBetaInstance moduleInstance;
	private String name;

	@Override
	public IDisplayString toDisplayWithoutId() {

		return new DisplayString().append(getValue(NAME));
	}

	@Override
	public TestStandardModuleBetaEntityTable table() {

		return TABLE;
	}
}
