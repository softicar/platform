package com.softicar.platform.core.module.user;

/**
 * Provides user specific preferences.
 * <p>
 * <b>WARNING:</b> Instances are serialized and un-serialized via Gson. Renaming
 * fields in here will partially invalidate stored user preferences.
 *
 * @author Alexander Schmidt
 */
public class UserPreferences {

	public boolean automaticallyCollapseFolders = false;
	public boolean recursivelyCollapseFolders = false;
}
