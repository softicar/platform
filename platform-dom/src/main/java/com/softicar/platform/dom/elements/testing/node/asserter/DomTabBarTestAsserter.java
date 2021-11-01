package com.softicar.platform.dom.elements.testing.node.asserter;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.elements.tab.DomTab;
import com.softicar.platform.dom.elements.tab.DomTabBar;
import com.softicar.platform.dom.elements.tab.DomTabHeader;
import com.softicar.platform.dom.elements.testing.node.tester.DomNodeTester;
import com.softicar.platform.dom.node.IDomNode;
import java.util.List;
import java.util.Objects;
import org.junit.Assert;

public class DomTabBarTestAsserter {

	private final DomNodeTester rootNode;

	public DomTabBarTestAsserter(DomNodeTester rootNode) {

		this.rootNode = Objects.requireNonNull(rootNode);
	}

	// ---------------- interactions ---------------- //

	public void clickTabBarHeader(int index) {

		getMinNumberOfTabBarHeaders(index + 1)//
			.get(index)
			.click();
	}

	// ---------------- assertions ---------------- //

	public void assertTabBarAbsent() {

		rootNode//
			.findNodes(DomTabBar.class)
			.assertNone();
	}

	public DomTabBar assertTabBarPresent() {

		DomNodeTester tabBarTester = rootNode//
			.findNodes(DomTabBar.class)
			.assertOne();
		return (DomTabBar) tabBarTester.getNode();
	}

	public void assertTabBarHeaderCount(int count) {

		rootNode//
			.findNodes(DomTabHeader.class)
			.assertSize(count);
	}

	public void assertTabBarHeader(int index, String label) {

		assertTabBarHeader(index, IDisplayString.create(label));
	}

	public void assertTabBarHeader(int index, IDisplayString label) {

		getMinNumberOfTabBarHeaders(index + 1)//
			.get(index)
			.assertContainsText(label);
	}

	public void assertCurrentTab(DomNodeTester tabTester) {

		assertCurrentTab(tabTester.getNode());
	}

	public void assertCurrentTab(IDomNode tab) {

		DomTabBar tabBar = assertTabBarPresent();
		DomTab currentTab = tabBar.getCurrentTab();
		Assert.assertSame(tab, currentTab);
	}

	// ---------------- node finders ---------------- //

	public List<DomNodeTester> getMinNumberOfTabBarHeaders(int minNumber) {

		return getMinNumberOfNodes(DomTabHeader.class, minNumber);
	}

	public <T extends IDomNode> List<DomNodeTester> getMinNumberOfNodes(Class<T> nodeClass, int minNumber) {

		List<DomNodeTester> nodes = getSomeNodes(nodeClass);
		if (nodes.size() < minNumber) {
			Assert
				.fail(
					String
						.format(//
							"Expected at least %s node(s) of type %s but encountered %s.",
							minNumber,
							nodeClass.getSimpleName(),
							nodes.size()));
		}
		return nodes;
	}

	public <T extends IDomNode> List<DomNodeTester> getSomeNodes(Class<T> nodeClass) {

		return rootNode//
			.findNodes(nodeClass)
			.assertSome();
	}
}
