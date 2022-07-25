package com.softicar.platform.ajax.input.auto.complete;

import com.softicar.platform.common.container.derived.CurrentDerivedObjectRegistry;
import com.softicar.platform.common.core.thread.Locker;
import com.softicar.platform.dom.elements.input.auto.DomAutoCompleteDefaultInputEngine;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

public class AjaxAutoCompleteTestInputEngine extends DomAutoCompleteDefaultInputEngine<AjaxAutoCompleteTestItem> {

	private final Collection<AjaxAutoCompleteTestItem> items;
	private final Lock lock;
	private Optional<Consumer<String>> requestListener;

	public AjaxAutoCompleteTestInputEngine(AjaxAutoCompleteTestItem...items) {

		this.items = new ArrayList<>();
		this.items.addAll(Arrays.asList(items));
		this.lock = new ReentrantLock();
		this.requestListener = Optional.empty();

		setLoader(() -> this.items);
		addDependsOn(this.items);
	}

	@Override
	public Collection<AjaxAutoCompleteTestItem> findMatches(String pattern, int limit) {

		try (Locker locker = lock()) {
			requestListener.ifPresent(listener -> listener.accept(pattern));
			return super.findMatches(pattern, limit);
		}
	}

	public AjaxAutoCompleteTestInputEngine clearItems() {

		items.clear();
		invalidateCache();
		return this;
	}

	public AjaxAutoCompleteTestInputEngine addItem(AjaxAutoCompleteTestItem item) {

		items.add(item);
		invalidateCache();
		return this;
	}

	public AjaxAutoCompleteTestInputEngine addItems(AjaxAutoCompleteTestItem...items) {

		List.of(items).forEach(this::addItem);
		return this;
	}

	public AjaxAutoCompleteTestInputEngine addStringItem(String name) {

		return addItem(new AjaxAutoCompleteTestItem(name));
	}

	public AjaxAutoCompleteTestInputEngine setRequestListener(Consumer<String> requestListener) {

		this.requestListener = Optional.of(requestListener);
		return this;
	}

	public Locker lock() {

		return new Locker(lock);
	}

	private void invalidateCache() {

		CurrentDerivedObjectRegistry.getInstance().invalidateDerivedObjects(items);
	}
}
