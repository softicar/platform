package com.softicar.platform.core.module.page;

import com.softicar.platform.ajax.document.AjaxDocumentParameters;
import com.softicar.platform.ajax.testing.selenium.engine.level.high.AjaxSeleniumTestExecutionEngine;
import com.softicar.platform.common.code.reference.point.SourceCodeReferencePoints;
import com.softicar.platform.core.module.page.navigation.IPageNavigationTestMethods;
import com.softicar.platform.core.module.page.navigation.PageNavigationDiv;
import com.softicar.platform.core.module.page.navigation.PageNavigationMarker;
import com.softicar.platform.dom.elements.testing.engine.IDomTestExecutionEngine;
import com.softicar.platform.dom.elements.testing.node.tester.DomNodeTester;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import org.junit.Rule;
import org.junit.Test;

public class PageDivTest extends AbstractPageDivTest implements IPageNavigationTestMethods {

	@Rule public final AjaxSeleniumTestExecutionEngine engine = new AjaxSeleniumTestExecutionEngine();

	public PageDivTest() {

		setNodeSupplier(() -> new PageDiv(new AjaxDocumentParameters(new TreeMap<>())));
	}

	@Override
	public IDomTestExecutionEngine getEngine() {

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
			"Email",
			"Events",
			"Modules",
			"Programs",
			"Server",
			"Stored File",
			"Test Page",
			"Users");
		clickFolderLink("Events");
		assertEventsFolderLinksPresent();
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
		String[] page = { SourceCodeReferencePoints.getUuidOrThrow(TestPage.class).toString() };
		AjaxDocumentParameters parameters = new AjaxDocumentParameters(Map.of("moduleInstance", moduleInstance, "page", page));
		setNodeSupplier(() -> new PageDiv(parameters));
		assertLinksPresent("[System]", "Core", "Test Page");
		assertTestPageIsShown();
	}

	@Test
	public void testMultipleOpenFoldersWithAutomaticAndNonRecursiveCollapse() {

		testUser.setAutomaticallyCollapseFolders(true).save();
		testUser.setRecursivelyCollapseFolders(false).save();

		clickFolderLink("[System]");
		clickFolderLink("Core");
		clickFolderLink("Email");
		assertLinkPresent("Buffered Emails");

		clickFolderLink("Events");
		assertEventsFolderLinksPresent();
		assertNoLinkPresent("Buffered Emails");
	}

	@Test
	public void testMultipleOpenFoldersWithNonAutomaticAndNonRecursiveCollapse() {

		testUser.setAutomaticallyCollapseFolders(false).save();
		testUser.setRecursivelyCollapseFolders(false).save();

		clickFolderLink("[System]");
		clickFolderLink("Core");
		clickFolderLink("Email");
		assertLinkPresent("Buffered Emails");

		clickFolderLink("Events");
		assertEventsFolderLinksPresent();

		clickFolderLink("[System]");
		assertNoLinkPresent("Core");

		clickFolderLink("[System]");
		assertEventsFolderLinksPresent();
	}

	@Test
	public void testMultipleOpenFoldersWithNonAutomaticAndRecursiveCollapse() {

		testUser.setAutomaticallyCollapseFolders(false).save();
		testUser.setRecursivelyCollapseFolders(true).save();

		clickFolderLink("[System]");
		clickFolderLink("Core");
		clickFolderLink("Email");
		assertLinkPresent("Buffered Emails");

		clickFolderLink("[System]");
		assertNoLinkPresent("Core");

		clickFolderLink("[System]");
		assertLinksPresent("[System]", "Core");
		assertNoLinkPresent("Email");
	}

	@Test
	public void testMultipleOpenFoldersWithAutomaticAndRecursiveCollapse() {

		testUser.setAutomaticallyCollapseFolders(true).save();
		testUser.setRecursivelyCollapseFolders(true).save();

		clickFolderLink("[System]");
		clickFolderLink("Core");
		clickFolderLink("Users");
		clickFolderLink("Password");
		assertLinkPresent("User Passwords");

		clickFolderLink("Email");
		assertNoLinkPresent("Password");
		assertLinkPresent("Buffered Emails");

		clickFolderLink("Users");
		assertNoLinkPresent("Buffered Emails");
		assertNoLinkPresent("User Passwords");
		assertLinkPresent("Password");
	}

	private void assertTestPageIsShown() {

		DomNodeTester headerDivTester = findPageNode(PageHeaderDiv.class).assertOne();

		String headerText = headerDivTester.getAllTextsInTree().collect(Collectors.joining("|"));
		assertTrue(headerText.contains("[System]|>>|Core|>>|Test Page"));

		DomNodeTester contentDivTester = findPageNode(PageNavigationMarker.PAGE_CONTENT_DIV).assertOne();
		contentDivTester.assertContainsText(TestPage.CONTENT_STRING);
		assertTrue(engine.getCurrentUrl().contains("page=" + TestPage.UUID));
	}

	private void assertEventsFolderLinksPresent() {

		assertLinksPresent("AJAX Exceptions", "Current Panic Entries", "Log Messages", "Log View", "System Events");
	}
}
