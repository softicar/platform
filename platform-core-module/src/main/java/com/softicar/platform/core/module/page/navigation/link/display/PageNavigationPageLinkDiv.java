package com.softicar.platform.core.module.page.navigation.link.display;

import com.softicar.platform.core.module.CoreCssClasses;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.core.module.CoreTestMarker;
import com.softicar.platform.core.module.page.PageUrlBuilder;
import com.softicar.platform.core.module.page.navigation.PageNavigationPageController;
import com.softicar.platform.core.module.page.navigation.link.PageNavigationLink;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.event.IDomClickEventHandler;
import com.softicar.platform.dom.event.IDomEvent;

public class PageNavigationPageLinkDiv extends DomDiv {

	private final PageNavigationPageController pageController;
	private final PageNavigationFolderDiv folderDiv;
	private final PageNavigationLink<?> link;

	public PageNavigationPageLinkDiv(PageNavigationPageController pageController, PageNavigationFolderDiv folderDiv, PageNavigationLink<?> link) {

		this.pageController = pageController;
		this.folderDiv = folderDiv;
		this.link = link;

		addMarker(CoreTestMarker.PAGE_NAVIGATION_PAGE_LINK_DIV);
		setCssClass(CoreCssClasses.PAGE_NAVIGATION_PAGE_LINK_DIV);
		appendChild(new Title());
		appendChild(new OpenInNewTabButton());
	}

	public PageNavigationFolderDiv getFolderDiv() {

		return folderDiv;
	}

	public PageNavigationLink<?> getLink() {

		return link;
	}

	private void showPage() {

		pageController.showPage(this);
	}

	private class Title extends DomDiv implements IDomClickEventHandler {

		public Title() {

			appendText(link.getTitle());
			setCssClass(CoreCssClasses.PAGE_NAVIGATION_PAGE_LINK_TITLE);
		}

		@Override
		public void handleClick(IDomEvent event) {

			showPage();
		}
	}

	private class OpenInNewTabButton extends DomButton {

		public OpenInNewTabButton() {

			setIcon(CoreImages.PAGE_NAVIGATION_OPEN_IN_NEW_TAB_ICON.getResource());
			setTitle(CoreI18n.CLICK_TO_OPEN_IN_NEW_TAB);
			setTabIndex(-1);
			setExternalLink(new PageUrlBuilder<>(link).build().getStartingFromPath());
		}
	}
}
