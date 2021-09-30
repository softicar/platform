package com.softicar.platform.db.runtime.transients;

import java.util.Arrays;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Map;

public class TransientAccumulativeFieldTestBase<E> extends TransientFieldTestBase {

	protected final Map<TestObject, Collection<E>> elementsInStore;

	public TransientAccumulativeFieldTestBase() {

		this.elementsInStore = new IdentityHashMap<>();
	}

	@SafeVarargs
	protected final void setElementsInStore(TestObject object, E...elements) {

		elementsInStore.put(object, Arrays.asList(elements));
	}

	protected final void setElementsInStore(TestObject object, Collection<E> elements) {

		elementsInStore.put(object, elements);
	}
}
