package com.softicar.platform.emf.collection.set;

import com.softicar.platform.emf.collection.IEmfEntityCollection;
import com.softicar.platform.emf.entity.IEmfEntity;
import java.util.Set;

public interface IEmfEntitySet<C extends IEmfEntitySet<C, E>, E extends IEmfEntity<E, ?>> extends IEmfEntityCollection<C, E, Set<E>> {

	// nothing
}
