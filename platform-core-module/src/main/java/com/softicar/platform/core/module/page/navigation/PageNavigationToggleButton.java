package com.softicar.platform.core.module.page.navigation;

import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.dom.elements.button.DomButton;

public class PageNavigationToggleButton extends DomButton {

	public PageNavigationToggleButton(INullaryVoidFunction navigationToggleFunction) {

		setIcon(CoreImages.PAGE_NAVIGATION_TOGGLE_ICON.getResource());
		setClickCallback(navigationToggleFunction);
		setTitle(CoreI18n.COLLAPSE_OR_EXPAND_NAVIGATION);
	}
}
