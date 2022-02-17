package com.softicar.platform.core.module.page;

import com.softicar.platform.ajax.document.AjaxDocumentParameters;
import com.softicar.platform.ajax.testing.selenium.engine.level.high.AjaxSeleniumTestEngine;
import com.softicar.platform.core.module.page.navigation.IPageNavigationTestMethods;
import com.softicar.platform.core.module.page.navigation.PageNavigationDiv;
import com.softicar.platform.core.module.page.navigation.PageNavigationMarker;
import com.softicar.platform.dom.elements.testing.engine.IDomTestEngine;
import com.softicar.platform.dom.elements.testing.node.tester.DomNodeTester;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePoints;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import org.junit.Rule;
import org.junit.Test;

public class PageDivTest extends AbstractPageDivTest implements IPageNavigationTestMethods {

	@Rule public final AjaxSeleniumTestEngine engine = new AjaxSeleniumTestEngine();

	public PageDivTest() {

		setNodeSupplier(() -> new PageDiv(new AjaxDocumentParameters(new TreeMap<>())));
	}

	@Override
	public IDomTestEngine getEngine() {

		return engine;
	}

	@Test
	public void testDisplayElements() {

		findPageNode(PageDiv.class).assertOne();
		findPageNode(PageNavigationDiv.class).assertOne();
		findPageNode(PageHeaderAndContentDiv.class).assertOne();
		findPageNode(PageHeaderDiv.class).assertOne();
		findPageNode(PageNavigationMarker.PAGE_CONTENT_DIV).assertOne();
		DomNodeTester headerDivTester = findPageNode(PageHeaderDiv.class).assertOne();
		headerDivTester.assertContainsText("Start Page");
	}

	@Test
	public void testClickOnFolder() {

		assertLinksPresent("[System]", "Session", "Start Page", "User Profile");
		clickFolderLink("[System]");
		clickFolderLink("Core");
		assertLinksPresent(//
			"AJAX",
			"Email",
			"Logging",
			"Modules",
			"Programs",
			"Server",
			"Stored File",
			"Test Page",
			"Users",
			"UUIDs",
			"Core Module Instances");
		clickFolderLink("Logging");
		assertLinksPresent("Current Panic Entries", "Log Messages", "Log View");
	}

	@Test
	public void testClickOnPageChangesContentAndHeader() {

		clickFolderLink("[System]");
		clickFolderLink("Core");
		clickPageLink("Test Page");

		assertTestPageIsShown();
	}

	@Test
	public void testOpeningPageWithParameters() {

		String[] moduleInstance = { "0" };
		String[] page = { EmfSourceCodeReferencePoints.getUuidOrThrow(TestPage.class).toString() };
		AjaxDocumentParameters parameters = new AjaxDocumentParameters(Map.of("moduleInstance", moduleInstance, "page", page));
		setNodeSupplier(() -> new PageDiv(parameters));
		assertLinksPresent("[System]", "Core", "Test Page");
		assertTestPageIsShown();
	}

	@Test
	public void testMultipleOpenFolder() {

		clickFolderLink("[System]");
		clickFolderLink("Core");
		clickFolderLink("Email");
		assertLinkPresent("Buffered Emails");
		clickFolderLink("Logging");
		assertLinksPresent("Buffered Emails", "Current Panic Entries", "Log Messages", "Log View");
	}

	private void assertTestPageIsShown() {

		DomNodeTester headerDivTester = findPageNode(PageHeaderDiv.class).assertOne();

		String headerText = headerDivTester.getAllTextsInTree().collect(Collectors.joining("|"));
		assertTrue(headerText.contains("[System]|>>|Core|>>|Test Page"));

		DomNodeTester contentDivTester = findPageNode(PageNavigationMarker.PAGE_CONTENT_DIV).assertOne();
		contentDivTester.assertContainsText(TestPage.CONTENT_STRING);
		assertTrue(engine.getCurrentUrl().contains("page=" + TestPage.UUID));
	}
}
