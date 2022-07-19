package com.softicar.platform.emf.data.table;

import com.softicar.platform.common.container.data.table.DataTableIdentifier;
import com.softicar.platform.common.container.data.table.IDataTable;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.common.string.hash.Hash;
import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.document.DomDocument;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.dom.elements.popup.compositor.IDomPopupContext;
import com.softicar.platform.dom.elements.tab.DomTab;
import com.softicar.platform.dom.elements.tab.DomTabBar;
import com.softicar.platform.dom.elements.testing.engine.IDomTestExecutionEngine;
import com.softicar.platform.dom.elements.testing.engine.IDomTestExecutionEngineMethods;
import com.softicar.platform.dom.elements.testing.engine.document.DomDocumentTestExecutionEngine;
import com.softicar.platform.dom.elements.testing.node.tester.DomNodeTester;
import com.softicar.platform.dom.engine.DomTestEngine;
import com.softicar.platform.dom.parent.IDomParentElement;
import com.softicar.platform.emf.form.popup.EmfFormPopup;
import com.softicar.platform.emf.page.IEmfPage;
import com.softicar.platform.emf.page.IEmfPageContentElement;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import com.softicar.platform.emf.test.simple.EmfTestObject;
import java.lang.reflect.Field;
import java.util.Optional;
import java.util.UUID;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

public class EmfDataTablePathTest extends AbstractTest implements IDomTestExecutionEngineMethods {

	private static final String TABLE_IDENTIFIER = "my-data-table-identifier";
	private static final String POPUP_CAPTION = "my-popup-caption";
	private static final String POPUP_OTHER_CAPTION = "my-popup-other-caption";
	private static final UUID PAGE_UUID = UUID.fromString("3abf80e6-b34d-4861-9828-43edbfcdb9ff");

	@Rule public IDomTestExecutionEngine engine = new DomDocumentTestExecutionEngine();

	private final DomDocument document;
	private final IEmfPageContentElement pageContentElement;

	public EmfDataTablePathTest() {

		document = new DomDocument(new DomTestEngine());
		CurrentDomDocument.set(document);

		pageContentElement = createEmfPageContentElement(PAGE_UUID);
		document.getBody().appendChild(pageContentElement);

		setNodeSupplier(document::getBody);
	}

	@Override
	public IDomTestExecutionEngine getEngine() {

		return engine;
	}

	@Test
	public void test() {

		var emfDataTable = createEmfDataTable(TABLE_IDENTIFIER);

		appendButton(pageContentElement, () -> {
			pageContentElement.appendChild(emfDataTable);
		}).click();

		var path = new EmfDataTablePath(emfDataTable);
		assertEquals("page(%s)/table(%s)".formatted(PAGE_UUID, TABLE_IDENTIFIER), path.toString());
		assertEquals(Hash.SHA1.getHashStringLC(path.toString()), path.getHash());
	}

	@Test
	public void testWithoutPageContentElement() {

		var body = document.getBody();
		body.removeChildren();

		var emfDataTable = createEmfDataTable(TABLE_IDENTIFIER);

		appendButton(body, () -> {
			body.appendChild(emfDataTable);
		}).click();

		var path = new EmfDataTablePath(emfDataTable);
		assertEquals("table(%s)".formatted(TABLE_IDENTIFIER), path.toString());
		assertEquals(Hash.SHA1.getHashStringLC(path.toString()), path.getHash());
	}

	@Test
	public void testWithMagicCharactersInTableIdentifier() {

		var emfDataTable = createEmfDataTable("my-data-tab/le-iden(ti)fier");

		appendButton(pageContentElement, () -> {
			pageContentElement.appendChild(emfDataTable);
		}).click();

		var path = new EmfDataTablePath(emfDataTable);
		assertEquals("page(%s)/table(%s)".formatted(PAGE_UUID, "my-data-tab\\/le-iden\\(ti\\)fier"), path.toString());
		assertEquals(Hash.SHA1.getHashStringLC(path.toString()), path.getHash());
	}

	@Test
	public void testWithEmptyTableIdentifier() {

		var emfDataTable = createEmfDataTable("");

		appendButton(pageContentElement, () -> {
			pageContentElement.appendChild(emfDataTable);
		}).click();

		var path = new EmfDataTablePath(emfDataTable);
		assertEquals("page(%s)/table()".formatted(PAGE_UUID), path.toString());
		assertEquals(Hash.SHA1.getHashStringLC(path.toString()), path.getHash());
	}

