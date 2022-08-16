package com.softicar.platform.dom.elements.popup.compositor;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.common.core.interfaces.IStaticObject;
import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.dom.DomTestMarker;
import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.document.DomBody;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.dom.elements.popup.DomPopupFrame;
import com.softicar.platform.dom.elements.popup.configuration.DomPopupConfiguration;
import com.softicar.platform.dom.elements.popup.modal.DomModalPopupBackdrop;
import com.softicar.platform.dom.elements.testing.engine.IDomTestExecutionEngine;
import com.softicar.platform.dom.elements.testing.engine.IDomTestExecutionEngineMethods;
import com.softicar.platform.dom.elements.testing.engine.document.DomDocumentTestExecutionEngine;
import com.softicar.platform.dom.elements.testing.node.tester.DomNodeTester;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.dom.parent.IDomParentElement;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

public class DomDefaultPopupCompositorTest extends AbstractTest implements IDomTestExecutionEngineMethods {

	private static final IStaticObject POPUP1 = Mockito.mock(IStaticObject.class);
	private static final IStaticObject POPUP2 = Mockito.mock(IStaticObject.class);
	private static final IStaticObject POPUP3 = Mockito.mock(IStaticObject.class);
	private static final String TEST_TEXT = "Some text in the element-under-test";

	@Rule public IDomTestExecutionEngine engine = new DomDocumentTestExecutionEngine();

	private final DomDefaultPopupCompositor compositor;
	private final TestDiv testDiv;
	private final DomPopupConfiguration popup1Config;
	private final DomPopupConfiguration popup2Config;
	private final DomPopupConfiguration popup3Config;
	private final DomPopup popup1;
	private final DomPopup popup2;
	private final DomPopup popup3;

	public DomDefaultPopupCompositorTest() {

		this.compositor = new DomDefaultPopupCompositor();
		this.testDiv = new TestDiv();
		this.testDiv.appendText(TEST_TEXT);

		this.popup1Config = new DomPopupConfiguration();
		this.popup2Config = new DomPopupConfiguration();
		this.popup3Config = new DomPopupConfiguration();

		this.popup1 = new DomPopup().setConfiguration(popup1Config);
		this.popup1.addMarker(POPUP1);
		this.popup2 = new DomPopup().setConfiguration(popup2Config);
		this.popup2.addMarker(POPUP2);
		this.popup3 = new DomPopup().setConfiguration(popup3Config);
		this.popup3.addMarker(POPUP3);

		setNodeSupplier(() -> testDiv);
	}

	@Override
	public IDomTestExecutionEngine getEngine() {

		return engine;
	}

	// -------------------------------- open -------------------------------- //

	@Test
	public void testOpenWithDraggablePopup() {

		// setup
		var openPopup1Button = appendButton(() -> compositor.open(popup1));

		// assert initial state
		assertNone(POPUP1);
		assertNoBackdrop();
		assertBodyText();

		// execute
		openPopup1Button.click();

		// assert result
		assertOne(POPUP1);
		assertNoBackdrop();
		assertBodyText();
	}

	@Test
	public void testOpenWithDraggablePopupFromMaximizedPopup() {

		// setup
		var openPopup1Button = appendButton(() -> compositor.open(popup1));
		openPopup1Button.click();

		popup2Config.setDisplayModeMaximized();
		var openPopup2Button = appendButton(() -> compositor.open(popup2));
		openPopup2Button.click();

		var openPopup3Button = appendButton(popup2, () -> compositor.open(popup3));

		// assert initial state
		assertNone(POPUP1);
		assertOne(POPUP2);
		assertNone(POPUP3);
		assertNoBackdrop();
		assertNoBodyText();

		// execute
		openPopup3Button.click();

		// assert result
		assertNone(POPUP1);
		assertOne(POPUP2);
		assertOne(POPUP3);
		assertNoBackdrop();
		assertNoBodyText();
	}

	@Test
	public void testOpenWithDraggablePopupFromPopoverFromPopup() {

		// setup
		var openPopup1Button = appendButton(() -> compositor.open(popup1));
		openPopup1Button.click();

		popup2Config.setDisplayModePopover();
		var openPopup2Button = appendButton(popup1, () -> compositor.open(popup2));
		openPopup2Button.click();

		var openPopup3Button = appendButton(popup2, () -> {
			compositor.close(popup2);
			compositor.open(popup3);
		});

		// assert initial state
		assertOne(POPUP1);
		assertOne(POPUP2);
		assertNone(POPUP3);

		// execute
		openPopup3Button.click();

		// assert result
		assertOne(POPUP1);
		assertNone(POPUP2);
		assertOne(POPUP3);
		assertAscendingZIndexes(popup1, popup3);
		assertSameContext(popup1, popup3);
	}

