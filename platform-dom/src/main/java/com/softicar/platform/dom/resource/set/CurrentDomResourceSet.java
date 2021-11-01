package com.softicar.platform.dom.resource.set;

import com.softicar.platform.common.core.singleton.Singleton;

/**
 * Holds the {@link Singleton} that contains the current {@link IDomResourceSet}.
 *
 * @author Oliver Richers
 */
public class CurrentDomResourceSet {

	private static final Singleton<IDomResourceSet> RESOURCE_SET = new Singleton<>(DomResourceSet::new);

	/**
	 * Returns the current {@link IDomResourceSet}.
	 *
	 * @return the current {@link IDomResourceSet} (never <i>null</i>)
	 */
	public static IDomResourceSet get() {

		return RESOURCE_SET.get();
	}

	/**
	 * Defines the current {@link IDomResourceSet} instance to user.
	 *
	 * @param resourceSet
	 *            the {@link IDomResourceSet} instance (never <i>null</i>)
	 */
	public static void set(IDomResourceSet resourceSet) {

		RESOURCE_SET.set(resourceSet);
	}

	/**
	 * Resets the {@link Singleton} to its default value.
	 */
	public static void reset() {

		RESOURCE_SET.reset();
	}
}
