package com.softicar.platform.emf.form.popup;


import com.softicar.platform.dom.element.IDomElement;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.emf.AbstractEmfTest;
import com.softicar.platform.emf.test.simple.EmfTestObject;
import java.util.Collection;
import org.junit.Test;

public class EmfFormPopupConfigurationTest extends AbstractEmfTest {

	private final EmfTestObject object;
	private final EmfFormPopupConfiguration<EmfTestObject> popupConfiguration;

	public EmfFormPopupConfigurationTest() {

		this.object = new EmfTestObject().save();
		this.popupConfiguration = new EmfFormPopupConfiguration<>();
	}

	@Test
	public void testAdditionalContentPresent() {

		popupConfiguration.addAdditionalContent(TestDiv::new);
		Collection<IDomElement> additionalContent = popupConfiguration.createAdditionalContent(object);

		IDomElement content = assertOne(additionalContent);
		assertTrue(content instanceof TestDiv);
	}

	private class TestDiv extends DomDiv {

		public TestDiv(EmfTestObject object) {

			appendChild(object);
		}
	}
}
