package com.softicar.platform.emf.form.error;

import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.emf.authorizer.EmfAccessPermissionException;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public class EmfDefaultAccessDeniedDisplayCreator<R extends IEmfTableRow<R, ?>> implements IEmfAccessDeniedDisplayCreator<R> {

	@Override
	public IDomNode create(R tableRow, EmfAccessPermissionException exception) {

		return createExceptionMessageElement(exception);
	}
}
