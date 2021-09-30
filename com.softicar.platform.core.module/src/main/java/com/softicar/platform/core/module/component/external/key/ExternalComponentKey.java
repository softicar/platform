package com.softicar.platform.core.module.component.external.key;

import com.softicar.platform.core.module.component.external.type.ExternalComponentType;
import java.util.Objects;

/**
 * Common implementation of {@link IExternalComponentKey}.
 *
 * @author Alexander Schmidt
 */
public class ExternalComponentKey implements IExternalComponentKey {

	private final ExternalComponentType type;
	private final String name;

	/**
	 * Constructs a new {@link ExternalComponentKey}.
	 *
	 * @param type
	 *            the component type (never <i>null</i>)
	 * @param name
	 *            the component name (never <i>null</i>)
	 * @throws IllegalArgumentException
	 *             if the given name is blank
	 */
	public ExternalComponentKey(ExternalComponentType type, String name) {

		this.type = Objects.requireNonNull(type);
		this.name = validateName(name);
	}

	@Override
	public ExternalComponentType getType() {

		return type;
	}

	@Override
	public String getName() {

		return name;
	}

	private String validateName(String name) {

		Objects.requireNonNull(name);
		if (name.isBlank()) {
			throw new IllegalArgumentException();
		}
		return name;
	}
}
