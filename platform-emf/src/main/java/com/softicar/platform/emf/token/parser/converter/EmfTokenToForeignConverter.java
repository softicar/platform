package com.softicar.platform.emf.token.parser.converter;

import com.softicar.platform.common.core.exceptions.SofticarNotImplementedYetException;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.logic.IDbObject;
import com.softicar.platform.db.runtime.table.IDbTable;
import com.softicar.platform.db.runtime.table.field.DbTableFieldExtractor;
import com.softicar.platform.emf.EmfI18n;

class EmfTokenToForeignConverter extends AbstractEmfTokenConverter<IDbObject<?>, IDbForeignField<?, ?>> {

	@Override
	protected EmfTokenConverterResult<IDbObject<?>> convertToken(IDbForeignField<?, ?> field, String token) {

		Class<?> targetClass = field.getValueType().getValueClass();
		IDbTable<?, ?> targetTable = new DbTableFieldExtractor().extractTable(targetClass);
		if (hasIntegerPrimaryKey(targetTable)) {
			int id = Integer.parseInt(token);
			IDbObject<?> value = CastUtils.cast(targetTable.get(CastUtils.cast(id)));
			if (value != null) {
				return EmfTokenConverterResult.okay(value);
			} else {
				return EmfTokenConverterResult
					.failed(EmfI18n.THE_RECORD_WITH_ID_ARG1_COULD_NOT_BE_FOUND_IN_TABLE_ARG2.toDisplay(id, targetTable.getFullName()));
			}
		} else {
			// TODO later-on: support this
			throw new SofticarNotImplementedYetException("Foreign key references to tables with non-integer primary keys are not yet supported.");
		}
	}

	@Override
	public IDisplayString getTypeDescription() {

		return createTypeDescriptionWithExample(EmfI18n.FOREIGN_ID, "128");
	}

	private boolean hasIntegerPrimaryKey(IDbTable<?, ?> targetTable) {

		return targetTable.getPrimaryKey().getIdField() != null;
	}
}
