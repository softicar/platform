package com.softicar.platform.dom.elements.tab;

import com.softicar.platform.dom.DomCssClasses;
import com.softicar.platform.dom.elements.DomDiv;
import java.util.Optional;

class DomTabBarContentContainer extends DomDiv {

	private final DomTabBar tabBar;

	public DomTabBarContentContainer(DomTabBar tabBar) {

		this.tabBar = tabBar;

		setCssClass(DomCssClasses.DOM_TAB_BAR_CONTENT_CONTAINER);
	}

	public void refresh() {

		removeChildren();
		Optional.ofNullable(tabBar.getCurrentTab()).ifPresent(this::appendChild);
	}
}
