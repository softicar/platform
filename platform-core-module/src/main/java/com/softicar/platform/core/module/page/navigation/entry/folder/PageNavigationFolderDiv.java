package com.softicar.platform.core.module.page.navigation.entry.folder;

import com.softicar.platform.core.module.CoreCssClasses;
import com.softicar.platform.core.module.page.navigation.PageNavigationController;
import com.softicar.platform.core.module.page.navigation.entry.PageNavigationEntry;
import com.softicar.platform.dom.DomCssPseudoClasses;
import com.softicar.platform.dom.elements.DomDiv;

public class PageNavigationFolderDiv extends DomDiv {

	private final PageNavigationController controller;
	private final PageNavigationFolderDiv parentFolderDiv;
	private final PageNavigationFolderContentDiv contentDiv;

	public PageNavigationFolderDiv(PageNavigationController controller, PageNavigationFolderDiv parentFolderDiv, PageNavigationEntry<?> folderEntry) {

		this.controller = controller;
		this.parentFolderDiv = parentFolderDiv;
		this.contentDiv = new PageNavigationFolderContentDiv(controller, folderEntry, this);

		setCssClass(CoreCssClasses.PAGE_NAVIGATION_FOLDER_DIV);
		appendChild(new PageNavigationFolderTitleDiv(folderEntry, this::toggle));
	}

	public PageNavigationFolderDiv getParentFolderDiv() {

		return parentFolderDiv;
	}

	public void open() {

		addCssClass(DomCssPseudoClasses.SELECTED);
		appendChild(contentDiv);
	}

	public void close() {

		removeCssClass(DomCssPseudoClasses.SELECTED);
		if (hasChild(contentDiv)) {
			removeChild(contentDiv);
		}
	}

	private void toggle() {

		controller.toggleFolder(this);
	}
}