	@Test
	public void testOpenWithDraggablePopupReplacingDraggablePopup() {

		// setup
		var openPopup1Button = appendButton(() -> compositor.open(popup1));
		openPopup1Button.click();

		var openPopup2Button = appendButton(popup1, () -> {
			compositor.open(popup2);
			compositor.close(popup1);
		});

		// assert initial state
		assertOne(POPUP1);
		assertNone(POPUP2);
		assertNoBackdrop();
		assertBodyText();

		// execute
		openPopup2Button.click();

		// assert result
		assertNone(POPUP1);
		assertOne(POPUP2);
		assertNoBackdrop();
		assertBodyText();
	}

	@Test
	public void testOpenWithMaximizedPopup() {

		// setup
		var openPopup1Button = appendButton(() -> compositor.open(popup1));
		openPopup1Button.click();

		popup2Config.setDisplayModeMaximized();
		var openPopup2Button = appendButton(() -> compositor.open(popup2));

		// assert initial state
		assertOne(POPUP1);
		assertNone(POPUP2);
		assertNoBackdrop();
		assertBodyText();

		// execute
		openPopup2Button.click();

		// assert result
		assertNone(POPUP1);
		assertOne(POPUP2);
		assertNoBackdrop();
		assertNoBodyText();
	}

	@Test
	public void testOpenWithModalPopup() {

		// setup
		var openPopup1Button = appendButton(() -> compositor.open(popup1));
		openPopup1Button.click();

		popup2Config.setDisplayModeDraggableModal();
		var openPopup2Button = appendButton(() -> compositor.open(popup2));

		// assert initial state
		assertOne(POPUP1);
		assertNone(POPUP2);
		assertNoBackdrop();
		assertBodyText();

		// execute
		openPopup2Button.click();

		// assert result
		assertOne(POPUP1);
		assertOne(POPUP2);
		assertBackdrop();
		assertBodyText();
		assertAscendingZIndexes(popup1, findBackdrop(), popup2);
		// TODO PLAT-847 assert focus
	}

	@Test
	public void testOpenWithModalPopupFromModalPopup() {

		// setup
		popup1Config.setDisplayModeDraggableModal();
		var openPopup1Button = appendButton(() -> compositor.open(popup1));

		popup2Config.setDisplayModeDraggableModal();
		var openPopup2Button = appendButton(popup1, () -> compositor.open(popup2));

		var openPopup3Button = appendButton(popup2, () -> compositor.open(popup3));

		// assert initial state
		assertNone(POPUP1);
		assertNone(POPUP2);
		assertNone(POPUP3);
		assertNoBackdrop();
		assertBodyText();

		// execute
		openPopup1Button.click();
		openPopup2Button.click();
		openPopup3Button.click();

		// assert result
		assertOne(POPUP1);
		assertOne(POPUP2);
		assertOne(POPUP3);
		assertBackdrops(2);
		assertBodyText();
		List<IDomNode> backdrops = findBackdrops();
		assertAscendingZIndexes(backdrops.get(0), popup1, backdrops.get(1), popup2, popup3);
	}

	@Test
	public void testOpenWithModalPopupFromBody() {

		// setup
		popup1Config.setDisplayModeDraggableModal();
		DomBody body = CurrentDomDocument.get().getBody();
		DomNodeTester openPopup1Button = appendButton(body, () -> compositor.open(popup1));

		// assert initial state
		assertNone(POPUP1);
		assertNoBackdrop();
		assertBodyText();

		// execute
		openPopup1Button.click();

		// assert result
		assertOne(POPUP1);
		assertBackdropOnBody();
		assertBodyText();
	}

