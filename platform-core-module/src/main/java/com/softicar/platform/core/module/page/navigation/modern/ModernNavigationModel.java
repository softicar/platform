package com.softicar.platform.core.module.page.navigation.modern;

import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.core.module.CoreModule;
import com.softicar.platform.core.module.page.navigation.entry.PageNavigationEntry;
import com.softicar.platform.emf.module.IEmfModule;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Interprets the tree of {@link PageNavigationEntry} objects for
 * {@link ModernNavigationDiv}.
 * <p>
 * A <i>tenant</i> is a folder at the top level of the tree (e.g. a business
 * unit). The folder of the system modules ({@link CoreModule#getParentPagePath})
 * is a tenant only if there is no other folder at the top level; otherwise it
 * is offered separately. A <i>module</i> is a folder directly within a tenant;
 * deeper folders are <i>groups</i>.
 */
class ModernNavigationModel {

	private final PageNavigationEntry<?> root;
	private final Map<PageNavigationEntry<?>, PageNavigationEntry<?>> parents;
	private final PageNavigationEntry<?> systemFolder;
	private final List<PageNavigationEntry<?>> tenants;

	public ModernNavigationModel(PageNavigationEntry<?> root) {

		this.root = root;
		this.parents = new IdentityHashMap<>();
		registerParents(root);

		this.systemFolder = getFolders(root)//
			.stream()
			.filter(folder -> folder.getPath().equals(CoreModule.getParentPagePath()))
			.findFirst()
			.orElse(null);
		var otherFolders = getFolders(root).stream().filter(folder -> folder != systemFolder).toList();
		this.tenants = otherFolders.isEmpty() && systemFolder != null? List.of(systemFolder) : otherFolders;
	}

	public List<PageNavigationEntry<?>> getTenants() {

		return tenants;
	}

	/**
	 * Returns the folder of the system modules, if it is not one of the
	 * tenants.
	 */
	public Optional<PageNavigationEntry<?>> getSeparateSystemFolder() {

		return Optional.<PageNavigationEntry<?>> ofNullable(systemFolder).filter(folder -> !tenants.contains(folder));
	}

	public boolean isSystemFolder(PageNavigationEntry<?> entry) {

		return entry != null && entry == systemFolder;
	}

	public List<PageNavigationEntry<?>> getRootLinks() {

		return getLinks(root);
	}

	public List<PageNavigationEntry<?>> getFolders(PageNavigationEntry<?> entry) {

		return getChildren(entry).stream().filter(PageNavigationEntry::isFolder).toList();
	}

	public List<PageNavigationEntry<?>> getLinks(PageNavigationEntry<?> entry) {

		return getChildren(entry).stream().filter(child -> !child.isFolder()).toList();
	}

	public List<PageNavigationEntry<?>> getChildren(PageNavigationEntry<?> entry) {

		return new ArrayList<PageNavigationEntry<?>>(entry.getChildren());
	}

	public Stream<PageNavigationEntry<?>> streamLinks(PageNavigationEntry<?> entry) {

		if (entry.isFolder()) {
			return getChildren(entry).stream().flatMap(this::streamLinks);
		} else {
			return Stream.of(entry);
		}
	}

	public int countLinks(PageNavigationEntry<?> entry) {

		return (int) streamLinks(entry).count();
	}

	/**
	 * Returns the folders below the given module, at any depth.
	 */
	public List<PageNavigationEntry<?>> getGroups(PageNavigationEntry<?> module) {

		List<PageNavigationEntry<?>> groups = new ArrayList<>();
		collectGroups(module, groups);
		return groups;
	}

	/**
	 * Returns the icon of the given folder, or else the icon of the module of
	 * its first page.
	 */
	public Optional<IResource> getIcon(PageNavigationEntry<?> folder) {

		return folder//
			.getModernIcon()
			.or(
				() -> streamLinks(folder)//
					.findFirst()
					.flatMap(link -> link.getModuleInstance().getModule())
					.map(IEmfModule::getModernIcon));
	}

	/**
	 * Returns the folder at the top level of the tree that contains the given
	 * entry.
	 */
	public Optional<PageNavigationEntry<?>> getTopFolder(PageNavigationEntry<?> entry) {

		return getAncestorAtDepth(entry, 1);
	}

	/**
	 * Returns the folder directly within a top level folder that contains the
	 * given entry.
	 */
	public Optional<PageNavigationEntry<?>> getModule(PageNavigationEntry<?> entry) {

		return getAncestorAtDepth(entry, 2);
	}

	/**
	 * Returns the folders between the given module and the given entry,
	 * excluding both.
	 */
	public List<PageNavigationEntry<?>> getGroupsOnPath(PageNavigationEntry<?> module, PageNavigationEntry<?> entry) {

		List<PageNavigationEntry<?>> groups = new ArrayList<>();
		PageNavigationEntry<?> parent = parents.get(entry);
		while (parent != null && parent != module && parent != root) {
			groups.add(parent);
			parent = parents.get(parent);
		}
		return parent == module? groups : List.of();
	}

	private Optional<PageNavigationEntry<?>> getAncestorAtDepth(PageNavigationEntry<?> entry, int depth) {

		List<PageNavigationEntry<?>> path = new ArrayList<>();
		PageNavigationEntry<?> parent = parents.get(entry);
		while (parent != null && parent != root) {
			path.add(0, parent);
			parent = parents.get(parent);
		}
		return path.size() >= depth? Optional.of(path.get(depth - 1)) : Optional.empty();
	}

	private void registerParents(PageNavigationEntry<?> entry) {

		for (PageNavigationEntry<?> child: getChildren(entry)) {
			parents.put(child, entry);
			registerParents(child);
		}
	}

	private void collectGroups(PageNavigationEntry<?> folder, List<PageNavigationEntry<?>> groups) {

		for (PageNavigationEntry<?> child: getFolders(folder)) {
			groups.add(child);
			collectGroups(child, groups);
		}
	}
}
