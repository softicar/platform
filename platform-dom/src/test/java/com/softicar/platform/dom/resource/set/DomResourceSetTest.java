package com.softicar.platform.dom.resource.set;

import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.common.io.resource.key.IResourceKey;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;
import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.common.ui.font.Fonts;
import com.softicar.platform.dom.DomCssFiles;
import com.softicar.platform.dom.resource.preprocessed.DomPreprocessedCssResource;
import java.util.Optional;
import org.junit.Test;

public class DomResourceSetTest extends AbstractTest {

	private static final IResourceSupplier DEFAULT_RESOURCE_EXAMPLE = Fonts.RUBIK_VARIABLE_WEIGHT;
	private static final IResourceSupplier CSS_TEMPLATE_DEFAULT_RESOURCE_EXAMPLE = DomCssFiles.DOM_STYLE;
	private final DomResourceSet resourceSet;

	public DomResourceSetTest() {

		this.resourceSet = new DomResourceSet();
	}

	@Test
	public void testGetResourceWithDefaultResource() {

		IResourceKey resourceKey = DEFAULT_RESOURCE_EXAMPLE.getResourceKey();
		Optional<IResource> resource = resourceSet.getResource(resourceKey);

		assertTrue(resource.isPresent());
		assertFalse(DomPreprocessedCssResource.class.isAssignableFrom(resource.get().getClass()));
	}

	@Test
	public void testGetResourceWithCssTemplateDefaultResource() {

		IResourceKey resourceKey = CSS_TEMPLATE_DEFAULT_RESOURCE_EXAMPLE.getResourceKey();
		Optional<IResource> resource = resourceSet.getResource(resourceKey);

		assertTrue(resource.isPresent());
		assertTrue(DomPreprocessedCssResource.class.isAssignableFrom(resource.get().getClass()));
	}
}
