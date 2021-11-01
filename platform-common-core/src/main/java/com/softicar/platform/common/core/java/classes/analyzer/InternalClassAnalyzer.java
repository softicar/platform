package com.softicar.platform.common.core.java.classes.analyzer;

import com.softicar.platform.common.core.java.classes.name.JavaClassName;
import com.softicar.platform.common.core.java.identifier.declaration.JavaIdentifierDeclaration;
import com.softicar.platform.common.core.java.identifier.key.JavaIdentifierKey;
import java.util.Arrays;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.TypePath;

class InternalClassAnalyzer extends ClassVisitor {

	private final AnalyzedJavaClass javaClass;

	public InternalClassAnalyzer(AnalyzedJavaClass javaClass) {

		super(Opcodes.ASM9);
		this.javaClass = javaClass;
	}

	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {

		javaClass.setClassName(name);
		javaClass.setAccess(access);
		if (superName != null) {
			javaClass.setSuperClass(new JavaClassName(superName));
		}
		if (interfaces != null) {
			Arrays//
				.asList(interfaces)
				.stream()
				.map(JavaClassName::new)
				.forEach(javaClass::addInterface);
		}
		javaClass.addReferencesFromSignature(signature);
	}

	@Override
	public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {

		javaClass.addAnnotation(descriptor);
		javaClass.addReferencesFromSignature(descriptor);
		return new InternalAnnotationAnalyzer(javaClass);
	}

	@Override
	public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {

		// add field declaration
		javaClass.addDeclaredField(new JavaIdentifierDeclaration(javaClass, new JavaIdentifierKey(name, descriptor), access));

		javaClass.addReferencesFromSignature(descriptor);
		javaClass.addReferencesFromSignature(signature);
		if (value instanceof Type) {
			javaClass.addReferencesFromType((Type) value);
		}
		return new InternalFieldAnalyzer(javaClass);
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {

		// add method declaration
		javaClass.addDeclaredMethod(new JavaIdentifierDeclaration(javaClass, new JavaIdentifierKey(name, descriptor), access));

		// collect dependencies
		javaClass.addReferencesFromSignature(descriptor);
		javaClass.addReferencesFromSignature(signature);
		if (exceptions != null) {
			javaClass.addReferencedClasses(exceptions);
		}
		return new InternalMethodAnalyzer(javaClass);
	}

	@Override
	public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {

		javaClass.addReferencesFromSignature(descriptor);
		return new InternalAnnotationAnalyzer(javaClass);
	}
}
