package com.softicar.platform.common.code.title;

import org.junit.Assert;
import org.junit.Test;

public class TitleFromIdentifierDeterminerTest extends Assert {

	@Test
	public void test() {

		assertTitle("Cat", "Cats", "cat");
		assertTitle("Invoice", "Invoices", "invoice");
		assertTitle("Man", "Men", "man");

		assertTitle("Company Address", "Company Addresses", "CompanyAddress");
		assertTitle("Company Address Log", "Company Address Logs", "CompanyAddressLog");

		assertTitle("Invoice to Company Mapper", "Invoice to Company Mappers", "InvoiceToCompanyMapper");
		assertTitle("Invoice by Company Filter", "Invoice by Company Filters", "InvoiceByCompanyFilter");
	}

	@Test
	public void testWithDifferentSeparators() {

		assertTitle("Company Address", "Company Addresses", "CompanyAddress");
		assertTitle("Company Address", "Company Addresses", "company_address");
		assertTitle("Company Address", "Company Addresses", "Company-Address");
		assertTitle("Company Address", "Company Addresses", "Company Address");
	}

	@Test
	public void testWithNumbers() {

		assertTitle("Foo 1337", "Foo 1337s", "Foo1337");
		assertTitle("1 St Foo", "1 St Foos", "1stFoo");
	}

	@Test
	public void testWithCompoundWords() {

		assertTitle("Bill of Material", "Bill of Materials", "BillOfMaterial");
		assertTitle("Mother in Law", "Mother in Laws", "MotherInLaw");
	}

	private void assertTitle(String expectedTitle, String expectedPluralTitle, String identifier) {

		assertEquals(expectedTitle, new TitleFromIdentifierDeterminer(identifier).getTitle());
		assertEquals(expectedPluralTitle, new TitleFromIdentifierDeterminer(identifier).getPluralTitle());
	}
}
