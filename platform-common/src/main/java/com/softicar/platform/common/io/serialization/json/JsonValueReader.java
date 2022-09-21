package com.softicar.platform.common.io.serialization.json;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import com.softicar.platform.common.core.utils.DevNull;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Facilitates reading values from a JSON {@link String}.
 * <p>
 * Based upon "JsonPath" and its syntax.
 *
 * @author Alexander Schmidt
 */
public class JsonValueReader {

	private final DocumentContext context;

	/**
	 * Constructs a new {@link JsonValueReader}.
	 * <p>
	 * If the given JSON {@link String} is blank, a logically-empty JSON
	 * {@link String} will be assumed, to avoid a parsing error.
	 *
	 * @param json
	 *            the JSON {@link String} to process (never <i>null</i>)
	 */
	public JsonValueReader(String json) {

		Objects.requireNonNull(json);
		var configuration = Configuration.defaultConfiguration().setOptions(Option.SUPPRESS_EXCEPTIONS);
		this.context = JsonPath.using(configuration).parse(assumeMinimalJsonIfBlank(json));
	}

	/**
	 * Reads a {@link String} value from the given path inside the JSON
	 * {@link String}.
	 * <p>
	 * Returns an empty {@link Optional} if no such path exists inside the JSON
	 * {@link String}.
	 *
	 * @param path
	 *            the path, in "JsonPath" syntax (never <i>null</i>)
	 * @return the {@link String} value to which the given path points
	 */
	public Optional<String> readValue(String path) {

		String value = readInternal(path);
		return Optional.ofNullable(value);
	}

	/**
	 * Reads a {@link Boolean} value from the given path inside the JSON
	 * {@link String}.
	 * <p>
	 * Returns an empty {@link Optional} if no such path exists inside the JSON
	 * {@link String}.
	 *
	 * @param path
	 *            the path, in "JsonPath" syntax (never <i>null</i>)
	 * @return the {@link Boolean} value to which the given path points
	 */
	public Optional<Boolean> readBoolean(String path) {

		Boolean value = readInternal(path);
		return Optional.ofNullable(value);
	}

	/**
	 * Reads a {@link List} of {@link String} values from the given path inside
	 * the JSON {@link String}.
	 * <p>
	 * Returns an empty {@link List} if no such path exists inside the JSON
	 * {@link String}, or if the path does <b>not</b> point to an array of
	 * values.
	 *
	 * @param path
	 *            the path, in "JsonPath" syntax (never <i>null</i>)
	 * @return the {@link List} of {@link String} values to which the given path
	 *         points
	 */
	public List<String> readList(String path) {

		try {
			List<String> list = readInternal(path);
			return Optional.ofNullable(list).orElse(Collections.emptyList());
		} catch (ClassCastException exception) {
			DevNull.swallow(exception);
			return Collections.emptyList();
		}
	}

	private <T> T readInternal(String path) {

		Objects.requireNonNull(path);
		return context.read(path);
	}

	private String assumeMinimalJsonIfBlank(String json) {

		return json.isBlank()? "{}" : json;
	}
}