	@Test
	public void testOpenWithPopoverFromModalPopup() {

		// setup
		popup1Config.setDisplayModeDraggableModal();
		var openPopup1Button = appendButton(() -> compositor.open(popup1));
		openPopup1Button.click();

		popup2Config.setDisplayModePopover();
		var openPopup2Button = appendButton(popup1, () -> compositor.open(popup2));

		// assert initial state
		assertOne(POPUP1);
		assertNone(POPUP2);
		assertBackdrop();
		assertBodyText();

		// execute
		openPopup2Button.click();

		// assert result
		assertOne(POPUP1);
		assertOne(POPUP2);
		assertBackdrops(true, false);
		assertBodyText();
	}

	@Test
	public void testOpenWithOpenPopup() {

		// setup
		var openPopup1Button = appendButton(() -> compositor.open(popup1));

		var openPopup2Button = appendButton(popup1, () -> compositor.open(popup2));

		// assert initial state
		assertNone(POPUP1);
		assertNone(POPUP2);
		assertNoBackdrop();
		assertBodyText();

		// execute
		openPopup1Button.click();
		openPopup2Button.click();
		openPopup1Button.click();

		// assert result
		assertOne(POPUP1);
		assertOne(POPUP2);
		assertNoBackdrop();
		assertBodyText();
		assertAscendingZIndexes(popup2, popup1);
	}

	// -------------------------------- close -------------------------------- //

	@Test
	public void testCloseWithDraggablePopup() {

		// setup
		var openPopup1Button = appendButton(() -> compositor.open(popup1));
		var closePopup1Button = appendButton(() -> compositor.close(popup1));
		openPopup1Button.click();

		// assert initial state
		assertOne(POPUP1);
		assertNoBackdrop();
		assertBodyText();

		// execute
		closePopup1Button.click();

		// assert result
		assertNone(POPUP1);
		assertNoBackdrop();
		assertBodyText();
	}

	@Test
	public void testCloseWithDraggablePopupAndChildRetention() {

		// setup
		var openPopup1Button = appendButton(() -> compositor.open(popup1));
		var openPopup2Button = appendButton(popup1, () -> compositor.open(popup2));
		var closePopup1Button = appendButton(() -> compositor.close(popup1));

		openPopup1Button.click();
		openPopup2Button.click();

		// assert initial state
		assertOne(POPUP1);
		assertOne(POPUP2);
		assertNoBackdrop();
		assertBodyText();

		// execute
		closePopup1Button.click();

		// assert result
		assertNone(POPUP1);
		assertOne(POPUP2);
		assertNoBackdrop();
		assertBodyText();
	}

	@Test
	public void testCloseWithDraggablePopupAndChildClosing() {

		// setup
		popup1Config.setChildClosingModeInteractiveAll();
		var openPopup1Button = appendButton(() -> compositor.open(popup1));
		var openPopup2Button = appendButton(popup1, () -> compositor.open(popup2));
		var closePopup1Button = appendButton(() -> compositor.close(popup1));

		openPopup1Button.click();
		openPopup2Button.click();

		// assert initial state
		assertOne(POPUP1);
		assertOne(POPUP2);
		assertNoBackdrop();
		assertBodyText();

		// execute
		closePopup1Button.click();

		// assert result
		assertNone(POPUP1);
		assertNone(POPUP2);
		assertNoBackdrop();
		assertBodyText();
	}

	@Test
	public void testCloseWithMaximizedPopup() {

		// setup
		var openPopup1Button = appendButton(() -> compositor.open(popup1));
		openPopup1Button.click();

		popup2Config.setDisplayModeMaximized();
		var openPopup2Button = appendButton(() -> compositor.open(popup2));
		openPopup2Button.click();

		var closePopup2Button = appendButton(popup2, () -> compositor.close(popup2));

		// assert initial state
		assertNone(POPUP1);
		assertOne(POPUP2);
		assertNoBackdrop();
		assertNoBodyText();

		// execute
		closePopup2Button.click();

		// assert result
		assertOne(POPUP1);
		assertNone(POPUP2);
		assertNoBackdrop();
		assertBodyText();
	}

	@Test
	public void testCloseWithMaximizedPopupAndChild() {

		// setup
		var openPopup1Button = appendButton(() -> compositor.open(popup1));
		openPopup1Button.click();

		popup2Config.setDisplayModeMaximized();
		popup2Config.setChildClosingModeInteractiveAll();
		var openPopup2Button = appendButton(() -> compositor.open(popup2));
		openPopup2Button.click();

		var openPopup3Button = appendButton(popup2, () -> compositor.open(popup3));
		openPopup3Button.click();

		var closePopup2Button = appendButton(popup2, () -> compositor.close(popup2));

		// assert initial state
		assertNone(POPUP1);
		assertOne(POPUP2);
		assertOne(POPUP3);
		assertNoBackdrop();
		assertNoBodyText();

		// execute
		closePopup2Button.click();

		// assert result
		assertOne(POPUP1);
		assertNone(POPUP2);
		assertNone(POPUP3);
		assertNoBackdrop();
		assertBodyText();
	}

