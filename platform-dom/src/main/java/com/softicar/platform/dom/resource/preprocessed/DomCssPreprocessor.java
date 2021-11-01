package com.softicar.platform.dom.resource.preprocessed;

import java.util.Objects;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class DomCssPreprocessor {

	private static final Pattern RESOURCE_PLACEHOLDER_PATTERN = Pattern.compile("<%%(.*?)%%>");
	private final Function<String, String> resourceUrlFactory;

	/**
	 * Constructs a new {@link DomCssPreprocessor}.
	 *
	 * @param resourceUrlFactory
	 *            a {@link Function} that derives a resource URL from a
	 *            placeholder text (never <i>null</i>)
	 */
	public DomCssPreprocessor(Function<String, String> resourceUrlFactory) {

		this.resourceUrlFactory = Objects.requireNonNull(resourceUrlFactory);
	}

	public String preprocess(String fileContent) {

		Objects.requireNonNull(fileContent);
		Matcher matcher = RESOURCE_PLACEHOLDER_PATTERN.matcher(fileContent);
		var output = new StringBuilder();
		int offset = 0;
		while (matcher.find(offset)) {
			int start = matcher.start();
			int end = matcher.end();
			String placeholderText = matcher.group(1).trim();
			String replacementText = resourceUrlFactory.apply(placeholderText);
			output//
				.append(fileContent.substring(offset, start))
				.append(replacementText);
			offset = end;
		}
		return output//
			.append(fileContent.substring(offset, fileContent.length()))
			.toString();
	}
}
