package com.softicar.platform.dom.elements.tab;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.common.core.interfaces.IStaticObject;
import com.softicar.platform.dom.elements.DomDiv;
import java.util.Optional;

/**
 * A single tab of a {@link DomTabBar}
 *
 * @author Torsten Sommerfeld
 */
public class DomTab extends DomDiv {

	private final IDisplayString label;
	private DomTabBar tabBar;
	private INullaryVoidFunction onShowRefreshable;
	private INullaryVoidFunction onHideRefreshable;
	private Optional<IStaticObject> headerMarker;

	/**
	 * Constructs a new {@link DomTab} with the given label.
	 *
	 * @param label
	 *            the label of this tab
	 */
	public DomTab(IDisplayString label) {

		this.label = label;
		this.headerMarker = Optional.empty();
	}

	/**
	 * Makes this {@link DomTab} element the current tab of the parent
	 * {@link DomTabBar}.
	 */
	public void show() {

		if (tabBar != null) {
			tabBar.showTab(this);
		}
	}

	/**
	 * Returns the label of this {@link DomTab} element.
	 *
	 * @return label
	 */
	protected IDisplayString getLabel() {

		return label;
	}

	/**
	 * Sets the {@link DomTabBar} element of this {@link DomTab}.
	 *
	 * @param tabBar
	 *            the {@link DomTabBar}
	 */
	protected void setTabBar(DomTabBar tabBar) {

		this.tabBar = tabBar;
	}

	/**
	 * Sets the {@link INullaryVoidFunction} that will be executed on
	 * {@link DomTab#show()}.
	 *
	 * @param onShowRefreshable
	 *            the {@link INullaryVoidFunction}
	 */
	public void setOnShowRefreshable(INullaryVoidFunction onShowRefreshable) {

		this.onShowRefreshable = onShowRefreshable;
	}

	public void executeOnShowRefreshable() {

		if (onShowRefreshable != null) {
			onShowRefreshable.apply();
		}
	}

	/**
	 * Sets the {@link INullaryVoidFunction} that will be executed on
	 * {@link DomTab#show()} of another tab (hide of current).
	 *
	 * @param onHideRefreshable
	 *            the {@link INullaryVoidFunction}
	 */
	public void setOnHideRefreshable(INullaryVoidFunction onHideRefreshable) {

		this.onHideRefreshable = onHideRefreshable;
	}

	public void executeOnHideRefreshable() {

		if (onHideRefreshable != null) {
			onHideRefreshable.apply();
		}
	}

	public DomTab setHeaderMarker(IStaticObject headerMarker) {

		this.headerMarker = Optional.of(headerMarker);
		return this;
	}

	public Optional<IStaticObject> getHeaderMarker() {

		return headerMarker;
	}
}
