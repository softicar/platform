package com.softicar.platform.dom.elements.input.auto;

import com.softicar.platform.common.container.derived.DerivedObject;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.i18n.IDisplayable;
import com.softicar.platform.common.core.locale.CurrentLocale;
import com.softicar.platform.common.core.utils.CastUtils;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Default implementation of {@link IDomAutoCompleteInputEngine}.
 * <p>
 * To determine all valid elements, this implementation relies on a
 * {@link Supplier} defined by calling {@link #setLoader}. Internally, these
 * loaded elements are then cached together with their respective
 * {@link IDisplayString}, using {@link DerivedObject}. Call
 * {@link #addDependsOn} to define dependencies to control cache invalidation.
 * <p>
 * By default, the {@link IDisplayString} for each element is determined by
 * testing if the element implements the interface {@link IDisplayable}. If this
 * fails, the {@link Object#toString} method is used as a fallback. This
 * mechanism can be overridden by calling {@link #setDisplayFunction(Function)}.
 * <p>
 * Elements evaluating to equal {@link IDisplayString} instances are
 * deduplicated by appending appropriate suffixes. The comparison is done
 * case-insensitive, although the letter cases of the respective
 * {@link IDisplayString} instances are retained, e.g. "foo" and "Foo" will
 * become "foo (1)" and "Foo (2)".
 * <p>
 * To ensure a deterministic order for deduplicated {@link IDisplayString}, the
 * elements should implement {@link Comparable}. This order can be customized by
 * calling {@link #setComparator}.
 * <p>
 * The comparator supplied to {@link #setComparator} will <b>not</b> be used to
 * order the elements returned by {@link #findMatches}. Instead,
 * {@link #findMatches} always returns elements order by their translated
 * {@link IDisplayString} in a lexicographical manner. Thus, the order depends
 * on {@link CurrentLocale}.
 *
 * @author Oliver Richers
 */
public class DomAutoCompleteDefaultInputEngine<T> implements IDomAutoCompleteInputEngine<T> {

	private final DerivedObject<Cache> cache;
	private Supplier<Collection<T>> loader;
	private Function<T, IDisplayString> displayFunction;
	private Comparator<T> comparator;

	public DomAutoCompleteDefaultInputEngine() {

		this.cache = new DerivedObject<>(Cache::new);
		this.loader = Collections::emptyList;
		this.displayFunction = new DefaultDisplayFunction();
		this.comparator = new DefaultComparator();
	}

	/**
	 * Defines the {@link Supplier} for the elements.
	 * <p>
	 * This {@link Supplier} should prefetch all necessary data so that calls to
	 * {@link #getDisplayString} for the returned elements are efficient.
	 * <p>
	 * No deduplication of elements is performed, since this is deemed to be the
	 * obligation of the {@link Supplier}.
	 *
	 * @param loader
	 *            the element {@link Supplier} (never <i>null</i>)
	 * @return this
	 */
	public DomAutoCompleteDefaultInputEngine<T> setLoader(Supplier<Collection<T>> loader) {

		this.loader = Objects.requireNonNull(loader);
		return this;
	}

	/**
	 * Adds the given {@link Objects} as a dependency to the internal
	 * {@link DerivedObject}, which is used to cache the loaded elements.
	 *
	 * @param object
	 *            the source object (never <i>null</i>)
	 * @return this
	 */
	public DomAutoCompleteDefaultInputEngine<T> addDependsOn(Object object) {

		cache.addDependsOn(object);
		return this;
	}

	/**
	 * Overrides the default implementation to determine the
	 * {@link IDisplayString} of an element.
	 * <p>
	 * The default implementation relies on the elements implementing the
	 * {@link IDisplayable} interface.
	 *
	 * @param displayFunction
	 *            the display function (never <i>null</i>)
	 * @return this
	 */
	public DomAutoCompleteDefaultInputEngine<T> setDisplayFunction(Function<T, IDisplayString> displayFunction) {

		this.displayFunction = Objects.requireNonNull(displayFunction);
		return this;
	}

	/**
	 * Overrides the {@link Comparator} to order elements with equal
	 * {@link IDisplayString} instances.
	 * <p>
	 * The default implementation relies on the elements implementing the
	 * {@link Comparable} interface.
	 *
	 * @param comparator
	 *            the {@link Comparator} (never <i>null</i>)
	 * @return this
	 */
	public DomAutoCompleteDefaultInputEngine<T> setComparator(Comparator<T> comparator) {

		this.comparator = Objects.requireNonNull(comparator);
		return this;
	}

	@Override
	public final IDisplayString getDisplayString(T element) {

		return cache.get().getDisplayString(element);
	}

	@Override
	public final Collection<T> findMatches(String pattern, int limit) {

		return cache.get().findMatches(pattern, limit);
	}

	@Override
	public void refresh() {

		cache.invalidate();
	}

	private class Cache {

		private final Map<String, T> stringToElementMap;
		private final Map<T, String> elementToStringMap;

		public Cache() {

			this.stringToElementMap = new DomAutoCompleteDisplayStringDeduplicator<>(displayFunction, comparator).apply(loader.get());
			this.elementToStringMap = stringToElementMap.entrySet().stream().collect(Collectors.toMap(Entry::getValue, Entry::getKey));
		}

		public IDisplayString getDisplayString(T element) {

			return Optional//
				.ofNullable(elementToStringMap.get(element))
				.map(IDisplayString::create)
				.orElse(IDisplayString.EMPTY);
		}

		public Collection<T> findMatches(String pattern, int limit) {

			return stringToElementMap//
				.entrySet()
				.stream()
				.filter(entry -> entry.getKey().toLowerCase().contains(pattern))
				.map(entry -> entry.getValue())
				.limit(limit)
				.collect(Collectors.toList());
		}
	}

	private class DefaultDisplayFunction implements Function<T, IDisplayString> {

		@Override
		public IDisplayString apply(T element) {

			if (element instanceof IDisplayable) {
				return ((IDisplayable) element).toDisplay();
			} else {
				return IDisplayString.create(element.toString());
			}
		}
	}

	private class DefaultComparator implements Comparator<T> {

		@Override
		public int compare(T a, T b) {

			Comparable<T> comparableA = CastUtils.castOrNull(a, Comparable.class);
			if (comparableA != null) {
				return comparableA.compareTo(b);
			} else {
				return 0;
			}
		}
	}
}
