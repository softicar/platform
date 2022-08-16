package com.softicar.platform.dom.elements.dialog;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.IStaticObject;
import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.dom.DomTestMarker;
import com.softicar.platform.dom.elements.testing.engine.IDomTestExecutionEngine;
import com.softicar.platform.dom.elements.testing.engine.IDomTestExecutionEngineMethods;
import com.softicar.platform.dom.elements.testing.engine.document.DomDocumentTestExecutionEngine;
import org.junit.Rule;
import org.mockito.Mockito;

public abstract class AbstractDomModalDialogTest extends AbstractTest implements IDomTestExecutionEngineMethods {

	protected static final IStaticObject OPEN_BUTTON = Mockito.mock(IStaticObject.class);
	protected static final IStaticObject OUTPUT_ELEMENT = Mockito.mock(IStaticObject.class);
	protected static final IDisplayString MESSAGE = IDisplayString.create("some message");

	@Rule public final IDomTestExecutionEngine engine = new DomDocumentTestExecutionEngine();

	@Override
	public IDomTestExecutionEngine getEngine() {

		return engine;
	}

	protected void assertNoDisplayedModalDialog() {

		findNodes(DomTestMarker.MODAL_DIALOG_FRAME).assertNone();
		findNodes(DomTestMarker.MODAL_DIALOG_POPUP).assertNone();
		findNodes(DomTestMarker.MODAL_DIALOG_CONTENT).assertNone();
		findNodes(DomTestMarker.POPUP_BACKDROP).assertNone();
	}
}
