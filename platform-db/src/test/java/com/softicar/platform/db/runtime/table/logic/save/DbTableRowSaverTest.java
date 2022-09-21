package com.softicar.platform.db.runtime.table.logic.save;

import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.db.core.connection.DbConnections;
import com.softicar.platform.db.core.connection.profiler.DbConnectionTestProfiler;
import com.softicar.platform.db.core.transaction.DbTransaction;
import com.softicar.platform.db.runtime.object.AbstractDbObjectTest;
import com.softicar.platform.db.runtime.object.DbTinyTestObject;
import com.softicar.platform.db.runtime.table.listener.DbListeningTestObject;
import com.softicar.platform.db.runtime.table.listener.DbTestTableListener;
import com.softicar.platform.db.runtime.table.listener.IDbTableListener;
import com.softicar.platform.db.runtime.table.listener.IDbTableRowNotificationSet;
import org.junit.Test;

public class DbTableRowSaverTest extends AbstractDbObjectTest {

	private static final String A = "A";
	private static final String B = "B";
	private static final String C = "C";
	private static final String X = "X";
	private final DbConnectionTestProfiler profiler;
	private final DbTestTableListener listener;
	private final DbListeningTestObject objectA;
	private final DbListeningTestObject objectB;
	private final DbListeningTestObject objectC;

	public DbTableRowSaverTest() {

		this.profiler = new DbConnectionTestProfiler();
		this.listener = DbListeningTestObject.TABLE.getListener();
		this.objectA = DbListeningTestObject.TABLE.createObject().setString(A);
		this.objectB = DbListeningTestObject.TABLE.createObject().setString(B);
		this.objectC = DbListeningTestObject.TABLE.createObject().setString(C);

		DbListeningTestObject.TABLE.createTable();
		DbConnections.setProfiler(profiler);
	}

	@Test
	public void testWithEmptyList() {

		int writtenRows = createSaver().save();

		listener.assertNotifications("");
		profiler.assertStatementCount(0);

		assertEquals(0, writtenRows);
	}

	/**
	 * Case:
	 * <ol>
	 * <li>We call {@link DbTableRowSaver#save} without transaction.</li>
	 * <li>The table has table listeners.</li>
	 * </ol>
	 * <p>
	 * We assert that an implicit transaction is automatically opened by
	 * {@link DbTableRowSaver}. We verify that the commit listeners are called
	 * correctly.
	 */
	@Test
	public void testSaveWithoutTransactionAndWithTableListener() {

		int writtenRows = createSaver()//
			.addRow(objectA)
			.addRow(objectB)
			.save();

		// assert notifications
		listener.assertNotifications("""
				beforeSave(A,B)
				HOOK
				afterSave(A,B)
				beforeCommit(SAVE-A,SAVE-B)
				afterCommit(SAVE-A,SAVE-B)
				""");

		// assert transaction was used
		profiler.assertStatementCount(3);
		profiler.assertStatement(0, "BEGIN");
		profiler.assertStatementMatches(1, "INSERT.*", A, B);
		profiler.assertStatement(2, "COMMIT");

		assertEquals(2, writtenRows);
	}

	/**
	 * Case:
	 * <ol>
	 * <li>We call {@link DbTableRowSaver#save} without transaction.</li>
	 * <li>The table has table listeners.</li>
	 * <li>An exception occurs during save.</li>
	 * </ol>
	 * <p>
	 * We assert that an implicit transaction is automatically opened by
	 * {@link DbTableRowSaver}. We verify that the optional transaction was
	 * correctly reverted.
	 */
	@Test
	public void testSaveWithoutTransactionAndWithTableListenerAndWithException() {

		// adding an extra listener to trigger an exception
		listener.setExtraListener(new IDbTableListener<DbListeningTestObject>() {

			@Override
			public void beforeCommit(IDbTableRowNotificationSet<DbListeningTestObject> notifications) {

				throw new RuntimeException("Test");
			}
		});

		try {
			createSaver().addRow(objectA).addRow(objectB).save();
			fail("Expected exception.");
		} catch (RuntimeException exception) {
			// expected
			DevNull.swallow(exception);
		}

		// assert notifications
		listener.assertNotifications("""
				beforeSave(A,B)
				HOOK
				afterSave(A,B)
				beforeCommit(SAVE-A,SAVE-B)
				""");

		// assert transaction was reverted
		profiler.assertStatementCount(3);
		profiler.assertStatement(0, "BEGIN");
		profiler.assertStatementMatches(1, "INSERT.*", A, B);
		profiler.assertStatement(2, "ROLLBACK");

		// assert no transaction is open
		assertFalse(DbConnections.isTransactionStarted());
	}

