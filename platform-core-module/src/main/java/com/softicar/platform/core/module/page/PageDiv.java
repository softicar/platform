package com.softicar.platform.core.module.page;

import com.softicar.platform.ajax.document.IAjaxDocumentParameters;
import com.softicar.platform.common.code.reference.point.SourceCodeReferencePointMissingException;
import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.core.module.CoreCssClasses;
import com.softicar.platform.core.module.page.navigation.PageNavigationController;
import com.softicar.platform.core.module.page.navigation.PageNavigationDiv;
import com.softicar.platform.core.module.page.navigation.entry.PageNavigationEntriesLoader;
import com.softicar.platform.core.module.page.navigation.entry.PageNavigationEntry;
import com.softicar.platform.dom.elements.DomDiv;

public class PageDiv extends DomDiv {

	private final PageHeaderAndContentDiv pageHeaderAndContentDiv;
	private final PageNavigationDiv navigationDiv;
	private boolean navigationOpen = true;

	public PageDiv(IAjaxDocumentParameters documentParameters) {

		var rootEntry = PageNavigationEntriesLoader.loadRootEntry();
		var controller = new PageNavigationController(this::openPage, rootEntry);

		this.pageHeaderAndContentDiv = new PageHeaderAndContentDiv(controller, this::collapseOrExpandNavigation);
		this.navigationDiv = new PageNavigationDiv(controller, rootEntry);

		appendChild(navigationDiv);
		appendChild(pageHeaderAndContentDiv);

		handleDocumentParameters(documentParameters, controller);
	}

	private void handleDocumentParameters(IAjaxDocumentParameters documentParameters, PageNavigationController controller) {

		try {
			var parameterParser = new PageParameterParser(documentParameters);
			controller.openPage(parameterParser.getPage(), parameterParser.getModuleInstanceId());
		} catch (SourceCodeReferencePointMissingException exception) {
			DevNull.swallow(exception);
			controller.openStartPageForNonExistingPage();
		}
	}

	private void collapseOrExpandNavigation() {

		this.navigationOpen = !navigationOpen;
		if (navigationOpen) {
			removeCssClass(CoreCssClasses.PAGE_NAVIGATION_COLLAPSED);
		} else {
			addCssClass(CoreCssClasses.PAGE_NAVIGATION_COLLAPSED);
		}
	}

	private void openPage(PageNavigationEntry<?> linkEntry) {

		pageHeaderAndContentDiv.openPage(linkEntry);
	}
}
