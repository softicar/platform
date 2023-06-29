package com.softicar.platform.dom.elements.select.value.simple;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.Consumers;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.dom.elements.DomValueOption;
import com.softicar.platform.dom.event.DomEventType;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.input.DomSelect;
import java.util.Collection;
import java.util.Comparator;
import java.util.NavigableMap;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * A simple value-based drop-down input element.
 * <p>
 * Allows for a selection among options which represent distinct values.
 * <p>
 * Features:
 * <ul>
 * <li>De-duplicates and orders options, according to a configurable
 * {@link Comparator}.</li>
 * <li>Derives option labels from a configurable value-to-{@link IDisplayString}
 * function.</li>
 * <li>Supports nil values, to allow for the selection of "nothing".</li>
 * <li>Supports adding options on-the-fly, when attempting to select a
 * nonexistent one.</li>
 * <li>Supports on-change callback functions.</li>
 * <li>Minimal interface, aiming for robustness and ease of use.</li>
 * </ul>
 * <p>
 * As an alternative to the constructors, {@link DomSimpleValueSelectBuilder} is
 * available.
 *
 * @param <V>
 *            the type of the values which are available for selection
 * @author Alexander Schmidt
 */
public class DomSimpleValueSelect<V> extends DomSelect<DomValueOption<V>> implements IDomSimpleValueSelect<V> {

	protected final IDomSimpleValueSelectConfiguration<V> configuration;
	private final NavigableMap<V, DomValueOption<V>> valueMap;
	private Function<V, IDisplayString> displayStringFunction;
	private INullaryVoidFunction callbackOnChange;
	private DomValueOption<V> nilOption;

	/**
	 * Constructs a new {@link DomSimpleValueSelect} with default configuration.
	 */
	public DomSimpleValueSelect() {

		this(Consumers.noOperation());
	}

	/**
	 * Constructs a new {@link DomSimpleValueSelect} with a default
	 * configuration that is modified via the given configurator. That is, a
	 * consumer of the default configuration.
	 *
	 * @param configurator
	 *            allows for modifications of the default configuration (never
	 *            <i>null</i>)
	 */
	public DomSimpleValueSelect(Consumer<DomSimpleValueSelectBuilder<V>> configurator) {

		this(createAndConfigureBuilder(configurator));
	}

	/**
	 * Constructs a new {@link DomSimpleValueSelect} from the given
	 * {@link IDomSimpleValueSelectConfiguration}.
	 *
	 * @param configuration
	 *            the configuration to use (never <i>null</i>)
	 */
	public DomSimpleValueSelect(IDomSimpleValueSelectConfiguration<V> configuration) {

		this.configuration = Objects.requireNonNull(configuration);
		this.valueMap = new TreeMap<>(configuration.getValueComparator());
		this.displayStringFunction = configuration.getValueDisplayStringFunction();
		this.callbackOnChange = INullaryVoidFunction.NO_OPERATION;
		this.nilOption = null;

		setValues(configuration.getValues());

		configuration.getCallbackOnChange().ifPresent(this::setCallbackOnChange);
	}

	@Override
	public DomSimpleValueSelect<V> setValues(Collection<? extends V> values) {

		var previousValue = getSelectedValue();
		reset();
		appendNilOptionIfAppropriate();
		appendValueOptions(values.stream().filter(Objects::nonNull).collect(Collectors.toList()));
		selectFirstOption();
		executeCallbackIfChanged(previousValue);
		return this;
	}

	@Override
	public Optional<V> getSelectedValue() {

		return Optional//
			.ofNullable(getSelectedOption())
			.map(DomValueOption::getValue);
	}

	@Override
	public void selectValue(V value) {

		if (value == null && !configuration.isNilOptionAvailable()) {
			throw new IllegalArgumentException();
		}

		var previousValue = getSelectedValue();
		selectOption(value);
		executeCallbackIfChanged(previousValue);
	}

	@Override
	public DomSimpleValueSelect<V> setCallbackOnChange(INullaryVoidFunction callbackOnChange) {

		this.callbackOnChange = Objects.requireNonNull(callbackOnChange);
		listenToEvent(DomEventType.CHANGE);
		return this;
	}

	@Override
	public void handleDOMEvent(IDomEvent event) {

		if (event.getType().equals(DomEventType.CHANGE)) {
			executeCallbackOnChange();
		}
	}

	private void reset() {

		removeChildren();
		valueMap.clear();
		nilOption = null;
	}

	private void appendNilOptionIfAppropriate() {

		if (configuration.isNilOptionAvailable() && !containsNilOption()) {
			this.nilOption = appendChild(new DomValueOption<>(null, configuration.getNilOptionDisplayString()));
		}
	}

	private void appendValueOptions(Collection<? extends V> values) {

		for (V value: values) {
			valueMap.put(value, createValueOption(value));
		}
		valueMap.values().forEach(this::appendChild);
	}

	private void selectFirstOption() {

		if (nilOption != null) {
			setSelectedOption(nilOption);
		} else if (!valueMap.isEmpty()) {
			setSelectedOption(valueMap.firstEntry().getValue());
		}
	}

	private DomValueOption<V> createValueOption(V value) {

		return new DomValueOption<>(value, displayStringFunction.apply(value));
	}

	private void selectOption(V value) {

		if (value != null) {
			selectValueOption(value);
		} else {
			selectNilOption();
		}
	}

	private void selectValueOption(V value) {

		if (!containsValueOption(value)) {
			insertValueOption(value);
		}
		setSelectedOption(valueMap.get(value));
	}

	private void selectNilOption() {

		setSelectedOption(nilOption);
	}

	private boolean containsValueOption(V value) {

		return valueMap.containsKey(value);
	}

	private boolean containsNilOption() {

		return nilOption != null;
	}

	private void insertValueOption(V value) {

		var option = createValueOption(value);
		valueMap.put(value, option);
		var tailMap = valueMap.tailMap(value, false);
		if (tailMap.isEmpty()) {
			appendChild(option);
		} else {
			insertBefore(option, tailMap.firstEntry().getValue());
		}
	}

	private void executeCallbackIfChanged(Optional<V> previousValue) {

		if (configuration.getValueComparator().compare(previousValue.orElse(null), getSelectedValue().orElse(null)) != 0) {
			executeCallbackOnChange();
		}
	}

	private void executeCallbackOnChange() {

		callbackOnChange.apply();
	}

	private static <V> DomSimpleValueSelectBuilder<V> createAndConfigureBuilder(Consumer<DomSimpleValueSelectBuilder<V>> configurator) {

		Objects.requireNonNull(configurator);
		var builder = new DomSimpleValueSelectBuilder<V>();
		configurator.accept(builder);
		return builder;
	}
}
