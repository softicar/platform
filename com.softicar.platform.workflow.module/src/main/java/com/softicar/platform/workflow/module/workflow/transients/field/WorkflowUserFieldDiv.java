package com.softicar.platform.workflow.module.workflow.transients.field;

import com.softicar.platform.dom.elements.DomDiv;
import java.util.Set;

public class WorkflowUserFieldDiv extends DomDiv {

	public WorkflowUserFieldDiv(Set<String> users) {

		for (String user: users) {
			appendChild(new DomDiv()).appendText(user);
		}
	}
}
