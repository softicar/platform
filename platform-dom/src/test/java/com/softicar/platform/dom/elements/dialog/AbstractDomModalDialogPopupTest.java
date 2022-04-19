package com.softicar.platform.dom.elements.dialog;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.IStaticObject;
import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.dom.elements.testing.engine.IDomTestEngine;
import com.softicar.platform.dom.elements.testing.engine.IDomTestEngineMethods;
import com.softicar.platform.dom.elements.testing.engine.document.DomDocumentTestEngine;
import org.junit.Rule;
import org.mockito.Mockito;

public abstract class AbstractDomModalDialogPopupTest extends AbstractTest implements IDomTestEngineMethods {

	protected static final IStaticObject OUTPUT_ELEMENT = Mockito.mock(IStaticObject.class);
	protected static final IStaticObject SHOW_BUTTON = Mockito.mock(IStaticObject.class);
	protected static final IDisplayString MESSAGE = IDisplayString.create("some message");

	@Rule public final IDomTestEngine engine = new DomDocumentTestEngine();

	@Override
	public IDomTestEngine getEngine() {

		return engine;
	}

	protected void assertNoDisplayedModalDialog() {

		findNodes(DomModalDialogMarker.FRAME).assertNone();
		findNodes(DomModalDialogMarker.POPUP).assertNone();
		findNodes(DomModalDialogMarker.CONTENT).assertNone();
		findNodes(DomModalDialogMarker.BACKDROP).assertNone();
	}
}
