package com.softicar.platform.core.module.page;

import com.softicar.platform.ajax.document.AjaxDocument;
import com.softicar.platform.ajax.document.AjaxDocumentParameters;
import com.softicar.platform.ajax.request.AjaxRequest;
import com.softicar.platform.ajax.testing.selenium.engine.level.high.AjaxSeleniumTestExecutionEngine;
import com.softicar.platform.common.code.reference.point.SourceCodeReferencePoints;
import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.CorePermissions;
import com.softicar.platform.core.module.CoreTestMarker;
import com.softicar.platform.core.module.ajax.page.EmfPageConnectionProfiler;
import com.softicar.platform.core.module.ajax.session.reset.AjaxSessionPage;
import com.softicar.platform.core.module.page.header.PageHeaderDiv;
import com.softicar.platform.core.module.page.navigation.IPageNavigationTestMethods;
import com.softicar.platform.core.module.page.navigation.PageNavigationDiv;
import com.softicar.platform.core.module.user.profile.UserProfilePage;
import com.softicar.platform.dom.DomTestMarker;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.testing.engine.IDomTestExecutionEngine;
import com.softicar.platform.dom.elements.testing.node.tester.DomNodeTester;
import com.softicar.platform.emf.page.IEmfPage;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

public class PageDivTest extends AbstractPageDivTest implements IPageNavigationTestMethods {

	@Rule public final AjaxSeleniumTestExecutionEngine engine = new AjaxSeleniumTestExecutionEngine();

	public PageDivTest() {

		insertPermissionAssignment(testUser, CorePermissions.ADMINISTRATION, AGCoreModuleInstance.getInstance());
		setNodeSupplier(() -> new PageDiv(AjaxDocument.getCurrentDocument().get().getParameters()));
	}

	@Override
	public IDomTestExecutionEngine getEngine() {

		return engine;
	}

	@Test
	public void testDisplayElements() {

		findNode(PageDiv.class);
		findNode(PageNavigationDiv.class);
		findNode(PageHeaderAndContentDiv.class);
		findPageContentDiv();
		DomNodeTester headerDivTester = findNode(PageHeaderDiv.class);
		headerDivTester.assertContainsText("Start Page");
	}

