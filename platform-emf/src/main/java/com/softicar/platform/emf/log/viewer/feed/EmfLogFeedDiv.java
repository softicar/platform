package com.softicar.platform.emf.log.viewer.feed;

import com.softicar.platform.common.container.map.list.ListTreeMap;
import com.softicar.platform.db.core.transaction.DbLazyTransaction;
import com.softicar.platform.dom.element.IDomElement;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomFieldset;
import com.softicar.platform.dom.elements.DomLegend;
import com.softicar.platform.dom.elements.message.DomMessageDiv;
import com.softicar.platform.dom.elements.message.style.DomMessageType;
import com.softicar.platform.emf.EmfCssClasses;
import com.softicar.platform.emf.EmfTestMarker;
import com.softicar.platform.emf.data.table.EmfDataTableI18n;
import com.softicar.platform.emf.log.viewer.EmfLogDisplayFactoryWrapper;
import com.softicar.platform.emf.log.viewer.item.EmfLogItem;
import com.softicar.platform.emf.log.viewer.item.EmfLogItemPopupButton;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import com.softicar.platform.emf.transaction.EmfTransactionObjectInfoDiv;
import com.softicar.platform.emf.transaction.IEmfTransactionObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The content element of the "Feed" tab.
 * <p>
 * Uses a {@link DbLazyTransaction} with implicit rollback, to make sure that
 * misbehaving display factories cannot write persistent data.
 */
public class EmfLogFeedDiv<R extends IEmfTableRow<R, ?>> extends DomDiv {

	public EmfLogFeedDiv(R tableRow) {

		addCssClass(EmfCssClasses.EMF_LOG_FEED);
		addMarker(EmfTestMarker.LOG_FEED_MAIN);

		EmfLogFeedCalculator<R> calculator = new EmfLogFeedCalculator<R>().calculate(tableRow);
		ListTreeMap<IEmfTransactionObject<?>, EmfLogFeedItem<R, ?>> feedItemMap = calculator.getFeedItemMap();

		try (DbLazyTransaction lazyTransaction = new DbLazyTransaction()) {
			if (!feedItemMap.isEmpty()) {
				for (IEmfTransactionObject<?> transactionObject: reverse(feedItemMap.keySet())) {
					appendChild(
						new Fieldset(//
							calculator.getLogItem(transactionObject),
							feedItemMap.getList(transactionObject)));
				}
			} else {
				appendChild(new DomMessageDiv(DomMessageType.INFO, EmfDataTableI18n.NO_ENTRIES_FOUND));
			}
		}
	}

	private <T> Collection<T> reverse(Collection<T> collection) {

		List<T> items = new ArrayList<>(collection);
		Collections.reverse(items);
		return items;
	}

	private class Fieldset extends DomFieldset {

		public Fieldset(EmfLogItem<R> logItem, List<EmfLogFeedItem<R, ?>> feedItems) {

			addMarker(EmfTestMarker.LOG_FEED_ITEM);
			DomLegend legend = new DomLegend();
			legend.addCssClass(EmfCssClasses.EMF_LOG_FEED_TRANSACTION_LEGEND);
			legend.appendChild(new EmfTransactionObjectInfoDiv(logItem.getTransactionObject()));
			legend.appendChild(new EmfLogItemPopupButton<>(logItem));
			appendChild(legend);

			EmfLogFeedGrid logViewerForm = appendChild(new EmfLogFeedGrid());
			for (EmfLogFeedItem<R, ?> item: getSortedFeedItems(feedItems)) {
				IDomElement displayElement = new EmfLogDisplayFactoryWrapper().createDisplay(() -> createDisplay(item));
				logViewerForm
					.appendRow(
						item.getTitle(),//
						displayElement,
						item.getType());
			}
		}

		private IDomElement createDisplay(EmfLogFeedItem<R, ?> item) {

			return item.getAttribute().createDisplay(item.getTableRow());
		}

		private List<EmfLogFeedItem<R, ?>> getSortedFeedItems(List<EmfLogFeedItem<R, ?>> feedItems) {

			return feedItems//
				.stream()
				.sorted(Comparator.comparing(item -> item.getTitle().toString()))
				.collect(Collectors.toList());
		}
	}
}
