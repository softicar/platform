package com.softicar.platform.db.runtime.transients;

import com.softicar.platform.common.container.pair.Pair;
import com.softicar.platform.db.runtime.table.row.IDbTableRow;
import java.util.Collections;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Defines a transient field with values of type {@link Map}.
 *
 * @author Oliver Richers
 */
public abstract class AbstractTransientMapField<O extends IDbTableRow<O, ?>, K, V> extends AbstractTransientCollectionField<O, Map<K, V>, Pair<K, V>> {

	public AbstractTransientMapField(Supplier<Map<K, V>> factory) {

		super(TransientFieldValueTypes.getMap(), Collections.emptyMap(), factory, (map, pair) -> map.put(pair.getFirst(), pair.getSecond()));
	}
}
