package com.softicar.platform.dom.event.upload;

/**
 * This interface can be implemented to handle file uploads.
 *
 * @author Oliver Richers
 */
@FunctionalInterface
public interface IDomFileUploadHandler {

	/**
	 * Handles the file uploads.
	 * <p>
	 * The fileUploads parameter contains only the valid uploads. File input
	 * elements without a selected file will not be contained.
	 *
	 * @param fileUploads
	 *            the files that the user wants to upload
	 */
	void handleFileUploads(Iterable<IDomFileUpload> fileUploads);
}
