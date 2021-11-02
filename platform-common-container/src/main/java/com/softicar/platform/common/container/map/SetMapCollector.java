package com.softicar.platform.common.container.map;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * A {@link Collector} for {@link Map} with {@link Set}.
 * <p>
 * TODO create unit test
 *
 * @author Oliver Richers
 */
public class SetMapCollector<T, K, V> implements Collector<T, Map<K, Set<V>>, Map<K, Set<V>>> {

	private final Function<? super T, ? extends K> keyExtractor;
	private final Function<? super T, ? extends V> valueExtractor;
	private final Supplier<Map<K, Set<V>>> mapFactory;
	private final Supplier<Set<V>> setFactory;

	private SetMapCollector(Function<? super T, ? extends K> keyExtractor, Function<? super T, ? extends V> valueExtractor, Supplier<Map<K, Set<V>>> mapFactory,
			Supplier<Set<V>> setFactory) {

		this.keyExtractor = keyExtractor;
		this.valueExtractor = valueExtractor;
		this.mapFactory = mapFactory;
		this.setFactory = setFactory;
	}

	public static <K, V> SetMapCollector<V, K, V> of(Function<? super V, ? extends K> keyExtractor) {

		// TODO cannot use HashMap::new because of defect in javac
		return new SetMapCollector<>(keyExtractor, Function.identity(), () -> new HashMap<>(), HashSet::new);
	}

	public static <K, V> SetMapCollector<V, K, V> of(Function<? super V, ? extends K> keyExtractor, Supplier<Map<K, Set<V>>> mapFactory,
			Supplier<Set<V>> setFactory) {

		return new SetMapCollector<>(keyExtractor, Function.identity(), mapFactory, setFactory);
	}

	public static <T, K, V> SetMapCollector<T, K, V> of(Function<? super T, ? extends K> keyExtractor, Function<? super T, ? extends V> valueExtractor) {

		// TODO cannot use HashMap::new because of defect in javac
		return new SetMapCollector<>(keyExtractor, valueExtractor, () -> new HashMap<>(), HashSet::new);
	}

	public static <T, K, V> SetMapCollector<T, K, V> of(Function<? super T, ? extends K> keyExtractor, Function<? super T, ? extends V> valueExtractor,
			Supplier<Map<K, Set<V>>> mapFactory, Supplier<Set<V>> setFactory) {

		return new SetMapCollector<>(keyExtractor, valueExtractor, mapFactory, setFactory);
	}

	@Override
	public Supplier<Map<K, Set<V>>> supplier() {

		return mapFactory;
	}

	@Override
	public BiConsumer<Map<K, Set<V>>, T> accumulator() {

		return (map, element) -> accumulate(map, element);
	}

	@Override
	public BinaryOperator<Map<K, Set<V>>> combiner() {

		return this::combine;
	}

	@Override
	public Function<Map<K, Set<V>>, Map<K, Set<V>>> finisher() {

		return map -> map;
	}

	@Override
	public Set<java.util.stream.Collector.Characteristics> characteristics() {

		return EnumSet.of(Characteristics.IDENTITY_FINISH);
	}

	private void accumulate(Map<K, Set<V>> map, T element) {

		K key = keyExtractor.apply(element);
		V value = valueExtractor.apply(element);
		getOrCreateSet(map, key).add(value);
	}

	private Map<K, Set<V>> combine(Map<K, Set<V>> map1, Map<K, Set<V>> map2) {

		for (Entry<K, Set<V>> entry: map2.entrySet()) {
			getOrCreateSet(map1, entry.getKey()).addAll(entry.getValue());
		}
		return map1;
	}

	private Set<V> getOrCreateSet(Map<K, Set<V>> map, K key) {

		Set<V> set = map.get(key);
		if (set == null) {
			map.put(key, set = setFactory.get());
		}
		return set;
	}
}
