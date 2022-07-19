package com.softicar.platform.emf.data.table;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.common.string.Imploder;
import com.softicar.platform.common.string.hash.Hash;
import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.dom.elements.popup.compositor.CurrentDomPopupCompositor;
import com.softicar.platform.dom.elements.tab.DomTabBar;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.emf.form.popup.EmfFormPopup;
import com.softicar.platform.emf.page.IEmfPageContentElement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

public class EmfDataTablePath {

	private final String pathString;

	public EmfDataTablePath(IEmfDataTable<?> table) {

		Objects.requireNonNull(table);

		var path = new PathList();
		IDomNode node = table;
		do {
			var n = node;
			Optional
				.<Path> empty()
				.or(() -> CastUtils.tryCast(n, IEmfPageContentElement.class).map(Path::new))
				.or(() -> CastUtils.tryCast(n, DomPopup.class).map(Path::new))
				.or(() -> CastUtils.tryCast(n, DomTabBar.class).map(Path::new))
				.or(() -> CastUtils.tryCast(n, IEmfDataTable.class).map(Path::new))
				.ifPresent(path::add);
			node = node.getParent();
		} while (node != null);

		this.pathString = path.reverse().implode();
	}

	@Override
	public String toString() {

		return pathString;
	}

	public String getHash() {

		return Hash.SHA1.getHashStringLC(pathString);
	}

	private static class PathList extends ArrayList<Path> {

		public static final String SEPARATOR = "/";

		public String implode() {

			return Imploder.implode(this, SEPARATOR);
		}

		public PathList reverse() {

			Collections.reverse(this);
			return this;
		}
	}

	private static class Path {

		private final String text;

		public Path(IEmfPageContentElement element) {

			this("page", element.getPage().map(it -> it.getAnnotatedUuid().toString()).orElse(""));
		}

		public Path(DomPopup popup) {

			this(getPopupPathList(popup));
		}

		public Path(DomTabBar tabBar) {

			this("tab", tabBar.getCurrentTab().getLabel().toString());
		}

		public Path(IEmfDataTable<?> dataTable) {

			this("table", dataTable.getDataTable().getIdentifier().toString());
		}

		public Path(String qualifier, String text) {

			text = text//
				.replace(PathList.SEPARATOR, "\\" + PathList.SEPARATOR)
				.replace("(", "\\(")
				.replace(")", "\\)");
			this.text = qualifier + "(" + text + ")";
		}

		public Path(PathList pathList) {

			this.text = pathList.implode();
		}

		@Override
		public String toString() {

			return text;
		}

		private static PathList getPopupPathList(DomPopup popup) {

			var path = new PathList();
			var hierarchyTree = CurrentDomPopupCompositor.get().getHierarchyTree();
			var child = popup;
			DomPopup parent = null;
			do {
				String popupIdentifier = CastUtils//
					.tryCast(child, EmfFormPopup.class)
					.map(it -> it.getTableRow().getClass().getCanonicalName())
					.orElse(child.getConfiguration().getCaption().orElse(IDisplayString.EMPTY).toString());
				path.add(new Path("popup", popupIdentifier));
				parent = hierarchyTree.getParentPopup(child).orElse(null);
				child = parent;
			} while (parent != null);
			return path.reverse();
		}
	}
}
