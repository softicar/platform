package com.softicar.platform.dom.elements.select.value.simple;

import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.dom.event.DomEventType;
import com.softicar.platform.dom.event.IDomEventHandler;
import java.util.Collection;
import java.util.Optional;

/**
 * Interface of simple value-based drop-down input elements.
 * <p>
 * Implementations allow for a selection among options which represent distinct
 * values.
 *
 * @param <V>
 *            the type of the values which are available for selection
 * @author Alexander Schmidt
 */
public interface IDomSimpleValueSelect<V> extends IDomEventHandler {

	/**
	 * Defines the values for which options shall be available for selection.
	 * <p>
	 * Previous options are discarded before the options for the given values
	 * are added.
	 * <p>
	 * After calling this method, the first (top-most) option will be selected
	 * (if available, that will be the nil option).
	 * <p>
	 * Any <i>null</i> value that is contained in the given {@link Collection}
	 * will be discarded (i.e. skipped).
	 *
	 * @param values
	 *            the values for which options shall be added (never
	 *            <i>null</i>)
	 * @return this {@link IDomSimpleValueSelect}
	 */
	IDomSimpleValueSelect<V> setValues(Collection<? extends V> values);

	/**
	 * Returns the value that is represented by the currently-selected option.
	 * <p>
	 * If the nil option is selected, {@link Optional#empty()} is returned.
	 *
	 * @return the value of the currently-selected option
	 */
	Optional<V> getSelectedValue();

	/**
	 * Selects the option that represents the given value.
	 * <p>
	 * <b>If there is no such option, a corresponding option is added</b>, and
	 * then selected.
	 * <p>
	 * If <i>null</i> is given, the nil option is selected.
	 *
	 * @param value
	 *            the value that is represented by the option to select (may be
	 *            <i>null</i>)
	 * @throws IllegalArgumentException
	 *             if <i>null</i> is given and the {@link IDomSimpleValueSelect}
	 *             does not comprise a nil option, according to its
	 *             configuration
	 */
	void selectValue(V value);

	/**
	 * Defines a callback function to be executed when the selected option is
	 * changed.
	 * <p>
	 * When this method is called for the first time, the
	 * {@link IDomSimpleValueSelect} starts to listen on
	 * {@link DomEventType#CHANGE}.
	 *
	 * @param callbackOnChange
	 *            the callback function (never <i>null</i>)
	 * @return this {@link IDomSimpleValueSelect}
	 */
	IDomSimpleValueSelect<V> setCallbackOnChange(INullaryVoidFunction callbackOnChange);
}
