package com.softicar.platform.dom.resource.preprocessed;

import com.softicar.platform.common.io.mime.IMimeType;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.common.io.resource.ResourceReader;
import com.softicar.platform.common.io.resource.hash.AbstractHashableResource;
import com.softicar.platform.dom.resource.set.IDomResourceSet;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;

public class DomPreprocessedCssResource extends AbstractHashableResource {

	private final IResource resource;
	private final IDomResourceSet resourceSet;
	private final String processedContent;

	public DomPreprocessedCssResource(IResource resource, IDomResourceSet resourceSet) {

		this.resource = Objects.requireNonNull(resource);
		this.resourceSet = Objects.requireNonNull(resourceSet);
		this.processedContent = preprocess(resource);
	}

	@Override
	public InputStream getResourceAsStream() {

		return new ByteArrayInputStream(processedContent.getBytes(StandardCharsets.UTF_8));
	}

	@Override
	public IMimeType getMimeType() {

		return resource.getMimeType();
	}

	@Override
	public Optional<Charset> getCharset() {

		return resource.getCharset();
	}

	@Override
	public Optional<String> getFilename() {

		return resource.getFilename();
	}

	private String preprocess(IResource resource) {

		var urlFactory = new DomCssPreprocessorResourceUrlFactory(resourceSet);
		return new DomCssPreprocessor(urlFactory::create)//
			.preprocess(ResourceReader.readAllText(resource));
	}
}
