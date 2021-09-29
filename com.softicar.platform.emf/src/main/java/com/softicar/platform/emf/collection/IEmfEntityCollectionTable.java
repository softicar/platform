package com.softicar.platform.emf.collection;

import com.softicar.platform.emf.entity.IEmfEntity;
import com.softicar.platform.emf.entity.table.IEmfEntityTable;
import com.softicar.platform.emf.object.table.IEmfObjectTable;
import java.util.Collection;
import java.util.stream.Collector;

public interface IEmfEntityCollectionTable<C extends IEmfEntityCollection<C, E, EC>, E extends IEmfEntity<E, ?>, EC extends Collection<E>, S>
		extends IEmfObjectTable<C, S> {

	C getOrInsert(EC elements);

	void prefetchElements(Collection<C> collections);

	EC loadElements(C collection);

	IEmfEntityTable<E, ?, ?> getElementTable();

	Collector<E, ?, EC> getCollector();
}
