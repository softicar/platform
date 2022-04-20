package com.softicar.platform.emf.data.table;

import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.common.testing.Asserts;
import org.junit.Test;

public class EmfDataTableTestUtilTest extends AbstractTest {

	@Test
	public void testCalculateTotalPageCount() {

		Asserts.assertException(Exception.class, () -> EmfDataTableTestUtil.calculateTotalPageCount(-1, 1));
		Asserts.assertException(Exception.class, () -> EmfDataTableTestUtil.calculateTotalPageCount(0, 0));
		Asserts.assertException(Exception.class, () -> EmfDataTableTestUtil.calculateTotalPageCount(0, -1));
		assertEquals(0, EmfDataTableTestUtil.calculateTotalPageCount(0, 18));
		assertEquals(1, EmfDataTableTestUtil.calculateTotalPageCount(1, 18));
		assertEquals(1, EmfDataTableTestUtil.calculateTotalPageCount(17, 18));
		assertEquals(1, EmfDataTableTestUtil.calculateTotalPageCount(18, 18));
		assertEquals(2, EmfDataTableTestUtil.calculateTotalPageCount(19, 18));
		assertEquals(2, EmfDataTableTestUtil.calculateTotalPageCount(35, 18));
		assertEquals(2, EmfDataTableTestUtil.calculateTotalPageCount(36, 18));
		assertEquals(3, EmfDataTableTestUtil.calculateTotalPageCount(37, 18));
		assertEquals(4, EmfDataTableTestUtil.calculateTotalPageCount(4, 1));
	}

	@Test
	public void testCalculateNumberOfRowsOnLastPage() {

		Asserts.assertException(Exception.class, () -> EmfDataTableTestUtil.calculateNumberOfRowsOnLastPage(-1, 1));
		Asserts.assertException(Exception.class, () -> EmfDataTableTestUtil.calculateNumberOfRowsOnLastPage(0, 0));
		Asserts.assertException(Exception.class, () -> EmfDataTableTestUtil.calculateNumberOfRowsOnLastPage(0, -1));
		assertEquals(0, EmfDataTableTestUtil.calculateNumberOfRowsOnLastPage(0, 18));
		assertEquals(1, EmfDataTableTestUtil.calculateNumberOfRowsOnLastPage(1, 18));
		assertEquals(17, EmfDataTableTestUtil.calculateNumberOfRowsOnLastPage(17, 18));
		assertEquals(18, EmfDataTableTestUtil.calculateNumberOfRowsOnLastPage(18, 18));
		assertEquals(1, EmfDataTableTestUtil.calculateNumberOfRowsOnLastPage(19, 18));
		assertEquals(17, EmfDataTableTestUtil.calculateNumberOfRowsOnLastPage(35, 18));
		assertEquals(18, EmfDataTableTestUtil.calculateNumberOfRowsOnLastPage(36, 18));
		assertEquals(1, EmfDataTableTestUtil.calculateNumberOfRowsOnLastPage(37, 18));
	}

	@Test
	public void testCalculateRowCountOnPage() {

		Asserts.assertException(Exception.class, () -> EmfDataTableTestUtil.calculateRowCountOnPage(2, 2, 1));
		assertEquals(1, EmfDataTableTestUtil.calculateRowCountOnPage(0, 1, 18));
		assertEquals(17, EmfDataTableTestUtil.calculateRowCountOnPage(0, 17, 18));
		assertEquals(18, EmfDataTableTestUtil.calculateRowCountOnPage(0, 18, 18));
		assertEquals(18, EmfDataTableTestUtil.calculateRowCountOnPage(0, 19, 18));
		assertEquals(1, EmfDataTableTestUtil.calculateRowCountOnPage(1, 19, 18));
		assertEquals(18, EmfDataTableTestUtil.calculateRowCountOnPage(1, 36, 18));
		assertEquals(18, EmfDataTableTestUtil.calculateRowCountOnPage(1, 37, 18));
		assertEquals(1, EmfDataTableTestUtil.calculateRowCountOnPage(2, 37, 18));
	}
}
