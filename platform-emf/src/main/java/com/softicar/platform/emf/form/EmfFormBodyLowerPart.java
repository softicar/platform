package com.softicar.platform.emf.form;

import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.input.IDomFocusable;
import com.softicar.platform.dom.input.IDomTextualInput;
import com.softicar.platform.emf.form.refresh.EmfFormInteractiveRefreshSectionDiv;
import com.softicar.platform.emf.form.section.IEmfFormSectionContainer;
import com.softicar.platform.emf.table.row.IEmfTableRow;

/**
 * Lower part of the {@link EmfFormBody}.
 * <p>
 * The lower part contains the actions and sections of the {@link EmfForm}.
 *
 * @author Oliver Richers
 */
class EmfFormBodyLowerPart<R extends IEmfTableRow<R, ?>> extends DomDiv {

	private final IEmfFormBody<R> formBody;

	public EmfFormBodyLowerPart(IEmfFormBody<R> formBody) {

		this.formBody = formBody;
	}

	public void showSectionContainer(IEmfFormSectionContainer sectionContainer) {

		removeChildren();
		appendChild(sectionContainer);
		IDomFocusable.focusFirst(IDomTextualInput.class, sectionContainer);
	}

	public void showStandardSectionContainer() {

		showSectionContainer(new EmfFormStandardActionsDiv<>(formBody));
	}

	public void showInteractiveRefreshSectionContainer() {

		showSectionContainer(new EmfFormInteractiveRefreshSectionDiv<>(formBody));
	}
}
