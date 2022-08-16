package com.softicar.platform.common.core.i18n;

/**
 * Represents an object that can be converted to {@link IDisplayString}.
 *
 * @author Oliver Richers
 */
public interface IDisplayable {

	/**
	 * Converts this object to {@link IDisplayString}.
	 *
	 * @return this object as {@link IDisplayString} (never <i>null</i>)
	 */
	IDisplayString toDisplay();
}
