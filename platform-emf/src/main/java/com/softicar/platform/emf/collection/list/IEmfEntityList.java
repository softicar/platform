package com.softicar.platform.emf.collection.list;

import com.softicar.platform.emf.collection.IEmfEntityCollection;
import com.softicar.platform.emf.entity.IEmfEntity;
import java.util.List;

public interface IEmfEntityList<C extends IEmfEntityList<C, E>, E extends IEmfEntity<E, ?>> extends IEmfEntityCollection<C, E, List<E>> {

	// nothing
}
