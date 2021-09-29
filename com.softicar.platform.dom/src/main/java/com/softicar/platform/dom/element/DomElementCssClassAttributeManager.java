package com.softicar.platform.dom.element;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Consumer;

class DomElementCssClassAttributeManager {

	private final IDomElement element;

	public DomElementCssClassAttributeManager(IDomElement element) {

		this.element = element;
	}

	public void setClasses(Collection<String> classes) {

		writeAttribute(classes);
	}

	public void addClasses(Collection<String> classes) {

		modifyClasses(it -> it.addAll(classes));
	}

	public void removeClasses(Collection<String> classes) {

		modifyClasses(it -> it.removeAll(classes));
	}

	private void modifyClasses(Consumer<Set<String>> modifier) {

		TreeSet<String> classes = new TreeSet<>(readAttribute());
		modifier.accept(classes);
		writeAttribute(classes);
	}

	// ------------------------------ low-level ------------------------------ //

	private Collection<String> readAttribute() {

		String attributeValue = element.getAttributeValue("class").orElse("").trim();

		if (attributeValue.isEmpty()) {
			return List.of();
		} else {
			return List.of(attributeValue.split("\\s+"));
		}
	}

	private void writeAttribute(Collection<String> classes) {

		element.setAttribute("class", String.join(" ", classes));
	}
}
