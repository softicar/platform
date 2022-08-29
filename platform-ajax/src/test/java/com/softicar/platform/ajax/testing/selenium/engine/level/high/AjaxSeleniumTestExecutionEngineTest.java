package com.softicar.platform.ajax.testing.selenium.engine.level.high;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.ITestMarker;
import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.testing.engine.IDomTestExecutionEngine;
import com.softicar.platform.dom.elements.testing.engine.IDomTestExecutionEngineMethods;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

public class AjaxSeleniumTestExecutionEngineTest extends AbstractTest implements IDomTestExecutionEngineMethods {

	@Rule public final IDomTestExecutionEngine engine = new AjaxSeleniumTestExecutionEngine();
	private static final ITestMarker BUTTON = Mockito.mock(ITestMarker.class);

	public AjaxSeleniumTestExecutionEngineTest() {

		setNodeSupplier(TestDiv::new);
	}

	@Test
	public void test() {

		findBody().assertContainsText("foo");
		findButton(BUTTON).click();
		findBody().assertContainsText("bar");
	}

	@Override
	public IDomTestExecutionEngine getEngine() {

		return engine;
	}

	private class TestDiv extends DomDiv {

		public TestDiv() {

			appendText("foo");
			appendChild(
				new DomButton()//
					.setLabel(IDisplayString.create("click me"))
					.setClickCallback(() -> appendText("bar"))
					.addMarker(BUTTON));
		}
	}
}
