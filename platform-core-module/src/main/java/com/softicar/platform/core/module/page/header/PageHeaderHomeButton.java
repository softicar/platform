package com.softicar.platform.core.module.page.header;

import com.softicar.platform.common.code.reference.point.SourceCodeReferencePoints;
import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.page.navigation.PageNavigationController;
import com.softicar.platform.core.module.page.navigation.modern.ModernNavigationCssClasses;
import com.softicar.platform.core.module.start.page.StartPage;
import com.softicar.platform.dom.elements.button.DomButton;

/**
 * The button of the modern theme that opens the {@link StartPage}; its icon is
 * drawn by the style sheet of the theme.
 */
class PageHeaderHomeButton extends DomButton {

	public PageHeaderHomeButton(PageNavigationController controller) {

		addCssClass(ModernNavigationCssClasses.MODERN_HEADER_HOME);
		setTitle(SourceCodeReferencePoints.getReferencePoint(StartPage.class).getTitle(AGCoreModuleInstance.getInstance()));
		setClickCallback(() -> controller.openPage(SourceCodeReferencePoints.getReferencePoint(StartPage.class), AGCoreModuleInstance.getInstance().getId()));
	}
}
