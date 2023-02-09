package com.softicar.platform.common.io.mime;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Parses a textual representation of a mime type into {@link IMimeType}.
 *
 * @author Oliver Richers
 */
public class MimeTypeParser {

	private static final String TOKEN = "[^/;=\\\\s]+";
	private static final Pattern MIMETYPE_PATTERN = compileTemplate("^(T)/(T)((;T=T)*)$");
	private static final Pattern PARAMETER_PATTERN = compileTemplate(";(T)=(T)");
	private final String text;

	public MimeTypeParser(String text) {

		this.text = text;
	}

	public Optional<IMimeType> parse() {

		var matcher = MIMETYPE_PATTERN.matcher(text);
		if (matcher.find()) {
			return Optional.of(new ParsedMimeType(matcher.group(1), matcher.group(2), parseParameters(matcher.group(3))));
		} else {
			return Optional.empty();
		}
	}

	public IMimeType parseOrThrow() {

		return parse().orElseThrow(() -> new RuntimeException("Illegal mime type: '%s'".formatted(text)));
	}

	private Map<String, String> parseParameters(String parametersString) {

		var parameters = new TreeMap<String, String>();
		var parameterMatcher = PARAMETER_PATTERN.matcher(parametersString);
		while (parameterMatcher.find()) {
			parameters.put(parameterMatcher.group(1), parameterMatcher.group(2));
		}
		return parameters;
	}

	private static Pattern compileTemplate(String regexTemplate) {

		var regex = regexTemplate//
			.replaceAll(";", "\\\\s*;\\\\s*")
			.replaceAll("=", "\\\\s*=\\\\s*")
			.replaceAll("T", TOKEN);
		return Pattern.compile(regex);
	}

	private static class ParsedMimeType implements IMimeType {

		private final String type;
		private final String subtype;
		private final Map<String, String> parameters;

		public ParsedMimeType(String type, String subtype, Map<String, String> parameters) {

			this.type = type;
			this.subtype = subtype;
			this.parameters = parameters;
		}

		@Override
		public String getIdentifier() {

			return type + '/' + subtype;
		}

		@Override
		public Collection<String> getParameters() {

			return parameters.keySet();
		}

		@Override
		public Optional<String> getParameter(String parameter) {

			return Optional.ofNullable(parameters.get(parameter));
		}

		@Override
		public String toString() {

			var identifierStream = Stream.of(getIdentifier());
			var parameterStream = parameters//
				.entrySet()
				.stream()
				.map(entry -> entry.getKey() + '=' + entry.getValue());
			return Stream//
				.concat(identifierStream, parameterStream)
				.collect(Collectors.joining("; "));
		}
	}
}
