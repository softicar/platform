package com.softicar.platform.common.string.csv;

import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class CsvTokenizerTest extends Assert {

	private List<List<String>> tokenMatrix;

	public CsvTokenizerTest() {

		this.tokenMatrix = null;
	}

	// ---------------- single line ---------------- //

	@Test
	public void testWithSingleLine() {

		tokenize("qwe,asd,zxc");
		new Asserter()//
			.assertRow("qwe", "asd", "zxc")
			.assertNoMoreRows();
	}

	// ---------------- multiples lines ---------------- //

	@Test
	public void testWithMultipleLinesSeparatedByBackslashN() {

		tokenize("qwe,asd,zxc\n123,456,789");
		new Asserter()//
			.assertRow("qwe", "asd", "zxc")
			.assertRow("123", "456", "789")
			.assertNoMoreRows();
	}

	@Test
	public void testWithMultipleLinesSeparatedByBackslashR() {

		tokenize("qwe,asd,zxc\r123,456,789");
		new Asserter()//
			.assertRow("qwe", "asd", "zxc")
			.assertRow("123", "456", "789")
			.assertNoMoreRows();
	}

	@Test
	public void testWithMultipleLinesSeparatedByBackslashRN() {

		tokenize("qwe,asd,zxc\r\n123,456,789");
		new Asserter()//
			.assertRow("qwe", "asd", "zxc")
			.assertRow("123", "456", "789")
			.assertNoMoreRows();
	}

	@Test
	public void testWithMultipleLinesAndEmptyLine() {

		tokenize("qwe,asd,zxc\n\n123,456,789");
		new Asserter()//
			.assertRow("qwe", "asd", "zxc")
			.assertRow("123", "456", "789")
			.assertNoMoreRows();
	}

	@Test
	public void testWithMultipleLinesOfVariableLength() {

		tokenize("qwe,asd,zxc\n123,456\nxxx,yyy,zzz,___");
		new Asserter()//
			.assertRow("qwe", "asd", "zxc")
			.assertRow("123", "456")
			.assertRow("xxx", "yyy", "zzz", "___")
			.assertNoMoreRows();
	}

	// ---------------- value separation ---------------- //

	@Test
	public void testWithValueSeparationByAdjacentCommas() {

		tokenize(",,qwe,asd,, ,zxc,,");
		new Asserter()//
			.assertRow("", "", "qwe", "asd", "", " ", "zxc", "", "")
			.assertNoMoreRows();
	}

	@Test
	public void testWithValueSeparationMissing() {

		try {
			tokenize("\"qwe\" \"asd\",zxc");
			fail();
		} catch (CsvSyntaxException exception) {
			assertEquals("CSV syntax error in line 1 at character 6", exception.getMessage());
		}
	}

	// ---------------- line-spanning value ---------------- //

	@Test
	public void testWithLineSpanningValue() {

		tokenize("\"X\nX\r\nX\rX\",asd,zxc");
		new Asserter()//
			.assertRow("X\nX\r\nX\rX", "asd", "zxc")
			.assertNoMoreRows();
	}

	// ---------------- spacing ---------------- //

	@Test
	public void testWithSpacesAroundValues() {

		tokenize(" qwe, asd ,zxc ");
		new Asserter()//
			.assertRow(" qwe", " asd ", "zxc ")
			.assertNoMoreRows();
	}

	// ---------------- quoting ---------------- //

	@Test
	public void testWithQuotes() {

		tokenize("\"\"\"qwe,123\"\"\",\"asd\",zxc");
		new Asserter()//
			.assertRow("\"qwe,123\"", "asd", "zxc")
			.assertNoMoreRows();
	}

	@Test
	public void testWithQuotesOnEdges() {

		tokenize("\"qwe\",asd,\"zxc\"");
		new Asserter()//
			.assertRow("qwe", "asd", "zxc")
			.assertNoMoreRows();
	}

	@Test
	public void testWithQuotesMissingInTheEnd() {

		try {
			tokenize("qwe,\"asd,zxc");
			fail();
		} catch (CsvSyntaxException exception) {
			assertEquals("CSV syntax error in line 1 at character 13", exception.getMessage());
		}
	}

	@Test
	public void testWithQuotesInAsymmetricalDistribution() {

		tokenize("qwe,\"\"\"asd\"\"\"\"\",\"\"\"zxc\"");
		new Asserter()//
			.assertRow("qwe", "\"asd\"\"", "\"zxc")
			.assertNoMoreRows();
	}

	@Test
	public void testWithQuotesInInvalidBlock() {

		try {
			tokenize("qwe,\"\"asd,zxc");
			fail();
		} catch (CsvSyntaxException exception) {
			assertEquals("CSV syntax error in line 1 at character 7", exception.getMessage());
		}
	}

	@Test
	public void testWithQuotesInInvalidBlockAndDistribution() {

		try {
			tokenize("qwe,\"\"asd\",zxc");
			fail();
		} catch (CsvSyntaxException exception) {
			assertEquals("CSV syntax error in line 1 at character 7", exception.getMessage());
		}
	}

	@Test
	public void testWithQuoteInNonQuotedValue() {

		try {
			tokenize("qwe,a\"sd,zxc");
			fail();
		} catch (CsvSyntaxException exception) {
			assertEquals("CSV syntax error in line 1 at character 6", exception.getMessage());
		}
	}

	// ---------------- blank values and lines ---------------- //

	@Test
	public void testWithEmptyString() {

		tokenize("");
		new Asserter()//
			.assertNoMoreRows();
	}

	@Test
	public void testWithBlankString() {

		tokenize("  ");
		new Asserter()//
			.assertRow("  ")
			.assertNoMoreRows();
	}

	@Test
	public void testWithEmptyLinesOnly() {

		tokenize("\n\n");
		new Asserter()//
			.assertNoMoreRows();
	}

	@Test
	public void testWithEmptyLinesOnEdges() {

		tokenize("\nqwe,asd\n");
		new Asserter()//
			.assertRow("qwe", "asd")
			.assertNoMoreRows();
	}

	// ---------------- erroneous parameters ---------------- //

	@Test(expected = NullPointerException.class)
	public void testWithNullString() {

		new CsvTokenizer().tokenize(null);
	}

	// ---------------- internal ---------------- //

	private void tokenize(String csv) {

		this.tokenMatrix = new CsvTokenizer().tokenize(csv);
	}

	private class Asserter {

		private int currentRow;

		public Asserter() {

			assertNotNull(tokenMatrix);
			this.currentRow = 0;
		}

		public Asserter assertRow(String...tokens) {

			var rowTokens = tokenMatrix.get(currentRow);
			assertEquals(//
				"Unexpected number of tokens in the logical row with index %s.\nExpected tokens: %s\nActual tokens: %s\n"
					.formatted(currentRow, Arrays.asList(tokens), rowTokens),
				tokens.length,
				rowTokens.size());
			for (int i = 0; i < tokens.length; i++) {
				String expectedToken = tokens[i];
				String actualToken = rowTokens.get(i);
				assertEquals(//
					"Unexpected token at index %s in the logical row with index %s.".formatted(i, currentRow),
					expectedToken,
					actualToken);
			}
			++currentRow;
			return this;
		}

		public Asserter assertNoMoreRows() {

			assertEquals(//
				"Encountered an unexpected number of logical rows.",
				currentRow,
				tokenMatrix.size());
			return this;
		}
	}
}
