package com.softicar.platform.db.runtime.table.logic;

import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.db.runtime.object.DbTestObject;
import java.lang.ref.WeakReference;
import java.util.Optional;
import org.junit.Test;

public class DbTableRowBackupMapTest extends AbstractTest {

	private final DbTableRowBackupMap backupMap;

	public DbTableRowBackupMapTest() {

		this.backupMap = new DbTableRowBackupMap();
	}

	@Test
	public void testGetBackup() {

		DbTestObject object = createObjectAndStoreBackup();

		Optional<DbTableRowBackup<DbTestObject, Integer>> backup = backupMap.getBackup(object);

		assertTrue(backup.isPresent());
	}

	@Test(timeout = 1000)
	public void testGetBackupWithGarbageCollection() {

		WeakReference<DbTestObject> objectReference = new WeakReference<>(createObjectAndStoreBackup());

		do {
			System.gc();
		} while (objectReference.get() != null);
	}

	private DbTestObject createObjectAndStoreBackup() {

		DbTestObject object = new DbTestObject();
		backupMap.storeBackupIfAbsent(object);
		return object;
	}
}
