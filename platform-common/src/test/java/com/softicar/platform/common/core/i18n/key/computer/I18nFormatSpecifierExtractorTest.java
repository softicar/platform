package com.softicar.platform.common.core.i18n.key.computer;

import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Test;

public class I18nFormatSpecifierExtractorTest extends AbstractTest {

	private final I18nFormatSpecifierExtractor extractor;

	public I18nFormatSpecifierExtractorTest() {

		this.extractor = new I18nFormatSpecifierExtractor();
	}

	// ---------------- No Wild-Cards ---------------- //

	@Test
	public void testWithEmptyString() {

		extractor.extract("");

		assertEquals(0, extractor.getLegalWildcardCount());
		assertEquals(0, extractor.getIllegalWildcardCount());
		assertFalse(extractor.hasIllegalWildcards());
		assertTrue(extractor.getLegalWildcardRanges().isEmpty());
	}

	@Test
	public void testWithoutWildcard() {

		extractor.extract("qwe asd");

		assertEquals(0, extractor.getLegalWildcardCount());
		assertEquals(0, extractor.getIllegalWildcardCount());
		assertFalse(extractor.hasIllegalWildcards());
		assertTrue(extractor.getLegalWildcardRanges().isEmpty());
	}

	// ---------------- String Wild-Cards ---------------- //

	@Test
	public void testWithStringWildcard() {

		extractor.extract("qwe %s asd");

		assertEquals(1, extractor.getLegalWildcardCount());
		assertEquals(0, extractor.getIllegalWildcardCount());
		assertFalse(extractor.hasIllegalWildcards());

		IndexRangeList ranges = extractor.getLegalWildcardRanges();
		assertEquals(1, ranges.size());
		assertEquals(4, ranges.get(0).getStart());
		assertEquals(6, ranges.get(0).getEnd());
	}

	@Test
	public void testWithMultipleStringWildcard() {

		extractor.extract("qwe %s asd %s zxc");

		assertEquals(2, extractor.getLegalWildcardCount());
		assertEquals(0, extractor.getIllegalWildcardCount());
		assertFalse(extractor.hasIllegalWildcards());

		IndexRangeList ranges = extractor.getLegalWildcardRanges();
		assertEquals(2, ranges.size());
		assertEquals(4, ranges.get(0).getStart());
		assertEquals(6, ranges.get(0).getEnd());
		assertEquals(11, ranges.get(1).getStart());
		assertEquals(13, ranges.get(1).getEnd());
	}

	@Test
	public void testWithLeadingAndTailingStringWildcard() {

		extractor.extract("%s asd %s");

		assertEquals(2, extractor.getLegalWildcardCount());
		assertEquals(0, extractor.getIllegalWildcardCount());
		assertFalse(extractor.hasIllegalWildcards());

		IndexRangeList ranges = extractor.getLegalWildcardRanges();
		assertEquals(2, ranges.size());
		assertEquals(0, ranges.get(0).getStart());
		assertEquals(2, ranges.get(0).getEnd());
		assertEquals(7, ranges.get(1).getStart());
		assertEquals(9, ranges.get(1).getEnd());
	}

	@Test
	public void testWithAdjacentStringWildcards() {

		extractor.extract("%s%s");

		assertEquals(2, extractor.getLegalWildcardCount());
		assertEquals(0, extractor.getIllegalWildcardCount());
		assertFalse(extractor.hasIllegalWildcards());

		IndexRangeList ranges = extractor.getLegalWildcardRanges();
		assertEquals(2, ranges.size());
		assertEquals(0, ranges.get(0).getStart());
		assertEquals(2, ranges.get(0).getEnd());
		assertEquals(2, ranges.get(1).getStart());
		assertEquals(4, ranges.get(1).getEnd());
	}

	@Test
	public void testWithOnlyStringWildcard() {

		extractor.extract("%s");

		assertEquals(1, extractor.getLegalWildcardCount());
		assertEquals(0, extractor.getIllegalWildcardCount());
		assertFalse(extractor.hasIllegalWildcards());

		IndexRangeList ranges = extractor.getLegalWildcardRanges();
		assertEquals(1, ranges.size());
		assertEquals(0, ranges.get(0).getStart());
		assertEquals(2, ranges.get(0).getEnd());
	}

	// ---------------- Escaped Percent Signs ---------------- //

	@Test
	public void testWithEscapedPercentSign() {

		extractor.extract("qwe %% asd");

		assertEquals(0, extractor.getLegalWildcardCount());
		assertEquals(0, extractor.getIllegalWildcardCount());
		assertFalse(extractor.hasIllegalWildcards());
		assertTrue(extractor.getLegalWildcardRanges().isEmpty());
	}

	@Test
	public void testWithEscapedPercentSignBeforeS() {

		extractor.extract("qwe %%s asd");

		assertEquals(0, extractor.getLegalWildcardCount());
		assertEquals(0, extractor.getIllegalWildcardCount());
		assertFalse(extractor.hasIllegalWildcards());
		assertTrue(extractor.getLegalWildcardRanges().isEmpty());
	}

	@Test
	public void testWithMultipleEscapedPercentSignsBeforeS() {

		extractor.extract("qwe %%%%s asd");

		assertEquals(0, extractor.getLegalWildcardCount());
		assertEquals(0, extractor.getIllegalWildcardCount());
		assertFalse(extractor.hasIllegalWildcards());
		assertTrue(extractor.getLegalWildcardRanges().isEmpty());
	}

