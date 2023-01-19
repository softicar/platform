package com.softicar.platform.core.module.uuid;

import com.softicar.platform.db.runtime.test.AbstractDbTest;
import java.util.UUID;
import org.junit.Test;

public class UuidCacheTest extends AbstractDbTest {

	private static final UUID UUID_A = UUID.fromString("3533c7d3-d5c0-4685-85f4-9dca40093e4e");
	private static final UUID UUID_B = UUID.fromString("6c8483bb-d334-4a1c-beba-7b1c7348eb5a");
	private final UuidCache cache;

	public UuidCacheTest() {

		this.cache = new UuidCache();
	}

	@Test
	public void testWithEmptyDatabase() {

		var uuidA = cache.getOrInsertUuid(UUID_A);
		var uuidB = cache.getOrInsertUuid(UUID_B);
		var uuidAAgain = cache.getOrInsertUuid(UUID_A);
		var uuidBAgain = cache.getOrInsertUuid(UUID_B);

		assertSame(uuidA, uuidAAgain);
		assertSame(uuidB, uuidBAgain);
		assertEquals(UUID_A, uuidA.getUuid());
		assertEquals(UUID_B, uuidB.getUuid());
		var uuids = assertCount(2, AGUuid.TABLE.loadAll());
		assertTrue(uuids.contains(uuidA));
		assertTrue(uuids.contains(uuidB));
	}

	@Test
	public void testWithNonEmptyDatabase() {

		// insert A with upper-case letters
		var existingUuidA = new AGUuid()//
			.setUuidString(UUID_A.toString().toUpperCase())
			.save();

		// insert B with lower-case letters
		var existingUuidB = new AGUuid()//
			.setUuidString(UUID_B.toString().toLowerCase())
			.save();

		var uuidAFromCache = cache.getOrInsertUuid(UUID_A);
		var uuidBFromCache = cache.getOrInsertUuid(UUID_B);

		assertSame(existingUuidA, uuidAFromCache);
		assertSame(existingUuidB, uuidBFromCache);
	}
}
