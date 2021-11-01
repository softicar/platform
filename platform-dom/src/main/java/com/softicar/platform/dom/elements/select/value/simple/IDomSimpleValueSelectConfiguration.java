package com.softicar.platform.dom.elements.select.value.simple;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Function;

/**
 * Configuration interface for {@link IDomSimpleValueSelect}.
 *
 * @param <V>
 *            the type of the values which are available for selection in the
 *            {@link IDomSimpleValueSelect}
 * @author Alexander Schmidt
 */
public interface IDomSimpleValueSelectConfiguration<V> {

	/**
	 * Returns the values for which options shall be added.
	 * <p>
	 * Any contained <i>null</i> value will be ignored.
	 *
	 * @return the values to add options for (never <i>null</i>)
	 */
	Collection<? extends V> getValues();

	/**
	 * Determines whether a nil option shall be available for selection.
	 *
	 * @return <i>true</i> if a nil option is available; <i>false</i> otherwise
	 */
	boolean isNilOptionAvailable();

	/**
	 * Returns the {@link IDisplayString} of the nil option.
	 *
	 * @return the nil option {@link IDisplayString} (never <i>null</i>)
	 */
	IDisplayString getNilOptionDisplayString();

	/**
	 * Returns a callback function to be executed when the selected option is
	 * changed.
	 *
	 * @return the callback function
	 */
	Optional<INullaryVoidFunction> getCallbackOnChange();

	/**
	 * Returns the {@link Function} that derives an {@link IDisplayString} from
	 * a given value.
	 *
	 * @return the {@link Function} to derive an {@link IDisplayString} from a
	 *         value (never <i>null</i>)
	 */
	Function<V, IDisplayString> getValueDisplayStringFunction();

	/**
	 * Returns a {@link Comparator} for values that are available for selection
	 * in the {@link IDomSimpleValueSelect}.
	 * <p>
	 * The {@link Comparator} must allow <i>null</i> arguments given to
	 * {@link Comparator#compare(Object, Object)}.
	 * <p>
	 * The {@link Comparator} should make use of
	 * {@link #getValueDisplayStringFunction()}.
	 * <p>
	 * The displayed options are identified and ordered, according to the
	 * {@link Comparator}.
	 *
	 * @return a {@link Comparator} for values (never <i>null</i>)
	 */
	Comparator<V> getValueComparator();
}
