package com.softicar.platform.core.module.page.header;

import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.core.module.CoreCssClasses;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.dom.elements.button.DomButton;

class PageHeaderNavigationToggleButton extends DomButton {

	public PageHeaderNavigationToggleButton(INullaryVoidFunction navigationToggleFunction) {

		setIcon(CoreImages.PAGE_NAVIGATION_TOGGLE_ICON.getResource());
		setClickCallback(navigationToggleFunction);
		setTitle(CoreI18n.COLLAPSE_OR_EXPAND_NAVIGATION);
		addCssClass(CoreCssClasses.PAGE_HEADER_NAVIGATION_TOGGLE_BUTTON);
	}
}
