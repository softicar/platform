package com.softicar.platform.core.module.page;

import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.common.core.locale.CurrentLocale;
import com.softicar.platform.common.string.normalizer.CurrentDiacriticNormalizationCache;
import com.softicar.platform.core.module.page.header.PageHeaderDiv;
import com.softicar.platform.core.module.page.navigation.PageNavigationController;
import com.softicar.platform.core.module.page.navigation.entry.PageNavigationEntry;
import com.softicar.platform.core.module.user.CurrentUser;
import com.softicar.platform.db.runtime.cache.DbTableRowCaches;
import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.popup.compositor.CurrentDomPopupCompositor;
import com.softicar.platform.dom.engine.IDomEngine;

/**
 * Container for the {@link PageHeaderDiv} and {@link PageContentDiv}.
 *
 * @author Oliver Richers
 */
public class PageHeaderAndContentDiv extends DomDiv {

	private final PageNavigationController controller;
	private final INullaryVoidFunction navigationToggleFunction;

	public PageHeaderAndContentDiv(PageNavigationController controller, INullaryVoidFunction navigationToggleFunction) {

		this.controller = controller;
		this.navigationToggleFunction = navigationToggleFunction;
	}

	public void openPage(PageNavigationEntry<?> linkEntry) {

		removeChildren();

		CurrentDomPopupCompositor.get().closeAll();
		CurrentDomDocument.get().getDeferredInitializationController().clear();
		CurrentDomDocument.get().getRefreshBus().discardEvent();
		CurrentLocale.set(CurrentUser.get().getLocale());
		DbTableRowCaches.invalidateAll();
		CurrentDiacriticNormalizationCache.get().clear();
		System.gc();

		changeBrowserUrl(linkEntry);
		appendChild(new PageHeaderDiv<>(linkEntry, navigationToggleFunction, controller));
		appendChild(new PageContentDiv(linkEntry));

		CurrentDomDocument.get().getDeferredInitializationController().handleAllAppended();
	}

	private void changeBrowserUrl(PageNavigationEntry<?> linkEntry) {

		IDomEngine engine = CurrentDomDocument.get().getEngine();
		engine.pushBrowserHistoryState(linkEntry.getTitle(), new PageUrlBuilder<>(linkEntry).build().getStartingFromPath());
		engine.setDocumentTitle(linkEntry.getTitle());
	}
}
