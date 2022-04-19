package com.softicar.platform.dom.resource.set;

import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.common.io.resource.key.IResourceKey;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;
import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.common.ui.font.Fonts;
import com.softicar.platform.dom.DomCssFiles;
import com.softicar.platform.dom.resource.supplier.DomResourceSupplierProxy;
import java.util.Optional;
import org.junit.Test;

public class DefaultDomResourceSetTest extends AbstractTest {

	private static final IResourceSupplier PROXY_BASED_EXAMPLE = DomCssFiles.DOM_STYLE;
	private static final IResourceSupplier NON_PROXY_BASED_EXAMPLE = Fonts.LIBERATION_MONO;

	@Test
	public void testGetResourceWithProxyBasedSupplier() {

		var resourceSupplier = assertProxyBased(PROXY_BASED_EXAMPLE);
		var resourceKey = resourceSupplier.getResourceKey();

		assertTrue(getResource(resourceKey).isPresent());
	}

	@Test
	public void testGetResourceWithNonProxyBasedSupplier() {

		var resourceSupplier = assertNonProxyBased(NON_PROXY_BASED_EXAMPLE);
		var resourceKey = resourceSupplier.getResourceKey();

		assertTrue(getResource(resourceKey).isPresent());
	}

	private Optional<IResource> getResource(IResourceKey resourceKey) {

		return DefaultDomResourceSet.getInstance().getResource(resourceKey);
	}

	private IResourceSupplier assertProxyBased(IResourceSupplier resourceSupplier) {

		return CastUtils//
			.tryCast(resourceSupplier, DomResourceSupplierProxy.class)
			.orElseThrow(AssertionError::new);
	}

	private IResourceSupplier assertNonProxyBased(IResourceSupplier resourceSupplier) {

		CastUtils//
			.tryCast(resourceSupplier, DomResourceSupplierProxy.class)
			.ifPresent(dummy -> {
				throw new AssertionError();
			});
		return resourceSupplier;
	}
}
