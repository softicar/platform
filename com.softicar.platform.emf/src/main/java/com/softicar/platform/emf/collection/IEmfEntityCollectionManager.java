package com.softicar.platform.emf.collection;

import com.softicar.platform.common.core.item.IBasicItem;
import com.softicar.platform.db.runtime.logic.IDbObject;
import java.util.Collection;

public interface IEmfEntityCollectionManager<C extends IDbObject<C>, E extends IBasicItem, EC extends Collection<E>> {

	EC loadElements(C collection);

	void prefetchElements(Collection<C> collections);

	C getOrInsert(EC elements);
}
