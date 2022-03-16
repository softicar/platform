package com.softicar.platform.common.core.i18n;

import java.util.stream.Collector;

/**
 * Utility methods for {@link IDisplayString}.
 *
 * @author Oliver Richers
 */
public class DisplayStrings {

	/**
	 * Creates a new {@link DisplayStringJoiningCollector} with
	 * {@link IDisplayString#EMPTY} as separator.
	 *
	 * @return new instance of {@link DisplayStringJoiningCollector} (never
	 *         null)
	 */
	public static Collector<IDisplayString, ?, IDisplayString> joining() {

		return joining(IDisplayString.EMPTY);
	}

	/**
	 * Creates a new {@link DisplayStringJoiningCollector} with the given
	 * separator.
	 *
	 * @return new instance of {@link DisplayStringJoiningCollector} (never
	 *         null)
	 */
	public static Collector<IDisplayString, ?, IDisplayString> joining(IDisplayString separator) {

		return new DisplayStringJoiningCollector(separator);
	}
}
