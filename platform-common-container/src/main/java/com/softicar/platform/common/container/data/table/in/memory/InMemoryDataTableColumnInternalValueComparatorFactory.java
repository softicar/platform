package com.softicar.platform.common.container.data.table.in.memory;

import com.softicar.platform.common.core.entity.IEntity;
import com.softicar.platform.common.core.utils.CastUtils;
import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;
import java.util.Optional;

class InMemoryDataTableColumnInternalValueComparatorFactory<V> {

	public Optional<Comparator<V>> create(Class<V> valueClass) {

		// compare entities
		if (IEntity.class.isAssignableFrom(valueClass)) {
			Comparator<IEntity> simpleItemComparator = Comparator//
				.<IEntity, String> comparing(entity -> entity.toDisplayWithoutId().toString(), this::compareWithCollation)
				.thenComparing(IEntity::getId);
			Comparator<V> comparator = (a, b) -> simpleItemComparator.compare(IEntity.class.cast(a), IEntity.class.cast(b));
			return Optional.of(comparator);
		}

		// compare strings
		else if (String.class.isAssignableFrom(valueClass)) {
			return Optional.of(this::compareWithCollation);
		}

		// compare any other comparable instances
		else if (Comparable.class.isAssignableFrom(valueClass)) {
			Class<Comparable<V>> comparableClass = CastUtils.cast(Comparable.class);
			Comparator<V> comparator = (V a, V b) -> comparableClass.cast(a).compareTo(b);
			return Optional.of(comparator);
		}

		else {
			return Optional.empty();
		}
	}

	/**
	 * Compares the given values in a collation-aware manner.
	 * <p>
	 * Does NOT rely on the system's default locale.
	 */
	private int compareWithCollation(Object first, Object second) {

		return Collator.getInstance(Locale.US).compare(first, second);
	}
}
