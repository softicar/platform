package com.softicar.platform.core.module.page;

import com.softicar.platform.ajax.document.IAjaxDocumentParameters;
import com.softicar.platform.common.code.reference.point.SourceCodeReferencePointMissingException;
import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.core.module.CoreCssClasses;
import com.softicar.platform.core.module.page.navigation.PageNavigationDiv;
import com.softicar.platform.dom.elements.DomDiv;

public class PageDiv extends DomDiv {

	private final PageHeaderAndContentDiv pageHeaderAndContentDiv;
	private boolean navigationOpen = true;
	private final PageNavigationDiv navigationDiv;

	public PageDiv(IAjaxDocumentParameters documentParameters) {

		this.pageHeaderAndContentDiv = new PageHeaderAndContentDiv(this::collapseOrExpandNavigation);
		this.navigationDiv = new PageNavigationDiv(pageHeaderAndContentDiv);

		appendChild(navigationDiv);
		appendChild(pageHeaderAndContentDiv);

		checkDocumentParameters(documentParameters);
	}

	private void checkDocumentParameters(IAjaxDocumentParameters documentParameters) {

		var parameterParser = new PageParameterParser(documentParameters);
		var pageController = navigationDiv.getPageController();

		try {
			pageController.showPage(parameterParser.getPage(), parameterParser.getModuleInstanceId());
		} catch (SourceCodeReferencePointMissingException exception) {
			DevNull.swallow(exception);
			pageController.showStartPageForNonExistingPage();
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
}
