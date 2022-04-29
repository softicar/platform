package com.softicar.platform.emf.test.tester;

import static org.junit.Assert.assertEquals;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.checkbox.DomCheckbox;
import com.softicar.platform.dom.elements.dialog.DomModalDialogPopup;
import com.softicar.platform.dom.elements.testing.engine.IDomTestExecutionEngine;
import com.softicar.platform.dom.elements.testing.node.iterable.IDomNodeIterable;
import com.softicar.platform.dom.elements.testing.node.tester.AbstractDomNodeTester;
import com.softicar.platform.dom.elements.testing.node.tester.DomNodeTester;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.action.IEmfManagementAction;
import com.softicar.platform.emf.action.marker.EmfManagementActionMarker;
import com.softicar.platform.emf.editor.EmfDeactivateAction;
import com.softicar.platform.emf.editor.EmfEditAction;
import com.softicar.platform.emf.editor.EmfViewAction;
import com.softicar.platform.emf.management.EmfManagementActionPopover;
import com.softicar.platform.emf.management.EmfManagementDiv;
import com.softicar.platform.emf.management.EmfManagementMarker;
import com.softicar.platform.emf.table.IEmfTable;
import java.util.function.Consumer;

public class EmfManagementDivTester extends AbstractDomNodeTester<EmfManagementDiv<?, ?, ?>> {

	public EmfManagementDivTester(IDomTestExecutionEngine engine, EmfManagementDiv<?, ?, ?> node) {

		super(engine, node);
	}

	// ------------------------------ click ------------------------------ //

	public void clickRefreshButton() {

		clickNode(EmfManagementMarker.REFRESH_BUTTON);
	}

	@Deprecated
	public void clickNewEntryButton() {

		clickCreateButton();
	}

	public void clickCreateButton() {

		clickNode(EmfManagementMarker.CREATE_BUTTON);
	}

	public void clickShowInactiveEntriesCheckbox() {

		clickNode(EmfManagementMarker.SHOW_INACTIVE_CHECKBOX);
	}

	public void clickShowFormButton() {

		clickActionButton(EmfViewAction.class);
	}

	public void clickEditButton() {

		clickActionButton(EmfEditAction.class);
	}

	public void clickDeactivateButtonAndConfirm() {

		clickActionButton(EmfDeactivateAction.class);
		findBody().findNode(DomModalDialogPopup.class).clickNode(EmfI18n.OK);
	}

	public void clickManageChildTableButton(IEmfTable<?, ?, ?> table) {

		findManageChildTableButtons(table).assertOne().click();
	}

	public <T extends IEmfManagementAction<?>> void clickActionButton(Class<T> actionClass) {

		clickActionsButtonAndGetPopover().clickNode(new EmfManagementActionMarker(actionClass));
	}

	// ------------------------------ find ------------------------------ //

	public DomButton findRefreshButton() {

		return findNodes(EmfManagementMarker.REFRESH_BUTTON).assertOne().assertType(DomButton.class);
	}

	public DomButton findNewEntryButton() {

		return findNodes(EmfManagementMarker.CREATE_BUTTON).assertOne().assertType(DomButton.class);
	}

	public DomCheckbox findShowInactiveEntriesCheckbox() {

		return findNodes(EmfManagementMarker.SHOW_INACTIVE_CHECKBOX).assertOne().assertType(DomCheckbox.class);
	}

	// ------------------------------ assert ------------------------------ //

	public EmfManagementDivTester assertNewEntryButton(boolean present) {

		assertEquals(//
			"new entry button %s be available".formatted(present? "*should*" : "should *not*"),
			present,
			findNewEntryButton().isEnabled());
		return this;
	}

	public EmfManagementDivTester assertViewButton(boolean present) {

		assertActionButton(EmfViewAction.class, present);
		return this;
	}

	public EmfManagementDivTester assertEditButton(boolean present) {

		assertActionButton(EmfEditAction.class, present);
		return this;
	}

	public EmfManagementDivTester assertDeactivateButton(boolean present) {

		assertActionButton(EmfDeactivateAction.class, present);
		return this;
	}

	public <T extends IEmfManagementAction<?>> EmfManagementDivTester assertActionButton(Class<T> actionClass, boolean present) {

		assertWithPopover(popover -> {
			assertButton(popover.findNodes(new EmfManagementActionMarker(actionClass)), actionClass.getSimpleName(), present);
		});
		return this;
	}

	public EmfManagementDivTester assertManageChildTableButton(IEmfTable<?, ?, ?> table, boolean present) {

		assertWithPopover(popover -> {
			assertButton(findManageChildTableButtons(popover, table), table.getTitle() + " manage", present);
		});
		return this;
	}

	public void assertNoActionButtons() {

		findNodes(EmfManagementMarker.ACTIONS_POPOVER_BUTTON).assertNone();
	}

	// ------------------------------ auxiliary ------------------------------ //

	private void assertButton(IDomNodeIterable<IDomNode> nodes, String buttonTitle, boolean present) {

		nodes.assertSize("%s button %s exist".formatted(buttonTitle, present? "*should*" : "should *not*"), present? 1 : 0);
	}

	private void assertWithPopover(Consumer<DomNodeTester> assertion) {

		var popoverTester = clickActionsButtonAndGetPopover();
		var popover = popoverTester.assertType(EmfManagementActionPopover.class);
		assertion.accept(popoverTester);
		popover.close();
	}

	private IDomNodeIterable<IDomNode> findManageChildTableButtons(IEmfTable<?, ?, ?> table) {

		return findManageChildTableButtons(clickActionsButtonAndGetPopover(), table);
	}

	private IDomNodeIterable<IDomNode> findManageChildTableButtons(DomNodeTester popover, IEmfTable<?, ?, ?> table) {

		return popover
			.findNodes(EmfManagementMarker.MANAGE_CHILD_TABLE_BUTTON)//
			.startingWithTooltip(EmfI18n.MANAGE_ARG1.toDisplay(table.getPluralTitle()));
	}

	private DomNodeTester clickActionsButtonAndGetPopover() {

		clickNode(EmfManagementMarker.ACTIONS_POPOVER_BUTTON);
		return findBody().findNode(EmfManagementMarker.ACTIONS_POPOVER);
	}
}
