package com.softicar.platform.core.module.email.converter;

import com.softicar.platform.common.io.resource.container.ResourceSupplierContainer;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplierFactory;
import com.softicar.platform.common.io.resource.supplier.ResourceSupplierFactory;

@ResourceSupplierContainer
public interface EmailToPdfConverterTestFiles {

	IResourceSupplierFactory FACTORY = new ResourceSupplierFactory(EmailToPdfConverterTestFiles.class);

	IResourceSupplier EML_HTML = FACTORY.create("eml-html.eml");
	IResourceSupplier EML_HTML_WITH_EMBEDDED_IMAGE_VIA_CONTENT_ID = FACTORY.create("eml-html-with-embedded-image-via-content-id.eml");
	IResourceSupplier EML_HTML_WITH_EMBEDDED_IMAGE_VIA_CONTENT_ID_AND_BODY_ENCODING_BASE64 =
			FACTORY.create("eml-html-with-embedded-image-via-content-id-and-body-encoding-base64.eml");
	IResourceSupplier EML_HTML_WITH_EMBEDDED_IMAGE_VIA_X_ATTACHMENT_ID = FACTORY.create("eml-html-with-embedded-image-via-x-attachment-id.eml");
	IResourceSupplier EML_PLAIN = FACTORY.create("eml-plain.eml");
	IResourceSupplier MSG_HTML = FACTORY.create("msg-html.msg");
	IResourceSupplier MSG_PLAIN = FACTORY.create("msg-plain.msg");
}
