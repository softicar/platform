package com.softicar.platform.dom.elements.popup.compositor;

import com.softicar.platform.common.core.singleton.Singleton;

/**
 * A {@link Singleton} that holds the currently-used
 * {@link IDomPopupCompositor}.
 *
 * @author Alexander Schmidt
 */
public class CurrentDomPopupCompositor {

	private static final Singleton<IDomPopupCompositor> INSTANCE = new Singleton<>(DomDefaultPopupCompositor::new);

	/**
	 * Returns the currently-used {@link IDomPopupCompositor}.
	 *
	 * @return the current {@link IDomPopupCompositor} (may be <i>null</i>)
	 */
	public static IDomPopupCompositor get() {

		return INSTANCE.get();
	}

	/**
	 * Sets the currently-used {@link IDomPopupCompositor}.
	 *
	 * @param compositor
	 *            the current {@link IDomPopupCompositor} (may be <i>null</i>)
	 */
	public static void set(IDomPopupCompositor compositor) {

		INSTANCE.set(compositor);
	}
}
