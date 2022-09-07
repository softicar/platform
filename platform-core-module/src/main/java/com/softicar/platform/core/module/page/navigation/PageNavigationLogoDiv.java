package com.softicar.platform.core.module.page.navigation;

import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.CoreCssClasses;
import com.softicar.platform.core.module.file.stored.AGStoredFile;
import com.softicar.platform.core.module.file.stored.StoredFileResource;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomImage;
import java.util.Optional;

public class PageNavigationLogoDiv extends DomDiv {

	public PageNavigationLogoDiv() {

		setCssClass(CoreCssClasses.PAGE_NAVIGATION_LOGO_DIV);

		Optional//
			.ofNullable(AGCoreModuleInstance.getInstance().getPortalLogo())
			.ifPresent(this::appendLogo);
	}

	private void appendLogo(AGStoredFile logoFile) {

		appendChild(new DomImage(new StoredFileResource(logoFile)));
	}
}
