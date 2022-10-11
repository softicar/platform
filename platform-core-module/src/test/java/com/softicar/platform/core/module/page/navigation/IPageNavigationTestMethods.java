package com.softicar.platform.core.module.page.navigation;

import com.softicar.platform.core.module.CoreTestMarker;
import com.softicar.platform.dom.elements.testing.engine.IDomTestExecutionEngineMethods;
import java.util.List;

public interface IPageNavigationTestMethods extends IDomTestExecutionEngineMethods {

	default void assertLinkPresent(String linkName) {

		findBody().assertContainsText(linkName);
	}

	default void assertLinksPresent(String...linkNames) {

		List.of(linkNames).forEach(this::assertLinkPresent);
	}

	default void assertNoLinkPresent(String linkName) {

		findBody().assertDoesNotContainText(linkName);
	}

	default void clickFolderLink(String linkName) {

		findBody()//
			.findNodes(CoreTestMarker.PAGE_NAVIGATION_FOLDER_TITLE_DIV)
			.startingWithText(linkName)
			.assertOne()
			.click();
	}

	default void clickPageLink(String linkName) {

		findBody()//
			.findNodes(CoreTestMarker.PAGE_NAVIGATION_LINK_DIV)
			.startingWithText(linkName)
			.assertOne()
			.click();
	}
}
