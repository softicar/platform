package com.softicar.platform.common.core.properties;

abstract class AbstractPropertyFactory {

	private final String namePrefix;

	public AbstractPropertyFactory(String namePrefix) {

		this.namePrefix = namePrefix;
	}

	protected PropertyName createName(String propertyName) {

		if (namePrefix.isEmpty()) {
			return new PropertyName(propertyName);
		} else {
			return new PropertyName(namePrefix + "." + propertyName);
		}
	}
}
