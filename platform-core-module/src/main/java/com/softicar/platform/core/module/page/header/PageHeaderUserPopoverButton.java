package com.softicar.platform.core.module.page.header;

import com.softicar.platform.common.string.unicode.UnicodeEnum;
import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.core.module.CoreTestMarker;
import com.softicar.platform.core.module.page.navigation.PageNavigationController;
import com.softicar.platform.core.module.user.CurrentUser;
import com.softicar.platform.dom.elements.button.DomButton;

class PageHeaderUserPopoverButton extends DomButton {

	public PageHeaderUserPopoverButton(PageNavigationController controller) {

		setLabel(CurrentUser.get().toDisplayWithoutId() + " " + UnicodeEnum.BLACK_DOWN_POINTING_SMALL_TRIANGLE);
		setIcon(CoreImages.USER.getResource());
		setClickCallback(new PageHeaderUserPopover(controller)::open);
		addMarker(CoreTestMarker.PAGE_HEADER_USER_POPOVER_BUTTON);
	}
}
