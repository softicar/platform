package com.softicar.platform.emf.action.wizard;

import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.emf.action.IEmfPrimaryAction;
import com.softicar.platform.emf.action.marker.EmfActionMarker;
import com.softicar.platform.emf.form.section.IEmfFormSectionDiv;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractEmfWizardAction<R extends IEmfTableRow<R, ?>> implements IEmfPrimaryAction<R> {

	private final List<IEmfWizardActionStep<R>> steps = new ArrayList<>();

	@Override
	public final boolean isAvailable(R tableRow, IBasicUser user) {

		return IEmfPrimaryAction.super.isAvailable(tableRow, user);
	}

	@Override
	public void integrate(R tableRow, IEmfFormSectionDiv<R> actionContainer) {

		actionContainer.addElement(() -> createActionDiv(tableRow, actionContainer));
		actionContainer.setInvisibleMode();
	}

	public List<IEmfWizardActionStep<R>> getSteps() {

		return steps;
	}

	protected void addStep(IEmfWizardActionStep<R> step) {

		steps.add(step);
	}

	private DomDiv createActionDiv(R tableRow, IEmfFormSectionDiv<R> actionContainer) {

		EmfWizardActionDiv<R> actionDiv = new EmfWizardActionDiv<>(this, tableRow, actionContainer.getFormBody());
		actionDiv.addMarker(new EmfActionMarker(this));
		return actionDiv;
	}
}
