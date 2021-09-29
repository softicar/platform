package com.softicar.platform.emf.collection.list;

import com.softicar.platform.emf.collection.AbstractEmfEntityCollection;
import com.softicar.platform.emf.entity.IEmfEntity;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public abstract class AbstractEmfEntityList<C extends AbstractEmfEntityList<C, E>, E extends IEmfEntity<E, ?>>
		extends AbstractEmfEntityCollection<C, E, List<E>>
		implements IEmfEntityList<C, E> {

	@Override
	public Function<List<E>, List<E>> getUnmodifiableConverter() {

		return Collections::unmodifiableList;
	}
}
