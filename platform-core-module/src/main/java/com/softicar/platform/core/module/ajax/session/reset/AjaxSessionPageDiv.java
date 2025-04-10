package com.softicar.platform.core.module.ajax.session.reset;

import com.softicar.platform.ajax.document.AjaxDocument;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.environment.LiveSystemRevision;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.label.DomLabelGrid;
import java.util.Date;
import java.util.Optional;
import jakarta.servlet.http.HttpSession;

public class AjaxSessionPageDiv extends DomDiv {

	public AjaxSessionPageDiv() {

		appendChild(new DetailsDisplay());
	}

	private class DetailsDisplay extends DomLabelGrid {

		public DetailsDisplay() {

			HttpSession httpSession = AjaxDocument//
				.getCurrentDocument()
				.get()
				.getHttpSession();

			var revision = LiveSystemRevision.getCurrentRevision();
			add(CoreI18n.SESSION_ID, httpSession.getId());
			add(CoreI18n.CREATED_AT, DayTime.fromDate(new Date(httpSession.getCreationTime())).toString());
			add(CoreI18n.LAST_ACCESS, DayTime.fromDate(new Date(httpSession.getLastAccessedTime())).toString());
			add(CoreI18n.TIMEOUT_MIN, "" + httpSession.getMaxInactiveInterval() / 60);
			add(CoreI18n.SYSTEM_REVISION, "" + getSystemRevisionInfo(revision.getName()));
			add(CoreI18n.SYSTEM_VERSION, "" + getSystemRevisionInfo(revision.getVersion()));
		}
	}

	private static IDisplayString getSystemRevisionInfo(Optional<String> info) {

		if (info.isPresent()) {
			return IDisplayString.create(info.get());
		} else {
			return CoreI18n.UNKNOWN;
		}
	}
}
