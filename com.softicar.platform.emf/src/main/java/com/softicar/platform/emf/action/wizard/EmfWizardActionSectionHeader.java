package com.softicar.platform.emf.action.wizard;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.emf.form.section.header.IEmfFormSectionHeader;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.Optional;

class EmfWizardActionSectionHeader<R extends IEmfTableRow<R, ?>> extends EmfWizardActionHolder<R> implements IEmfFormSectionHeader {

	public EmfWizardActionSectionHeader(EmfWizardActionDiv<R> actionDiv, IEmfWizardActionStep<R> action) {

		super(actionDiv, action);
	}

	@Override
	public Optional<IResource> getIcon() {

		return Optional.ofNullable(getAction().getIcon());
	}

	@Override
	public IDisplayString getDisplayString() {

		return getAction().getTitle();
	}
}
