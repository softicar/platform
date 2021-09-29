package com.softicar.platform.core.module.license;

import java.util.Objects;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Test;

public class LicenseDeterminerTest extends Assert {

	private License expectedLicense;

	public LicenseDeterminerTest() {

		this.expectedLicense = null;
	}

	@Test
	public void testDetermineFromTextWithWithApache20() {

		this.expectedLicense = License.APACHE_2_0;

		assertDetermined("""
				...
				Apache License
				Version 2.0...
				...
				""");
		assertDetermined("Apache 2.0");
		assertDetermined("The Apache Software License, Version 2.0");
		assertDetermined("Apache License, Version 2.0");
		assertDetermined("apache.org/licenses/LICENSE-2.0");
	}

	@Test
	public void testDetermineFromTextWithWithBsd3Clause() {

		this.expectedLicense = License.BSD_3_CLAUSE;

		assertDetermined("Revised BSD License");
		assertDetermined("New BSD License");
		assertDetermined("Modified BSD License");
		assertDetermined("BSD-3-Clause...");
	}

	@Test
	public void testDetermineFromTextWithWithBsd4Clause() {

		this.expectedLicense = License.BSD_4_CLAUSE;

		assertDetermined("""
				...
				... BSD License ...
				...
				""");
		assertDetermined("BSD License");
	}

	@Test
	public void testDetermineFromTextWithWithCddl10() {

		this.expectedLicense = License.CDDL_1_0;

		assertDetermined("""
				...
				... COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL) Version 1.0 ...
				...
				""");
	}

	@Test
	public void testDetermineFromTextWithWithEclipse10() {

		this.expectedLicense = License.EPL_1_0;

		assertDetermined("""
				...
				... Eclipse Public License - v 1.0 ...
				...
				""");
		assertDetermined("eclipse.org/legal/epl-1.0");
	}

	@Test
	public void testDetermineFromTextWithWithEclipse20() {

		this.expectedLicense = License.EPL_2_0;

		assertDetermined("""
				...
				... Eclipse Public License - v 2.0 ...
				...
				""");
		assertDetermined("eclipse.org/legal/epl-2.0");
	}

	@Test
	public void testDetermineFromTextWithWithLgpl21() {

		this.expectedLicense = License.LGPL_2_1;

		assertDetermined("GNU Lesser General Public License, version 2.1");
	}

	@Test
	public void testDetermineFromTextWithWithMit() {

		this.expectedLicense = License.MIT;

		assertDetermined("""
				...
				... The MIT License (MIT) ...
				...
				""");
		assertDetermined("MIT License");
	}

	@Test
	public void testDetermineFromTextWithWithEmptyText() {

		Optional<License> license = determineFromText("");
		assertFalse(license.isPresent());
	}

	@Test(expected = NullPointerException.class)
	public void testDetermineFromTextWithWithNullText() {

		determineFromText(null);
	}

	private Optional<License> determineFromText(String licenseText) {

		return new LicenseDeterminer().determineFromText(licenseText);
	}

	private void assertDetermined(String licenseText) {

		Objects.requireNonNull(expectedLicense);
		Optional<License> license = determineFromText(licenseText);
		assertTrue(license.isPresent());
		assertEquals(expectedLicense, license.get());
	}
}
