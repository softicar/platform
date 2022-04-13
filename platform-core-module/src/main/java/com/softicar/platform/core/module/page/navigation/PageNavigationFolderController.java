package com.softicar.platform.core.module.page.navigation;

import com.softicar.platform.common.container.set.Sets;
import com.softicar.platform.core.module.page.navigation.link.PageNavigationLink;
import com.softicar.platform.core.module.page.navigation.link.display.PageNavigationFolderDiv;
import com.softicar.platform.core.module.user.CurrentUser;
import com.softicar.platform.dom.DomCssPseudoClasses;
import java.util.HashSet;
import java.util.Set;

public class PageNavigationFolderController {

	private final PageNavigationDiv navigationDiv;
	private final Set<PageNavigationFolderDiv> openFolderDivs;

	public PageNavigationFolderController(PageNavigationDiv navigationDiv) {

		this.navigationDiv = navigationDiv;
		this.openFolderDivs = new HashSet<>();
	}

	public PageNavigationFolderDiv createFolderDiv(PageNavigationFolderDiv parentFolderDiv, PageNavigationLink<?> folderLink) {

		return new PageNavigationFolderDiv(navigationDiv, parentFolderDiv, folderLink);
	}

	public void toggleFolder(PageNavigationFolderDiv folderDiv) {

		if (openFolderDivs.contains(folderDiv)) {
			close(folderDiv);
		} else {
			openFolder(folderDiv);
		}
	}

	public void openFolder(PageNavigationFolderDiv folderDiv) {

		Set<PageNavigationFolderDiv> desiredFolderDivs = new HashSet<>();
		while (folderDiv != null) {
			desiredFolderDivs.add(folderDiv);
			folderDiv = folderDiv.getParentFolderDiv();
		}
		if (CurrentUser.get().isAutomaticallyCollapseFolders()) {
			Sets.difference(openFolderDivs, desiredFolderDivs).forEach(this::close);
		}
		Sets.difference(desiredFolderDivs, openFolderDivs).forEach(this::open);
	}

	private void open(PageNavigationFolderDiv folderDiv) {

		folderDiv.addCssClass(DomCssPseudoClasses.SELECTED);
		folderDiv.appendContentDiv();
		openFolderDivs.add(folderDiv);
	}

	private void close(PageNavigationFolderDiv folderDiv) {

		folderDiv.removeCssClass(DomCssPseudoClasses.SELECTED);
		folderDiv.removeContentDiv();
		openFolderDivs.remove(folderDiv);
	}
}
