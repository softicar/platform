package com.softicar.platform.core.module.page.navigation.modern;

import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.core.module.CoreTestMarker;
import com.softicar.platform.core.module.file.stored.StoredFileResource;
import com.softicar.platform.core.module.page.PageTheme;
import com.softicar.platform.core.module.page.PageUrlBuilder;
import com.softicar.platform.core.module.page.navigation.PageNavigationController;
import com.softicar.platform.core.module.page.navigation.entry.PageNavigationEntry;
import com.softicar.platform.core.module.page.navigation.entry.link.PageNavigationBadgeDiv;
import com.softicar.platform.core.module.user.CurrentUser;
import com.softicar.platform.dom.DomCssPseudoClasses;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomImage;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.event.IDomClickEventHandler;
import com.softicar.platform.dom.event.IDomEnterKeyEventHandler;
import com.softicar.platform.dom.event.IDomEscapeKeyEventHandler;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.event.IDomInputEventHandler;
import com.softicar.platform.dom.input.DomTextInput;
import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

/**
 * The navigation of the {@link PageTheme#MODERN} theme.
 * <p>
 * An icon rail on the left holds the modules of the current tenant; the panel
 * next to it shows one of three views:
 * <ul>
 * <li>all modules of the tenant, with the number of their pages, and a search
 * field,</li>
 * <li>one module with its pages in collapsible groups,</li>
 * <li>the pages of all modules that match the search text.</li>
 * </ul>
 * Tenants, modules and groups are interpreted by {@link ModernNavigationModel}.
 * Pages are opened through the {@link PageNavigationController}; the view
 * follows the opened page.
 */
public class ModernNavigationDiv extends DomDiv {

	/** Modules with more pages start with their groups collapsed. */
	private static final int LARGE_MODULE_PAGE_COUNT = 15;

	private final PageNavigationController controller;
	private final INullaryVoidFunction collapseFunction;
	private final ModernNavigationModel model;
	private final DomDiv rail;
	private final DomDiv tenantArea;
	private final SearchInput searchInput;
	private final DomDiv body;
	private final Set<PageNavigationEntry<?>> openGroups;
	private PageNavigationEntry<?> tenant;
	private PageNavigationEntry<?> module;
	private PageNavigationEntry<?> currentLink;
	private View view;
	private boolean tenantMenuOpen;

	private enum View {
		ALL,
		MODULE,
		SEARCH
	}

	public ModernNavigationDiv(PageNavigationController controller, PageNavigationEntry<?> rootEntry, INullaryVoidFunction collapseFunction) {

		this.controller = controller;
		this.collapseFunction = collapseFunction;
		this.model = new ModernNavigationModel(rootEntry);
		this.openGroups = Collections.newSetFromMap(new IdentityHashMap<>());
		this.tenant = model.getTenants().stream().findFirst().orElse(model.getSeparateSystemFolder().orElse(null));
		this.module = null;
		this.currentLink = null;
		this.view = View.ALL;
		this.tenantMenuOpen = false;

		setCssClass(ModernNavigationCssClasses.MODERN_NAVIGATION);

		this.rail = appendChild(new DomDiv());
		rail.setCssClass(ModernNavigationCssClasses.MODERN_NAVIGATION_RAIL);

		DomDiv panel = appendChild(new DomDiv());
		panel.setCssClass(ModernNavigationCssClasses.MODERN_NAVIGATION_PANEL);
		this.tenantArea = panel.appendChild(new DomDiv());
		tenantArea.setCssClass(ModernNavigationCssClasses.MODERN_NAVIGATION_TENANT);
		this.searchInput = panel.appendChild(new SearchInput());
		this.body = panel.appendChild(new DomDiv());
		body.setCssClass(ModernNavigationCssClasses.MODERN_NAVIGATION_BODY);
		panel.appendChild(new FooterDiv());

		controller.addPageOpenListener(this::handlePageOpened);
		refresh();
	}

	// -------------------- state changes -------------------- //

	private void handlePageOpened(PageNavigationEntry<?> link) {

		this.currentLink = link;
		this.tenantMenuOpen = false;
		model.getTopFolder(link).filter(this::isSelectableTenant).ifPresent(top -> this.tenant = top);
		searchInput.setValue("");
		model.getModule(link).ifPresentOrElse(this::showModule, this::showAll);
	}

