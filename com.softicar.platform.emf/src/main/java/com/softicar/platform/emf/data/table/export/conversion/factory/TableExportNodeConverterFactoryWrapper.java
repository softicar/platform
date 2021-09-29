package com.softicar.platform.emf.data.table.export.conversion.factory;

import com.softicar.platform.common.core.entity.IEntity;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.item.BasicItemComparator;
import com.softicar.platform.common.core.item.IBasicItem;

public class TableExportNodeConverterFactoryWrapper<CT> implements IEntity {

	private final ITableExportNodeConverterFactory<CT> converterFactory;
	private final int id;

	public TableExportNodeConverterFactoryWrapper(ITableExportNodeConverterFactory<CT> converterFactory, int id) {

		this.converterFactory = converterFactory;
		this.id = id;
	}

	public ITableExportNodeConverterFactory<CT> getConverterFactory() {

		return converterFactory;
	}

	@Override
	public Integer getId() {

		return id;
	}

	@Override
	public int compareTo(IBasicItem o) {

		return BasicItemComparator.get().compare(this, o);
	}

	@Override
	public IDisplayString toDisplayWithoutId() {

		return converterFactory.getConverterDisplayString();
	}
}
