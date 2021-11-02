package com.softicar.platform.workflow.module.workflow.management.display.element;

import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.workflow.module.WorkflowCssClasses;

public abstract class AbstractDisplayElement extends DomDiv {

	public static final int BORDER = 1;
	public static final int HEIGHT = 70;
	public static final int WIDTH = 150;

	public AbstractDisplayElement() {

		addCssClass(WorkflowCssClasses.WORKFLOW_DISPLAY_ELEMENT);
	}
}
