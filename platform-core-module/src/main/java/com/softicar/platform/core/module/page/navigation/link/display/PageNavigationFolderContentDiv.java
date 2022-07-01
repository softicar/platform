package com.softicar.platform.core.module.page.navigation.link.display;

import com.softicar.platform.core.module.page.navigation.PageNavigationCssClasses;
import com.softicar.platform.core.module.page.navigation.PageNavigationDiv;
import com.softicar.platform.core.module.page.navigation.link.PageNavigationLink;
import com.softicar.platform.dom.elements.DomDiv;

public class PageNavigationFolderContentDiv extends DomDiv {

	private final PageNavigationDiv navigationDiv;
	private final PageNavigationFolderDiv parentLinkDiv;

	public PageNavigationFolderContentDiv(PageNavigationDiv navigationDiv, PageNavigationFolderDiv parentLinkDiv, PageNavigationLink<?> parentLink) {

		this.navigationDiv = navigationDiv;
		this.parentLinkDiv = parentLinkDiv;

		setCssClass(PageNavigationCssClasses.PAGE_NAVIGATION_FOLDER_CONTENT_DIV);
		parentLink.getChildren().forEach(this::appendChild);
	}

	private void appendChild(PageNavigationLink<?> link) {

		var linkDiv = createLinkDiv(link);

		if (link.isVisible()) {
			appendChild(linkDiv);
		}
	}

	private DomDiv createLinkDiv(PageNavigationLink<?> link) {

		if (link.isFolder()) {
			return navigationDiv.getFolderController().createFolderDiv(parentLinkDiv, link);
		} else {
			return navigationDiv.getPageController().createPageLinkDiv(parentLinkDiv, link);
		}
	}
}
