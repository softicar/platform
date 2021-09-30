package com.softicar.platform.common.io.serialization.json;

import com.softicar.platform.common.container.pair.Pair;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * A simple builder for JSON blocks.
 *
 * @author Alexander Schmidt
 */
public class JsonBlock {

	private final List<Pair<String, String>> attributeValuePairs;

	/**
	 * Constructs a new {@link JsonBlock}.
	 */
	public JsonBlock() {

		this.attributeValuePairs = new ArrayList<>();
	}

	/**
	 * Adds the given attribute and value.
	 * <p>
	 * If, and only if, the given value is a {@link String}, it will be quoted
	 * in the output of {@link JsonBlock#toString()}.
	 *
	 * @param attribute
	 *            the name of the attribute (never <i>null</i>)
	 * @param value
	 *            the value (never <i>null</i>)
	 * @return this {@link JsonBlock}
	 * @throws NullPointerException
	 *             if either of the arguments is <i>null</i>
	 */
	public JsonBlock add(String attribute, Object value) {

		Objects.requireNonNull(attribute);
		Objects.requireNonNull(value);
		return addPair(attribute, formatValue(value));
	}

	/**
	 * Adds the given attribute and value list (i.e. a "JSON array").
	 *
	 * @param attribute
	 *            the name of the attribute (never <i>null</i>)
	 * @param values
	 *            the values
	 * @return this {@link JsonBlock}
	 * @throws NullPointerException
	 *             if either of the arguments is <i>null</i>
	 */
	public JsonBlock addAll(String attribute, Object...values) {

		Objects.requireNonNull(attribute);
		Objects.requireNonNull(values);
		return addPair(attribute, "[%s]".formatted(formatValues(values)));
	}

	/**
	 * Returns the {@link String} representation of this {@link JsonBlock}.
	 *
	 * @return this {@link JsonBlock} as a {@link String} (never <i>null</i>)
	 */
	@Override
	public String toString() {

		return "{" +//
				attributeValuePairs//
					.stream()
					.map(pair -> "%s:%s".formatted(pair.getFirst(), pair.getSecond()))
					.collect(Collectors.joining(","))
				+ "}";
	}

	private JsonBlock addPair(String attribute, String formattedValue) {

		this.attributeValuePairs.add(new Pair<>("\"%s\"".formatted(attribute), formattedValue));
		return this;
	}

	private String formatValue(Object value) {

		Objects.requireNonNull(value);
		if (value instanceof String) {
			return "\"%s\"".formatted(value);
		} else {
			return "%s".formatted(value);
		}
	}

	private String formatValues(Object[] values) {

		return Arrays//
			.asList(values)
			.stream()
			.map(this::formatValue)
			.collect(Collectors.joining(","));
	}
}
