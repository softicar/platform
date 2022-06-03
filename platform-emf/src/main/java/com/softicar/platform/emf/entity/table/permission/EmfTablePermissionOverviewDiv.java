package com.softicar.platform.emf.entity.table.permission;

import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.emf.permission.CurrentEmfPermissionRegistry;
import com.softicar.platform.emf.table.IEmfTable;

public class EmfTablePermissionOverviewDiv extends DomDiv {

	public EmfTablePermissionOverviewDiv(IEmfTable<?, ?, ?> table) {

		CurrentEmfPermissionRegistry//
			.get()
			.getAtomicPermissions(table)
			.stream()
			.map(permission -> permission.getTitle())
			.map(title -> title.toString())
			.distinct()
			.sorted()
			.forEach(title -> appendPermissionTitle(title));
	}

	private void appendPermissionTitle(String title) {

		appendChild(title);
		appendNewChild(DomElementTag.BR);
	}
}
