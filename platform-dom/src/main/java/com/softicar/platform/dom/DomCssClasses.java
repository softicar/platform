package com.softicar.platform.dom;

import com.softicar.platform.dom.style.CssClass;
import com.softicar.platform.dom.style.ICssClass;

public interface DomCssClasses {

	ICssClass DOM_ACTION_BAR = new CssClass("DomActionBar", DomCssFiles.DOM_ACTION_BAR_STYLE);

	ICssClass DOM_AUTO_COMPLETE_BACKDROP = new CssClass("DomAutoCompleteBackdrop", DomCssFiles.DOM_AUTO_COMPLETE_STYLE);
	ICssClass DOM_AUTO_COMPLETE_INDICATOR = new CssClass("DomAutoCompleteIndicator", DomCssFiles.DOM_AUTO_COMPLETE_STYLE);
	ICssClass DOM_AUTO_COMPLETE_INDICATOR_AMBIGUOUS = new CssClass("DomAutoCompleteIndicatorAmbiguous", DomCssFiles.DOM_AUTO_COMPLETE_STYLE);
	ICssClass DOM_AUTO_COMPLETE_INDICATOR_PARENT = new CssClass("DomAutoCompleteIndicatorParent", DomCssFiles.DOM_AUTO_COMPLETE_STYLE);
	ICssClass DOM_AUTO_COMPLETE_INDICATOR_ILLEGAL = new CssClass("DomAutoCompleteIndicatorIllegal", DomCssFiles.DOM_AUTO_COMPLETE_STYLE);
	ICssClass DOM_AUTO_COMPLETE_INPUT = new CssClass("DomAutoCompleteInput", DomCssFiles.DOM_AUTO_COMPLETE_STYLE);
	ICssClass DOM_AUTO_COMPLETE_INPUT_FIELD = new CssClass("DomAutoCompleteInputField", DomCssFiles.DOM_AUTO_COMPLETE_STYLE);
	ICssClass DOM_AUTO_COMPLETE_INPUT_FILTER_DISPLAY = new CssClass("DomAutoCompleteInputFilterDisplay", DomCssFiles.DOM_AUTO_COMPLETE_STYLE);
	ICssClass DOM_AUTO_COMPLETE_MATCH = new CssClass("DomAutoCompleteMatch", DomCssFiles.DOM_AUTO_COMPLETE_STYLE);
	ICssClass DOM_AUTO_COMPLETE_MORE_VALUES = new CssClass("DomAutoCompleteMoreValues", DomCssFiles.DOM_AUTO_COMPLETE_STYLE);
	ICssClass DOM_AUTO_COMPLETE_NO_VALUES = new CssClass("DomAutoCompleteNoValues", DomCssFiles.DOM_AUTO_COMPLETE_STYLE);
	ICssClass DOM_AUTO_COMPLETE_POPUP = new CssClass("DomAutoCompletePopup", DomCssFiles.DOM_AUTO_COMPLETE_STYLE);
	ICssClass DOM_AUTO_COMPLETE_SELECTED_VALUE = new CssClass("DomAutoCompleteSelectedValue", DomCssFiles.DOM_AUTO_COMPLETE_STYLE);
	ICssClass DOM_AUTO_COMPLETE_VALUE = new CssClass("DomAutoCompleteValue", DomCssFiles.DOM_AUTO_COMPLETE_STYLE);

	ICssClass DOM_BAR = new CssClass("DomBar", DomCssFiles.DOM_BAR_STYLE);

	ICssClass DOM_BUTTON = new CssClass("DomButton", DomCssFiles.DOM_BUTTON_STYLE);
	ICssClass DOM_BUTTON_ICON = new CssClass("DomButtonIcon", DomCssFiles.DOM_BUTTON_STYLE);
	ICssClass DOM_BUTTON_LABEL = new CssClass("DomButtonLabel", DomCssFiles.DOM_BUTTON_STYLE);

