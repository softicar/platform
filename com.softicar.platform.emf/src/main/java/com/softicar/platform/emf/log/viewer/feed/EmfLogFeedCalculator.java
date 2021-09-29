package com.softicar.platform.emf.log.viewer.feed;

import com.softicar.platform.common.container.map.list.ListTreeMap;
import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.log.viewer.EmfLogAttributeFilter;
import com.softicar.platform.emf.log.viewer.item.EmfLogItem;
import com.softicar.platform.emf.log.viewer.item.EmfLogItemLoader;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import com.softicar.platform.emf.transaction.IEmfTransactionObject;
import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;

class EmfLogFeedCalculator<R extends IEmfTableRow<R, ?>> {

	private final ListTreeMap<IEmfTransactionObject<?>, EmfLogFeedItem<R, ?>> feedItemMap;
	private final EmfLogItemLoader<R> loader;

	public EmfLogFeedCalculator() {

		this.feedItemMap = new ListTreeMap<>();
		this.loader = new EmfLogItemLoader<>();
	}

	public EmfLogFeedCalculator<R> calculate(R tableRow) {

		this.feedItemMap.clear();
		this.loader.load(tableRow);

		EmfLogItem<R> previousLogItem = null;
		for (EmfLogItem<R> logItem: loader.getLogItems()) {
			R oldEntity = Optional.ofNullable(previousLogItem).map(it -> it.getImpermanentTableRow()).orElse(null);
			R newEntity = Optional.ofNullable(logItem).map(it -> it.getImpermanentTableRow()).orElse(null);
			for (IEmfAttribute<R, ?> attribute: getDisplayableAttributes(tableRow)) {
				processLogItem(attribute, logItem, oldEntity, newEntity);
			}
			previousLogItem = logItem;
		}

		return this;
	}

	public ListTreeMap<IEmfTransactionObject<?>, EmfLogFeedItem<R, ?>> getFeedItemMap() {

		return feedItemMap;
	}

	public EmfLogItem<R> getLogItem(IEmfTransactionObject<?> transactionObject) {

		return loader.getLogItem(transactionObject);
	}

	private <V> void processLogItem(IEmfAttribute<R, V> attribute, EmfLogItem<R> logItem, R oldEntity, R newEntity) {

		Optional<EmfLogFeedItemType> type = getType(attribute, oldEntity, newEntity);
		if (type.isPresent()) {
			IEmfTransactionObject<?> transactionObject = logItem.getTransactionObject();
			R tableRow = type.get().getRelevantEntity(oldEntity, newEntity);
			EmfLogFeedItem<R, V> feedItem = new EmfLogFeedItem<>(attribute, tableRow, type.get());
			feedItemMap.addToList(transactionObject, feedItem);
		}
	}

	private <V> Optional<EmfLogFeedItemType> getType(IEmfAttribute<R, V> attribute, R oldEntity, R newEntity) {

		Optional<Comparator<V>> comparator = attribute.getValueComparator();
		if (comparator.isPresent()) {
			V oldValue = Optional.ofNullable(oldEntity).map(attribute::getValue).orElse(null);
			V newValue = Optional.ofNullable(newEntity).map(attribute::getValue).orElse(null);
			if (valueChanged(comparator.get(), oldValue, newValue)) {
				if (oldValue == null && newValue != null) {
					return Optional.of(EmfLogFeedItemType.ADDITION);
				} else if (oldValue != null && newValue == null) {
					return Optional.of(EmfLogFeedItemType.REMOVAL);
				} else if (oldValue != null && newValue != null) {
					return Optional.of(EmfLogFeedItemType.UPDATE);
				}
			}
		}
		return Optional.empty();
	}

	private <V> boolean valueChanged(Comparator<V> comparator, V oldValue, V newValue) {

		return comparator.compare(oldValue, newValue) != 0;
	}

	private Collection<IEmfAttribute<R, ?>> getDisplayableAttributes(R tableRow) {

		return new EmfLogAttributeFilter<>(tableRow).getDisplayableAttributes();
	}
}
