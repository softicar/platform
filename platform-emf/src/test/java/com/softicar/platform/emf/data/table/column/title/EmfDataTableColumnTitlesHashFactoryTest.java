package com.softicar.platform.emf.data.table.column.title;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.testing.AbstractTest;
import java.util.List;
import org.junit.Test;

public class EmfDataTableColumnTitlesHashFactoryTest extends AbstractTest {

	private final EmfDataTableColumnTitlesHashFactory factory;

	public EmfDataTableColumnTitlesHashFactoryTest() {

		this.factory = new EmfDataTableColumnTitlesHashFactory();
	}

	@Test
	public void testCreateHashWithSingleColumn() {

		List<IDisplayString> titles = List.of(IDisplayString.create("foo"));
		String hash = factory.createHash(titles);
		assertEquals("0beec7b5ea3f0fdbc95d0dd47f3c5bc275da8a33", hash);
	}

	@Test
	public void testCreateHashWithMultipleColumns() {

		List<IDisplayString> titles = List
			.of(//
				IDisplayString.create("foo"),
				IDisplayString.create("bar"));
		String hash = factory.createHash(titles);
		assertEquals("46966271c3dcb19da2b2382cfa1563ee88ea050c", hash);
	}

	@Test
	public void testCreateHashWithEmptyList() {

		List<IDisplayString> titles = List.of();
		String hash = factory.createHash(titles);
		assertEquals("da39a3ee5e6b4b0d3255bfef95601890afd80709", hash);
	}

	@Test(expected = NullPointerException.class)
	public void testCreateHashWithNullList() {

		factory.createHash(null);
	}
}
