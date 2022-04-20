package com.softicar.platform.core.module.page.navigation;

import com.softicar.platform.common.container.set.Sets;
import com.softicar.platform.core.module.page.navigation.link.PageNavigationLink;
import com.softicar.platform.core.module.page.navigation.link.display.PageNavigationFolderDiv;
import com.softicar.platform.core.module.user.CurrentUser;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class PageNavigationFolderController {

	private final PageNavigationDiv navigationDiv;
	private final Set<PageNavigationFolderDiv> openFolderDivs;

	public PageNavigationFolderController(PageNavigationDiv navigationDiv) {

		this.navigationDiv = navigationDiv;
		this.openFolderDivs = new HashSet<>();
	}

	public PageNavigationFolderDiv createFolderDiv(PageNavigationFolderDiv parentFolderDiv, PageNavigationLink<?> folderLink) {

		return new PageNavigationFolderDiv(navigationDiv, parentFolderDiv, folderLink);
	}

	public void toggleFolder(PageNavigationFolderDiv folderDiv) {

		if (openFolderDivs.contains(folderDiv)) {
			close(folderDiv);
		} else {
			openFolder(folderDiv);
		}
	}

	public void openFolder(PageNavigationFolderDiv folderDiv) {

		Set<PageNavigationFolderDiv> desiredFolderDivs = new HashSet<>();
		while (folderDiv != null) {
			desiredFolderDivs.add(folderDiv);
			folderDiv = folderDiv.getParentFolderDiv();
		}
		if (CurrentUser.get().isAutomaticallyCollapseFolders()) {
			Sets.difference(openFolderDivs, desiredFolderDivs).forEach(this::close);
		}
		Sets.difference(desiredFolderDivs, openFolderDivs).forEach(this::open);
	}

	private void open(PageNavigationFolderDiv folderDiv) {

		folderDiv.open();
		openFolderDivs.add(folderDiv);
	}

	private void close(PageNavigationFolderDiv folderDiv) {

		new FolderCloser(CurrentUser.get().isRecursivelyCollapseFolders()).close(folderDiv);
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
