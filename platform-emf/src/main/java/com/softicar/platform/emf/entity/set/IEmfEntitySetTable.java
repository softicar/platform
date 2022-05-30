package com.softicar.platform.emf.entity.set;

import com.softicar.platform.emf.entity.IEmfEntity;
import com.softicar.platform.emf.object.table.IEmfObjectTable;
import java.util.Collection;
import java.util.Set;

public interface IEmfEntitySetTable<ES extends IEmfEntitySet<ES, E>, E extends IEmfEntity<E, ?>, S> extends IEmfObjectTable<ES, S> {

	ES getOrInsert(Set<E> elements);

	void prefetchElements(Collection<ES> entitySets);

	Set<E> loadElements(ES entitySet);
}
