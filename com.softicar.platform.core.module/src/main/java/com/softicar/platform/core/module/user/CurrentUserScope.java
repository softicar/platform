package com.softicar.platform.core.module.user;

/**
 * An {@link AutoCloseable} scope to change the {@link CurrentUser} temporarily.
 * <p>
 * Only use this class if you know what you are doing. Its primary use case is
 * for unit test.
 *
 * @author Oliver Richers
 */
public class CurrentUserScope implements AutoCloseable {

	private final AGUser originalUser;

	public CurrentUserScope(AGUser user) {

		this.originalUser = CurrentUser.get();

		CurrentUser.set(user);
	}

	@Override
	public void close() {

		CurrentUser.set(originalUser);
	}
}
