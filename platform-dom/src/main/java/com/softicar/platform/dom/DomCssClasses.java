package com.softicar.platform.dom;

import com.softicar.platform.dom.elements.DomElementsCssClasses;
import com.softicar.platform.dom.style.CssClass;
import com.softicar.platform.dom.style.ICssClass;

public interface DomCssClasses extends DomElementsCssClasses {

	ICssClass DOM_IMAGE_VIEWER = new CssClass("DomImageViewer", DomCssFiles.DOM_IMAGE_VIEWER_STYLE);
	ICssClass DOM_IMAGE_VIEWER_CANVAS = new CssClass("DomImageViewerCanvas", DomCssFiles.DOM_IMAGE_VIEWER_STYLE);
	ICssClass DOM_IMAGE_VIEWER_IMAGE = new CssClass("DomImageViewerImage", DomCssFiles.DOM_IMAGE_VIEWER_STYLE);
	ICssClass DOM_IMAGE_VIEWER_IMAGE_HOLDER = new CssClass("DomImageViewerImageHolder", DomCssFiles.DOM_IMAGE_VIEWER_STYLE);
	ICssClass DOM_IMAGE_VIEWER_NAVIGATION_BAR = new CssClass("DomImageViewerNavigationBar", DomCssFiles.DOM_IMAGE_VIEWER_STYLE);
	ICssClass DOM_IMAGE_VIEWER_PAGE_INDICATOR = new CssClass("DomImageViewerPageIndicator", DomCssFiles.DOM_IMAGE_VIEWER_STYLE);
	ICssClass DOM_IMAGE_VIEWER_TOOL_BAR = new CssClass("DomImageViewerToolBar", DomCssFiles.DOM_IMAGE_VIEWER_STYLE);

	ICssClass DOM_JSON_ARRAY_DISPLAY = new CssClass("DomJsonArrayDisplay", DomCssFiles.DOM_JSON_DISPLAY_STYLE);
	ICssClass DOM_JSON_BOOLEAN_DISPLAY = new CssClass("DomJsonBooleanDisplay", DomCssFiles.DOM_JSON_DISPLAY_STYLE);
	ICssClass DOM_JSON_ERROR_DISPLAY = new CssClass("DomJsonErrorDisplay", DomCssFiles.DOM_JSON_DISPLAY_STYLE);
	ICssClass DOM_JSON_OBJECT_DISPLAY = new CssClass("DomJsonObjectDisplay", DomCssFiles.DOM_JSON_DISPLAY_STYLE);
	ICssClass DOM_JSON_NULL_DISPLAY = new CssClass("DomJsonNullDisplay", DomCssFiles.DOM_JSON_DISPLAY_STYLE);
	ICssClass DOM_JSON_NUMBER_DISPLAY = new CssClass("DomJsonNumberDisplay", DomCssFiles.DOM_JSON_DISPLAY_STYLE);
	ICssClass DOM_JSON_STRING_DISPLAY = new CssClass("DomJsonStringDisplay", DomCssFiles.DOM_JSON_DISPLAY_STYLE);
}
