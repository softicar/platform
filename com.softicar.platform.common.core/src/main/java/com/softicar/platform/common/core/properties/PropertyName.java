package com.softicar.platform.common.core.properties;

import com.softicar.platform.common.core.immutable.ImmutableStringWrapper;

/**
 * The name of an {@link IProperty}.
 */
public class PropertyName extends ImmutableStringWrapper {

	protected PropertyName(String name) {

		super(name.trim().toLowerCase());
	}
}
