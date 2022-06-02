package com.softicar.platform.emf.form.tab.factory.trait;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.IStaticObject;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.db.core.transaction.DbTransaction;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.input.IDomTextualInput;
import com.softicar.platform.emf.AbstractEmfTest;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.management.EmfManagementDiv;
import com.softicar.platform.emf.test.simple.EmfTestObject;
import com.softicar.platform.emf.test.tester.EmfFormPopupTester;
import com.softicar.platform.emf.trait.EmfTestTrait;
import org.junit.Test;
import org.mockito.Mockito;

public class EmfFormTraitTabFactoryTest extends AbstractEmfTest {

	private static final IStaticObject WITH_TRANSACTION = Mockito.mock(IStaticObject.class);
	private static final IStaticObject WITHOUT_TRANSACTION = Mockito.mock(IStaticObject.class);
	private final EmfTestObject testObject;

	public EmfFormTraitTabFactoryTest() {

		setNodeSupplier(() -> new TestDiv());
		this.testObject = new EmfTestObject().setDay(Day.fromYMD(2000, 2, 2)).save();
		this.testObject.addAuthorizedUser(user);
	}

	@Test
	public void testWithTraitCachePoisoningWithTransaction() {

		findNode(WITH_TRANSACTION).click();
		var formPopup = openFormPopupAndSwitchToTraitTab();
		assertInputValue(formPopup, "");
	}

	@Test
	public void testWithTraitCachePoisoningWithoutTransaction() {

		//TODO Omitting a transaction should not change the behavior.
		findNode(WITHOUT_TRANSACTION).click();
		var formPopup = openFormPopupAndSwitchToTraitTab();
		assertInputValue(formPopup, "15");
	}

	private EmfFormPopupTester openFormPopupAndSwitchToTraitTab() {

		findManagementDiv().clickShowFormButton();
		var formPopup = findFormPopup(EmfTestObject.class);
		formPopup.clickTab(EmfTestTrait.TABLE.getTitle());
		formPopup.clickNode(EmfI18n.CONFIGURE_TRAIT);
		return formPopup;
	}

	private void assertInputValue(EmfFormPopupTester formPopup, String value) {

		formPopup//
			.findNode(EmfTestTrait.VALUE)
			.findNode(IDomTextualInput.class)
			.assertInputValue(value);
	}

	private class TestDiv extends DomDiv {

		// Please note, we need to ensure that the trait object is not garbage collected and thus evicted from the cache.
		@SuppressWarnings("unused") private EmfTestTrait testTrait;

		public TestDiv() {

			this.testTrait = null;
			appendChild(
				new DomButton()//
					.setLabel(IDisplayString.create("With Transaction"))
					.setClickCallback(() -> {
						try (DbTransaction trans = new DbTransaction()) {
							createTrait();
						}
					})
					.addMarker(WITH_TRANSACTION));
			appendChild(
				new DomButton()//
					.setClickCallback(this::createTrait)
					.setLabel(IDisplayString.create("Without Transaction"))
					.addMarker(WITHOUT_TRANSACTION));
			appendChild(new EmfManagementDiv<>(EmfTestObject.TABLE, moduleInstance));
		}

		private void createTrait() {

			testTrait = EmfTestTrait.TABLE.getOrCreate(testObject).setValue(15);
		}
	}
}
