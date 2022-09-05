package com.softicar.platform.emf.form.error;

import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.DomImages;
import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.emf.authorizer.EmfAccessPermissionException;
import com.softicar.platform.emf.form.EmfForm;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public class EmfFormAccessDeniedDiv<R extends IEmfTableRow<R, ?>> extends DomDiv {

	public EmfFormAccessDeniedDiv(EmfForm<R> form, EmfAccessPermissionException exception) {

		appendChild(createExceptionDisplay(form.getTableRow(), exception));
		appendNewChild(DomElementTag.HR);
		appendChild(
			new DomButton()//
				.setClickCallback(form::closeFrame)
				.setIcon(DomImages.DIALOG_CLOSE.getResource())
				.setLabel(DomI18n.CLOSE));
	}

	private IDomNode createExceptionDisplay(R tableRow, EmfAccessPermissionException exception) {

		return tableRow//
			.table()
			.getAuthorizer()
			.getAccessDeniedDisplayCreator()
			.create(tableRow, exception);
	}
}
