package com.softicar.platform.db.runtime.table.logic;

import com.softicar.platform.db.core.connection.DbConnections;
import com.softicar.platform.db.core.connection.profiler.DbConnectionTestProfiler;
import com.softicar.platform.db.core.transaction.DbTransaction;
import com.softicar.platform.db.runtime.exception.DbException;
import com.softicar.platform.db.runtime.object.AbstractDbObjectTest;
import com.softicar.platform.db.runtime.object.DbTestObject;
import com.softicar.platform.db.runtime.table.listener.DbListeningTestObject;
import com.softicar.platform.db.runtime.table.listener.DbTestTableListener;
import java.util.Arrays;
import org.junit.Test;

public class DbTableRowDeleterTest extends AbstractDbObjectTest {

	private static final String A = "A";
	private static final String B = "B";
	private final DbTestTableListener listener;
	private final DbListeningTestObject objectA;
	private final DbListeningTestObject objectB;
	private final DbConnectionTestProfiler profiler;

	public DbTableRowDeleterTest() {

		this.listener = DbListeningTestObject.TABLE.getListener();
		this.objectA = DbListeningTestObject.TABLE.createObject().setString(A);
		this.objectB = DbListeningTestObject.TABLE.createObject().setString(B);

		DbListeningTestObject.TABLE.saveAll(Arrays.asList(objectA, objectB));
		listener.clearNotifications();

		this.profiler = new DbConnectionTestProfiler();
		DbConnections.setProfiler(profiler);
	}

	@Test
	public void testDeleteWithoutTransactionAndWithTableListener() {

		new DbTableRowDeleter<>(DbListeningTestObject.TABLE).addRow(objectA).delete();

		// assert notifications
		listener.assertNotifications("""
				beforeDelete(A)
				afterDelete(A)
				beforeCommit(DELETE-A)
				afterCommit(DELETE-A)
				""");

		// assert transaction was used
		profiler.assertStatementCount(3);
		profiler.assertStatement(0, "BEGIN");
		verifyDelete(1, objectA.getId());
		profiler.assertStatement(2, "COMMIT");
	}

	@Test
	public void testDeleteWithoutTransactionAndWithoutTableListener() {

		DbTestObject object = DbTestObject.TABLE.createObject();
		object.save();

		profiler.clear();
		new DbTableRowDeleter<>(DbTestObject.TABLE).addRow(object).delete();

		// assert no transaction was used
		profiler.assertStatementCount(1);
		verifyDelete(0, object.getId());
	}

	@Test
	public void testDeleteWithTableListenerAndTransaction() {

		try (DbTransaction transaction = new DbTransaction()) {
			new DbTableRowDeleter<>(DbListeningTestObject.TABLE).addRow(objectA).delete();
			transaction.commit();
		}

		// assert notifications
		listener.assertNotifications("""
				beforeDelete(A)
				afterDelete(A)
				beforeCommit(DELETE-A)
				afterCommit(DELETE-A)
				""");

		// assert transaction was used
		profiler.assertStatementCount(3);
		profiler.assertStatement(0, "BEGIN");
		verifyDelete(1, objectA.getId());
		profiler.assertStatement(2, "COMMIT");
	}

	@Test(expected = DbException.class)
	public void testDeleteWithImpermanentObject() {

		new DbTableRowDeleter<>(DbTestObject.TABLE).addRow(new DbTestObject()).delete();
	}

	private void verifyDelete(int index, Integer...ids) {

		profiler.assertStatementMatches(index, "DELETE.*", Arrays.asList(ids));
	}
}