	@Test
	public void testWithEmfFormPopup() {

		var emfFormPopup = createEmfFormPopup(new EmfTestObject());
		var emfDataTable = createEmfDataTable(TABLE_IDENTIFIER);

		appendButton(pageContentElement, () -> {
			emfFormPopup.open();
			emfFormPopup.appendChild(emfDataTable);
		}).click();

		var path = new EmfDataTablePath(emfDataTable);
		assertEquals("page(%s)/popup(%s)/table(%s)".formatted(PAGE_UUID, EmfTestObject.class.getCanonicalName(), TABLE_IDENTIFIER), path.toString());
		assertEquals(Hash.SHA1.getHashStringLC(path.toString()), path.getHash());
	}

	@Test
	public void testWithEmfFormPopupWithTabs() {

		var emfFormPopup = createEmfFormPopup(new EmfTestObject());

		DomTabBar tabBar = createTabBar();
		tabBar.appendTab(new DomTab(IDisplayString.create("tab 1")));
		DomTab secondTab = tabBar.appendTab(new DomTab(IDisplayString.create("tab 2")));
		tabBar.appendTab(new DomTab(IDisplayString.create("tab 3")));

		var emfDataTable = createEmfDataTable(TABLE_IDENTIFIER);

		appendButton(pageContentElement, () -> {
			emfFormPopup.open();
			emfFormPopup.appendChild(tabBar);
			tabBar.showTab(secondTab);
			secondTab.appendChild(emfDataTable);
		}).click();

		var path = new EmfDataTablePath(emfDataTable);
		assertEquals(
			"page(%s)/popup(%s)/tab(%s)/table(%s)".formatted(PAGE_UUID, EmfTestObject.class.getCanonicalName(), "tab 2", TABLE_IDENTIFIER),
			path.toString());
		assertEquals(Hash.SHA1.getHashStringLC(path.toString()), path.getHash());
	}

	@Test
	public void testWithDomPopup() {

		var domPopup = createDomPopup(POPUP_CAPTION);
		var emfDataTable = createEmfDataTable(TABLE_IDENTIFIER);

		appendButton(pageContentElement, () -> {
			domPopup.open();
			domPopup.appendChild(emfDataTable);
		}).click();

		var path = new EmfDataTablePath(emfDataTable);
		assertEquals("page(%s)/popup(%s)/table(%s)".formatted(PAGE_UUID, POPUP_CAPTION, TABLE_IDENTIFIER), path.toString());
		assertEquals(Hash.SHA1.getHashStringLC(path.toString()), path.getHash());
	}

	@Test
	public void testWithDomPopupWithTabs() {

		var domPopup = createDomPopup(POPUP_CAPTION);

		DomTabBar tabBar = createTabBar();
		tabBar.appendTab(new DomTab(IDisplayString.create("tab 1")));
		DomTab secondTab = tabBar.appendTab(new DomTab(IDisplayString.create("tab 2")));
		tabBar.appendTab(new DomTab(IDisplayString.create("tab 3")));

		var emfDataTable = createEmfDataTable(TABLE_IDENTIFIER);

		appendButton(pageContentElement, () -> {
			domPopup.open();
			domPopup.appendChild(tabBar);
			tabBar.showTab(secondTab);
			secondTab.appendChild(emfDataTable);
		}).click();

		var path = new EmfDataTablePath(emfDataTable);
		assertEquals("page(%s)/popup(%s)/tab(%s)/table(%s)".formatted(PAGE_UUID, POPUP_CAPTION, "tab 2", TABLE_IDENTIFIER), path.toString());
		assertEquals(Hash.SHA1.getHashStringLC(path.toString()), path.getHash());
	}

	@Test
	public void testWithDomPopupWithTabsFromDomPopup() {

		var firstDomPopup = createDomPopup(POPUP_CAPTION);
		var secondDomPopup = createDomPopup(POPUP_OTHER_CAPTION);

		DomTabBar tabBar = createTabBar();
		tabBar.appendTab(new DomTab(IDisplayString.create("tab 1")));
		DomTab secondTab = tabBar.appendTab(new DomTab(IDisplayString.create("tab 2")));
		tabBar.appendTab(new DomTab(IDisplayString.create("tab 3")));

		var emfDataTable = createEmfDataTable(TABLE_IDENTIFIER);

		appendButton(pageContentElement, () -> {
			firstDomPopup.open();
		}).click();

		appendButton(firstDomPopup, () -> {
			secondDomPopup.open();
			secondDomPopup.appendChild(tabBar);
			tabBar.showTab(secondTab);
			secondTab.appendChild(emfDataTable);
		}).click();

		var path = new EmfDataTablePath(emfDataTable);
		assertEquals(
			"page(%s)/popup(%s)/popup(%s)/tab(%s)/table(%s)".formatted(PAGE_UUID, POPUP_CAPTION, POPUP_OTHER_CAPTION, "tab 2", TABLE_IDENTIFIER),
			path.toString());
		assertEquals(Hash.SHA1.getHashStringLC(path.toString()), path.getHash());
	}

