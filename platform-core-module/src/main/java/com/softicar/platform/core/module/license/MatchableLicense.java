package com.softicar.platform.core.module.license;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.regex.Pattern;

/**
 * An enumeration of matchable wrappers for well-known licenses.
 * <p>
 * Use {@link #matches(String)} to check whether a given license text matches
 * the respective {@link License}.
 *
 * @author Alexander Schmidt
 */
enum MatchableLicense {

	APACHE_2_0(//
			License.APACHE_2_0,//
			".*Apache (Software )?(License[\\s,]+)?(Version )?2\\.0.*",//
			"apache\\.org/licenses/LICENSE-2\\.0"),
	BOUNCY_CASTLE(License.BOUNCY_CASTLE,//
			".*Bouncy Castle License.*"),
	BSD_3_CLAUSE(//
			License.BSD_3_CLAUSE,//
			".*((Revised|New|Modified) BSD|BSD-3-Clause).*"),
	BSD_4_CLAUSE(//
			License.BSD_4_CLAUSE,//
			".*(?!(Revised|New|Modified) )BSD.*"),
	CDDL_1_0(//
			License.CDDL_1_0,//
			".*COMMON DEVELOPMENT AND DISTRIBUTION LICENSE \\(CDDL\\) Version 1\\.0.*"),
	EPL_1_0(//
			License.EPL_1_0,//
			".*Eclipse Public License[\\s\\-]*v\\s*1\\.0.*",//
			"eclipse\\.org/legal/epl-1\\.0"),
	EPL_2_0(//
			License.EPL_2_0,//
			".*Eclipse Public License[\\s\\-]*v\\s*2\\.0.*",//
			"eclipse\\.org/legal/epl-2\\.0"),
	LGPL_2_1(//
			License.LGPL_2_1,//
			".*GNU Lesser General Public License, version 2\\.1.*"),
	MIT(//
			License.MIT,//
			".*MIT License.*");

	private final License license;
	private final Collection<Pattern> patterns;

	private MatchableLicense(License license, String pattern, String...morePatterns) {

		this.license = license;
		this.patterns = compilePatterns(pattern, morePatterns);
	}

	/**
	 * Returns all enumerated entries in a {@link Collection}.
	 *
	 * @return all enumerated entries (never <i>null</i>)
	 */
	public static Collection<MatchableLicense> getAll() {

		return Arrays.asList(values());
	}

	/**
	 * Returns the wrapped {@link License}.
	 *
	 * @return the wrapped {@link License} (never <i>null</i>)
	 */
	public License getLicense() {

		return license;
	}

	/**
	 * Determines whether the given license text matches any of the patterns.
	 *
	 * @param text
	 *            the license text (never <i>null</i>)
	 * @return <i>true</i> if the license text matches any of the patterns;
	 *         <i>false</i> otherwise
	 */
	public boolean matches(String text) {

		return patterns//
			.stream()
			.anyMatch(pattern -> pattern.matcher(text).find());
	}

	private Collection<Pattern> compilePatterns(String pattern, String...morePatterns) {

		var patterns = new ArrayList<Pattern>();
		patterns.add(Pattern.compile(pattern));
		Arrays.asList(morePatterns).stream().map(Pattern::compile).forEach(patterns::add);
		return patterns;
	}
}
