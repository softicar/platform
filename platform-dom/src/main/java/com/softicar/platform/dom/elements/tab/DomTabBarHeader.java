package com.softicar.platform.dom.elements.tab;

import com.softicar.platform.dom.DomCssClasses;
import com.softicar.platform.dom.elements.DomDiv;
import java.util.List;

public class DomTabBarHeader extends DomDiv {

	private final DomTabBar bar;

	public DomTabBarHeader(DomTabBar bar) {

		this.bar = bar;
		setCssClass(DomCssClasses.DOM_TAB_BAR_HEADER);
	}

	public void refresh(List<DomTab> tabs) {

		removeChildren();

		for (DomTab tab: tabs) {
			appendChild(new DomTabHeader(tab, bar));
			appendChild(new DomTabHeaderSeparator());
		}
	}
}
