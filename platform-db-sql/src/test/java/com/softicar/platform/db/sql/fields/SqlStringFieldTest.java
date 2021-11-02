package com.softicar.platform.db.sql.fields;

import static org.mockito.Mockito.mock;
import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.db.sql.ISqlTable;
import com.softicar.platform.db.sql.Sql;
import com.softicar.platform.db.sql.test.AbstractSqlBuildableTest;
import com.softicar.platform.db.sql.type.SqlValueTypes;
import org.junit.Test;

public class SqlStringFieldTest extends AbstractSqlBuildableTest {

	private static interface Table {
		// nothing to add
	}

	private static final ISqlTable<Table> TABLE = CastUtils.cast(mock(ISqlTable.class));
	private static final ISqlStringField<Table> FOO = new SqlStringTestField<>(TABLE, "foo", 42);
	private static final ISqlStringField<Table> BAR = new SqlStringTestField<>(TABLE, "bar", 1337);
	private static final String VALUE = "foo";

	// ---------------------------------------- BASIC FIELD ---------------------------------------- //

	@Test
	public void getName() {

		assertEquals("foo", FOO.getName());
		assertEquals("bar", BAR.getName());
	}

	@Test
	public void getIndex() {

		assertEquals(42, FOO.getIndex());
		assertEquals(1337, BAR.getIndex());
	}

	@Test
	public void toStringReturnsName() {

		assertEquals(FOO.getName(), FOO.toString());
	}

	@Test
	public void getColumnCountReturnOne() {

		assertEquals(1, FOO.getColumnCount());
	}

	@Test
	public void getValueType() {

		assertSame(SqlValueTypes.STRING, FOO.getValueType());
	}

	@Test
	public void getTable() {

		assertSame(TABLE, FOO.getTable());
	}

	@Test
	public void testIsIn() {

		build(FOO.isIn("aaa", "bbb", "ccc"));

		assertParameters("aaa", "bbb", "ccc");
		assertText("(.`foo` IN (?,?,?))");
	}

	@Test
	public void testIsNotIn() {

		build(FOO.isNotIn("aaa", "bbb", "ccc"));

		assertParameters("aaa", "bbb", "ccc");
		assertText("(.`foo` NOT IN (?,?,?))");
	}

	// ---------------------------------------- BASIC VALUE FIELD ---------------------------------------- //

	@Test
	public void testIsEqualWithFieldAndNull() {

		build(FOO.isEqual((String) null));

		assertParameters();
		assertText("(.`foo` IS NULL)");
	}

	@Test
	public void testIsNotEqualWithFieldAndNull() {

		build(FOO.isNotEqual((String) null));

		assertParameters();
		assertText("(.`foo` IS NOT NULL)");
	}

	@Test
	public void testIsEqualWithFieldLiteral() {

		build(FOO.isEqual(VALUE));

		assertParameters(VALUE);
		assertText("(.`foo` = ?)");
	}

	@Test
	public void testIsLessWithFieldAndLiteral() {

		build(FOO.isLess(VALUE));

		assertParameters(VALUE);
		assertText("(.`foo` < ?)");
	}

	@Test
	public void testIsGreaterEqualWithFieldAndLiteral() {

		build(FOO.isGreaterEqual(VALUE));

		assertParameters(VALUE);
		assertText("(.`foo` >= ?)");
	}

	@Test
	public void testIsEqualWithLiteralAndField() {

		build(Sql.literal(VALUE).isEqual(FOO));

		assertParameters(VALUE);
		assertText("(? = .`foo`)");
	}

	@Test
	public void testIsEqualWithFieldAndField() {

		build(FOO.isEqual(BAR));

		assertText("(.`foo` = .`bar`)");
	}

	// ---------------------------------------- isLike() and isNotLike() ---------------------------------------- //

	@Test
	public void testIsLikeWithFieldAndLiteral() {

		build(FOO.isLike(VALUE));

		assertParameters(VALUE);
		assertText("(.`foo` LIKE ?)");
	}

	@Test
	public void testIsLikeWithFieldAndField() {

		build(BAR.isLike(FOO));

		assertText("(.`bar` LIKE .`foo`)");
	}

	@Test
	public void testIsNotLikeWithFieldAndField() {

		build(BAR.isNotLike(FOO));

		assertText("(.`bar` NOT LIKE .`foo`)");
	}

	// ---------------------------------------- isRegexp() and isNotRegexp() ---------------------------------------- //

	@Test
	public void testIsRegexpWithFieldAndLiteral() {

		build(FOO.isRegexp(VALUE));

		assertParameters(VALUE);
		assertText("(.`foo` REGEXP ?)");
	}

	@Test
	public void testIsRegexpWithFieldAndField() {

		build(BAR.isRegexp(FOO));

		assertText("(.`bar` REGEXP .`foo`)");
	}

	@Test
	public void testIsNotRegexpWithFieldAndField() {

		build(BAR.isNotRegexp(FOO));

		assertText("(.`bar` NOT REGEXP .`foo`)");
	}
}
