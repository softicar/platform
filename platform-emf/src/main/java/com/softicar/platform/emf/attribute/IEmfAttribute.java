package com.softicar.platform.emf.attribute;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.ITestMarker;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.dom.element.IDomElement;
import com.softicar.platform.emf.attribute.field.foreign.row.IEmfForeignRowAttribute;
import com.softicar.platform.emf.attribute.input.IEmfInput;
import com.softicar.platform.emf.data.table.column.handler.IEmfDataTableRowBasedColumnHandler;
import com.softicar.platform.emf.management.EmfManagementDiv;
import com.softicar.platform.emf.predicate.IEmfPredicate;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import com.softicar.platform.emf.validation.result.IEmfValidationResult;
import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * Represents an attribute of an {@link IEmfTableRow}.
 */
public interface IEmfAttribute<R extends IEmfTableRow<R, ?>, V> {

	/**
	 * Returns the {@link IEmfTable} of this attribute.
	 *
	 * @return the table (never null)
	 */
	IEmfTable<R, ?, ?> getTable();

	/**
	 * Returns the {@link IDbField} that corresponds to this attribute.
	 *
	 * @return the {@link IDbField} that corresponds to this attribute
	 */
	Optional<IDbField<R, V>> getField();

	/**
	 * Returns the class of the values of this attribute.
	 *
	 * @return the class of the values of this attribute (never null)
	 */
	Class<V> getValueClass();

	/**
	 * Returns a comparator for values of this attribute.
	 *
	 * @return a comparator for values of this attribute
	 */
	Optional<Comparator<V>> getValueComparator();

	/**
	 * Pre-fetches (burst-loads) the values of this attribute for the given
	 * collection of entities.
	 *
	 * @param entities
	 *            the entities for which the values of this attribute should be
	 *            pre-fetched (never null)
	 */
	void prefetchValues(Collection<R> entities);

	/**
	 * Returns the value of this attribute from the given {@link IEmfTableRow}.
	 *
	 * @param tableRow
	 *            the {@link IEmfTableRow} to retrieve the value from (never
	 *            null)
	 * @return the value of this attribute from the given {@link IEmfTableRow}
	 *         (may be null)
	 */
	V getValue(R tableRow);

	/**
	 * Sets the given value for this attribute of the given
	 * {@link IEmfTableRow}.
	 *
	 * @param tableRow
	 *            the {@link IEmfTableRow} on which the value of this attribute
	 *            should be set (never null)
	 * @param value
	 *            the value to set (may be null)
	 */
	void setValue(R tableRow, V value);

	/**
	 * Returns the title of this attribute.
	 *
	 * @return the title of this attribute (never null)
	 */
	IDisplayString getTitle();

	/**
	 * Returns the {@link ITestMarker} to be used in the UI.
	 *
	 * @return the {@link ITestMarker} for this {@link IEmfAttribute} (never
	 *         <i>null</i>)
	 */
	ITestMarker getTestMarker();

	/**
	 * Returns true if this attribute is the scope, false otherwise.
	 *
	 * @return true if this is the scope attribute, false otherwise
	 */
	boolean isScope();

	/**
	 * Returns true if this attribute is concealed, false otherwise.
	 *
	 * @return true if this attribute is concealed, false otherwise
	 */
	boolean isConcealed();

	/**
	 * Returns true if this attribute is editable, false otherwise.
	 * <p>
	 * Please note that non-editable does not imply immutable. A non-editable
	 * attribute is not writable by the user directly, but its value might be
	 * changed indirectly.
	 *
	 * @return true if this attribute is editable, false otherwise
	 */
	boolean isEditable();

	/**
	 * Returns true if this attribute is immutable, false otherwise.
	 * <p>
	 * Please note that immutable does not imply non-editable. An immutable
	 * attribute will never change its value, but only <b>after</b> its
	 * creation, that is, the user may be able to define its initial value as
	 * long as the attribute is editable.
	 *
	 * @return true if this attribute is immutable, false otherwise
	 */
	boolean isImmutable();

	/**
	 * Returns true if this attribute is transient, false otherwise.
	 *
	 * @return true if this attribute is transient, false otherwise
	 */
	boolean isTransient();

	/**
	 * Returns true if this attribute is visible for the given
	 * {@link IEmfTableRow}, false otherwise.
	 *
	 * @param tableRow
	 *            the {@link IEmfTableRow} for which the visible state of this
	 *            attribute should be tested (never null)
	 * @return true if this attribute is visible for the given
	 *         {@link IEmfTableRow}, false otherwise
	 */
	boolean isVisible(R tableRow);

	/**
	 * Returns the visible predicate defined for this attribute.
	 *
	 * @return the visible predicate of this attribute (never null)
	 */
	IEmfPredicate<R> getPredicateVisible();

	/**
	 * Returns true if this attribute is editable for the given
	 * {@link IEmfTableRow}, false otherwise.
	 *
	 * @param tableRow
	 *            the {@link IEmfTableRow} for which the editable state of this
	 *            attribute should be tested (never null)
	 * @return true if this attribute is editable for the given
	 *         {@link IEmfTableRow}, false otherwise
	 */
	boolean isEditable(R tableRow);

