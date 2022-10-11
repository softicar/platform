package com.softicar.platform.core.module.page.navigation.entry;

import com.softicar.platform.common.core.utils.equals.Equals;
import com.softicar.platform.emf.page.IEmfPage;
import java.util.Objects;

public class PageNavigationEntryKey {

	private final IEmfPage<?> page;
	private final Integer moduleInstanceId;

	public PageNavigationEntryKey(PageNavigationEntry<?> entry) {

		this(entry.getPage(), Integer.valueOf(entry.getModuleInstance().getItemId().toString()));
	}

	public PageNavigationEntryKey(IEmfPage<?> page, Integer moduleInstanceId) {

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
			.comparing(PageNavigationEntryKey::getPage)
			.comparing(PageNavigationEntryKey::getModuleInstanceId)
			.compareToObject(this, other);
	}
}
