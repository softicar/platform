package com.softicar.platform.core.module.user.preferences;

import com.google.gson.Gson;
import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.core.module.user.AGUser;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Facilitates loading and saving {@link UserPreferences}.
 *
 * @author Alexander Schmidt
 */
public class UserPreferencesManager {

	private final AGUser user;

	/**
	 * Constructs a new {@link UserPreferencesManager}
	 *
	 * @param user
	 *            the user to manage {@link UserPreferences} for (never
	 *            <i>null</i>)
	 */
	public UserPreferencesManager(AGUser user) {

		this.user = Objects.requireNonNull(user);
	}

	/**
	 * Derives the current {@link UserPreferences} from the current value of
	 * {@link AGUser#PREFERENCES_JSON}.
	 * <p>
	 * If the processed JSON value is empty or invalid, a
	 * {@link UserPreferences} instance with default values is returned.
	 *
	 * @return the {@link UserPreferences} (never <i>null</i>)
	 */
	public UserPreferences getPreferences() {

		return getPreferencesFromJson().orElse(new UserPreferences());
	}

	/**
	 * Saves the given {@link UserPreferences} for this {@link AGUser}.
	 *
	 * @param preferences
	 *            the {@link UserPreferences} to save (never <i>null</i>)
	 */
	public void savePreferences(UserPreferences preferences) {

		Objects.requireNonNull(preferences);
		String preferencesJson = new Gson().toJson(preferences);
		user.setPreferencesJson(preferencesJson).save();
	}

	/**
	 * Fetches the current {@link UserPreferences}, modifies them with the given
	 * {@link Consumer}, and saves the result.
	 *
	 * @param preferencesModifier
	 *            modifies the loaded {@link UserPreferences} (never
	 *            <i>null</i>)
	 */
	public void updatePreferences(Consumer<UserPreferences> preferencesModifier) {

		var preferences = getPreferences();
		preferencesModifier.accept(preferences);
		savePreferences(preferences);
	}

	private Optional<UserPreferences> getPreferencesFromJson() {

		try {
			return Optional.ofNullable(new Gson().fromJson(user.getPreferencesJson(), UserPreferences.class));
		} catch (Exception exception) {
			DevNull.swallow(exception);
			Log.ferror("Failed to retrieve preferences for user '%s'.", user.toDisplay());
			return Optional.empty();
		}
	}
}
