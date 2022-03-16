package com.softicar.platform.common.io.classfile.constant;

import com.softicar.platform.common.container.enums.IdToEnumMap;
import com.softicar.platform.common.io.classfile.ClassFileReader;
import com.softicar.platform.common.io.classfile.constants.ClassFileConstant;
import com.softicar.platform.common.io.classfile.constants.ClassFileConstantClass;
import com.softicar.platform.common.io.classfile.constants.ClassFileConstantDouble;
import com.softicar.platform.common.io.classfile.constants.ClassFileConstantFieldReference;
import com.softicar.platform.common.io.classfile.constants.ClassFileConstantFloat;
import com.softicar.platform.common.io.classfile.constants.ClassFileConstantInteger;
import com.softicar.platform.common.io.classfile.constants.ClassFileConstantInterfaceMethodReference;
import com.softicar.platform.common.io.classfile.constants.ClassFileConstantInvokeDynamic;
import com.softicar.platform.common.io.classfile.constants.ClassFileConstantLong;
import com.softicar.platform.common.io.classfile.constants.ClassFileConstantMethodHandle;
import com.softicar.platform.common.io.classfile.constants.ClassFileConstantMethodReference;
import com.softicar.platform.common.io.classfile.constants.ClassFileConstantMethodType;
import com.softicar.platform.common.io.classfile.constants.ClassFileConstantNameAndType;
import com.softicar.platform.common.io.classfile.constants.ClassFileConstantString;
import com.softicar.platform.common.io.classfile.constants.ClassFileConstantUtf8;
import java.util.function.Function;

public enum ClassFileConstantType {

	UTF8(1, ClassFileConstantUtf8::new),
	INTEGER(3, ClassFileConstantInteger::new),
	FLOAT(4, ClassFileConstantFloat::new),
	LONG(5, ClassFileConstantLong::new),
	DOUBLE(6, ClassFileConstantDouble::new),
	CLASS(7, ClassFileConstantClass::new),
	STRING(8, ClassFileConstantString::new),
	FIELD_REFERENCE(9, ClassFileConstantFieldReference::new),
	METHOD_REFERENCE(10, ClassFileConstantMethodReference::new),
	INTERFACE_METHOD_REFERENCE(11, ClassFileConstantInterfaceMethodReference::new),
	NAME_AND_TYPE(12, ClassFileConstantNameAndType::new),
	METHOD_HANDLE(15, ClassFileConstantMethodHandle::new),
	METHOD_TYPE(16, ClassFileConstantMethodType::new),
	INVOKE_DYNAMIC(18, ClassFileConstantInvokeDynamic::new),
	//
	;

	private static final IdToEnumMap<ClassFileConstantType> ID_TO_ENUM_MAP = new IdToEnumMap<>(ClassFileConstantType.class, ClassFileConstantType::getId);
	private int id;
	private Function<ClassFileReader, ClassFileConstant> parser;

	private ClassFileConstantType(int id) {

		this.id = id;
	}

	private ClassFileConstantType(int id, Function<ClassFileReader, ClassFileConstant> parser) {

		this.id = id;
		this.parser = parser;
	}

	public int getId() {

		return id;
	}

	public ClassFileConstant parse(ClassFileReader reader) {

		return parser.apply(reader);
	}

	public static ClassFileConstantType getById(int id) {

		return ID_TO_ENUM_MAP.get(id);
	}
}
