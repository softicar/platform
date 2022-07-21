package com.softicar.platform.core.module.maintenance;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.CoreCssClasses;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.dom.elements.message.DomMessageDiv;
import com.softicar.platform.dom.elements.message.style.DomMessageType;
import com.softicar.platform.dom.elements.wiki.DomWikiDivBuilder;
import java.util.Collection;
import java.util.Objects;

public class MaintenanceWindowsInfoElement extends DomMessageDiv {

	public MaintenanceWindowsInfoElement(Collection<AGMaintenanceWindow> maintenanceWindows) {

		super(DomMessageType.INFO);
		Objects.requireNonNull(maintenanceWindows);

		var divBuilder = new DomWikiDivBuilder().addLine(CoreI18n.PENDING_MAINTENANCE.enclose("**", "**"));

		maintenanceWindows//
			.stream()
			.map(AGMaintenanceWindow::toDisplayWithoutId)
			.forEach(it -> divBuilder.addLine(IDisplayString.create("  * ").concat(it)));

		divBuilder.buildAndAppendTo(this);

		addCssClass(CoreCssClasses.MAINTENANCE_WINDOWS_INFO_ELEMENT);
	}
}
