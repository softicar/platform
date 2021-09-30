package com.softicar.platform.emf.form.tab;

import com.softicar.platform.dom.elements.tab.DomTab;
import com.softicar.platform.dom.elements.tab.DomTabBar;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.form.IEmfForm;
import com.softicar.platform.emf.form.IEmfFormBody;
import com.softicar.platform.emf.form.tab.factory.IEmfFormTabConfiguration;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public class EmfFormTabBar<R extends IEmfTableRow<R, ?>> extends DomTabBar {

	public EmfFormTabBar(IEmfForm<R> form, IEmfFormTabConfiguration<R> tabConfiguration, IEmfFormBody<R> mainBody) {

		appendTab(new PropertiesTab(mainBody));
		appendTabs(form, tabConfiguration);
	}

	private void appendTabs(IEmfForm<R> form, IEmfFormTabConfiguration<R> tabConfiguration) {

		tabConfiguration//
			.createVisibleTabs(form)
			.forEach(this::appendTab);
	}

	private class PropertiesTab extends DomTab {

		public PropertiesTab(IEmfFormBody<R> mainBody) {

			super(EmfI18n.PROPERTIES);
			appendChild(mainBody);
		}
	}
}
