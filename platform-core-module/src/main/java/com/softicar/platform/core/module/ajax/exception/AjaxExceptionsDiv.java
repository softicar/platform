package com.softicar.platform.core.module.ajax.exception;

import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.ajax.session.AGAjaxSession;
import com.softicar.platform.core.module.ajax.session.revision.AGAjaxSessionRevision;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomElementsImages;
import com.softicar.platform.dom.elements.DomPre;
import com.softicar.platform.dom.elements.bar.DomActionBar;
import com.softicar.platform.dom.elements.button.popup.DomPopupButton;
import com.softicar.platform.dom.elements.label.DomLabelGrid;
import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.emf.data.table.EmfDataTableDivBuilder;
import com.softicar.platform.emf.data.table.IEmfDataTableCell;
import com.softicar.platform.emf.data.table.column.handler.EmfDataTableRowBasedColumnHandler;
import com.softicar.platform.emf.data.table.column.handler.EmfDataTableValueBasedColumnHandler;

class AjaxExceptionsDiv extends DomDiv {

	public AjaxExceptionsDiv() {

		appendChild(
			new EmfDataTableDivBuilder<>(IAjaxExceptionsQuery.FACTORY.createQuery())
				.setColumnHandler(IAjaxExceptionsQuery.SESSION_COLUMN, new SessionColumnHandler())
				.setColumnHandler(IAjaxExceptionsQuery.TEXT_COLUMN, new StackTraceColumnHandler())
				.setConcealed(IAjaxExceptionsQuery.AJAX_EXCEPTION_COLUMN, true)
				.build());

	}

	private static class SessionColumnHandler extends EmfDataTableValueBasedColumnHandler<AGAjaxSession> {

		@Override
		public void buildCell(IEmfDataTableCell<?, AGAjaxSession> cell, AGAjaxSession session) {

			cell
				.appendChild(
					new DomActionBar(//
						new DomPopupButton()//
							.setPopupFactory(() -> new SessionInfoPopup(session))
							.setIcon(DomElementsImages.INFO.getResource())));
		}
	}

	private static class StackTraceColumnHandler extends EmfDataTableRowBasedColumnHandler<IAjaxExceptionsQuery.IRow, String> {

		@Override
		public void buildCell(IEmfDataTableCell<IAjaxExceptionsQuery.IRow, String> cell, IAjaxExceptionsQuery.IRow row) {

			DomActionBar actionBar = new DomActionBar();
			actionBar.appendChild(new StackTraceInfoButton(row));
			actionBar.appendText(row.getText());
			cell.appendChild(actionBar);
		}
	}

	private static class StackTraceInfoButton extends DomPopupButton {

		public StackTraceInfoButton(IAjaxExceptionsQuery.IRow row) {

			setPopupFactory(() -> new StacktraceInfoPopup(row.getAjaxException().getExceptionStackTrace()));
			setIcon(DomElementsImages.INFO.getResource());
			setTitle(CoreI18n.SHOW_STACK_TRACE);
		}
	}

	private static class SessionInfoPopup extends DomPopup {

		public SessionInfoPopup(AGAjaxSession session) {

			AGAjaxSessionRevision revision = AGAjaxSessionRevision.TABLE.createSelect().where(AGAjaxSessionRevision.SESSION.isEqual(session)).getFirst();
			setCaption(CoreI18n.SESSION_INFORMATION);
			appendChild(new DomLabelGrid())//
				.add(CoreI18n.SESSION_ID, session.getId().toString())
				.add(CoreI18n.USER, session.getUser().toDisplay())
				.add(CoreI18n.REMOTE_ADDRESS, session.getClientIpAddress())
				.add(CoreI18n.LOCAL_NAME, session.getLocalName())
				.add(CoreI18n.LOCAL_ADDRESS, session.getLocalAddress())
				.add(CoreI18n.LOCAL_PORT, session.getLocalPort().toString())
				.add(CoreI18n.ACCESS_DATE, session.getAccessDate().toGermanString())
				.add(CoreI18n.REVISION, revision == null? "" : revision.getRevision());
			appendCloseButton();
		}
	}

	private static class StacktraceInfoPopup extends DomPopup {

		public StacktraceInfoPopup(String stackTrace) {

			setCaption(CoreI18n.STACK_TRACE);
			DomPre domPre = appendChild(new DomPre());
			domPre.appendText(stackTrace);
			appendCloseButton();
		}
	}
}
