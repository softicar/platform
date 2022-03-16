package com.softicar.platform.common.core.uuid;

import java.util.UUID;
import org.junit.Assert;
import org.junit.Test;

public class UuidBytesTest extends Assert {

	private static final UUID TEST_UUID = UUID.fromString("0e9d6d53-036e-471f-9114-e994714d4479");
	private static final byte[] UUID_BYTES = //
			{ 0x0e, (byte) 0x9d, 0x6d, 0x53, 0x03, 0x6e, 0x47, 0x1f, (byte) 0x91, 0x14, (byte) 0xe9, (byte) 0x94, 0x71, 0x4d, 0x44, 0x79 };

	@Test
	public void testAsBytes() {

		byte[] bytes = UuidBytes.asBytes(TEST_UUID);
		assertArrayEquals(UUID_BYTES, bytes);
	}

	@Test
	public void testAsUuid() {

		byte[] bytes = UUID_BYTES;
		assertEquals(TEST_UUID, UuidBytes.asUuid(bytes));
	}
}
