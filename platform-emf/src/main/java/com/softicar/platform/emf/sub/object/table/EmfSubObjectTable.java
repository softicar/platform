package com.softicar.platform.emf.sub.object.table;

import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.runtime.field.IDbForeignRowField;
import com.softicar.platform.db.runtime.object.sub.DbSubObjectTable;
import com.softicar.platform.db.runtime.object.sub.IDbSubObjectTableBuilder;
import com.softicar.platform.emf.attribute.EmfInheritedAttribute;
import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.deactivation.EmfSubObjectDeactivationStrategy;
import com.softicar.platform.emf.entity.IEmfEntity;
import com.softicar.platform.emf.entity.table.IEmfEntityTable;
import com.softicar.platform.emf.sub.object.IEmfSubObject;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;
import com.softicar.platform.emf.table.configuration.IEmfTableConfiguration;
import com.softicar.platform.emf.table.listener.EmfTableListener;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import com.softicar.platform.emf.table.specialization.IEmfTableSpecialization;
import java.util.ArrayList;
import java.util.Collection;

public class EmfSubObjectTable<O extends IEmfSubObject<O, B>, B extends IEmfEntity<B, P>, P, S> extends DbSubObjectTable<O, B, P>
		implements IEmfSubObjectTable<O, B, P, S> {

	private final EmfTableConfiguration<O, B, S> configuration;

	public EmfSubObjectTable(IDbSubObjectTableBuilder<O, B, P> builder) {

		super(builder);

		this.configuration = new EmfTableConfiguration<>(this);
		this.configuration.setDeactivationStrategy(new EmfSubObjectDeactivationStrategy<>(this));

		addTableListener(new EmfTableListener<>(this));
		customizeEmfTableConfiguration(configuration);
	}

	@Override
	public IEmfTableConfiguration<O, B, S> getEmfTableConfiguration() {

		return configuration;
	}

	@Override
	public IEmfTableSpecialization<O, B, S> getTableSpecialization() {

		return new EmfSubObjectTableSpecialization<>(this);
	}

	@Override
	@SuppressWarnings("unchecked")
	public IEmfEntityTable<B, P, S> getBaseTable() {

		// TODO remove cast (i54622)
		return (IEmfEntityTable<B, P, S>) super.getBaseTable();
	}

	@Override
	public IDbForeignRowField<O, B, P> getBaseField() {

		return getPrimaryKeyField();
	}

	@Override
	public Collection<IEmfTableRow<?, ?>> getAttributeOwners(O object) {

		Collection<IEmfTableRow<?, ?>> rows = new ArrayList<>();
		rows.add(object);
		object.pk().getAttributeOwners().forEach(rows::add);
		return rows;
	}

	public <V> EmfInheritedAttribute<O, B, V> inheritAttribute(IDbField<B, V> field) {

		IEmfAttribute<B, V> attribute = getBaseTable().getAttribute(field);
		return new EmfInheritedAttribute<>(getPrimaryKeyField(), attribute);
	}
}
