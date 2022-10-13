package com.softicar.platform.core.module.page.navigation.entry.folder;

import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.core.module.CoreCssClasses;
import com.softicar.platform.core.module.CoreTestMarker;
import com.softicar.platform.core.module.page.navigation.entry.PageNavigationEntry;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomImage;
import com.softicar.platform.dom.event.IDomClickEventHandler;
import com.softicar.platform.dom.event.IDomEvent;

class PageNavigationFolderTitleDiv extends DomDiv implements IDomClickEventHandler {

	private final PageNavigationEntry<?> folderEntry;
	private final INullaryVoidFunction clickCallback;

	public PageNavigationFolderTitleDiv(PageNavigationEntry<?> folderEntry, INullaryVoidFunction clickCallback) {

		this.folderEntry = folderEntry;
		this.clickCallback = clickCallback;

		addMarker(CoreTestMarker.PAGE_NAVIGATION_FOLDER_TITLE_DIV);
		setCssClass(CoreCssClasses.PAGE_NAVIGATION_FOLDER_TITLE_DIV);
		appendModuleIconIfPresent();
		appendChild(new TitleSpan());
		appendChild(new FolderChevron());
	}

	@Override
	public void handleClick(IDomEvent event) {

		clickCallback.apply();
	}

	private void appendModuleIconIfPresent() {

		folderEntry//
			.getIcon()
			.map(DomImage::new)
			.ifPresent(this::appendChild);
	}

	private class TitleSpan extends DomDiv {

		public TitleSpan() {

			appendText(folderEntry.getTitle());
		}
	}

	private class FolderChevron extends DomDiv {

		public FolderChevron() {

			setCssClass(CoreCssClasses.PAGE_NAVIGATION_FOLDER_CHEVRON);
			appendText(">");
		}
	}
}
