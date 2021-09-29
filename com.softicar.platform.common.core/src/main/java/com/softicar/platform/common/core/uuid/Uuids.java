package com.softicar.platform.common.core.uuid;

import com.softicar.platform.common.core.utils.DevNull;
import java.util.Optional;
import java.util.UUID;

/**
 * Utility methods for {@link UUID}.
 *
 * @author Oliver Richers
 */
public class Uuids {

	public static boolean isUuid(String uuidString) {

		try {
			if (uuidString != null) {
				UUID uuid = UUID.fromString(uuidString);
				return uuid.toString().equalsIgnoreCase(uuidString);
			} else {
				return false;
			}
		} catch (IllegalArgumentException exception) {
			DevNull.swallow(exception);
			return false;
		}
	}

	public static Optional<UUID> parseUuid(String uuidString) {

		UUID uuid = null;
		try {
			uuid = UUID.fromString(uuidString);
		} catch (IllegalArgumentException exception) {
			DevNull.swallow(exception);
		}
		return Optional.ofNullable(uuid);
	}
}
