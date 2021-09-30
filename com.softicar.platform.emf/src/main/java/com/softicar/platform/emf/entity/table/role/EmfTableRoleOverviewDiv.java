package com.softicar.platform.emf.entity.table.role;

import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.emf.authorization.role.CurrentEmfRoleRegistry;
import com.softicar.platform.emf.table.IEmfTable;

public class EmfTableRoleOverviewDiv extends DomDiv {

	public EmfTableRoleOverviewDiv(IEmfTable<?, ?, ?> table) {

		CurrentEmfRoleRegistry//
			.get()
			.getAtomicRoles(table)
			.stream()
			.map(role -> role.getTitle())
			.map(title -> title.toString())
			.distinct()
			.sorted()
			.forEach(title -> appendRoleTitle(title));
	}

	private void appendRoleTitle(String title) {

		appendChild(title);
		appendNewChild(DomElementTag.BR);
	}
}
