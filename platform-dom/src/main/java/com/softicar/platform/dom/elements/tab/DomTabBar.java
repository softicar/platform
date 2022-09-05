package com.softicar.platform.dom.elements.tab;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.DomCssClasses;
import com.softicar.platform.dom.elements.DomDiv;
import java.util.ArrayList;
import java.util.List;

public class DomTabBar extends DomDiv {

	private final List<DomTab> tabs;
	private final DomTabBarHeader tabBarHeader;
	private final DomTabBarContentContainer tabContentContainer;
	private DomTab currentTab;

	public DomTabBar() {

		this.tabs = new ArrayList<>();
		this.tabBarHeader = new DomTabBarHeader(this);
		this.tabContentContainer = new DomTabBarContentContainer(this);
		this.currentTab = null;
		setCssClass(DomCssClasses.DOM_TAB_BAR);
		addCssClass(DomCssClasses.DOM_TAB_BAR_HORIZONTAL);

		appendChild(tabBarHeader);
		appendChild(tabContentContainer);
	}

	public DomTabBar setVerticalDisplay() {

		removeCssClass(DomCssClasses.DOM_TAB_BAR_HORIZONTAL);
		addCssClass(DomCssClasses.DOM_TAB_BAR_VERTICAL);
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
			tabBarHeader.refresh(tabs);
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
		tabBarHeader.refresh(tabs);
		tabContentContainer.refresh();

		currentTab.executeOnShowRefreshable();
	}

	public DomTab getCurrentTab() {

		return currentTab;
	}
}
