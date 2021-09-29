package com.softicar.platform.core.module.component.external;

import com.softicar.platform.core.module.component.external.key.IExternalComponentKey;
import com.softicar.platform.core.module.component.external.type.ExternalComponentType;

/**
 * An external component, with a specific {@link IExternalComponentKey} and
 * additional information.
 *
 * @author Alexander Schmidt
 */
public interface IExternalComponent {

	/**
	 * Returns the {@link IExternalComponentKey} of this component.
	 *
	 * @return the {@link IExternalComponentKey} (never <i>null</i>)
	 */
	IExternalComponentKey getKey();

	/**
	 * Returns the type of this component.
	 *
	 * @return the component type (never <i>null</i>)
	 */
	default ExternalComponentType getType() {

		return getKey().getType();
	}

	/**
	 * Returns the name of this component.
	 *
	 * @return the component name (never <i>null</i>)
	 */
	default String getName() {

		return getKey().getName();
	}

	/**
	 * Returns the description of this component.
	 *
	 * @return the component description (may be <i>null</i>)
	 */
	String getDescription();

	/**
	 * Returns the version of this component.
	 *
	 * @return the component version (may be <i>null</i>)
	 */
	String getVersion();

	/**
	 * Returns the license of this component.
	 *
	 * @return the component license (may be <i>null</i>)
	 */
	String getLicense();
}
