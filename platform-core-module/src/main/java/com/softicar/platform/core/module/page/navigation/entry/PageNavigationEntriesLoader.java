package com.softicar.platform.core.module.page.navigation.entry;

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

public class PageNavigationEntriesLoader<I extends IEmfModuleInstance<I>> {

	private final Map<EmfPagePath, PageNavigationEntry<I>> folders;
	private final Map<EmfPagePath, PageNavigationEntry<I>> links;

	public static <I extends IEmfModuleInstance<I>> PageNavigationEntry<I> loadRootEntry() {

		PageNavigationEntry<I> rootEntry = PageNavigationEntry.createFolder(EmfPagePath.EMPTY_PATH);
		new PageNavigationEntriesLoader<>(rootEntry).loadAll();
		return rootEntry;
	}

	private PageNavigationEntriesLoader(PageNavigationEntry<I> rootEntry) {

		this.folders = new TreeMap<>();
		this.links = new TreeMap<>();
		this.links.put(EmfPagePath.EMPTY_PATH, rootEntry);
	}

	private void loadAll() {

		for (IEmfModule<?> module: IEmfModuleRegistry.get().getAllModules()) {
			loadEntriesForModule(CastUtils.cast(module));
		}
		for (PageNavigationEntry<?> folderEntry: folders.values()) {
			folderEntry.sortChildrenByTitle();
		}
	}

	private void loadEntriesForModule(IEmfModule<I> module) {

		for (I moduleInstance: module.getActiveModuleInstances()) {
			EmfPagePath moduleFolderPath = module.getDefaultPagePath(moduleInstance);
			for (IEmfPage<I> page: EmfPages.getPages(module)) {
				if (isAccessGranted(page, moduleInstance)) {
					var pageEntry = PageNavigationEntry.createPage(moduleFolderPath, page, moduleInstance);
					registerNewEntry(module, pageEntry);
				}
			}
		}
	}

	private boolean isAccessGranted(IEmfPage<I> page, I moduleInstance) {

		return page.getPrecondition().test(moduleInstance) & page.getRequiredPermission().test(moduleInstance, CurrentUser.get());
	}

	private PageNavigationEntry<I> registerNewEntry(IEmfModule<I> module, PageNavigationEntry<I> entry) {

		links.put(entry.getPath(), entry);
		EmfPagePath parentPath = entry.getPath().getParent();
		PageNavigationEntry<I> parent = getOrCreateFolderEntry(module, parentPath);
		parent.addChild(entry);
		folders.put(parentPath, parent);

		return entry;
	}

	private PageNavigationEntry<I> getOrCreateFolderEntry(IEmfModule<I> module, EmfPagePath folderPath) {

		PageNavigationEntry<I> entry = links.get(folderPath);
		if (entry != null) {
			return entry;
		} else {
			entry = PageNavigationEntry.createFolder(folderPath);
			if (entry.getPath().getParent().isEmpty()) {
				entry.setIcon(module.getIcon());
			}
			return registerNewEntry(module, entry);
		}
	}
}
