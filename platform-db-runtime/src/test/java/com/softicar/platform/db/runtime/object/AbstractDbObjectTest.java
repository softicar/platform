package com.softicar.platform.db.runtime.object;

import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.runtime.test.AbstractDbTest;
import com.softicar.platform.db.sql.Sql;

public class AbstractDbObjectTest extends AbstractDbTest {

	protected static final Integer NON_EXISTING_ID = 1337;

	public AbstractDbObjectTest() {

		DbTinyTestObject.TABLE.createTable();
	}

	protected <R, V> V selectValue(IDbField<R, V> field, int id) {

		return Sql//
			.from(field.getTable())
			.select(field)
			.where(field.getTable().getPrimaryKey().getIdField().isEqual(id))
			.getOne();
	}

	protected Integer insertTinyObjectRow(String string) {

		return DbTinyTestObject.TABLE//
			.createInsert()
			.set(DbTinyTestObject.STRING_FIELD, string)
			.execute();
	}

	protected Integer insertObjectRow(String string) {

		return DbTestObject.TABLE//
			.createInsert()
			.set(DbTestObject.STRING_FIELD, string)
			.execute();
	}

	protected void updateObjectRow(int id, String string) {

		DbTestObject.TABLE//
			.createUpdate()
			.set(DbTestObject.STRING_FIELD, string)
			.where(DbTestObject.ID_FIELD.isEqual(id))
			.execute();
	}

	protected void updateObjectRowSetForeignId(int id, Integer foreignId) {

		DbTestObject.TABLE//
			.createUpdate()
			.set(DbTestObject.FOREIGN_FIELD, DbTestObject.TABLE.getStub(foreignId))
			.where(DbTestObject.ID_FIELD.isEqual(id))
			.execute();
	}

	protected void deleteObjectRow(int id) {

		DbTestObject.TABLE//
			.createDelete()
			.where(DbTestObject.ID_FIELD.isEqual(id))
			.execute();
	}

	protected DbTestObject insertObject(String string) {

		DbTestObject object = new DbTestObject();
		object.setString(string);
		object.save();
		return object;
	}

	protected DbTestObject insertObject(DbTestObject foreign) {

		DbTestObject object = new DbTestObject();
		object.setForeign(foreign);
		object.save();
		return object;
	}
}
