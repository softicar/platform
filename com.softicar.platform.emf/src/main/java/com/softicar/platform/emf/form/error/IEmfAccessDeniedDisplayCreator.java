package com.softicar.platform.emf.form.error;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.elements.message.DomMessageDiv;
import com.softicar.platform.dom.elements.message.style.DomMessageType;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.dom.parent.IDomParentElement;
import com.softicar.platform.emf.authorizer.EmfAccessPermissionException;
import com.softicar.platform.emf.table.row.IEmfTableRow;

@FunctionalInterface
public interface IEmfAccessDeniedDisplayCreator<R extends IEmfTableRow<R, ?>> {

	IDomNode create(R tableRow, EmfAccessPermissionException exception);

	default IDomParentElement createExceptionMessageElement(EmfAccessPermissionException exception) {

		return new DomMessageDiv(DomMessageType.ERROR, IDisplayString.create(exception.getLocalizedMessage()));
	}
}
