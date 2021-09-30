package com.softicar.platform.core.module.page.service;

import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.core.module.CoreProperties;
import com.softicar.platform.dom.resource.set.DomResourceSet;
import com.softicar.platform.dom.resource.set.loader.DomResourceSetFromFolderLoader;
import java.io.File;
import java.util.Objects;
import java.util.Optional;

class PageServiceDefaultThemeLoader {

	public Optional<DomResourceSet> load() {

		return Optional//
			.ofNullable(CoreProperties.DEFAULT_THEME.getValue())
			.filter(Objects::nonNull)
			.filter(PageServiceDefaultThemeLoader::isValidThemeFolder)
			.map(theme -> new DomResourceSetFromFolderLoader(theme).load());
	}

	private static boolean isValidThemeFolder(File folder) {

		if (folder.isDirectory()) {
			return true;
		} else {
			Log.ferror("Theme folder is not a directory or does not exist: '%s'", folder);
			return false;
		}
	}
}
