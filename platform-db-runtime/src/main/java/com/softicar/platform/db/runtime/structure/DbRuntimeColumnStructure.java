package com.softicar.platform.db.runtime.structure;

import com.softicar.platform.common.core.optional.OptionalListEvaluator;
import com.softicar.platform.db.runtime.field.IDbBigDecimalField;
import com.softicar.platform.db.runtime.field.IDbByteArrayField;
import com.softicar.platform.db.runtime.field.IDbDayTimeField;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.structure.column.DbColumnStructure;
import com.softicar.platform.db.structure.table.IDbTableStructure;
import com.softicar.platform.db.structure.utils.DbColumnCommentParser;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class DbRuntimeColumnStructure extends DbColumnStructure {

	public DbRuntimeColumnStructure(IDbTableStructure tableStructure, IDbField<?, ?> field) {

		super(tableStructure);

		setAutoIncrement(field.cast().toIdField().isPresent());
		setBase(isBaseField(field));
		setBits(determineBits(field));
		setCharacterSet(determineCharacterSet(field));
		setCollation(determineCollation(field));
		setComment(determineComment(field));
		setDefaultValue(new InternalDbFieldDefaultDeterminer(field).getLiteralDefaultValue());
		setDefaultType(new InternalDbFieldDefaultDeterminer(field).getDefaultType());
		setEnumValues(determineEnumValues(field));
		setFieldType(field.getPhysicalFieldType());
		setLengthBits(determineLengthBits(field));
		setLogicalName(determineLogicalName(field));
		setMaxLength(determineMaxLength(field));
		setNullable(field.isNullable());
		setName(field.getName());
		setPrecision(determinePrecision(field));
		setOnUpdateNow(determineOnUpdateNow(field));
		setScale(determineScale(field));
		setTimestamp(determineTimestamp(field));
		setUnsigned(determineUnsigned(field));
	}

	private static boolean isBaseField(IDbField<?, ?> field) {

		return field.cast().toBaseField().isPresent();
	}

	private static int determineBits(IDbField<?, ?> field) {

		return new OptionalListEvaluator<>(0)//
			.add(field.cast().toIntegerField().map(it -> it.getBits()))
			.add(field.cast().toLongField().map(it -> it.getBits()))
			.add(field.cast().toIdField().map(it -> it.getBits()))
			.add(field.cast().toForeignRowField().map(it -> determineBits(it.getTargetField())))
			.getFirstOrDefault();
	}

	private static String determineCharacterSet(IDbField<?, ?> field) {

		return new OptionalListEvaluator<String>()//
			.add(field.cast().toEnumField().flatMap(it -> it.getCharacterSet()))
			.add(field.cast().toStringField().flatMap(it -> it.getCharacterSet()))
			.getFirstOrDefault();
	}

	private static String determineCollation(IDbField<?, ?> field) {

		return new OptionalListEvaluator<String>()//
			.add(field.cast().toEnumField().flatMap(it -> it.getCollation()))
			.add(field.cast().toStringField().flatMap(it -> it.getCollation()))
			.getFirstOrDefault();
	}

	/**
	 * FIXME This hack should not be necessary. We need a concept to avoid it.
	 */
	private static String determineComment(IDbField<?, ?> field) {

		String comment = field.getComment().orElse(null);
		boolean baseField = isBaseField(field);
		if (baseField) {
			if (comment == null) {
				comment = "@base@";
			} else if (!comment.equals("@base@")) {
				String errorMessage = String
					.format(//
						"Failed to mark '%s' as base field: Base fields must not have comments other than '@base@'. Encountered comment: '%s'",
						field.getTitle(),
						comment);
				throw new IllegalArgumentException(errorMessage);
			}
		}
		return comment;
	}

	private static List<String> determineEnumValues(IDbField<?, ?> field) {

		return new OptionalListEvaluator<>(Collections.<String> emptyList())//
			.add(field.cast().toEnumField().map(it -> determineEnumConstantsAsStrings(it.getEnumClass())))
			.getFirstOrDefault();
	}

	private static <E extends Enum<E>> List<String> determineEnumConstantsAsStrings(Class<E> enumClass) {

		return Arrays//
			.asList(enumClass.getEnumConstants())
			.stream()
			.map(Object::toString)
			.collect(Collectors.toList());
	}

	private static int determineLengthBits(IDbField<?, ?> field) {

		switch (field.getFieldType()) {
		case BYTE_ARRAY:
			return field.cast().toByteArrayField().map(IDbByteArrayField::getLengthBits).orElse(0);
		case STRING:
			return field.cast().toStringField().map(IDbStringField::getLengthBits).orElse(0);
		default:
			return 0;
		}
	}

	private static String determineLogicalName(IDbField<?, ?> field) {

		String nameOverride = new DbColumnCommentParser(field.getComment().orElse(null)).getNameOverride();
		return nameOverride != null? nameOverride : field.getName();
	}

	private static int determineMaxLength(IDbField<?, ?> field) {

		switch (field.getFieldType()) {
		case BYTE_ARRAY:
			return field.cast().toByteArrayField().map(IDbByteArrayField::getMaximumLength).orElse(0);
		case STRING:
			return field.cast().toStringField().map(IDbStringField::getMaximumLength).orElse(0);
		default:
			return 0;
		}
	}

	private static int determinePrecision(IDbField<?, ?> field) {

		return field.cast().toBigDecimalField().map(IDbBigDecimalField::getPrecision).orElse(0);
	}

	private static int determineScale(IDbField<?, ?> field) {

		return field.cast().toBigDecimalField().map(IDbBigDecimalField::getScale).orElse(0);
	}

	private static boolean determineOnUpdateNow(IDbField<?, ?> field) {

		return field.cast().toDayTimeField().map(IDbDayTimeField::isOnUpdateNow).orElse(false);
	}

	private static boolean determineTimestamp(IDbField<?, ?> field) {

		return field.cast().toDayTimeField().map(IDbDayTimeField::isTimestamp).orElse(false);
	}

	private static boolean determineUnsigned(IDbField<?, ?> field) {

		return new OptionalListEvaluator<>(false)//
			.add(field.cast().toIntegerField().map(it -> it.isUnsigned()))
			.add(field.cast().toLongField().map(it -> it.isUnsigned()))
			.add(field.cast().toIdField().map(it -> it.isUnsigned()))
			.add(field.cast().toForeignRowField().map(it -> determineUnsigned(it.getTargetField())))
			.getFirstOrDefault();
	}
}
