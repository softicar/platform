package com.softicar.platform.emf.log.viewer;

import com.softicar.platform.dom.element.IDomElement;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.tab.DomTab;
import com.softicar.platform.dom.elements.tab.DomTabBar;
import com.softicar.platform.dom.elements.tab.DomTabBuilder;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.log.viewer.feed.EmfLogFeedDiv;
import com.softicar.platform.emf.log.viewer.table.EmfLogDataTableDiv;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public class EmfLogDiv<R extends IEmfTableRow<R, ?>> extends DomDiv {

	public EmfLogDiv(R tableRow) {

		DomTab feedTab = new DomTabBuilder()//
			.setContentSupplier(() -> createLogFeedElement(tableRow))
			.setLabel(EmfI18n.FEED)
			.build()
			.setHeaderMarker(EmfLogMarker.FEED_TAB);

		DomTab tableTab = new DomTabBuilder()//
			.setContentSupplier(() -> createLogDataTableElement(tableRow))
			.setLabel(EmfI18n.TABLE)
			.build()
			.setHeaderMarker(EmfLogMarker.TABLE_TAB);

		DomTabBar tabBar = appendChild(new DomTabBar());
		tabBar.appendTab(feedTab);
		tabBar.appendTab(tableTab);
	}

	private IDomElement createLogFeedElement(R tableRow) {

		return new EmfLogFeedDiv<>(tableRow);
	}

	private IDomElement createLogDataTableElement(R tableRow) {

		return new EmfLogDataTableDiv<>(tableRow);
	}
}
