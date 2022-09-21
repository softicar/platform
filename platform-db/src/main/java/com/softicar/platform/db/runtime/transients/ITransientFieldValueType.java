package com.softicar.platform.db.runtime.transients;

import java.util.Comparator;
import java.util.Optional;

public interface ITransientFieldValueType<V> {

	Class<V> getValueClass();

	Optional<Comparator<V>> getComparator();
}
