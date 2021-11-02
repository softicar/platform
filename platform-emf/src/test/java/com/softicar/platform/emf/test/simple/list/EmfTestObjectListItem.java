package com.softicar.platform.emf.test.simple.list;

import com.softicar.platform.common.container.tuple.Tuple3;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIntegerField;
import com.softicar.platform.db.runtime.key.DbTableKeyFactory;
import com.softicar.platform.db.runtime.key.IDbTableKey;
import com.softicar.platform.db.runtime.record.AbstractDbRecord;
import com.softicar.platform.db.runtime.record.DbRecordTable;
import com.softicar.platform.db.runtime.record.IDbRecordTable;
import com.softicar.platform.db.runtime.table.DbTableBuilder;
import com.softicar.platform.emf.test.simple.EmfTestObject;

public class EmfTestObjectListItem extends AbstractDbRecord<EmfTestObjectListItem, Tuple3<EmfTestObjectList, EmfTestObject, Integer>> {

	// @formatter:off
	private static final DbTableBuilder<EmfTestObjectListItem, EmfTestObjectListItem, Tuple3<EmfTestObjectList, EmfTestObject, Integer>> BUILDER = new DbTableBuilder<>("Test", "EntityListItem", EmfTestObjectListItem::new, EmfTestObjectListItem.class);
	public static final IDbForeignField<EmfTestObjectListItem, EmfTestObjectList> OBJECT_LIST = BUILDER.addForeignField("entityList", o->o.m_objectList, (o,v)->o.m_objectList=v, EmfTestObjectList.ID).setDefault(null);
	public static final IDbForeignField<EmfTestObjectListItem, EmfTestObject> OBJECT = BUILDER.addForeignField("entity", o->o.m_object, (o,v)->o.m_object=v, EmfTestObject.ID).setDefault(null);
	public static final IDbIntegerField<EmfTestObjectListItem> INDEX = BUILDER.addIntegerField("index", o -> o.m_index, (o, v) -> o.m_index = v).setDefault(null);
	public static final IDbTableKey<EmfTestObjectListItem, Tuple3<EmfTestObjectList, EmfTestObject, Integer>> PRIMARY_KEY = BUILDER.setPrimaryKey(DbTableKeyFactory.createKey(OBJECT_LIST, OBJECT, INDEX));
	public static final DbRecordTable<EmfTestObjectListItem, Tuple3<EmfTestObjectList, EmfTestObject, Integer>> TABLE = new DbRecordTable<>(BUILDER);
	// @formatter:on

	@Override
	public IDbRecordTable<EmfTestObjectListItem, Tuple3<EmfTestObjectList, EmfTestObject, Integer>> table() {

		return TABLE;
	}

	private EmfTestObjectList m_objectList;
	private EmfTestObject m_object;
	private Integer m_index;
}
