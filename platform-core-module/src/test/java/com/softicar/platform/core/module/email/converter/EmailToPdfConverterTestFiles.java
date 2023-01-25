package com.softicar.platform.core.module.email.converter;

import com.softicar.platform.common.io.resource.container.ResourceSupplierContainer;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplierFactory;
import com.softicar.platform.common.io.resource.supplier.ResourceSupplierFactory;

@ResourceSupplierContainer
public interface EmailToPdfConverterTestFiles {

	IResourceSupplierFactory FACTORY = new ResourceSupplierFactory(EmailToPdfConverterTestFiles.class);

	IResourceSupplier EML_HTML = FACTORY.create("eml-html.eml");
	IResourceSupplier EML_PLAIN = FACTORY.create("eml-plain.eml");
	IResourceSupplier EML_HTML_WITH_NON_ESCAPED_AMPERSAND_IN_SCRIPT_AND_EMBEDDED_IMAGES =
			FACTORY.create("eml-html-with-non-escaped-ampersand-in-script-and-embedded-images.eml");
	IResourceSupplier MSG_HTML = FACTORY.create("msg-html.msg");
	IResourceSupplier MSG_PLAIN = FACTORY.create("msg-plain.msg");
}
