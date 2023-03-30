package com.softicar.platform.workflow.module;

import com.softicar.platform.dom.style.CssClass;
import com.softicar.platform.dom.style.ICssClass;

public interface WorkflowCssClasses {

	ICssClass WORKFLOW_CIRCLE_DISPLAY_ELEMENT = new CssClass("WorkflowCircleDisplayElement", WorkflowCssFiles.WORKFLOW_DISPLAY_ELEMENTS_STYLE);
	ICssClass WORKFLOW_DISPLAY_ELEMENT = new CssClass("WorkflowDisplayElement", WorkflowCssFiles.WORKFLOW_DISPLAY_ELEMENTS_STYLE);
	ICssClass WORKFLOW_DISPLAY_LINE = new CssClass("WorkflowDisplayLine", WorkflowCssFiles.WORKFLOW_DISPLAY_ELEMENTS_STYLE);
	ICssClass WORKFLOW_DISPLAY_LINE_PIXEL = new CssClass("WorkflowDisplayLinePixel", WorkflowCssFiles.WORKFLOW_DISPLAY_ELEMENTS_STYLE);
	ICssClass WORKFLOW_END_CIRCLE_INNER_CIRCLE = new CssClass("WorkflowEndCircleInnerCircle", WorkflowCssFiles.WORKFLOW_DISPLAY_ELEMENTS_STYLE);

	ICssClass WORKFLOW_ITEM_COUNT_IN_INAVTICE_VERSIONS_CELL =
			new CssClass("WorkflowItemCountInInavticeVersionsCell", WorkflowCssFiles.WORKFLOW_ITEM_HISTORY_STYLE);
	ICssClass WORKFLOW_ITEM_HISTORY_DIV = new CssClass("WorkflowItemHistoryDiv", WorkflowCssFiles.WORKFLOW_ITEM_HISTORY_STYLE);
	ICssClass WORKFLOW_ITEM_HISTORY_ENTRY_DATE = new CssClass("WorkflowItemHistoryEntryDate", WorkflowCssFiles.WORKFLOW_ITEM_HISTORY_STYLE);
	ICssClass WORKFLOW_ITEM_HISTORY_ENTRY_DIV = new CssClass("WorkflowItemHistoryEntryDiv", WorkflowCssFiles.WORKFLOW_ITEM_HISTORY_STYLE);
	ICssClass WORKFLOW_ITEM_HISTORY_ENTRY_TEXT = new CssClass("WorkflowItemHistoryEntryText", WorkflowCssFiles.WORKFLOW_ITEM_HISTORY_STYLE);
	ICssClass WORKFLOW_ITEM_HISTORY_ENTRY_USER = new CssClass("WorkflowItemHistoryEntryUser", WorkflowCssFiles.WORKFLOW_ITEM_HISTORY_STYLE);
	ICssClass WORKFLOW_ITEM_HISTORY_SHOW_MORE_DIV = new CssClass("WorkflowItemHistoryShowMoreDiv", WorkflowCssFiles.WORKFLOW_ITEM_HISTORY_STYLE);
	ICssClass WORKFLOW_ITEM_HISTORY_WORKFLOW_NODE_DIV = new CssClass("WorkflowItemHistoryWorkflowNodeDiv", WorkflowCssFiles.WORKFLOW_ITEM_HISTORY_STYLE);
	ICssClass WORKFLOW_ITEM_HISTORY_WORKFLOW_NODE_LABEL = new CssClass("WorkflowItemHistoryWorkflowNodeLabel", WorkflowCssFiles.WORKFLOW_ITEM_HISTORY_STYLE);

	ICssClass WORKFLOW_MANAGEMENT_DIV = new CssClass("WorkflowManagementDiv", WorkflowCssFiles.WORKFLOW_DISPLAY_ELEMENTS_STYLE);
	ICssClass WORKFLOW_NODE = new CssClass("WorkflowNode", WorkflowCssFiles.WORKFLOW_DISPLAY_ELEMENTS_STYLE);
	ICssClass WORKFLOW_NODE_SEPARATOR = new CssClass("WorkflowNodeSeparator", WorkflowCssFiles.WORKFLOW_DISPLAY_ELEMENTS_STYLE);
	ICssClass WORKFLOW_NODE_NAME = new CssClass("WorkflowNodeName", WorkflowCssFiles.WORKFLOW_DISPLAY_ELEMENTS_STYLE);
	ICssClass WORKFLOW_VERSION_ROW = new CssClass("WorkflowVersionRow", WorkflowCssFiles.WORKFLOW_DISPLAY_ELEMENTS_STYLE);
}
