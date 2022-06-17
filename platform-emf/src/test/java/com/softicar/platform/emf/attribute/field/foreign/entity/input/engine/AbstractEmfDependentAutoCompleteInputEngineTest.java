package com.softicar.platform.emf.attribute.field.foreign.entity.input.engine;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.IStaticObject;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.input.DomTextInput;
import com.softicar.platform.emf.AbstractEmfTest;
import com.softicar.platform.emf.attribute.field.foreign.entity.input.EmfEntityInput;
import com.softicar.platform.emf.test.simple.EmfTestObject;
import com.softicar.platform.emf.token.parser.EmfTokenMatrixParserTest.TestObject;
import java.util.Collection;
import org.junit.Test;

public class AbstractEmfDependentAutoCompleteInputEngineTest extends AbstractEmfTest {

	private static final IStaticObject OBJECT_INPUT_MARKER = newMarker();
	private static final IStaticObject DEPENDENT_INPUT_MARKER = newMarker();
	private static final IStaticObject NEW_OBJECT_BUTTON_MARKER = newMarker();
	private int loadCalls;

	public AbstractEmfDependentAutoCompleteInputEngineTest() {

		this.loadCalls = 0;

		setNodeSupplier(() -> new TestDiv());

		insertTestObject(1, "one");
		insertTestObject(2, "two");
		insertTestObject(3, "three");
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
		clickNewObjectButton();
		assertLoadCalls(2);
		assertDependentInputValue("new [42]");

		// select object #1 and assert no additional load call
		setObjectInputValue("1");
		assertLoadCalls(2);
		assertDependentInputValue("one [1]");
	}

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

		assertEquals(expectedCount, loadCalls);
	}

	private void assertDependentInputValue(String expectedValue) {

		var value = findBody()//
			.findInput(DEPENDENT_INPUT_MARKER)
			.getInputValue();
		assertEquals(expectedValue, value);
	}

	private EmfTestObject insertTestObject(int id, String name) {

		int insertedId = EmfTestObject.TABLE//
			.createInsert()
			.set(EmfTestObject.ID, id)
			.set(EmfTestObject.NAME, name)
			.execute();
		return EmfTestObject.TABLE.load(insertedId);
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

				super(new TestObjectInputEngine());

				addChangeCallback(() -> {
					var text = getValue()//
						.map(object -> object.toDisplay().toString())
						.orElse("not found");
					dependentInput.setValue(text);
				});
				addMarker(OBJECT_INPUT_MARKER);
			}
		}

		private class TestObjectInputEngine extends AbstractEmfDependentAutoCompleteInputEngine<EmfTestObject> {

			public TestObjectInputEngine() {

				super(EmfTestObject.TABLE);
			}

			@Override
			protected Collection<EmfTestObject> loadItems() {

				loadCalls++;
				return EmfTestObject.TABLE.loadAll();
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
