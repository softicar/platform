package com.softicar.platform.dom.elements.tab;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.element.IDomElement;
import com.softicar.platform.dom.elements.DomDiv;
import java.util.Objects;
import java.util.function.Supplier;

public class DomTabBuilder {

	private IDisplayString label;
	private Supplier<IDomElement> contentSupplier;

	public DomTabBuilder() {

		this.label = IDisplayString.EMPTY;
		this.contentSupplier = DomDiv::new;
	}

	public DomTabBuilder setLabel(IDisplayString label) {

		this.label = Objects.requireNonNull(label);
		return this;
	}

	public DomTabBuilder setContentSupplier(Supplier<IDomElement> contentSupplier) {

		this.contentSupplier = Objects.requireNonNull(contentSupplier);
		return this;
	}

	public DomTab build() {

		Objects.requireNonNull(label);
		Objects.requireNonNull(contentSupplier);
		return new Tab();
	}

	public DomTab buildAndAppendTo(DomTabBar tabBar) {

		return tabBar.appendTab(build());
	}

	private class Tab extends DomTab {

		private boolean initialized;

		public Tab() {

			super(label);
			this.initialized = false;
			setOnShowRefreshable(this::onShow);
		}

		private void onShow() {

			if (!initialized) {
				appendChild(contentSupplier.get());
				initialized = true;
			}
		}
	}
}