	ICssClass DOM_CHECKBOX = new CssClass("DomCheckbox", DomCssFiles.DOM_CHECKBOX_STYLE);
	ICssClass DOM_CHECKBOX_BOX = new CssClass("DomCheckboxBox", DomCssFiles.DOM_CHECKBOX_STYLE);
	ICssClass DOM_CHECKBOX_BOX_FILLER = new CssClass("DomCheckboxBoxFiller", DomCssFiles.DOM_CHECKBOX_STYLE);
	ICssClass DOM_CHECKBOX_LABEL = new CssClass("DomCheckboxLabel", DomCssFiles.DOM_CHECKBOX_STYLE);
	ICssClass DOM_CHECKBOX_LIST = new CssClass("DomCheckboxList", DomCssFiles.DOM_CHECKBOX_STYLE);
	ICssClass DOM_DATA_TABLE = new CssClass("DomDataTable", DomCssFiles.DOM_DATA_TABLE_STYLE);
	ICssClass DOM_DAY_CHOOSER_DIV = new CssClass("DomDayChooserDiv", DomCssFiles.DOM_DAY_CHOOSER_DIV_STYLE);
	ICssClass DOM_DAY_CHOOSER_DIV_WEEKEND = new CssClass("DomDayChooserDivWeekend", DomCssFiles.DOM_DAY_CHOOSER_DIV_STYLE);
	ICssClass DOM_DAY_CHOOSER_DIV_WORKDAY = new CssClass("DomDayChooserDivWorkday", DomCssFiles.DOM_DAY_CHOOSER_DIV_STYLE);
	ICssClass DOM_DAY_INPUT = new CssClass("DomDayInput", DomCssFiles.DOM_DAY_INPUT_STYLE);
	ICssClass DOM_DAY_TIME_INPUT = new CssClass("DomDayTimeInput", DomCssFiles.DOM_DAY_TIME_INPUT_STYLE);

	ICssClass DOM_ICON = new CssClass("DomIcon", DomCssFiles.DOM_ICON_STYLE);

	ICssClass DOM_IMAGE_VIEWER = new CssClass("DomImageViewer", DomCssFiles.DOM_IMAGE_VIEWER_STYLE);
	ICssClass DOM_IMAGE_VIEWER_CANVAS = new CssClass("DomImageViewerCanvas", DomCssFiles.DOM_IMAGE_VIEWER_STYLE);
	ICssClass DOM_IMAGE_VIEWER_IMAGE = new CssClass("DomImageViewerImage", DomCssFiles.DOM_IMAGE_VIEWER_STYLE);
	ICssClass DOM_IMAGE_VIEWER_IMAGE_HOLDER = new CssClass("DomImageViewerImageHolder", DomCssFiles.DOM_IMAGE_VIEWER_STYLE);
	ICssClass DOM_IMAGE_VIEWER_NAVIGATION_BAR = new CssClass("DomImageViewerNavigationBar", DomCssFiles.DOM_IMAGE_VIEWER_STYLE);
	ICssClass DOM_IMAGE_VIEWER_PAGE_INDICATOR = new CssClass("DomImageViewerPageIndicator", DomCssFiles.DOM_IMAGE_VIEWER_STYLE);
	ICssClass DOM_IMAGE_VIEWER_TAG = new CssClass("DomImageViewerTag", DomCssFiles.DOM_IMAGE_VIEWER_STYLE);
	ICssClass DOM_IMAGE_VIEWER_TAG_CANVAS = new CssClass("DomImageViewerImageTagCanvas", DomCssFiles.DOM_IMAGE_VIEWER_STYLE);
	ICssClass DOM_IMAGE_VIEWER_TAG_CAPTION = new CssClass("DomImageViewerTagCaption", DomCssFiles.DOM_IMAGE_VIEWER_STYLE);
	ICssClass DOM_IMAGE_VIEWER_TOOL_BAR = new CssClass("DomImageViewerToolBar", DomCssFiles.DOM_IMAGE_VIEWER_STYLE);
	ICssClass DOM_IMAGE_VIEWER_TOOL_BAR_SPACER = new CssClass("DomImageViewerToolBarSpacer", DomCssFiles.DOM_IMAGE_VIEWER_STYLE);

	ICssClass DOM_INPUT_DIAGNOSTICS_DISPLAY = new CssClass("DomInputDiagnosticsDisplay", DomCssFiles.DOM_INPUT_DIAGNOSTICS_STYLE);

