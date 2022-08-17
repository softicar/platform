package com.softicar.platform.common.core.i18n;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * A mutable implementation of {@link IDisplayString}.
 *
 * @author Oliver Richers
 */
public class DisplayString implements IDisplayString {

	private final List<IDisplayString> list;
	private String separator;

	/**
	 * Creates a new {@link DisplayString}.
	 */
	public DisplayString() {

		this.list = new ArrayList<>();
		this.separator = "";
	}

	/**
	 * Creates a new {@link DisplayString} from the given
	 * {@link IDisplayString}.
	 *
	 * @param other
	 *            the initially-appended {@link IDisplayString} (never
	 *            <i>null</i>)
	 */
	public DisplayString(IDisplayString other) {

		this();
		append(other);
	}

	/**
	 * Replaces the separator with the new given separator.
	 * <p>
	 * Upon invocation of {@link #toString}, the given separator is used to
	 * concatenate previously-appended instances of {@link IDisplayString}.
	 *
	 * @param separator
	 *            the new separator (never null)
	 * @return this {@link DisplayString}
	 */
	public DisplayString setSeparator(String separator) {

		this.separator = separator;
		return this;
	}

	/**
	 * Removes all previously-appended instances of {@link IDisplayString}.
	 *
	 * @return this {@link DisplayString}
	 */
	public DisplayString clear() {

		list.clear();
		return this;
	}

	/**
	 * Appends the given {@link IDisplayString}.
	 *
	 * @param displayString
	 *            the {@link IDisplayString} to append (never <i>null</i>)
	 * @return this {@link DisplayString}
	 */
	public DisplayString append(IDisplayString displayString) {

		list.add(displayString);
		return this;
	}

	/**
	 * Appends the given optional {@link IDisplayString}.
	 * <p>
	 * If the given {@link Optional} is not empty,
	 * {@link #append(IDisplayString)} is called, otherwise, nothing is done.
	 *
	 * @param displayString
	 *            the optional {@link IDisplayString} to append (never
	 *            <i>null</i>)
	 * @return this {@link DisplayString}
	 */
	public DisplayString append(Optional<IDisplayString> displayString) {

		displayString.ifPresent(this::append);
		return this;
	}

	/**
	 * Creates an {@link IDisplayString} from the given format string and
	 * arguments, and appends it.
	 *
	 * @param formatString
	 *            the formatting {@link String} (may be <i>null</i>)
	 * @param arguments
	 *            the formatting arguments (never <i>null</i>)
	 * @return this {@link DisplayString}
	 */
	public DisplayString append(String formatString, Object...arguments) {

		return append(IDisplayString.format(formatString, arguments));
	}

	/**
	 * Appends {@link IDisplayString#EMPTY}
	 *
	 * @return this {@link DisplayString}
	 */
	public DisplayString append() {

		return append(IDisplayString.EMPTY);
	}

	/**
	 * Checks if {@link IDisplayString} instances have been appended.
	 *
	 * @return <i>true</i> if no {@link IDisplayString} instances have been
	 *         appended; <i>false</i> otherwise
	 */
	public boolean isEmpty() {

		return list.isEmpty();
	}

	@Override
	public String toString() {

		return list//
			.stream()
			.map(IDisplayString::toString)
			.collect(Collectors.joining(separator));
	}

	@Override
	public boolean equals(Object object) {

		if (object instanceof DisplayString) {
			var other = (DisplayString) object;
			return list.equals(other.list) && separator.equals(other.separator);
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {

		return Objects.hash(list, separator);
	}
}
