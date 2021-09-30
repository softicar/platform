package com.softicar.platform.db.runtime.field;

import com.softicar.platform.db.runtime.table.IDbTableBuilder;
import com.softicar.platform.db.sql.type.SqlFieldType;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class DbStringField<R> extends AbstractDbPrimitiveField<R, String, DbStringField<R>> implements IDbStringField<R> {

	private Optional<String> characterSet;
	private Optional<String> collation;
	private int maximumLength;
	private int lengthBits;

	public DbStringField(IDbTableBuilder<R, ?> builder, String name, Function<? super R, String> getter, BiConsumer<? super R, String> setter) {

		super(builder, SqlFieldType.STRING, name, getter, setter);

		this.characterSet = Optional.empty();
		this.collation = Optional.empty();
		this.maximumLength = 0;
		this.lengthBits = 0;
	}

	@Override
	public Optional<String> getCharacterSet() {

		return characterSet;
	}

	@Override
	public Optional<String> getCollation() {

		return collation;
	}

	@Override
	public int getMaximumLength() {

		return maximumLength;
	}

	@Override
	public int getLengthBits() {

		return lengthBits;
	}

	// ------------------------------ setters ------------------------------ //

	public DbStringField<R> setCharacterSet(String characterSet) {

		this.characterSet = Optional.of(characterSet);
		return this;
	}

	public DbStringField<R> setCollation(String collation) {

		this.collation = Optional.of(collation);
		return this;
	}

	public DbStringField<R> setMaximumLength(int maximumLength) {

		this.maximumLength = maximumLength;
		return this;
	}

	public DbStringField<R> setLengthBits(int lengthBits) {

		this.lengthBits = lengthBits;
		return this;
	}

	// ------------------------------ protected ------------------------------ //

	@Override
	protected DbStringField<R> getThis() {

		return this;
	}
}
