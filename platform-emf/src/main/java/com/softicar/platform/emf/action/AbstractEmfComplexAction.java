package com.softicar.platform.emf.action;

import com.softicar.platform.common.core.interfaces.IRefreshable;
import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.emf.action.marker.EmfActionMarker;
import com.softicar.platform.emf.form.section.IEmfFormSectionDiv;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public abstract class AbstractEmfComplexAction<R extends IEmfTableRow<R, ?>> implements IEmfPrimaryAction<R> {

	@Override
	public final boolean isAvailable(R tableRow, IBasicUser user) {

		return IEmfPrimaryAction.super.isAvailable(tableRow, user);
	}

	@Override
	public void integrate(R tableRow, IEmfFormSectionDiv<R> actionContainer) {

		actionContainer.addElement(() -> createActionDiv(tableRow, actionContainer));
	}

	protected abstract DomDiv createActionDiv(R tableRow, IRefreshable refreshable);

	private DomDiv createActionDiv(R tableRow, IEmfFormSectionDiv<R> actionContainer) {

		DomDiv actionDiv = createActionDiv(tableRow, actionContainer.getFormBody()::queueEntityForRefresh);
		actionDiv.setMarker(new EmfActionMarker(this));
		return actionDiv;
	}
}
