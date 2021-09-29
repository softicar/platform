package com.softicar.platform.core.module.page.navigation.link;

import com.softicar.platform.common.core.utils.equals.Equals;
import com.softicar.platform.emf.page.IEmfPage;
import java.util.Objects;

public class PageNavigationLinkKey {

	private final IEmfPage<?> page;
	private final Integer moduleInstanceId;

	public PageNavigationLinkKey(PageNavigationLink<?> link) {

		this(link.getPage(), Integer.valueOf(link.getModuleInstance().getItemId().toString()));
	}

	public PageNavigationLinkKey(IEmfPage<?> page, Integer moduleInstanceId) {

		this.page = page;
		this.moduleInstanceId = moduleInstanceId;
	}

	public IEmfPage<?> getPage() {

		return page;
	}

	public Integer getModuleInstanceId() {

		return moduleInstanceId;
	}

	@Override
	public int hashCode() {

		return Objects.hash(page, moduleInstanceId);
	}

	@Override
	public boolean equals(Object other) {

		return Equals//
			.comparing(PageNavigationLinkKey::getPage)
			.comparing(PageNavigationLinkKey::getModuleInstanceId)
			.compareToObject(this, other);
	}
}
