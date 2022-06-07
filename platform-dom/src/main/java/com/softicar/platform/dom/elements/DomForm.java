package com.softicar.platform.dom.elements;

import com.softicar.platform.dom.element.DomElementTag;
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

	/**
	 * Triggers the upload of all files, selected in the contained
	 * {@link DomFileInput} elements.
	 */
	public void uploadFiles() {

		getDomEngine().uploadFiles(this);
	}
}
