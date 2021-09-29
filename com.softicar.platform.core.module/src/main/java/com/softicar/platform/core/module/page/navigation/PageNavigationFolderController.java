package com.softicar.platform.core.module.page.navigation;

import com.softicar.platform.common.container.set.Sets;
import com.softicar.platform.core.module.page.navigation.link.PageNavigationLink;
import com.softicar.platform.core.module.page.navigation.link.display.PageNavigationFolderDiv;
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
			closeFolder(folderDiv);
		} else {
			openFolderAndCloseOthers(folderDiv);
		}
	}

	public void openFolderAndCloseOthers(PageNavigationFolderDiv folderDiv) {

		Set<PageNavigationFolderDiv> desiredFolderDivs = new HashSet<>();
		while (folderDiv != null) {
			desiredFolderDivs.add(folderDiv);
			folderDiv = folderDiv.getParentFolderDiv();
		}
		Sets.difference(openFolderDivs, desiredFolderDivs).forEach(this::closeFolder);
		Sets.difference(desiredFolderDivs, openFolderDivs).forEach(this::openFolder);
	}

	public void openFolder(PageNavigationFolderDiv folderDiv) {

		folderDiv.addCssClass(DomCssPseudoClasses.SELECTED);
		folderDiv.appendContentDiv();
		openFolderDivs.add(folderDiv);
	}

	public void closeFolder(PageNavigationFolderDiv folderDiv) {

		folderDiv.removeCssClass(DomCssPseudoClasses.SELECTED);
		folderDiv.removeContentDiv();
		openFolderDivs.remove(folderDiv);
	}
}
