package com.softicar.platform.core.module.page;

import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.common.core.locale.CurrentLocale;
import com.softicar.platform.core.module.page.navigation.link.PageNavigationLink;
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

	private final INullaryVoidFunction navigationToggleFunction;

	public PageHeaderAndContentDiv(INullaryVoidFunction navigationToggleFunction) {

		this.navigationToggleFunction = navigationToggleFunction;
	}

	public void setContent(PageNavigationLink<?> link) {

		removeChildren();
		CurrentDomPopupCompositor.get().closeAll();

		DbTableRowCaches.invalidateAll();
		CurrentLocale.set(CurrentUser.get().getLocale());

		changeBrowserUrl(link);
		appendChild(new PageHeaderDiv<>(link, navigationToggleFunction));
		appendChild(new PageContentDiv(link));
	}

	private void changeBrowserUrl(PageNavigationLink<?> link) {

		IDomEngine engine = CurrentDomDocument.get().getEngine();
		engine.pushBrowserHistoryState(link.getTitle(), new PageUrlBuilder<>(link).build().getStartingFromPath());
		engine.setDocumentTitle(link.getTitle());
	}
}
