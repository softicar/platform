package com.softicar.platform.emf.test.simple.set;

import com.softicar.platform.common.container.tuple.Tuple2;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.key.DbTableKeyFactory;
import com.softicar.platform.db.runtime.key.IDbTableKey;
import com.softicar.platform.db.runtime.record.AbstractDbRecord;
import com.softicar.platform.db.runtime.record.DbRecordTable;
import com.softicar.platform.db.runtime.record.IDbRecordTable;
import com.softicar.platform.db.runtime.table.DbTableBuilder;
import com.softicar.platform.emf.test.simple.EmfTestObject;

public class EmfTestObjectSetItem extends AbstractDbRecord<EmfTestObjectSetItem, Tuple2<EmfTestObjectSet, EmfTestObject>> {

	// @formatter:off
	private static final DbTableBuilder<EmfTestObjectSetItem, EmfTestObjectSetItem, Tuple2<EmfTestObjectSet, EmfTestObject>> BUILDER = new DbTableBuilder<>("Test", "EntitySetItem", EmfTestObjectSetItem::new, EmfTestObjectSetItem.class);
	public static final IDbForeignField<EmfTestObjectSetItem, EmfTestObjectSet> OBJECT_SET = BUILDER.addForeignField("entitySet", o->o.m_objectSet, (o,v)->o.m_objectSet=v, EmfTestObjectSet.ID).setDefault(null);
	public static final IDbForeignField<EmfTestObjectSetItem, EmfTestObject> OBJECT = BUILDER.addForeignField("entity", o->o.m_object, (o,v)->o.m_object=v, EmfTestObject.ID).setDefault(null);
	public static final IDbTableKey<EmfTestObjectSetItem, Tuple2<EmfTestObjectSet, EmfTestObject>> PRIMARY_KEY = BUILDER.setPrimaryKey(DbTableKeyFactory.createKey(OBJECT_SET, OBJECT));
	public static final DbRecordTable<EmfTestObjectSetItem, Tuple2<EmfTestObjectSet, EmfTestObject>> TABLE = new DbRecordTable<>(BUILDER);
	// @formatter:on

	@Override
	public IDbRecordTable<EmfTestObjectSetItem, Tuple2<EmfTestObjectSet, EmfTestObject>> table() {

		return TABLE;
	}

	private EmfTestObjectSet m_objectSet;
	private EmfTestObject m_object;
}
