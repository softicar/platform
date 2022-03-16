package com.softicar.platform.emf.attribute;

import com.softicar.platform.common.core.entity.IEntity;
import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.common.core.exceptions.SofticarUnknownEnumConstantException;
import com.softicar.platform.common.core.item.IBasicItem;
import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.db.runtime.enums.IDbEnumTableRow;
import com.softicar.platform.db.runtime.field.IDbBigDecimalField;
import com.softicar.platform.db.runtime.field.IDbBooleanField;
import com.softicar.platform.db.runtime.field.IDbByteArrayField;
import com.softicar.platform.db.runtime.field.IDbDayField;
import com.softicar.platform.db.runtime.field.IDbDayTimeField;
import com.softicar.platform.db.runtime.field.IDbDoubleField;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.runtime.field.IDbFloatField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbForeignRowField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbIntegerField;
import com.softicar.platform.db.runtime.field.IDbLongField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.field.IDbTimeField;
import com.softicar.platform.db.runtime.table.IDbTable;
import com.softicar.platform.db.sql.type.SqlFieldType;
import com.softicar.platform.emf.attribute.field.EmfFieldAttribute;
import com.softicar.platform.emf.attribute.field.bool.EmfBooleanAttribute;
import com.softicar.platform.emf.attribute.field.bytes.EmfByteArrayAttribute;
import com.softicar.platform.emf.attribute.field.day.EmfDayAttribute;
import com.softicar.platform.emf.attribute.field.daytime.EmfDayTimeAttribute;
import com.softicar.platform.emf.attribute.field.decimal.EmfBigDecimalAttribute;
import com.softicar.platform.emf.attribute.field.decimal.EmfDoubleAttribute;
import com.softicar.platform.emf.attribute.field.decimal.EmfFloatAttribute;
import com.softicar.platform.emf.attribute.field.enums.EmfEnumAttribute;
import com.softicar.platform.emf.attribute.field.enums.table.row.EmfEnumTableRowAttribute;
import com.softicar.platform.emf.attribute.field.id.EmfIdAttribute;
import com.softicar.platform.emf.attribute.field.integer.EmfIntegerAttribute;
import com.softicar.platform.emf.attribute.field.item.EmfBasicEntityAttribute;
import com.softicar.platform.emf.attribute.field.item.EmfBasicItemAttribute;
import com.softicar.platform.emf.attribute.field.longs.EmfLongAttribute;
import com.softicar.platform.emf.attribute.field.string.EmfStringAttribute;
import com.softicar.platform.emf.attribute.field.time.EmfTimeAttribute;
import com.softicar.platform.emf.attribute.field.transaction.EmfTransactionAttribute;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import com.softicar.platform.emf.transaction.IEmfTransactionObject;

public class EmfAttributeFactory<R extends IEmfTableRow<R, ?>, V> {

	private final IDbField<R, V> field;

	public EmfAttributeFactory(IDbField<R, V> field) {

		this.field = field;
	}

	@SuppressWarnings("unchecked")
	public EmfFieldAttribute<R, ?> create() {

		if (field instanceof IDbForeignRowField) {
			IDbForeignRowField<R, ?, ?> foreignRowField = (IDbForeignRowField<R, ?, ?>) field;

			// check that target table exists (important for unit tests)
			if (foreignRowField.getTargetTable() != null) {
				// test for IEmfTransactionObject
				if (IEmfTransactionObject.class.isAssignableFrom(foreignRowField.getTargetTable().getValueClass())) {
					return new EmfTransactionAttribute<>(CastUtils.cast(foreignRowField));
				}

				// test for IEmfTableRow
				IDbTable<?, ?> targetTable = foreignRowField.getTargetTable();
				if (targetTable instanceof IEmfTable) {
					return createForeignRowAttribute((IEmfTable<?, ?, ?>) targetTable, foreignRowField);
				}

				// test for IDbEnumTableRow
				if (IDbEnumTableRow.class.isAssignableFrom(foreignRowField.getValueType().getValueClass())) {
					return new EmfEnumTableRowAttribute<>(CastUtils.cast(field));
				}

				// test for IEntity
				if (IEntity.class.isAssignableFrom(foreignRowField.getValueType().getValueClass())) {
					return new EmfBasicEntityAttribute<>((IDbForeignRowField<R, ? extends IEntity, ?>) field);
				}

				// test for IBasicItem
				if (IBasicItem.class.isAssignableFrom(foreignRowField.getValueType().getValueClass())) {
					return new EmfBasicItemAttribute<>((IDbForeignRowField<R, ? extends IBasicItem, ?>) field);
				}

				throw new SofticarDeveloperException(//
					"Failed to determine attribute type for %s '%s' of table '%s'",
					IDbForeignField.class.getSimpleName(),
					field.getName(),
					field.getTable().getClass().getSimpleName());
			}
		}

		if (field instanceof IDbIdField) {
			return new EmfIdAttribute<>((IDbField<R, Integer>) field);
		}

		return getPrimitiveTypeAttribute(field.getFieldType());
	}

	@SuppressWarnings("unchecked")
	private EmfFieldAttribute<R, ?> getPrimitiveTypeAttribute(SqlFieldType fieldType) {

		switch (fieldType) {
		case BIG_DECIMAL:
			return new EmfBigDecimalAttribute<>((IDbBigDecimalField<R>) field);
		case BOOLEAN:
			return new EmfBooleanAttribute<>((IDbBooleanField<R>) field);
		case BYTE_ARRAY:
			return new EmfByteArrayAttribute<>((IDbByteArrayField<R>) field);
		case DAY:
			return new EmfDayAttribute<>((IDbDayField<R>) field);
		case DAY_TIME:
			return new EmfDayTimeAttribute<>((IDbDayTimeField<R>) field);
		case DOUBLE:
			return new EmfDoubleAttribute<>((IDbDoubleField<R>) field);
		case ENUM:
			return EmfEnumAttribute.create(field);
		case FLOAT:
			return new EmfFloatAttribute<>((IDbFloatField<R>) field);
		case INTEGER:
			return new EmfIntegerAttribute<>((IDbIntegerField<R>) field);
		case LONG:
			return new EmfLongAttribute<>((IDbLongField<R>) field);
		case STRING:
			return new EmfStringAttribute<>((IDbStringField<R>) field);
		case TIME:
			return new EmfTimeAttribute<>((IDbTimeField<R>) field);
		}
		throw new SofticarUnknownEnumConstantException(fieldType);
	}

	@SuppressWarnings("unchecked")
	public <F extends IEmfTableRow<F, FP>, FP> EmfFieldAttribute<R, F> createForeignRowAttribute(IEmfTable<F, FP, ?> table,
			IDbForeignRowField<R, ?, ?> foreignRowField) {

		return table.getEmfTableConfiguration().getAttributeFactory().create((IDbForeignRowField<R, F, FP>) foreignRowField);
	}
}