	@Test
	public void testWithEscapedPercentSignBeforeStringWildcard() {

		extractor.extract("qwe %%%s asd");

		assertEquals(1, extractor.getLegalWildcardCount());
		assertEquals(0, extractor.getIllegalWildcardCount());
		assertFalse(extractor.hasIllegalWildcards());

		IndexRangeList ranges = extractor.getLegalWildcardRanges();
		assertEquals(1, ranges.size());
		assertEquals(6, ranges.get(0).getStart());
		assertEquals(8, ranges.get(0).getEnd());
	}

	@Test
	public void testWithMultipleEscapedPercentSignBeforeStringWildcard() {

		extractor.extract("qwe %%%%%s asd");

		assertEquals(1, extractor.getLegalWildcardCount());
		assertEquals(0, extractor.getIllegalWildcardCount());
		assertFalse(extractor.hasIllegalWildcards());

		IndexRangeList ranges = extractor.getLegalWildcardRanges();
		assertEquals(1, ranges.size());
		assertEquals(8, ranges.get(0).getStart());
		assertEquals(10, ranges.get(0).getEnd());
	}

	// ---------------- Floating-Point Wild-Cards ---------------- //

	@Test
	public void testWithFloatingPointWildcard() {

		extractor.extract("qwe %f asd");

		assertEquals(1, extractor.getLegalWildcardCount());
		assertEquals(0, extractor.getIllegalWildcardCount());
		assertFalse(extractor.hasIllegalWildcards());

		IndexRangeList ranges = extractor.getLegalWildcardRanges();
		assertEquals(1, ranges.size());
		assertEquals(4, ranges.get(0).getStart());
		assertEquals(6, ranges.get(0).getEnd());
	}

	@Test
	public void testWithIntegerFloatingPointWildcard() {

		extractor.extract("qwe %3f asd");

		assertEquals(1, extractor.getLegalWildcardCount());
		assertEquals(0, extractor.getIllegalWildcardCount());
		assertFalse(extractor.hasIllegalWildcards());

		IndexRangeList ranges = extractor.getLegalWildcardRanges();
		assertEquals(1, ranges.size());
		assertEquals(4, ranges.get(0).getStart());
		assertEquals(7, ranges.get(0).getEnd());
	}

	@Test
	public void testWithAbbreviatedFractionalFloatingPointWildcard() {

		extractor.extract("qwe %.3f asd");

		assertEquals(1, extractor.getLegalWildcardCount());
		assertEquals(0, extractor.getIllegalWildcardCount());
		assertFalse(extractor.hasIllegalWildcards());

		IndexRangeList ranges = extractor.getLegalWildcardRanges();
		assertEquals(1, ranges.size());
		assertEquals(4, ranges.get(0).getStart());
		assertEquals(8, ranges.get(0).getEnd());
	}

	@Test
	public void testWithFullFractionalFloatingPointWildcard() {

		extractor.extract("qwe %2.3f asd");

		assertEquals(1, extractor.getLegalWildcardCount());
		assertEquals(0, extractor.getIllegalWildcardCount());
		assertFalse(extractor.hasIllegalWildcards());

		IndexRangeList ranges = extractor.getLegalWildcardRanges();
		assertEquals(1, ranges.size());
		assertEquals(4, ranges.get(0).getStart());
		assertEquals(9, ranges.get(0).getEnd());
	}

	@Test
	public void testWithIllegalFloatingPointWildcardDueToZero() {

		extractor.extract("qwe %.03f asd");

		assertEquals(0, extractor.getLegalWildcardCount());
		assertEquals(1, extractor.getIllegalWildcardCount());
		assertTrue(extractor.hasIllegalWildcards());
		assertTrue(extractor.getLegalWildcardRanges().isEmpty());
	}

	@Test
	public void testWithIllegalFloatingPointWildcardDueToNonDigit() {

		extractor.extract("qwe %.xf asd");

		assertEquals(0, extractor.getLegalWildcardCount());
		assertEquals(1, extractor.getIllegalWildcardCount());
		assertTrue(extractor.hasIllegalWildcards());
		assertTrue(extractor.getLegalWildcardRanges().isEmpty());
	}

	// ---------------- Mixed Wild-Card Types ---------------- //

	@Test
	public void testWithStringWildcardAndFloatingPointWildcard() {

		extractor.extract("qwe %s asd %3f zxc");

		assertEquals(2, extractor.getLegalWildcardCount());
		assertEquals(0, extractor.getIllegalWildcardCount());
		assertFalse(extractor.hasIllegalWildcards());

		IndexRangeList ranges = extractor.getLegalWildcardRanges();
		assertEquals(2, ranges.size());
		assertEquals(4, ranges.get(0).getStart());
		assertEquals(6, ranges.get(0).getEnd());
		assertEquals(11, ranges.get(1).getStart());
		assertEquals(14, ranges.get(1).getEnd());
	}

	// ---------------- Illegal Wild-Cards Types ---------------- //

	@Test
	public void testWithIllegalWildcardType() {

		extractor.extract("qwe %d asd");

		assertEquals(0, extractor.getLegalWildcardCount());
		assertEquals(1, extractor.getIllegalWildcardCount());
		assertTrue(extractor.hasIllegalWildcards());
		assertTrue(extractor.getLegalWildcardRanges().isEmpty());
	}

	@Test
	public void testWithIllegalAndLegalWildcardTypes() {

		extractor.extract("qwe %d asd %s zxc");

		assertEquals(1, extractor.getLegalWildcardCount());
		assertEquals(1, extractor.getIllegalWildcardCount());
		assertTrue(extractor.hasIllegalWildcards());

		IndexRangeList ranges = extractor.getLegalWildcardRanges();
		assertEquals(1, ranges.size());
		assertEquals(11, ranges.get(0).getStart());
		assertEquals(13, ranges.get(0).getEnd());
	}
}
