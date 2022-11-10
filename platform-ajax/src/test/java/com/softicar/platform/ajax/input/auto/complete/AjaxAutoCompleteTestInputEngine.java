package com.softicar.platform.ajax.input.auto.complete;

import com.softicar.platform.common.container.derived.CurrentDerivedObjectRegistry;
import com.softicar.platform.common.core.thread.Locker;
import com.softicar.platform.dom.elements.input.auto.DomAutoCompleteDefaultInputEngine;
import com.softicar.platform.dom.elements.input.auto.matching.IAutoCompleteMatches;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

public class AjaxAutoCompleteTestInputEngine extends DomAutoCompleteDefaultInputEngine<AjaxAutoCompleteTestValue> {

	private final Collection<AjaxAutoCompleteTestValue> values;
	private final Lock lock;
	private Optional<Consumer<String>> requestListener;

	public AjaxAutoCompleteTestInputEngine(AjaxAutoCompleteTestValue...values) {

		this.values = new ArrayList<>();
		this.values.addAll(Arrays.asList(values));
		this.lock = new ReentrantLock();
		this.requestListener = Optional.empty();

		setLoader(() -> this.values);
		addDependsOn(this.values);
	}

	@Override
	public IAutoCompleteMatches<AjaxAutoCompleteTestValue> findMatches(String pattern, int limit) {

		try (Locker locker = lock()) {
			requestListener.ifPresent(listener -> listener.accept(pattern));
			return super.findMatches(pattern, limit);
		}
	}

	public AjaxAutoCompleteTestInputEngine clearValues() {

		values.clear();
		invalidateCache();
		return this;
	}

	public AjaxAutoCompleteTestInputEngine addValue(AjaxAutoCompleteTestValue value) {

		values.add(value);
		invalidateCache();
		return this;
	}

	public AjaxAutoCompleteTestInputEngine addValues(AjaxAutoCompleteTestValue...values) {

		List.of(values).forEach(this::addValue);
		return this;
	}

	public AjaxAutoCompleteTestInputEngine addStringValue(String name) {

		return addValue(new AjaxAutoCompleteTestValue(name));
	}

	public AjaxAutoCompleteTestInputEngine setRequestListener(Consumer<String> requestListener) {

		this.requestListener = Optional.of(requestListener);
		return this;
	}

	public Locker lock() {

		return new Locker(lock);
	}

	private void invalidateCache() {

		CurrentDerivedObjectRegistry.getInstance().invalidateDerivedObjects(values);
	}
}
