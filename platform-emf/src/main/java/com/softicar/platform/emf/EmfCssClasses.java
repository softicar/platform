package com.softicar.platform.emf;

import com.softicar.platform.dom.style.CssClass;
import com.softicar.platform.dom.style.ICssClass;

public interface EmfCssClasses {

	ICssClass OPEN = new CssClass("open");
	ICssClass INVISIBLE = new CssClass("invisible");

	ICssClass EMF_ATTRIBUTE_VALUE_HELP_INDICATOR = new CssClass("EmfAttributeValueHelpIndicator", EmfCssFiles.EMF_ATTRIBUTE_DISPLAY_STYLE);
	ICssClass EMF_ATTRIBUTE_VALUE_HELP_TEXT_ELEMENT = new CssClass("EmfAttributeValueHelpTextElement", EmfCssFiles.EMF_ATTRIBUTE_DISPLAY_STYLE);
	ICssClass EMF_ATTRIBUTE_VALUE_MANDATORY_INDICATOR = new CssClass("EmfAttributeValueMandatoryIndicator", EmfCssFiles.EMF_ATTRIBUTE_DISPLAY_STYLE);
	ICssClass EMF_ATTRIBUTE_VALUE_TITLE = new CssClass("EmfAttributeValueTitle", EmfCssFiles.EMF_ATTRIBUTE_DISPLAY_STYLE);
	ICssClass EMF_BOOLEAN_DISPLAY = new CssClass("EmfBooleanDisplay", EmfCssFiles.EMF_ATTRIBUTE_DISPLAY_STYLE);
	ICssClass EMF_BOOLEAN_DISPLAY_TRUE = new CssClass("EmfBooleanDisplayTrue", EmfCssFiles.EMF_ATTRIBUTE_DISPLAY_STYLE);
	ICssClass EMF_BOOLEAN_DISPLAY_FALSE = new CssClass("EmfBooleanDisplayFalse", EmfCssFiles.EMF_ATTRIBUTE_DISPLAY_STYLE);

	ICssClass EMF_DATA_TABLE = new CssClass("EmfDataTable", EmfCssFiles.EMF_DATA_TABLE_STYLE);
	ICssClass EMF_DATA_TABLE_DIV = new CssClass("EmfDataTableDiv", EmfCssFiles.EMF_DATA_TABLE_STYLE);
	ICssClass EMF_DATA_TABLE_EMPTY_TABLE_PLACEHOLDER_DIV = new CssClass("EmfDataTableEmptyTablePlaceholderDiv", EmfCssFiles.EMF_DATA_TABLE_STYLE);
	ICssClass EMF_DATA_TABLE_FILTER_LIST_DIV = new CssClass("EmfDataTableFilterListDiv", EmfCssFiles.EMF_DATA_TABLE_STYLE);
	ICssClass EMF_DATA_TABLE_FILTER_LIST_ELEMENT_DIV = new CssClass("EmfDataTableFilterListElementDiv", EmfCssFiles.EMF_DATA_TABLE_STYLE);
	ICssClass EMF_DATA_TABLE_HEADER_CELL_DIV = new CssClass("EmfDataTableHeaderCellDiv", EmfCssFiles.EMF_DATA_TABLE_STYLE);
	ICssClass EMF_DATA_TABLE_ORDER_BY_BUTTON_COUNT_ELEMENT = new CssClass("EmfDataTableOrderByButtonCountElement", EmfCssFiles.EMF_DATA_TABLE_STYLE);
	ICssClass EMF_DATA_TABLE_ROW_SELECTION_CHECKBOX = new CssClass("EmfDataTableRowSelectionCheckbox", EmfCssFiles.EMF_DATA_TABLE_STYLE);
	ICssClass EMF_DATA_TABLE_ROW_SELECTION_CONTROL_ELEMENT = new CssClass("EmfDataTableRowSelectionControlElement", EmfCssFiles.EMF_DATA_TABLE_STYLE);
	ICssClass EMF_DATA_TABLE_VERTICAL_HEADER_CELL = new CssClass("EmfDataTableVerticalHeaderCell", EmfCssFiles.EMF_DATA_TABLE_STYLE);
	ICssClass EMF_DATA_TABLE_VERTICAL_HEADER_CELL_DIV = new CssClass("EmfDataTableVerticalHeaderCellDiv", EmfCssFiles.EMF_DATA_TABLE_STYLE);

	ICssClass EMF_DATA_TABLE_CONFIGURATION_POPUP = new CssClass("EmfDataTableConfigurationPopup", EmfCssFiles.EMF_DATA_TABLE_CONFIGURATION_STYLE);
	ICssClass EMF_DATA_TABLE_CONFIGURATION_COLUMN_ORDER_INPUT =
			new CssClass("EmfDataTableConfigurationColumnOrderInput", EmfCssFiles.EMF_DATA_TABLE_CONFIGURATION_STYLE);
	ICssClass EMF_DATA_TABLE_CONFIGURATION_COLUMN_POSITION_INPUT =
			new CssClass("EmfDataTableConfigurationColumnPositionInput", EmfCssFiles.EMF_DATA_TABLE_CONFIGURATION_STYLE);
	ICssClass EMF_DATA_TABLE_CONFIGURATION_PAGE_SIZE_INPUT =
			new CssClass("EmfDataTableConfigurationPageSizeInput", EmfCssFiles.EMF_DATA_TABLE_CONFIGURATION_STYLE);

