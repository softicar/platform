package com.softicar.platform.dom.elements;

import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.dom.parent.DomParentElement;

/**
 * This class represents an Html form element.
 * <p>
 * A form element is generally useless with Ajax.
 * <p>
 * Please note that by default, submitting is disabled.
 *
 * @author Oliver Richers
 */
public class DomForm extends DomParentElement {

	public DomForm() {

		this(false);
	}

	public DomForm(boolean submittable) {

		if (!submittable) {
			setAttribute("onsubmit", "function() { return false; }", false);
		}
	}

	@Override
	public DomElementTag getTag() {

		return DomElementTag.FORM;
	}

	public DomForm triggerUploadOnChange(IDomNode triggerNode) {

		getDomEngine().triggerUploadOnChange(this, triggerNode);
		return this;
	}

	public DomForm triggerUploadOnClick(IDomNode triggerNode) {

		getDomEngine().triggerUploadOnClick(this, triggerNode);
		return this;
	}
}
