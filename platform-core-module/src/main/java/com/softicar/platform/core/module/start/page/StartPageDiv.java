package com.softicar.platform.core.module.start.page;

import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.common.core.thread.sleep.Sleep;
import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CorePermissions;
import com.softicar.platform.core.module.CoreTestMarker;
import com.softicar.platform.core.module.event.AGSystemEvent;
import com.softicar.platform.core.module.event.SystemEventPage;
import com.softicar.platform.core.module.maintenance.AGMaintenanceWindow;
import com.softicar.platform.core.module.maintenance.MaintenanceWindowsInfoElement;
import com.softicar.platform.core.module.page.PageButton;
import com.softicar.platform.core.module.user.CurrentUser;
import com.softicar.platform.core.module.user.password.change.UserPasswordChangeDiv;
import com.softicar.platform.core.module.user.password.policy.SofticarPasswordPolicy;
import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.element.IDomElement;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.bar.DomBar;
import com.softicar.platform.dom.elements.message.DomMessageDiv;
import com.softicar.platform.dom.elements.message.style.DomMessageType;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.event.IDomWheelEventHandler;
import com.softicar.platform.dom.style.CssStyle;
import java.util.ArrayList;
import java.util.Collection;

class StartPageDiv extends DomDiv {

	public StartPageDiv() {

		var sections = new ArrayList<IDomElement>();
		addPendingSystemEventsSection(sections);
		addPendingMaintenanceSection(sections);
		addPasswordChangeSection(sections);
		appendSectionsSeparatedByHr(sections);

		addMarker(CoreTestMarker.START_PAGE_MAIN_ELEMENT);

		// FIXME remove
		appendChild(new WheelTestDiv());
	}

	// FIXME remove
	private class WheelTestDiv extends DomDiv implements IDomWheelEventHandler {

		public WheelTestDiv() {

			appendText("wheel me");
			setStyle(CssStyle.BACKGROUND_COLOR, "yellow");
		}

		@Override
		public void handleWheel(IDomEvent event) {

			if (event.getDeltaY() > 0) {
				Log.finfo("down");
				appendChild(new DomDiv()).appendText("down");
				Sleep.sleep(1000);
			} else if (event.getDeltaY() < 0) {
				Log.finfo("up");
				appendChild(new DomDiv()).appendText("up");
				Sleep.sleep(1000);
			}
		}
	}

	private void addPendingSystemEventsSection(Collection<IDomElement> sections) {

		if (CorePermissions.ADMINISTRATION.test(AGCoreModuleInstance.getInstance(), CurrentUser.get())) {
			var count = AGSystemEvent.TABLE.createSelect().where(AGSystemEvent.NEEDS_CONFIRMATION).count();
			if (count > 0) {
				var message = CoreI18n.THERE_ARE_ARG1_SYSTEM_EVENTS_THAT_NEED_YOUR_ATTENTION.toDisplay(count);
				var pageButton = new PageButton<>(SystemEventPage.class, AGCoreModuleInstance.getInstance())//
					.setLabel(CoreI18n.OPEN);
				var messageBar = new DomBar();
				messageBar.appendText(message);
				messageBar.appendChild(pageButton);
				sections.add(new DomMessageDiv(DomMessageType.ERROR, messageBar));
			}
		}
	}

	private void addPendingMaintenanceSection(Collection<IDomElement> sections) {

		var maintenanceWindows = AGMaintenanceWindow.getAllPendingMaintenanceWindows();
		if (!maintenanceWindows.isEmpty()) {
			sections.add(new MaintenanceWindowsInfoElement(maintenanceWindows));
		}
	}

	private void addPasswordChangeSection(Collection<IDomElement> sections) {

		if (CurrentUser.get().isPasswordChangeNecessary()) {
			sections.add(new UserPasswordChangeDiv(SofticarPasswordPolicy.get()));
		}
	}

	private void appendSectionsSeparatedByHr(Collection<IDomElement> sections) {

		for (var iterator = sections.iterator(); iterator.hasNext();) {
			appendChild(iterator.next());
			if (iterator.hasNext()) {
				appendNewChild(DomElementTag.HR);
			}
		}
	}
}
