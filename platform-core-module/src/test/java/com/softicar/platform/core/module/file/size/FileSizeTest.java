package com.softicar.platform.core.module.file.size;

import com.softicar.platform.common.core.utils.DevNull;
import org.junit.Assert;
import org.junit.Test;

/**
 * TODO: test compareTo, equals and hashCode?
 */
public class FileSizeTest extends Assert {

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorThrowsExceptionForNegativeValues() {

		DevNull.swallow(new FileSize(-1));
	}

	@Test
	public void testConstructorDefaultsToBytesScale() {

		FileSize fileSize = new FileSize(123);
		assertEquals(FileSizeScale.B, fileSize.getScale());
	}

	@Test
	public void testConstructorWithNullScaleDefaultToBytesScale() {

		FileSize fileSize = new FileSize(123, null);
		assertEquals(FileSizeScale.B, fileSize.getScale());
	}
}
