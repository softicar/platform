package com.softicar.platform.core.module.component.external;

import com.softicar.platform.common.container.comparator.OrderDirection;
import com.softicar.platform.common.container.data.table.DataTableIdentifier;
import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.common.container.data.table.in.memory.AbstractInMemoryDataTable;
import com.softicar.platform.common.core.i18n.IDisplayable;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.component.external.type.font.ExternalFontsFetcher;
import com.softicar.platform.core.module.component.external.type.library.ExternalLibrariesFromClasspathFetcher;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.emf.data.table.EmfDataTableDivBuilder;
import java.util.ArrayList;
import java.util.List;

class ExternalComponentsPageNode extends DomDiv {

	public ExternalComponentsPageNode() {

		var dataTable = new DataTable();
		new EmfDataTableDivBuilder<>(dataTable)//
			.setOrderBy(dataTable.getNameColumn(), OrderDirection.ASCENDING)
			.buildAndAppendTo(this);
	}

	private static class DataTable extends AbstractInMemoryDataTable<IExternalComponent> {

		private final List<IExternalComponent> rows;
		private final IDataTableColumn<IExternalComponent, String> nameColumn;

		public DataTable() {

			this.rows = new ArrayList<>();
			this.rows.addAll(new ExternalLibrariesFromClasspathFetcher().fetchAll());
			this.rows.addAll(new ExternalFontsFetcher().fetchAll());

			newColumn(IDisplayable.class)//
				.setGetter(IExternalComponent::getType)
				.setTitle(CoreI18n.TYPE)
				.addColumn();

			this.nameColumn = newColumn(String.class)//
				.setGetter(IExternalComponent::getName)
				.setTitle(CoreI18n.NAME)
				.addColumn();

			newColumn(String.class)//
				.setGetter(IExternalComponent::getVersion)
				.setTitle(CoreI18n.VERSION)
				.addColumn();

			newColumn(String.class)//
				.setGetter(IExternalComponent::getDescription)
				.setTitle(CoreI18n.DESCRIPTION)
				.addColumn();

			newColumn(String.class)//
				.setGetter(IExternalComponent::getLicense)
				.setTitle(CoreI18n.LICENSE)
				.addColumn();
		}

		@Override
		public DataTableIdentifier getIdentifier() {

			return new DataTableIdentifier("21362d1f-33a7-4a2c-9f54-5c8c1fa213c8");
		}

		@Override
		protected Iterable<IExternalComponent> getTableRows() {

			return rows;
		}

		public IDataTableColumn<IExternalComponent, String> getNameColumn() {

			return nameColumn;
		}
	}
}
