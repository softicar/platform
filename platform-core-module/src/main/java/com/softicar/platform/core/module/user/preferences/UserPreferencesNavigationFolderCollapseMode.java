package com.softicar.platform.core.module.user.preferences;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.i18n.IDisplayable;
import com.softicar.platform.core.module.CoreI18n;
import java.util.Objects;

/**
 * Enumerates navigation folder collapse modes that can be selected as a user
 * preference.
 * <p>
 * <b>WARNING:</b> Used in {@link UserPreferences}. Renaming constants in this
 * enum will partially invalidate stored user preferences.
 *
 * @author Alexander Schmidt
 */
public enum UserPreferencesNavigationFolderCollapseMode implements IDisplayable {

	AUTOMATIC(CoreI18n.AUTOMATIC, true, true),
	MANUAL(CoreI18n.MANUAL, false, false),
	MANUAL_RECURSIVE(CoreI18n.MANUAL_RECURSIVE, false, true),
	//
	;

	private final IDisplayString displayName;
	private final boolean automatic;
	private final boolean recursive;

	private UserPreferencesNavigationFolderCollapseMode(IDisplayString displayName, boolean automatic, boolean recursive) {

		this.automatic = automatic;
		this.recursive = recursive;
		this.displayName = Objects.requireNonNull(displayName);
	}

	@Override
	public IDisplayString toDisplay() {

		return displayName;
	}

	/**
	 * Determines whether this mode implies automatic collapsing.
	 *
	 * @return <i>true</i> if this mode implies automatic collapsing;
	 *         <i>false</i> otherwise
	 */
	public boolean isAutomatic() {

		return automatic;
	}

	/**
	 * Determines whether this mode implies recursive collapsing.
	 *
	 * @return <i>true</i> if this mode implies recursive collapsing;
	 *         <i>false</i> otherwise
	 */
	public boolean isRecursive() {

		return recursive;
	}
}
