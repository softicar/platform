package com.softicar.platform.core.module.page.navigation;

import com.softicar.platform.core.module.CoreCssClasses;
import com.softicar.platform.core.module.page.navigation.entry.PageNavigationEntry;
import com.softicar.platform.core.module.page.navigation.entry.folder.PageNavigationFolderContentDiv;
import com.softicar.platform.dom.elements.DomDiv;

public class PageNavigationDiv extends DomDiv {

	public PageNavigationDiv(PageNavigationController controller, PageNavigationEntry<?> rootEntry) {

		setCssClass(CoreCssClasses.PAGE_NAVIGATION_DIV);

		appendChild(new PageNavigationLogoDiv());
		appendChild(new PageNavigationFolderContentDiv(controller, rootEntry));
	}
}