	@Test
	public void testCloseWithModalPopup() {

		// setup
		var openPopup1Button = appendButton(() -> compositor.open(popup1));
		openPopup1Button.click();

		popup2Config.setDisplayModeDraggableModal();
		var openPopup2Button = appendButton(() -> compositor.open(popup2));
		openPopup2Button.click();

		var closePopup2Button = appendButton(popup2, () -> compositor.close(popup2));

		// assert initial state
		assertOne(POPUP1);
		assertOne(POPUP2);
		assertBackdrop();
		assertBodyText();

		// execute
		closePopup2Button.click();

		// assert result
		assertOne(POPUP1);
		assertNone(POPUP2);
		assertNoBackdrop();
		assertBodyText();
	}

	@Test
	public void testCloseWithModalPopupAndChild() {

		// setup
		var openPopup1Button = appendButton(() -> compositor.open(popup1));
		openPopup1Button.click();

		popup2Config.setDisplayModeDraggableModal();
		var openPopup2Button = appendButton(() -> compositor.open(popup2));
		openPopup2Button.click();

		var openPopup3Button = appendButton(popup2, () -> compositor.open(popup3));
		openPopup3Button.click();

		var closePopup2Button = appendButton(popup2, () -> compositor.close(popup2));

		// assert initial state
		assertOne(POPUP1);
		assertOne(POPUP2);
		assertOne(POPUP3);
		assertBackdrop();
		assertBodyText();

		// execute
		closePopup2Button.click();

		// assert result
		assertOne(POPUP1);
		assertNone(POPUP2);
		assertNone(POPUP3);
		assertNoBackdrop();
		assertBodyText();
	}

	// -------------------------------- closeAll -------------------------------- //

	@Test
	public void testCloseAll() {

		// setup
		var openPopup1Button = appendButton(() -> compositor.open(popup1));
		var openPopup2Button = appendButton(() -> compositor.open(popup2));
		var closeAllButton = appendButton(() -> compositor.closeAll());
		openPopup1Button.click();
		openPopup2Button.click();

		// assert initial state
		assertOne(POPUP1);
		assertOne(POPUP2);
		assertNoBackdrop();
		assertBodyText();

		// execute
		closeAllButton.click();

		// assert result
		assertNone(POPUP1);
		assertNone(POPUP2);
		assertNoBackdrop();
		assertBodyText();
	}

	@Test
	public void testCloseAllWithMaximizedPopup() {

		// setup
		var openPopup1Button = appendButton(() -> compositor.open(popup1));
		openPopup1Button.click();

		popup2Config.setDisplayModeMaximized();
		var openPopup2Button = appendButton(() -> compositor.open(popup2));
		openPopup2Button.click();

		var closeAllButton = appendButton(popup2, () -> compositor.closeAll());

		// assert initial state
		assertNone(POPUP1);
		assertOne(POPUP2);
		assertNoBackdrop();
		assertNoBodyText();

		// execute
		closeAllButton.click();

		// assert result
		assertNone(POPUP1);
		assertNone(POPUP2);
		assertNoBackdrop();
		assertBodyText();
	}

	@Test
	public void testCloseAllWithMaximizedPopupAndChild() {

		// setup
		var openPopup1Button = appendButton(() -> compositor.open(popup1));
		openPopup1Button.click();

		popup2Config.setDisplayModeMaximized();
		var openPopup2Button = appendButton(() -> compositor.open(popup2));
		openPopup2Button.click();

		var openPopup3Button = appendButton(popup2, () -> compositor.open(popup3));
		openPopup3Button.click();

		var closeAllButton = appendButton(popup2, () -> compositor.closeAll());

		// assert initial state
		assertNone(POPUP1);
		assertOne(POPUP2);
		assertOne(POPUP3);
		assertNoBackdrop();
		assertNoBodyText();

		// execute
		closeAllButton.click();

		// assert result
		assertNone(POPUP1);
		assertNone(POPUP2);
		assertNone(POPUP3);
		assertNoBackdrop();
		assertBodyText();
	}

