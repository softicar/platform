package com.softicar.platform.core.module.user.password;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.emf.action.IEmfManagementAction;
import com.softicar.platform.emf.permission.EmfPermissions;
import com.softicar.platform.emf.permission.IEmfPermission;
import com.softicar.platform.emf.predicate.EmfPredicate;
import com.softicar.platform.emf.predicate.IEmfPredicate;
import java.util.Optional;
import java.util.function.Supplier;

public class UserResetPasswordAction implements IEmfManagementAction<AGUser> {

	@Override
	public IEmfPredicate<AGUser> getPrecondition() {

		return new EmfPredicate<>(CoreI18n.USER_IS_NOT_SYSTEM_USER, user -> !user.isSystemUser());
	}

	@Override
	public IEmfPermission<AGUser> getRequiredPermission() {

		/*
		 * TODO: change to CorePermissions.SUPER_USER.of(AGUser.MODULE_INSTANCE)
		 * once CoreModule is StandardModule and AGUser is scoped
		 */
		return EmfPermissions.always();
	}

	@Override
	public IResource getIcon() {

		return CoreImages.PASSWORD_RESET.getResource();
	}

	@Override
	public IDisplayString getTitle() {

		return CoreI18n.RESET_PASSWORD;
	}

	@Override
	public void handleClick(AGUser user) {

		new UserPasswordGenerator().resetUserPassword(user, true);
	}

	@Override
	public Optional<Supplier<IDisplayString>> getConfirmationMessageSupplier(AGUser user) {

		return Optional.of(() -> CoreI18n.ARE_YOU_SURE_QUESTION);
	}
}
