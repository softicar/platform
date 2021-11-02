package com.softicar.platform.emf.attribute.field.foreign.entity.collection.set;

import com.softicar.platform.db.runtime.field.IDbForeignRowField;
import com.softicar.platform.emf.attribute.field.foreign.entity.collection.EmfForeignEntityCollectionAttribute;
import com.softicar.platform.emf.collection.IEmfEntityCollectionTable;
import com.softicar.platform.emf.collection.set.IEmfEntitySet;
import com.softicar.platform.emf.entity.IEmfEntity;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.Set;

public class EmfForeignEntitySetAttribute<R extends IEmfTableRow<R, ?>, S extends IEmfEntitySet<S, F>, F extends IEmfEntity<F, ?>>
		extends EmfForeignEntityCollectionAttribute<R, S, F, Set<F>> {

	public EmfForeignEntitySetAttribute(IDbForeignRowField<R, S, ?> field, IEmfEntityCollectionTable<S, F, Set<F>, ?> collectionTable) {

		super(field, collectionTable);

		setDisplayFactory(EmfForeignEntitySetDisplay::new);
		setInputFactoryByEntity(entity -> new EmfForeignEntitySetInput<>(this, entity));
	}
}
