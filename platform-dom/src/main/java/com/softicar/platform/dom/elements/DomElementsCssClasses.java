package com.softicar.platform.dom.elements;

import com.softicar.platform.dom.style.CssClass;
import com.softicar.platform.dom.style.ICssClass;

// TODO rename to just DomCssClasses and move up
public interface DomElementsCssClasses {

	ICssClass DOM_ACTION_BAR = new CssClass("DomActionBar", DomElementsCssFiles.DOM_ACTION_BAR_STYLE);

	ICssClass DOM_AUTO_COMPLETE_INPUT = new CssClass("DomAutoCompleteInput", DomElementsCssFiles.DOM_AUTO_COMPLETE_STYLE);
	ICssClass DOM_AUTO_COMPLETE_INPUT_FILTER_DISPLAY = new CssClass("DomAutoCompleteInputFilterDisplay", DomElementsCssFiles.DOM_AUTO_COMPLETE_STYLE);

	ICssClass DOM_BAR = new CssClass("DomBar", DomElementsCssFiles.DOM_BAR_STYLE);

	ICssClass DOM_BUTTON = new CssClass("DomButton", DomElementsCssFiles.DOM_BUTTON_STYLE);
	ICssClass DOM_BUTTON_ICON = new CssClass("DomButtonIcon", DomElementsCssFiles.DOM_BUTTON_STYLE);
	ICssClass DOM_BUTTON_LABEL = new CssClass("DomButtonLabel", DomElementsCssFiles.DOM_BUTTON_STYLE);

	ICssClass DOM_CHECKBOX = new CssClass("DomCheckbox", DomElementsCssFiles.DOM_CHECKBOX_STYLE);
	ICssClass DOM_CHECKBOX_BOX = new CssClass("DomCheckboxBox", DomElementsCssFiles.DOM_CHECKBOX_STYLE);
	ICssClass DOM_CHECKBOX_BOX_FILLER = new CssClass("DomCheckboxBoxFiller", DomElementsCssFiles.DOM_CHECKBOX_STYLE);
	ICssClass DOM_CHECKBOX_LABEL = new CssClass("DomCheckboxLabel", DomElementsCssFiles.DOM_CHECKBOX_STYLE);
	ICssClass DOM_CHECKBOX_LIST = new CssClass("DomCheckboxList", DomElementsCssFiles.DOM_CHECKBOX_STYLE);
	ICssClass DOM_DATA_TABLE = new CssClass("DomDataTable", DomElementsCssFiles.DOM_DATA_TABLE_STYLE);
	ICssClass DOM_DAY_CHOOSER_DIV = new CssClass("DomDayChooserDiv", DomElementsCssFiles.DOM_DAY_CHOOSER_DIV_STYLE);
	ICssClass DOM_DAY_CHOOSER_DIV_WEEKEND = new CssClass("DomDayChooserDivWeekend", DomElementsCssFiles.DOM_DAY_CHOOSER_DIV_STYLE);
	ICssClass DOM_DAY_CHOOSER_DIV_WORKDAY = new CssClass("DomDayChooserDivWorkday", DomElementsCssFiles.DOM_DAY_CHOOSER_DIV_STYLE);
	ICssClass DOM_DAY_INPUT = new CssClass("DomDayInput", DomElementsCssFiles.DOM_DAY_INPUT_STYLE);
	ICssClass DOM_DAY_TIME_INPUT = new CssClass("DomDayTimeInput", DomElementsCssFiles.DOM_DAY_TIME_INPUT_STYLE);

	ICssClass DOM_ICON = new CssClass("DomIcon", DomElementsCssFiles.DOM_ICON_STYLE);
	ICssClass DOM_INPUT_DIAGNOSTICS_DISPLAY = new CssClass("DomInputDiagnosticsDisplay", DomElementsCssFiles.DOM_INPUT_DIAGNOSTICS_STYLE);
	ICssClass DOM_LABEL_GRID = new CssClass("DomLabelGrid", DomElementsCssFiles.DOM_LABEL_GRID_STYLE);

	ICssClass DOM_MESSAGE_DIV = new CssClass("DomMessageDiv", DomElementsCssFiles.DOM_MESSAGE_DIV_STYLE);

	ICssClass DOM_MODAL_DIALOG_POPUP = new CssClass("DomModalDialogPopup", DomElementsCssFiles.DOM_MODAL_DIALOG_STYLE);
	ICssClass DOM_MODAL_DIALOG_POPUP_CONTENT = new CssClass("DomModalDialogPopupContent", DomElementsCssFiles.DOM_MODAL_DIALOG_STYLE);
	ICssClass DOM_MODAL_DIALOG_POPUP_WRAPPED = new CssClass("DomModalDialogPopupWrapped", DomElementsCssFiles.DOM_MODAL_DIALOG_STYLE);
	ICssClass DOM_MODAL_DIALOG_BACKDROP = new CssClass("DomModalDialogBackdrop", DomElementsCssFiles.DOM_MODAL_DIALOG_STYLE);

	ICssClass DOM_PAGEABLE_TABLE_NAVIGATION = new CssClass("DomPageableTableNavigation", DomElementsCssFiles.DOM_PAGEABLE_TABLE_STYLE);
	ICssClass DOM_PAGEABLE_TABLE_NAVIGATION_GO_TO_PAGE_POPUP =
			new CssClass("DomPageableTableNavigationGoToPagePopup", DomElementsCssFiles.DOM_PAGEABLE_TABLE_STYLE);
	ICssClass DOM_PAGEABLE_TABLE_NAVIGATION_PAGE_LIST = new CssClass("DomPageableTableNavigationPageList", DomElementsCssFiles.DOM_PAGEABLE_TABLE_STYLE);

