package com.softicar.platform.emf.entity.table.view;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.dom.elements.tab.DomTabBar;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.entity.table.action.EmfTableActionOverviewTab;
import com.softicar.platform.emf.entity.table.attribute.EmfTableAttributeOverviewTab;
import com.softicar.platform.emf.entity.table.property.EmfTablePropertyTab;
import com.softicar.platform.emf.table.IEmfTable;

public class EmfTableViewPopup extends DomPopup {

	private final DomTabBar tabBar = new DomTabBar();

	public EmfTableViewPopup(IEmfTable<?, ?, ?> table) {

		setCaption(EmfI18n.TABLE_INFORMATION);
		setSubCaption(IDisplayString.create(table.getValueClass().getSimpleName()));
		tabBar.appendTab(new EmfTablePropertyTab(table));
		tabBar.appendTab(new EmfTableAttributeOverviewTab(table));
		tabBar.appendTab(new EmfTableActionOverviewTab(table));
		appendChild(tabBar);
	}
}
