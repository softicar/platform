package com.softicar.platform.emf.page;

import com.softicar.platform.common.container.iterable.Iterables;
import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.emf.module.IEmfModule;
import com.softicar.platform.emf.module.registry.CurrentEmfModuleRegistry;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePoints;
import com.softicar.platform.emf.test.module.EmfTestModule;
import com.softicar.platform.emf.test.module.EmfTestModuleInstance;
import java.util.Collection;
import org.junit.Test;

public class EmfPagesTest extends AbstractTest {

	private final EmfTestModule testModule;
	private final EmfTestPage testPage;

	public EmfPagesTest() {

		this.testModule = CurrentEmfModuleRegistry.get().getModule(EmfTestModule.class);
		this.testPage = EmfSourceCodeReferencePoints.getReferencePoint(EmfTestPage.class);
	}

	@Test
	public void testGetModuleWithPage() {

		IEmfModule<EmfTestModuleInstance> module = EmfPages.getModule(testPage);

		assertSame(testModule, module);
	}

	@Test
	public void testGetModuleWithPageClass() {

		IEmfModule<EmfTestModuleInstance> module = EmfPages.getModule(EmfTestPage.class);

		assertSame(testModule, module);
	}

	@Test
	public void testGetPagesWithModule() {

		Collection<IEmfPage<EmfTestModuleInstance>> pages = EmfPages.getPages(EmfTestModule.class);

		assertEquals(1, pages.size());
		assertSame(testPage, Iterables.getFirst(pages));
	}

	@Test
	public void testGetPagesWithModuleClass() {

		Collection<IEmfPage<EmfTestModuleInstance>> pages = EmfPages.getPages(testModule);

		assertEquals(1, pages.size());
		assertSame(testPage, Iterables.getFirst(pages));
	}
}
