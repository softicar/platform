package com.softicar.platform.core.module.component.external.release;

import com.softicar.platform.common.string.regex.Patterns;
import com.softicar.platform.core.module.component.external.license.rule.ExternalComponentLicenseRule;
import com.softicar.platform.core.module.license.License;
import java.util.Objects;
import java.util.Optional;

class ExternalComponentReleaseMatcher {

	private final ExternalComponentRelease release;

	public ExternalComponentReleaseMatcher(ExternalComponentRelease release) {

		this.release = Objects.requireNonNull(release);
	}

	public Optional<License> getLicenseIfMatches(ExternalComponentLicenseRule rule) {

		if (matchesType(rule) && matchesName(rule) && matchesVersion(rule)) {
			return Optional.ofNullable(rule.getLicense());
		} else {
			return Optional.empty();
		}
	}

	private boolean matchesType(ExternalComponentLicenseRule rule) {

		return release.getKey().getType().equals(rule.getType());
	}

	private boolean matchesName(ExternalComponentLicenseRule rule) {

		return Patterns.matches(release.getKey().getName(), rule.getNamePattern());
	}

	private boolean matchesVersion(ExternalComponentLicenseRule rule) {

		return Optional//
			.ofNullable(release.getVersion())
			.map(version -> Patterns.matches(version, rule.getVersionPattern()))
			.orElse(false);
	}
}
