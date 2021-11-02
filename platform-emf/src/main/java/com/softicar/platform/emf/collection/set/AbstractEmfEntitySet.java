package com.softicar.platform.emf.collection.set;

import com.softicar.platform.emf.collection.AbstractEmfEntityCollection;
import com.softicar.platform.emf.entity.IEmfEntity;
import java.util.Collections;
import java.util.Set;
import java.util.function.Function;

public abstract class AbstractEmfEntitySet<C extends AbstractEmfEntitySet<C, E>, E extends IEmfEntity<E, ?>> extends AbstractEmfEntityCollection<C, E, Set<E>>
		implements IEmfEntitySet<C, E> {

	@Override
	public Function<Set<E>, Set<E>> getUnmodifiableConverter() {

		return Collections::unmodifiableSet;
	}
}
