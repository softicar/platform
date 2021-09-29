package com.softicar.platform.ajax.testing.selenium.engine.level.high;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.IStaticObject;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.testing.engine.IDomTestEngine;
import com.softicar.platform.dom.elements.testing.engine.IDomTestEngineMethods;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

public class AjaxSeleniumTestEngineTest implements IDomTestEngineMethods {

	@Rule public final IDomTestEngine engine = new AjaxSeleniumTestEngine();
	private static final IStaticObject BUTTON = Mockito.mock(IStaticObject.class);

	public AjaxSeleniumTestEngineTest() {

		setNodeSupplier(TestDiv::new);
	}

	@Test
	public void test() {

		findBody().assertContainsText("foo");
		findButton(BUTTON).click();
		findBody().assertContainsText("bar");
	}

	@Override
	public IDomTestEngine getEngine() {

		return engine;
	}

	private class TestDiv extends DomDiv {

		public TestDiv() {

			appendText("foo");
			appendChild(
				new DomButton()//
					.setLabel(IDisplayString.create("click me"))
					.setClickCallback(() -> appendText("bar"))
					.setMarker(BUTTON));
		}
	}
}
