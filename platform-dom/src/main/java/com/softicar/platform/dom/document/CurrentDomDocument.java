package com.softicar.platform.dom.document;

import com.softicar.platform.common.core.singleton.Singleton;
import java.util.Optional;

/**
 * This is a {@link Singleton} to hold the currently-used {@link IDomDocument}.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public class CurrentDomDocument {

	private static final Singleton<IDomDocument> CURRENT_DOM_DOCUMENT = new Singleton<>();

	/**
	 * Returns the currently used {@link IDomDocument}.
	 *
	 * @return the current {@link IDomDocument} (may be <i>null</i>)
	 */
	public static IDomDocument get() {

		return CURRENT_DOM_DOCUMENT.get();
	}

	/**
	 * Returns the currently used {@link IDomDocument}.
	 *
	 * @return the current {@link IDomDocument} as {@link Optional}
	 */
	public static Optional<IDomDocument> getAsOptional() {

		return Optional.ofNullable(get());
	}

	/**
	 * Sets the currently used {@link IDomDocument}.
	 *
	 * @param document
	 *            the current {@link IDomDocument} (may be <i>null</i>)
	 */
	public static void set(IDomDocument document) {

		CURRENT_DOM_DOCUMENT.set(document);
	}
}
