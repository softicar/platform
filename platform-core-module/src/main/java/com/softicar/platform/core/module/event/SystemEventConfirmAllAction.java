package com.softicar.platform.core.module.event;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreRoles;
import com.softicar.platform.core.module.module.instance.system.SystemModuleInstance;
import com.softicar.platform.dom.elements.DomElementsImages;
import com.softicar.platform.emf.action.IEmfScopeAction;
import com.softicar.platform.emf.authorization.role.IEmfRole;
import com.softicar.platform.emf.predicate.EmfPredicates;
import com.softicar.platform.emf.predicate.IEmfPredicate;
import java.util.Optional;
import java.util.function.Supplier;

public class SystemEventConfirmAllAction implements IEmfScopeAction<SystemModuleInstance> {

	@Override
	public IEmfPredicate<SystemModuleInstance> getPrecondition() {

		return EmfPredicates.always();
	}

	@Override
	public IEmfRole<SystemModuleInstance> getAuthorizedRole() {

		return CoreRoles.SYSTEM_ADMINISTRATOR;
	}

	@Override
	public IResource getIcon() {

		return DomElementsImages.DIALOG_OKAY.getResource();
	}

	@Override
	public IDisplayString getTitle() {

		return CoreI18n.CONFIRM_ALL;
	}

	@Override
	public Optional<Supplier<IDisplayString>> getConfirmationMessageSupplier(SystemModuleInstance object) {

		return Optional.of(() -> CoreI18n.ARE_YOU_SURE_QUESTION);
	}

	@Override
	public void handleClick(SystemModuleInstance scope) {

		AGSystemEvent.TABLE//
			.createUpdate()
			.set(AGSystemEvent.NEEDS_ATTENTION, false)
			.where(AGSystemEvent.NEEDS_ATTENTION)
			.execute();
	}
}
