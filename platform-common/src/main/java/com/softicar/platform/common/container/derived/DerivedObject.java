package com.softicar.platform.common.container.derived;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * Caches an {@link Object} that is derived from one or more source objects.
 * <p>
 * The derived {@link Object} will be discarded and recomputed whenever a change
 * to one of the source objects occurs. The source objects, on which the derived
 * {@link Object} depends, must be configured by calling {@link #addDependsOn}.
 *
 * @author Oliver Richers
 */
public class DerivedObject<T> {

	private final Supplier<T> factory;
	private T object;

	/**
	 * Constructs this {@link DerivedObject} with the given factory.
	 * <p>
	 * The factory may never return <i>null</i>. Use {@link Optional} if
	 * necessary.
	 *
	 * @param factory
	 *            the factory (never <i>null</i>)
	 */
	public DerivedObject(Supplier<T> factory) {

		this.factory = Objects.requireNonNull(factory);
		this.object = null;
	}

	/**
	 * Specifies that the derived {@link Object} depends on the given source
	 * object.
	 * <p>
	 * The source object should have identity semantics, that is, it should
	 * implement neither {@link Object#hashCode} nor {@link Object#equals}.
	 *
	 * @param sourceObject
	 *            the source object that this derived {@link Object} depends on
	 *            (never <i>null</i>)
	 * @return this
	 */
	public DerivedObject<T> addDependsOn(Object sourceObject) {

		Objects.requireNonNull(sourceObject);
		CurrentDerivedObjectRegistry.getInstance().addDependency(sourceObject, this);
		return this;
	}

	/**
	 * Returns the derived {@link Object}.
	 *
	 * @return the derived {@link Object} (never <i>null</i>)
	 */
	public T get() {

		if (object == null) {
			this.object = Objects.requireNonNull(factory.get());
		}
		return object;
	}

	/**
	 * Explicitly discards the derived {@link Object}.
	 * <p>
	 * The {@link Object} will be recomputed on the next call to {@link #get()}.
	 */
	public void invalidate() {

		this.object = null;
	}
}
