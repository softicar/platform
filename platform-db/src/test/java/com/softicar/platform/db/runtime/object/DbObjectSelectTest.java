package com.softicar.platform.db.runtime.object;

import java.util.List;
import org.junit.Test;

public class DbObjectSelectTest extends AbstractDbObjectTest {

	private final Integer fooId;
	private final Integer barId;
	private final Integer bazId;

	public DbObjectSelectTest() {

		this.fooId = insertObjectRow("foo");
		this.barId = insertObjectRow("bar");
		this.bazId = insertObjectRow("baz");
	}

	// -------------------- equal null -------------------- //

	@Test
	public void testEqualNull() {

		Integer nullId = insertObjectRow(null);

		List<DbTestObject> list = DbTestObject.TABLE
			.createSelect()//
			.where(DbTestObject.STRING_FIELD.isEqual((String) null))
			.list();

		assertEquals(1, list.size());
		assertEquals(nullId, list.get(0).getId());
	}

	@Test
	public void testNotEqualNull() {

		insertObjectRow(null);

		List<DbTestObject> list = DbTestObject.TABLE
			.createSelect()//
			.where(DbTestObject.STRING_FIELD.isNotEqual((String) null))
			.list();

		assertEquals(3, list.size());
	}

	// -------------------- like -------------------- //

	@Test
	public void testLike() {

		List<DbTestObject> list = DbTestObject.TABLE
			.createSelect()//
			.where(DbTestObject.STRING_FIELD.isLike("b%"))
			.orderBy(DbTestObject.STRING_FIELD)
			.list();

		assertEquals(2, list.size());
		assertEquals(barId, list.get(0).getId());
		assertEquals(bazId, list.get(1).getId());
	}

	@Test
	public void testNotLike() {

		List<DbTestObject> list = DbTestObject.TABLE
			.createSelect()//
			.where(DbTestObject.STRING_FIELD.isNotLike("b%"))
			.orderBy(DbTestObject.STRING_FIELD)
			.list();

		assertEquals(1, list.size());
		assertEquals(fooId, list.get(0).getId());
	}

	// -------------------- in -------------------- //

	@Test
	public void testIn() {

		List<DbTestObject> list = DbTestObject.TABLE
			.createSelect()//
			.where(DbTestObject.ID_FIELD.isIn(fooId, bazId))
			.orderBy(DbTestObject.INTEGER_FIELD)
			.list();

		assertEquals(2, list.size());
		assertEquals(fooId, list.get(0).getId());
		assertEquals(bazId, list.get(1).getId());
	}

	@Test
	public void testNotIn() {

		List<DbTestObject> list = DbTestObject.TABLE
			.createSelect()//
			.where(DbTestObject.ID_FIELD.isNotIn(fooId, bazId))
			.orderBy(DbTestObject.INTEGER_FIELD)
			.list();

		assertEquals(1, list.size());
		assertEquals(barId, list.get(0).getId());
	}

	@Test
	public void testInEmptyList() {

		List<DbTestObject> list = DbTestObject.TABLE
			.createSelect()//
			.where(DbTestObject.ID_FIELD.isIn())
			.orderBy(DbTestObject.INTEGER_FIELD)
			.list();

		assertTrue(list.isEmpty());
	}

	@Test
	public void testNotInEmptyList() {

		List<DbTestObject> list = DbTestObject.TABLE
			.createSelect()//
			.where(DbTestObject.ID_FIELD.isNotIn())
			.orderBy(DbTestObject.INTEGER_FIELD)
			.list();

		assertEquals(3, list.size());
		assertEquals(fooId, list.get(0).getId());
		assertEquals(barId, list.get(1).getId());
		assertEquals(bazId, list.get(2).getId());
	}

	// -------------------- order by -------------------- //

	@Test
	public void testOrderBy() {

		List<DbTestObject> list = DbTestObject.TABLE
			.createSelect()//
			.orderBy(DbTestObject.STRING_FIELD)
			.list();

		assertEquals(3, list.size());
		assertEquals(barId, list.get(0).getId());
		assertEquals(bazId, list.get(1).getId());
		assertEquals(fooId, list.get(2).getId());
	}

	@Test
	public void testOrderDescendingBy() {

		List<DbTestObject> list = DbTestObject.TABLE
			.createSelect()//
			.orderDescendingBy(DbTestObject.STRING_FIELD)
			.list();

		assertEquals(3, list.size());
		assertEquals(fooId, list.get(0).getId());
		assertEquals(bazId, list.get(1).getId());
		assertEquals(barId, list.get(2).getId());
	}
}
