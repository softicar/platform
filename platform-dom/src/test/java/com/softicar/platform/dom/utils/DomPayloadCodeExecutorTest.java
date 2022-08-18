package com.softicar.platform.dom.utils;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.string.formatting.StackTraceFormatting;
import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.document.DomDocument;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.dialog.DomModalAlertDialog;
import com.softicar.platform.dom.elements.testing.engine.IDomTestExecutionEngine;
import com.softicar.platform.dom.elements.testing.engine.IDomTestExecutionEngineMethods;
import com.softicar.platform.dom.elements.testing.engine.document.DomDocumentTestExecutionEngine;
import com.softicar.platform.dom.elements.testing.node.tester.DomNodeTester;
import com.softicar.platform.dom.exception.IDomExceptionDisplayElement;
import java.util.function.Consumer;
import org.junit.Rule;
import org.junit.Test;

public class DomPayloadCodeExecutorTest extends AbstractTest implements IDomTestExecutionEngineMethods {

	private final DomPayloadCodeExecutor executor;
	private boolean executed;
	private final DomNodeTester exceptionDisplayElementTester;
	private final DomDiv eventNode;

	@Rule public IDomTestExecutionEngine engine = new DomDocumentTestExecutionEngine();

	public DomPayloadCodeExecutorTest() {

		this.executor = new DomPayloadCodeExecutor();
		this.executed = false;

		CurrentDomDocument.set(new DomDocument());

		var testDiv = new DomDiv();
		var exceptionDisplayElement = testDiv.appendChild(new TestExceptionDisplayElement());
		this.eventNode = exceptionDisplayElement.appendChild(new DomDiv());
		this.exceptionDisplayElementTester = asTester(exceptionDisplayElement);

		setNodeSupplier(() -> testDiv);
	}

	@Override
	public IDomTestExecutionEngine getEngine() {

		return engine;
	}

	@Test
	public void testExecute() {

		// execute
		executor.execute(() -> executed = true);

		// assert results
		findNodes(DomModalAlertDialog.class).assertNone();
		assertTrue(executed);
	}

	@Test
	public void testExecuteWithException() {

		// execute
		executor.execute(() -> {
			throw new RuntimeException();
		});

		// assert results
		findNode(DomModalAlertDialog.class).assertContainsText(DomI18n.AN_INTERNAL_PROGRAM_ERROR_OCCURRED);
	}

	@Test
	public void testExecuteWithEventNode() {

		// setup
		executor.setEventNode(eventNode);

		// execute
		executor.execute(() -> executed = true);

		// assert results
		findNodes(DomModalAlertDialog.class).assertNone();
		assertTrue(executed);
	}

	@Test
	public void testExecuteWithEventNodeAndExceptionAndAcceptingDisplayElement() {

		// setup
		executor.setEventNode(eventNode);

		// execute
		executor.execute(() -> {
			throw new SofticarUserException(IDisplayString.create("foo"));
		});

		// assert results
		findNodes(DomModalAlertDialog.class).assertNone();
		exceptionDisplayElementTester.assertContainsText("foo");
	}

	@Test
	public void testExecuteWithEventNodeAndExceptionAndNonAcceptingDisplayElement() {

		// setup
		executor.setEventNode(eventNode);

		// execute
		executor.execute(() -> {
			throw new RuntimeException();
		});

		// assert results
		findNode(DomModalAlertDialog.class).assertContainsText(DomI18n.AN_INTERNAL_PROGRAM_ERROR_OCCURRED);
		exceptionDisplayElementTester.assertContainsNoText();
	}

	@Test
	public void testExecuteWithEventNodeAndExceptionHandlers() {

		// setup
		executor.setEventNode(eventNode);

		var firstExceptionHandler = new TestExceptionHandler();
		var secondExceptionHandler = new TestExceptionHandler();
		executor.addExceptionHandler(firstExceptionHandler);
		executor.addExceptionHandler(secondExceptionHandler);

		// execute
		var exception = new RuntimeException();
		executor.execute(() -> {
			throw exception;
		});

		// assert results
		findNode(DomModalAlertDialog.class).assertContainsText(DomI18n.AN_INTERNAL_PROGRAM_ERROR_OCCURRED);
		exceptionDisplayElementTester.assertContainsNoText();
		assertSame(exception, firstExceptionHandler.getException());
		assertSame(exception, secondExceptionHandler.getException());
	}

	@Test
	public void testExecuteWithEventNodeAndExceptionHandlersAndAcceptingDisplayElement() {

		// setup
		executor.setEventNode(eventNode);

		var firstExceptionHandler = new TestExceptionHandler();
		var secondExceptionHandler = new TestExceptionHandler();
		executor.addExceptionHandler(firstExceptionHandler);
		executor.addExceptionHandler(secondExceptionHandler);

		// execute
		var exception = new SofticarUserException(IDisplayString.create("foo"));
		executor.execute(() -> {
			throw exception;
		});

		// assert results
		findNodes(DomModalAlertDialog.class).assertNone();
		exceptionDisplayElementTester.assertContainsText("foo");
		assertSame(exception, firstExceptionHandler.getException());
		assertSame(exception, secondExceptionHandler.getException());
	}

	private static class TestExceptionDisplayElement extends DomDiv implements IDomExceptionDisplayElement {

		@Override
		public boolean accepts(Exception exception) {

			return exception instanceof SofticarUserException;
		}

		@Override
		public void display(Exception exception) {

			appendChild(StackTraceFormatting.getStackTraceAsString(exception));
		}
	}

	private static class TestExceptionHandler implements Consumer<Exception> {

		private Exception exception;

		public TestExceptionHandler() {

			this.exception = null;
		}

		@Override
		public void accept(Exception exception) {

			this.exception = exception;
		}

		public Exception getException() {

			return exception;
		}
	}
}
