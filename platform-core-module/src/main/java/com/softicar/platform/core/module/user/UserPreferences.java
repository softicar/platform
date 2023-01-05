package com.softicar.platform.core.module.user;

public class UserPreferences {

	public boolean automaticallyCollapseFolders = false;
	public boolean recursivelyCollapseFolders = false;

	public boolean isAutomaticallyCollapseFolders() {

		return automaticallyCollapseFolders;
	}

	public boolean isRecursivelyCollapseFolders() {

		return recursivelyCollapseFolders;
	}

	public void setAutomaticallyCollapseFolders(boolean automaticallyCollapseFolders) {

		this.automaticallyCollapseFolders = automaticallyCollapseFolders;
	}

	public void setRecursivelyCollapseFolders(boolean recursivelyCollapseFolders) {

		this.recursivelyCollapseFolders = recursivelyCollapseFolders;
	}
}
