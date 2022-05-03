package com.softicar.platform.dom.elements.dialog;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.IStaticObject;
import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomElementsImages;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.testing.engine.IDomTestExecutionEngine;
import com.softicar.platform.dom.elements.testing.engine.IDomTestExecutionEngineMethods;
import com.softicar.platform.dom.elements.testing.engine.document.DomDocumentTestExecutionEngine;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

public class DomModalDialogBuilderTest extends AbstractTest implements IDomTestExecutionEngineMethods {

	private static final IStaticObject BUTTON_CANCEL = Mockito.mock(IStaticObject.class);
	private static final IStaticObject BUTTON_OKAY = Mockito.mock(IStaticObject.class);
	private static final IStaticObject DIALOG = Mockito.mock(IStaticObject.class);
	private static final IDisplayString LABEL_CANCEL = IDisplayString.create("cancel");
	private static final IDisplayString LABEL_OKAY = IDisplayString.create("okay");
	private static final IDisplayString MESSAGE = IDisplayString.create("some message");

	@Rule public final IDomTestExecutionEngine engine = new DomDocumentTestExecutionEngine();

	private final DomDiv testDiv;

	public DomModalDialogBuilderTest() {

		this.testDiv = new DomDiv();
		setNodeSupplier(() -> testDiv);
	}

	@Override
	public IDomTestExecutionEngine getEngine() {

		return engine;
	}

	@Test
	public void test() {

		var beforeOpenCallback = new Callback();
		var okayCallback = new Callback();
		var cancelCallback = new Callback();

		var builder = new DomModalDialogBuilder();
		builder.setMessage(MESSAGE);
		builder.setCallbackBeforeOpen(beforeOpenCallback::apply);
		builder.addMarkers(DIALOG);
		builder.addOption(DomElementsImages.DIALOG_OKAY.getResource(), LABEL_OKAY, okayCallback::apply, BUTTON_OKAY);
		builder.addOption(DomElementsImages.DIALOG_CANCEL.getResource(), LABEL_CANCEL, cancelCallback::apply, BUTTON_CANCEL);

		var dialogPopup = builder.build();
		beforeOpenCallback.assertNotCalled();
		asTester(testDiv.appendChild(new DomButton().setClickCallback(dialogPopup::open))).click();
		assertTrue(dialogPopup.isAppended());

		var dialog = findPopup(DIALOG);
		dialog.assertContainsText(MESSAGE);

		var okayButton = findButton(BUTTON_OKAY);
		okayButton.assertText(LABEL_OKAY);

		var cancelButton = findButton(BUTTON_CANCEL);
		cancelButton.assertText(LABEL_CANCEL);

		okayButton.click();

		assertFalse(dialogPopup.isAppended());
		beforeOpenCallback.assertCalled();
		okayCallback.assertCalled();
		cancelCallback.assertNotCalled();
	}

	private class Callback {

		private boolean called;

		public Callback() {

			this.called = false;
		}

		public void apply() {

			if (!called) {
				this.called = true;
			} else {
				throw new IllegalStateException();
			}
		}

		public void assertCalled() {

			assertTrue(called);
		}

		public void assertNotCalled() {

			assertFalse(called);
		}
	}
}
