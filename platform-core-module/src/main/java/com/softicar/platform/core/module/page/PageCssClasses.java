package com.softicar.platform.core.module.page;

import com.softicar.platform.dom.style.CssClass;
import com.softicar.platform.dom.style.ICssClass;

public interface PageCssClasses {

	ICssClass PAGE_DIV = new CssClass("PageDiv", PageResources.PAGE_STYLE);
	ICssClass PAGE_CONTENT_DIV = new CssClass("PageContentDiv", PageResources.PAGE_STYLE);
	ICssClass PAGE_HEADER_AND_CONTENT_DIV = new CssClass("PageHeaderAndContentDiv", PageResources.PAGE_STYLE);
	ICssClass PAGE_HEADER_DIV = new CssClass("PageHeaderDiv", PageResources.PAGE_STYLE);
	ICssClass PAGE_HEADER_PATH_DIV = new CssClass("PageHeaderPathDiv", PageResources.PAGE_STYLE);
	ICssClass PAGE_HEADER_PAGE_NAME = new CssClass("PageHeaderPageName", PageResources.PAGE_STYLE);
	ICssClass PAGE_HEADER_SEPARATOR = new CssClass("PageHeaderSeparator", PageResources.PAGE_STYLE);
	ICssClass PAGE_HEADER_USER_DISPLAY = new CssClass("PageHeaderUserDisplay", PageResources.PAGE_STYLE);
	ICssClass PAGE_SERVICE_LOGIN_DIV = new CssClass("PageServiceLoginDiv", PageResources.PAGE_STYLE);
	ICssClass PAGE_SERVICE_LOGIN_ERROR_DIV = new CssClass("PageServiceLoginErrorDiv", PageResources.PAGE_STYLE);
	ICssClass TEST_SYSTEM = new CssClass("TestSystem", PageResources.PAGE_STYLE);
}
