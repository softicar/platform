package com.softicar.platform.dom.elements.tables.pageable.navigation;

import com.softicar.platform.dom.elements.DomElementsCssClasses;
import com.softicar.platform.dom.elements.bar.DomBar;
import com.softicar.platform.dom.elements.tables.pageable.DomPageableTable;
import com.softicar.platform.dom.elements.tables.pageable.DomPageableTableMarker;
import com.softicar.platform.dom.elements.tables.pageable.IDomPageableTableNavigationButtonBuilder;
import java.util.List;

public class DomPageableTableNavigation extends DomBar implements IDomPageableTableNavigation {

	private final DomPageableTable table;
	private final List<IDomPageableTableNavigationButtonBuilder> customButtonBuilders;

	public DomPageableTableNavigation(DomPageableTable table, List<IDomPageableTableNavigationButtonBuilder> customButtonBuilders) {

		this.table = table;
		this.customButtonBuilders = customButtonBuilders;

		setMarker(DomPageableTableMarker.NAVIGATION);
		addCssClass(DomElementsCssClasses.DOM_PAGEABLE_TABLE_NAVIGATION);
	}

	@Override
	public void update() {

		removeChildren();

		if (isOneOrMorePages()) {
			appendCustomButtons();
			if (isMoreThanOnePage()) {
				appendPageNavigationButtons();
			}
		}
	}

	private boolean isOneOrMorePages() {

		return table.getPageSize() >= 1 && table.getPageCount() >= 1;
	}

	private boolean isMoreThanOnePage() {

		return table.getPageCount() > 1;
	}

	private void appendCustomButtons() {

		customButtonBuilders//
			.stream()
			.filter(builder -> builder.isAuthorized())
			.map(builder -> builder.build())
			.forEach(this::appendChild);
	}

	private void appendPageNavigationButtons() {

		appendChild(new GoToPageButton(table));
		appendChild(new TurnPageButton(table, PagingDirection.BACKWARD));
		appendChild(new DomPageableTableNavigationPageList(table));
		appendChild(new TurnPageButton(table, PagingDirection.FORWARD));
	}

	static enum PagingDirection {
		BACKWARD,
		FORWARD
	}
}
