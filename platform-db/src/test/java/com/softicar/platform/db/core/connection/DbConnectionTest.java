package com.softicar.platform.db.core.connection;

import com.softicar.platform.db.core.DbResultSet;
import com.softicar.platform.db.core.SofticarSqlException;
import com.softicar.platform.db.core.statement.DbStatement;
import com.softicar.platform.db.core.test.AbstractDbCoreTest;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class DbConnectionTest extends AbstractDbCoreTest {

	private static final String INSERT = "INSERT INTO foo (name, someValue) VALUES (?, ?)";
	private static final String MULTI_INSERT = "INSERT INTO foo (name, someValue) VALUES (?, ?), (?, ?), (?, ?)";
	private final IDbConnection connection;

	public DbConnectionTest() {

		this.connection = DbConnections.getConnection();

		createTestTable();
	}

	@Test
	public void testExecuteUncachedWithoutParameters() {

		connection.executeInsert(new DbStatement(INSERT).addParameter("foo").addParameter(5));
		connection.executeUncached(new DbStatement("DELETE FROM foo"));
		assertRows();
	}

	@Test
	public void testExecuteUncachedWithParameters() {

		connection.executeUncached(new DbStatement(INSERT).addParameter("foo").addParameter(5));
		connection.executeUncached(new DbStatement(INSERT).addParameter("bar").addParameter(7));

		assertRows(//
			Arrays.asList(1, "foo", 5),
			Arrays.asList(2, "bar", 7));
	}

	@Test
	public void testExecuteInsert() {

		int id1 = connection.executeInsert(new DbStatement(INSERT).addParameter("foo").addParameter(11));
		int id2 = connection.executeInsert(new DbStatement(INSERT).addParameter("bar").addParameter(13));
		int id3 = connection.executeInsert(new DbStatement(INSERT).addParameter("baz").addParameter(17));

		assertEquals(1, id1);
		assertEquals(2, id2);
		assertEquals(3, id3);

		assertRows(//
			Arrays.asList(1, "foo", 11),
			Arrays.asList(2, "bar", 13),
			Arrays.asList(3, "baz", 17));
	}

	@Test
	public void testExecuteMultiRowInsert() {

		List<Integer> ids1 = connection.executeMultiRowInsert(new DbStatement(MULTI_INSERT).addParameters(Arrays.asList("foo", 11, "faa", 13, "fee", 17)));
		List<Integer> ids2 = connection.executeMultiRowInsert(new DbStatement(MULTI_INSERT).addParameters(Arrays.asList("boo", 11, "baa", 13, "bee", 17)));

		assertEquals(3, ids1.size());
		assertEquals(1, ids1.get(0));
		assertEquals(2, ids1.get(1));
		assertEquals(3, ids1.get(2));

		assertEquals(3, ids2.size());
		assertEquals(4, ids2.get(0));
		assertEquals(5, ids2.get(1));
		assertEquals(6, ids2.get(2));

		assertRows(//
			Arrays.asList(1, "foo", 11),
			Arrays.asList(2, "faa", 13),
			Arrays.asList(3, "fee", 17),
			Arrays.asList(4, "boo", 11),
			Arrays.asList(5, "baa", 13),
			Arrays.asList(6, "bee", 17));
	}

	private void assertRows(List<?>...expectedRows) {

		List<List<Object>> rows = new ArrayList<>();
		try (DbResultSet resultSet = connection.executeQuery(new DbStatement("SELECT * FROM foo"))) {
			while (resultSet.next()) {
				int columnCount = resultSet.getMetaData().getColumnCount();
				List<Object> row = new ArrayList<>();
				for (int i = 1; i <= columnCount; i++) {
					row.add(resultSet.get(i));
				}
				rows.add(row);
			}
		} catch (SQLException exception) {
			throw new SofticarSqlException(exception);
		}
		assertEquals(expectedRows.length, rows.size());
		for (int i = 0; i < expectedRows.length; i++) {
			assertEquals(expectedRows[i], rows.get(i));
		}
	}

	private void createTestTable() {

		connection
			.execute(
				new DbStatement()//
					.addText("CREATE TABLE foo (")
					.addText("	id INT NOT NULL AUTO_INCREMENT,")
					.addText("	name VARCHAR(255),")
					.addText("	someValue INT,")
					.addText("	PRIMARY KEY (id))"));
	}
}
