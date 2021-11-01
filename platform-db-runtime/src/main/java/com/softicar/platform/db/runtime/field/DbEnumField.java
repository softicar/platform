package com.softicar.platform.db.runtime.field;

import com.softicar.platform.db.runtime.table.IDbTableBuilder;
import com.softicar.platform.db.sql.type.ISqlValueType;
import com.softicar.platform.db.sql.type.SqlFieldType;
import com.softicar.platform.db.sql.type.SqlValueTypes;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class DbEnumField<R, E extends Enum<E>> extends AbstractDbPrimitiveField<R, E, DbEnumField<R, E>> implements IDbEnumField<R, E> {

	private final Class<E> enumClass;
	private final ISqlValueType<E> valueType;
	private Optional<String> characterSet;
	private Optional<String> collation;

	public DbEnumField(IDbTableBuilder<R, ?> builder, String name, Function<? super R, E> getter, BiConsumer<? super R, E> setter, Class<E> enumClass) {

		super(builder, SqlFieldType.ENUM, name, getter, setter);

		this.enumClass = enumClass;
		this.valueType = SqlValueTypes.createEnumType(enumClass);
		this.characterSet = Optional.empty();
		this.collation = Optional.empty();
	}

	@Override
	public Class<E> getEnumClass() {

		return enumClass;
	}

	@Override
	public ISqlValueType<E> getValueType() {

		return valueType;
	}

	@Override
	public Optional<String> getCharacterSet() {

		return characterSet;
	}

	@Override
	public Optional<String> getCollation() {

		return collation;
	}

	// ------------------------------ setters ------------------------------ //

	public DbEnumField<R, E> setCharacterSet(String characterSet) {

		this.characterSet = Optional.of(characterSet);
		return this;
	}

	public DbEnumField<R, E> setCollation(String collation) {

		this.collation = Optional.of(collation);
		return this;
	}

	// ------------------------------ protected ------------------------------ //

	@Override
	protected DbEnumField<R, E> getThis() {

		return this;
	}
}