	@Test
	public void testCloseAllWithModalPopup() {

		// setup
		var openPopup1Button = appendButton(() -> compositor.open(popup1));
		openPopup1Button.click();

		popup2Config.setDisplayModeDraggableModal();
		var openPopup2Button = appendButton(() -> compositor.open(popup2));
		openPopup2Button.click();

		var closeAllButton = appendButton(popup2, () -> compositor.closeAll());

		// assert initial state
		assertOne(POPUP1);
		assertOne(POPUP2);
		assertBackdrop();
		assertBodyText();

		// execute
		closeAllButton.click();

		// assert result
		assertNone(POPUP1);
		assertNone(POPUP2);
		assertNoBackdrop();
		assertBodyText();
	}

	@Test
	public void testCloseAllWithModalPopupAndChild() {

		// setup
		var openPopup1Button = appendButton(() -> compositor.open(popup1));
		openPopup1Button.click();

		popup2Config.setDisplayModeDraggableModal();
		var openPopup2Button = appendButton(() -> compositor.open(popup2));
		openPopup2Button.click();

		var openPopup3Button = appendButton(popup2, () -> compositor.open(popup3));
		openPopup3Button.click();

		var closeAllButton = appendButton(popup2, () -> compositor.closeAll());

		// assert initial state
		assertOne(POPUP1);
		assertOne(POPUP2);
		assertOne(POPUP3);
		assertBackdrop();
		assertBodyText();

		// execute
		closeAllButton.click();

		// assert result
		assertNone(POPUP1);
		assertNone(POPUP2);
		assertNone(POPUP3);
		assertNoBackdrop();
		assertBodyText();
	}

	// -------------------------------- closeInteractively -------------------------------- //

	@Test
	public void testCloseInteractivelyWithDraggablePopup() {

		// setup
		var openPopup1Button = appendButton(() -> compositor.open(popup1));
		var closePopup1Button = appendButton(() -> compositor.closeInteractively(popup1));
		openPopup1Button.click();

		// assert initial state
		assertOne(POPUP1);
		assertNoBackdrop();
		assertBodyText();

		// execute
		closePopup1Button.click();

		// assert result
		assertNone(POPUP1);
		assertNoBackdrop();
		assertBodyText();
	}

	@Test
	public void testCloseInteractivelyWithDraggablePopupAndChildRetention() {

		// setup
		var openPopup1Button = appendButton(() -> compositor.open(popup1));
		var openPopup2Button = appendButton(popup1, () -> compositor.open(popup2));
		var closePopup1Button = appendButton(() -> compositor.closeInteractively(popup1));
		openPopup1Button.click();
		openPopup2Button.click();

		// assert initial state
		assertOne(POPUP1);
		assertOne(POPUP2);
		assertNoBackdrop();
		assertBodyText();

		// execute
		closePopup1Button.click();

		// assert result
		assertNone(POPUP1);
		assertOne(POPUP2);
		assertNoBackdrop();
		assertBodyText();
	}

	@Test
	public void testCloseInteractivelyWithDraggablePopupAndChildClosingConfirmedAll() {

		// setup
		popup1Config.setChildClosingModeInteractiveOptional();
		var openPopup1Button = appendButton(() -> compositor.open(popup1));
		var openPopup2Button = appendButton(popup1, () -> compositor.open(popup2));
		var closePopup1Button = appendButton(() -> compositor.closeInteractively(popup1));
		openPopup1Button.click();
		openPopup2Button.click();

		// assert initial state
		assertOne(POPUP1);
		assertOne(POPUP2);
		assertNoBackdrop();
		assertBodyText();

		// execute
		closePopup1Button.click();
		findButton(DomTestMarker.POPUP_COMPOSITOR_DIALOG_BUTTON_CLOSE_ALL).click();

		// assert result
		assertNone(POPUP1);
		assertNone(POPUP2);
		assertNoBackdrop();
		assertBodyText();
	}

