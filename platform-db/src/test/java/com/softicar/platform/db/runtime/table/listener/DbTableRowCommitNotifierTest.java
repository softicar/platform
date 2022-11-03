package com.softicar.platform.db.runtime.table.listener;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.db.core.transaction.DbTransaction;
import com.softicar.platform.db.runtime.object.AbstractDbObjectTest;
import java.util.List;
import org.junit.Test;

public class DbTableRowCommitNotifierTest extends AbstractDbObjectTest {

	private static final String A = "A";
	private static final String B = "B";
	private static final String C = "C";
	private final DbTestTableListener listener;
	private final DbListeningTestObject objectA;
	private final DbListeningTestObject objectB;
	private final DbListeningTestObject objectC;

	public DbTableRowCommitNotifierTest() {

		DbListeningTestObject.TABLE.createTable();

		this.listener = DbListeningTestObject.TABLE.getListener();
		this.objectA = DbListeningTestObject.TABLE.createObject().setString(A);
		this.objectB = DbListeningTestObject.TABLE.createObject().setString(B);
		this.objectC = DbListeningTestObject.TABLE.createObject().setString(C);
	}

	@Test
	public void test() {

		objectC.save();

		try (DbTransaction rootTransaction = new DbTransaction()) {
			try (DbTransaction childTransaction = new DbTransaction()) {
				objectA.save();
				objectC.reload();
				objectC.delete();
				childTransaction.commit();
			}
			objectB.save();
			rootTransaction.commit();
		}

		try (DbTransaction rootTransaction = new DbTransaction()) {
			DbListeningTestObject.TABLE.saveAll(List.of(objectA, objectB));
			rootTransaction.commit();
		}

		listener.assertNotifications("""
				beforeSave(C)
				afterSave(C)
				beforeCommit(CHANGE-C,SAVE-C)
				afterCommit(CHANGE-C,SAVE-C)
				beforeSave(A)
				afterSave(A)
				beforeDelete(C)
				afterDelete(C)
				beforeSave(B)
				afterSave(B)
				beforeCommit(CHANGE-A,CHANGE-B,CHANGE-C,DELETE-C,SAVE-A,SAVE-B)
				afterCommit(CHANGE-A,CHANGE-B,CHANGE-C,DELETE-C,SAVE-A,SAVE-B)
				beforeSave(A,B)
				afterSave(A,B)
				beforeCommit(SAVE-A,SAVE-B)
				afterCommit(SAVE-A,SAVE-B)
				""");
	}

	@Test
	public void testWithRollbackOfChildTransaction() {

		try (DbTransaction rootTransaction = new DbTransaction()) {
			try (DbTransaction childTransaction = new DbTransaction()) {
				objectA.save();
				childTransaction.rollback();
			}
			objectB.save();
			rootTransaction.commit();
		}

		listener.assertNotifications("""
				beforeSave(A)
				afterSave(A)
				beforeSave(B)
				afterSave(B)
				beforeCommit(CHANGE-B,SAVE-B)
				afterCommit(CHANGE-B,SAVE-B)
				""");
	}

	@Test
	public void testWithInsertOfNewObjectsByBeforeCommitHandler() {

		listener.setExtraListener(new IDbTableListener<DbListeningTestObject>() {

			@Override
			public void beforeCommit(IDbTableRowNotificationSet<DbListeningTestObject> notifications) {

				if (objectB.getId() == null) {
					objectB.save();
				} else if (objectC.getId() == null) {
					objectC.save();
				}
			}
		});

		try (DbTransaction rootTransaction = new DbTransaction()) {
			objectA.save();
			rootTransaction.commit();
		}

		assertNotNull(objectA.getId());
		assertNotNull(objectB.getId());
		assertNotNull(objectC.getId());

		listener.assertNotifications("""
				beforeSave(A)
				afterSave(A)
				beforeCommit(CHANGE-A,SAVE-A)
				beforeSave(B)
				afterSave(B)
				beforeCommit(CHANGE-B,SAVE-B)
				beforeSave(C)
				afterSave(C)
				beforeCommit(CHANGE-C,SAVE-C)
				afterCommit(CHANGE-A,CHANGE-B,CHANGE-C,SAVE-A,SAVE-B,SAVE-C)
				""");
	}

