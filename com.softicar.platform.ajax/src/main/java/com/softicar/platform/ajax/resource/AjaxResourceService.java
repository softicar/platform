package com.softicar.platform.ajax.resource;

import com.softicar.platform.ajax.exceptions.AjaxHttpBadRequestError;
import com.softicar.platform.ajax.exceptions.AjaxHttpNotFoundError;
import com.softicar.platform.ajax.export.AjaxDomExportNode;
import com.softicar.platform.ajax.request.IAjaxRequest;
import com.softicar.platform.ajax.resource.registry.AjaxResourceRegistry;
import com.softicar.platform.ajax.service.AbstractAjaxService;
import com.softicar.platform.common.core.exceptions.SofticarIOException;
import com.softicar.platform.common.core.number.parser.IntegerParser;
import com.softicar.platform.common.io.StreamUtils;
import com.softicar.platform.common.io.file.name.FilenameCleaner;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.common.io.resource.hash.ResourceHash;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;

/**
 * AJAX service to handle resource requests.
 *
 * @author Oliver Richers
 */
public class AjaxResourceService extends AbstractAjaxService {

	public AjaxResourceService(IAjaxRequest request) {

		super(request);
	}

	@Override
	public void service() {

		sendResource(getResourceDescriptionOrThrow());
	}

	private void sendResource(ResourceDescription resourceDescription) {

		// set cache control
		response.setHeader("Cache-Control", resourceDescription.getCacheControl(request.getAjaxFramework().getSettings()));

		// set content type
		response.setContentType(resourceDescription.getContentTypeString());

		// set content disposition
		IResource resource = resourceDescription.getResource();
		Optional<String> filename = resource.getFilename();
		if (filename.isPresent()) {
			String disposition = String.format("attachment; filename=\"%s\"", new FilenameCleaner(filename.get()).getCleanFilename());
			response.setHeader("Content-Disposition", disposition);
		}

		try (InputStream inputStream = resource.getResourceAsStream(); OutputStream outputStream = getOutputStream(response)) {
			StreamUtils.copy(inputStream, outputStream);
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		} finally {
			if (resource instanceof AjaxDomExportNode) {
				((AjaxDomExportNode) resource).dispose();
			}
		}
	}

	private ResourceDescription getResourceDescriptionOrThrow() {

		return getResourceDescription().orElseThrow(AjaxHttpNotFoundError::new);
	}

	private Optional<ResourceDescription> getResourceDescription() {

		// check for resource id
		String resourceId = request.getParameter(AjaxResourceUrl.getIdParameterName());
		if (resourceId != null) {
			return getResourceById(resourceId).map(ResourceDescription::new).map(it -> it.setCachable(false));
		}

		// check for resource hash
		String resourceHash = request.getParameter(AjaxResourceUrl.getHashParameterName());
		if (resourceHash != null) {
			return getResourceByHash(resourceHash).map(ResourceDescription::new).map(it -> it.setCachable(true));
		}

		throw new AjaxHttpBadRequestError("Missing resource URL parameter.");
	}

	private Optional<IResource> getResourceById(String resourceIdString) {

		int resourceId = IntegerParser//
			.parse(resourceIdString)
			.orElseThrow(() -> new AjaxHttpBadRequestError("Illegal resource ID: '%s'", resourceIdString));
		return getResourceRegistry().getResourceById(resourceId);
	}

	private Optional<IResource> getResourceByHash(String resourceHashString) {

		return getResourceRegistry().getResourceByHash(new ResourceHash(resourceHashString));
	}

	private AjaxResourceRegistry getResourceRegistry() {

		return AjaxResourceRegistry.getInstance(request.getHttpSession());
	}

	private OutputStream getOutputStream(HttpServletResponse response) {

		try {
			return response.getOutputStream();
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}
}
