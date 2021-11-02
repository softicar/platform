package com.softicar.platform.ajax.testing;

import com.softicar.platform.ajax.customization.AbstractAjaxStrategy;
import com.softicar.platform.ajax.document.IAjaxDocument;
import com.softicar.platform.ajax.export.AjaxDomExportNode;
import com.softicar.platform.ajax.export.AjaxMemoryBuffer;
import com.softicar.platform.ajax.export.IAjaxExportBuffer;
import com.softicar.platform.ajax.request.CurrentAjaxRequest;
import com.softicar.platform.ajax.request.IAjaxRequest;
import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.document.DomBody;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.node.IDomNode;
import java.util.Collection;
import java.util.HashSet;
import java.util.function.Function;

public class AjaxTestingStrategy extends AbstractAjaxStrategy {

	private final Collection<IDomNode> exportIframes;
	private Function<IAjaxDocument, ? extends IDomNode> nodeFactory;

	public AjaxTestingStrategy() {

		this.exportIframes = new HashSet<>();
		this.nodeFactory = dummy -> new DomDiv();
	}

	public void setNodeFactory(Function<IAjaxDocument, ? extends IDomNode> nodeFactory) {

		this.nodeFactory = nodeFactory;
	}

	@Override
	public void buildDocument(IAjaxDocument document) {

		document.appendToBody(nodeFactory.apply(document));
	}

	@Override
	public void beforeDocumentRequestHandling(IAjaxDocument document, IAjaxRequest request) {

		CurrentAjaxRequest.set(document, request);
	}

	@Override
	public IAjaxExportBuffer createExportBuffer() {

		return new NonDisposableExportBuffer();
	}

	// This buffer ensures that exports can be downloaded several times
	// which is important for the AjaxDownloadTest to work.
	// This is actually a dirty solution but there is hardly any other
	// way to properly test the content of an AJAX download with Selenium.
	// If you find a cleaner solution, feel free to replace this class.
	private class NonDisposableExportBuffer extends AjaxMemoryBuffer {

		private final DomBody body;

		public NonDisposableExportBuffer() {

			this.body = CurrentDomDocument.get().getBody();
		}

		@Override
		public void dispose() {

			// Collecting all export iframes to ensure
			// that they are referred to by a strong reference,
			// so that they are not collected.
			body//
				.getChildren()
				.stream()
				.filter(node -> node instanceof AjaxDomExportNode)
				.forEach(exportIframes::add);
		}
	}
}
