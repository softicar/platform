package com.softicar.platform.core.module;

import com.softicar.platform.common.code.reference.point.SourceCodeReferencePointUuid;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.core.module.module.AbstractModule;
import com.softicar.platform.core.module.module.instance.IModuleInstanceTable;
import com.softicar.platform.emf.page.EmfPagePath;

@SourceCodeReferencePointUuid("a8b076bd-582d-446d-9bce-85a8a180afd5")
public class CoreModule extends AbstractModule<AGCoreModuleInstance> {

	private static final IDisplayString PARENT_FOLDER_TITLE = CoreI18n.SYSTEM.encloseInBrackets();

	@Override
	public IResource getIcon() {

		return CoreImages.CORE_MODULE.getResource();
	}

	@Override
	public IDisplayString toDisplay() {

		return CoreI18n.CORE;
	}

	@Override
	public IModuleInstanceTable<AGCoreModuleInstance> getModuleInstanceTable() {

		return AGCoreModuleInstance.TABLE;
	}

	@Override
	public EmfPagePath getDefaultPagePath(AGCoreModuleInstance moduleInstance) {

		return getParentPagePath().append(toDisplay());
	}

	/**
	 * Returns the {@link EmfPagePath} of the artificial parent folder that
	 * contains sub-folders for {@link CoreModule} and potentially further
	 * modules.
	 *
	 * @return the parent folder as an {@link EmfPagePath} (never <i>null</i>)
	 */
	public static EmfPagePath getParentPagePath() {

		return new EmfPagePath().append(PARENT_FOLDER_TITLE);
	}
}