	@Test
	public void testWithSavingSameObject() {

		listener.setExtraListener(new IDbTableListener<DbListeningTestObject>() {

			@Override
			public void beforeCommit(IDbTableRowNotificationSet<DbListeningTestObject> notifications) {

				objectA.save();
			}
		});

		try (DbTransaction rootTransaction = new DbTransaction()) {
			objectA.save();
			rootTransaction.commit();
		}

		assertNotNull(objectA.getId());

		listener.assertNotifications("""
				beforeSave(A)
				afterSave(A)
				beforeCommit(CHANGE-A,SAVE-A)
				beforeSave(A)
				afterSave(A)
				afterCommit(CHANGE-A,SAVE-A)
					""");
	}

	@Test
	public void testWithReloadingObjects() {

		objectA.save();
		objectB.save();
		listener.clearNotifications();

		try (DbTransaction rootTransaction = new DbTransaction()) {
			DbListeningTestObject.TABLE//
				.createUpdate()
				.set(DbListeningTestObject.STRING_FIELD, "X")
				.where(DbListeningTestObject.ID_FIELD.isEqual(objectB))
				.execute();
			objectA.reload();
			objectB.reload();
			rootTransaction.commit();
		}

		listener.assertNotifications("""
				beforeCommit(CHANGE-X)
				afterCommit(CHANGE-X)
				""");
	}

	@Test
	public void testWithSavingChangedAndUnchangedObjects() {

		objectA.save();
		objectB.save();
		listener.clearNotifications();

		try (DbTransaction rootTransaction = new DbTransaction()) {
			objectA.setString("X");
			objectA.save();
			objectB.save();
			rootTransaction.commit();
		}

		listener.assertNotifications("""
				beforeSave(X)
				afterSave(X)
				beforeSave(B)
				afterSave(B)
				beforeCommit(CHANGE-X,SAVE-B,SAVE-X)
				afterCommit(CHANGE-X,SAVE-B,SAVE-X)
				""");
	}

	@Test
	public void testWithLoadingStubObjects() {

		var id = DbListeningTestObject.TABLE//
			.createInsert()
			.set(DbListeningTestObject.STRING_FIELD, "X")
			.execute();
		var objectX = DbListeningTestObject.TABLE.getStub(id);
		listener.clearNotifications();

		try (DbTransaction rootTransaction = new DbTransaction()) {
			objectX.getString();
			rootTransaction.commit();
		}

		listener.assertNotifications("""
				""");
	}

	@Test
	public void testWithInfiniteSaveInListener() {

		try {
			listener.setExtraListener(new IDbTableListener<DbListeningTestObject>() {

				@Override
				public void beforeCommit(IDbTableRowNotificationSet<DbListeningTestObject> notifications) {

					DbListeningTestObject.TABLE.createObject().save();
				}
			});

			DbListeningTestObject.TABLE.createObject().save();
			fail("Expected exception!");
		} catch (SofticarDeveloperException exception) {
			assertContains("infinite loop", exception.getMessage());
		}
	}

	@Test
	public void testWithoutExplicitTransaction() {

		objectA.save();
		objectA.delete();

		listener.assertNotifications("""
				beforeSave(A)
				afterSave(A)
				beforeCommit(CHANGE-A,SAVE-A)
				afterCommit(CHANGE-A,SAVE-A)
				beforeDelete(A)
				afterDelete(A)
				beforeCommit(CHANGE-A,DELETE-A)
				afterCommit(CHANGE-A,DELETE-A)
				""");
	}
}
