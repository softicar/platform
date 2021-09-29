package com.softicar.platform.dom.elements.select.value.simple;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.elements.select.value.simple.display.DomSimpleValueSelectDefaultDisplayStringFunction;
import com.softicar.platform.dom.elements.select.value.simple.display.DomSimpleValueSelectDefaultValueComparator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

/**
 * A builder for {@link DomSimpleValueSelect} instances.
 * <p>
 * Also serves as a configuration object.
 *
 * @param <V>
 *            the type of the values which are available for selection in the
 *            {@link DomSimpleValueSelect}
 * @author Alexander Schmidt
 */
public class DomSimpleValueSelectBuilder<V> implements IDomSimpleValueSelectConfiguration<V> {

	private Collection<? extends V> values;
	private boolean nilOptionAvailable;
	private IDisplayString nilOptionDisplayString;
	private INullaryVoidFunction callbackOnChange;
	private Function<V, IDisplayString> valueDisplayStringFunction;
	private Comparator<V> valueComparator;

	/**
	 * Constructs a new {@link DomSimpleValueSelectBuilder} instance.
	 */
	public DomSimpleValueSelectBuilder() {

		this.values = new ArrayList<>();
		this.nilOptionAvailable = true;
		this.nilOptionDisplayString = DomI18n.NONE.encloseInBrackets();
		this.callbackOnChange = null;
		this.valueDisplayStringFunction = new DomSimpleValueSelectDefaultDisplayStringFunction<>();
		this.valueComparator = new DomSimpleValueSelectDefaultValueComparator<>(this::getValueDisplayStringFunction);
	}

	/**
	 * Builds a new {@link DomSimpleValueSelect} instance.
	 *
	 * @return a new {@link DomSimpleValueSelect} (never <i>null</i>)
	 */
	public DomSimpleValueSelect<V> build() {

		return new DomSimpleValueSelect<>(this);
	}

	@Override
	public Collection<? extends V> getValues() {

		return values;
	}

	/**
	 * Defines the values for which options shall be added.
	 * <p>
	 * Any contained <i>null</i> value will be ignored.
	 *
	 * @param values
	 *            the values to add options for (never <i>null</i>)
	 * @return this {@link DomSimpleValueSelectBuilder}
	 */
	public DomSimpleValueSelectBuilder<V> setValues(Collection<? extends V> values) {

		this.values = Objects.requireNonNull(values);
		return this;
	}

	@Override
	public boolean isNilOptionAvailable() {

		return nilOptionAvailable;
	}

	/**
	 * Defines whether a nil option shall be available for selection.
	 *
	 * @param nilOptionAvailable
	 *            <i>true</i> if a nil option is available; <i>false</i>
	 *            otherwise
	 * @return this {@link DomSimpleValueSelectBuilder}
	 */
	public DomSimpleValueSelectBuilder<V> setNilOptionAvailable(boolean nilOptionAvailable) {

		this.nilOptionAvailable = nilOptionAvailable;
		return this;
	}

	@Override
	public IDisplayString getNilOptionDisplayString() {

		return nilOptionDisplayString;
	}

	/**
	 * Defines the {@link IDisplayString} of the nil option.
	 *
	 * @param nilOptionDisplayString
	 *            the nil option {@link IDisplayString} (never <i>null</i>)
	 * @return this {@link DomSimpleValueSelectBuilder}
	 */
	public DomSimpleValueSelectBuilder<V> setNilOptionDisplayString(IDisplayString nilOptionDisplayString) {

		this.nilOptionDisplayString = Objects.requireNonNull(nilOptionDisplayString);
		return this;
	}

	@Override
	public Optional<INullaryVoidFunction> getCallbackOnChange() {

		return Optional.ofNullable(callbackOnChange);
	}

	/**
	 * Defines a callback function to be executed when the selected option is
	 * changed.
	 *
	 * @param callbackOnChange
	 *            the callback function (never <i>null</i>)
	 * @return this {@link DomSimpleValueSelectBuilder}
	 */
	public DomSimpleValueSelectBuilder<V> setCallbackOnChange(INullaryVoidFunction callbackOnChange) {

		this.callbackOnChange = Objects.requireNonNull(callbackOnChange);
		return this;
	}

	@Override
	public Function<V, IDisplayString> getValueDisplayStringFunction() {

		return valueDisplayStringFunction;
	}

	/**
	 * Defines the {@link Function} that derives an {@link IDisplayString} from
	 * a given value.
	 *
	 * @param valueDisplayStringFunction
	 *            the {@link Function} to derive an {@link IDisplayString} from
	 *            a value (never <i>null</i>)
	 * @return this {@link DomSimpleValueSelectBuilder}
	 */
	public DomSimpleValueSelectBuilder<V> setValueDisplayStringFunction(Function<V, IDisplayString> valueDisplayStringFunction) {

		this.valueDisplayStringFunction = Objects.requireNonNull(valueDisplayStringFunction);
		return this;
	}

	@Override
	public Comparator<V> getValueComparator() {

		return valueComparator;
	}

	/**
	 * Defines a {@link Comparator} for values that are available for selection
	 * in the {@link IDomSimpleValueSelect}.
	 * <p>
	 * The {@link Comparator} will be wrapped in
	 * {@link Comparator#nullsFirst(Comparator)} to ensure that it supports
	 * <i>null</i> arguments given to
	 * {@link Comparator#compare(Object, Object)}.
	 * <p>
	 * The given {@link Comparator} should make use of
	 * {@link #getValueDisplayStringFunction()}.
	 * <p>
	 * The displayed options are identified and ordered, according to the given
	 * {@link Comparator}.
	 *
	 * @param valueComparator
	 *            a {@link Comparator} for values (never <i>null</i>)
	 * @return this {@link DomSimpleValueSelectBuilder}
	 */
	public DomSimpleValueSelectBuilder<V> setValueComparator(Comparator<V> valueComparator) {

		this.valueComparator = Comparator.nullsFirst(Objects.requireNonNull(valueComparator));
		return this;
	}
}
