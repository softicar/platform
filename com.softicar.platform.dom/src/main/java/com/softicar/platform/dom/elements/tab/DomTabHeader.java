package com.softicar.platform.dom.elements.tab;

import com.softicar.platform.dom.DomCssPseudoClasses;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomElementsCssClasses;
import com.softicar.platform.dom.event.IDomClickEventHandler;
import com.softicar.platform.dom.event.IDomEvent;

public class DomTabHeader extends DomDiv implements IDomClickEventHandler {

	private final DomTab tab;
	private final DomTabBar tabBar;

	public DomTabHeader(DomTab tab, DomTabBar tabBar) {

		this.tab = tab;
		this.tabBar = tabBar;

		setCssClass(DomElementsCssClasses.DOM_TAB_HEADER);

		if (isCurrentTab()) {
			addCssClass(DomCssPseudoClasses.SELECTED);
		}
		appendText(tab.getLabel());

		tab.getHeaderMarker().ifPresent(this::setMarker);
	}

	@Override
	public void handleClick(IDomEvent event) {

		tabBar.showTab(tab);
	}

	private boolean isCurrentTab() {

		return tab == tabBar.getCurrentTab();
	}
}
