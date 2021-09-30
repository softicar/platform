package com.softicar.platform.emf.record.table;

import com.softicar.platform.db.runtime.record.DbRecordTable;
import com.softicar.platform.db.runtime.table.IDbTableBuilder;
import com.softicar.platform.emf.record.IEmfRecord;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;
import com.softicar.platform.emf.table.configuration.IEmfTableConfiguration;
import com.softicar.platform.emf.table.listener.EmfTableListener;
import com.softicar.platform.emf.table.specialization.IEmfTableSpecialization;

public class EmfRecordTable<R extends IEmfRecord<R, P>, P, S> extends DbRecordTable<R, P> implements IEmfRecordTable<R, P, S> {

	protected final EmfTableConfiguration<R, P, S> configuration;

	public EmfRecordTable(IDbTableBuilder<R, P> builder) {

		super(builder);

		this.configuration = new EmfTableConfiguration<>(this);

		addTableListener(new EmfTableListener<>(this));
		customizeEmfTableConfiguration(configuration);
	}

	@Override
	public IEmfTableConfiguration<R, P, S> getEmfTableConfiguration() {

		return configuration;
	}

	@Override
	public IEmfTableSpecialization<R, P, S> getTableSpecialization() {

		return new EmfRecordTableSpecialization<>(this);
	}
}
