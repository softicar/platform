package com.softicar.platform.emf.record;

import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.runtime.table.row.DbTableRowFlag;
import com.softicar.platform.dom.DomImages;
import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.dom.elements.popup.compositor.CurrentDomPopupCompositor;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.EmfImages;
import com.softicar.platform.emf.EmfTestMarker;
import com.softicar.platform.emf.editor.EmfAttributesDiv;
import com.softicar.platform.emf.form.popup.EmfFormPopup;
import com.softicar.platform.emf.record.table.IEmfRecordTable;

public class EmfRecordCreationPopup<R extends IEmfRecord<R, P>, P, S> extends DomPopup {

	private final IEmfRecordTable<R, P, S> table;
	private final S scope;
	private final R dummy;
	private final EmfAttributesDiv<R> attributesDiv;

	public EmfRecordCreationPopup(IEmfRecordTable<R, P, S> table, S scope) {

		this.table = table;
		this.scope = scope;
		this.dummy = createDummy();
		this.attributesDiv = new EmfAttributesDiv<>(dummy, true);
		this.configuration.setChildClosingModeAutomaticNone();

		table//
			.getPrimaryKey()
			.getFields()
			.stream()
			.filter(this::isNotScopeField)
			.forEach(attributesDiv::addAttribute);

		appendChild(attributesDiv);

		appendNewChild(DomElementTag.HR);

		appendActionNode(
			new DomButton()//
				.setIcon(EmfImages.ENTITY_CREATE_NEXT_STEP.getResource())
				.setLabel(EmfI18n.NEXT)
				.addMarker(EmfTestMarker.FORM_CREATE_NEXT)
				.setClickCallback(this::showFormPopup));
		appendActionNode(
			new DomButton()//
				.setIcon(DomImages.DIALOG_CANCEL.getResource())
				.setLabel(EmfI18n.CANCEL)
				.addMarker(EmfTestMarker.FORM_CANCEL)
				.setClickCallback(() -> CurrentDomPopupCompositor.get().closeInteractively(this)));

		setCaption(table.getTitle());
	}

	private R createDummy() {

		R dummy = table//
			.getRowFactory()
			.get()
			.initializer()
			.initializeFlagsAndSetAllFieldsToDefault(DbTableRowFlag.IMPERMANENT);
		setScope(dummy);
		return dummy;
	}

	private void showFormPopup() {

		close();
		new EmfFormPopup<>(getOrCreateRecord()).setDirectEditing(true).open();
	}

	private R getOrCreateRecord() {

		attributesDiv.applyToTableRow();
		R record = table.getOrCreate(table.getPrimaryKey().getFromRow(dummy));
		setScope(record);
		return record;
	}

	private void setScope(R record) {

		table//
			.getScopeField()
			.ifPresent(field -> field.setValue(record, scope));
	}

	private boolean isNotScopeField(IDbField<R, ?> field) {

		return !isScopeField(field);
	}

	private boolean isScopeField(IDbField<R, ?> field) {

		return table.getScopeField().map(scopeField -> scopeField == field).orElse(false);
	}
}
