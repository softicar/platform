package com.softicar.platform.core.module.event;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CorePermissions;
import com.softicar.platform.dom.DomImages;
import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.emf.action.IEmfScopeAction;
import com.softicar.platform.emf.permission.IEmfPermission;
import com.softicar.platform.emf.predicate.EmfPredicates;
import com.softicar.platform.emf.predicate.IEmfPredicate;
import java.util.Optional;
import java.util.function.Supplier;

public class SystemEventConfirmAllAction implements IEmfScopeAction<AGCoreModuleInstance> {

	@Override
	public IEmfPredicate<AGCoreModuleInstance> getPrecondition() {

		return EmfPredicates.always();
	}

	@Override
	public IEmfPermission<AGCoreModuleInstance> getRequiredPermission() {

		return CorePermissions.ADMINISTRATION;
	}

	@Override
	public IResource getIcon() {

		return DomImages.DIALOG_OKAY.getResource();
	}

	@Override
	public IDisplayString getTitle() {

		return CoreI18n.CONFIRM_ALL;
	}

	@Override
	public Optional<Supplier<IDisplayString>> getConfirmationMessageSupplier(AGCoreModuleInstance object) {

		return Optional.of(() -> CoreI18n.ARE_YOU_SURE_QUESTION);
	}

	@Override
	public void handleClick(AGCoreModuleInstance scope) {

		AGSystemEvent.TABLE//
			.createUpdate()
			.set(AGSystemEvent.NEEDS_CONFIRMATION, false)
			.where(AGSystemEvent.NEEDS_CONFIRMATION)
			.execute();
		CurrentDomDocument.get().getRefreshBus().setAllChanged(AGSystemEvent.class);
	}
}
