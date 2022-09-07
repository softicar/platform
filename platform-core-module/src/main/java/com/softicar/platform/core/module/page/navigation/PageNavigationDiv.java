package com.softicar.platform.core.module.page.navigation;

import com.softicar.platform.core.module.CoreCssClasses;
import com.softicar.platform.core.module.page.PageHeaderAndContentDiv;
import com.softicar.platform.core.module.page.navigation.link.PageNavigationLinkLoader;
import com.softicar.platform.core.module.page.navigation.link.display.PageNavigationFolderContentDiv;
import com.softicar.platform.dom.elements.DomDiv;

public class PageNavigationDiv extends DomDiv {

	private final PageNavigationFolderController folderController;
	private final PageNavigationPageController pageController;

	public PageNavigationDiv(PageHeaderAndContentDiv pageHeaderAndContentDiv) {

		this.folderController = new PageNavigationFolderController(this);
		this.pageController = new PageNavigationPageController(folderController, pageHeaderAndContentDiv);

		setCssClass(CoreCssClasses.PAGE_NAVIGATION_DIV);

		appendChild(new PageNavigationLogoDiv());
		appendChild(new PageNavigationFolderContentDiv(this, null, PageNavigationLinkLoader.loadRootLink()));
	}

	public PageNavigationFolderController getFolderController() {

		return folderController;
	}

	public PageNavigationPageController getPageController() {

		return pageController;
	}
}
