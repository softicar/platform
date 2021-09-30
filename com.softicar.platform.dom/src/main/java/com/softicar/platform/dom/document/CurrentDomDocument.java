package com.softicar.platform.dom.document;

import com.softicar.platform.common.core.singleton.Singleton;

/**
 * This is a {@link Singleton} to hold the currently-used {@link IDomDocument}.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public class CurrentDomDocument {

	private static final Singleton<IDomDocument> CURRENT_DOM_DOCUMENT = new Singleton<>();

	/**
	 * Returns the currently-used {@link IDomDocument}.
	 *
	 * @return the current {@link IDomDocument} (may be <i>null</i>)
	 */
	public static IDomDocument get() {

		return CURRENT_DOM_DOCUMENT.get();
	}

	/**
	 * Sets the currently-used {@link IDomDocument}.
	 *
	 * @param document
	 *            the current {@link IDomDocument} (may be <i>null</i>)
	 */
	public static void set(IDomDocument document) {

		CURRENT_DOM_DOCUMENT.set(document);
	}
}
