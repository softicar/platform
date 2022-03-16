package com.softicar.platform.common.core.i18n;

import com.softicar.platform.common.core.locale.CurrentLocale;
import java.util.Objects;
import java.util.Optional;

/**
 * Represents a text to be displayed.
 * <p>
 * Translatable texts are localized, according to the preference of the user.
 * <p>
 * If a translatable text contains format specifiers (as in
 * {@link String#format(String, Object...)}), those are replaced with the
 * arguments given to the respective method.
 * <p>
 * FIXME i70184 should not implement {@link Comparable}
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public interface IDisplayString extends Comparable<IDisplayString> {

	/**
	 * An immutable, empty {@link IDisplayString}.
	 */
	IDisplayString EMPTY = PlainDisplayString.getEmpty();

	/**
	 * Returns the string to be displayed, localized according to
	 * {@link CurrentLocale}.
	 *
	 * @return the string to be displayed (never null)
	 */
	@Override
	String toString();

	/**
	 * Returns the hash code of this {@link IDisplayString}.
	 * <p>
	 * Must be implemented based upon {@link #toString()}.
	 * <p>
	 * FIXME i70184 this method should not be in here
	 *
	 * @return the hash code of this {@link IDisplayString}
	 */
	@Override
	int hashCode();

	/**
	 * Compares this {@link IDisplayString} to the given one.
	 * <p>
	 * Must be implemented based upon {@link #toString()}.
	 * <p>
	 * FIXME i70184 this method should not be in here
	 *
	 * @param other
	 *            the {@link IDisplayString} to which this one shall be compared
	 *            (never null)
	 * @return the result of the comparison
	 */
	@Override
	default int compareTo(IDisplayString other) {

		return toString().compareTo(other.toString());
	}

	/**
	 * Compares this {@link IDisplayString} to the given one, ignoring case.
	 * <p>
	 * Must be implemented based upon {@link #toString()}.
	 *
	 * @param other
	 *            the {@link IDisplayString} to which this one shall be compared
	 *            (never null)
	 * @return the result of the comparison
	 */
	default int compareToIgnoreCase(IDisplayString other) {

		return toString().compareToIgnoreCase(other.toString());
	}

	// -------------------- creation -------------------- //

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

		return formatAsOptional(formatString, arguments).orElse(EMPTY);
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

		return Optional.ofNullable(formatString).map(it -> new FormattingDisplayString(it, arguments));
	}

	// -------------------- concatenation -------------------- //

	/**
	 * Creates a new {@link IDisplayString}, as a concatenation of this
	 * {@link IDisplayString} and the given {@link IDisplayString}.
	 *
	 * @param other
	 *            the {@link IDisplayString} to append to the new
	 *            {@link IDisplayString}, after this {@link IDisplayString}
	 *            (never null)
	 * @return the new {@link IDisplayString} (never null)
	 */
	default IDisplayString concat(IDisplayString other) {

		return new ConcatDisplayString(this, other);
	}

	/**
	 * Creates a new {@link IDisplayString}, as a concatenation of this
	 * {@link IDisplayString} and an {@link IDisplayString} which is derived
	 * from the given plain {@link String}.
	 * <p>
	 * The given {@link String} will <b>not</b> be translated.
	 * <p>
	 * If the given {@link String} is <i>null</i>, nothing will be appended to
	 * the new {@link IDisplayString}, after this {@link IDisplayString}.
	 *
	 * @param string
	 *            the plain {@link String} from which the second
	 *            {@link IDisplayString} shall be created (may be null)
	 * @return the new {@link IDisplayString} (never null)
	 */
	default IDisplayString concat(String string) {

		return new ConcatDisplayString(this, create(string));
	}

	/**
	 * Creates a new {@link IDisplayString}, as a concatenation of this
	 * {@link IDisplayString} and an {@link IDisplayString} which is derived
	 * from the given formatting {@link String} and formatting arguments.
	 * <p>
	 * The given {@link String} will <b>not</b> be translated.
	 * <p>
	 * If the given {@link String} is <i>null</i>, nothing will be appended to
	 * the new {@link IDisplayString}, after this {@link IDisplayString}.
	 *
	 * @param formatString
	 *            the formatting {@link String} from which the second
	 *            {@link IDisplayString} shall be created (may be null)
	 * @param arguments
	 *            the formatting arguments (never null)
	 * @return the new {@link IDisplayString} (never null)
	 */
	default IDisplayString concatFormat(String formatString, Object...arguments) {

		return new ConcatDisplayString(this, format(formatString, arguments));
	}

	/**
	 * Creates a new {@link IDisplayString}, as a concatenation of this
	 * {@link IDisplayString}, a space, and the given {@link IDisplayString}.
	 * <p>
	 * Assumes this {@link IDisplayString} and the given {@link IDisplayString}
	 * to be sentences, which are separated by a space.
	 *
	 * @param other
	 *            the {@link IDisplayString} to append to the new
	 *            {@link IDisplayString}, after this {@link IDisplayString} and
	 *            a space (never null)
	 * @return the new {@link IDisplayString} (never null)
	 */
	default IDisplayString concatSentence(IDisplayString other) {

		return concatSpace().concat(other);
	}

	/**
	 * Creates a new {@link IDisplayString}, as a concatenation of this
	 * {@link IDisplayString}, and the given {@link IDisplayString} enclosed in
	 * brackets ({@code "["} and {@code "]"}).
	 *
	 * @param other
	 *            the {@link IDisplayString} to append to the new
	 *            {@link IDisplayString}, enclosed in brackets, after this
	 *            {@link IDisplayString} (never null)
	 * @return the new {@link IDisplayString} (never null)
	 */
	default IDisplayString concatInBrackets(IDisplayString other) {

		return concat(other.encloseInBrackets());
	}

	/**
	 * Creates a new {@link IDisplayString}, as a concatenation of this
	 * {@link IDisplayString}, and the given {@link IDisplayString} enclosed in
	 * parentheses ({@code "("} and {@code ")"}).
	 *
	 * @param other
	 *            the {@link IDisplayString} to append to the new
	 *            {@link IDisplayString}, enclosed in parentheses, after this
	 *            {@link IDisplayString} (never null)
	 * @return the new {@link IDisplayString} (never null)
	 */
	default IDisplayString concatInParentheses(IDisplayString other) {

		return concat(other.encloseInParentheses());
	}

	/**
	 * Creates a new {@link IDisplayString}, as a concatenation of this
	 * {@link IDisplayString} and a colon character ({@code ":"}).
	 *
	 * @return the new {@link IDisplayString} (never null)
	 * @see #concatFormat(String, Object...)
	 */
	default IDisplayString concatColon() {

		return concat(":");
	}

	/**
	 * Creates a new {@link IDisplayString}, as a concatenation of this
	 * {@link IDisplayString} and three dot characters ({@code "..."}).
	 *
	 * @return the new {@link IDisplayString} (never null)
	 * @see #concatFormat(String, Object...)
	 */
	default IDisplayString concatEllipsis() {

		return concat("...");
	}

	/**
	 * Creates a new {@link IDisplayString}, as a concatenation of this
	 * {@link IDisplayString} and a space character ({@code " "}).
	 *
	 * @return the new {@link IDisplayString} (never null)
	 * @see #concatFormat(String, Object...)
	 */
	default IDisplayString concatSpace() {

		return concat(" ");
	}

	// -------------------- enclosing -------------------- //

	/**
	 * Creates a new {@link IDisplayString}, by enclosing this
	 * {@link IDisplayString} in the given prefix and suffix.
	 *
	 * @param prefix
	 *            the prefix to prepend (never null)
	 * @param suffix
	 *            the suffix to append (never null)
	 * @return the new {@link IDisplayString} (never null)
	 * @throws NullPointerException
	 *             if either of the given arguments is <i>null</i>
	 */
	default IDisplayString enclose(String prefix, String suffix) {

		Objects.requireNonNull(prefix);
		Objects.requireNonNull(suffix);

		return new DisplayString()//
			.append(prefix)
			.append(this)
			.append(suffix);
	}

	/**
	 * Same as {@link #enclose(String, String)}, with identical prefix and
	 * suffix.
	 *
	 * @param prefixSuffix
	 *            the {@link String} to use as both a prefix and a suffix (never
	 *            null)
	 * @return the new {@link IDisplayString} (never null)
	 * @throws NullPointerException
	 *             if the given argument is <i>null</i>
	 * @see #enclose(String, String)
	 */
	default IDisplayString enclose(String prefixSuffix) {

		return enclose(prefixSuffix, prefixSuffix);
	}

	/**
	 * Creates a new {@link IDisplayString}, by enclosing this
	 * {@link IDisplayString} in brackets ({@code "["} and {@code "]"}).
	 *
	 * @return the new {@link IDisplayString} (never null)
	 */
	default IDisplayString encloseInBrackets() {

		return enclose("[", "]");
	}

	/**
	 * Creates a new {@link IDisplayString}, by enclosing this
	 * {@link IDisplayString} in parentheses ({@code "("} and {@code ")"}).
	 *
	 * @return the new {@link IDisplayString} (never null)
	 */
	default IDisplayString encloseInParentheses() {

		return enclose("(", ")");
	}
}
