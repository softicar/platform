package com.softicar.platform.emf.page;

import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.emf.module.IEmfModule;
import com.softicar.platform.emf.module.IEmfModuleInstance;

/**
 * Tests and casts {@link IEmfPage} instance for a given {@link IEmfModule}
 * class.
 *
 * @author Oliver Richers
 */
class EmfPageCaster<I extends IEmfModuleInstance<I>> {

	private final IEmfModule<I> module;

	public EmfPageCaster(IEmfModule<I> module) {

		this.module = module;
	}

	public boolean test(IEmfPage<?> page) {

		return page.getModuleClass().equals(module.getClass());
	}

	public IEmfPage<I> cast(IEmfPage<?> page) {

		return CastUtils.cast(page);
	}
}
