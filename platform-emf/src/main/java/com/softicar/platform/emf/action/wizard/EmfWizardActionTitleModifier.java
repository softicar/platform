package com.softicar.platform.emf.action.wizard;

import com.softicar.platform.dom.element.IDomElement;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomElementsImages;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.checkbox.DomCheckbox;
import com.softicar.platform.emf.EmfCssClasses;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.form.section.header.EmfFormSectionHeaderDiv;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.function.Consumer;

class EmfWizardActionTitleModifier<R extends IEmfTableRow<R, ?>> extends EmfWizardActionHolder<R> implements Consumer<EmfFormSectionHeaderDiv> {

	public EmfWizardActionTitleModifier(EmfWizardActionDiv<R> actionDiv, IEmfWizardActionStep<R> action) {

		super(actionDiv, action);
	}

	@Override
	public void accept(EmfFormSectionHeaderDiv headerDiv) {

		DomDiv content = new DomDiv();
		content.addCssClass(EmfCssClasses.EMF_FORM_WIZARD_ACTION_TITLE_MODIFIER);
		boolean done = isDone();
		boolean doable = isDoable();

		// append "undo" element
		if (done) {
			IDomElement button = new DomButton()//
				.setClickCallback(this::undo)
				.setIcon(DomElementsImages.UNDO.getResource())
				.setTitle(EmfI18n.UNDO);
			content.appendChild(button);
		}

		// append "done" indicator
		content
			.appendChild(
				new DomCheckbox(done)//
					.setDisabled(true));

		// make (non-)clickable
		if (doable && !done) {
			headerDiv.setClickable(true);
		} else {
			headerDiv.setClickable(false);
		}

		// set opacity
		if (!doable && !done) {
			headerDiv.addCssClass(EmfCssClasses.EMF_FORM_WIZARD_ACTION_HEADER_DISABLED);
		}

		headerDiv.setContent(content);
	}
}
