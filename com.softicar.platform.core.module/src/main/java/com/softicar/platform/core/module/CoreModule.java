package com.softicar.platform.core.module;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.core.module.file.smb.jcifs.JcifsSmbFileUtils;
import com.softicar.platform.core.module.module.AbstractSystemModule;
import com.softicar.platform.core.module.module.instance.system.SystemModuleInstance;
import com.softicar.platform.emf.page.EmfPagePath;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePointUuid;

@EmfSourceCodeReferencePointUuid("a8b076bd-582d-446d-9bce-85a8a180afd5")
public class CoreModule extends AbstractSystemModule {

	private static final IDisplayString PARENT_FOLDER_TITLE = CoreI18n.SYSTEM.encloseInBrackets();
	private static final SystemModuleInstance MODULE_INSTANCE = new SystemModuleInstance(CoreModule.class);

	@Override
	public SystemModuleInstance getSystemModuleInstance() {

		return getModuleInstance();
	}

	public static SystemModuleInstance getModuleInstance() {

		return MODULE_INSTANCE;
	}

	@Override
	public IResource getIcon() {

		return CoreImages.CORE_MODULE.getResource();
	}

	public CoreModule() {

		// FIXME vvvv explain why. don't make obscure claims. digging out #38419 should not be necessary to understand
		// the reason. vvvv
		// this is very important (see #38419)
		JcifsSmbFileUtils.disableDfs();
	}

	@Override
	public IDisplayString toDisplay() {

		return CoreI18n.CORE;
	}

	@Override
	public EmfPagePath getDefaultPagePath(SystemModuleInstance moduleInstance) {

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
