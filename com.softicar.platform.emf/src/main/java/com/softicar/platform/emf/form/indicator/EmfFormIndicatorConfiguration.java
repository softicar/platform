package com.softicar.platform.emf.form.indicator;

import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class EmfFormIndicatorConfiguration<R extends IEmfTableRow<R, ?>> implements IEmfFormIndicatorConfiguration<R> {

	private final Map<EmfFormIndicatorAlignment, Collection<IEmfFormIndicatorFactory<R>>> map;

	public EmfFormIndicatorConfiguration() {

		this.map = new TreeMap<>();
	}

	@Override
	public Collection<IEmfFormIndicatorFactory<R>> getIndicatorFactories(EmfFormIndicatorAlignment alignment) {

		Objects.requireNonNull(alignment);
		return map.getOrDefault(alignment, Collections.emptySet());
	}

	public void addIndicator(EmfFormIndicatorAlignment alignment, IEmfFormIndicatorFactory<R> factory) {

		map.computeIfAbsent(alignment, dummy -> new ArrayList<>()).add(factory);
	}

	public void addIndicatorLeft(IEmfFormIndicatorFactory<R> factory) {

		addIndicator(EmfFormIndicatorAlignment.LEFT, factory);
	}

	public void addIndicatorCenter(IEmfFormIndicatorFactory<R> factory) {

		addIndicator(EmfFormIndicatorAlignment.CENTER, factory);
	}

	public void addIndicatorRight(IEmfFormIndicatorFactory<R> factory) {

		addIndicator(EmfFormIndicatorAlignment.RIGHT, factory);
	}
}
