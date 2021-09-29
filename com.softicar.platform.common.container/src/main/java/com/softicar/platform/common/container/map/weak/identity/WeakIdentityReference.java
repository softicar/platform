package com.softicar.platform.common.container.map.weak.identity;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

public class WeakIdentityReference<T> extends WeakReference<T> {

	private final int hashCode;

	public WeakIdentityReference(T object) {

		super(object);
		this.hashCode = System.identityHashCode(object);
	}

	public WeakIdentityReference(T object, ReferenceQueue<? super T> referenceQueue) {

		super(object, referenceQueue);
		this.hashCode = System.identityHashCode(object);
	}

	@Override
	public int hashCode() {

		return hashCode;
	}

	@Override
	public boolean equals(Object other) {

		// references are always equal to themselves, which is a necessary property
		// to allow the removal of references with collected objects from a container
		if (this == other) {
			return true;
		}

		// if the referenced object has been collected, it can't be identical to any other object
		T object = get();
		if (object == null) {
			return false;
		}

		// now, compare referenced objects for identity
		if (other instanceof WeakIdentityReference<?>) {
			WeakIdentityReference<?> otherReference = (WeakIdentityReference<?>) other;
			return object == otherReference.get();
		}

		return false;
	}
}
