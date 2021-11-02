package com.softicar.platform.emf.data.table.export.node;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.common.date.DayTime;

/**
 * Facilitates type safe content handling during the node conversion process.
 *
 * @author Alexander Schmidt
 */
public class TableExportTypedNodeValue {

	private final TableExportNodeValueType valueType;
	private Object value = null;

	public TableExportTypedNodeValue(TableExportNodeValueType valueType, Object value) {

		assertValidValueType(valueType);
		assertCompatibleValueType(valueType.get(), value);

		this.valueType = valueType;
		this.value = value;
	}

	public TableExportNodeValueType getValueType() {

		return valueType;
	}

	public Object getValue() {

		return value;
	}

	// ----

	public Day getDay() {

		return castTo(Day.class, value);
	}

	public DayTime getDayTime() {

		return castTo(DayTime.class, value);
	}

	public Number getNumber() {

		return castTo(Number.class, value);
	}

	public String getString() {

		return castTo(String.class, value);
	}

	// ----

	/**
	 * Trims the contained String value. Throws an Exception in vase
	 * {@link TableExportNodeValueType} is not {@link TableExportNodeValueType#STRING}.
	 */
	public void trimString() {

		if (getValueType() == TableExportNodeValueType.STRING) {

			String str = getString();
			if (str != null) {
				this.value = getString().trim();
			}
		} else {
			throw new SofticarDeveloperException(
				"This method must not be called for %s instances that have a %s different from %s.",
				TableExportTypedNodeValue.class.getSimpleName(),
				TableExportNodeValueType.class.getSimpleName(),
				getValueType().getClass().getSimpleName());
		}
	}

	// ----

	private <T> T castTo(Class<T> targetClass, Object value) {

		if (value != null) {
			if (targetClass.isInstance(value)) {
				return CastUtils.cast(value);
			} else {
				throw new InvalidGetterException(targetClass, value);
			}
		} else {
			return null;
		}
	}

	private static void assertValidValueType(TableExportNodeValueType valueType) {

		if (valueType == null) {
			throw new MissingValueTypeException();
		} else if (valueType.get().equals(Object.class)) {
			throw new InvalidValueClassException(valueType);
		}
	}

	private static void assertCompatibleValueType(Class<?> valueClass, Object value) {

		if (value != null && !valueClass.isInstance(value)) {
			throw new IncompatibleValueClassException(valueClass, value);
		}
	}

	// ----

	private static class IncompatibleValueClassException extends SofticarDeveloperException {

		public IncompatibleValueClassException(Class<?> valueClass, Object value) {

			super("Incompatible types: %s (%s) is not a %s.", value, value.getClass().getSimpleName(), valueClass.getSimpleName());
		}
	}

	private static class InvalidGetterException extends SofticarDeveloperException {

		public InvalidGetterException(Class<?> targetClass, Object value) {

			super(
				"Cannot fetch %s (%s) as %s. Determine the value type before calling the appropriate getter.",
				value,
				value.getClass().getSimpleName(),
				targetClass.getSimpleName());
		}
	}

	private static class MissingValueTypeException extends SofticarDeveloperException {

		public MissingValueTypeException() {

			super("No value type provided.");
		}
	}

	private static class InvalidValueClassException extends SofticarDeveloperException {

		public InvalidValueClassException(TableExportNodeValueType valueType) {

			super("%s is not allowed as a value type.", valueType.get().getSimpleName());
		}
	}
}
