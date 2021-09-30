package com.softicar.platform.core.module.page.navigation.link.display;

import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.core.module.page.navigation.PageNavigationCssClasses;
import com.softicar.platform.core.module.page.navigation.PageNavigationMarker;
import com.softicar.platform.core.module.page.navigation.link.PageNavigationLink;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomImage;
import com.softicar.platform.dom.event.IDomClickEventHandler;
import com.softicar.platform.dom.event.IDomEvent;

class PageNavigationFolderTitleDiv extends DomDiv implements IDomClickEventHandler {

	private final PageNavigationLink<?> link;
	private final INullaryVoidFunction clickCallback;

	public PageNavigationFolderTitleDiv(PageNavigationLink<?> link, INullaryVoidFunction clickCallback) {

		this.link = link;
		this.clickCallback = clickCallback;

		setMarker(PageNavigationMarker.FOLDER_TITLE_DIV);
		setCssClass(PageNavigationCssClasses.PAGE_NAVIGATION_FOLDER_TITLE_DIV);
		appendModuleIconIfPresent();
		appendChild(new TitleSpan());
		appendChild(new FolderChevron());
	}

	@Override
	public void handleClick(IDomEvent event) {

		clickCallback.apply();
	}

	private void appendModuleIconIfPresent() {

		link//
			.getIcon()
			.map(DomImage::new)
			.ifPresent(this::appendChild);
	}

	private class TitleSpan extends DomDiv {

		public TitleSpan() {

			appendText(link.getTitle());
		}
	}

	private class FolderChevron extends DomDiv {

		public FolderChevron() {

			setCssClass(PageNavigationCssClasses.PAGE_NAVIGATION_FOLDER_CHEVRON);
			appendText(">");
		}
	}
}
