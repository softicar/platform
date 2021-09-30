package com.softicar.platform.emf.management.prefilter;

import com.softicar.platform.common.core.interfaces.Consumers;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.dom.elements.tab.DomTab;
import com.softicar.platform.dom.elements.tab.DomTabBar;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.EmfImages;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class EmfPrefilterPopup<T extends IEmfTableRow<T, ?>> extends DomPopup {

	private final List<IEmfPrefilterElement<T>> prefilterElements;
	private final DomDiv tabBarContainer;
	private Consumer<Set<T>> callback;

	public EmfPrefilterPopup() {

		this.callback = Consumers.noOperation();
		this.prefilterElements = new ArrayList<>();
		this.tabBarContainer = new DomDiv();
		setCaption(EmfI18n.PREFILTER);
		appendChild(tabBarContainer);
		appendActionNode(
			new DomButton()//
				.setClickCallback(() -> applyFilter())
				.setIcon(EmfImages.FIND.getResource())
				.setLabel(EmfI18n.APPLY));
		appendCancelButton();
		appendActionNode(
			new DomButton()//
				.setClickCallback(() -> resetTabBar())
				.setIcon(EmfImages.REFRESH.getResource())
				.setLabel(EmfI18n.RESET));
	}

	public void setPrefilterCallback(Consumer<Set<T>> callback) {

		this.callback = callback;
	}

	protected EmfPrefilterPopup<T> addPrefilterElement(IEmfPrefilterElement<T> prefilterElement) {

		this.prefilterElements.add(prefilterElement);
		refreshTabBar();
		return this;
	}

	protected void applyFilter() {

		Set<T> filteredItems = getFilteredItems();
		this.callback.accept(filteredItems);
		hide();
	}

	protected Set<T> getFilteredItems() {

		List<Set<T>> filteredItemSets = getFilteredItemSets();
		Set<T> remaining = new HashSet<>();
		boolean first = true;
		for (Set<T> set: filteredItemSets) {
			if (first) {
				remaining.addAll(set);
			} else {
				remaining.retainAll(set);
			}
			first = false;
		}
		return remaining;
	}

	private DomTabBar refreshTabBar() {

		DomTabBar tabBar = buildTabBar();
		this.tabBarContainer.removeChildren();
		this.tabBarContainer.appendChild(tabBar);
		return tabBar;
	}

	private DomTabBar buildTabBar() {

		DomTabBar tabBar = new DomTabBar();
		prefilterElements//
			.stream()
			.map(this::buildTab)
			.forEach(tabBar::appendTab);
		return tabBar;
	}

	private DomTab buildTab(IEmfPrefilterElement<T> prefilterElement) {

		prefilterElement.disappend();
		DomTab tab = new DomTab(prefilterElement.getCaption());
		tab.appendChild(prefilterElement);
		return tab;
	}

	private void resetTabBar() {

		prefilterElements//
			.stream()
			.forEach(IEmfPrefilterElement::reset);
	}

	private List<Set<T>> getFilteredItemSets() {

		return prefilterElements//
			.stream()
			.map(IEmfPrefilterElement::getFilteredItems)
			.map(HashSet::new)
			.collect(Collectors.toList());
	}
}
