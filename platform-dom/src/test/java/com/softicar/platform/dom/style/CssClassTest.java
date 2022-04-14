package com.softicar.platform.dom.style;

import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;
import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.document.IDomDocument;
import com.softicar.platform.dom.engine.IDomEngine;
import org.junit.Test;
import org.mockito.Mockito;

public class CssClassTest extends AbstractTest {

	private static final String FOO = "foo";
	private final IDomDocument document;
	private final IDomEngine engine;
	private final IResource resource;
	private final IResourceSupplier resourceSupplier;
	private final CssClass cssClass;

	public CssClassTest() {

		this.document = Mockito.mock(IDomDocument.class);
		this.engine = Mockito.mock(IDomEngine.class);
		this.resource = Mockito.mock(IResource.class);
		this.resourceSupplier = Mockito.mock(IResourceSupplier.class);
		this.cssClass = new CssClass(FOO, resourceSupplier);

		Mockito.when(document.getEngine()).thenReturn(engine);
		Mockito.when(resourceSupplier.getResource()).thenReturn(resource);
	}

	@Test
	public void testBeforeUse() {

		CurrentDomDocument.set(document);

		cssClass.beforeUse();

		Mockito.verify(engine).registerCssResourceLink(resource);
	}

	@Test
	public void testGetName() {

		assertEquals(FOO, cssClass.getName());
	}

	@Test
	public void testToString() {

		assertEquals(FOO, cssClass.toString());
	}
}
