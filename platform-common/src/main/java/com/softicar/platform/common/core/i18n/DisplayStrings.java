package com.softicar.platform.common.core.i18n;

import java.util.Optional;
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

	/**
	 * Creates a new {@link IDisplayString} from the given plain {@link String}.
	 * <p>
	 * The given {@link String} will <b>not</b> be translated.
	 * <p>
	 * If the given {@link String} is <i>null</i>, {@link IDisplayString#EMPTY}
	 * will be returned.
	 *
	 * @param string
	 *            the plain {@link String} (may be null)
	 * @return the new {@link IDisplayString} (never null)
	 */
	static IDisplayString create(String string) {

		return Optional//
			.ofNullable(string)
			.map(PlainDisplayString::new)
			.orElse(PlainDisplayString.getEmpty());
	}

	/**
	 * Creates a new {@link IDisplayString} from the given formatting
	 * {@link String} and formatting arguments.
	 * <p>
	 * The given {@link String} will <b>not</b> be translated.
	 * <p>
	 * If the given {@link String} is <i>null</i>, {@link IDisplayString#EMPTY}
	 * will be returned. In that case, formatting arguments are ignored.
	 *
	 * @param formatString
	 *            the formatting {@link String} (may be null)
	 * @param arguments
	 *            the formatting arguments (never null)
	 * @return the new {@link IDisplayString} (never null)
	 */
	static IDisplayString format(String formatString, Object...arguments) {

		return formatAsOptional(formatString, arguments).orElse(IDisplayString.EMPTY);
	}

	/**
	 * Creates a new {@link IDisplayString} from the given formatting
	 * {@link String} and formatting arguments.
	 * <p>
	 * The given {@link String} will <b>not</b> be translated.
	 * <p>
	 * If the given {@link String} is <i>null</i>, {@link Optional#empty()} will
	 * be returned. In that case, formatting arguments are ignored.
	 *
	 * @param formatString
	 *            the formatting {@link String} (may be null)
	 * @param arguments
	 *            the formatting arguments (never null)
	 * @return the new {@link IDisplayString}
	 */
	static Optional<IDisplayString> formatAsOptional(String formatString, Object...arguments) {

		return Optional.ofNullable(formatString).map(it -> {
			var string = arguments.length > 0? String.format(it, arguments) : it;
			return create(string);
		});
	}
}
