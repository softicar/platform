package com.softicar.platform.common.core.java.classes.name.matcher;

import com.softicar.platform.common.core.java.classes.name.JavaClassName;
import java.util.regex.Pattern;

/**
 * An {@link IJavaClassNameMatcher} based on a <i>glob pattern</i>.
 * <p>
 * The only supported wildcards in the glob pattern are the double asterisk (**)
 * and the single asterisk (*). The former matches all characters, while the
 * latter matches all characters but the dot (.). All other characters are
 * treated literally.
 * <p>
 * Not all characters are allowed, only alphanumeric characters as well as the
 * underscore (_), dot (.), dollar ($) and asterisk (*) are legal.
 *
 * @author Oliver Richers
 */
public class JavaClassNameGlobPatternMatcher implements IJavaClassNameMatcher {

	private final String globPattern;
	private final Pattern regexPattern;

	/**
	 * Constructs this matcher with the given glob pattern.
	 *
	 * @param globPattern
	 *            the glob pattern (never null)
	 * @throws IllegalArgumentException
	 *             if the glob pattern is illegal, i.e. contains illegal
	 *             characters or is empty
	 */
	public JavaClassNameGlobPatternMatcher(String globPattern) {

		this.globPattern = globPattern;
		this.regexPattern = Pattern.compile(new InternalGlobPatternToRegexConverter(globPattern).convert());
	}

	@Override
	public boolean test(JavaClassName className) {

		return regexPattern.matcher(className.getName()).matches();
	}

	@Override
	public String toString() {

		return globPattern;
	}
}
