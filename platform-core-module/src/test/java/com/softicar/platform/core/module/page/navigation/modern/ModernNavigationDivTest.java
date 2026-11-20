package com.softicar.platform.core.module.page.navigation.modern;

import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.CorePermissions;
import com.softicar.platform.core.module.CoreTestMarker;
import com.softicar.platform.core.module.page.navigation.PageNavigationController;
import com.softicar.platform.core.module.page.navigation.entry.PageNavigationEntriesLoader;
import com.softicar.platform.core.module.page.navigation.entry.PageNavigationEntry;
import com.softicar.platform.core.module.test.AbstractCoreTest;
import com.softicar.platform.core.module.user.CurrentUser;
import com.softicar.platform.dom.elements.testing.node.tester.DomNodeTester;
import com.softicar.platform.dom.event.DomEventType;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class ModernNavigationDivTest extends AbstractCoreTest {

	private final List<PageNavigationEntry<?>> openedEntries;
	private int collapseCount;

	public ModernNavigationDivTest() {

		this.openedEntries = new ArrayList<>();
		this.collapseCount = 0;

		insertPermissionAssignment(CurrentUser.get(), CorePermissions.ADMINISTRATION, AGCoreModuleInstance.getInstance());
		setNodeSupplier(() -> {
			var rootEntry = PageNavigationEntriesLoader.loadRootEntry();
			var controller = new PageNavigationController(openedEntries::add, rootEntry);
			return new ModernNavigationDiv(controller, rootEntry, () -> collapseCount++);
		});
	}

	@Test
	public void testListOfAllModules() {

		DomNodeTester coreHead = findModuleHead("Core");

		assertTrue(Integer.parseInt(getLinkCount(coreHead)) > 1);
		assertFalse(getLinkTitles().contains("Test Page"));
	}

	@Test
	public void testModuleAndPage() {

		findModuleHead("Core").click();

		assertEquals(1, findNodes(CoreTestMarker.MODERN_NAVIGATION_MODULE_HEAD).size());
		assertFalse(findNodes(CoreTestMarker.MODERN_NAVIGATION_GROUP_HEAD).toList().isEmpty());

		findLink("Test Page").click();

		assertEquals(1, openedEntries.size());
		assertEquals("Test Page", openedEntries.get(0).getTitle());
	}

	@Test
	public void testGroupToggle() {

		findModuleHead("Core").click();
		int linksBefore = getLinkTitles().size();

		DomNodeTester groupHead = findNodes(CoreTestMarker.MODERN_NAVIGATION_GROUP_HEAD).toList().get(0);
		groupHead.click();
		int linksAfterFirstClick = getLinkTitles().size();
		groupHead.click();

		assertTrue(linksBefore != linksAfterFirstClick);
		assertEquals(linksBefore, getLinkTitles().size());
	}

	@Test
	public void testSearch() {

		DomNodeTester search = findNode(CoreTestMarker.MODERN_NAVIGATION_SEARCH);
		search.setInputValue("test pa");
		search.sendEvent(DomEventType.INPUT);

		assertEquals(List.of("Test Page"), getLinkTitles());

		search.setInputValue("");
		search.sendEvent(DomEventType.INPUT);

		assertFalse(getLinkTitles().contains("Test Page"));
	}

	@Test
	public void testEnterOpensTheFirstMatch() {

		DomNodeTester search = findNode(CoreTestMarker.MODERN_NAVIGATION_SEARCH);
		search.setInputValue("test pa");
		search.sendEvent(DomEventType.INPUT);
		search.sendEvent(DomEventType.ENTER);

		assertEquals(1, openedEntries.size());
		assertEquals("Test Page", openedEntries.get(0).getTitle());
	}

	@Test
	public void testEnterInEmptySearchOpensNothing() {

		DomNodeTester search = findNode(CoreTestMarker.MODERN_NAVIGATION_SEARCH);
		search.setInputValue("  ");
		search.sendEvent(DomEventType.ENTER);

		assertTrue(openedEntries.isEmpty());
	}

	@Test
	public void testCollapse() {

		findNode(CoreTestMarker.MODERN_NAVIGATION_RAIL_TOGGLE).click();

		assertEquals(1, collapseCount);
	}

	private DomNodeTester findModuleHead(String title) {

		return findNodes(CoreTestMarker.MODERN_NAVIGATION_MODULE_HEAD)//
			.toList()
			.stream()
			.filter(head -> head.containsText(title))
			.findFirst()
			.orElseThrow(() -> new AssertionError("Missing module: " + title));
	}

	private DomNodeTester findLink(String title) {

		return findNodes(CoreTestMarker.MODERN_NAVIGATION_LINK)//
			.toList()
			.stream()
			.filter(link -> link.containsText(title))
			.findFirst()
			.orElseThrow(() -> new AssertionError("Missing link: " + title));
	}

	private List<String> getLinkTitles() {

		return findNodes(CoreTestMarker.MODERN_NAVIGATION_LINK)//
			.toList()
			.stream()
			.map(link -> link.getAllTextsInTree().reduce("", String::concat))
			.toList();
	}

	private String getLinkCount(DomNodeTester moduleHead) {

		String text = moduleHead.getAllTextsInTree().reduce("", (a, b) -> a + "|" + b);
		return text.substring(text.lastIndexOf('|') + 1);
	}
}
