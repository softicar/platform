package com.softicar.platform.emf.action.wizard;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.emf.form.section.IEmfFormSectionDiv;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public interface IEmfWizardActionStep<R extends IEmfTableRow<R, ?>> {

	IResource getIcon();

	IDisplayString getTitle();

	boolean isDone(R tableRow);

	boolean isDoable(R tableRow);

	void undo(R tableRow);

	void integrate(R tableRow, IEmfFormSectionDiv<R> actionContainer);
}