	private void showAll() {

		this.view = View.ALL;
		this.module = null;
		refresh();
	}

	private void showModule(PageNavigationEntry<?> module) {

		if (this.module != module) {
			openGroups.clear();
			if (model.countLinks(module) <= LARGE_MODULE_PAGE_COUNT) {
				openGroups.addAll(model.getGroups(module));
			}
		}
		if (currentLink != null) {
			openGroups.addAll(model.getGroupsOnPath(module, currentLink));
		}
		this.module = module;
		this.view = View.MODULE;
		refresh();
	}

	private void selectTenant(PageNavigationEntry<?> tenant) {

		this.tenant = tenant;
		this.tenantMenuOpen = false;
		searchInput.setValue("");
		showAll();
	}

	private void toggleGroup(PageNavigationEntry<?> group) {

		if (!openGroups.remove(group)) {
			openGroups.add(group);
		}
		refreshBody();
	}

	private void search() {

		if (searchInput.getValueTextTrimmed().isEmpty()) {
			this.view = module != null? View.MODULE : View.ALL;
		} else {
			this.view = View.SEARCH;
		}
		refreshRail();
		refreshBody();
		setAttribute("data-view", getViewName());
	}

	private boolean isSelectableTenant(PageNavigationEntry<?> folder) {

		return getTenantOptions().contains(folder);
	}

	private List<PageNavigationEntry<?>> getTenantOptions() {

		List<PageNavigationEntry<?>> options = new ArrayList<>(model.getTenants());
		model.getSeparateSystemFolder().ifPresent(options::add);
		return options;
	}

	// -------------------- rendering -------------------- //

	private void refresh() {

		setAttribute("data-view", getViewName());
		refreshRail();
		refreshTenantArea();
		refreshBody();
	}

	private String getViewName() {

		return view.name().toLowerCase(Locale.ROOT);
	}

	private void refreshRail() {

		rail.removeChildren();
		rail.appendChild(new RailLogo());
		rail.appendChild(new DomDiv()).setCssClass(ModernNavigationCssClasses.MODERN_NAVIGATION_RAIL_DIVIDER);
		rail.appendChild(new RailToggle());
		rail.appendChild(new DomDiv()).setCssClass(ModernNavigationCssClasses.MODERN_NAVIGATION_RAIL_DIVIDER);
		rail.appendChild(new RailAllItem());
		if (tenant != null) {
			model.getFolders(tenant).forEach(folder -> rail.appendChild(new RailModuleItem(folder)));
		}
	}

	private void refreshTenantArea() {

		tenantArea.removeChildren();
		if (tenant != null) {
			tenantArea.appendChild(new TenantCard());
			if (tenantMenuOpen) {
				tenantArea.appendChild(new TenantMenu());
			}
		}
	}

	private void refreshBody() {

		body.removeChildren();
		switch (view) {
		case ALL -> appendAllView();
		case MODULE -> appendModuleView();
		case SEARCH -> appendSearchView();
		}
	}

	private void appendAllView() {

		if (tenant != null) {
			model.getFolders(tenant).forEach(folder -> body.appendChild(new ModuleHead(folder, false)));
			model.getLinks(tenant).forEach(link -> body.appendChild(new LinkRow(link)));
		}
		// as in the ERP: the general pages (profile, start page, session) below the modules, set apart by a line
		if (!model.getRootLinks().isEmpty()) {
			DomDiv rootLinks = body.appendChild(new DomDiv());
			rootLinks.setCssClass(ModernNavigationCssClasses.MODERN_NAVIGATION_ROOT_LINKS);
			model.getRootLinks().forEach(link -> rootLinks.appendChild(new LinkRow(link)));
		}
	}

	private void appendModuleView() {

		body.appendChild(new ModuleHead(module, true));
		appendFolderContent(body, module);
	}

	private void appendFolderContent(DomDiv container, PageNavigationEntry<?> folder) {

		for (PageNavigationEntry<?> child: model.getChildren(folder)) {
			if (child.isFolder()) {
				container.appendChild(new GroupDiv(child));
			} else {
				container.appendChild(new LinkRow(child));
			}
		}
	}

