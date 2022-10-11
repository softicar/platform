package com.softicar.platform.core.module.page.navigation.entry.folder;

import com.softicar.platform.core.module.CoreCssClasses;
import com.softicar.platform.core.module.page.navigation.PageNavigationController;
import com.softicar.platform.core.module.page.navigation.entry.PageNavigationEntry;
import com.softicar.platform.core.module.page.navigation.entry.link.PageNavigationLinkDiv;
import com.softicar.platform.dom.elements.DomDiv;
import java.util.Objects;

public class PageNavigationFolderContentDiv extends DomDiv {

	private final PageNavigationController controller;
	private final PageNavigationFolderDiv parentFolderDiv;

	public PageNavigationFolderContentDiv(PageNavigationController controller, PageNavigationEntry<?> entry) {

		this(controller, entry, null);
	}

	public PageNavigationFolderContentDiv(PageNavigationController controller, PageNavigationEntry<?> entry, PageNavigationFolderDiv parentFolderDiv) {

		Objects.requireNonNull(controller);
		Objects.requireNonNull(entry);

		this.controller = controller;
		this.parentFolderDiv = parentFolderDiv;

		setCssClass(CoreCssClasses.PAGE_NAVIGATION_FOLDER_CONTENT_DIV);
		entry.getChildren().forEach(this::appendChild);
	}

	private void appendChild(PageNavigationEntry<?> entry) {

		if (entry.isFolder()) {
			appendChild(createFolderDiv(parentFolderDiv, entry));
		} else if (entry.getPage().isListed()) {
			appendChild(createLinkDiv(parentFolderDiv, entry));
		}
	}

	private PageNavigationFolderDiv createFolderDiv(PageNavigationFolderDiv parentFolderDiv, PageNavigationEntry<?> folderEntry) {

		return new PageNavigationFolderDiv(controller, parentFolderDiv, folderEntry);
	}

	private PageNavigationLinkDiv createLinkDiv(PageNavigationFolderDiv parentFolderDiv, PageNavigationEntry<?> linkEntry) {

		var linkDiv = new PageNavigationLinkDiv(controller, parentFolderDiv, linkEntry);
		return controller.registerLinkDiv(linkDiv);
	}
}