	ICssClass DOM_JSON_ARRAY_DISPLAY = new CssClass("DomJsonArrayDisplay", DomCssFiles.DOM_JSON_DISPLAY_STYLE);
	ICssClass DOM_JSON_BOOLEAN_DISPLAY = new CssClass("DomJsonBooleanDisplay", DomCssFiles.DOM_JSON_DISPLAY_STYLE);
	ICssClass DOM_JSON_ERROR_DISPLAY = new CssClass("DomJsonErrorDisplay", DomCssFiles.DOM_JSON_DISPLAY_STYLE);
	ICssClass DOM_JSON_OBJECT_DISPLAY = new CssClass("DomJsonObjectDisplay", DomCssFiles.DOM_JSON_DISPLAY_STYLE);
	ICssClass DOM_JSON_NULL_DISPLAY = new CssClass("DomJsonNullDisplay", DomCssFiles.DOM_JSON_DISPLAY_STYLE);
	ICssClass DOM_JSON_NUMBER_DISPLAY = new CssClass("DomJsonNumberDisplay", DomCssFiles.DOM_JSON_DISPLAY_STYLE);
	ICssClass DOM_JSON_STRING_DISPLAY = new CssClass("DomJsonStringDisplay", DomCssFiles.DOM_JSON_DISPLAY_STYLE);

	ICssClass DOM_LABEL_GRID = new CssClass("DomLabelGrid", DomCssFiles.DOM_LABEL_GRID_STYLE);
	ICssClass DOM_LABEL_GRID_ROW = new CssClass("DomLabelGridRow", DomCssFiles.DOM_LABEL_GRID_STYLE);

	ICssClass DOM_MENU_POPOVER = new CssClass("DomMenuPopover", DomCssFiles.DOM_POPUP_STYLE);

	ICssClass DOM_MESSAGE_DIV = new CssClass("DomMessageDiv", DomCssFiles.DOM_MESSAGE_DIV_STYLE);

	ICssClass DOM_MODAL_DIALOG_POPUP = new CssClass("DomModalDialogPopup", DomCssFiles.DOM_MODAL_POPUP_STYLE);
	ICssClass DOM_MODAL_DIALOG_POPUP_CONTENT = new CssClass("DomModalDialogPopupContent", DomCssFiles.DOM_MODAL_POPUP_STYLE);

	ICssClass DOM_MODAL_POPUP_BACKDROP = new CssClass("DomModalPopupBackdrop", DomCssFiles.DOM_MODAL_POPUP_STYLE);

	ICssClass DOM_MULTILINE_STRING_DISPLAY = new CssClass("DomMultilineStringDisplay", DomCssFiles.DOM_MULTI_LINE_STRING_DISPLAY_STYLE);

	ICssClass DOM_PAGEABLE_TABLE_NAVIGATION = new CssClass("DomPageableTableNavigation", DomCssFiles.DOM_PAGEABLE_TABLE_STYLE);
	ICssClass DOM_PAGEABLE_TABLE_NAVIGATION_PAGE_LIST = new CssClass("DomPageableTableNavigationPageList", DomCssFiles.DOM_PAGEABLE_TABLE_STYLE);

	ICssClass DOM_PERCENTAGE = new CssClass("DomPercentage", DomCssFiles.DOM_PERCENTAGE_BAR_STYLE);
	ICssClass DOM_PERCENTAGE_BAR = new CssClass("DomPercentageBar", DomCssFiles.DOM_PERCENTAGE_BAR_STYLE);
	ICssClass DOM_PERCENTAGE_MULTI_BAR = new CssClass("DomPercentageMultiBar", DomCssFiles.DOM_PERCENTAGE_BAR_STYLE);
	ICssClass DOM_PERCENTAGE_LABEL = new CssClass("DomPercentageLabel", DomCssFiles.DOM_PERCENTAGE_BAR_STYLE);

