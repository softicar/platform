package com.softicar.platform.core.module.page.navigation.entry;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.locale.CurrentLocale;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.emf.module.IEmfModuleInstance;
import com.softicar.platform.emf.page.EmfPagePath;
import com.softicar.platform.emf.page.IEmfPage;
import com.softicar.platform.emf.page.badge.EmfPageBadge;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class PageNavigationEntry<I extends IEmfModuleInstance<I>> {

	private final EmfPagePath path;
	private final IEmfPage<I> page;
	private final I moduleInstance;
	private final List<PageNavigationEntry<I>> children;
	private Optional<IResource> icon;

	public static <I extends IEmfModuleInstance<I>> PageNavigationEntry<I> createFolder(EmfPagePath parentPath) {

		return new PageNavigationEntry<>(parentPath, null, null);
	}

	public static <I extends IEmfModuleInstance<I>> PageNavigationEntry<I> createPage(EmfPagePath parentPath, IEmfPage<I> page, I moduleInstance) {

		return new PageNavigationEntry<>(
			page//
				.getPagePath(parentPath)
				.append(page.getTitle(moduleInstance)),
			page,
			moduleInstance);
	}

	private PageNavigationEntry(EmfPagePath path, IEmfPage<I> page, I moduleInstance) {

		this.path = path;
		this.page = page;
		this.moduleInstance = moduleInstance;
		this.children = new ArrayList<>();
		this.icon = Optional.empty();
	}

	public PageNavigationEntry<I> setIcon(IResource icon) {

		this.icon = Optional.ofNullable(icon);
		return this;
	}

	public String getTitle() {

		return Optional//
			.ofNullable(page)
			.map(it -> it.getTitle(moduleInstance))
			.map(IDisplayString::toString)
			.orElseGet(this::getLastSegmentOrEmpty);
	}

	public EmfPagePath getPath() {

		return path;
	}

	public IEmfPage<I> getPage() {

		return page;
	}

	public Collection<EmfPageBadge> getPageBadges() {

		try {
			return page.getBadges(moduleInstance);
		} catch (Throwable throwable) {
			throwable.printStackTrace();
			return Collections.emptyList();
		}
	}

	public I getModuleInstance() {

		return moduleInstance;
	}

	public Optional<IResource> getIcon() {

		return icon;
	}

	public IDomNode createContentNode() {

		return page.createContentNode(moduleInstance);
	}

	public void addChild(PageNavigationEntry<I> child) {

		children.add(child);
	}

	public List<PageNavigationEntry<I>> getChildren() {

		return children;
	}

	public boolean isFolder() {

		return page == null;
	}

	public void sortChildrenByTitle() {

		Collator collator = Collator.getInstance(CurrentLocale.get().getLanguage().getLocale());
		Collections
			.sort(
				children,
				(a, b) -> Comparator//
					.comparing(PageNavigationEntry<I>::isFolder, Comparator.reverseOrder())
					.thenComparing(PageNavigationEntry::getTitle, collator)
					.compare(a, b));
	}

	private String getLastSegmentOrEmpty() {

		if (path.getSegments().isEmpty()) {
			return "";
		}
		return path.getLastSegment();
	}
}
