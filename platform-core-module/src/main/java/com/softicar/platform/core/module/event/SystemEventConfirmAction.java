package com.softicar.platform.core.module.event;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CorePermissions;
import com.softicar.platform.dom.elements.DomElementsImages;
import com.softicar.platform.emf.action.AbstractEmfButtonAction;
import com.softicar.platform.emf.action.IEmfManagementAction;
import com.softicar.platform.emf.form.IEmfFormBody;
import com.softicar.platform.emf.permission.IEmfPermission;
import com.softicar.platform.emf.predicate.IEmfPredicate;
import java.util.Optional;
import java.util.function.Supplier;

public class SystemEventConfirmAction extends AbstractEmfButtonAction<AGSystemEvent> implements IEmfManagementAction<AGSystemEvent> {

	@Override
	public IEmfPredicate<AGSystemEvent> getPrecondition() {

		return SystemEventPredicates.NEEDS_CONFIRMATION;
	}

	@Override
	public IEmfPermission<AGSystemEvent> getRequiredPermission() {

		return CorePermissions.SYSTEM_ADMINISTRATION.toOtherEntityPermission();
	}

	@Override
	public IResource getIcon() {

		return DomElementsImages.DIALOG_OKAY.getResource();
	}

	@Override
	public IDisplayString getTitle() {

		return CoreI18n.CONFIRMATION;
	}

	@Override
	public Optional<Supplier<IDisplayString>> getConfirmationMessageSupplier(AGSystemEvent object) {

		return Optional.of(() -> CoreI18n.ARE_YOU_SURE_QUESTION);
	}

	@Override
	public void handleClick(IEmfFormBody<AGSystemEvent> formBody) {

		handleClick(formBody.getTableRow());
	}

	@Override
	public void handleClick(AGSystemEvent tableRow) {

		tableRow//
			.setNeedsConfirmation(false)
			.save();
	}
}
