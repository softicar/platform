package com.softicar.platform.core.module.page;

import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.core.module.page.navigation.link.PageNavigationLink;
import com.softicar.platform.db.runtime.cache.DbTableRowCaches;
import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.popup.IDomPopupFrame;
import com.softicar.platform.dom.engine.IDomEngine;
import java.util.stream.Collectors;

/**
 * Container for the {@link PageHeaderDiv} and {@link PageContentDiv}.
 *
 * @author Oliver Richers
 */
public class PageHeaderAndContentDiv extends DomDiv {

	private final INullaryVoidFunction navigationToggleFunction;

	public PageHeaderAndContentDiv(INullaryVoidFunction navigationToggleFunction) {

		this.navigationToggleFunction = navigationToggleFunction;
	}

	public void setContent(PageNavigationLink<?> link) {

		removeChildren();
		closeRemainingPopupFrames();

		DbTableRowCaches.invalidateAll();

		changeBrowserUrl(link);
		appendChild(new PageHeaderDiv<>(link, navigationToggleFunction));
		appendChild(new PageContentDiv(link));
	}

	private void closeRemainingPopupFrames() {

		CurrentDomDocument//
			.get()
			.getBody()
			.getChildren()
			.stream()
			.filter(IDomPopupFrame.class::isInstance)
			.map(IDomPopupFrame.class::cast)
			// do not remove this call - it avoids a ConcurrentModificationException when changing pages while a popup is open
			.collect(Collectors.toList())
			.forEach(IDomPopupFrame::closePopup);
	}

	private void changeBrowserUrl(PageNavigationLink<?> link) {

		IDomEngine engine = CurrentDomDocument.get().getEngine();
		engine.pushBrowserHistoryState(link.getTitle(), new PageUrlBuilder<>(link).build().getStartingFromPath());
		engine.setDocumentTitle(link.getTitle());
	}
}
