package com.softicar.platform.dom.elements.input.auto.util.collector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class AbstractItemToListCollector<I, L> implements IItemToListCollector<I, L> {

	@Override
	public Supplier<List<I>> supplier() {

		return ArrayList::new;
	}

	@Override
	public BiConsumer<List<I>, I> accumulator() {

		return (list, item) -> list.add(item);
	}

	@Override
	public BinaryOperator<List<I>> combiner() {

		return this::combine;
	}

	@Override
	public Function<List<I>, L> finisher() {

		return this::finish;
	}

	@Override
	public Set<Characteristics> characteristics() {

		return Collections.emptySet();
	}

	private List<I> combine(List<I> first, List<I> second) {

		List<I> list = supplier().get();
		list.addAll(first);
		list.addAll(second);
		return list;
	}
}
