package com.softicar.platform.db.runtime.transients;

import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.logic.DbObjectTable;
import com.softicar.platform.db.runtime.logic.IDbObjectTable;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.db.runtime.test.AbstractDbTest;

public class TransientFieldTestBase extends AbstractDbTest {

	protected final TestObject objectA;
	protected final TestObject objectB;
	protected final TestObject objectC;

	public TransientFieldTestBase() {

		this.objectA = new TestObject();
		this.objectB = new TestObject();
		this.objectC = new TestObject();
	}

	protected <V> void setCachedValue(TestObject object, ITransientField<TestObject, V> field, V value) {

		object.table().getCache().getTransientValueCache().setTransientValue(object, field, value);
	}

	protected static class TestObject extends AbstractDbObject<TestObject> {

		private static final DbObjectTableBuilder<TestObject, TestObject> BUILDER = new DbObjectTableBuilder<>("db", "foo", TestObject::new, TestObject.class);
		public static final IDbIdField<TestObject> ID = BUILDER.addIdField("id", o -> o.id, (o, v) -> o.id = v);
		public static final TestObjectTable TABLE = new TestObjectTable(BUILDER);
		private Integer id;

		@Override
		public IDbObjectTable<TestObject> table() {

			return TABLE;
		}
	}

	protected static class TestObjectTable extends DbObjectTable<TestObject> {

		public TestObjectTable(IDbObjectTableBuilder<TestObject> builder) {

			super(builder);
		}
	}
}
