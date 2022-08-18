package com.softicar.platform.core.module.page.navigation;

import com.softicar.platform.common.core.interfaces.IStaticObject;
import com.softicar.platform.core.module.CoreTestMarker;
import com.softicar.platform.dom.elements.testing.engine.IDomTestExecutionEngineMethods;
import com.softicar.platform.dom.elements.testing.node.iterable.IDomNodeIterable;
import com.softicar.platform.dom.node.IDomNode;
import java.util.List;

public interface IPageNavigationTestMethods extends IDomTestExecutionEngineMethods {

	default <T extends IDomNode> IDomNodeIterable<T> findPageNode(Class<T> pageNodeClass) {

		return findBody().findNodes(pageNodeClass);
	}

	default IDomNodeIterable<IDomNode> findPageNode(IStaticObject marker) {

		return findBody().findNodes(marker);
	}

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
			.findNodes(CoreTestMarker.PAGE_NAVIGATION_PAGE_LINK_DIV)
			.startingWithText(linkName)
			.assertOne()
			.click();
	}
}
