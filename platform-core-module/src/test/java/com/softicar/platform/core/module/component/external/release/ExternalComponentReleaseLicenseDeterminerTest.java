package com.softicar.platform.core.module.component.external.release;

import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.core.module.component.external.key.ExternalComponentKey;
import com.softicar.platform.core.module.component.external.license.rule.ExternalComponentLicenseRule;
import com.softicar.platform.core.module.component.external.type.ExternalComponentType;
import com.softicar.platform.core.module.license.License;
import java.util.Arrays;
import java.util.Optional;
import org.junit.Test;

public class ExternalComponentReleaseLicenseDeterminerTest extends AbstractTest {

	private final ExternalComponentReleaseLicenseDeterminer determiner;

	public ExternalComponentReleaseLicenseDeterminerTest() {

		this.determiner = createDeterminer(//
			new ExternalComponentLicenseRule(ExternalComponentType.LIBRARY, "foo", ".*", License.APACHE_2_0),
			new ExternalComponentLicenseRule(ExternalComponentType.LIBRARY, "bar", "1\\..*", License.EPL_2_0),
			new ExternalComponentLicenseRule(ExternalComponentType.LIBRARY, "bar", "2\\..*", License.MIT));
	}

	@Test
	public void testWithWildcardVersion() {

		var release = createRelease("foo", "1.2.3");
		Optional<License> license = determiner.determineLicense(release);
		assertTrue(license.isPresent());
		assertEquals(License.APACHE_2_0, license.get());
	}

	@Test
	public void testWithLowerVersion() {

		var release = createRelease("bar", "1.2.0");
		Optional<License> license = determiner.determineLicense(release);
		assertTrue(license.isPresent());
		assertEquals(License.EPL_2_0, license.get());
	}

	@Test
	public void testWithHigherVersion() {

		var release = createRelease("bar", "2.0.0");
		Optional<License> license = determiner.determineLicense(release);
		assertTrue(license.isPresent());
		assertEquals(License.MIT, license.get());
	}

	@Test
	public void testWithoutMatch() {

		var release = createRelease("baz", "1.2.3");
		Optional<License> license = determiner.determineLicense(release);
		assertFalse(license.isPresent());
	}

	private ExternalComponentReleaseLicenseDeterminer createDeterminer(ExternalComponentLicenseRule...libraryLicenses) {

		return new ExternalComponentReleaseLicenseDeterminer(Arrays.asList(libraryLicenses));
	}

	private ExternalComponentRelease createRelease(String name, String version) {

		var key = new ExternalComponentKey(ExternalComponentType.LIBRARY, name);
		return new ExternalComponentRelease(key, version);
	}
}
