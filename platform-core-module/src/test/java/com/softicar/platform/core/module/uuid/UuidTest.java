package com.softicar.platform.core.module.uuid;

import com.softicar.platform.db.core.connection.DbConnectionOverrideScope;
import com.softicar.platform.db.core.transaction.DbTransaction;
import com.softicar.platform.db.runtime.test.AbstractDbTest;
import java.util.UUID;
import org.junit.Test;

public class UuidTest extends AbstractDbTest {

	private static final UUID TEST_UUID = UUID.fromString("0e9d6d53-036e-471f-9114-e994714d4479");

	@Test
	public void testConcurrentInsert() {

		// Unfortunately, this test is actually ineffective on an H2 database
		// since H2 does not support the REPEATABLE READ locking mode.

		try (DbTransaction transaction = new DbTransaction()) {
			// enforce a repeatable read for the current transaction
			AGUuid uuidObject = AGUuid.loadByUuidString(TEST_UUID.toString());
			assertNull(uuidObject);

			// insert the UUID with another connection
			Integer uuidId = insertUuidConcurrently();
			assertNotNull(uuidId);

			// check that the new UUID is found
			uuidObject = AGUuid.getOrCreate(TEST_UUID);
			assertNotNull(uuidObject);
			assertEquals(uuidId, uuidObject.getId());
		}
	}

	private Integer insertUuidConcurrently() {

		try (DbConnectionOverrideScope scope = new DbConnectionOverrideScope()) {
			AGUuid uuid = AGUuid.getOrCreate(TEST_UUID);
			assertNotNull(uuid);
			return uuid.getId();
		}
	}
}
