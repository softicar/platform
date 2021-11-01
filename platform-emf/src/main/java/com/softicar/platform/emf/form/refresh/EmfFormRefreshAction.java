package com.softicar.platform.emf.form.refresh;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.db.runtime.cache.DbTableRowCaches;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.EmfImages;
import com.softicar.platform.emf.action.IEmfCommonAction;
import com.softicar.platform.emf.authorization.role.EmfRoles;
import com.softicar.platform.emf.authorization.role.IEmfRole;
import com.softicar.platform.emf.form.IEmfFormBody;
import com.softicar.platform.emf.predicate.EmfPredicates;
import com.softicar.platform.emf.predicate.IEmfPredicate;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public class EmfFormRefreshAction<R extends IEmfTableRow<R, ?>> implements IEmfCommonAction<R> {

	@Override
	public IResource getIcon() {

		return EmfImages.REFRESH.getResource();
	}

	@Override
	public IDisplayString getTitle() {

		return EmfI18n.REFRESH;
	}

	@Override
	public IDisplayString getDescription() {

		return EmfI18n.RELOADS_THE_ENTRY_AND_REFRESHES_THE_FORM;
	}

	@Override
	public IEmfPredicate<R> getPrecondition() {

		return EmfPredicates.always();
	}

	@Override
	public IEmfRole<R> getAuthorizedRole() {

		return EmfRoles.anybody();
	}

	@Override
	public void handleClick(IEmfFormBody<R> formBody) {

		DbTableRowCaches.invalidateAll();
		formBody.queueEntityForRefresh();
	}
}
