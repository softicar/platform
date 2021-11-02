package com.softicar.platform.core.module.page.navigation.link.display;

import com.softicar.platform.core.module.page.navigation.link.PageNavigationLinkKey;
import com.softicar.platform.emf.page.IEmfPage;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PageNavigationPageLinkDivMap {

	private final Map<PageNavigationLinkKey, PageNavigationPageLinkDiv> map;

	public PageNavigationPageLinkDivMap() {

		this.map = new HashMap<>();
	}

	public Optional<PageNavigationPageLinkDiv> getLinkDiv(IEmfPage<?> page, Integer moduleInstanceId) {

		return Optional.ofNullable(map.get(new PageNavigationLinkKey(page, moduleInstanceId)));
	}

	public void add(PageNavigationPageLinkDiv pageLinkDiv) {

		map.put(new PageNavigationLinkKey(pageLinkDiv.getLink()), pageLinkDiv);
	}
}