	ICssClass EMF_ENTITY_INPUT_BROWSE_POPOVER = new CssClass("EmfEntityInputBrowsePopover", EmfCssFiles.EMF_INPUT_STYLE);
	ICssClass EMF_ENTITY_INPUT_BROWSE_POPOVER_CELL_CONTENT = new CssClass("EmfEntityInputBrowsePopoverCellContent", EmfCssFiles.EMF_INPUT_STYLE);

	ICssClass EMF_EXTERNAL_PAGE_FRAME = new CssClass("EmfExternalPageFrame", EmfCssFiles.EMF_EXTERNAL_PAGE_STYLE);
	ICssClass EMF_EXTERNAL_PAGE_FRAME_CONTAINER = new CssClass("EmfExternalPageFrameContainer", EmfCssFiles.EMF_EXTERNAL_PAGE_STYLE);

	ICssClass EMF_ADJACENT_NODE_FORM = new CssClass("EmfAdjacentNodeForm", EmfCssFiles.EMF_FORM_STYLE);
	ICssClass EMF_FORM = new CssClass("EmfForm", EmfCssFiles.EMF_FORM_STYLE);
	ICssClass EMF_FORM_COMMON_ACTIONS_DIV = new CssClass("EmfFormCommonActionsDiv", EmfCssFiles.EMF_FORM_STYLE);
	ICssClass EMF_FORM_DELEGATOR = new CssClass("EmfFormDelegator", EmfCssFiles.EMF_FORM_STYLE);
	ICssClass EMF_FORM_FRAME = new CssClass("EmfFormFrame", EmfCssFiles.EMF_FORM_STYLE);
	ICssClass EMF_FORM_FRAME_CAPTION = new CssClass("EmfFormFrameCaption", EmfCssFiles.EMF_FORM_STYLE);
	ICssClass EMF_FORM_FRAME_HEADER = new CssClass("EmfFormFrameHeader", EmfCssFiles.EMF_FORM_STYLE);
	ICssClass EMF_FORM_FRAME_SUBCAPTION = new CssClass("EmfFormFrameSubCaption", EmfCssFiles.EMF_FORM_STYLE);
	ICssClass EMF_FORM_INDICATOR = new CssClass("EmfFormIndicator", EmfCssFiles.EMF_FORM_STYLE);
	ICssClass EMF_FORM_INDICATOR_CONTAINER = new CssClass("EmfFormIndicatorContainer", EmfCssFiles.EMF_FORM_STYLE);
	ICssClass EMF_FORM_INDICATOR_IMAGE = new CssClass("EmfFormIndicatorImage", EmfCssFiles.EMF_FORM_STYLE);
	ICssClass EMF_FORM_INDICATOR_ROW = new CssClass("EmfFormIndicatorRow", EmfCssFiles.EMF_FORM_STYLE);
	ICssClass EMF_FORM_PRIMARY_ACTIONS_SECTION_DIV = new CssClass("EmfFormPrimaryActionSectionDiv", EmfCssFiles.EMF_FORM_STYLE);
	ICssClass EMF_FORM_SAVE_OR_CANCEL_ACTIONS_INPUT = new CssClass("EmfFormSaveOrCancelActionsInput", EmfCssFiles.EMF_FORM_STYLE);
	ICssClass EMF_FORM_SECTION_CONTENT_DIV = new CssClass("EmfFormSectionContentDiv", EmfCssFiles.EMF_FORM_SECTION_STYLE);
	ICssClass EMF_FORM_SECTION_DIV = new CssClass("EmfFormSectionDiv", EmfCssFiles.EMF_FORM_SECTION_STYLE);
	ICssClass EMF_FORM_SECTION_HEADER_CONTENT = new CssClass("EmfFormSectionHeaderContent", EmfCssFiles.EMF_FORM_SECTION_STYLE);
	ICssClass EMF_FORM_SECTION_HEADER_DIV = new CssClass("EmfFormSectionHeaderDiv", EmfCssFiles.EMF_FORM_SECTION_STYLE);
	ICssClass EMF_FORM_SECTION_HEADER_ICON = new CssClass("EmfFormSectionHeaderIcon", EmfCssFiles.EMF_FORM_SECTION_STYLE);
	ICssClass EMF_FORM_SECTION_HEADER_OPEN_INDICATOR = new CssClass("EmfFormSectionHeaderOpenIndicator", EmfCssFiles.EMF_FORM_SECTION_STYLE);
	ICssClass EMF_FORM_SECTION_HEADER_TITLE = new CssClass("EmfFormSectionHeaderTitle", EmfCssFiles.EMF_FORM_SECTION_STYLE);
	ICssClass EMF_FORM_SECTION_NO_CONTENT = new CssClass("EmfFormSectionNoContent", EmfCssFiles.EMF_FORM_SECTION_STYLE);
	ICssClass EMF_FORM_UPPER_BODY_PART = new CssClass("EmfFormBodyUpperPart", EmfCssFiles.EMF_FORM_STYLE);
	ICssClass EMF_FORM_WIZARD_ACTION_HEADER_DISABLED = new CssClass("EmfFormWizardActionHeaderDisabled", EmfCssFiles.EMF_FORM_STYLE);
	ICssClass EMF_FORM_WIZARD_ACTION_TITLE_MODIFIER = new CssClass("EmfFormWizardActionTitleModifier", EmfCssFiles.EMF_FORM_STYLE);

