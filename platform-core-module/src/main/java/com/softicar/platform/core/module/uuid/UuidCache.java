package com.softicar.platform.core.module.uuid;

import com.softicar.platform.db.core.connection.DbConnectionOverrideScope;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

class UuidCache {

	private Map<UUID, AGUuid> cache;

	public UuidCache() {

		this.cache = null;
	}

	public AGUuid getOrInsertUuid(UUID uuid) {

		if (cache == null) {
			this.cache = loadAll();
		}

		return cache.computeIfAbsent(uuid, this::insertUuid);
	}

	private Map<UUID, AGUuid> loadAll() {

		return AGUuid.TABLE//
			.loadAll()
			.stream()
			.collect(Collectors.toMap(uuid -> uuid.getUuid(), Function.identity()));
	}

	private AGUuid insertUuid(UUID uuid) {

		return insertUuid(uuid.toString());
	}

	private AGUuid insertUuid(String uuid) {

		// we must use a separate connection for insertion here
		try (DbConnectionOverrideScope scope = new DbConnectionOverrideScope()) {
			try {
				return new AGUuid()//
					.setUuidString(uuid)
					.save();
			} catch (Exception exception) {
				// assuming someone else inserted the UUID concurrently
				return AGUuid.TABLE//
					.createSelect()
					.where(AGUuid.UUID_STRING.isEqual(uuid))
					.getOneAsOptional()
					.orElseThrow(() -> new RuntimeException("Failed to insert UUID '%s'.".formatted(uuid), exception));
			}
		}
	}
}
