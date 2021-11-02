package com.softicar.platform.core.module.program.execution.scheduled.information;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.module.instance.system.SystemModuleInstance;
import com.softicar.platform.dom.elements.DomElementsImages;
import com.softicar.platform.dom.elements.popup.manager.DomPopupManager;
import com.softicar.platform.emf.action.IEmfScopeAction;
import com.softicar.platform.emf.authorization.role.EmfRoles;
import com.softicar.platform.emf.authorization.role.IEmfRole;
import com.softicar.platform.emf.predicate.EmfPredicates;
import com.softicar.platform.emf.predicate.IEmfPredicate;

public class CronExpressionInformationAction implements IEmfScopeAction<SystemModuleInstance> {

	@Override
	public IEmfPredicate<SystemModuleInstance> getPrecondition() {

		return EmfPredicates.always();
	}

	@Override
	public IEmfRole<SystemModuleInstance> getAuthorizedRole() {

		return EmfRoles.anybody();
	}

	@Override
	public IResource getIcon() {

		return DomElementsImages.HELP.getResource();
	}

	@Override
	public IDisplayString getTitle() {

		return CoreI18n.CRON_SYNTAX;
	}

	@Override
	public void handleClick(SystemModuleInstance scope) {

		DomPopupManager//
			.getInstance()
			.getPopup(scope, CronSyntaxPopup.class, it -> new CronSyntaxPopup())
			.show();
	}
}
