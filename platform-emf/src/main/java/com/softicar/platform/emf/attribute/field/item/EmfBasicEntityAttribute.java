package com.softicar.platform.emf.attribute.field.item;

import com.softicar.platform.common.core.entity.IEntity;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.emf.attribute.field.EmfFieldAttribute;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public class EmfBasicEntityAttribute<R extends IEmfTableRow<R, ?>, V extends IEntity> extends EmfFieldAttribute<R, V> {

	public EmfBasicEntityAttribute(IDbField<R, V> field) {

		super(field);

		setDisplayFactory((V value) -> new EmfBasicEntityDisplay<>(value));
		setInputFactory(EmfReadOnlyBasicEntityInput::new);
	}
}
