package com.softicar.platform.core.module.component.external.release;

import com.softicar.platform.core.module.component.external.key.IExternalComponentKey;
import java.util.Objects;

/**
 * Represents a specific release of an external component, as identified via
 * {@link IExternalComponentKey} and version.
 *
 * @author Alexander Schmidt
 */
public class ExternalComponentRelease {

	private final IExternalComponentKey key;
	private final String version;

	/**
	 * Constructs a new {@link ExternalComponentRelease}.
	 *
	 * @param key
	 *            the component key (never <i>null</i>)
	 * @param version
	 *            the component version (may be <i>null</i>)
	 */
	public ExternalComponentRelease(IExternalComponentKey key, String version) {

		this.key = Objects.requireNonNull(key);
		this.version = version;
	}

	/**
	 * Returns the {@link IExternalComponentKey} of this release.
	 *
	 * @return the {@link IExternalComponentKey} (never <i>null</i>)
	 */
	public IExternalComponentKey getKey() {

		return key;
	}

	/**
	 * Returns the version of this release.
	 *
	 * @return the release version (may be <i>null</i>)
	 */
	public String getVersion() {

		return version;
	}
}
