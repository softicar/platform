package com.softicar.platform.common.core.properties;

import java.math.BigDecimal;
import java.util.function.Function;

/**
 * A factory for {@link SystemProperty} based instances of {@link IProperty}.
 *
 * @author Alexander Schmidt
 */
public class SystemPropertyFactory extends AbstractPropertyFactory {

	public SystemPropertyFactory() {

		this("");
	}

	public SystemPropertyFactory(String namePrefix) {

		super(namePrefix);
	}

	public IProperty<Integer> createIntegerProperty(String name, Integer defaultValue) {

		return new SystemProperty<>(createName(name), defaultValue, Integer::parseInt);
	}

	public IProperty<String> createStringProperty(String name, String defaultValue) {

		return new SystemProperty<>(createName(name), defaultValue, Function.identity());
	}

	public IProperty<Boolean> createBooleanProperty(String name, Boolean defaultValue) {

		return new SystemProperty<>(createName(name), defaultValue, Boolean::parseBoolean);
	}

	public IProperty<BigDecimal> createBigDecimalProperty(String name, BigDecimal defaultValue) {

		return new SystemProperty<>(createName(name), defaultValue, BigDecimal::new);
	}
}
