package com.softicar.platform.db.runtime.transients;

import com.softicar.platform.db.runtime.table.row.IDbTableRow;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.WeakHashMap;

/**
 * A special cache for transient values of instances of {@link IDbTableRow}.
 *
 * @param <R>
 *            type of {@link IDbTableRow}
 * @author Oliver Richers
 */
public class DbTransientValueCache<R> {

	private final Map<R, DbTransientValueMap<R>> valueMaps;

	public DbTransientValueCache() {

		this.valueMaps = new WeakHashMap<>();
	}

	public void invalidate(R tableRow) {

		valueMaps.remove(tableRow);
	}

	public void invalidateAll(Collection<R> rows) {

		if (!valueMaps.isEmpty()) {
			rows.forEach(valueMaps::remove);
		}
	}

	public void invalidateAll() {

		valueMaps.clear();
	}

	public <V> void setTransientValue(R tableRow, IDbTransientField<R, V> field, V value) {

		valueMaps//
			.computeIfAbsent(tableRow, row -> new DbTransientValueMap<>())
			.setValue(field, value);
	}

	public <V> V getTransientValue(R tableRow, IDbTransientField<R, V> field) {

		return Optional//
			.ofNullable(valueMaps.get(tableRow))
			.map(valueMap -> valueMap.getValue(field))
			.orElse(null);
	}
}
