package com.softicar.platform.common.string.tag;

import com.softicar.platform.common.core.exceptions.SofticarException;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Replaces tags in a given string with a replacement.
 * <p>
 * The tag matching is done case-sensitive.
 *
 * @author Oliver Richers
 */
public class TagReplacer {

	public static final String DEFAULT_PATTERN = "\\{\\{([a-zA-Z0-9-_\\.]+)\\}\\}";
	public static final String ANGLE_BRACKETS_PATTERN = "<(.*?)>";
	private final Map<String, String> tags;
	private final Pattern pattern;

	/**
	 * Creates a new instance with an empty tag map and uses the default tag
	 * pattern.
	 * <p>
	 * You can call {@link #addTag(String, String)} to add the tags to be
	 * replaced.
	 */
	public TagReplacer() {

		this(DEFAULT_PATTERN);
	}

	/**
	 * Creates a new empty instance with the given pattern.
	 *
	 * @param pattern
	 *            the pattern to use for tag matching
	 */
	public TagReplacer(String pattern) {

		this(new TreeMap<>(), pattern);
	}

	/**
	 * Creates a new instance with the given tag map and pattern.
	 *
	 * @param tags
	 *            a map from the tag to the replacement
	 * @param pattern
	 *            the pattern to use for tag matching
	 */
	public TagReplacer(Map<String, String> tags, String pattern) {

		this.tags = tags;
		this.pattern = Pattern.compile(pattern);
	}

	/**
	 * Removes all previously added tags.
	 */
	public void clear() {

		this.tags.clear();
	}

	/**
	 * Adds a new tag mapping.
	 *
	 * @param tag
	 *            the tag used for matching
	 * @param replacement
	 *            the replacement for the tag
	 */
	public void addTag(String tag, String replacement) {

		this.tags.put(tag, replacement);
	}

	/**
	 * Replaces all tags in the input string with the respective replacements.
	 *
	 * @param input
	 *            the input string to process
	 * @throws SofticarException
	 *             if a tag was references in the input text but not defined
	 * @return the input string after replacing the tags
	 */
	public String replace(String input) {

		StringBuffer buffer = new StringBuffer();
		Matcher matcher = pattern.matcher(input);
		while (matcher.find()) {
			String tag = matcher.group(1);
			String replacement = tags.get(tag);
			if (replacement == null) {
				throw new SofticarException("The tag '%s' was not defined.", tag);
			}

			matcher.appendReplacement(buffer, replacement);
		}
		matcher.appendTail(buffer);
		return buffer.toString();
	}
}
