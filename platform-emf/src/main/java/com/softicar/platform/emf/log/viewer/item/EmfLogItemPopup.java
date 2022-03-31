package com.softicar.platform.emf.log.viewer.item;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.label.DomLabelGrid;
import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.attribute.EmfAttributeByTitleComparator;
import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.log.viewer.EmfLogAttributeFilter;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import com.softicar.platform.emf.transaction.IEmfTransactionObject;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EmfLogItemPopup<R extends IEmfTableRow<R, ?>> extends DomPopup {

	private final EmfLogItem<R> logItem;

	public EmfLogItemPopup(EmfLogItem<R> logItem) {

		this.logItem = logItem;
		setCaption(EmfI18n.HISTORY_ENTRY);
		setSubCaption(getTransactionInfo());
		appendChild(new ContentDiv());
		appendCloseButton();
	}

	private IDisplayString getTransactionInfo() {

		IEmfTransactionObject<?> transactionObject = logItem.getTransactionObject();
		String by = Optional//
			.ofNullable(transactionObject.getBy())
			.map(it -> " - " + it.toDisplay())
			.orElse("");
		return IDisplayString.format("%s%s", transactionObject.getAt(), by);
	}

	private class ContentDiv extends DomDiv {

		public ContentDiv() {

			DomLabelGrid attributeDisplayGrid = appendChild(new DomLabelGrid());
			for (IEmfAttribute<R, ?> attribute: getOrderedAttributes()) {
				IDisplayString title = attribute.getTitle();
				attributeDisplayGrid.add(title, attribute.createDisplay(logItem.getImpermanentTableRow()));
			}
		}

		private List<IEmfAttribute<R, ?>> getOrderedAttributes() {

			Collection<IEmfAttribute<R, ?>> attributes = new EmfLogAttributeFilter<>(logItem.getImpermanentTableRow()).getDisplayableAttributes();
			return attributes//
				.stream()
				.sorted(new EmfAttributeByTitleComparator<>())
				.collect(Collectors.toList());
		}
	}
}
