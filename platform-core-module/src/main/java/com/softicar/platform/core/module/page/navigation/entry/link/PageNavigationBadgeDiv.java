package com.softicar.platform.core.module.page.navigation.entry.link;

import com.softicar.platform.core.module.CoreCssClasses;
import com.softicar.platform.dom.DomCssPseudoClasses;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.refresh.bus.IDomRefreshBusEvent;
import com.softicar.platform.dom.refresh.bus.IDomRefreshBusListener;
import com.softicar.platform.dom.style.ICssClass;
import com.softicar.platform.emf.page.badge.EmfPageBadge;
import com.softicar.platform.emf.validation.result.EmfDiagnosticLevel;
import java.util.Objects;

public class PageNavigationBadgeDiv extends DomDiv implements IDomRefreshBusListener {

	private final EmfPageBadge badge;

	public PageNavigationBadgeDiv(EmfPageBadge badge) {

		this.badge = Objects.requireNonNull(badge);
		refresh();
		badge.getTitle().ifPresent(this::setTitle);
	}

	@Override
	public void refresh(IDomRefreshBusEvent event) {

		if (badge.needsUpdate(event)) {
			refresh();
		}
	}

	private void refresh() {

		int count = badge.getCount();
		setDisplayNone(count == 0 && badge.isHideZero());

		removeChildren();
		appendChild("" + count);

		unsetCssClass();
		addCssClass(getCssClass(badge.getLevel()));
		addCssClass(CoreCssClasses.PAGE_NAVIGATION_BADGE);
	}

	private ICssClass getCssClass(EmfDiagnosticLevel level) {

		return switch (level) {
		case INFO -> DomCssPseudoClasses.INFO;
		case WARNING -> DomCssPseudoClasses.WARNING;
		case ERROR -> DomCssPseudoClasses.ERROR;
		};
	}
}
