package com.softicar.platform.emf.test.module.alpha.entity;

import com.softicar.platform.common.core.i18n.DisplayString;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.emf.object.AbstractEmfObject;
import com.softicar.platform.emf.test.module.alpha.TestModuleAlphaInstance;

public class TestModuleAlphaEntity extends AbstractEmfObject<TestModuleAlphaEntity> {

	// @formatter:off
	public static final DbObjectTableBuilder<TestModuleAlphaEntity, TestModuleAlphaEntity> BUILDER = new DbObjectTableBuilder<>("Core", TestModuleAlphaEntity.class.getSimpleName(), TestModuleAlphaEntity::new, TestModuleAlphaEntity.class);
	public static final IDbIdField<TestModuleAlphaEntity> ID = BUILDER.addIdField("id", o->o.id, (o,v)->o.id=v);
	public static final IDbForeignField<TestModuleAlphaEntity, TestModuleAlphaInstance> MODULE_INSTANCE = BUILDER.addForeignField("moduleInstance", o->o.moduleInstance, (o,v)->o.moduleInstance=v, TestModuleAlphaInstance.ID).setDefault(null);
	public static final IDbStringField<TestModuleAlphaEntity> NAME = BUILDER.addStringField("name", o->o.name, (o,v)->o.name=v).setDefault(null);
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
