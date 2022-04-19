package com.softicar.platform.common.string.plural;

import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Test;

public class PluralDeterminerTest extends AbstractTest {

	@Test
	public void testWithNormalCases() {

		assertPlural("houses", "house");
		assertPlural("mouses", "mouse");
		assertPlural("papers", "paper");
	}

	@Test
	public void testWithEndingY() {

		assertPlural("boys", "boy");
		assertPlural("holidays", "holiday");

		assertPlural("tries", "try");
		assertPlural("batteries", "battery");
	}

	@Test
	public void testWithEndingO() {

		assertPlural("photos", "photo");
		assertPlural("pros", "pro");
		assertPlural("vulcanos", "vulcano");
		assertPlural("zeros", "zero");

		assertPlural("heroes", "hero");
	}

	@Test
	public void testWithEndingCh() {

		assertPlural("switches", "switch");
		assertPlural("witches", "witch");
	}

	@Test
	public void testWithEndingSh() {

		assertPlural("dishes", "dish");
		assertPlural("wishes", "wish");
	}

	@Test
	public void testWithEndingS() {

		assertPlural("addresses", "address");
		assertPlural("kisses", "kiss");
	}

	@Test
	public void testWithSpecialCases() {

		assertPlural("children", "child");
		assertPlural("leaves", "leaf");
		assertPlural("lives", "life");
		assertPlural("men", "man");
		assertPlural("women", "woman");
	}

	private void assertPlural(String expectedPlural, String sigular) {

		assertEquals(expectedPlural, new PluralDeterminer(sigular).getPlural());
	}
}
