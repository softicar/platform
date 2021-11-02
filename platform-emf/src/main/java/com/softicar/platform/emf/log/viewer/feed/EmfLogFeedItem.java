package com.softicar.platform.emf.log.viewer.feed;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.table.row.IEmfTableRow;

class EmfLogFeedItem<R extends IEmfTableRow<R, ?>, V> {

	private final IEmfAttribute<R, V> attribute;
	private final R tableRow;
	private final EmfLogFeedItemType type;

	public EmfLogFeedItem(IEmfAttribute<R, V> attribute, R tableRow, EmfLogFeedItemType type) {

		this.attribute = attribute;
		this.tableRow = tableRow;
		this.type = type;
	}

	public IEmfAttribute<R, V> getAttribute() {

		return attribute;
	}

	public R getTableRow() {

		return tableRow;
	}

	public IDisplayString getTitle() {

		return attribute.getTitle();
	}

	public EmfLogFeedItemType getType() {

		return type;
	}
}
