package com.softicar.platform.ajax.engine;

import com.softicar.platform.ajax.engine.auto.complete.AjaxDomEngineAutoCompleteJavascriptContainer;
import com.softicar.platform.ajax.engine.indicator.AjaxDomEngineIndicatorJavascriptContainer;
import com.softicar.platform.common.core.exceptions.SofticarIOException;
import com.softicar.platform.common.io.reader.ManagedReader;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;
import com.softicar.platform.common.io.writer.IManagedPrintWriter;
import com.softicar.platform.common.string.charset.Charsets;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Prints all relevant JavaScript files through a {@link PrintWriter}.
 *
 * @author Oliver Richers
 * @author Alexander Schmidt
 */
public class AjaxJavascriptPrinter {

	public void printAll(IManagedPrintWriter writer, boolean strip) {

		getJavascriptResources()//
			.stream()
			.map(IResource::getResourceAsStream)
			.forEach(inputStream -> print(inputStream, writer, strip));
	}

	private Collection<IResource> getJavascriptResources() {

		Collection<IResource> resources = new ArrayList<>();
		addAllResourcesFromContainer(AjaxDomEngineJavascriptContainer.class, resources);
		addAllResourcesFromContainer(AjaxDomEngineIndicatorJavascriptContainer.class, resources);
		addAllResourcesFromContainer(AjaxDomEngineAutoCompleteJavascriptContainer.class, resources);
		return resources;
	}

	private void addAllResourcesFromContainer(Class<?> containerClass, Collection<IResource> resources) {

		IResourceSupplier//
			.getResourceSuppliers(containerClass)
			.stream()
			.map(IResourceSupplier::getResource)
			.forEach(resources::add);
	}

	private void print(InputStream inputStream, IManagedPrintWriter writer, boolean strip) {

		try (Reader reader = new InputStreamReader(inputStream, Charsets.UTF8)) {
			if (strip) {
				JavascriptStripper stripper = new JavascriptStripper(reader, writer);
				stripper.transfer();
				writer.println();
			} else {
				writer.write(new ManagedReader(reader));
			}
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}
}
