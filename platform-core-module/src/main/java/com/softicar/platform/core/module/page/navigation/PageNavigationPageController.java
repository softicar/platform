package com.softicar.platform.core.module.page.navigation;

import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.page.PageHeaderAndContentDiv;
import com.softicar.platform.core.module.page.navigation.link.PageNavigationLink;
import com.softicar.platform.core.module.page.navigation.link.display.PageNavigationFolderDiv;
import com.softicar.platform.core.module.page.navigation.link.display.PageNavigationPageLinkDiv;
import com.softicar.platform.core.module.page.navigation.link.display.PageNavigationPageLinkDivMap;
import com.softicar.platform.core.module.start.page.StartPage;
import com.softicar.platform.dom.DomCssPseudoClasses;
import com.softicar.platform.dom.elements.dialog.DomModalAlertDialog;
import com.softicar.platform.emf.page.IEmfPage;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePoints;
import java.util.Optional;

public class PageNavigationPageController {

	private final PageNavigationFolderController folderController;
	private final PageHeaderAndContentDiv pageHeaderAndContentDiv;
	private final PageNavigationPageLinkDivMap pageLinkDivMap;
	private PageNavigationPageLinkDiv currentPageLinkDiv;

	public PageNavigationPageController(PageNavigationFolderController folderController, PageHeaderAndContentDiv pageHeaderAndContentDiv) {

		this.folderController = folderController;
		this.pageHeaderAndContentDiv = pageHeaderAndContentDiv;
		this.pageLinkDivMap = new PageNavigationPageLinkDivMap();
		this.currentPageLinkDiv = null;
	}

	public PageNavigationPageLinkDiv createPageLinkDiv(PageNavigationFolderDiv folderDiv, PageNavigationLink<?> pageLink) {

		PageNavigationPageLinkDiv pageLinkDiv = new PageNavigationPageLinkDiv(this, folderDiv, pageLink);
		pageLinkDivMap.add(pageLinkDiv);
		return pageLinkDiv;
	}

	public void showPage(IEmfPage<?> page, Integer moduleInstanceId) {

		pageLinkDivMap//
			.getLinkDiv(page, moduleInstanceId)
			.ifPresentOrElse(this::showPage, this::showStartPage);
	}

	private void showStartPage() {

		showPage(EmfSourceCodeReferencePoints.getReferencePoint(StartPage.class), AGCoreModuleInstance.getInstance().getId());
		new DomModalAlertDialog(CoreI18n.PAGE_NOT_ACCESSIBLE.concatSentence(CoreI18n.RETURNING_TO_START_PAGE)).open();
	}

	public void showPage(PageNavigationPageLinkDiv pageLinkDiv) {

		unselectCurrentPageLinkDiv();
		selectPageLinkDiv(pageLinkDiv);

		folderController.openFolder(pageLinkDiv.getFolderDiv());
		pageHeaderAndContentDiv.setContent(pageLinkDiv.getLink());
	}

	private void unselectCurrentPageLinkDiv() {

		Optional//
			.ofNullable(currentPageLinkDiv)
			.ifPresent(display -> display.removeCssClass(DomCssPseudoClasses.SELECTED));
		this.currentPageLinkDiv = null;
	}

	private void selectPageLinkDiv(PageNavigationPageLinkDiv pageLinkDiv) {

		pageLinkDiv.addCssClass(DomCssPseudoClasses.SELECTED);
		this.currentPageLinkDiv = pageLinkDiv;
	}
}
