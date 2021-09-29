package com.softicar.platform.common.core.java.classes.analyzer;

import java.util.Arrays;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.TypePath;

class InternalMethodAnalyzer extends MethodVisitor {

	private static final String JUNIT_TEST_ANNOTATION_DESCRIPTOR = "Lorg/junit/Test;";
	private final AnalyzedJavaClass javaClass;
	private int line;

	public InternalMethodAnalyzer(AnalyzedJavaClass javaClass) {

		super(Opcodes.ASM9);
		this.javaClass = javaClass;
		this.line = 1;
	}

	public int getLine() {

		return line;
	}

	@Override
	public void visitLdcInsn(Object value) {

		if (value instanceof Type) {
			javaClass.addReferencesFromType((Type) value);
		}
	}

	@Override
	public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {

		// look for junit test annotation
		if (descriptor.equals(JUNIT_TEST_ANNOTATION_DESCRIPTOR)) {
			javaClass.setHasJunitTests(true);
		}

		// collect dependencies
		javaClass.addReferencesFromSignature(descriptor);
		return new InternalAnnotationAnalyzer(javaClass);
	}

	@Override
	public void visitLineNumber(int line, Label start) {

		this.line = line;
	}

	@Override
	public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {

		javaClass.addReferencedClass(owner);
		javaClass.addReferencesFromSignature(descriptor);
	}

	@Override
	public AnnotationVisitor visitLocalVariableAnnotation(int typeRef, TypePath typePath, Label[] start, Label[] end, int[] index, String descriptor,
			boolean visible) {

		javaClass.addReferencesFromSignature(descriptor);
		return new InternalAnnotationAnalyzer(javaClass);
	}

	@Override
	public AnnotationVisitor visitInsnAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {

		javaClass.addReferencesFromSignature(descriptor);
		return new InternalAnnotationAnalyzer(javaClass);
	}

	@Override
	public void visitInvokeDynamicInsn(String name, String descriptor, Handle bootstrapMethodHandle, Object...bootstrapMethodArguments) {

		javaClass.addReferencesFromSignature(descriptor);
		javaClass.addReferencesFromHandle(bootstrapMethodHandle);
		Arrays.asList(bootstrapMethodArguments).forEach(this::visitBootstrapMethodArgument);
	}

	@Override
	public void visitLocalVariable(String name, String descriptor, String signature, Label start, Label end, int index) {

		javaClass.addReferencesFromSignature(descriptor);
		javaClass.addReferencesFromSignature(signature);
	}

	@Override
	public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {

		javaClass.addReferencedClass(owner);
		javaClass.addReferencedMethod(owner, name, descriptor);
		javaClass.addReferencesFromSignature(descriptor);
	}

	@Override
	public void visitMultiANewArrayInsn(String descriptor, int numDimensions) {

		javaClass.addReferencesFromSignature(descriptor);
	}

	@Override
	public AnnotationVisitor visitParameterAnnotation(int parameter, String descriptor, boolean visible) {

		javaClass.addReferencesFromSignature(descriptor);
		return new InternalAnnotationAnalyzer(javaClass);
	}

	@Override
	public AnnotationVisitor visitTryCatchAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {

		javaClass.addReferencesFromSignature(descriptor);
		return new InternalAnnotationAnalyzer(javaClass);
	}

	@Override
	public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {

		javaClass.addReferencesFromSignature(descriptor);
		return new InternalAnnotationAnalyzer(javaClass);
	}

	@Override
	public void visitTypeInsn(int opcode, String type) {

		javaClass.addReferencesFromType(Type.getObjectType(type));
	}

	private void visitBootstrapMethodArgument(Object argument) {

		if (argument instanceof Type) {
			javaClass.addReferencesFromType((Type) argument);
		} else if (argument instanceof Handle) {
			javaClass.addReferencesFromHandle((Handle) argument);
		}
	}
}
