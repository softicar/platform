package com.softicar.platform.core.module.page.navigation;

import com.softicar.platform.core.module.page.PageHeaderAndContentDiv;
import com.softicar.platform.core.module.page.navigation.link.PageNavigationLinkLoader;
import com.softicar.platform.core.module.page.navigation.link.display.PageNavigationFolderContentDiv;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.refresh.bus.IDomRefreshBusEvent;
import com.softicar.platform.dom.refresh.bus.IDomRefreshBusListener;

public class PageNavigationDiv extends DomDiv implements IDomRefreshBusListener {

	private final PageNavigationFolderController folderController;
	private final PageNavigationPageController pageController;

	public PageNavigationDiv(PageHeaderAndContentDiv pageHeaderAndContentDiv) {

		this.folderController = new PageNavigationFolderController(this);
		this.pageController = new PageNavigationPageController(folderController, pageHeaderAndContentDiv);

		setCssClass(PageNavigationCssClasses.PAGE_NAVIGATION_DIV);

		refresh();
	}

	@Override
	public void refresh(IDomRefreshBusEvent event) {

		if (event.isAnyObjectChanged(PageNavigationDiv.class)) {
			refresh();
		}
	}

	public PageNavigationFolderController getFolderController() {

		return folderController;
	}

	public PageNavigationPageController getPageController() {

		return pageController;
	}

	private void refresh() {

		removeChildren();
		appendChild(new PageNavigationLogoDiv());
		appendChild(new PageNavigationFolderContentDiv(this, null, PageNavigationLinkLoader.loadRootLink()));
	}
}
