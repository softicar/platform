package com.softicar.platform.core.module.page.navigation.link;

import com.softicar.platform.common.core.i18n.CurrentLanguage;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.emf.module.IEmfModuleInstance;
import com.softicar.platform.emf.page.EmfPagePath;
import com.softicar.platform.emf.page.IEmfPage;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class PageNavigationLink<I extends IEmfModuleInstance<I>> {

	protected final List<PageNavigationLink<I>> children = new ArrayList<>();
	private final EmfPagePath path;
	private final IEmfPage<I> page;
	private final I moduleInstance;
	private Optional<IResource> icon = Optional.empty();

	public PageNavigationLink(EmfPagePath path) {

		this.path = path;
		this.page = null;
		this.moduleInstance = null;
	}

	public PageNavigationLink(EmfPagePath moduleFolderPath, IEmfPage<I> page, I moduleInstance) {

		this.page = page;
		this.moduleInstance = moduleInstance;
		this.path = page//
			.getPagePath(moduleFolderPath)
			.append(page.getTitle());
	}

	public PageNavigationLink<I> setIcon(IResource icon) {

		this.icon = Optional.ofNullable(icon);
		return this;
	}

	public String getTitle() {

		return Optional//
			.ofNullable(page)
			.map(IEmfPage::getTitle)
			.map(IDisplayString::toString)
			.orElseGet(this::getLastSegmentOrEmpty);
	}

	public EmfPagePath getPath() {

		return path;
	}

	public IEmfPage<I> getPage() {

		return page;
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

	public void addChild(PageNavigationLink<I> child) {

		children.add(child);
	}

	public List<PageNavigationLink<I>> getChildren() {

		return children;
	}

	public boolean isFolder() {

		return page == null;
	}

	public void sortChildrenByTitle() {

		Collator collator = Collator.getInstance(CurrentLanguage.get().getLocale());
		Collections
			.sort(
				children,
				(a, b) -> Comparator//
					.comparing(PageNavigationLink<I>::isFolder, Comparator.reverseOrder())
					.thenComparing(PageNavigationLink::getTitle, collator)
					.compare(a, b));
	}

	private String getLastSegmentOrEmpty() {

		if (path.getSegments().isEmpty()) {
			return "";
		}
		return path.getLastSegment();
	}
}
