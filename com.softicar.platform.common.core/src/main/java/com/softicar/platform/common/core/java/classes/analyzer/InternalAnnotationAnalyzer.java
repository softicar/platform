package com.softicar.platform.common.core.java.classes.analyzer;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

class InternalAnnotationAnalyzer extends AnnotationVisitor {

	private final AnalyzedJavaClass javaClass;

	public InternalAnnotationAnalyzer(AnalyzedJavaClass javaClass) {

		super(Opcodes.ASM9);
		this.javaClass = javaClass;
	}

	@Override
	public void visit(String name, Object value) {

		if (value instanceof Type) {
			javaClass.addReferencesFromType((Type) value);
		}
	}

	@Override
	public AnnotationVisitor visitAnnotation(String name, String descriptor) {

		javaClass.addReferencesFromSignature(descriptor);
		return new InternalAnnotationAnalyzer(javaClass);
	}

	@Override
	public void visitEnum(String name, String descriptor, String value) {

		javaClass.addReferencesFromSignature(descriptor);
	}
}
