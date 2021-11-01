package com.softicar.platform.ajax.upload;

import com.softicar.platform.common.io.StreamUtils;
import com.softicar.platform.dom.elements.DomAnchor;
import com.softicar.platform.dom.elements.DomFileInput;
import com.softicar.platform.dom.elements.DomForm;
import com.softicar.platform.dom.event.upload.IDomFileUpload;
import com.softicar.platform.dom.event.upload.IDomFileUploadHandler;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

class AjaxUploadTestForm extends DomForm implements IDomFileUploadHandler {

	private final DomFileInput fileInput;
	private final SubmitAnchor submitAnchor;
	private final Collection<UploadData> uploads;

	public AjaxUploadTestForm() {

		super(true);

		this.fileInput = appendChild(new DomFileInput());
		this.submitAnchor = appendChild(new SubmitAnchor(this));
		this.uploads = new ArrayList<>();
	}

	public DomFileInput getFileInput() {

		return fileInput;
	}

	public SubmitAnchor getSubmitAnchor() {

		return submitAnchor;
	}

	public Collection<UploadData> getUploads() {

		return uploads;
	}

	@Override
	public void handleFileUploads(Iterable<IDomFileUpload> fileUploads) {

		fileUploads.forEach(upload -> uploads.add(new UploadData(upload)));
	}

	public class UploadData {

		private final String filename;
		private final String contentType;
		private final byte[] content;

		public UploadData(IDomFileUpload upload) {

			this.filename = upload.getFilename();
			this.contentType = upload.getContentType();
			try (InputStream stream = upload.getStream()) {
				this.content = StreamUtils.toByteArray(stream);
			} catch (IOException exception) {
				throw new RuntimeException(exception);
			}
		}

		public String getFilename() {

			return filename;
		}

		public String getContentType() {

			return contentType;
		}

		public byte[] getContent() {

			return content;
		}
	}

	private static class SubmitAnchor extends DomAnchor {

		public SubmitAnchor(DomForm form) {

			appendText("Submit");
			setAttribute("href", getHRef(form));
		}

		private String getHRef(DomForm form) {

			return new StringBuilder().append("javascript:GLOBAL.context.submitForm(").append(form.getNodeId()).append(");").toString();
		}
	}
}
