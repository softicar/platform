package com.softicar.platform.dom.resource.preprocessed;

import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.common.io.resource.IResourceUrl;
import com.softicar.platform.common.io.resource.ResourceUrl;
import com.softicar.platform.common.io.resource.key.ResourceKey;
import com.softicar.platform.common.testing.Asserts;
import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.document.DomDocument;
import com.softicar.platform.dom.engine.DomNullEngine;
import com.softicar.platform.dom.resource.set.DomBasicResourceSet;
import java.util.Optional;
import org.junit.Test;
import org.mockito.Mockito;

public class DomCssPreprocessorResourceUrlFactoryTest extends Asserts {

	private final DomCssPreprocessorResourceUrlFactory factory;

	public DomCssPreprocessorResourceUrlFactoryTest() {

		CurrentDomDocument.set(new DomDocument(new ResourceUrlTestEngine()));

		var resourceSet = new TestResourceSet()//
			.putMocked("path.to", "Some-font.ttf");

		this.factory = new DomCssPreprocessorResourceUrlFactory(resourceSet);
	}

	@Test
	public void testCreate() {

		assertEquals("/url/to/Some-font.ttf", factory.create("/path/to/Some-font.ttf"));
	}

	@Test(expected = DomCssPreprocessorException.class)
	public void testPreprocessWithInvalidResourcePath() {

		factory.create("/nowhere/nothing.ttf");
	}

	private static class ResourceUrlTestEngine extends DomNullEngine {

		@Override
		public IResourceUrl getResourceUrl(IResource resource) {

			return new ResourceUrl("/url/to/" + resource.getFilename().get());
		}
	}

	private static class TestResourceSet extends DomBasicResourceSet {

		public TestResourceSet putMocked(String packageName, String filename) {

			put(new ResourceKey(packageName, filename), mockFileResource(filename));
			return this;
		}

		private IResource mockFileResource(String filename) {

			IResource resource = Mockito.mock(IResource.class);
			Mockito.when(resource.getFilename()).thenReturn(Optional.of(filename));
			return resource;
		}
	}
}
