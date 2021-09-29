package com.softicar.platform.dom.elements.select.value.simple.display;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.i18n.IDisplayable;
import com.softicar.platform.common.core.item.BasicItemComparator;
import com.softicar.platform.common.core.item.IBasicItem;
import com.softicar.platform.dom.elements.select.value.simple.DomSimpleValueSelect;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Default, type-aware value comparator for {@link DomSimpleValueSelect}.
 * <p>
 * If the value type implements either {@link IDisplayable} and/or
 * {@link IBasicItem}:
 * <ol>
 * <li>{@link IDisplayable} values are compared via the supplied
 * value-to-{@link IDisplayString} function. If the result is non-zero, the
 * values are considered different and no further comparisons are
 * performed.</li>
 * <li>In case the above comparison returns zero, or was inapplicable,
 * {@link IBasicItem} values are compared via their IDs.</li>
 * </ol>
 * <p>
 * Assumes a surjective relation between a value and its {@link IDisplayString}
 * representation. That is: There is a unique {@link IDisplayString}
 * representation for each value - but different values might have the same
 * {@link IDisplayString} representation.
 * <p>
 * As a fallback, if the value type implements neither {@link IDisplayable} nor
 * {@link IBasicItem}, values are compared based upon {@link Object#toString()}.
 * <p>
 * Supports <i>null</i> values:
 * <ul>
 * <li>any two <i>null</i> values are considered equal</li>
 * <li>a <i>null</i> value is considered smaller than any non-<i>null</i>
 * value</li>
 * </ul>
 *
 * @author Alexander Schmidt
 */
public class DomSimpleValueSelectDefaultValueComparator<V> implements Comparator<V> {

	private final Supplier<Function<V, IDisplayString>> displayStringFunctionSupplier;

	public DomSimpleValueSelectDefaultValueComparator(Supplier<Function<V, IDisplayString>> displayStringFunctionSupplier) {

		this.displayStringFunctionSupplier = Objects.requireNonNull(displayStringFunctionSupplier);
	}

	@Override
	public int compare(V first, V second) {

		if (isArgumentsCastableToAny(first, second, IDisplayable.class, IBasicItem.class)) {
			int result = 0;

			if (isArgumentsCastableTo(first, second, IDisplayable.class)) {
				var comparator = new CollatingDisplayStringFunctionBasedComparator<>(displayStringFunctionSupplier.get());
				result = Comparator.nullsFirst(comparator).compare(first, second);
			}

			if (result == 0) {
				if (isArgumentsCastableTo(first, second, IBasicItem.class)) {
					var comparator = BasicItemComparator.get();
					result = Comparator.nullsFirst(comparator).compare((IBasicItem) first, (IBasicItem) second);
				}
			}

			return result;
		}

		else {
			var comparator = Comparator.comparing(Object::toString);
			return Comparator.nullsFirst(comparator).compare(first, second);
		}
	}

	private boolean isArgumentsCastableToAny(V first, V second, Class<?>...targetClasses) {

		return Arrays//
			.asList(targetClasses)
			.stream()
			.anyMatch(targetClass -> isArgumentsCastableTo(first, second, targetClass));
	}

	private boolean isArgumentsCastableTo(V first, V second, Class<?> targetClass) {

		if (isArgumentCastableTo(first, targetClass) && isArgumentCastableTo(second, targetClass)) {
			return true;
		} else if (isArgumentCastableTo(first, targetClass) && second == null) {
			return true;
		} else if (first == null && isArgumentCastableTo(second, targetClass)) {
			return true;
		} else {
			return false;
		}
	}

	private boolean isArgumentCastableTo(V argument, Class<?> targetClass) {

		return argument != null && targetClass.isAssignableFrom(argument.getClass());
	}
}