	@Test
	public void testCloseInteractivelyWithDraggablePopupAndChildClosingConfirmedParentOnly() {

		// setup
		popup1Config.setChildClosingModeInteractiveOptional();
		var openPopup1Button = appendButton(() -> compositor.open(popup1));
		var openPopup2Button = appendButton(popup1, () -> compositor.open(popup2));
		var closePopup1Button = appendButton(() -> compositor.closeInteractively(popup1));
		openPopup1Button.click();
		openPopup2Button.click();

		// assert initial state
		assertOne(POPUP1);
		assertOne(POPUP2);
		assertNoBackdrop();
		assertBodyText();

		// execute
		closePopup1Button.click();
		findButton(DomTestMarker.POPUP_COMPOSITOR_DIALOG_BUTTON_CLOSE_PARENT_ONLY).click();

		// assert result
		assertNone(POPUP1);
		assertOne(POPUP2);
		assertNoBackdrop();
		assertBodyText();
	}

	@Test
	public void testCloseInteractivelyWithDraggablePopupAndChildClosingCancelled() {

		// setup
		popup1Config.setChildClosingModeInteractiveOptional();
		var openPopup1Button = appendButton(() -> compositor.open(popup1));
		var openPopup2Button = appendButton(popup1, () -> compositor.open(popup2));
		var closePopup1Button = appendButton(() -> compositor.closeInteractively(popup1));
		openPopup1Button.click();
		openPopup2Button.click();

		// assert initial state
		assertOne(POPUP1);
		assertOne(POPUP2);
		assertNoBackdrop();
		assertBodyText();

		// execute
		closePopup1Button.click();
		findButton(DomTestMarker.POPUP_COMPOSITOR_DIALOG_BUTTON_CANCEL).click();

		// assert result
		assertOne(POPUP1);
		assertOne(POPUP2);
		assertNoBackdrop();
		assertBodyText();
	}

	@Test
	public void testCloseInteractivelyWithMaximizedPopup() {

		// setup
		var openPopup1Button = appendButton(() -> compositor.open(popup1));
		openPopup1Button.click();

		popup2Config.setDisplayModeMaximized();
		var openPopup2Button = appendButton(() -> compositor.open(popup2));
		openPopup2Button.click();

		var closePopup2Button = appendButton(popup2, () -> compositor.closeInteractively(popup2));

		// assert initial state
		assertNone(POPUP1);
		assertOne(POPUP2);
		assertNoBackdrop();
		assertNoBodyText();

		// execute
		closePopup2Button.click();

		// assert result
		assertOne(POPUP1);
		assertNone(POPUP2);
		assertNoBackdrop();
		assertBodyText();
	}

	@Test
	public void testCloseInteractivelyWithMaximizedPopupAndChildClosingConfirmed() {

		// setup
		var openPopup1Button = appendButton(() -> compositor.open(popup1));
		openPopup1Button.click();

		popup2Config.setDisplayModeMaximized();
		var openPopup2Button = appendButton(() -> compositor.open(popup2));
		openPopup2Button.click();

		var openPopup3Button = appendButton(popup2, () -> compositor.open(popup3));
		openPopup3Button.click();

		var closePopup2Button = appendButton(popup2, () -> compositor.closeInteractively(popup2));

		// assert initial state
		assertNone(POPUP1);
		assertOne(POPUP2);
		assertOne(POPUP3);
		assertNoBackdrop();
		assertNoBodyText();

		// execute
		closePopup2Button.click();
		findButton(DomTestMarker.POPUP_COMPOSITOR_DIALOG_BUTTON_CLOSE_ALL).click();

		// assert result
		assertOne(POPUP1);
		assertNone(POPUP2);
		assertNone(POPUP3);
		assertNoBackdrop();
		assertBodyText();
	}

	@Test
	public void testCloseInteractivelyWithMaximizedPopupAndChildClosingCancelled() {

		// setup
		var openPopup1Button = appendButton(() -> compositor.open(popup1));
		openPopup1Button.click();

		popup2Config.setDisplayModeMaximized();
		var openPopup2Button = appendButton(() -> compositor.open(popup2));
		openPopup2Button.click();

		var openPopup3Button = appendButton(popup2, () -> compositor.open(popup3));
		openPopup3Button.click();

		var closePopup2Button = appendButton(popup2, () -> compositor.closeInteractively(popup2));

		// assert initial state
		assertNone(POPUP1);
		assertOne(POPUP2);
		assertOne(POPUP3);
		assertNoBackdrop();
		assertNoBodyText();

		// execute
		closePopup2Button.click();
		findButton(DomTestMarker.POPUP_COMPOSITOR_DIALOG_BUTTON_CANCEL).click();

		// assert result
		assertNone(POPUP1);
		assertOne(POPUP2);
		assertOne(POPUP3);
		assertNoBackdrop();
		assertNoBodyText();
	}

