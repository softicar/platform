package com.softicar.platform.emf.action;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.db.core.transaction.DbTransaction;
import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomElementsImages;
import com.softicar.platform.dom.elements.DomTextArea;
import com.softicar.platform.dom.elements.bar.DomActionBar;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.label.DomLabelGrid;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.EmfImages;
import com.softicar.platform.emf.EmfTestMarker;
import com.softicar.platform.emf.form.IEmfFormBody;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.ArrayList;
import java.util.List;

class EmfPromptActionInput<R extends IEmfTableRow<R, ?>> extends DomDiv implements IEmfPromptActionInput<R> {

	private final IEmfFormBody<R> formBody;
	private final List<INullaryVoidFunction> saveHandlers;
	private final DomDiv contentContainer;
	private final DomDiv buttonContainer;
	private DomLabelGrid inputGrid;
	private boolean cancelSave;

	public EmfPromptActionInput(IEmfFormBody<R> formBody) {

		this.formBody = formBody;
		this.saveHandlers = new ArrayList<>();
		this.contentContainer = new DomDiv();
		this.buttonContainer = new DomActionBar();
		this.inputGrid = null;
		this.cancelSave = false;

		buttonContainer
			.appendChild(
				new DomButton()//
					.setIcon(EmfImages.ENTITY_SAVE.getResource())
					.setLabel(EmfI18n.SAVE)
					.setClickCallback(() -> save())
					.addMarker(EmfTestMarker.FORM_SAVE));

		buttonContainer
			.appendChild(
				new DomButton()//
					.setIcon(EmfImages.ENTITY_SAVE_AND_CLOSE.getResource())
					.setLabel(EmfI18n.SAVE_AND_CLOSE)
					.setClickCallback(() -> saveAndClose())
					.addMarker(EmfTestMarker.FORM_SAVE_AND_CLOSE));

		buttonContainer
			.appendChild(
				new DomButton()//
					.setIcon(DomElementsImages.DIALOG_CANCEL.getResource())
					.setLabel(EmfI18n.CANCEL)
					.setClickCallback(() -> formBody.showStandardSectionContainer())
					.addMarker(EmfTestMarker.FORM_CANCEL));

		appendChild(contentContainer);
		appendNewChild(DomElementTag.HR);
		appendChild(buttonContainer);
	}

	@Override
	public IEmfFormBody<R> getFormBody() {

		return formBody;
	}

	@Override
	public void addSaveHandler(INullaryVoidFunction saveHandler) {

		this.saveHandlers.add(saveHandler);
	}

	@Override
	public void appendRow(IDisplayString title, IDomNode node) {

		if (inputGrid == null) {
			inputGrid = prependChild(new DomLabelGrid());
		}

		inputGrid.add(title, node);
	}

	@Override
	public DomTextArea appendTextArea(IDisplayString title) {

		DomTextArea textArea = new DomTextArea().setRowCount(5);
		appendRow(title, textArea);
		return textArea;
	}

	@Override
	public void appendNode(IDomNode node) {

		contentContainer.appendChild(node);
	}

	@Override
	public void cancelSave() {

		this.cancelSave = true;
	}

	private void saveAndClose() {

		save(true);
	}

	private void save() {

		save(false);
	}

	private void save(boolean close) {

		try (DbTransaction transaction = new DbTransaction()) {
			formBody.getTableRow().assertFresh();
			if (executeSaveHandlers()) {
				formBody.queueEntityForRefresh();
				if (close) {
					formBody.closeFrame();
				}
			}
			transaction.commit();
		}
	}

	private boolean executeSaveHandlers() {

		this.cancelSave = false;
		for (INullaryVoidFunction handler: saveHandlers) {
			handler.apply();
			if (cancelSave) {
				return false;
			}
		}
		return true;
	}
}
