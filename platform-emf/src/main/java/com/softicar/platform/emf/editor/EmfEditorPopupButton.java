package com.softicar.platform.emf.editor;

import com.softicar.platform.common.core.user.CurrentBasicUser;
import com.softicar.platform.dom.elements.button.popup.DomPopupButton;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.EmfImages;
import com.softicar.platform.emf.form.popup.EmfFormPopup;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public class EmfEditorPopupButton<R extends IEmfTableRow<R, ?>> extends DomPopupButton {

	public EmfEditorPopupButton(R tableRow) {

		setPopupFactory(() -> new EmfFormPopup<>(tableRow).setDirectEditing(true));
		setDisabled(!isEditAllowed(tableRow));
		setIcon(EmfImages.ENTITY_EDIT.getResource());
		setTitle(EmfI18n.EDIT_ENTRY);
	}

	private boolean isEditAllowed(R tableRow) {

		return tableRow//
			.table()
			.getAuthorizer()
			.getEditRole()
			.test(tableRow, CurrentBasicUser.get());
	}
}
