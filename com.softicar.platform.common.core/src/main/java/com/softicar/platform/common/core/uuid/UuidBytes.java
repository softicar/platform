package com.softicar.platform.common.core.uuid;

import java.nio.ByteBuffer;
import java.util.UUID;

/**
 * Conversion methods between UUID and bytes array.
 *
 * @author Oliver Richers
 */
public class UuidBytes {

	public static UUID asUuid(byte[] bytes) {

		ByteBuffer bb = ByteBuffer.wrap(bytes);
		long firstLong = bb.getLong();
		long secondLong = bb.getLong();
		return new UUID(firstLong, secondLong);
	}

	public static byte[] asBytes(UUID uuid) {

		ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
		bb.putLong(uuid.getMostSignificantBits());
		bb.putLong(uuid.getLeastSignificantBits());
		return bb.array();
	}
}
