package com.softicar.platform.core.module.user.session;

import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.bar.DomActionBar;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.EmfImages;
import com.softicar.platform.emf.data.table.EmfDataTableDivBuilder;
import com.softicar.platform.emf.data.table.IEmfDataTableActionCell;
import com.softicar.platform.emf.data.table.IEmfDataTableActionColumnHandler;
import com.softicar.platform.emf.data.table.IEmfDataTableCell;
import com.softicar.platform.emf.data.table.IEmfDataTableDiv;
import com.softicar.platform.emf.data.table.column.handler.EmfDataTableValueBasedColumnHandler;
import com.softicar.platform.emf.table.row.EmfTableRowDisplay;
import jakarta.servlet.http.HttpSession;

public class UserSessionsPageDiv extends DomDiv {

	private final IEmfDataTableDiv<HttpSession> tableDiv;

	public UserSessionsPageDiv() {

		var actionBar = appendChild(new DomActionBar());
		var table = new UserSessionsTable();
		this.tableDiv = new EmfDataTableDivBuilder<>(table)//
			.setActionColumnHandler(new ActionColumnHandler())
			.setColumnHandler(table.getUserColumn(), new UserColumnHandler())
			.buildAndAppendTo(this);

		actionBar
			.appendChild(
				new DomButton()//
					.setClickCallback(tableDiv::refresh)
					.setIcon(EmfImages.REFRESH.getResource())
					.setLabel(EmfI18n.REFRESH));

		actionBar
			.appendChild(
				new DomButton()//
					.setClickCallback(new UserSessionManager()::invalidateAllSessions)
					.setIcon(CoreImages.TERMINATE.getResource())
					.setLabel(CoreI18n.TERMINATE_ALL_SESSIONS)
					.setConfirmationMessage(CoreI18n.ARE_YOU_SURE_QUESTION));
	}

	private class ActionColumnHandler implements IEmfDataTableActionColumnHandler<HttpSession> {

		@Override
		public void buildCell(IEmfDataTableActionCell<HttpSession> cell, HttpSession session) {

			cell
				.appendChild(
					new DomActionBar(
						new DomButton()//
							.setClickCallback(() -> invalidateSessionAndRefresh(session))
							.setTitle(CoreI18n.TERMINATE_SESSION)
							.setIcon(CoreImages.TERMINATE.getResource())
							.setConfirmationMessage(CoreI18n.ARE_YOU_SURE_QUESTION)));
		}
	}

	private class UserColumnHandler extends EmfDataTableValueBasedColumnHandler<AGUser> {

		@Override
		public void buildCell(IEmfDataTableCell<?, AGUser> cell, AGUser user) {

			if (user != null) {
				cell.appendChild(new EmfTableRowDisplay<>(user));
			} else {
				cell.appendText(CoreI18n.UNKNOWN.encloseInParentheses());
			}
		}
	}

	private void invalidateSessionAndRefresh(HttpSession session) {

		session.invalidate();
		tableDiv.refresh();
	}
}
