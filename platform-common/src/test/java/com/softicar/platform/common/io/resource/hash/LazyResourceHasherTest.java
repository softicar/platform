package com.softicar.platform.common.io.resource.hash;

import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.common.string.charset.Charsets;
import com.softicar.platform.common.string.hash.Hash;
import com.softicar.platform.common.testing.AbstractTest;
import java.io.ByteArrayInputStream;
import org.junit.Test;
import org.mockito.Mockito;

public class LazyResourceHasherTest extends AbstractTest {

	private static final String SAMPLE_DATA = "foo";
	private static final byte[] SAMPLE_DATA_BYTES = SAMPLE_DATA.getBytes(Charsets.UTF8);
	private static final ResourceHash SAMPLE_DATA_HASH = new ResourceHash(Hash.SHA1.getHashStringLC(SAMPLE_DATA));
	private final IResource resource;
	private final LazyResourceHasher resourceHasher;

	public LazyResourceHasherTest() {

		this.resource = Mockito.mock(IResource.class);
		this.resourceHasher = new LazyResourceHasher(resource);
	}

	@Test
	@SuppressWarnings("resource")
	public void testConstructor() {

		Mockito.when(resource.getResourceAsStream()).thenThrow(new AssertionError("Unexpected call!"));

		LazyResourceHasher dummy = new LazyResourceHasher(resource);
		DevNull.swallow(dummy);
	}

	@Test
	@SuppressWarnings("resource")
	public void testGetHash() {

		Mockito.when(resource.getResourceAsStream()).thenReturn(new ByteArrayInputStream(SAMPLE_DATA_BYTES));

		ResourceHash hash = resourceHasher.getHash();

		assertEquals(SAMPLE_DATA_HASH, hash);
	}

	@Test
	@SuppressWarnings("resource")
	public void testGetHashMultipleCalls() {

		Mockito.when(resource.getResourceAsStream()).thenReturn(new ByteArrayInputStream(SAMPLE_DATA_BYTES));

		resourceHasher.getHash();
		resourceHasher.getHash();
		resourceHasher.getHash();

		Mockito.verify(resource, Mockito.times(1)).getResourceAsStream();
	}
}
