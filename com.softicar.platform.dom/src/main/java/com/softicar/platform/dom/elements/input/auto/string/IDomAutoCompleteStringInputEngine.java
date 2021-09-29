package com.softicar.platform.dom.elements.input.auto.string;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.elements.input.auto.IDomAutoCompleteInputEngine;
import com.softicar.platform.dom.elements.input.auto.util.collector.IItemToListCollector;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public interface IDomAutoCompleteStringInputEngine extends IDomAutoCompleteInputEngine<String> {

	@Override
	default IDisplayString getDisplayString(String item) {

		return IDisplayString.create(item);
	}

	default List<String> createList() {

		return new ArrayList<>();
	}

	default <R> IItemToListCollector<R, List<String>> createCollector(Function<R, String> stringExtractor) {

		return new StringToListCollector<>(stringExtractor);
	}
}
