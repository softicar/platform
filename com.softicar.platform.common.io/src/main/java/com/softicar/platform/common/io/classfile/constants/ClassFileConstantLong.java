package com.softicar.platform.common.io.classfile.constants;

import com.softicar.platform.common.io.classfile.ClassFileReader;
import com.softicar.platform.common.io.classfile.constant.ClassFileConstantType;

public class ClassFileConstantLong extends ClassFileConstant {

	private final long value;

	public ClassFileConstantLong(ClassFileReader reader) {

		super(ClassFileConstantType.LONG);

		this.value = reader.readLong();
	}

	public long getValue() {

		return value;
	}
}
