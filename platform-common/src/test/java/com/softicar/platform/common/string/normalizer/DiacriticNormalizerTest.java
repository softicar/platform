package com.softicar.platform.common.string.normalizer;

import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Test;

/**
 * For required test cases, see https://en.wikipedia.org/wiki/Diacritic
 */
public class DiacriticNormalizerTest extends AbstractTest {

	/**
	 * https://en.wikipedia.org/wiki/Acute_accent
	 */
	@Test
	public void testAccentAcuteRemoval() {

		String input = "áÁć";
		String output = new DiacriticNormalizer().normalize(input);
		assertEquals("aAc", output);
	}

	/**
	 * https://en.wikipedia.org/wiki/Double_acute_accent
	 */
	@Test
	public void testAccentDoubleAcuteRemoval() {

		String input = "e̋E̋";
		String output = new DiacriticNormalizer().normalize(input);
		assertEquals("eE", output);
	}

	/**
	 * https://en.wikipedia.org/wiki/Grave_accent
	 */
	@Test
	public void testAccentGraveRemoval() {

		String input = "èÈ";
		String output = new DiacriticNormalizer().normalize(input);
		assertEquals("eE", output);
	}

	/**
	 * https://en.wikipedia.org/wiki/Double_grave_accent
	 */
	@Test
	public void testAccentDoubleGraveRemoval() {

		String input = "ȉȈ";
		String output = new DiacriticNormalizer().normalize(input);
		assertEquals("iI", output);
	}

	/**
	 * https://en.wikipedia.org/wiki/Breve
	 */
	@Test
	public void testBreveRemoval() {

		String input = "ăĂ";
		String output = new DiacriticNormalizer().normalize(input);
		assertEquals("aA", output);
	}

	/**
	 * https://en.wikipedia.org/wiki/Inverted_breve
	 */
	@Test
	public void testInvertedBreveRemoval() {

		String input = "ȓȒ";
		String output = new DiacriticNormalizer().normalize(input);
		assertEquals("rR", output);
	}

	/**
	 * https://en.wikipedia.org/wiki/Caron
	 */
	@Test
	public void testCaronRemoval() {

		String input = "čČšž";
		String output = new DiacriticNormalizer().normalize(input);
		assertEquals("cCsz", output);
	}

	/**
	 * https://en.wikipedia.org/wiki/Cedilla
	 */
	@Test
	public void testCedillaRemoval() {

		String input = "çÇ";
		String output = new DiacriticNormalizer().normalize(input);
		assertEquals("cC", output);
	}

	/**
	 * https://en.wikipedia.org/wiki/Circumflex
	 */
	@Test
	public void testCircumflexRemoval() {

		String input = "ôÔ";
		String output = new DiacriticNormalizer().normalize(input);
		assertEquals("oO", output);
	}

	/**
	 * https://en.wikipedia.org/wiki/Diaeresis_%28diacritic%29
	 */
	@Test
	public void testDiaeresisRemoval() {

		String input = "äÄ";
		String output = new DiacriticNormalizer().normalize(input);
		assertEquals("aA", output);
	}

	/**
	 * https://en.wikipedia.org/wiki/Dot_%28diacritic%29
	 */
	@Test
	public void testDotRemoval() {

		String input = "ḍḌėĖ";
		String output = new DiacriticNormalizer().normalize(input);
		assertEquals("dDeE", output);
	}

	/**
	 * https://en.wikipedia.org/wiki/Hook_above
	 */
	@Test
	public void testHookAboveRemoval() {

		String input = "ảẢ";
		String output = new DiacriticNormalizer().normalize(input);
		assertEquals("aA", output);
	}

	/**
	 * https://en.wikipedia.org/wiki/Horn_%28diacritic%29
	 */
	@Test
	public void testHornRemoval() {

		String input = "ơƠ";
		String output = new DiacriticNormalizer().normalize(input);
		assertEquals("oO", output);
	}

	/**
	 * https://en.wikipedia.org/wiki/Iota_subscript
	 */
	@Test
	public void testIoaSubscriptRemoval() {

		String input = "ῃῳᾳ";
		String output = new DiacriticNormalizer().normalize(input);
		assertEquals("ηωα", output);
	}

	/**
	 * https://en.wikipedia.org/wiki/Horn_%28diacritic%29
	 */
	@Test
	public void testMacronRemoval() {

		String input = "āĀḏḎ";
		String output = new DiacriticNormalizer().normalize(input);
		assertEquals("aAdD", output);
	}

	/**
	 * https://en.wikipedia.org/wiki/Ogonek
	 */
	@Test
	public void testOgonekRemoval() {

		String input = "ąĄ";
		String output = new DiacriticNormalizer().normalize(input);
		assertEquals("aA", output);
	}

	/**
	 * https://en.wikipedia.org/wiki/Circumflex
	 */
	@Test
	public void testPerispomeneRemoval() {

		String input = "ễỄ";
		String output = new DiacriticNormalizer().normalize(input);
		assertEquals("eE", output);
	}

	/**
	 * https://en.wikipedia.org/wiki/Ring_%28diacritic%29
	 */
	@Test
	public void testRingRemoval() {

		String input = "åÅs̥S̥";
		String output = new DiacriticNormalizer().normalize(input);
		assertEquals("aAsS", output);
	}

	/**
	 * https://en.wikipedia.org/wiki/Rough_breathing
	 */
	@Test
	public void testRoughBreathingRemoval() {

		String input = "t̔";
		String output = new DiacriticNormalizer().normalize(input);
		assertEquals("t", output);
	}

	/**
	 * https://en.wikipedia.org/wiki/Smooth_breathing
	 */
	@Test
	public void testSmoothBreathingRemoval() {

		String input = "ἀὠ";
		String output = new DiacriticNormalizer().normalize(input);
		assertEquals("αω", output);
	}
}