	ICssClass DOM_POPUP = new CssClass("DomPopup", DomCssFiles.DOM_POPUP_STYLE);
	ICssClass DOM_POPUP_CAPTION = new CssClass("DomPopupCaption", DomCssFiles.DOM_POPUP_STYLE);
	ICssClass DOM_POPUP_FRAME = new CssClass("DomPopupFrame", DomCssFiles.DOM_POPUP_STYLE);
	ICssClass DOM_POPUP_FRAME_HEADER = new CssClass("DomPopupFrameHeader", DomCssFiles.DOM_POPUP_STYLE);
	ICssClass DOM_POPUP_SUB_CAPTION = new CssClass("DomPopupSubCaption", DomCssFiles.DOM_POPUP_STYLE);

	ICssClass DOM_PREFORMATTED_LABEL = new CssClass("DomPreformattedLabel", DomCssFiles.DOM_PREFORMATTED_LABEL_STYLE);

	ICssClass DOM_SEPARATOR_CELL = new CssClass("DomSeparatorCell", DomCssFiles.DOM_SEPARATOR_CELL_STYLE);

	ICssClass DOM_TAB_BAR = new CssClass("DomTabBar", DomCssFiles.DOM_TAB_BAR_STYLE);
	ICssClass DOM_TAB_BAR_CONTENT_CONTAINER = new CssClass("DomTabBarContentContainer", DomCssFiles.DOM_TAB_BAR_STYLE);
	ICssClass DOM_TAB_BAR_HEADER = new CssClass("DomTabBarHeader", DomCssFiles.DOM_TAB_BAR_STYLE);
	ICssClass DOM_TAB_BAR_HORIZONTAL = new CssClass("DomTabBarHorizontal", DomCssFiles.DOM_TAB_BAR_STYLE);
	ICssClass DOM_TAB_BAR_VERTICAL = new CssClass("DomTabBarVertical", DomCssFiles.DOM_TAB_BAR_STYLE);
	ICssClass DOM_TAB_HEADER = new CssClass("DomTabHeader", DomCssFiles.DOM_TAB_BAR_STYLE);
	ICssClass DOM_TAB_HEADER_SEPARATOR = new CssClass("DomTabHeaderSeparator", DomCssFiles.DOM_TAB_BAR_STYLE);

	ICssClass DOM_TIME_INPUT = new CssClass("DomTimeInput", DomCssFiles.DOM_TIME_INPUT_STYLE);
	ICssClass DOM_TIME_INPUT_ELEMENT = new CssClass("DomTimeInputElement", DomCssFiles.DOM_TIME_INPUT_STYLE);

	ICssClass DOM_UPLOAD_FORM = new CssClass("DomUploadForm", DomCssFiles.DOM_UPLOAD_STYLE);
	ICssClass DOM_VERTICAL_TEXT_BOX = new CssClass("DomVerticalTextBox", DomCssFiles.DOM_VERTICAL_TEXT_BOX_STYLE);

	ICssClass DOM_WIKI_HEADLINE = new CssClass("DomWikiHeadline", DomCssFiles.DOM_WIKI_ELEMENTS_STYLE);
	ICssClass DOM_WIKI_MONOSPACE = new CssClass("DomWikiMonospace", DomCssFiles.DOM_WIKI_ELEMENTS_STYLE);
	ICssClass DOM_WIKI_RAW_TEXT = new CssClass("DomWikiRawText", DomCssFiles.DOM_WIKI_ELEMENTS_STYLE);
	ICssClass DOM_WIKI_RAW_TEXT_CODE = new CssClass("DomWikiRawTextCode", DomCssFiles.DOM_WIKI_ELEMENTS_STYLE);
	ICssClass DOM_WIKI_RAW_TEXT_CODE_BLOCK = new CssClass("DomWikiRawTextCodeBlock", DomCssFiles.DOM_WIKI_ELEMENTS_STYLE);
	ICssClass DOM_WIKI_RAW_TEXT_INLINE = new CssClass("DomWikiRawTextInline", DomCssFiles.DOM_WIKI_ELEMENTS_STYLE);
	ICssClass DOM_WIKI_SYNTAX_DIV = new CssClass("DomWikiSyntaxDiv", DomCssFiles.DOM_WIKI_ELEMENTS_STYLE);
	ICssClass DOM_WIKI_UNDERLINE = new CssClass("DomWikiUnderline", DomCssFiles.DOM_WIKI_ELEMENTS_STYLE);
}
