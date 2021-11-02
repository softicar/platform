package com.softicar.platform.dom.elements.input.auto.string;

import com.softicar.platform.dom.elements.input.auto.util.collector.AbstractItemToListCollector;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class StringToListCollector<I> extends AbstractItemToListCollector<I, List<String>> {

	private final Function<I, String> stringExtractor;

	public StringToListCollector(Function<I, String> stringExtractor) {

		this.stringExtractor = stringExtractor;
	}

	@Override
	public List<String> finish(List<I> items) {

		List<String> result = new ArrayList<>();
		for (I item: items) {
			result.add(stringExtractor.apply(item));
		}
		return result;
	}
}
