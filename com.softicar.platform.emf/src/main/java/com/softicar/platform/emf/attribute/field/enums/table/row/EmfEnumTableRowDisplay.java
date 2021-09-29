package com.softicar.platform.emf.attribute.field.enums.table.row;

import com.softicar.platform.common.core.entity.IEntity;
import com.softicar.platform.db.runtime.enums.IDbEnumTableRow;
import com.softicar.platform.db.runtime.enums.IDbEnumTableRowEnum;
import com.softicar.platform.dom.elements.DomDiv;
import java.util.Optional;

public class EmfEnumTableRowDisplay<R extends IDbEnumTableRow<R, V>, V extends IDbEnumTableRowEnum<V, R>> extends DomDiv {

	public EmfEnumTableRowDisplay(R row) {

		Optional//
			.ofNullable(row)
			.map(EmfEnumTableRowEntityWrapper::wrap)
			.map(IEntity::toDisplay)
			.ifPresent(this::appendText);
	}
}
