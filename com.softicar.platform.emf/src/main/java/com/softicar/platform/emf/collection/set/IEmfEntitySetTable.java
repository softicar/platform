package com.softicar.platform.emf.collection.set;

import com.softicar.platform.emf.collection.IEmfEntityCollection;
import com.softicar.platform.emf.collection.IEmfEntityCollectionTable;
import com.softicar.platform.emf.entity.IEmfEntity;
import java.util.Set;

public interface IEmfEntitySetTable<C extends IEmfEntityCollection<C, E, Set<E>>, E extends IEmfEntity<E, ?>, S>
		extends IEmfEntityCollectionTable<C, E, Set<E>, S> {

	// nothing
}
