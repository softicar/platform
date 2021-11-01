package com.softicar.platform.dom.elements.input.auto.util.collector;

import java.util.List;
import java.util.stream.Collector;

public interface IItemToListCollector<I, L> extends Collector<I, List<I>, L> {

	L finish(List<I> items);
}