	private void appendSearchView() {

		String text = searchInput.getValueTextTrimmed().toLowerCase();
		appendMatches(null, model.getRootLinks(), text);
		if (tenant != null) {
			for (PageNavigationEntry<?> folder: model.getFolders(tenant)) {
				appendMatches(folder, model.streamLinks(folder).toList(), text);
			}
			appendMatches(null, model.getLinks(tenant), text);
		}
	}

	private void appendMatches(PageNavigationEntry<?> folder, List<PageNavigationEntry<?>> links, String text) {

		var matches = links.stream().filter(link -> link.getTitle().toLowerCase().contains(text)).toList();
		if (!matches.isEmpty()) {
			if (folder != null) {
				body.appendChild(new SectionTitle(folder));
			}
			matches.forEach(link -> body.appendChild(new LinkRow(link)));
		}
	}

	private Optional<PageNavigationEntry<?>> getFirstMatch() {

		String text = searchInput.getValueTextTrimmed().toLowerCase();
		if (text.isEmpty()) {
			return Optional.empty();
		}
		List<PageNavigationEntry<?>> candidates = new ArrayList<>(model.getRootLinks());
		if (tenant != null) {
			candidates.addAll(model.streamLinks(tenant).toList());
		}
		return candidates.stream().filter(link -> link.getTitle().toLowerCase().contains(text)).findFirst();
	}

	private DomImage createIcon(IResource resource) {

		DomImage image = new DomImage(resource);
		image.setCssClass(ModernNavigationCssClasses.MODERN_NAVIGATION_MODULE_ICON);
		markIcon(image, resource);
		return image;
	}

	/** the file name of the icon, so that the theme can tint the plain platform icons and leave coloured ones */
	private static void markIcon(DomImage image, IResource resource) {

		resource.getFilename().map(name -> name.replaceFirst("\\.[A-Za-z0-9]+$", "")).ifPresent(name -> image.setAttribute("data-icon", name));
	}

	private DomDiv createCount(int count) {

		DomDiv countDiv = new DomDiv();
		countDiv.setCssClass(ModernNavigationCssClasses.MODERN_NAVIGATION_COUNT);
		countDiv.appendText("" + count);
		return countDiv;
	}

	// -------------------- rail -------------------- //

	private class RailLogo extends DomDiv {

		public RailLogo() {

			setCssClass(ModernNavigationCssClasses.MODERN_NAVIGATION_RAIL_LOGO);
			IResource logo = Optional//
				.ofNullable(AGCoreModuleInstance.getInstance().getPortalLogo())
				.<IResource> map(StoredFileResource::new)
				.orElseGet(CoreImages.PAGE_ICON::getResource);
			appendChild(new DomImage(logo));
		}
	}

	private class RailToggle extends DomDiv implements IDomClickEventHandler {

		public RailToggle() {

			setCssClass(ModernNavigationCssClasses.MODERN_NAVIGATION_RAIL_TOGGLE);
			addMarker(CoreTestMarker.MODERN_NAVIGATION_RAIL_TOGGLE);
			setTitle(CoreI18n.COLLAPSE_OR_EXPAND_NAVIGATION);
			appendChild(new DomDiv());
		}

		@Override
		public void handleClick(IDomEvent event) {

			collapseFunction.apply();
		}
	}

	private class RailAllItem extends DomDiv implements IDomClickEventHandler {

		public RailAllItem() {

			setCssClass(ModernNavigationCssClasses.MODERN_NAVIGATION_RAIL_ITEM, ModernNavigationCssClasses.MODERN_NAVIGATION_RAIL_ALL);
			setTitle(CoreI18n.ALL_MODULES);
			appendChild(new DomDiv());
			if (view != View.MODULE) {
				addCssClass(DomCssPseudoClasses.SELECTED);
			}
		}

		@Override
		public void handleClick(IDomEvent event) {

			searchInput.setValue("");
			showAll();
		}
	}

	private class RailModuleItem extends DomDiv implements IDomClickEventHandler {

		private final PageNavigationEntry<?> folder;

		public RailModuleItem(PageNavigationEntry<?> folder) {

			this.folder = folder;
			setCssClass(ModernNavigationCssClasses.MODERN_NAVIGATION_RAIL_ITEM);
			setAttribute("data-module", "1");
			setTitle(folder.getTitle());
			model.getIcon(folder).ifPresent(icon -> {
				DomImage image = appendChild(new DomImage(icon));
				markIcon(image, icon);
			});
			if (folder == module && view == View.MODULE) {
				addCssClass(DomCssPseudoClasses.SELECTED);
			}
		}

