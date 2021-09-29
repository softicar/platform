package com.softicar.platform.emf.attribute.field.foreign.entity.collection.list;

import com.softicar.platform.db.runtime.field.IDbForeignRowField;
import com.softicar.platform.emf.attribute.field.foreign.entity.collection.EmfForeignEntityCollectionAttribute;
import com.softicar.platform.emf.collection.IEmfEntityCollectionTable;
import com.softicar.platform.emf.collection.list.IEmfEntityList;
import com.softicar.platform.emf.entity.IEmfEntity;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.List;

public class EmfForeignEntityListAttribute<R extends IEmfTableRow<R, ?>, L extends IEmfEntityList<L, F>, F extends IEmfEntity<F, ?>>
		extends EmfForeignEntityCollectionAttribute<R, L, F, List<F>> {

	public EmfForeignEntityListAttribute(IDbForeignRowField<R, L, ?> field, IEmfEntityCollectionTable<L, F, List<F>, ?> collectionTable) {

		super(field, collectionTable);

		setDisplayFactory(EmfForeignEntityListDisplay::new);
		setInputFactoryByEntity(entity -> new EmfForeignEntityListInput<>(this, entity));
	}
}
