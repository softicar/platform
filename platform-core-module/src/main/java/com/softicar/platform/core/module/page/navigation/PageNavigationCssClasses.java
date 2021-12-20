package com.softicar.platform.core.module.page.navigation;

import com.softicar.platform.dom.style.CssClass;
import com.softicar.platform.dom.style.ICssClass;

public interface PageNavigationCssClasses {

	ICssClass PAGE_NAVIGATION_COLLAPSED = new CssClass("PageNavigationCollapsed", PageNavigationResources.PAGE_NAVIGATION_STYLE);
	ICssClass PAGE_NAVIGATION_DIV = new CssClass("PageNavigationDiv", PageNavigationResources.PAGE_NAVIGATION_STYLE);
	ICssClass PAGE_NAVIGATION_FOLDER_DIV = new CssClass("PageNavigationFolderDiv", PageNavigationResources.PAGE_NAVIGATION_STYLE);
	ICssClass PAGE_NAVIGATION_FOLDER_CHEVRON = new CssClass("PageNavigationFolderChevron", PageNavigationResources.PAGE_NAVIGATION_STYLE);
	ICssClass PAGE_NAVIGATION_FOLDER_CONTENT_DIV = new CssClass("PageNavigationFolderContentDiv", PageNavigationResources.PAGE_NAVIGATION_STYLE);
	ICssClass PAGE_NAVIGATION_FOLDER_TITLE = new CssClass("PageNavigationFolderTitle", PageNavigationResources.PAGE_NAVIGATION_STYLE);
	ICssClass PAGE_NAVIGATION_FOLDER_TITLE_DIV = new CssClass("PageNavigationFolderTitleDiv", PageNavigationResources.PAGE_NAVIGATION_STYLE);
	ICssClass PAGE_NAVIGATION_LOGO_DIV = new CssClass("PageNavigationLogoDiv", PageNavigationResources.PAGE_NAVIGATION_STYLE);
	ICssClass PAGE_NAVIGATION_PAGE_LINK_DIV = new CssClass("PageNavigationPageLinkDiv", PageNavigationResources.PAGE_NAVIGATION_STYLE);
	ICssClass PAGE_NAVIGATION_PAGE_LINK_TITLE = new CssClass("PageNavigationPageLinkTitle", PageNavigationResources.PAGE_NAVIGATION_STYLE);
}
