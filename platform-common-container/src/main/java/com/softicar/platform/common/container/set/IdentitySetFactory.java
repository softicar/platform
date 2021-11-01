package com.softicar.platform.common.container.set;

import java.util.Collection;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Set;
import java.util.function.Supplier;

public class IdentitySetFactory<T> implements Supplier<Collection<T>> {

	@Override
	public Set<T> get() {

		return Collections.newSetFromMap(new IdentityHashMap<>());
	}
}
