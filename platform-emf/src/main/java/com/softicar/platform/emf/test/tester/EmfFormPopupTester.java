package com.softicar.platform.emf.test.tester;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.db.runtime.field.IDbBooleanField;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.checkbox.DomCheckbox;
import com.softicar.platform.dom.elements.tab.DomTab;
import com.softicar.platform.dom.elements.tab.DomTabBar;
import com.softicar.platform.dom.elements.tab.DomTabMarker;
import com.softicar.platform.dom.elements.testing.engine.IDomTestExecutionEngine;
import com.softicar.platform.dom.elements.testing.node.tester.AbstractDomNodeTester;
import com.softicar.platform.dom.elements.testing.node.tester.DomNodeTester;
import com.softicar.platform.emf.EmfMarker;
import com.softicar.platform.emf.action.IEmfPrimaryAction;
import com.softicar.platform.emf.action.marker.EmfActionMarker;
import com.softicar.platform.emf.action.marker.EmfCommonActionMarker;
import com.softicar.platform.emf.editor.EmfEditAction;
import com.softicar.platform.emf.form.EmfFormMarker;
import com.softicar.platform.emf.form.popup.EmfFormPopup;
import com.softicar.platform.emf.log.viewer.EmfLogAction;
import com.softicar.platform.emf.management.EmfManagementDiv;
import com.softicar.platform.emf.management.EmfManagementMarker;

public class EmfFormPopupTester extends AbstractDomNodeTester<EmfFormPopup<?>> {

	public EmfFormPopupTester(IDomTestExecutionEngine engine, EmfFormPopup<?> node) {

		super(engine, node);
	}

	public void clickTab(IDisplayString tabName) {

		findNode(DomTabBar.class).clickNode(tabName);
	}

	public void clickSaveButton() {

		clickNode(EmfMarker.SAVE);
	}

	public void clickSaveAndCloseButton() {

		clickNode(EmfMarker.SAVE_AND_CLOSE);
	}

	public void clickActionButton(Class<? extends IEmfPrimaryAction<?>> actionClass) {

		clickNode(new EmfActionMarker(actionClass));
	}

	public void clickCommonActionRefreshButton() {

		findCommonActionDiv().clickNode(EmfManagementMarker.REFRESH_BUTTON);
	}

	public void clickCommonActionEditButton() {

		findCommonActionDiv().clickNode(new EmfCommonActionMarker(EmfEditAction.class));
	}

	public void clickCommonActionLogButton() {

		findCommonActionDiv().clickNode(new EmfCommonActionMarker(EmfLogAction.class));
	}

	public void clickCheckbox(IDbBooleanField<?> booleanField) {

		findNode(booleanField).findNode(DomCheckbox.class).click();
	}

	public DomTab findTab(IDisplayString tabName) {

		return findNode(DomTabBar.class)//
			.findNode(DomTabMarker.TAB_BAR_HEADER)
			.findNodes(DomTab.class)
			.withText(tabName)
			.assertOne()
			.assertType(DomTab.class);
	}

	public DomButton findSaveButton() {

		return findNode(EmfMarker.SAVE).assertType(DomButton.class);
	}

	public DomButton findSaveAndCloseButton() {

		return findNode(EmfMarker.SAVE_AND_CLOSE).assertType(DomButton.class);
	}

	public DomButton findCommonActionRefreshButton() {

		return findCommonActionDiv()//
			.findNode(EmfManagementMarker.REFRESH_BUTTON)
			.assertType(DomButton.class);
	}

	public DomButton findCommonActionEditButton() {

		return findCommonActionDiv()//
			.findNode(new EmfCommonActionMarker(EmfEditAction.class))
			.assertType(DomButton.class);
	}

	public DomButton findCommonActionLogButton() {

		return findCommonActionDiv()//
			.findNode(new EmfCommonActionMarker(EmfLogAction.class))
			.assertType(DomButton.class);
	}

	public EmfManagementDivTester findManagementDiv() {

		return new EmfManagementDivTester(//
			getEngine(),
			findNode(EmfManagementDiv.class).assertType(EmfManagementDiv.class));
	}

	private DomNodeTester findCommonActionDiv() {

		return findNode(EmfFormMarker.COMMON_ACTIONS_DIV);
	}
}
