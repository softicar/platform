package com.softicar.platform.core.module.page.navigation;

import com.softicar.platform.common.code.reference.point.SourceCodeReferencePoints;
import com.softicar.platform.common.container.set.Sets;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.page.navigation.entry.PageNavigationEntry;
import com.softicar.platform.core.module.page.navigation.entry.PageNavigationEntryKey;
import com.softicar.platform.core.module.page.navigation.entry.folder.PageNavigationFolderDiv;
import com.softicar.platform.core.module.page.navigation.entry.link.PageNavigationLinkDiv;
import com.softicar.platform.core.module.start.page.StartPage;
import com.softicar.platform.core.module.user.CurrentUser;
import com.softicar.platform.dom.DomCssPseudoClasses;
import com.softicar.platform.dom.elements.dialog.DomModalAlertDialog;
import com.softicar.platform.emf.page.IEmfPage;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class PageNavigationController {

	private final Consumer<PageNavigationEntry<?>> pageOpener;
	private final Map<PageNavigationEntryKey, PageNavigationEntry<?>> linkEntryMap;
	private final Map<PageNavigationEntryKey, PageNavigationLinkDiv> linkDivMap;
	private final Set<PageNavigationFolderDiv> openFolderDivs;
	private PageNavigationLinkDiv currentLinkDiv;

	public PageNavigationController(Consumer<PageNavigationEntry<?>> pageOpener, PageNavigationEntry<?> rootEntry) {

		this.pageOpener = pageOpener;
		this.linkEntryMap = new HashMap<>();
		this.linkDivMap = new HashMap<>();
		this.openFolderDivs = new HashSet<>();
		this.currentLinkDiv = null;

		registerTransitiveLinkEntries(rootEntry);
	}

	public PageNavigationLinkDiv registerLinkDiv(PageNavigationLinkDiv linkDiv) {

		linkDivMap.put(new PageNavigationEntryKey(linkDiv.getEntry()), linkDiv);
		return linkDiv;
	}

	public void openPage(IEmfPage<?> page, Integer moduleInstanceId) {

		Optional//
			.ofNullable(linkEntryMap.get(new PageNavigationEntryKey(page, moduleInstanceId)))
			.ifPresentOrElse(this::openPage, this::openStartPageForInaccessiblePage);
	}

	public void openPage(PageNavigationEntry<?> linkEntry) {

		pageOpener.accept(linkEntry);

		unselectCurrentLinkDiv();
		Optional//
			.ofNullable(linkDivMap.get(new PageNavigationEntryKey(linkEntry)))
			.ifPresent(linkDiv -> {
				selectLinkDiv(linkDiv);
				openFolder(linkDiv.getFolderDiv());
			});
	}

	public void openStartPageForInaccessiblePage() {

		openStartPageWithAlert(CoreI18n.PAGE_NOT_ACCESSIBLE);
	}

	public void openStartPageForNonExistingPage() {

		openStartPageWithAlert(CoreI18n.PAGE_DOES_NOT_EXIST);
	}

	public void toggleFolder(PageNavigationFolderDiv folderDiv) {

		if (openFolderDivs.contains(folderDiv)) {
			close(folderDiv);
		} else {
			openFolder(folderDiv);
		}
	}

	private void registerTransitiveLinkEntries(PageNavigationEntry<?> entry) {

		if (entry.isFolder()) {
			entry.getChildren().forEach(this::registerTransitiveLinkEntries);
		} else {
			linkEntryMap.put(new PageNavigationEntryKey(entry), entry);
		}
	}

	private void openFolder(PageNavigationFolderDiv folderDiv) {

		Set<PageNavigationFolderDiv> desiredFolderDivs = new HashSet<>();
		while (folderDiv != null) {
			desiredFolderDivs.add(folderDiv);
			folderDiv = folderDiv.getParentFolderDiv();
		}
		if (CurrentUser.get().getPreferences().automaticallyCollapseFolders) {
			Sets.difference(openFolderDivs, desiredFolderDivs).forEach(this::close);
		}
		Sets.difference(desiredFolderDivs, openFolderDivs).forEach(this::open);
	}

	private void open(PageNavigationFolderDiv folderDiv) {

		folderDiv.open();
		openFolderDivs.add(folderDiv);
	}

	private void close(PageNavigationFolderDiv folderDiv) {

		new FolderCloser(CurrentUser.get().getPreferences().recursivelyCollapseFolders).close(folderDiv);
	}

	private void openStartPageWithAlert(IDisplayString message) {

		openPage(SourceCodeReferencePoints.getReferencePoint(StartPage.class), AGCoreModuleInstance.getInstance().getId());
		new DomModalAlertDialog(message).open();
	}

	private void unselectCurrentLinkDiv() {

		Optional//
			.ofNullable(currentLinkDiv)
			.ifPresent(display -> display.removeCssClass(DomCssPseudoClasses.SELECTED));
		this.currentLinkDiv = null;
	}

	private void selectLinkDiv(PageNavigationLinkDiv linkDiv) {

		linkDiv.addCssClass(DomCssPseudoClasses.SELECTED);
		this.currentLinkDiv = linkDiv;
	}

	private class FolderCloser {

		private final boolean recursive;
		private final Map<PageNavigationFolderDiv, List<PageNavigationFolderDiv>> parentToChildFolders;

		public FolderCloser(boolean recursive) {

			this.recursive = recursive;
			this.parentToChildFolders = openFolderDivs//
				.stream()
				.filter(this::hasParentFolder)
				.collect(Collectors.groupingBy(PageNavigationFolderDiv::getParentFolderDiv, HashMap::new, Collectors.toList()));
		}

		public void close(PageNavigationFolderDiv folderDiv) {

			if (recursive) {
				Optional.ofNullable(parentToChildFolders.get(folderDiv)).ifPresent(children -> children.forEach(this::close));
			}
			openFolderDivs.remove(folderDiv);
			folderDiv.close();
		}

		private boolean hasParentFolder(PageNavigationFolderDiv folderDiv) {

			return folderDiv.getParentFolderDiv() != null;
		}
	}
}