	@Test
	public void testCloseInteractivelyWithModalPopup() {

		// setup
		var openPopup1Button = appendButton(() -> compositor.open(popup1));
		openPopup1Button.click();

		popup2Config.setDisplayModeDraggableModal();
		var openPopup2Button = appendButton(() -> compositor.open(popup2));
		openPopup2Button.click();

		var closePopup2Button = appendButton(popup2, () -> compositor.closeInteractively(popup2));

		// assert initial state
		assertOne(POPUP1);
		assertOne(POPUP2);
		assertBackdrop();
		assertBodyText();

		// execute
		closePopup2Button.click();

		// assert result
		assertOne(POPUP1);
		assertNone(POPUP2);
		assertNoBackdrop();
		assertBodyText();
	}

	@Test
	public void testCloseInteractivelyWithModalPopupAndChildClosingConfirmed() {

		// setup
		var openPopup1Button = appendButton(() -> compositor.open(popup1));
		openPopup1Button.click();

		popup2Config.setDisplayModeDraggableModal();
		var openPopup2Button = appendButton(() -> compositor.open(popup2));
		openPopup2Button.click();

		var openPopup3Button = appendButton(popup2, () -> compositor.open(popup3));
		openPopup3Button.click();

		var closePopup2Button = appendButton(popup2, () -> compositor.closeInteractively(popup2));

		// assert initial state
		assertOne(POPUP1);
		assertOne(POPUP2);
		assertOne(POPUP3);
		assertBackdrop();
		assertBodyText();

		// execute
		closePopup2Button.click();
		findButton(DomTestMarker.POPUP_COMPOSITOR_DIALOG_BUTTON_CLOSE_ALL).click();

		// assert result
		assertOne(POPUP1);
		assertNone(POPUP2);
		assertNone(POPUP3);
		assertNoBackdrop();
		assertBodyText();
	}

	@Test
	public void testCloseInteractivelyWithModalPopupAndChildClosingCancelled() {

		// setup
		var openPopup1Button = appendButton(() -> compositor.open(popup1));
		openPopup1Button.click();

		popup2Config.setDisplayModeDraggableModal();
		var openPopup2Button = appendButton(() -> compositor.open(popup2));
		openPopup2Button.click();

		var openPopup3Button = appendButton(popup2, () -> compositor.open(popup3));
		openPopup3Button.click();

		var closePopup2Button = appendButton(popup2, () -> compositor.closeInteractively(popup2));

		// assert initial state
		assertOne(POPUP1);
		assertOne(POPUP2);
		assertOne(POPUP3);
		assertBackdrop();
		assertBodyText();

		// execute
		closePopup2Button.click();
		findButton(DomTestMarker.POPUP_COMPOSITOR_DIALOG_BUTTON_CANCEL).click();

		// assert result
		assertOne(POPUP1);
		assertOne(POPUP2);
		assertOne(POPUP3);
		assertBackdrop();
		assertBodyText();
	}

	// -------------------------------- focus -------------------------------- //

	@Ignore
	@Test
	public void testFocus() {

		// TODO PLAT-847 must be implemented before this test can be written.
	}

	// -------------------------------- refreshFrame -------------------------------- //

	@Test
	public void testRefreshFrame() {

		// setup
		popup1Config.setCaption(IDisplayString.create("aaa"));
		popup1Config.setSubCaption(IDisplayString.create("bbb"));

		// setup
		var openPopup1Button = appendButton(() -> compositor.open(popup1));
		openPopup1Button.click();

		var refreshFramePopup1Button = appendButton(() -> compositor.refreshFrame(popup1));

		popup1Config.setCaption(IDisplayString.create("xxx"));
		popup1Config.setSubCaption(IDisplayString.create("yyy"));

		// assert initial state
		assertOne(POPUP1);
		assertNoBackdrop();
		assertBodyText();
		findNode(DomTestMarker.POPUP_FRAME_HEADER).assertContainsText("aaa");
		findNode(DomTestMarker.POPUP_FRAME_HEADER).assertContainsText("bbb");
		findBody().assertContainsText(TEST_TEXT);

		// execute
		refreshFramePopup1Button.click();

		// assert results
		assertOne(POPUP1);
		assertNoBackdrop();
		assertBodyText();
		findNode(DomTestMarker.POPUP_FRAME_HEADER).assertContainsText("xxx");
		findNode(DomTestMarker.POPUP_FRAME_HEADER).assertContainsText("yyy");
		findBody().assertContainsText(TEST_TEXT);
	}