	/**
	 * Case:
	 * <ol>
	 * <li>We call {@link DbTableRowSaver#save} without transaction.</li>
	 * <li>The table has <b>no</b> table listeners.</li>
	 * </ol>
	 * <p>
	 * We assert that <b>no</b> implicit transaction is opened.
	 */
	@Test
	public void testSaveWithoutTransactionAndWithoutTableListener() {

		DbTinyTestObject object = DbTinyTestObject.TABLE.createObject();

		int writtenRows = new DbTableRowSaver<>(DbTinyTestObject.TABLE)//
			.setFinalizingBeforeSaveHook(() -> listener.addNotification("HOOK"))
			.addRow(object)
			.save();

		// assert hook is still called
		listener.assertNotifications("HOOK");

		// assert no transaction was opened
		profiler.assertStatementCount(1);
		profiler.assertStatementMatches(0, "INSERT.*", null, null);

		assertEquals(1, writtenRows);
	}

	/**
	 * Case:
	 * <ol>
	 * <li>We call {@link DbTableRowSaver#save} within a transaction.</li>
	 * <li>The table has table listeners.</li>
	 * </ol>
	 * <p>
	 * We assert that <b>no</b> redundant implicit transaction is opened. We
	 * verify that the commit listeners are called correctly.
	 */
	@Test
	public void testSaveWithTransactionAndWithTableListener() {

		int writtenRows;
		try (DbTransaction transaction = new DbTransaction()) {
			writtenRows = createSaver().addRow(objectA).addRow(objectB).save();
			transaction.commit();
		}

		listener.assertNotifications("""
				beforeSave(A,B)
				HOOK
				afterSave(A,B)
				beforeCommit(SAVE-A,SAVE-B)
				afterCommit(SAVE-A,SAVE-B)
				""");

		// assert no extra transaction was opened
		profiler.assertStatementCount(3);
		profiler.assertStatement(0, "BEGIN");
		profiler.assertStatementMatches(1, "INSERT.*", A, B);
		profiler.assertStatement(2, "COMMIT");

		assertEquals(2, writtenRows);
	}

	/**
	 * Case:
	 * <ol>
	 * <li>We call {@link DbTableRowSaver#save} within a transaction.</li>
	 * <li>We enable {@link DbTableRowSaver#setLazyMode}.</li>
	 * <li>The table has table listeners.</li>
	 * </ol>
	 * <p>
	 * We assert that <b>only</b> the necessary rows are saved. We verify that
	 * the listeners are called for <b>all</b> table rows.
	 */
	@Test
	public void testSaveWithTransactionAndWithTableListenerAndWithLazyMode() {

		// prepare objects
		objectA.save();
		objectB.save();
		listener.clearNotifications();
		profiler.clear();

		int writtenRows;
		try (DbTransaction transaction = new DbTransaction()) {
			objectB.setString(X);
			writtenRows = createSaver()//
				.setLazyMode(true)
				.addRow(objectA)
				.addRow(objectB)
				.addRow(objectC)
				.save();
			transaction.commit();
		}

		listener.assertNotifications("""
				beforeSave(A,C,X)
				HOOK
				afterSave(A,C,X)
				beforeCommit(SAVE-A,SAVE-C,SAVE-X)
				afterCommit(SAVE-A,SAVE-C,SAVE-X)
				""");

		// assert INSERT and UPDATE was executed
		profiler.assertStatementCount(4);
		profiler.assertStatement(0, "BEGIN");
		profiler.assertStatementMatches(1, "INSERT.*", C);
		profiler.assertStatementMatches(2, "UPDATE.*", X, objectB.getId());
		profiler.assertStatement(3, "COMMIT");

		assertEquals(2, writtenRows);
	}

	/**
	 * Case:
	 * <ol>
	 * <li>We enable {@link DbTableRowSaver#setLazyMode}.</li>
	 * <li>We have rows with dirty flag enabled but without data changed
	 * flag.</li>
	 * </ol>
	 * <p>
	 * We assert that all dirty flags are disabled afterwards.
	 */
	@Test
	public void testSaveWithDirtyObjectAndWithLazyMode() {

		// prepare object
		objectA.setString("A");
		objectA.save();
		objectA.setString("A");
		listener.clearNotifications();
		profiler.clear();

		assertTrue(objectA.dirty());
		int writtenRows = createSaver()//
			.setLazyMode(true)
			.addRow(objectA)
			.save();

		listener.assertNotifications("""
				beforeSave(A)
				HOOK
				afterSave(A)
				beforeCommit(SAVE-A)
				afterCommit(SAVE-A)
				""");
		profiler.assertStatementCount(0);
		assertFalse(objectA.dirty());

		assertEquals(0, writtenRows);
	}

	private DbTableRowSaver<DbListeningTestObject, Integer> createSaver() {

		return new DbTableRowSaver<>(DbListeningTestObject.TABLE)//
			.setFinalizingBeforeSaveHook(() -> listener.addNotification("HOOK"));
	}
}
