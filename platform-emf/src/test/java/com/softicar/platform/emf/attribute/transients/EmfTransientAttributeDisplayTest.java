package com.softicar.platform.emf.attribute.transients;

import com.softicar.platform.common.core.locale.CurrentLocale;
import com.softicar.platform.common.core.locale.Locale;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.emf.AbstractEmfTest;
import java.math.BigDecimal;
import org.junit.Test;

public class EmfTransientAttributeDisplayTest extends AbstractEmfTest {

	private final DomDiv testDiv;

	public EmfTransientAttributeDisplayTest() {

		this.testDiv = new DomDiv();

		setNode(testDiv);

		var locale = new Locale()//
			.setDecimalSeparator(",")
			.setDigitGroupSeparator("'")
			.setDateFormat("dd.MM.yyyy");
		CurrentLocale.set(locale);
	}

	@Test
	public void testWithBigDecimal() {

		testDiv.appendChild(new EmfTransientAttributeDisplay<>(new BigDecimal("12345.678")));

		assertText("12'345,678");
	}

	@Test
	public void testWithDouble() {

		testDiv.appendChild(new EmfTransientAttributeDisplay<>(12345.125));

		assertText("12'345,125");
	}

	@Test
	public void testWithFloat() {

		testDiv.appendChild(new EmfTransientAttributeDisplay<>(12345.25f));

		assertText("12'345,25");
	}

	@Test
	public void testWithDay() {

		testDiv.appendChild(new EmfTransientAttributeDisplay<>(Day.fromYMD(2022, 8, 1)));

		assertText("01.08.2022");
	}

	@Test
	public void testWithDayTime() {

		testDiv.appendChild(new EmfTransientAttributeDisplay<>(DayTime.fromYMD_HMS(2022, 8, 1, 11, 2, 5)));

		assertText("01.08.2022 11:02:05");
	}

	private void assertText(String expectedText) {

		assertEquals(expectedText, findBody().getAllTextInDocument("|"));
	}
}
