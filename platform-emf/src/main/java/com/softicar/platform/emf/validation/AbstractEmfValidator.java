package com.softicar.platform.emf.validation;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import com.softicar.platform.emf.validation.result.IEmfValidationResult;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Base class for custom implementations of {@link IEmfValidator}.
 * <p>
 * This class simplifies the implementation of custom {@link IEmfValidator}
 * classes. Just implement the abstract {@link #validate()} method and call
 * {@link #addError} as necessary. Additionally, the provided assert-methods can
 * be used for simple checks.
 *
 * @author Oliver Richers
 */
public abstract class AbstractEmfValidator<R extends IEmfTableRow<R, ?>> implements IEmfValidator<R> {

	protected R tableRow;
	protected IEmfValidationResult result;

	@Override
	public final void validate(R tableRow, IEmfValidationResult result) {

		this.tableRow = Objects.requireNonNull(tableRow);
		this.result = Objects.requireNonNull(result);

		validate();
	}

	protected abstract void validate();

	/**
	 * Adds the given error message to the {@link IEmfValidationResult} for the
	 * specified {@link IDbField}.
	 * <p>
	 * This is just a convenience method.
	 *
	 * @param field
	 *            the {@link IDbField} (never <i>null</i>)
	 * @param errorMessage
	 *            the error message (never <i>null</i>)
	 */
	protected void addError(IDbField<R, ?> field, IDisplayString errorMessage) {

		result.addError(getAttribute(field), errorMessage);
	}

	/**
	 * Returns the {@link IEmfAttribute} of the given {@link IDbField}.
	 * <p>
	 * This is just a convenience method.
	 *
	 * @param field
	 *            the {@link IDbField} (never <i>null</i>)
	 * @return the {@link IEmfAttribute} (never <i>null</i>)
	 */
	protected <V> IEmfAttribute<R, V> getAttribute(IDbField<R, V> field) {

		return tableRow.table().getAttribute(field);
	}

	/**
	 * Returns the title of the {@link IEmfAttribute} of the given
	 * {@link IDbField}.
	 * <p>
	 * This is just a convenience method.
	 *
	 * @param field
	 *            the {@link IDbField} (never <i>null</i>)
	 * @return the title (never <i>null</i>)
	 */
	protected IDisplayString getTitle(IDbField<R, ?> field) {

		return getAttribute(field).getTitle();
	}

	// ------------------------------ assert methods ------------------------------ //

	/**
	 * Asserts that the value of the given field <b>is</b> <i>null</i>.
	 *
	 * @param field
	 *            the {@link IDbField} (never null)
	 */
	protected <V> void assertNull(IDbField<R, V> field) {

		V value = field.getValue(tableRow);
		if (value != null) {
			addError(field, EmfI18n.THE_ATTRIBUTE_ARG1_MUST_NOT_BE_DEFINED_BUT_WAS_ARG2.toDisplay(getTitle(field), value));
		}
	}

	/**
	 * Asserts that the value of the given field is <b>not</b> <i>null</i>.
	 *
	 * @param field
	 *            the {@link IDbField} (never null)
	 */
	protected <V> void assertNotNull(IDbField<R, V> field) {

		V value = field.getValue(tableRow);
		if (value == null) {
			addError(field, EmfI18n.THE_ATTRIBUTE_ARG1_IS_MANDATORY.toDisplay(getTitle(field)));
		}
	}

	/**
	 * Asserts that the value of the given {@link String} field is either
	 * <i>null</i> or empty.
	 *
	 * @param field
	 *            the {@link IDbField} (never null)
	 */
	protected void assertEmpty(IDbField<R, String> field) {

		String value = field.getValue(tableRow);
		if (value != null && !value.isEmpty()) {
			addError(field, EmfI18n.THE_ATTRIBUTE_ARG1_MUST_BE_EMPTY.toDisplay(getTitle(field)));
		}
	}

	/**
	 * Asserts that the value of the given {@link String} field is not
	 * <i>null</i> and not blank (as in {@link String#isBlank}).
	 *
	 * @param field
	 *            the {@link IDbField} (never null)
	 */
	protected void assertNotBlank(IDbField<R, String> field) {

		String value = field.getValue(tableRow);
		if (value == null || value.isBlank()) {
			addError(field, EmfI18n.THE_ATTRIBUTE_ARG1_MAY_NOT_BE_EMPTY.toDisplay(getTitle(field)));
		}
	}

	/**
	 * Asserts that the given condition evaluates to <i>true</i>.
	 * <p>
	 * This method is for backwards compatibility to the old validation API,
	 * which employed declarative validation. Instead you can now evaluate your
	 * conditions immediately and then call {@link #addError} directly on
	 * failure.
	 *
	 * @param condition
	 *            the condition to test (never null)
	 * @param messageSupplier
	 *            the {@link Supplier} for the error message to add if the
	 *            condition evaluates to <i>false</i> (never null)
	 * @param fields
	 *            the {@link IDbField} instances of the attributes to assign the
	 *            error message to (never <i>null</i>)
	 */
	@SafeVarargs
	protected final void assertCondition(Supplier<Boolean> condition, Supplier<IDisplayString> messageSupplier, IDbField<R, ?>...fields) {

		if (!condition.get()) {
			Stream.of(fields).forEach(field -> addError(field, messageSupplier.get()));
		}
	}
}