		@Override
		public void handleClick(IDomEvent event) {

			searchInput.setValue("");
			showModule(folder);
		}
	}

	// -------------------- tenant -------------------- //

	private class TenantCard extends DomDiv implements IDomClickEventHandler {

		private final boolean selectable;

		public TenantCard() {

			this.selectable = getTenantOptions().size() > 1;
			setCssClass(ModernNavigationCssClasses.MODERN_NAVIGATION_TENANT_CARD);
			if (tenantMenuOpen) {
				addCssClass(DomCssPseudoClasses.ACTIVE);
			}

			DomDiv text = appendChild(new DomDiv());
			DomDiv label = text.appendChild(new DomDiv());
			label.setCssClass(ModernNavigationCssClasses.MODERN_NAVIGATION_TENANT_LABEL);
			label.appendText((model.isSystemFolder(tenant)? CoreI18n.SYSTEM : CoreI18n.COMPANY).toString());
			DomDiv name = text.appendChild(new DomDiv());
			name.setCssClass(ModernNavigationCssClasses.MODERN_NAVIGATION_TENANT_NAME);
			name.appendText(tenant.getTitle());
			name.setTitle(tenant.getTitle());

			if (selectable) {
				appendChild(new DomDiv()).setCssClass(ModernNavigationCssClasses.MODERN_NAVIGATION_TENANT_CHEVRON);
			}
		}

		@Override
		public void handleClick(IDomEvent event) {

			if (selectable) {
				tenantMenuOpen = !tenantMenuOpen;
				refreshTenantArea();
			}
		}
	}

	private class TenantMenu extends DomDiv {

		public TenantMenu() {

			setCssClass(ModernNavigationCssClasses.MODERN_NAVIGATION_TENANT_MENU);
			getTenantOptions().forEach(option -> appendChild(new TenantOption(option)));
		}
	}

	private class TenantOption extends DomDiv implements IDomClickEventHandler {

		private final PageNavigationEntry<?> option;

		public TenantOption(PageNavigationEntry<?> option) {

			this.option = option;
			setCssClass(ModernNavigationCssClasses.MODERN_NAVIGATION_TENANT_OPTION);
			appendText(option.getTitle());
			if (option == tenant) {
				addCssClass(DomCssPseudoClasses.SELECTED);
			}
		}

		@Override
		public void handleClick(IDomEvent event) {

			selectTenant(option);
		}
	}

	// -------------------- panel content -------------------- //

	private class SearchInput extends DomTextInput implements IDomInputEventHandler, IDomEnterKeyEventHandler, IDomEscapeKeyEventHandler {

		public SearchInput() {

			setCssClass(ModernNavigationCssClasses.MODERN_NAVIGATION_SEARCH);
			addMarker(CoreTestMarker.MODERN_NAVIGATION_SEARCH);
			setAttribute("spellcheck", "false");
			setPlaceholder(CoreI18n.SEARCH);
		}

		@Override
		public void handleInput(IDomEvent event) {

			search();
		}

		@Override
		public void handleEnterKey(IDomEvent event) {

			getFirstMatch().ifPresent(controller::openPage);
		}

		@Override
		public void handleEscapeKey(IDomEvent event) {

			setValue("");
			search();
		}
	}

	private class ModuleHead extends DomDiv implements IDomClickEventHandler {

		private final PageNavigationEntry<?> folder;
		private final boolean opened;

		public ModuleHead(PageNavigationEntry<?> folder, boolean opened) {

			this.folder = folder;
			this.opened = opened;
			setCssClass(ModernNavigationCssClasses.MODERN_NAVIGATION_MODULE_HEAD);
			addMarker(CoreTestMarker.MODERN_NAVIGATION_MODULE_HEAD);
			if (opened) {
				addCssClass(DomCssPseudoClasses.SELECTED);
			}
			model.getIcon(folder).map(ModernNavigationDiv.this::createIcon).ifPresent(this::appendChild);
			DomDiv title = appendChild(new DomDiv());
			title.setCssClass(ModernNavigationCssClasses.MODERN_NAVIGATION_MODULE_TITLE);
			title.appendText(folder.getTitle());
			setTitle(folder.getTitle());
			appendChild(createCount(model.countLinks(folder)));
		}

