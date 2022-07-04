package com.softicar.platform.core.module.email.buffer;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.core.module.CorePermissions;
import com.softicar.platform.db.core.transaction.DbTransaction;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.EmfImages;
import com.softicar.platform.emf.action.IEmfManagementAction;
import com.softicar.platform.emf.permission.IEmfPermission;
import com.softicar.platform.emf.predicate.EmfPredicate;
import com.softicar.platform.emf.predicate.IEmfPredicate;

public class BufferedEmailDeactivateAction implements IEmfManagementAction<AGBufferedEmail> {

	@Override
	public IEmfPredicate<AGBufferedEmail> getPrecondition() {

		return new EmfPredicate<>(EmfI18n.ACTIVE, AGBufferedEmail::isActive);
	}

	@Override
	public IEmfPermission<AGBufferedEmail> getRequiredPermission() {

		return CorePermissions.SUPER_USER.toOtherEntityPermission();
	}

	@Override
	public IResource getIcon() {

		return EmfImages.ENTITY_DEACTIVATE.getResource();
	}

	@Override
	public IDisplayString getTitle() {

		return EmfI18n.DEACTIVATE;
	}

	@Override
	public void handleClick(AGBufferedEmail tableRow) {

		try (var transaction = new DbTransaction()) {
			tableRow.setActive(false).save();
			transaction.commit();
		}
	}
}
