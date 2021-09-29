package com.softicar.platform.core.module.component.external.key;

import com.softicar.platform.core.module.component.external.type.ExternalComponentType;
import java.util.Comparator;

/**
 * Identifies an external component via type and name.
 *
 * @author Alexander Schmidt
 */
public interface IExternalComponentKey extends Comparable<IExternalComponentKey> {

	/**
	 * Returns the type of the component.
	 *
	 * @return the type (never <i>null</i>)
	 */
	ExternalComponentType getType();

	/**
	 * Returns the name of the component.
	 *
	 * @return the component name (never <i>null</i>)
	 */
	String getName();

	@Override
	default int compareTo(IExternalComponentKey other) {

		return Comparator//
			.comparing(IExternalComponentKey::getType)
			.thenComparing(IExternalComponentKey::getName)
			.compare(this, other);
	}
}