	/**
	 * Returns the editable predicate defined for this attribute.
	 *
	 * @return the editable predicate of this attribute (never null)
	 */
	IEmfPredicate<R> getPredicateEditable();

	/**
	 * Returns true if this attribute is mandatory for the given
	 * {@link IEmfTableRow}, false otherwise.
	 *
	 * @param tableRow
	 *            the {@link IEmfTableRow} for which the the mandatory state of
	 *            this attribute should be tested (never null)
	 * @return true if this attribute is mandatory for the given entity, false
	 *         otherwise
	 */
	boolean isMandatory(R tableRow);

	/**
	 * Returns the mandatory predicate defined for this attribute.
	 *
	 * @return the mandatory predicate of this attribute (never null)
	 */
	IEmfPredicate<R> getPredicateMandatory();

	/**
	 * Tries to deduce and set the value of this {@link IEmfAttribute} for the
	 * given {@link IEmfTableRow}.
	 * <p>
	 * By default, this method does nothing, but the {@link IEmfAttribute} can
	 * be configured to deduce its value.
	 *
	 * @param tableRow
	 *            the {@link IEmfTableRow} (never <i>null</i>)
	 */
	void applyValueDeducer(R tableRow);

	/**
	 * Returns a new {@link IDomElement} that displays the value of this
	 * attribute for the given {@link IEmfTableRow}.
	 *
	 * @param tableRow
	 *            the {@link IEmfTableRow} for which the value of this attribute
	 *            should be displayed (never <i>null</i>)
	 * @return a new {@link IDomElement} that displays the value of this
	 *         attribute for the given {@link IEmfTableRow} (never <i>null</i>)
	 */
	IDomElement createDisplay(R tableRow);

	/**
	 * Returns a new {@link IDomElement} that displays the value of this
	 * attribute for the given {@link IEmfTableRow}, optimized to be shown in
	 * tabular display elements like {@link EmfManagementDiv}.
	 *
	 * @param tableRow
	 *            the {@link IEmfTableRow} for which the value of this attribute
	 *            should be displayed (never <i>null</i>)
	 * @return a new {@link IDomElement} that displays the value of this
	 *         attribute for the given {@link IEmfTableRow} (never <i>null</i>)
	 * @see EmfManagementDiv
	 */
	IDomElement createTabularDisplay(R tableRow);

	/**
	 * Returns an optional factory for a display element that describes this
	 * {@link IEmfAttribute}.
	 *
	 * @return a factory of help text display elements
	 */
	Optional<Supplier<IDomElement>> getHelpDisplayFactory();

	/**
	 * Returns a new {@link IEmfInput} element to enter the value of this
	 * attribute for the given {@link IEmfTableRow}.
	 *
	 * @param tableRow
	 *            the {@link IEmfTableRow} for which the value of this attribute
	 *            should be entered (never <i>null</i>)
	 * @return a new {@link IEmfInput} element to enter the value of this
	 *         attribute for the given {@link IEmfTableRow} (never <i>null</i>)
	 */
	IEmfInput<V> createInput(R tableRow);

	/**
	 * Returns a new {@link IEmfDataTableRowBasedColumnHandler} to display the
	 * values of this attribute in a table.
	 *
	 * @return a new {@link IEmfDataTableRowBasedColumnHandler} to display the
	 *         values of this attribute in a table (never null)
	 * @see EmfManagementDiv
	 */
	IEmfDataTableRowBasedColumnHandler<R, V> createColumnHandler();

	/**
	 * Validates the given {@link IEmfTableRow} with respect to this attribute.
	 *
	 * @param tableRow
	 *            the {@link IEmfTableRow} to validate (never null)
	 * @param result
	 *            the {@link IEmfValidationResult} object (never null)
	 */
	void validate(R tableRow, IEmfValidationResult result);

	/**
	 * Returns the original attribute.
	 * <p>
	 * For normal attributes, the original attribute is the attribute itself.
	 * For borrowed attributes, this returns the original attribute.
	 *
	 * @return the original attribute (never null)
	 */
	default IEmfAttribute<?, V> getOriginalAttribute() {

		return this;
	}

	/**
	 * Tries to cast this {@link IEmfAttribute} into an
	 * {@link IEmfForeignRowAttribute}.
	 *
	 * @param <F>
	 *            the foreign {@link IEmfTableRow} type
	 * @return the optional {@link IEmfForeignRowAttribute}
	 */
	@SuppressWarnings("unchecked")
	default <F extends IEmfTableRow<F, ?>> Optional<IEmfForeignRowAttribute<R, F>> asForeignRowAttribute() {

		if (this instanceof IEmfForeignRowAttribute) {
			return Optional.of((IEmfForeignRowAttribute<R, F>) this);
		} else {
			return Optional.empty();
		}
	}
}
