package com.softicar.platform.dom.elements.input.auto;

import com.softicar.platform.common.container.derived.DerivedObject;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.i18n.IDisplayable;
import com.softicar.platform.common.core.locale.CurrentLocale;
import com.softicar.platform.common.core.number.parser.IntegerParser;
import com.softicar.platform.common.core.utils.CastUtils;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Default implementation of {@link IDomAutoCompleteInputEngine}.
 * <p>
 * To determine all allowed values, this implementation relies on a
 * {@link Supplier} defined by calling {@link #setLoader}. After loading, the
 * values are cached, together with their respective {@link IDisplayString},
 * using {@link DerivedObject}. Call {@link #addDependsOn} to define
 * dependencies to control cache invalidation.
 * <p>
 * By default, the {@link IDisplayString} for each value is determined by
 * testing if the value implements the interface {@link IDisplayable}. If this
 * fails, the {@link Object#toString} method is used as a fallback. This
 * mechanism can be overridden by calling {@link #setDisplayFunction}.
 * <p>
 * Values evaluating to equivalent {@link IDisplayString} instances are
 * deduplicated by appending appropriate suffixes to the {@link IDisplayString}.
 * The comparison is done case-insensitive, although the letter cases of the
 * respective {@link IDisplayString} instances are retained, e.g. "foo" and
 * "Foo" will become "foo (1)" and "Foo (2)".
 * <p>
 * To ensure a deterministic order for deduplicated {@link IDisplayString}
 * instances, the values should implement {@link Comparable}. This order can be
 * customized by calling {@link #setComparator}.
 * <p>
 * The comparator supplied to {@link #setComparator} will <b>not</b> be used to
 * order the values returned by {@link #findMatches}. Instead,
 * {@link #findMatches} always returns the values ordered by their localized
 * {@link IDisplayString}, in a lexicographical manner. Thus, the order depends
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
	 * Defines the {@link Supplier} for the values.
	 * <p>
	 * This {@link Supplier} should prefetch all necessary data, so that calls
	 * to {@link #getDisplayString} for the returned values are efficient.
	 * <p>
	 * No deduplication of values is performed, since this is deemed to be the
	 * obligation of the {@link Supplier}.
	 *
	 * @param loader
	 *            the value {@link Supplier} (never <i>null</i>)
	 * @return this
	 */
	public DomAutoCompleteDefaultInputEngine<T> setLoader(Supplier<Collection<T>> loader) {

		this.loader = Objects.requireNonNull(loader);
		return this;
	}

	/**
	 * Adds the given {@link Objects} as a dependency to the internal
	 * {@link DerivedObject}, which is used to cache the loaded values.
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
	 * {@link IDisplayString} of a value.
	 * <p>
	 * The default implementation relies on the values implementing the
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
	 * Overrides the {@link Comparator} to order values with equivalent
	 * {@link IDisplayString} instances.
	 * <p>
	 * The default implementation relies on the values implementing the
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
	public final IDisplayString getDisplayString(T value) {

		return cache.get().getDisplayString(value);
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

		private final Map<String, T> stringToValueMap;
		private final Map<Integer, T> idToValueMap;
		private final Map<T, String> valueToStringMap;

		public Cache() {

			this.stringToValueMap = new DomAutoCompleteDisplayStringDeduplicator<>(displayFunction, comparator).apply(loader.get());
			this.idToValueMap = new DomAutoCompleteIdMapFactory<>(stringToValueMap).create().orElse(Collections.emptyMap());
			this.valueToStringMap = new HashMap<>();

			stringToValueMap//
				.entrySet()
				.forEach(entry -> valueToStringMap.put(entry.getValue(), entry.getKey()));
		}

		public IDisplayString getDisplayString(T value) {

			return Optional//
				.ofNullable(valueToStringMap.get(value))
				.map(IDisplayString::create)
				.orElse(IDisplayString.EMPTY);
		}

		public Collection<T> findMatches(String pattern, int limit) {

			return findIdMatch(pattern).orElse(findStringMatch(pattern, limit));
		}

		private Optional<Collection<T>> findIdMatch(String pattern) {

			return IntegerParser//
				.parse(pattern)
				.map(id -> idToValueMap.get(id))
				.map(Collections::singleton);
		}

		private Collection<T> findStringMatch(String pattern, int limit) {

			return stringToValueMap//
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
		public IDisplayString apply(T value) {

			if (value instanceof IDisplayable) {
				return ((IDisplayable) value).toDisplay();
			} else {
				return IDisplayString.create(value.toString());
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
