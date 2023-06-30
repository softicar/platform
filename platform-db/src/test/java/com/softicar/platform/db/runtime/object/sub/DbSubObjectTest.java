package com.softicar.platform.db.runtime.object.sub;

import com.softicar.platform.db.runtime.entity.IDbEntity;
import com.softicar.platform.db.runtime.table.IDbTable;
import com.softicar.platform.db.runtime.table.logic.DbTableRowFlagsAsserter;
import com.softicar.platform.db.runtime.table.row.DbTableRowFlag;
import com.softicar.platform.db.runtime.test.AbstractDbTest;
import org.junit.Test;

public class DbSubObjectTest extends AbstractDbTest {

	private final DbTestSubSubObject subSubObject;
	private final DbTestSubObject subObject;
	private final DbTestBaseObject baseObject;

	public DbSubObjectTest() {

		this.subSubObject = new DbTestSubSubObject();
		this.subObject = subSubObject.pk();
		this.baseObject = subObject.pk();
	}

	@Test
	public void testNew() {

		assertNotNull(baseObject);
		assertNull(baseObject.getId());
		new DbTableRowFlagsAsserter(baseObject).assertOnlyImpermanent();

		assertNotNull(subObject);
		assertNull(subObject.getId());
		new DbTableRowFlagsAsserter(subObject).assertOnlyImpermanent();

		assertNull(subSubObject.getId());
		new DbTableRowFlagsAsserter(subSubObject).assertOnlyImpermanent();
	}

	// ------------------------------ save ------------------------------ //

	@Test
	public void testSaveOnBase() {

		baseObject.save();

		assertProperlySaved(baseObject);
		assertImpermanent(subObject);
		assertImpermanent(subSubObject);
	}

	@Test
	public void testSaveOnSubObject() {

		subObject.save();

		assertProperlySaved(baseObject);
		assertProperlySaved(subObject);
		assertImpermanent(subSubObject);
	}

	@Test
	public void testSaveOnSubSubObject() {

		subSubObject.save();

		assertProperlySaved(baseObject);
		assertProperlySaved(subObject);
		assertProperlySaved(subSubObject);
	}

	// ------------------------------ save with persistent base ------------------------------ //

	@Test
	public void testSaveOnSubObjectWithBaseSavedFirst() {

		baseObject.save();
		subObject.save();

		assertProperlySaved(baseObject);
		assertProperlySaved(subObject);
		assertImpermanent(subSubObject);
	}

	@Test
	public void testSaveOnSubSubObjectWithBaseSavedFirst() {

		baseObject.save();
		subSubObject.save();

		assertProperlySaved(baseObject);
		assertProperlySaved(subObject);
		assertProperlySaved(subSubObject);
	}

	// ------------------------------ save with stub base ------------------------------ //

	@Test
	public void testSaveOnSubObjectWithBaseAsStub() {

		DbTestBaseObject base = DbTestBaseObject.TABLE.getStub(insertBaseObject());
		insertSubObject(base);
		DbTestSubObject object = DbTestSubObject.TABLE.get(base);

		object.save();

		assertTrue(base.stub());
	}

	// ------------------------------ saveIfNecessary ------------------------------ //

	@Test
	public void testSaveIfNecessaryWithNewObject() {

		subSubObject.saveIfNecessary();

		assertProperlySaved(baseObject);
		assertProperlySaved(subObject);
		assertProperlySaved(subSubObject);
	}

	@Test
	public void testSaveIfNecessaryWithChangedBase() {

		// save base and object
		baseObject.setBaseText("foo");
		subSubObject.save();

		// change only base and keep sub-object untouched
		baseObject.setBaseText("bar");
		assertTrue(!subSubObject.dataChanged() && !subSubObject.impermanent());
		assertTrue(baseObject.dataChanged() && !baseObject.impermanent());

		// lazy saving of sub-object
		subSubObject.saveIfNecessary();

		// assert that base was saved properly
		assertFalse(baseObject.dataChanged());
		assertFalse(baseObject.dirty());
		assertEquals(1, DbTestBaseObject.TABLE.createSelect().where(DbTestBaseObject.BASE_TEXT.isEqual("bar")).count());
	}

	// ------------------------------ delete ------------------------------ //

