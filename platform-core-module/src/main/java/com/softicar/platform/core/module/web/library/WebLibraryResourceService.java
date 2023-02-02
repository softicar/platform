package com.softicar.platform.core.module.web.library;

import com.softicar.platform.common.core.exceptions.SofticarIOException;
import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.common.io.mime.MimeType;
import com.softicar.platform.common.io.stream.copy.StreamCopy;
import com.softicar.platform.common.io.stream.copy.StreamCopyOutputException;
import com.softicar.platform.common.string.Trim;
import com.softicar.platform.common.web.service.IWebService;
import com.softicar.platform.core.module.web.service.WebServicePath;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;
import java.util.function.Function;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The {@link IWebService} implementation used by {@link WebLibrary}.
 *
 * @author Oliver Richers
 */
class WebLibraryResourceService implements IWebService {

	private final String packagePath;
	private Function<String, MimeType> mimeTypeDeterminer;

	public WebLibraryResourceService(String packagePath) {

		this.packagePath = Trim.trimRight(packagePath, '/');
		this.mimeTypeDeterminer = MimeType::getByFilenameOrDefault;
	}

	/**
	 * Overrides the default {@link MimeType} determiner.
	 * <p>
	 * By default, {@link MimeType#getByFilenameOrDefault} is used to determine
	 * the {@link MimeType} of a given resource path.
	 *
	 * @param mimeTypeDeterminer
	 *            a {@link Function} mapping from the resource path to the
	 *            respective {@link MimeType} (never <i>null</i>)
	 */
	public void setMimeTypeDeterminer(Function<String, MimeType> mimeTypeDeterminer) {

		this.mimeTypeDeterminer = Objects.requireNonNull(mimeTypeDeterminer);
	}

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) {

		var resourcePath = WebServicePath.parseOrThrow(request.getPathInfo()).getResourcePath();
		try (var resourceStream = getClass().getResourceAsStream(packagePath + resourcePath)) {
			if (resourceStream != null) {
				response.setContentType(mimeTypeDeterminer.apply(resourcePath).getIdentifier());
				try (var responseStream = response.getOutputStream()) {
					copyNoThrow(resourceStream, responseStream);
				}
			} else {
				throw new IllegalArgumentException("Missing resource: " + resourcePath);
			}
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}

	private void copyNoThrow(InputStream inputStream, OutputStream outputStream) {

		try {
			new StreamCopy(inputStream, outputStream).copyAndClose();
		} catch (StreamCopyOutputException exception) {
			DevNull.swallow(exception);
			Log.error("Failed to transfer resource to HTTP client. The client probably closed the connection prematurely.");
		}
	}
}
