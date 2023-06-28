package com.softicar.platform.dom.elements;

import com.softicar.platform.common.container.comparator.OrderDirection;
import com.softicar.platform.common.container.comparator.ReverseComparator;
import com.softicar.platform.common.container.list.ListFactory;
import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.dom.input.DomOption;
import com.softicar.platform.dom.input.DomSelect;
import com.softicar.platform.dom.node.IDomNode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.StreamSupport;

/**
 * Abstract base class for drop-down input elements.
 * <p>
 * Maintains an internal mapping between {@link DomOption} and typed value. Each
 * value has a unique ID and an {@link IDisplayString} shown to the user. A
 * special "nil value" may be contained, to allow for the selection of
 * "nothing".
 *
 * @param <T>
 *            the type of the selectable values
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public abstract class AbstractDomValueSelect<T> extends DomSelect<DomValueOption<T>> {

	private final Map<Integer, DomValueOption<T>> valueMap;
	private DomValueOption<T> nilOption;

	public AbstractDomValueSelect() {

		this.valueMap = new TreeMap<>();
		this.nilOption = null;
	}

	// -------------------------------- ADDING VALUES -------------------------------- //

	/**
	 * Adds the value as a new option to this input element.
	 * <p>
	 * If a value with the same id has previously been added, it will be
	 * overridden with the new value. The old option will be removed and a new
	 * option will in inserted at the same place.
	 *
	 * @param value
	 *            the value to add
	 */
	public final void addValue(T value) {

		DomValueOption<T> option = new DomValueOption<>(value, getValueDisplayString(value));
		DomValueOption<T> previousOption = valueMap.put(getValueId(value), option);
		if (previousOption == null) {
			appendChild(option);
		} else {
			replaceChild(option, previousOption);
		}
	}

	/**
	 * Adds the specified values as new options to this input element.
	 *
	 * @param values
	 *            the values to add (never null)
	 */
	public final void addValues(Iterable<? extends T> values) {

		for (T value: values) {
			addValue(value);
		}
	}

	/**
	 * Adds the specified values as new options to this input element.
	 *
	 * @param values
	 *            the values to add (never null)
	 */
	@SafeVarargs
	public final void addValues(T...values) {

		for (T value: values) {
			addValue(value);
		}
	}

	/**
	 * Adds the specified values as options to this input element.
	 * <p>
	 * The values are sorted by their {@link IDisplayString}, in ascending
	 * order. Values with equal {@link IDisplayString} are sorted by ID.
	 *
	 * @param values
	 *            the values to add (never null)
	 */
	public final void addValuesSortedByDisplayString(Iterable<? extends T> values) {

		addValuesSortedByDisplayString(values, OrderDirection.ASCENDING);
	}

	/**
	 * Adds the specified values as options to this input element.
	 * <p>
	 * The values are sorted by their {@link IDisplayString}, in the given
	 * {@link OrderDirection}. Values with equal {@link IDisplayString} are
	 * sorted by ID.
	 *
	 * @param values
	 *            the values to add (never null)
	 * @param direction
	 *            the sorting order of the values to be added (never null)
	 */
	public final void addValuesSortedByDisplayString(Iterable<? extends T> values, OrderDirection direction) {

		Comparator<T> comparator = new DisplayStringComparator();
		if (direction == OrderDirection.DESCENDING) {
			comparator = new ReverseComparator<>(comparator);
		}

		StreamSupport//
			.stream(values.spliterator(), false)
			.sorted(comparator)
			.forEach(this::addValue);
	}

	/**
	 * Sorts the options in this input element by the {@link IDisplayString} of
	 * their values, in ascending order.
	 * <p>
	 * The nil value (if any) will always be the first option.
	 */
	public void sortValuesByDisplayString() {

		sortValuesByDisplayString(OrderDirection.ASCENDING);
	}

	/**
	 * Sorts the options in this input element by the {@link IDisplayString} of
	 * their values, in the given {@link OrderDirection}.
	 * <p>
	 * The nil value (if any) will always be the first option.
	 *
	 * @param direction
	 *            the sorting order of the values (never null)
	 */
	public void sortValuesByDisplayString(OrderDirection direction) {

		// save all existing values
		IDisplayString nilValueDisplayString = getNilValueDisplayString();
		List<T> values = new ArrayList<>(getValueList());

		// remove all options and add them back in desired order
		removeValues();
		if (nilValueDisplayString != null) {
			addNilValue(nilValueDisplayString);
		}
		addValuesSortedByDisplayString(values, direction);
	}

	// -------------------------------- GETTING VALUES -------------------------------- //

	public final T getValue(int index) {

		return getOption(index).getValue();
	}

	public final T getValueById(int id) {

		DomValueOption<T> option = valueMap.get(id);
		return option != null? option.getValue() : null;
	}

	/**
	 * Returns all values in this input element, ordered by ID, excluding the
	 * nil value (if any).
	 *
	 * @return a {@link List} of all values in this input element (never null)
	 */
	public final List<T> getValueList() {

		List<T> result = ListFactory.createArrayList(valueMap.size());
		for (DomValueOption<T> option: valueMap.values()) {
			result.add(option.getValue());
		}
		return result;
	}

	/**
	 * Returns all values in this input element, in the visual order, including
	 * the nil value (if any).
	 *
	 * @return a {@link List} of all visual values, in the visual order (never
	 *         null)
	 */
	public final List<T> getVisualValueList() {

		List<T> result = ListFactory.createArrayList(valueMap.size());
		for (IDomNode child: getChildren()) {
			DomValueOption<T> option = CastUtils.cast(child);
			result.add(option.getValue());
		}
		return result;
	}

	/**
	 * Checks if the given value can be selected.
	 * <p>
	 * If null is given, a check for the presence of the nil option is
	 * performed.
	 *
	 * @param value
	 *            the value to check (may be null)
	 * @return true if the given value can be selected; false otherwise
	 */
	public final boolean containsValue(T value) {

		if (value == null) {
			return containsNilOption();
		} else {
			return valueMap.containsKey(getValueId(value));
		}
	}

	/**
	 * Checks if the nil value can be selected.
	 *
	 * @return true if the nil value can be selected; false otherwise
	 */
	public final boolean containsNilOption() {

		return nilOption != null;
	}

	// -------------------------------- NIL VALUE -------------------------------- //

	/**
	 * Adds an option that represents the nil value, at the first position.
	 *
	 * @param displayString
	 *            the {@link IDisplayString} of the nil value (never null)
	 */
	public final void prependNilValue(IDisplayString displayString) {

		nilOption = prependChild(new DomValueOption<>(null, displayString));
	}

	/**
	 * Adds an option that represents the nil value.
	 *
	 * @param displayString
	 *            the {@link IDisplayString} of the nil value (never null)
	 */
	public final void addNilValue(IDisplayString displayString) {

		nilOption = appendChild(new DomValueOption<>(null, displayString));
	}

	/**
	 * Returns the {@link IDisplayString} of the nil value, or null if there is
	 * no nil value.
	 *
	 * @return {@link IDisplayString} of the nil value (may be null)
	 */
	public final IDisplayString getNilValueDisplayString() {

		return Optional//
			.ofNullable(nilOption)
			.map(DomValueOption::toDisplay)
			.orElse(null);
	}

	/**
	 * Returns the {@link DomValueOption} that represents the nil value, or null
	 * if no nil value is present.
	 *
	 * @return the {@link DomValueOption} that represents the nil value (may be
	 *         null)
	 */
	public final DomValueOption<T> getNilOption() {

		return nilOption;
	}

	// -------------------------------- REMOVING VALUES -------------------------------- //

	/**
	 * Removes the specified value from the {@link Collection} of selectable
	 * options.
	 * <p>
	 * If the given value is null, the nil option is removed.
	 *
	 * @param value
	 *            the value to remove (may be null)
	 */
	public final void removeValue(T value) {

		if (value != null) {
			removeChild(valueMap.remove(getValueId(value)));
		} else {
			removeChild(nilOption);
			nilOption = null;
		}
	}

	/**
	 * Removes all values from the {@link Collection} of selectable options.
	 * <p>
	 * Removes the nil option as well, if any.
	 */
	public final void removeValues() {

		removeChildren();
		valueMap.clear();
		nilOption = null;
	}

	// -------------------------------- SELECTED VALUES -------------------------------- //

	/**
	 * Returns the value of the currently-selected option.
	 * <p>
	 * If the nil option is selected, null is returned.
	 *
	 * @return the selected value (may be null)
	 */
	public final T getSelectedValue() {

		return Optional//
			.ofNullable(getSelectedOption())
			.map(DomValueOption::getValue)
			.orElse(null);
	}

	/**
	 * Returns the values of the currently-selected options.
	 *
	 * @return the selected values (never null)
	 */
	public Collection<T> getSelectedValues() {

		Collection<DomValueOption<T>> selectedOptions = getSelectedOptions();
		List<T> selectedValues = new ArrayList<>(selectedOptions.size());
		for (DomValueOption<T> option: selectedOptions) {
			selectedValues.add(option.getValue());
		}
		return selectedValues;
	}

	/**
	 * Selects the option which is associated with the specified value. All
	 * other options will be unselected.
	 * <p>
	 * If null is given, the nil option is selected.
	 *
	 * @param value
	 *            the value to be selected (may be null)
	 */
	public void setSelectedValue(T value) {

		if (value != null) {
			setSelectedOption(valueMap.get(getValueId(value)));
		} else {
			setSelectedOption(nilOption);
		}
	}

	public void setOrAddSelectedValue(T value) {

		if (value != null && !containsValue(value)) {
			addValue(value);
		}
		setSelectedValue(value);
	}

	/**
	 * Selects the option which is associated with the specified ID. All other
	 * options will be unselected.
	 * <p>
	 * If null is given, the nil option is selected.
	 *
	 * @param id
	 *            the ID of value to be selected (may be null)
	 */
	public void setSelectedValueById(Integer id) {

		if (id != null) {
			try {
				setSelectedOption(valueMap.get(id));
			} catch (SofticarDeveloperException exception) {
				throw new SofticarDeveloperException(exception, "Tried to select an invalid option!");
			}
		} else {
			setSelectedOption(nilOption);
		}
	}

	/**
	 * Selects the options which are associated with the specified values. All
	 * other options will be unselected.
	 *
	 * @param values
	 *            the values to be selected (never null)
	 */
	public final void setSelectedValues(Collection<? extends T> values) {

		// collect all selected options
		List<DomValueOption<T>> selectedOptions = ListFactory.createArrayList(values.size());
		for (T value: values) {
			if (value != null) {
				selectedOptions.add(valueMap.get(getValueId(value)));
			} else {
				selectedOptions.add(nilOption);
			}
		}

		setSelectedOptions(selectedOptions);
	}

	// -------------------------------- PROTECTED -------------------------------- //

	/**
	 * Returns the ID of the specified value object.
	 * <p>
	 * FIXME This method should be allowed to return null (or an empty
	 * {@link Optional}), in case the given value object is not a possible
	 * option in this input element.
	 *
	 * @param value
	 *            the value object (never null)
	 * @return the ID of the value object (never null)
	 */
	protected abstract Integer getValueId(T value);

	/**
	 * Returns the {@link IDisplayString} of the specified value object.
	 * <p>
	 * In case the {@link IDisplayString} of the nil option is required,
	 * {@link #getNilValueDisplayString()} shall be used.
	 * <p>
	 * If the given value is non-null, and not an available option,
	 * {@link IDisplayString#EMPTY} is returned.
	 *
	 * @param value
	 *            the value object (never null)
	 * @return the {@link IDisplayString} of the value object (never null)
	 */
	protected abstract IDisplayString getValueDisplayString(T value);

	// -------------------------------- PRIVATE -------------------------------- //

	private final class DisplayStringComparator implements Comparator<T> {

		@Override
		public int compare(T first, T second) {

			var firstString = getValueDisplayString(first).toString();
			var secondString = getValueDisplayString(second).toString();
			int result = firstString.compareToIgnoreCase(secondString);
			return result != 0? result : getValueId(first) - getValueId(second);
		}
	}
}