	@Test
	public void testDeleteOnBase() {

		subSubObject.save();
		subSubObject.pk().pk().delete();

		assertImpermanentAndInvalidated(baseObject);
		assertProperlySavedWithoutTableRow(subObject);
		assertProperlySavedWithoutTableRow(subSubObject);
	}

	@Test
	public void testDeleteOnSubObject() {

		subSubObject.save();
		subSubObject.pk().delete();

		assertImpermanentAndInvalidated(baseObject);
		assertImpermanentAndInvalidated(subObject);
		assertProperlySavedWithoutTableRow(subSubObject);
	}

	@Test
	public void testDeleteOnSubSubObject() {

		subSubObject.save();
		subSubObject.delete();

		assertImpermanentAndInvalidated(baseObject);
		assertImpermanentAndInvalidated(subObject);
		assertImpermanentAndInvalidated(subSubObject);
	}

	// ------------------------------ copy ------------------------------ //

	@Test
	public void testCopyOfSubSubObject() {

		subSubObject.save();
		DbTestSubSubObject copy = subSubObject.copy();

		assertNull(copy.getId());
		assertNull(copy.getItemId());
		new DbTableRowFlagsAsserter(copy).assertOnlyImpermanent();

		assertNotNull(copy.pk());
		assertNotNull(copy.pk().pk());
		assertNotSame(copy.pk(), subSubObject.pk());
		assertNotSame(copy.pk().pk(), subSubObject.pk().pk());
	}

	// ------------------------------ assert ------------------------------ //

	private <T extends IDbEntity<T, ?>> void assertProperlySavedWithoutTableRow(T object) {

		assertHasId(object);
		assertHasSameIdAsBase(object);
		assertNoFlags(object);
	}

	private <T extends IDbEntity<T, ?>> void assertProperlySaved(T object) {

		assertProperlySavedWithoutTableRow(object);
		assertSingleTableRow(object);
	}

	private <T extends IDbEntity<T, ?>> void assertImpermanent(T object) {

		assertHasSameIdAsBase(object);
		assertImpermanentFlag(object);
		assertTableRowCount(object.table(), 0);
	}

	private <T extends IDbEntity<T, ?>> void assertImpermanentAndInvalidated(T object) {

		assertHasSameIdAsBase(object);
		assertImpermanentAndInvalidatedFlags(object);
		assertTableRowCount(object.table(), 0);
	}

	private void assertHasId(IDbEntity<?, ?> entity) {

		assertNotNull(entity.getId());
	}

	private <T extends IDbEntity<T, ?>> void assertHasSameIdAsBase(T entity) {

		entity.table().ifSubObjectTable(table -> {
			assertEquals(entity.getId(), table.getPrimaryKeyField().getValue(entity).getId());
		});
	}

	private <T extends IDbEntity<T, ?>> void assertNoFlags(T object) {

		new DbTableRowFlagsAsserter(object).assertNone();
	}

	private <T extends IDbEntity<T, ?>> void assertImpermanentFlag(T object) {

		new DbTableRowFlagsAsserter(object).assertOnlyImpermanent();
	}

	private <T extends IDbEntity<T, ?>> void assertImpermanentAndInvalidatedFlags(T object) {

		new DbTableRowFlagsAsserter(object).assertOnly(DbTableRowFlag.IMPERMANENT, DbTableRowFlag.INVALIDATED);
	}

	private <T extends IDbEntity<T, ?>> void assertSingleTableRow(T object) {

		assertSame(object, object.table().createSelect().getOne());
	}

	private void assertTableRowCount(IDbTable<?, ?> table, int expectedCount) {

		assertEquals(String.format("Row count of table %s", table.getValueClass().getSimpleName()), expectedCount, table.createSelect().count());
	}

	// ------------------------------ insert ------------------------------ //

	private void insertSubObject(DbTestBaseObject base) {

		DbTestSubObject.TABLE//
			.createInsert()
			.set(DbTestSubObject.BASE_OBJECT, base)
			.executeWithoutIdGeneration();
	}

	private int insertBaseObject() {

		return DbTestBaseObject.TABLE//
			.createInsert()
			.set(DbTestBaseObject.BASE_TEXT, "foo")
			.execute();
	}
}
