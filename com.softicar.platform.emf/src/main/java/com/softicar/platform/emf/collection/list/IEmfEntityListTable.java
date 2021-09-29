package com.softicar.platform.emf.collection.list;

import com.softicar.platform.emf.collection.IEmfEntityCollection;
import com.softicar.platform.emf.collection.IEmfEntityCollectionTable;
import com.softicar.platform.emf.entity.IEmfEntity;
import java.util.List;

public interface IEmfEntityListTable<C extends IEmfEntityCollection<C, E, List<E>>, E extends IEmfEntity<E, ?>, S>
		extends IEmfEntityCollectionTable<C, E, List<E>, S> {

	// nothing
}
