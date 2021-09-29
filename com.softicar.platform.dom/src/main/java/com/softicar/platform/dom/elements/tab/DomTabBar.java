package com.softicar.platform.dom.elements.tab;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomElementsCssClasses;
import java.util.ArrayList;
import java.util.List;

public class DomTabBar extends DomDiv {

	private final List<DomTab> tabs;
	private final TabBarHeader tabBarHeaderContainer;
	private final DomTabBarContentContainer tabContentContainer;
	private DomTab currentTab;

	public DomTabBar() {

		this.tabs = new ArrayList<>();
		this.tabBarHeaderContainer = new TabBarHeader();
		this.tabContentContainer = new DomTabBarContentContainer(this);
		this.currentTab = null;
		setCssClass(DomElementsCssClasses.DOM_TAB_BAR);
		addCssClass(DomElementsCssClasses.DOM_TAB_BAR_HORIZONTAL);

		appendChild(tabBarHeaderContainer);
		appendChild(tabContentContainer);
	}

	public DomTabBar setVerticalDisplay() {

		removeCssClass(DomElementsCssClasses.DOM_TAB_BAR_HORIZONTAL);
		addCssClass(DomElementsCssClasses.DOM_TAB_BAR_VERTICAL);
		return this;
	}

	/**
	 * Appends a new {@link DomTab} with the given label.
	 *
	 * @param label
	 *            the label of the new {@link DomTab} (never <i>null</i>)
	 * @return the appended {@link DomTab} element (never <i>null</i>)
	 */
	public DomTab appendTab(IDisplayString label) {

		return appendTab(new DomTab(label));
	}

	/**
	 * Appends the given {@link DomTab}.
	 * <p>
	 * You may not append the same {@link DomTab} element at multiple
	 * {@link DomTabBar} elements.
	 *
	 * @param tab
	 *            the {@link DomTab} element to append (never <i>null</i>)
	 * @return the appended {@link DomTab} element (never <i>null</i>)
	 */
	public DomTab appendTab(DomTab tab) {

		tab.setTabBar(this);
		tabs.add(tab);

		if (currentTab == null) {
			showTab(tab);
		} else {
			tabBarHeaderContainer.refresh();
		}
		return tab;
	}

	public void showTab(DomTab tab) {

		if (!tabs.contains(tab)) {
			throw new SofticarDeveloperException("You cannot show tab '%s' which is not appended to the tab bar '%s'.", tab, this);
		}

		if (currentTab != null) {
			currentTab.executeOnHideRefreshable();
		}

		currentTab = tab;
		tabBarHeaderContainer.refresh();
		tabContentContainer.refresh();

		currentTab.executeOnShowRefreshable();
	}

	public DomTab getCurrentTab() {

		return currentTab;
	}

	private class TabBarHeader extends DomDiv {

		public TabBarHeader() {

			setCssClass(DomElementsCssClasses.DOM_TAB_BAR_HEADER);
		}

		public void refresh() {

			removeChildren();

			for (DomTab tab: tabs) {
				appendChild(new DomTabHeader(tab, DomTabBar.this));
				appendChild(new DomTabHeaderSeparator());
			}
		}
	}
}
