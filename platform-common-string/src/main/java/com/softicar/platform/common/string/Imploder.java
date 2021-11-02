package com.softicar.platform.common.string;

import com.softicar.platform.common.core.exceptions.SofticarException;
import com.softicar.platform.common.string.imploder.IObjectImploder;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

/**
 * This class creates a new string out of a sequence of input strings.
 * <p>
 * The input strings will be appended to each other and a separator will be put
 * between them.
 *
 * @author Oliver Richers
 */
public class Imploder {

	public static String implode(Object[] input, String separator) {

		return implode(Arrays.asList(input), separator);
	}

	public static String implode(Iterable<?> input, String separator) {

		try {
			StringBuilder builder = new StringBuilder();
			implode(input, separator, builder);
			return builder.toString();
		} catch (IOException exception) {
			throw new SofticarException(exception, "IOException while imploding a collection.");
		}
	}

	public static String implode(Iterable<?> input, String separator, String prefix, String sufix) {

		try {
			StringBuilder builder = new StringBuilder();
			implode(input, separator, prefix, sufix, builder);
			return builder.toString();
		} catch (IOException exception) {
			throw new SofticarException(exception, "IOException while imploding a collection.");
		}
	}

	public static void implode(Iterable<?> input, String separator, Appendable out) throws IOException {

		Iterator<?> it = input.iterator();
		if (it.hasNext()) {
			out.append("" + it.next());
			while (it.hasNext()) {
				out.append(separator);
				out.append("" + it.next()); // NOTE: not using toString, to support null pointers
			}
		}
	}

	public static void implode(Iterable<?> input, String separator, String prefix, String sufix, Appendable out) throws IOException {

		Iterator<?> it = input.iterator();
		if (it.hasNext()) {
			out.append(prefix);
			out.append("" + it.next());
			out.append(sufix);
			while (it.hasNext()) {
				out.append(separator);
				out.append(prefix);
				out.append("" + it.next()); // NOTE: not using toString, to support null pointers
				out.append(sufix);
			}
		}
	}

	public static String implodeQuoted(Object[] input, String separator, String quote) {

		return implodeQuoted(Arrays.asList(input), separator, quote);
	}

	public static String implodeQuoted(Iterable<?> input, String separator, String quote) {

		try {
			StringBuilder builder = new StringBuilder();
			implodeQuoted(input, separator, quote, builder);
			return builder.toString();
		} catch (IOException exception) {
			throw new SofticarException(exception, "IOException while imploding a collection.");
		}
	}

	public static void implodeQuoted(Iterable<?> input, String separator, String quote, Appendable out) throws IOException {

		Iterator<?> it = input.iterator();
		if (it.hasNext()) {
			Object next = it.next();
			if (next != null) {
				out.append(quote);
				out.append(next.toString());
				out.append(quote);
			} else {
				out.append(null);
			}

			while (it.hasNext()) {
				out.append(separator);

				out.append(quote);
				out.append("" + it.next()); // NOTE: not using toString, to support null pointers
				out.append(quote);
			}
		}
	}

	public static String implode(String item, int count, String separator) {

		StringBuilder result = new StringBuilder();
		if (count > 0) {
			result.append(item);

			for (int i = 1; i != count; ++i) {
				result.append(separator);
				result.append(item);
			}
		}

		return result.toString();
	}

	public static <T> String implode(Iterable<T> input, IObjectImploder<T> objectImploder, String separator) {

		StringBuilder output = new StringBuilder();

		if (input != null) {
			Iterator<T> iterator = input.iterator();

			while (iterator.hasNext()) {
				T obj = iterator.next();
				output.append(objectImploder.implode(obj));

				if (iterator.hasNext()) {
					output.append(separator);
				}
			}
		}

		return output.toString();
	}
}
