package com.softicar.platform.emf.form;

import com.softicar.platform.db.core.transaction.DbTransaction;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomElementsImages;
import com.softicar.platform.dom.elements.bar.DomActionBar;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.emf.EmfCssClasses;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.EmfImages;
import com.softicar.platform.emf.EmfTestMarker;
import com.softicar.platform.emf.table.row.IEmfTableRow;

class EmfFormSaveOrCancelActionsInput<R extends IEmfTableRow<R, ?>> extends DomDiv {

	private final IEmfFormBody<R> formBody;
	private final EmfFormAttributesDiv<R> attributesDiv;
	private final DomDiv buttonContainer;

	public EmfFormSaveOrCancelActionsInput(IEmfFormBody<R> formBody, EmfFormAttributesDiv<R> attributesDiv) {

		this.formBody = formBody;
		this.attributesDiv = attributesDiv;
		this.buttonContainer = new DomActionBar();

		addCssClass(EmfCssClasses.EMF_FORM_SAVE_OR_CANCEL_ACTIONS_INPUT);

		if (!formBody.getForm().isDirectEditingEnabled()) {
			buttonContainer
				.appendChild(
					new DomButton()//
						.setIcon(EmfImages.ENTITY_SAVE.getResource())
						.setLabel(EmfI18n.SAVE)
						.addMarker(EmfTestMarker.FORM_SAVE)
						.setClickCallback(() -> save(false)));
		}
		buttonContainer
			.appendChild(
				new DomButton()//
					.setIcon(EmfImages.ENTITY_SAVE_AND_CLOSE.getResource())
					.setLabel(EmfI18n.SAVE_AND_CLOSE)
					.addMarker(EmfTestMarker.FORM_SAVE_AND_CLOSE)
					.setClickCallback(() -> save(true)));
		buttonContainer
			.appendChild(
				new DomButton()//
					.setIcon(DomElementsImages.DIALOG_CANCEL.getResource())
					.setLabel(EmfI18n.CANCEL)
					.addMarker(EmfTestMarker.FORM_CANCEL)
					.setClickCallback(() -> formBody.cancelEditMode()));

		appendChild(buttonContainer);
	}

	private void save(boolean closeAfterSave) {

		boolean creation = formBody.getTableRow().impermanent();
		try (var rootTransaction = new DbTransaction()) {
			rootTransaction.assertIsRootTransaction();
			formBody.getTableRow().assertFresh();
			if (!attributesDiv.tryToApplyValidateAndSave()) {
				return;
			}
			rootTransaction.commit();
		}
		if (creation) {
			formBody.applyCallbackAfterCreation();
		}
		formBody.finishEditMode(closeAfterSave);
	}
}
