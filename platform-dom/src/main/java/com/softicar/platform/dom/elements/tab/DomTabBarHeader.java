package com.softicar.platform.dom.elements.tab;

import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomElementsCssClasses;
import java.util.List;

public class DomTabBarHeader extends DomDiv {

	private final DomTabBar bar;

	public DomTabBarHeader(DomTabBar bar) {

		this.bar = bar;
		setCssClass(DomElementsCssClasses.DOM_TAB_BAR_HEADER);
	}

	public void refresh(List<DomTab> tabs) {

		removeChildren();

		for (DomTab tab: tabs) {
			appendChild(new DomTabHeader(tab, bar));
			appendChild(new DomTabHeaderSeparator());
		}
	}
}
