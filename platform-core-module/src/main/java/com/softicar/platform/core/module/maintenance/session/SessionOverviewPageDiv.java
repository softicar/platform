package com.softicar.platform.core.module.maintenance.session;

import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.emf.data.table.EmfDataTableDivBuilder;

public class SessionOverviewPageDiv extends DomDiv {

	public SessionOverviewPageDiv() {

		new EmfDataTableDivBuilder<>(new SessionOverviewTable()).buildAndAppendTo(this);
	}
}
