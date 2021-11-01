package com.softicar.platform.db.core;

import com.softicar.platform.db.core.statement.DbStatement;
import com.softicar.platform.db.core.test.AbstractDbCoreTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.mockito.Mockito;

public class DbResultSetIteratorTest extends AbstractDbCoreTest {

	public DbResultSetIteratorTest() {

		new DbStatement()//
			.addText("CREATE TABLE Foo (bar INT)")
			.execute();
	}

	@Test
	public void testClosesResultSetOnLastItem() {

		new DbStatement()//
			.addText("INSERT INTO Foo (bar) VALUES (7)")
			.executeUpdate();

		try (DbResultSet resultSet = new DbStatement().addText("SELECT bar FROM Foo").executeQuery()) {
			try (DbResultSet resultSetSpy = Mockito.spy(resultSet)) {
				DbResultSetIterator<Integer> iterator = new DbResultSetIterator<>(() -> resultSetSpy, rs -> rs.getInteger(1));
				while (iterator.hasNext()) {
					iterator.next();
				}
				Mockito.verify(resultSetSpy).close();
			}
		}
	}

	@Test
	public void testLoadingOfNullValues() {

		new DbStatement()//
			.addText("INSERT INTO Foo (bar) VALUES (7), (NULL), (13)")
			.executeUpdate();

		try (DbResultSet resultSet = new DbStatement().addText("SELECT bar FROM Foo").executeQuery()) {
			List<Integer> values = new ArrayList<>();
			DbResultSetIterator<Integer> iterator = new DbResultSetIterator<>(() -> resultSet, rs -> rs.getInteger(1));
			while (iterator.hasNext()) {
				values.add(iterator.next());
			}

			assertEquals(3, values.size());
			assertEquals(Integer.valueOf(7), values.get(0));
			assertNull(values.get(1));
			assertEquals(Integer.valueOf(13), values.get(2));
		}
	}
}