	ICssClass EMF_ICON_COLUMN_HANDLER = new CssClass("EmfIconColumnHandler", EmfCssFiles.EMF_COLUMN_HANDLER_STYLE);
	ICssClass EMF_SCOPE_FIELD_COLUMN_HANDLER = new CssClass("EmfScopeFieldColumnHandler", EmfCssFiles.EMF_COLUMN_HANDLER_STYLE);
	ICssClass EMF_CHILD_TABLES_COLUMN_HANDLER = new CssClass("EmfChildTablesColumnHandler", EmfCssFiles.EMF_COLUMN_HANDLER_STYLE);
	ICssClass EMF_DISPLAY_STRING_TO_TEXT_AREA_COLUMN_HANDLER = new CssClass("EmfDisplayStringToTextAreaColumnHandler", EmfCssFiles.EMF_COLUMN_HANDLER_STYLE);
	ICssClass EMF_PREDICATE_COLUMN_HANDLER = new CssClass("EmfPredicateColumnHandler", EmfCssFiles.EMF_COLUMN_HANDLER_STYLE);

	ICssClass EMF_PASSWORD_INPUT = new CssClass("EmfPasswordInput", EmfCssFiles.EMF_INPUT_STYLE);
	ICssClass EMF_PASSWORD_VISIBILITY_BUTTON = new CssClass("EmfPasswordVisibilityButton", EmfCssFiles.EMF_INPUT_STYLE);

	ICssClass EMF_LOG_FEED = new CssClass("EmfLogFeed", EmfCssFiles.EMF_LOG_FEED_STYLE);
	ICssClass EMF_LOG_FEED_GRID = new CssClass("EmfLogFeedGrid", EmfCssFiles.EMF_LOG_FEED_STYLE);
	ICssClass EMF_LOG_FEED_GRID_LABEL = new CssClass("EmfLogFeedGridLabel", EmfCssFiles.EMF_LOG_FEED_STYLE);
	ICssClass EMF_LOG_FEED_GRID_VALUE = new CssClass("EmfLogFeedGridValue", EmfCssFiles.EMF_LOG_FEED_STYLE);
	ICssClass EMF_LOG_FEED_TRANSACTION_LEGEND = new CssClass("EmfLogFeedTransactionLegend", EmfCssFiles.EMF_LOG_FEED_STYLE);

	ICssClass EMF_MANAGEMENT_ACTIONS_BUTTON = new CssClass("EmfManagementActionsButton", EmfCssFiles.EMF_MANAGEMENT_STYLE);
	ICssClass EMF_MANAGEMENT_DIV = new CssClass("EmfManagementDiv", EmfCssFiles.EMF_MANAGEMENT_STYLE);

	ICssClass EMF_PREFILTER_ELEMENT = new CssClass("EmfPrefilterElement", EmfCssFiles.EMF_PREFILTER_ROW_STYLE);
	ICssClass EMF_PREFILTER_ROW_CONTENT = new CssClass("EmfPrefilterRowContent", EmfCssFiles.EMF_PREFILTER_ROW_STYLE);
	ICssClass EMF_PREFILTER_ADD_FILTER_BAR = new CssClass("EmfPrefilterAddFilterBar", EmfCssFiles.EMF_PREFILTER_ROW_STYLE);
	ICssClass EMF_PREFILTER_FILTER_TYPE_BUTTON = new CssClass("EmfPrefilterFilterTypeButton", EmfCssFiles.EMF_PREFILTER_ROW_STYLE);

	ICssClass EMF_STRING_DISPLAY = new CssClass("EmfStringDisplay", EmfCssFiles.EMF_DISPLAY_STYLE);
	ICssClass EMF_TABLE_ROW_DISPLAY = new CssClass("EmfTableRowDisplay", EmfCssFiles.EMF_DISPLAY_STYLE);

	ICssClass EMF_WIKI_TEXT_INPUT = new CssClass("EmfWikiTextInput", EmfCssFiles.EMF_INPUT_STYLE);
	ICssClass EMF_WIKI_TEXT_INPUT_PREVIEW = new CssClass("EmfWikiTextInputPreview", EmfCssFiles.EMF_INPUT_STYLE);
}
