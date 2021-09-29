package com.softicar.platform.core.module.page.navigation.link.display;

import com.softicar.platform.core.module.page.navigation.PageNavigationCssClasses;
import com.softicar.platform.core.module.page.navigation.PageNavigationDiv;
import com.softicar.platform.core.module.page.navigation.PageNavigationFolderController;
import com.softicar.platform.core.module.page.navigation.link.PageNavigationLink;
import com.softicar.platform.dom.elements.DomDiv;

public class PageNavigationFolderDiv extends DomDiv {

	private final PageNavigationFolderController folderController;
	private final PageNavigationFolderDiv parentFolderDiv;
	private final PageNavigationFolderContentDiv contentDiv;

	public PageNavigationFolderDiv(PageNavigationDiv navigationDiv, PageNavigationFolderDiv parentFolderDiv, PageNavigationLink<?> link) {

		this.folderController = navigationDiv.getFolderController();
		this.parentFolderDiv = parentFolderDiv;
		this.contentDiv = new PageNavigationFolderContentDiv(navigationDiv, this, link);

		setCssClass(PageNavigationCssClasses.PAGE_NAVIGATION_FOLDER_DIV);
		appendChild(new PageNavigationFolderTitleDiv(link, this::toggleFolder));
	}

	public PageNavigationFolderDiv getParentFolderDiv() {

		return parentFolderDiv;
	}

	public void appendContentDiv() {

		appendChild(contentDiv);
	}

	public void removeContentDiv() {

		removeChild(contentDiv);
	}

	private void toggleFolder() {

		folderController.toggleFolder(this);
	}
}
