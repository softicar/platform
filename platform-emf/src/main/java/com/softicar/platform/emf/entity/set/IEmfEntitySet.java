package com.softicar.platform.emf.entity.set;

import com.softicar.platform.emf.entity.IEmfEntity;
import com.softicar.platform.emf.object.IEmfObject;
import java.util.Set;

public interface IEmfEntitySet<ES extends IEmfEntitySet<ES, E>, E extends IEmfEntity<E, ?>> extends IEmfObject<ES> {

	@Override
	IEmfEntitySetTable<ES, E, ?> table();

	Set<E> getElements();
}