	@Test
	public void testWithDomPopupWithTabsFromClosedDomPopup() {

		var firstDomPopup = createDomPopup(POPUP_CAPTION);
		var secondDomPopup = createDomPopup(POPUP_OTHER_CAPTION);

		DomTabBar tabBar = createTabBar();
		tabBar.appendTab(new DomTab(IDisplayString.create("tab 1")));
		DomTab secondTab = tabBar.appendTab(new DomTab(IDisplayString.create("tab 2")));
		tabBar.appendTab(new DomTab(IDisplayString.create("tab 3")));

		var emfDataTable = createEmfDataTable(TABLE_IDENTIFIER);

		appendButton(pageContentElement, () -> {
			firstDomPopup.open();
		}).click();

		appendButton(firstDomPopup, () -> {
			secondDomPopup.open();
			firstDomPopup.close();
			secondDomPopup.appendChild(tabBar);
			tabBar.showTab(secondTab);
			secondTab.appendChild(emfDataTable);
		}).click();

		var path = new EmfDataTablePath(emfDataTable);
		assertEquals(
			"page(%s)/popup(%s)/popup(%s)/tab(%s)/table(%s)".formatted(PAGE_UUID, POPUP_CAPTION, POPUP_OTHER_CAPTION, "tab 2", TABLE_IDENTIFIER),
			path.toString());
		assertEquals(Hash.SHA1.getHashStringLC(path.toString()), path.getHash());
	}

	@Test
	public void testWithDomPopupWithoutPageContentElement() {

		var body = document.getBody();
		body.removeChildren();

		var domPopup = createDomPopup(POPUP_CAPTION);
		var emfDataTable = createEmfDataTable(TABLE_IDENTIFIER);

		appendButton(body, () -> {
			domPopup.open();
			domPopup.appendChild(emfDataTable);
		}).click();

		var path = new EmfDataTablePath(emfDataTable);
		assertEquals("popup(%s)/table(%s)".formatted(POPUP_CAPTION, TABLE_IDENTIFIER), path.toString());
		assertEquals(Hash.SHA1.getHashStringLC(path.toString()), path.getHash());
	}

	private DomNodeTester appendButton(IDomParentElement parent, INullaryVoidFunction callback) {

		var button = new DomButton().setClickCallback(callback);
		parent.appendChild(button);
		return asTester(button);
	}

	private DomPopup createDomPopup(String caption) {

		return new DomPopup().setCaption(IDisplayString.create(caption));
	}

	private <R extends IEmfTableRow<R, ?>> EmfFormPopup<R> createEmfFormPopup(R tableRow) {

		return new EmfFormPopup<>(tableRow);
	}

	private DomTabBar createTabBar() {

		return new DomTabBar();
	}

	private IEmfPageContentElement createEmfPageContentElement(UUID pageUuid) {

		IEmfPage<?> page = Mockito.mock(IEmfPage.class);
		Mockito.when(page.getAnnotatedUuid()).thenReturn(pageUuid);

		return new TestPageContentElement(page);
	}

	private IEmfDataTable<?> createEmfDataTable(String identifierString) {

		IDataTable<?> dataTable = Mockito.mock(IDataTable.class);
		Mockito.when(dataTable.getIdentifier()).thenReturn(new DataTableIdentifier(identifierString));

		try {
			var dataTableDiv = new EmfDataTableDivBuilder<>(dataTable).build();
			Field tableField = dataTableDiv.getClass().getDeclaredField("table");
			tableField.setAccessible(true);
			var emfDataTable = (IEmfDataTable<?>) tableField.get(dataTableDiv);
			emfDataTable.disappend();
			return emfDataTable;
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	private static class TestPageContentElement extends DomDiv implements IEmfPageContentElement, IDomPopupContext {

		private final IEmfPage<?> page;

		public TestPageContentElement(IEmfPage<?> page) {

			this.page = page;
		}

		@Override
		public Optional<IEmfPage<?>> getPage() {

			return Optional.of(page);
		}
	}
}
