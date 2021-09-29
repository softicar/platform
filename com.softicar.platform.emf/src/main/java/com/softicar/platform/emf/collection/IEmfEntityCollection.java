package com.softicar.platform.emf.collection;

import com.softicar.platform.emf.entity.IEmfEntity;
import com.softicar.platform.emf.object.IEmfObject;
import java.util.Collection;
import java.util.function.Function;

public interface IEmfEntityCollection<C extends IEmfEntityCollection<C, E, EC>, E extends IEmfEntity<E, ?>, EC extends Collection<E>> extends IEmfObject<C> {

	@Override
	IEmfEntityCollectionTable<C, E, EC, ?> table();

	EC getElements();

	Function<EC, EC> getUnmodifiableConverter();
}
