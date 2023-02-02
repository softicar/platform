package com.softicar.platform.core.module.web.library;

import com.softicar.platform.common.web.service.IWebService;
import com.softicar.platform.common.web.service.IWebServiceFactory;
import java.util.Objects;

/**
 * An {@link IWebServiceFactory} to serve a static web library, e.g. a library
 * containing JavaScript code or similar.
 *
 * @author Oliver Richers
 */
public class WebLibrary implements IWebServiceFactory {

	private final String packagePath;

	/**
	 * The Java package path to the content of the web library.
	 * <p>
	 * It is assumed that the web library is part of the Java class path as a
	 * Jar file.
	 * <p>
	 * <b>Warning:</b> Be sure to supply a path as deep as possible to not
	 * expose resources that should remain confidential. For example, supplying
	 * the path {@code "/"} would provide access to the whole code base.
	 *
	 * @param packagePath
	 *            the package path (never <i>null</i>)
	 */
	public WebLibrary(String packagePath) {

		this.packagePath = Objects.requireNonNull(packagePath);
	}

	@Override
	public IWebService createInstance() {

		return new WebLibraryResourceService(packagePath);
	}
}
