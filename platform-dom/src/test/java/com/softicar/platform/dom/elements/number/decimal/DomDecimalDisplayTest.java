package com.softicar.platform.dom.elements.number.decimal;

import com.softicar.platform.common.core.locale.Locale;
import com.softicar.platform.common.core.locale.LocaleScope;
import com.softicar.platform.common.core.number.BigDecimalMapper;
import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.dom.elements.testing.engine.IDomTestEngine;
import com.softicar.platform.dom.elements.testing.engine.IDomTestEngineMethods;
import com.softicar.platform.dom.elements.testing.engine.document.DomDocumentTestEngine;
import java.math.BigDecimal;
import org.junit.Rule;
import org.junit.Test;

public class DomDecimalDisplayTest extends AbstractTest implements IDomTestEngineMethods {

	@Rule public final IDomTestEngine engine = new DomDocumentTestEngine();
	private final DomDecimalDisplay<BigDecimal> display;
	private final Locale locale;

	public DomDecimalDisplayTest() {

		this.display = new DomDecimalDisplay<>(BigDecimalMapper.BIG_DECIMAL);
		this.locale = new Locale()//
			.setDecimalSeparator(",")
			.setDigitGroupSeparator(".");

		setNodeSupplier(() -> display);
	}

	@Override
	public IDomTestEngine getEngine() {

		return engine;
	}

	@Test
	public void testPositiveValue() {

		var input = "123456.789";

		try (var scope = new LocaleScope(locale)) {
			assertDisplay("123.456,789", input, null);

			assertDisplay("123.456,7890", input, 4);
			assertDisplay("123.456,789", input, 3);
			assertDisplay("123.456,79", input, 2);
			assertDisplay("123.457", input, 0);
			assertDisplay("123.500", input, -2);
		}
	}

	@Test
	public void testNegariveValue() {

		var input = "-123456.789";

		try (var scope = new LocaleScope(locale)) {
			assertDisplay("-123.456,789", input, null);

			assertDisplay("-123.456,7890", input, 4);
			assertDisplay("-123.456,789", input, 3);
			assertDisplay("-123.456,79", input, 2);
			assertDisplay("-123.457", input, 0);
			assertDisplay("-123.500", input, -2);
		}
	}

	@Test
	public void testWithZeroValue() {

		try (var scope = new LocaleScope(locale)) {
			assertDisplay("0", "0", null);
			assertDisplay("0,0", "0.0", null);
			assertDisplay("0,0", "00.0", null);

			assertDisplay("0,0000", "0", 4);
			assertDisplay("0,00", "0", 2);
			assertDisplay("0", "0", 0);
			assertDisplay("0", "0", -2);
		}
	}

	private void assertDisplay(String expectedDisplay, String input, Integer scale) {

		display.setScale(scale).setValue(new BigDecimal(input));

		findNode(DomDecimalDisplay.class).assertText(expectedDisplay);
	}
}
