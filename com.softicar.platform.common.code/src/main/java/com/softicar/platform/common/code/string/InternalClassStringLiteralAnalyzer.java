package com.softicar.platform.common.code.string;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

class InternalClassStringLiteralAnalyzer extends ClassVisitor {

	private static final String ENUM_CLASS_SUPERNAME = "java/lang/Enum";
	private final ClassStringLiteralAnalyzerResult result;

	public InternalClassStringLiteralAnalyzer(ClassStringLiteralAnalyzerResult result) {

		super(Opcodes.ASM9);
		this.result = result;
	}

	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {

		if (superName.equals(ENUM_CLASS_SUPERNAME)) {
			result.setEnum(true);
		}
		super.visit(version, access, name, signature, superName, interfaces);
	}

	@Override
	public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {

		if (value instanceof String) {
			result.addStringLiteral((String) value);
		}
		return super.visitField(access, name, descriptor, signature, value);
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {

		return new InternalMethodStringLiteralAnalyzer(result);
	}
}
