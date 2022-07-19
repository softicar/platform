package com.softicar.platform.emf.attribute.field.daytime;

import com.softicar.platform.common.core.locale.CurrentLocale;
import com.softicar.platform.common.core.locale.Locale;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.testing.engine.IDomTestExecutionEngine;
import com.softicar.platform.dom.elements.testing.engine.IDomTestExecutionEngineMethods;
import com.softicar.platform.dom.elements.testing.engine.document.DomDocumentTestExecutionEngine;
import org.junit.Rule;
import org.junit.Test;

public class EmfDayTimeDisplayTest extends AbstractTest implements IDomTestExecutionEngineMethods {

	@Rule public IDomTestExecutionEngine engine = new DomDocumentTestExecutionEngine();
	private final DayTime dayTime;
	private final DomDiv testDiv;

	public EmfDayTimeDisplayTest() {

		dayTime = DayTime.fromYMD_HMS(2022, 12, 31, 14, 30, 20);
		testDiv = new DomDiv();
		setNodeSupplier(() -> testDiv);
	}

	@Override
	public IDomTestExecutionEngine getEngine() {

		return engine;
	}

	@Test
	public void testWithDefaultLocale() {

		testDiv.appendChild(new EmfDayTimeDisplay(dayTime));

		assertEquals("2022-12-31 14:30:20", findBody().getAllTextInDocument("|"));
	}

	@Test
	public void testWithCustomLocale() {

		CurrentLocale.set(new Locale().setDateFormat("dd.MM.yyyy"));
		testDiv.appendChild(new EmfDayTimeDisplay(dayTime));

		assertEquals("31.12.2022 14:30:20", findBody().getAllTextInDocument("|"));
	}
}
