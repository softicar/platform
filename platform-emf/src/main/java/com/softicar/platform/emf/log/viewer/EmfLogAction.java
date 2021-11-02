package com.softicar.platform.emf.log.viewer;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.EmfImages;
import com.softicar.platform.emf.action.IEmfCommonAction;
import com.softicar.platform.emf.action.IEmfManagementAction;
import com.softicar.platform.emf.authorization.role.IEmfRole;
import com.softicar.platform.emf.form.IEmfFormBody;
import com.softicar.platform.emf.predicate.EmfPredicate;
import com.softicar.platform.emf.predicate.IEmfPredicate;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public class EmfLogAction<R extends IEmfTableRow<R, ?>> implements IEmfCommonAction<R>, IEmfManagementAction<R> {

	private final IEmfTable<R, ?, ?> table;

	public EmfLogAction(IEmfTable<R, ?, ?> table) {

		this.table = table;
	}

	@Override
	public IResource getIcon() {

		return EmfImages.ENTITY_LOG.getResource();
	}

	@Override
	public IDisplayString getTitle() {

		return EmfI18n.SHOW_HISTORY;
	}

	@Override
	public IDisplayString getDescription() {

		return EmfI18n.OPENS_THE_HISTORY_WINDOW_FOR_THE_GIVEN_ENTRY;
	}

	@Override
	public IEmfPredicate<R> getPrecondition() {

		return new EmfPredicate<>(EmfI18n.CHANGE_LOGGERS_EXIST, this::hasChangeLoggers);
	}

	@Override
	public IEmfRole<R> getAuthorizedRole() {

		return table.getAuthorizer().getViewRole();
	}

	@Override
	public void handleClick(IEmfFormBody<R> formBody) {

		handleClick(formBody.getTableRow());
	}

	@Override
	public void handleClick(R tableRow) {

		new EmfLogPopup<>(tableRow).show();
	}

	private boolean hasChangeLoggers(R tableRow) {

		return !tableRow.table().getChangeLoggers().isEmpty();
	}
}
