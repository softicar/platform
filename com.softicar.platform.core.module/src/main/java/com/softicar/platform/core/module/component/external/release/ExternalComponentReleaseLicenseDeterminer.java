package com.softicar.platform.core.module.component.external.release;

import com.softicar.platform.core.module.component.external.license.rule.ExternalComponentLicenseRule;
import com.softicar.platform.core.module.license.License;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

/**
 * Determines the {@link License} of a given {@link ExternalComponentRelease},
 * according to a {@link Collection} of {@link ExternalComponentLicenseRule}
 * definitions.
 *
 * @author Alexander Schmidt
 */
public class ExternalComponentReleaseLicenseDeterminer {

	private final Collection<ExternalComponentLicenseRule> rules;

	/**
	 * Constructs a new {@link ExternalComponentReleaseLicenseDeterminer}.
	 *
	 * @param rules
	 *            the {@link ExternalComponentLicenseRule} definitions from
	 *            which license information shall be inferred (never
	 *            <i>null</i>)
	 */
	public ExternalComponentReleaseLicenseDeterminer(Collection<ExternalComponentLicenseRule> rules) {

		this.rules = Objects.requireNonNull(rules);
	}

	/**
	 * Determines {@link License} of the given {@link ExternalComponentRelease},
	 * according to the previously defined {@link ExternalComponentLicenseRule}
	 * definitions.
	 *
	 * @param release
	 *            the {@link ExternalComponentRelease} for which a
	 *            {@link License} shall be determined (never <i>null</i>)
	 * @return the determined {@link License}
	 */
	public Optional<License> determineLicense(ExternalComponentRelease release) {

		Objects.requireNonNull(release);
		return rules//
			.stream()
			.map(new ExternalComponentReleaseMatcher(release)::getLicenseIfMatches)
			.flatMap(Optional::stream)
			.findFirst();
	}
}
