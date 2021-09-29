package com.softicar.platform.common.core.properties;

import java.io.File;
import java.util.function.Function;

/**
 * A factory for instances of {@link IProperty}.
 *
 * @author Oliver Richers
 */
public class PropertyFactory extends AbstractPropertyFactory {

	private final IPropertyMap propertyMap;

	public PropertyFactory(IPropertyMap propertyMap, String namePrefix) {

		super(namePrefix);
		this.propertyMap = propertyMap;
	}

	/**
	 * Creates a boolean property.
	 * <p>
	 * The textual value of a boolean property may be <i>true</i>, <i>yes</i> or
	 * <i>1</i> for the value to represent <i>true</i>. For <i>false</i> the
	 * textual value may be <i>false</i>, <i>no</i> or <i>0</i>.
	 *
	 * @param name
	 *            the name of the property
	 * @param defaultValue
	 *            the default value (may be null)
	 */
	public IProperty<Boolean> createBooleanProperty(String name, Boolean defaultValue) {

		return new Property<>(propertyMap, createName(name), defaultValue, this::parseBoolean);
	}

	/**
	 * Creates a property denoting an {@link Enum}.
	 * <p>
	 * The textual value of a property must exactly match the name of a
	 * enumerator.
	 *
	 * @param enumClass
	 *            the class of the {@link Enum}
	 * @param name
	 *            the name of the property
	 * @param defaultValue
	 *            the default value (may be null)
	 */
	public <E extends Enum<E>> IProperty<E> createEnumProperty(Class<E> enumClass, String name, E defaultValue) {

		return new Property<>(propertyMap, createName(name), defaultValue, valueString -> Enum.valueOf(enumClass, valueString));
	}

	/**
	 * Creates a property denoting a {@link File}.
	 * <p>
	 * A file property automatically expands leading tilde (~) characters into
	 * the home folder of the user, as defined by
	 * {@link SystemPropertyEnum#USER_HOME}. This is also works for the default
	 * value.
	 *
	 * @param name
	 *            the name of the property
	 * @param defaultValue
	 *            the default value (may be null)
	 */
	public IProperty<File> createFileProperty(String name, String defaultValue) {

		return new Property<>(propertyMap, createName(name), parseFile(defaultValue), this::parseFile);
	}

	public IProperty<Integer> createIntegerProperty(String name, Integer defaultValue) {

		return new Property<>(propertyMap, createName(name), defaultValue, Integer::parseInt);
	}

	public IProperty<Long> createLongProperty(String name, Long defaultValue) {

		return new Property<>(propertyMap, createName(name), defaultValue, Long::parseLong);
	}

	public IProperty<String> createStringProperty(String name, String defaultValue) {

		return new Property<>(propertyMap, createName(name), defaultValue, Function.identity());
	}

	private Boolean parseBoolean(String stringValue) {

		if (isTrue(stringValue)) {
			return true;
		} else if (isFalse(stringValue)) {
			return false;
		} else {
			throw new IllegalArgumentException(String.format("Invalid boolean constant '%s'.", stringValue));
		}
	}

	private static boolean isTrue(String stringValue) {

		return stringValue.equalsIgnoreCase("true") || stringValue.equalsIgnoreCase("yes") || stringValue.equals("1");
	}

	private boolean isFalse(String stringValue) {

		return stringValue.equalsIgnoreCase("false") || stringValue.equalsIgnoreCase("no") || stringValue.equals("0");
	}

	public File parseFile(String valueString) {

		if (valueString == null) {
			return null;
		} else if (valueString.equals("~")) {
			valueString = SystemPropertyEnum.USER_HOME.get();
		} else if (valueString.startsWith("~" + SystemPropertyEnum.FILE_SEPARATOR.get())) {
			valueString = SystemPropertyEnum.USER_HOME.get() + valueString.substring(1);
		}

		return new File(valueString);
	}
}
