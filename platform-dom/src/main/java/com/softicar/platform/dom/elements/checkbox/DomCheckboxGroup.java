package com.softicar.platform.dom.elements.checkbox;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.i18n.IDisplayable;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.common.core.interfaces.NullaryVoidFunctionList;
import com.softicar.platform.dom.DomCssClasses;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.input.IDomValueInput;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * A group of {@link DomCheckbox} nodes that mimic the behavior of grouped HTML
 * checkboxes.
 *
 * @param <V>
 *            the type of the values to be selected (should implement
 *            {@code equals} and {@code hashCode})
 * @author Alexander Schmidt
 */
public class DomCheckboxGroup<V> extends DomDiv implements IDomValueInput<V> {

	private final Map<DomCheckbox, V> valueMap;
	private final Map<V, DomCheckbox> checkboxMap;
	private final NullaryVoidFunctionList callbacks;
	private boolean disabled;

	/**
	 * Constructs a new {@link DomCheckboxGroup}.
	 */
	public DomCheckboxGroup() {

		this(HashMap::new);
	}

	/**
	 * Constructs a new {@link DomCheckboxGroup}, using the given {@link Map}
	 * factory.
	 * <p>
	 * The created {@link Map} must support instances of the value type as keys.
	 *
	 * @param mapFactory
	 *            the map factory (never <i>null</i>)
	 */
	public DomCheckboxGroup(Supplier<Map<V, DomCheckbox>> mapFactory) {

		Objects.requireNonNull(mapFactory);

		this.valueMap = new HashMap<>();
		this.checkboxMap = mapFactory.get();
		this.callbacks = new NullaryVoidFunctionList();
		this.disabled = false;

		setCssClass(DomCssClasses.DOM_CHECKBOX_GROUP);
	}

	/**
	 * Adds an option for the given value, deriving the label from the given
	 * value.
	 * <p>
	 * If the given type of the given value implements {@link IDisplayable}, the
	 * return value of {@link IDisplayable#toDisplay()} will be used as a label.
	 * <p>
	 * The first added option is always preselected.
	 *
	 * @param value
	 *            the value that is represented by the option (never
	 *            <i>null</i>)
	 * @return this
	 */
	public DomCheckboxGroup<V> addOption(V value) {

		return addOption(value, createLabel(value));
	}

	/**
	 * Adds an option for the given value, deriving the label from the given
	 * value.
	 * <p>
	 * If the given type of the given value implements {@link IDisplayable}, the
	 * return value of {@link IDisplayable#toDisplay()} will be used as a label.
	 * <p>
	 * The option may be preselected. If the option is preselected, all other
	 * options will be un-selected (including previously preselected options).
	 * <p>
	 * The first added option is always preselected.
	 *
	 * @param value
	 *            the value that is represented by the option (never
	 *            <i>null</i>)
	 * @param preselected
	 *            <i>true</i> if the option shall be preselected; <i>false</i>
	 *            otherwise
	 * @return this
	 */
	public DomCheckboxGroup<V> addOption(V value, boolean preselected) {

		return addOption(value, createLabel(value), preselected);
	}

	/**
	 * Adds an option for the given value, with the given label.
	 * <p>
	 * The first added option is always preselected.
	 *
	 * @param value
	 *            the value that is represented by the option (never
	 *            <i>null</i>)
	 * @param label
	 *            the label to display next to the option (never <i>null</i>)
	 * @return this
	 */
	public DomCheckboxGroup<V> addOption(V value, IDisplayString label) {

		return addOption(value, label, false);
	}

	/**
	 * Adds an option for the given value, with the given label.
	 * <p>
	 * The option may be preselected. If the option is preselected, all other
	 * options will be un-selected (including previously preselected options).
	 * <p>
	 * The first added option is always preselected.
	 *
	 * @param value
	 *            the value that is represented by the option (never
	 *            <i>null</i>)
	 * @param label
	 *            the label to display next to the option (never <i>null</i>)
	 * @param preselected
	 *            <i>true</i> if the option shall be preselected; <i>false</i>
	 *            otherwise
	 * @return this
	 */
	public DomCheckboxGroup<V> addOption(V value, IDisplayString label, boolean preselected) {

		Objects.requireNonNull(value);
		Objects.requireNonNull(label);

		if (valueMap.isEmpty()) {
			preselected = true;
		}

		var checkbox = new Checkbox(label, preselected).setDisabled(disabled);
		if (preselected) {
			unselectOthers(checkbox);
		}

		valueMap.put(checkbox, value);
		checkboxMap.put(value, checkbox);

		appendChild(checkbox);

		return this;
	}

	@Override
	public DomCheckboxGroup<V> setDisabled(boolean disabled) {

		this.disabled = disabled;
		this.valueMap.keySet().forEach(it -> it.setDisabled(disabled));
		return this;
	}

	@Override
	public boolean isDisabled() {

		return disabled;
	}

	@Override
	public DomCheckboxGroup<V> setEnabled(boolean enabled) {

		return setDisabled(!enabled);
	}

	@Override
	public boolean isEnabled() {

		return !isDisabled();
	}

	@Override
	public Optional<V> getValue() {

		return valueMap//
			.keySet()
			.stream()
			.filter(DomCheckbox::isChecked)
			.map(valueMap::get)
			.findFirst();
	}

	/**
	 * Selects the option that corresponds to the given value, and un-selects
	 * all other options.
	 * <p>
	 * If no option can be found for the given value, or if the given value is
	 * <i>null</i>, nothing will happen.
	 *
	 * @param value
	 *            the value of the option to select (may be <i>null</i>)
	 */
	@Override
	public void setValue(V value) {

		if (value != null) {
			var checkbox = checkboxMap.get(value);
			if (checkbox != null) {
				checkbox.setValueAndHandleChangeCallback(true);
			}
		}
	}

	@Override
	public void setValueAndHandleChangeCallback(V value) {

		setValue(value);
		callbacks.apply();
	}

	@Override
	public void addChangeCallback(INullaryVoidFunction callback) {

		this.callbacks.add(callback);
	}

	private IDisplayString createLabel(V value) {

		Objects.requireNonNull(value);
		if (value instanceof IDisplayable) {
			return ((IDisplayable) value).toDisplay();
		} else {
			return IDisplayString.create(value.toString());
		}
	}

	private void unselectOthers(DomCheckbox checkbox) {

		valueMap.keySet().stream().filter(it -> it != checkbox).forEach(it -> it.setValue(false));
	}

	private class Checkbox extends DomCheckbox {

		public Checkbox(IDisplayString label, boolean checked) {

			super(checked);
			setLabel(label);
			addChangeCallback(this::handleClicked);
		}

		private void handleClicked() {

			if (isChecked()) {
				unselectOthers(this);
				callbacks.apply();
			} else {
				setValue(true);
			}
		}
	}
}