	ICssClass DOM_PERCENTAGE = new CssClass("DomPercentage", DomElementsCssFiles.DOM_PERCENTAGE_BAR_STYLE);
	ICssClass DOM_PERCENTAGE_BAR = new CssClass("DomPercentageBar", DomElementsCssFiles.DOM_PERCENTAGE_BAR_STYLE);
	ICssClass DOM_PERCENTAGE_MULTI_BAR = new CssClass("DomPercentageMultiBar", DomElementsCssFiles.DOM_PERCENTAGE_BAR_STYLE);
	ICssClass DOM_PERCENTAGE_LABEL = new CssClass("DomPercentageLabel", DomElementsCssFiles.DOM_PERCENTAGE_BAR_STYLE);

	ICssClass DOM_POPOVER = new CssClass("DomPopover", DomElementsCssFiles.DOM_POPOVER_STYLE);

	ICssClass DOM_POPUP = new CssClass("DomPopup", DomElementsCssFiles.DOM_POPUP_STYLE);
	ICssClass DOM_POPUP_CAPTION = new CssClass("DomPopupCaption", DomElementsCssFiles.DOM_POPUP_STYLE);
	ICssClass DOM_POPUP_FRAME = new CssClass("DomPopupFrame", DomElementsCssFiles.DOM_POPUP_STYLE);
	ICssClass DOM_POPUP_FRAME_HEADER = new CssClass("DomPopupFrameHeader", DomElementsCssFiles.DOM_POPUP_STYLE);
	ICssClass DOM_POPUP_SUB_CAPTION = new CssClass("DomPopupSubCaption", DomElementsCssFiles.DOM_POPUP_STYLE);

	ICssClass DOM_PREFORMATTED_LABEL = new CssClass("DomPreformattedLabel", DomElementsCssFiles.DOM_PREFORMATTED_LABEL_STYLE);

	ICssClass DOM_SEPARATOR_CELL = new CssClass("DomSeparatorCell", DomElementsCssFiles.DOM_SEPARATOR_CELL_STYLE);

	ICssClass DOM_TAB_BAR = new CssClass("DomTabBar", DomElementsCssFiles.DOM_TAB_BAR_STYLE);
	ICssClass DOM_TAB_BAR_CONTENT_CONTAINER = new CssClass("DomTabBarContentContainer", DomElementsCssFiles.DOM_TAB_BAR_STYLE);
	ICssClass DOM_TAB_BAR_HEADER = new CssClass("DomTabBarHeader", DomElementsCssFiles.DOM_TAB_BAR_STYLE);
	ICssClass DOM_TAB_BAR_HORIZONTAL = new CssClass("DomTabBarHorizontal", DomElementsCssFiles.DOM_TAB_BAR_STYLE);
	ICssClass DOM_TAB_BAR_VERTICAL = new CssClass("DomTabBarVertical", DomElementsCssFiles.DOM_TAB_BAR_STYLE);
	ICssClass DOM_TAB_HEADER = new CssClass("DomTabHeader", DomElementsCssFiles.DOM_TAB_BAR_STYLE);
	ICssClass DOM_TAB_HEADER_SEPARATOR = new CssClass("DomTabHeaderSeparator", DomElementsCssFiles.DOM_TAB_BAR_STYLE);

	ICssClass DOM_TIME_INPUT = new CssClass("DomTimeInput", DomElementsCssFiles.DOM_TIME_INPUT_STYLE);
	ICssClass DOM_TIME_INPUT_ELEMENT = new CssClass("DomTimeInputElement", DomElementsCssFiles.DOM_TIME_INPUT_STYLE);

	ICssClass DOM_VERTICAL_TEXT_BOX = new CssClass("DomVerticalTextBox", DomElementsCssFiles.DOM_VERTICAL_TEXT_BOX_STYLE);

	ICssClass DOM_WIKI_HEADLINE = new CssClass("DomWikiHeadline", DomElementsCssFiles.DOM_WIKI_ELEMENTS_STYLE);
	ICssClass DOM_WIKI_MONOSPACE = new CssClass("DomWikiMonospace", DomElementsCssFiles.DOM_WIKI_ELEMENTS_STYLE);
	ICssClass DOM_WIKI_RAW_TEXT = new CssClass("DomWikiRawText", DomElementsCssFiles.DOM_WIKI_ELEMENTS_STYLE);
	ICssClass DOM_WIKI_RAW_TEXT_CODE = new CssClass("DomWikiRawTextCode", DomElementsCssFiles.DOM_WIKI_ELEMENTS_STYLE);
	ICssClass DOM_WIKI_RAW_TEXT_CODE_BLOCK = new CssClass("DomWikiRawTextCodeBlock", DomElementsCssFiles.DOM_WIKI_ELEMENTS_STYLE);
	ICssClass DOM_WIKI_RAW_TEXT_INLINE = new CssClass("DomWikiRawTextInline", DomElementsCssFiles.DOM_WIKI_ELEMENTS_STYLE);
	ICssClass DOM_WIKI_SYNTAX_DIV = new CssClass("DomWikiSyntaxDiv", DomElementsCssFiles.DOM_WIKI_ELEMENTS_STYLE);
	ICssClass DOM_WIKI_UNDERLINE = new CssClass("DomWikiUnderline", DomElementsCssFiles.DOM_WIKI_ELEMENTS_STYLE);
}
