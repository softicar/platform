package com.softicar.platform.emf.action;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.permission.IEmfPermission;
import com.softicar.platform.emf.predicate.IEmfPredicate;
import java.util.Optional;
import java.util.function.Supplier;

public interface IEmfAction<T> {

	/**
	 * Returns the necessary precondition for this action to be available.
	 *
	 * @return the precondition (never null)
	 */
	IEmfPredicate<T> getPrecondition();

	/**
	 * Returns the necessary {@link IEmfPermission} for this action to be
	 * available.
	 *
	 * @return the {@link IEmfPermission} (never null)
	 */
	IEmfPermission<T> getRequiredPermission();

	/**
	 * Returns the icon of this action.
	 *
	 * @return the icon (never null)
	 */
	IResource getIcon();

	/**
	 * Returns the title of this action.
	 *
	 * @return the title (never null)
	 */
	IDisplayString getTitle();

	/**
	 * Returns whether this action is available for given object and user.
	 *
	 * @param object
	 *            the object (never null)
	 * @param user
	 *            the user (never null)
	 * @return <i>true</i> if this action is available, <i>false</i> otherwise
	 */
	default boolean isAvailable(T object, IBasicUser user) {

		return getPrecondition().test(object) && getRequiredPermission().test(object, user);
	}

	/**
	 * Returns a textual description containing what the action does.
	 * <p>
	 * Description will be empty unless defined otherwise.
	 *
	 * @return the description (never null)
	 */
	default IDisplayString getDescription() {

		return IDisplayString.EMPTY;
	}

	/**
	 * Verifies that this action is available for given object and user.
	 *
	 * @param object
	 *            the object (never null)
	 * @param user
	 *            the user (never null)
	 * @throws SofticarUserException
	 *             if this action is not available
	 */
	default void assertAvailability(T object, IBasicUser user) {

		if (!getPrecondition().test(object)) {
			throw new SofticarUserException(
				EmfI18n.INVALID_PRECONDITION//
					.concatColon()
					.concatSentence(EmfI18n.ARG1_DOES_NOT_APPLY.toDisplay(getPrecondition().getTitle())));
		}

		if (!getRequiredPermission().test(object, user)) {
			throw new SofticarUserException(
				EmfI18n.MISSING_AUTHORIZATION//
					.concatColon()
					.concatSentence(EmfI18n.THE_CURRENT_USER_DOES_NOT_POSSESS_THE_PERMISSION_ARG1.toDisplay(getRequiredPermission().getTitle())));
		}
	}

	default Optional<Supplier<IDisplayString>> getConfirmationMessageSupplier(T object) {

		DevNull.swallow(object);
		return Optional.empty();
	}
}
