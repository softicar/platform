package com.softicar.platform.core.module.page.navigation.entry.link;

import com.softicar.platform.core.module.CoreCssClasses;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.core.module.CoreTestMarker;
import com.softicar.platform.core.module.page.PageUrlBuilder;
import com.softicar.platform.core.module.page.navigation.PageNavigationController;
import com.softicar.platform.core.module.page.navigation.entry.PageNavigationEntry;
import com.softicar.platform.core.module.page.navigation.entry.folder.PageNavigationFolderDiv;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.event.IDomClickEventHandler;
import com.softicar.platform.dom.event.IDomEvent;

public class PageNavigationLinkDiv extends DomDiv {

	private final PageNavigationController controller;
	private final PageNavigationFolderDiv folderDiv;
	private final PageNavigationEntry<?> entry;

	public PageNavigationLinkDiv(PageNavigationController controller, PageNavigationFolderDiv folderDiv, PageNavigationEntry<?> entry) {

		this.controller = controller;
		this.folderDiv = folderDiv;
		this.entry = entry;

		addMarker(CoreTestMarker.PAGE_NAVIGATION_LINK_DIV);
		setCssClass(CoreCssClasses.PAGE_NAVIGATION_LINK_DIV);
		appendChild(new Title());
		appendBadges();
		appendChild(new OpenInNewTabButton());
	}

	public PageNavigationFolderDiv getFolderDiv() {

		return folderDiv;
	}

	public PageNavigationEntry<?> getEntry() {

		return entry;
	}

	private void appendBadges() {

		entry//
			.getPageBadges()
			.stream()
			.map(PageNavigationBadgeDiv::new)
			.forEach(this::appendChild);
	}

	private class Title extends DomDiv implements IDomClickEventHandler {

		public Title() {

			appendText(entry.getTitle());
			setCssClass(CoreCssClasses.PAGE_NAVIGATION_LINK_TITLE);
		}

		@Override
		public void handleClick(IDomEvent event) {

			controller.openPage(entry);
		}
	}

	private class OpenInNewTabButton extends DomButton {

		public OpenInNewTabButton() {

			setIcon(CoreImages.PAGE_NAVIGATION_OPEN_IN_NEW_TAB_ICON.getResource());
			setTitle(CoreI18n.CLICK_TO_OPEN_IN_NEW_TAB);
			setTabIndex(-1);
			setExternalLink(new PageUrlBuilder<>(entry).build().getStartingFromPath());
		}
	}
}