	// -------------------------------- private -------------------------------- //

	private void assertNone(IStaticObject marker) {

		findNodes(marker).assertNone();
	}

	private DomNodeTester assertOne(IStaticObject marker) {

		return findNodes(marker).assertOne();
	}

	private List<DomNodeTester> assertCount(IStaticObject marker, int count) {

		return findNodes(marker).assertSize(count);
	}

	private void assertNoBackdrop() {

		assertNone(DomTestMarker.POPUP_BACKDROP);
	}

	private void assertBackdrop() {

		var backdrop = assertOne(DomTestMarker.POPUP_BACKDROP);
		assertBackdropParent(backdrop, testDiv);
	}

	private void assertBackdropOnBody() {

		var backdrop = assertOne(DomTestMarker.POPUP_BACKDROP);
		assertBackdropParent(backdrop, CurrentDomDocument.get().getBody());
	}

	private void assertBackdrops(int count) {

		assertCount(DomTestMarker.POPUP_BACKDROP, count).forEach(it -> assertBackdropParent(it, testDiv));
	}

	private void assertBackdropParent(DomNodeTester backdropTester, IDomNode expectedParent) {

		var backdropNode = backdropTester.getNode();
		assertSame(//
			"Expected backdrop node %s to have a parent of type %s.".formatted(backdropNode.getNodeIdString(), expectedParent.getClass().getSimpleName()),
			expectedParent,
			backdropNode.getParent());
	}

	private void assertBackdrops(Boolean...visible) {

		List<DomModalPopupBackdrop> backdrops = assertCount(DomTestMarker.POPUP_BACKDROP, visible.length)//
			.stream()
			.map(DomNodeTester::getNode)
			.map(DomModalPopupBackdrop.class::cast)
			.collect(Collectors.toList());

		for (int i = 0; i < visible.length; i++) {
			DomModalPopupBackdrop backdrop = backdrops.get(i);
			assertEquals(visible[i], backdrop.isVisible());
		}
	}

	private void assertBodyText() {

		findBody().assertContainsText(TEST_TEXT);
	}

	private void assertNoBodyText() {

		findBody().assertDoesNotContainText(TEST_TEXT);
	}

	private void assertAscendingZIndexes(IDomNode...nodes) {

		for (int i = 0; i < nodes.length - 1; i++) {
			IDomNode current = nodes[i];
			IDomNode next = nodes[i + 1];
			assertTrue(//
				"Expected node %s to be below node %s.".formatted(toString(current), toString(next)),
				getZIndex(current) < getZIndex(next));
		}
	}

	private void assertSameContext(DomPopup first, DomPopup second) {

		assertSame(findContext(first), findContext(second));
	}

	private IDomPopupContext findContext(DomPopup popup) {

		return new DomParentNodeFinder<>(IDomPopupContext.class).findClosestParent(popup).get();
	}

	private String toString(IDomNode node) {

		return "%s (z-index %s, %s)".formatted(node.getNodeId(), getZIndex(node), node.getClass().getCanonicalName());
	}

	private DomNodeTester appendButton(INullaryVoidFunction callback) {

		return appendButton(testDiv, callback);
	}

	private DomNodeTester appendButton(IDomParentElement target, INullaryVoidFunction callback) {

		return asTester(target.appendChild(new DomButton().setClickCallback(callback)));
	}

	private IDomNode findBackdrop() {

		return findNodes(DomTestMarker.POPUP_BACKDROP).assertOne().getNode();
	}

	private List<IDomNode> findBackdrops() {

		return findNodes(DomTestMarker.POPUP_BACKDROP).toList().stream().map(DomNodeTester::getNode).collect(Collectors.toList());
	}

	private int getZIndex(IDomNode node) {

		if (node instanceof DomPopup) {
			node = new DomParentNodeFinder<>(DomPopupFrame.class).findClosestParent(node).get();
		}
		return asTester(node).getZIndex();
	}

	private static class TestDiv extends DomDiv implements IDomPopupContext {

		// nothing
	}
}
