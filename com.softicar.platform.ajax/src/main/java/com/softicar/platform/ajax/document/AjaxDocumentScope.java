package com.softicar.platform.ajax.document;

import com.softicar.platform.ajax.customization.IAjaxStrategy;
import com.softicar.platform.ajax.request.IAjaxRequest;
import com.softicar.platform.common.core.singleton.SingletonSetScope;

/**
 * A {@link SingletonSetScope} that also handles the notification of the
 * {@link IAjaxStrategy}.
 *
 * @author Oliver Richers
 */
public class AjaxDocumentScope extends SingletonSetScope {

	public AjaxDocumentScope(IAjaxDocument document, IAjaxRequest request) {

		super(document.getSingletonSet());

		document.getAjaxFramework().getAjaxStrategy().beforeDocumentRequestHandling(document, request);
	}

	@Override
	public void close() {

		super.close();
	}
}
