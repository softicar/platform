package com.softicar.platform.db.runtime.field;

import com.softicar.platform.db.runtime.table.IDbTableBuilder;
import com.softicar.platform.db.sql.type.SqlFieldType;
import java.util.Arrays;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class DbByteArrayField<R> extends AbstractDbPrimitiveField<R, byte[], DbByteArrayField<R>> implements IDbByteArrayField<R> {

	private int maximumLength;
	private int lengthBits;

	public DbByteArrayField(IDbTableBuilder<R, ?> builder, String name, Function<? super R, byte[]> getter, BiConsumer<? super R, byte[]> setter) {

		super(builder, SqlFieldType.BYTE_ARRAY, name, getter, setter);

		this.maximumLength = 0;
		this.lengthBits = 0;
	}

	@Override
	public byte[] copyValue(byte[] value) {

		return value != null? Arrays.copyOf(value, value.length) : null;
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

	public DbByteArrayField<R> setMaximumLength(int maximumLength) {

		this.maximumLength = maximumLength;
		return this;
	}

	public DbByteArrayField<R> setLengthBits(int lengthBits) {

		this.lengthBits = lengthBits;
		return this;
	}

	// ------------------------------ protected ------------------------------ //

	@Override
	protected DbByteArrayField<R> getThis() {

		return this;
	}
}