		@Override
		public void handleClick(IDomEvent event) {

			if (opened) {
				showAll();
			} else {
				showModule(folder);
			}
		}
	}

	private class SectionTitle extends DomDiv {

		public SectionTitle(PageNavigationEntry<?> folder) {

			setCssClass(ModernNavigationCssClasses.MODERN_NAVIGATION_SECTION_TITLE);
			model.getIcon(folder).map(ModernNavigationDiv.this::createIcon).ifPresent(this::appendChild);
			appendText(folder.getTitle());
		}
	}

	private class GroupDiv extends DomDiv {

		public GroupDiv(PageNavigationEntry<?> group) {

			setCssClass(ModernNavigationCssClasses.MODERN_NAVIGATION_GROUP);
			boolean open = openGroups.contains(group);
			if (open) {
				addCssClass(DomCssPseudoClasses.SELECTED);
			}
			appendChild(new GroupHead(group));
			if (open) {
				DomDiv groupBody = appendChild(new DomDiv());
				groupBody.setCssClass(ModernNavigationCssClasses.MODERN_NAVIGATION_GROUP_BODY);
				appendFolderContent(groupBody, group);
			}
		}
	}

	private class GroupHead extends DomDiv implements IDomClickEventHandler {

		private final PageNavigationEntry<?> group;

		public GroupHead(PageNavigationEntry<?> group) {

			this.group = group;
			setCssClass(ModernNavigationCssClasses.MODERN_NAVIGATION_GROUP_HEAD);
			addMarker(CoreTestMarker.MODERN_NAVIGATION_GROUP_HEAD);
			appendChild(new DomDiv()).setCssClass(ModernNavigationCssClasses.MODERN_NAVIGATION_GROUP_CHEVRON);
			DomDiv title = appendChild(new DomDiv());
			title.setCssClass(ModernNavigationCssClasses.MODERN_NAVIGATION_GROUP_TITLE);
			title.appendText(group.getTitle());
			setTitle(group.getTitle());
			appendChild(createCount(model.countLinks(group)));
		}

		@Override
		public void handleClick(IDomEvent event) {

			toggleGroup(group);
		}
	}

	private class LinkRow extends DomDiv {

		public LinkRow(PageNavigationEntry<?> link) {

			setCssClass(ModernNavigationCssClasses.MODERN_NAVIGATION_LINK_ROW);
			if (link == currentLink) {
				addCssClass(DomCssPseudoClasses.SELECTED);
			}
			appendChild(new LinkTitle(link));
			link.getPageBadges().stream().map(PageNavigationBadgeDiv::new).forEach(this::appendChild);
			appendChild(new NewTabButton(link));
		}
	}

	private class LinkTitle extends DomDiv implements IDomClickEventHandler {

		private final PageNavigationEntry<?> link;

		public LinkTitle(PageNavigationEntry<?> link) {

			this.link = link;
			setCssClass(ModernNavigationCssClasses.MODERN_NAVIGATION_LINK);
			addMarker(CoreTestMarker.MODERN_NAVIGATION_LINK);
			appendText(link.getTitle());
		}

		@Override
		public void handleClick(IDomEvent event) {

			controller.openPage(link);
		}
	}

	private class NewTabButton extends DomButton {

		public NewTabButton(PageNavigationEntry<?> link) {

			setCssClass(ModernNavigationCssClasses.MODERN_NAVIGATION_NEW_TAB_BUTTON);
			setIcon(CoreImages.PAGE_NAVIGATION_OPEN_IN_NEW_TAB_ICON.getResource());
			setTitle(CoreI18n.CLICK_TO_OPEN_IN_NEW_TAB);
			setTabIndex(-1);
			setExternalLink(new PageUrlBuilder<>(link).build().getStartingFromPath());
		}
	}

	private class FooterDiv extends DomDiv {

		public FooterDiv() {

			setCssClass(ModernNavigationCssClasses.MODERN_NAVIGATION_FOOTER);
			Optional.ofNullable(CurrentUser.get()).ifPresent(user -> appendText(user.getFirstAndLastName()));
		}
	}
}
