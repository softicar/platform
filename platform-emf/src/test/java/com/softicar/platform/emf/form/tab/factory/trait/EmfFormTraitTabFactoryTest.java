package com.softicar.platform.emf.form.tab.factory.trait;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.IStaticObject;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.db.core.transaction.DbTransaction;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.emf.AbstractEmfTest;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.management.EmfManagementDiv;
import com.softicar.platform.emf.test.simple.EmfTestObject;
import com.softicar.platform.emf.test.tester.EmfFormPopupTester;
import com.softicar.platform.emf.trait.EmfTestTrait;
import org.junit.Test;
import org.mockito.Mockito;

public class EmfFormTraitTabFactoryTest extends AbstractEmfTest {

	private static final IStaticObject TRANSACTION = Mockito.mock(IStaticObject.class);
	private static final IStaticObject NO_TRANSACTION = Mockito.mock(IStaticObject.class);
	private final EmfTestObject testObject;

	public EmfFormTraitTabFactoryTest() {

		setNodeSupplier(() -> new TestDiv());
		this.testObject = new EmfTestObject().setDay(Day.fromYMD(2000, 2, 2)).save();
		this.testObject.addAuthorizedUser(user);
	}

	@Test
	public void testWithImpermanentObjectInTransaction() {

		findNode(TRANSACTION).click();
		findManagementDiv().clickShowFormButton();
		EmfFormPopupTester formPopup = findFormPopup(EmfTestObject.class);
		formPopup.clickTab(EmfTestTrait.TABLE.getTitle());
		formPopup.clickNode(EmfI18n.CONFIGURE_TRAIT);
		formPopup.findNode(EmfTestTrait.VALUE).assertContainsNoText();
	}

	@Test
	public void testWithImpermanentObjectWithoutTransaction() {

		findNode(NO_TRANSACTION).click();
		findManagementDiv().clickShowFormButton();
		EmfFormPopupTester formPopup = findFormPopup(EmfTestObject.class);
		formPopup.clickTab(EmfTestTrait.TABLE.getTitle());
		formPopup.clickNode(EmfI18n.CONFIGURE_TRAIT);
		formPopup.findNode(EmfTestTrait.VALUE).assertContainsText("15");
	}

	private class TestDiv extends DomDiv {

		public TestDiv() {

			appendChild(new DomButton().setLabel(IDisplayString.create("Create Trait With Transaction")).setClickCallback(() -> {
				try (DbTransaction trans = new DbTransaction()) {
					EmfTestTrait.TABLE.getOrCreate(testObject).setValue(15);
				}
			}).addMarker(TRANSACTION));
			appendChild(
				new DomButton()
					.setClickCallback(() -> EmfTestTrait.TABLE.getOrCreate(testObject).setValue(15))
					.setLabel(IDisplayString.create("Create Trait"))
					.addMarker(NO_TRANSACTION));
			appendChild(new EmfManagementDiv<>(EmfTestObject.TABLE, moduleInstance));
		}
	}
}
