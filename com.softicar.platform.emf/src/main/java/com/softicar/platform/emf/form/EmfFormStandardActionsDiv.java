package com.softicar.platform.emf.form;

import com.softicar.platform.common.core.user.CurrentBasicUser;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.emf.action.IEmfPrimaryAction;
import com.softicar.platform.emf.form.section.EmfFormSectionDiv;
import com.softicar.platform.emf.form.section.IEmfFormSectionContainer;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class EmfFormStandardActionsDiv<R extends IEmfTableRow<R, ?>> extends DomDiv implements IEmfFormSectionContainer {

	private final IEmfFormBody<R> body;

	public EmfFormStandardActionsDiv(IEmfFormBody<R> body) {

		this.body = body;
		List<EmfFormSectionDiv<R>> sectionDivs = new ArrayList<>();
		addPrimaryActionSectionIfNecessary(sectionDivs);
		sectionDivs.addAll(fetchFactoryBasedSections());
		openFirstSection(sectionDivs);
		sectionDivs.forEach(this::appendChild);
	}

	private void addPrimaryActionSectionIfNecessary(List<EmfFormSectionDiv<R>> sectionDivs) {

		var tableRow = body.getTableRow();
		var user = CurrentBasicUser.get();
		List<IEmfPrimaryAction<R>> availableActions = tableRow//
			.table()
			.getPrimaryActions(tableRow)
			.stream()
			.filter(it -> it.isAvailable(tableRow, user))
			.collect(Collectors.toList());
		if (!availableActions.isEmpty()) {
			sectionDivs.add(new EmfFormPrimaryActionSectionDiv<>(body, availableActions));
		}
	}

	private void openFirstSection(List<EmfFormSectionDiv<R>> sectionDivs) {

		if (!sectionDivs.isEmpty()) {
			sectionDivs.iterator().next().setOpen(true);
		}
	}

	private List<EmfFormSectionDiv<R>> fetchFactoryBasedSections() {

		return body//
			.getEntityTable()
			.getFormSectionConfiguration()
			.createAvailableSections(body)
			.stream()
			.filter(EmfFormSectionDiv::showSection)
			.collect(Collectors.toList());
	}
}
