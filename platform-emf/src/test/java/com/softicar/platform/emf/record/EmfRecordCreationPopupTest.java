package com.softicar.platform.emf.record;



import com.softicar.platform.common.date.Day;
import com.softicar.platform.common.testing.Asserts;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.testing.node.tester.DomNodeTester;
import com.softicar.platform.emf.AbstractEmfTest;
import com.softicar.platform.emf.EmfMarker;
import com.softicar.platform.emf.EmfTestScope;
import com.softicar.platform.emf.form.popup.EmfFormPopup;
import com.softicar.platform.emf.test.simple.EmfTestObject;
import org.junit.Test;

public class EmfRecordCreationPopupTest extends AbstractEmfTest {

	private static final int SOME_VALUE = 31;
	private static final int OTHER_VALUE = 37;
	private final EmfTestObject object;

	public EmfRecordCreationPopupTest() {

		this.object = new EmfTestObject()//
			.setName("Foo")
			.setDay(Day.today())
			.setActive(true)
			.save();

		setNodeSupplier(TestDiv::new);
	}

	@Test
	public void testWithNewRecord() {

		inputPrimaryKeyAndClickNext(object);
		inputValueAndClickSaveAndClose(SOME_VALUE);

		// assert that record was correctly inserted
		EmfTestRecordWithObjectPk record = Asserts.assertOne(EmfTestRecordWithObjectPk.TABLE.loadAll());
		assertFalse(record.impermanent());
		assertSame(object, record.getObject());
		assertSame(SOME_VALUE, record.getValue());
	}

	@Test
	public void testWithExistingRecord() {

		EmfTestRecordWithObjectPk.TABLE//
			.getOrCreate(object)
			.setValue(SOME_VALUE)
			.save();

		inputPrimaryKeyAndClickNext(object);
		inputValueAndClickSaveAndClose(OTHER_VALUE);

		// assert that record was correctly updated
		EmfTestRecordWithObjectPk record = Asserts.assertOne(EmfTestRecordWithObjectPk.TABLE.loadAll());
		record.reload();
		assertSame(object, record.getObject());
		assertSame(OTHER_VALUE, record.getValue());
	}

	private void inputPrimaryKeyAndClickNext(EmfTestObject object) {

		DomNodeTester creationPopup = findBody().findNode(EmfRecordCreationPopup.class);
		creationPopup.setInputValue(EmfTestRecordWithObjectPk.OBJECT, object.toDisplay().toString());
		creationPopup.clickNode(EmfMarker.NEXT);
	}

	private void inputValueAndClickSaveAndClose(Integer value) {

		DomNodeTester formPopup = findBody().findNode(EmfFormPopup.class);
		formPopup.setInputValue(EmfTestRecordWithObjectPk.VALUE, "" + value);
		formPopup.clickNode(EmfMarker.SAVE_AND_CLOSE);
	}

	private static class TestDiv extends DomDiv {

		public TestDiv() {

			new EmfRecordCreationPopup<>(EmfTestRecordWithObjectPk.TABLE, EmfTestScope.getInstance()).show();
		}
	}
}
