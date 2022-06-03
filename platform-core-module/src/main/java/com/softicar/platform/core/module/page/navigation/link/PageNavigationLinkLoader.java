package com.softicar.platform.core.module.page.navigation.link;

import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.core.module.user.CurrentUser;
import com.softicar.platform.emf.module.IEmfModule;
import com.softicar.platform.emf.module.IEmfModuleInstance;
import com.softicar.platform.emf.module.registry.IEmfModuleRegistry;
import com.softicar.platform.emf.page.EmfPagePath;
import com.softicar.platform.emf.page.EmfPages;
import com.softicar.platform.emf.page.IEmfPage;
import java.util.Map;
import java.util.TreeMap;

public class PageNavigationLinkLoader<I extends IEmfModuleInstance<I>> {

	private final Map<EmfPagePath, PageNavigationLink<I>> folders;
	private final Map<EmfPagePath, PageNavigationLink<I>> links;

	public static <I extends IEmfModuleInstance<I>> PageNavigationLink<I> loadRootLink() {

		PageNavigationLink<I> rootLink = new PageNavigationLink<>(EmfPagePath.EMPTY_PATH);
		new PageNavigationLinkLoader<>(rootLink).loadAll();
		return rootLink;
	}

	private PageNavigationLinkLoader(PageNavigationLink<I> rootLink) {

		this.folders = new TreeMap<>();
		this.links = new TreeMap<>();
		this.links.put(EmfPagePath.EMPTY_PATH, rootLink);
	}

	public void loadAll() {

		for (IEmfModule<?> module: IEmfModuleRegistry.get().getAllModules()) {
			loadLinksForModule(CastUtils.cast(module));
		}
		for (PageNavigationLink<?> folderLink: folders.values()) {
			folderLink.sortChildrenByTitle();
		}
	}

	private void loadLinksForModule(IEmfModule<I> module) {

		for (I moduleInstance: module.getActiveModuleInstances()) {
			EmfPagePath moduleFolderPath = module.getDefaultPagePath(moduleInstance);
			for (IEmfPage<I> page: EmfPages.getPages(module)) {
				if (isAccessGranted(page, moduleInstance)) {
					PageNavigationLink<I> pageLink = new PageNavigationLink<>(moduleFolderPath, page, moduleInstance);
					registerNewLink(module, pageLink);
				}
			}
		}
	}

	private boolean isAccessGranted(IEmfPage<I> page, I moduleInstance) {

		return page.getPrecondition().test(moduleInstance) & page.getRequiredPermission().test(moduleInstance, CurrentUser.get());
	}

	private PageNavigationLink<I> registerNewLink(IEmfModule<I> module, PageNavigationLink<I> link) {

		links.put(link.getPath(), link);
		EmfPagePath parentPath = link.getPath().getParent();
		PageNavigationLink<I> parentLink = getOrCreateFolderLink(module, parentPath);
		parentLink.addChild(link);
		folders.put(parentPath, parentLink);

		return link;
	}

	private PageNavigationLink<I> getOrCreateFolderLink(IEmfModule<I> module, EmfPagePath folderPath) {

		PageNavigationLink<I> folderLink = links.get(folderPath);
		if (folderLink != null) {
			return folderLink;
		} else {
			folderLink = new PageNavigationLink<>(folderPath);
			if (folderLink.getPath().getParent().isEmpty()) {
				folderLink.setIcon(module.getIcon());
			}
			return registerNewLink(module, folderLink);
		}
	}
}
