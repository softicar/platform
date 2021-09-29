package com.softicar.platform.emf.page;

import com.softicar.platform.emf.module.IEmfModule;
import com.softicar.platform.emf.module.IEmfModuleInstance;
import com.softicar.platform.emf.module.registry.CurrentEmfModuleRegistry;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePoints;
import java.util.Collection;
import java.util.stream.Collectors;

public class EmfPages {

	// ------------------------------ page to module ------------------------------ //

	/**
	 * Returns the {@link IEmfModule} of the given {@link IEmfPage}.
	 *
	 * @param page
	 *            the {@link IEmfPage} (never <i>null</i>)
	 * @return the respective {@link IEmfModule} (never <i>null</i>)
	 */
	public static <I extends IEmfModuleInstance<I>> IEmfModule<I> getModule(IEmfPage<I> page) {

		return CurrentEmfModuleRegistry.get().getModule(page.getModuleClass());
	}

	/**
	 * Returns the {@link IEmfModule} of the given {@link IEmfPage} class.
	 *
	 * @param pageClass
	 *            the {@link Class} of the {@link IEmfPage} (never <i>null</i>)
	 * @return the respective {@link IEmfModule} (never <i>null</i>)
	 */
	public static <I extends IEmfModuleInstance<I>> IEmfModule<I> getModule(Class<? extends IEmfPage<I>> pageClass) {

		return getModule(EmfSourceCodeReferencePoints.getReferencePoint(pageClass));
	}

	// ------------------------------ module to page ------------------------------ //

	/**
	 * Returns all {@link IEmfPage} instances of the given {@link IEmfModule}.
	 *
	 * @param module
	 *            the {@link IEmfModule} (never <i>null</i>)
	 * @return all matching {@link IEmfPage} instances
	 */
	public static <I extends IEmfModuleInstance<I>> Collection<IEmfPage<I>> getPages(IEmfModule<I> module) {

		EmfPageCaster<I> pageCaster = new EmfPageCaster<>(module);

		return EmfSourceCodeReferencePoints//
			.getReferencePoints(IEmfPage.class)
			.stream()
			.filter(pageCaster::test)
			.map(pageCaster::cast)
			.collect(Collectors.toList());
	}

	/**
	 * Returns all {@link IEmfPage} instances of the given {@link IEmfModule}
	 * class.
	 *
	 * @param moduleClass
	 *            the {@link Class} of the {@link IEmfModule} (never
	 *            <i>null</i>)
	 * @return all matching {@link IEmfPage} instances
	 */
	public static <I extends IEmfModuleInstance<I>> Collection<IEmfPage<I>> getPages(Class<? extends IEmfModule<I>> moduleClass) {

		return getPages(CurrentEmfModuleRegistry.get().getModule(moduleClass));
	}
}
