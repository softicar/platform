package com.softicar.platform.emf.attribute.field.day;

import com.softicar.platform.common.core.locale.CurrentLocale;
import com.softicar.platform.common.core.locale.Locale;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.testing.engine.IDomTestExecutionEngine;
import com.softicar.platform.dom.elements.testing.engine.IDomTestExecutionEngineMethods;
import com.softicar.platform.dom.elements.testing.engine.document.DomDocumentTestExecutionEngine;
import org.junit.Rule;
import org.junit.Test;

public class EmfDayDisplayTest extends AbstractTest implements IDomTestExecutionEngineMethods {

	@Rule public IDomTestExecutionEngine engine = new DomDocumentTestExecutionEngine();
	private final Day day;
	private final DomDiv testDiv;

	public EmfDayDisplayTest() {

		day = Day.fromYMD(2022, 12, 31);
		testDiv = new DomDiv();
		setNodeSupplier(() -> testDiv);
	}

	@Override
	public IDomTestExecutionEngine getEngine() {

		return engine;
	}

	@Test
	public void testWithDefaultLocale() {

		testDiv.appendChild(new EmfDayDisplay(day));

		assertEquals("2022-12-31", findBody().getAllTextInDocument("|"));
	}

	@Test
	public void testWithCustomLocale() {

		CurrentLocale.set(new Locale().setDateFormat("dd.MM.yyyy"));
		testDiv.appendChild(new EmfDayDisplay(day));

		assertEquals("31.12.2022", findBody().getAllTextInDocument("|"));
	}
}
