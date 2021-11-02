package com.softicar.platform.core.module.ajax.exception.view;

import com.softicar.platform.common.core.interfaces.IRefreshable;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.ajax.session.AGAjaxSession;
import com.softicar.platform.core.module.environment.AGLiveSystemConfiguration;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomElementsImages;
import com.softicar.platform.dom.elements.bar.DomActionBar;
import com.softicar.platform.dom.elements.button.popup.DomPopupButton;
import com.softicar.platform.dom.elements.checkbox.DomCheckbox;
import com.softicar.platform.emf.data.table.EmfDataTableDivBuilder;
import com.softicar.platform.emf.data.table.IEmfDataTableCell;
import com.softicar.platform.emf.data.table.column.handler.EmfDataTableRowBasedColumnHandler;
import com.softicar.platform.emf.data.table.column.handler.EmfDataTableValueBasedColumnHandler;

public class AjaxExceptionsViewDiv extends DomDiv implements IRefreshable {

	private final DomCheckbox customExceptionCheckbox;
	private final DomActionBar checkBoxActionBar;

	public AjaxExceptionsViewDiv() {

		customExceptionCheckbox = new DomCheckbox()//
			.setLabel(CoreI18n.IGNORE_ARG1_EXCEPTIONS.toDisplay(AGLiveSystemConfiguration.getSystemIdentifier()))
			.setChangeCallback(this::refresh);
		checkBoxActionBar = new DomActionBar(customExceptionCheckbox);

		refresh();
	}

	@Override
	public void refresh() {

		removeChildren();

		appendChild(checkBoxActionBar);

		appendChild(
			new EmfDataTableDivBuilder<>(IAjaxExceptionViewQuery.FACTORY.createQuery().setIgnoreSofticarEx(customExceptionCheckbox.isChecked()))
				.setColumnHandler(IAjaxExceptionViewQuery.SESSION_COLUMN, new SessionColumnHandler())
				.setColumnHandler(IAjaxExceptionViewQuery.TEXT_COLUMN, new StackTraceColumnHandler())
				.setConcealed(IAjaxExceptionViewQuery.AJAX_EXCEPTION_COLUMN, true)
				.build());

	}

	private class SessionColumnHandler extends EmfDataTableValueBasedColumnHandler<AGAjaxSession> {

		@Override
		public void buildCell(IEmfDataTableCell<?, AGAjaxSession> cell, AGAjaxSession value) {

			cell
				.appendChild(
					new DomActionBar(//
						new DomPopupButton()//
							.setPopupFactory(() -> InfoPopupCreator.createForSession(value))
							.setIcon(DomElementsImages.INFO.getResource())));
		}
	}

	private class StackTraceColumnHandler extends EmfDataTableRowBasedColumnHandler<IAjaxExceptionViewQuery.IRow, String> {

		@Override
		public void buildCell(IEmfDataTableCell<IAjaxExceptionViewQuery.IRow, String> cell, IAjaxExceptionViewQuery.IRow row) {

			DomActionBar actionBar = new DomActionBar();
			actionBar.appendChild(new StackTraceInfoButton(row));
			actionBar.appendText(row.getText());
			cell.appendChild(actionBar);
		}
	}

	private static class StackTraceInfoButton extends DomPopupButton {

		public StackTraceInfoButton(IAjaxExceptionViewQuery.IRow row) {

			setPopupFactory(() -> InfoPopupCreator.createForStackTrace(row.getAjaxException().getExceptionStackTrace()));
			setIcon(DomElementsImages.INFO.getResource());
			setTitle(CoreI18n.SHOW_STACK_TRACE);
		}
	}
}
