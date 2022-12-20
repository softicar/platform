package com.softicar.platform.workflow.module;

import com.softicar.platform.dom.style.CssClass;
import com.softicar.platform.dom.style.ICssClass;

public interface WorkflowCssClasses {

	ICssClass WORKFLOW_MANAGEMENT_DIV = new CssClass("WorkflowManagementDiv", WorkflowCssFiles.WORKFLOW_DISPLAY_ELEMENTS_STYLE);
	ICssClass WORKFLOW_DISPLAY_ELEMENT = new CssClass("WorkflowDisplayElement", WorkflowCssFiles.WORKFLOW_DISPLAY_ELEMENTS_STYLE);
	ICssClass WORKFLOW_DISPLAY_LINE = new CssClass("WorkflowDisplayLine", WorkflowCssFiles.WORKFLOW_DISPLAY_ELEMENTS_STYLE);
	ICssClass WORKFLOW_END_CIRCLE_INNER_CIRCLE = new CssClass("WorkflowEndCircleInnerCircle", WorkflowCssFiles.WORKFLOW_DISPLAY_ELEMENTS_STYLE);
	ICssClass WORKFLOW_CIRCLE_DISPLAY_ELEMENT = new CssClass("WorkflowCircleDisplayElement", WorkflowCssFiles.WORKFLOW_DISPLAY_ELEMENTS_STYLE);
	ICssClass WORKFLOW_NODE = new CssClass("WorkflowNode", WorkflowCssFiles.WORKFLOW_DISPLAY_ELEMENTS_STYLE);
	ICssClass WORKFLOW_NODE_SEPARATOR = new CssClass("WorkflowNodeSeparator", WorkflowCssFiles.WORKFLOW_DISPLAY_ELEMENTS_STYLE);
	ICssClass WORKFLOW_NODE_NAME = new CssClass("WorkflowNodeName", WorkflowCssFiles.WORKFLOW_DISPLAY_ELEMENTS_STYLE);
	ICssClass WORKFLOW_DISPLAY_LINE_PIXEL = new CssClass("WorkflowDisplayLinePixel", WorkflowCssFiles.WORKFLOW_DISPLAY_ELEMENTS_STYLE);
}
