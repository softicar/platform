package com.softicar.platform.core.module.license;

import java.util.Objects;
import java.util.Optional;

/**
 * Determines a {@link License} from a given text, e.g. from the content of a
 * typical {@code LICENSE.txt} file.
 *
 * @author Alexander Schmidt
 */
public class LicenseDeterminer {

	/**
	 * Determines a {@link License} from the given text.
	 *
	 * @param licenseText
	 *            the text that contains the license definition (never
	 *            <i>null</i>)
	 * @return the determined {@link License}
	 */
	public Optional<License> determineFromText(String licenseText) {

		Objects.requireNonNull(licenseText);
		return MatchableLicense//
			.getAll()
			.stream()
			.filter(license -> license.matches(licenseText))
			.map(MatchableLicense::getLicense)
			.findFirst();
	}
}
