package com.softicar.platform.dom.elements.button.form;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.dom.elements.DomForm;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.event.DomEventType;

/**
 * A button to submit a {@link DomForm}.
 *
 * @author Oliver Richers
 */
public class DomFormSubmitButton extends DomButton {

	private final DomForm form;

	/**
	 * Constructs the button with the specified form, icon and label.
	 *
	 * @param form
	 *            the form to submit
	 * @param icon
	 *            the icon for the button
	 * @param label
	 *            the label of the button
	 */
	public DomFormSubmitButton(DomForm form, IResource icon, IDisplayString label) {

		this.form = form;

		setIcon(icon);
		setLabel(label);

		unlistenToEvent(DomEventType.CLICK);
		unlistenToEvent(DomEventType.ENTER);
		unlistenToEvent(DomEventType.SPACE);

		setAttribute("href", getHRef());
	}

	private String getHRef() {

		return new StringBuilder().append("javascript:GLOBAL.context.submitForm(").append(form.getNodeId()).append(");").toString();
	}
}
