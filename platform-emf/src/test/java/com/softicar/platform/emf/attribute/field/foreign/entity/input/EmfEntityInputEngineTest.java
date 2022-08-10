package com.softicar.platform.emf.attribute.field.foreign.entity.input;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.IStaticObject;
import com.softicar.platform.db.core.connection.DbConnectionOverrideScope;
import com.softicar.platform.db.core.connection.DbConnections;
import com.softicar.platform.db.core.connection.profiler.DbConnectionTestProfiler;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.input.DomTextInput;
import com.softicar.platform.emf.AbstractEmfTest;
import com.softicar.platform.emf.test.simple.EmfTestObject;
import com.softicar.platform.emf.test.simple.authorization.EmfTestObjectAuthorizedUser;
import com.softicar.platform.emf.token.parser.EmfTokenMatrixParserTest.TestObject;
import org.junit.Test;

public class EmfEntityInputEngineTest extends AbstractEmfTest {

	private static final IStaticObject OBJECT_INPUT_MARKER = newMarker();
	private static final IStaticObject DEPENDENT_INPUT_MARKER = newMarker();
	private static final IStaticObject NEW_OBJECT_BUTTON_MARKER = newMarker();
	private final DbConnectionTestProfiler profiler;

	public EmfEntityInputEngineTest() {

		this.profiler = new DbConnectionTestProfiler();

		setNodeSupplier(() -> new TestDiv());

		insertTestObject(1, "one");
		insertTestObject(2, "two");
		insertTestObject(3, "three");
		EmfTestObjectAuthorizedUser.TABLE.createTable();

		DbConnections.setProfiler(profiler);
	}

	@Test
	public void testWithoutNewObject() {

		// assert initial state
		assertLoadCalls(0);
		assertDependentInputValue("");

		// select object #2 and assert load call
		setObjectInputValue("2");
		assertLoadCalls(1);
		assertDependentInputValue("two [2]");

		// select object #3 and assert no additional load call
		setObjectInputValue("3");
		assertLoadCalls(1);
		assertDependentInputValue("three [3]");
	}

	@Test
	public void testWithNewObject() {

		// assert initial state
		assertLoadCalls(0);
		assertDependentInputValue("");

		// select object #2 and assert load call
		setObjectInputValue("2");
		assertLoadCalls(1);
		assertDependentInputValue("two [2]");

		// create and select new object and assert new load call
		// theres a new third call to check the availability of the edit action
		clickNewObjectButton();
		assertLoadCalls(3);
		assertDependentInputValue("new [42]");

		// select object #1 and assert no additional load call
		setObjectInputValue("1");
		assertLoadCalls(3);
		assertDependentInputValue("one [1]");
	}

	// TODO add tests with scope field and active flag

	private void setObjectInputValue(String value) {

		findBody()//
			.findNode(OBJECT_INPUT_MARKER)
			.setInputValue(value);
	}

	private void clickNewObjectButton() {

		findBody()//
			.findNode(NEW_OBJECT_BUTTON_MARKER)
			.click();
	}

	private void assertLoadCalls(int expectedCount) {

		profiler.assertStatementCount(expectedCount);
	}

	private void assertDependentInputValue(String expectedValue) {

		var value = findBody()//
			.findInput(DEPENDENT_INPUT_MARKER)
			.getInputValue();
		assertEquals(expectedValue, value);
	}

	private EmfTestObject insertTestObject(int id, String name) {

		// execute in dedicated connection to not interfere with profiler
		try (var scope = new DbConnectionOverrideScope()) {
			int insertedId = EmfTestObject.TABLE//
				.createInsert()
				.set(EmfTestObject.ID, id)
				.set(EmfTestObject.NAME, name)
				.execute();
			return EmfTestObject.TABLE.load(insertedId);
		}
	}

	private class TestDiv extends DomDiv {

		private final TestObjectInput objectInput;
		private final DependentInput dependentInput;
		private final DomButton createAndSetButton;

		public TestDiv() {

			this.objectInput = new TestObjectInput();
			this.dependentInput = new DependentInput();
			this.createAndSetButton = new NewObjectButton();

			appendChild(objectInput);
			appendChild(dependentInput);
			appendChild(createAndSetButton);
		}

		private class DependentInput extends DomTextInput {

			public DependentInput() {

				addMarker(DEPENDENT_INPUT_MARKER);
			}
		}

		private class TestObjectInput extends EmfEntityInput<EmfTestObject> {

			public TestObjectInput() {

				super(EmfTestObject.TABLE, null);

				addChangeCallback(() -> {
					var text = getValue()//
						.map(object -> object.toDisplay().toString())
						.orElse("not found");
					dependentInput.setValue(text);
				});
				addMarker(OBJECT_INPUT_MARKER);
			}
		}

		/**
		 * This button creates a new {@link TestObject}, writes its
		 * {@link IDisplayString} into the {@link TestObjectInput} and calls the
		 * change callback on the input.
		 *
		 * @author Oliver Richers
		 */
		private class NewObjectButton extends DomButton {

			public NewObjectButton() {

				setLabel("New Object");
				setClickCallback(() -> {
					EmfTestObject object = insertTestObject(42, "new");
					objectInput.setValueAndHandleChangeCallback(object);
				});
				addMarker(NEW_OBJECT_BUTTON_MARKER);
			}
		}
	}
}
