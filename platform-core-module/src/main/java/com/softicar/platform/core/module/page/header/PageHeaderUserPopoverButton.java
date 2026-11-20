package com.softicar.platform.core.module.page.header;

import com.softicar.platform.core.module.page.PageTheme;
import com.softicar.platform.core.module.page.navigation.modern.ModernNavigationCssClasses;
import com.softicar.platform.common.string.unicode.UnicodeEnum;
import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.core.module.CoreTestMarker;
import com.softicar.platform.core.module.page.navigation.PageNavigationController;
import com.softicar.platform.core.module.user.CurrentUser;
import com.softicar.platform.dom.elements.button.DomButton;

class PageHeaderUserPopoverButton extends DomButton {

	public PageHeaderUserPopoverButton(PageNavigationController controller) {

		if (PageTheme.getCurrent() == PageTheme.MODERN) {
			// an avatar with the initials, the full name as tooltip
			setLabel(getInitials(CurrentUser.get().getFirstAndLastName()));
			setTitle(CurrentUser.get().toDisplayWithoutId());
			addCssClass(ModernNavigationCssClasses.MODERN_HEADER_AVATAR);
		} else {
			setLabel(CurrentUser.get().toDisplayWithoutId() + " " + UnicodeEnum.BLACK_DOWN_POINTING_SMALL_TRIANGLE);
			setIcon(CoreImages.USER.getResource());
		}
		setClickCallback(new PageHeaderUserPopover(controller)::open);
		addMarker(CoreTestMarker.PAGE_HEADER_USER_POPOVER_BUTTON);
	}

	static String getInitials(String name) {

		StringBuilder initials = new StringBuilder();
		for (String part: name.trim().split("\\s+")) {
			if (!part.isEmpty() && initials.length() < 2) {
				initials.appendCodePoint(Character.toUpperCase(part.codePointAt(0)));
			}
		}
		return initials.toString();
	}
}
