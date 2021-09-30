package com.softicar.platform.emf.sub.object.table;

import com.softicar.platform.emf.attribute.IEmfAttributeList;
import com.softicar.platform.emf.attribute.initialization.EmfBaseAttributeInitializer;
import com.softicar.platform.emf.entity.IEmfEntity;
import com.softicar.platform.emf.entity.table.EmfEntityTableSpecialization;
import com.softicar.platform.emf.sub.object.IEmfSubObject;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.Optional;

public class EmfSubObjectTableSpecialization<O extends IEmfSubObject<O, B>, B extends IEmfEntity<B, ?>, S> extends EmfEntityTableSpecialization<O, B, S> {

	private final IEmfSubObjectTable<O, B, ?, S> table;

	public EmfSubObjectTableSpecialization(IEmfSubObjectTable<O, B, ?, S> table) {

		super(table);

		this.table = table;
	}

	@Override
	public void initializeAttributeList(IEmfAttributeList<O> attributeList) {

		super.initializeAttributeList(attributeList);

		new EmfBaseAttributeInitializer<>(table).initialize(attributeList);
	}

	@Override
	public void initializeFields(O object, S scope) {

		initializeBaseField(object, scope);
		super.initializeFields(object, scope);
	}

	@Override
	public Optional<IEmfTableRow<?, ?>> getBase(O object) {

		return Optional.of(table.getBaseField().getValue(object));
	}

	@Override
	public Optional<IEmfTable<?, ?, ?>> getBaseTable() {

		return Optional.of(table.getBaseTable());
	}

	private void initializeBaseField(O object, S scope) {

		B base = table.getBaseTable().createEntity(scope);
		table.getBaseField().setValue(object, base);
	}
}
