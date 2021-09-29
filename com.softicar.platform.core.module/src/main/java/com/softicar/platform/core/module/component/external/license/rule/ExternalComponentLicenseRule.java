package com.softicar.platform.core.module.component.external.license.rule;

import com.softicar.platform.core.module.component.external.type.ExternalComponentType;
import com.softicar.platform.core.module.license.License;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Associates a triplet of component type, component name pattern and component
 * version pattern with a specific {@link License}.
 *
 * @author Alexander Schmidt
 */
public class ExternalComponentLicenseRule {

	private final ExternalComponentType type;
	private final Pattern namePattern;
	private final Pattern versionPattern;
	private final License license;

	/**
	 * Constructs a new {@link ExternalComponentLicenseRule}.
	 *
	 * @param type
	 *            the component type (never <i>null</i>)
	 * @param namePattern
	 *            a pattern to match a component name against (never
	 *            <i>null</i>)
	 * @param versionPattern
	 *            a pattern to match a component version against (never
	 *            <i>null</i>)
	 * @param license
	 *            the {@link License} that is assumed in case the type, the name
	 *            pattern and the version pattern match (never <i>null</i>)
	 */
	public ExternalComponentLicenseRule(ExternalComponentType type, String namePattern, String versionPattern, License license) {

		this.type = Objects.requireNonNull(type);
		this.namePattern = Pattern.compile(namePattern);
		this.versionPattern = Pattern.compile(versionPattern);
		this.license = Objects.requireNonNull(license);
	}

	/**
	 * Returns the component type.
	 *
	 * @return the component type (never <i>null</i>)
	 */
	public ExternalComponentType getType() {

		return type;
	}

	/**
	 * Returns the component name {@link Pattern}.
	 *
	 * @return the component name {@link Pattern} (never <i>null</i>)
	 */
	public Pattern getNamePattern() {

		return namePattern;
	}

	/**
	 * Returns the component version {@link Pattern}.
	 *
	 * @return the component version {@link Pattern} (never <i>null</i>)
	 */
	public Pattern getVersionPattern() {

		return versionPattern;
	}

	/**
	 * Returns the {@link License}.
	 *
	 * @return the {@link License} (never <i>null</i>)
	 */
	public License getLicense() {

		return license;
	}
}
