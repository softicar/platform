package com.softicar.platform.emf.object.table;

import com.softicar.platform.db.runtime.logic.DbObjectTable;
import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.emf.entity.table.EmfEntityTableSpecialization;
import com.softicar.platform.emf.object.IEmfObject;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;
import com.softicar.platform.emf.table.configuration.IEmfTableConfiguration;
import com.softicar.platform.emf.table.listener.EmfTableListener;
import com.softicar.platform.emf.table.specialization.IEmfTableSpecialization;

/**
 * Default implementation of {@link IEmfObjectTable}.
 *
 * @author Oliver Richers
 */
public class EmfObjectTable<O extends IEmfObject<O>, S> extends DbObjectTable<O> implements IEmfObjectTable<O, S> {

	private final EmfTableConfiguration<O, Integer, S> configuration;

	public EmfObjectTable(IDbObjectTableBuilder<O> builder) {

		this(builder, true);
	}

	public EmfObjectTable(IDbObjectTableBuilder<O> builder, boolean addTableListener) {

		super(builder);

		this.configuration = new EmfTableConfiguration<>(this);
		customizeEmfTableConfiguration(configuration);

		if (addTableListener) {
			addTableListener(new EmfTableListener<>(this));
		}
	}

	@Override
	public final IEmfTableConfiguration<O, Integer, S> getEmfTableConfiguration() {

		return configuration;
	}

	@Override
	public IEmfTableSpecialization<O, Integer, S> getTableSpecialization() {

		return new EmfEntityTableSpecialization<>(this);
	}
}