	@Test
	public void testClickOnFolderEntry() {

		assertLinksPresent("[System]", "Start Page");
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
	public void testClickOnLinkEntry() {

		clickFolderLink("[System]");
		clickFolderLink("Core");
		clickPageLink("Test Page");

		assertTestPageIsShown();
	}

	@Test
	public void testClickOnLinkEntryWithDebuggingParameters() {

		var profiler = "13";
		var stacktrace = "42";
		engine.setUrlParameter(AjaxRequest.DEBUG_PARAMETER, "");
		engine.setUrlParameter(AjaxRequest.VERBOSE_PARAMETER, "");
		engine.setUrlParameter(EmfPageConnectionProfiler.PROFILER_PARAMETER, profiler);
		engine.setUrlParameter(EmfPageConnectionProfiler.STACKTRACE_PARAMETER, stacktrace);

		clickFolderLink("[System]");
		clickFolderLink("Core");
		clickPageLink("Test Page");

		assertTestPageIsShown();
		assertContains(AjaxRequest.DEBUG_PARAMETER, engine.getCurrentUrl());
		assertContains(AjaxRequest.VERBOSE_PARAMETER, engine.getCurrentUrl());
		assertContains(EmfPageConnectionProfiler.PROFILER_PARAMETER + "=" + profiler, engine.getCurrentUrl());
		assertContains(EmfPageConnectionProfiler.STACKTRACE_PARAMETER + "=" + stacktrace, engine.getCurrentUrl());
	}

	@Test
	public void testOpenUserPopover() {

		findNode(CoreTestMarker.PAGE_HEADER_USER_POPOVER_BUTTON).click();
		var popover = findNode(CoreTestMarker.PAGE_HEADER_USER_POPOVER);

		List<DomNodeTester> popoverActionButtons = popover.findNodes(DomButton.class).toList();
		popoverActionButtons.get(0).assertText("User Profile");
		popoverActionButtons.get(1).assertText("Session");
		popoverActionButtons.get(2).assertText("Logout");
		assertEquals(3, popoverActionButtons.size());
	}

	@Test
	public void testOpenUserPopoverAndUserProfilePage() {

		findNode(CoreTestMarker.PAGE_HEADER_USER_POPOVER_BUTTON).click();
		findNode(CoreTestMarker.PAGE_HEADER_USER_POPOVER_PROFILE_PAGE_BUTTON).click();

		assertUserProfilePageIsShown();
	}

	@Test
	public void testOpenUserPopoverAndSessionPage() {

		findNode(CoreTestMarker.PAGE_HEADER_USER_POPOVER_BUTTON).click();
		findNode(CoreTestMarker.PAGE_HEADER_USER_POPOVER_SESSION_PAGE_BUTTON).click();

		assertSessionPageIsShown();
	}

	@Ignore
	@Test
	public void testOpenUserPopoverAndLogout() {

		findNode(CoreTestMarker.PAGE_HEADER_USER_POPOVER_BUTTON).click();
		findNode(CoreTestMarker.PAGE_HEADER_USER_POPOVER_LOGOUT_BUTTON).click();
		findNode(DomTestMarker.MODAL_CONFIRM_OKAY_BUTTON).click();

		// TODO Assert that the logout works - but logging out from a high-level Selenium test is impossible.
		// TODO At the time of writing, it's even impossible with a low-level Selenium test.
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

		testUser.updatePreferences(preferences -> {
			preferences.automaticallyCollapseFolders = true;
			preferences.recursivelyCollapseFolders = false;
		});

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

		testUser.updatePreferences(preferences -> {
			preferences.automaticallyCollapseFolders = false;
			preferences.recursivelyCollapseFolders = false;
		});

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

		testUser.updatePreferences(preferences -> {
			preferences.automaticallyCollapseFolders = false;
			preferences.recursivelyCollapseFolders = true;
		});

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

		testUser.updatePreferences(preferences -> {
			preferences.automaticallyCollapseFolders = true;
			preferences.recursivelyCollapseFolders = true;
		});

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

	private DomNodeTester findPageContentDiv() {

		return findNode(CoreTestMarker.PAGE_NAVIGATION_PAGE_CONTENT_DIV);
	}

	private void assertPageHeaderTokens(String...tokens) {

		String expectedText = Arrays.asList(tokens).stream().collect(Collectors.joining("|>>|"));

		DomNodeTester headerDivTester = findNodes(PageHeaderDiv.class).assertOne();
		String headerText = headerDivTester.getAllTextsInTree().collect(Collectors.joining("|"));
		assertTrue(headerText.contains(expectedText));
	}

	private void assertTestPageIsShown() {

		assertNoModalDialog();
		assertPageUuid(UUID.fromString(TestPage.UUID));
		assertPageHeaderTokens("[System]", "Core", "Test Page");

		findPageContentDiv().assertContainsText(TestPage.CONTENT_STRING);

		// TODO Assert that the page link has the "selected" class - but we cannot do that with a high-level Selenium test.
	}

	private void assertUserProfilePageIsShown() {

		assertNoModalDialog();
		assertPageUrl(UserProfilePage.class);
		assertPageHeaderTokens("User Profile");

		// assert typical content of the "User Profile" page
		findPageContentDiv()//
			.assertContainsText("Password")
			.assertContainsText("Localization")
			.assertContainsText("Preferences");

		// TODO Assert that no page link has the "selected" class - but we cannot do that with a high-level Selenium test.
	}

	private void assertSessionPageIsShown() {

		assertNoModalDialog();
		assertPageUrl(AjaxSessionPage.class);
		assertPageHeaderTokens("Session");

		// assert typical content of the "Session" page
		findPageContentDiv()//
			.assertContainsText("Session ID")
			.assertContainsText("Created At");

		// TODO Assert that no page link has the "selected" class - but we cannot do that with a high-level Selenium test.
	}

	private void assertPageUrl(Class<? extends IEmfPage<?>> page) {

		assertPageUuid(getPageUuid(page));
	}

	private void assertPageUuid(UUID uuid) {

		assertTrue(engine.getCurrentUrl().contains("page=" + uuid));
	}

	private void assertEventsFolderLinksPresent() {

		assertLinksPresent("AJAX Exceptions", "Current Panic Entries", "Log Messages", "Log View", "System Events");
	}

	private void assertNoModalDialog() {

		findNodes(DomTestMarker.MODAL_DIALOG_FRAME).assertNone();
		findNodes(DomTestMarker.MODAL_DIALOG_POPUP).assertNone();
		findNodes(DomTestMarker.MODAL_DIALOG_CONTENT).assertNone();
		findNodes(DomTestMarker.POPUP_BACKDROP).assertNone();
	}

	private UUID getPageUuid(Class<? extends IEmfPage<?>> page) {

		return SourceCodeReferencePoints.getReferencePoint(page).getAnnotatedUuid();
	}
}
