package com.softicar.platform.core.module.file.stored.upload;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.common.io.mime.MimeType;
import com.softicar.platform.core.module.CoreTestMarker;
import com.softicar.platform.core.module.file.stored.AGStoredFile;
import com.softicar.platform.core.module.test.fixture.CoreModuleTestFixtureMethods;
import com.softicar.platform.db.runtime.test.AbstractDbTest;
import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.DomTestMarker;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.testing.engine.IDomTestExecutionEngine;
import com.softicar.platform.dom.elements.testing.engine.IDomTestExecutionEngineMethods;
import com.softicar.platform.dom.elements.testing.engine.document.DomDocumentTestExecutionEngine;
import com.softicar.platform.dom.elements.testing.node.tester.DomNodeTester;
import com.softicar.platform.dom.elements.upload.DomUploadForm;
import com.softicar.platform.dom.event.upload.IDomFileUpload;
import com.softicar.platform.dom.event.upload.IDomFileUploadHandler;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.apache.commons.io.IOUtils;
import org.junit.Rule;
import org.junit.Test;

public class StoredFileUploadDivTest extends AbstractDbTest implements IDomTestExecutionEngineMethods, CoreModuleTestFixtureMethods {

	@Rule public IDomTestExecutionEngine engine = new DomDocumentTestExecutionEngine();

	private final DomDiv testDiv;
	private int addHandlerCalls;
	private int removeHandlerCalls;
	private StoredFileUploadDiv uploadDiv;

	public StoredFileUploadDivTest() {

		this.testDiv = new DomDiv();
		setNodeSupplier(() -> testDiv);

		this.addHandlerCalls = 0;
		this.removeHandlerCalls = 0;
		this.uploadDiv = null;

		// Enforce proper initialization.
		// TODO This should not be necessary.
		findBody();
	}

	@Override
	public IDomTestExecutionEngine getEngine() {

		return engine;
	}

	// -------------------------------- user interaction -------------------------------- //

	@Test
	public void testUpload() {

		// setup
		useMultipleStoredFileUploadDiv();

		// execute
		uploadFiles(//
			new FileUpload("foo.txt", "this is foo"),
			new FileUpload("bar.txt", "this is bar"));

		// assert results
		assertUploadFormPresent();
		assertFileTable("foo.txt", "bar.txt");
		assertAddHandlerCalls(1);
		assertRemoveHandlerCalls(0);
	}

	@Test
	public void testUploadAndFurtherUpload() {

		// setup
		useMultipleStoredFileUploadDiv();

		// execute
		uploadFiles(//
			new FileUpload("foo.txt", "this is foo"),
			new FileUpload("bar.txt", "this is bar"));
		uploadFiles(//
			new FileUpload("qwe.txt", "this is qwe"));

		// assert results
		assertUploadFormPresent();
		assertFileTable("foo.txt", "bar.txt", "qwe.txt");
		assertAddHandlerCalls(2);
		assertRemoveHandlerCalls(0);
	}

	@Test
	public void testUploadWithSingleMode() {

		// setup
		useSingleStoredFileUploadDiv();

		// execute
		uploadFiles(new FileUpload("foo.txt", "this is foo"));

		// assert results
		assertUploadFormAbsent();
		assertFileTable("foo.txt");
		assertAddHandlerCalls(1);
		assertRemoveHandlerCalls(0);
	}

	@Test
	public void testUploadWithSingleModeAndMultipleFiles() {

		// setup
		useSingleStoredFileUploadDiv();

		// execute
		assertException(//
			SofticarUserException.class,
			"Only a single file may be uploaded.",
			() -> uploadFiles(//
				new FileUpload("foo.txt", "this is foo"),
				new FileUpload("bar.txt", "this is bar")));
	}

	@Test
	public void testUploadAndRemoval() {

		// setup
		useMultipleStoredFileUploadDiv();

		// execute
		uploadFiles(//
			new FileUpload("foo.txt", "this is foo"),
			new FileUpload("bar.txt", "this is bar"),
			new FileUpload("qwe.txt", "this is qwe"));
		removeFile("bar.txt");

		// assert results
		assertUploadFormPresent();
		assertFileTable("foo.txt", "qwe.txt");
		assertAddHandlerCalls(1);
		assertRemoveHandlerCalls(1);
	}

	// -------------------------------- programmatic stuff -------------------------------- //

	@Test
	public void testAfterConstruction() {

		// setup
		useMultipleStoredFileUploadDiv();

		// assert results
		assertUploadFormPresent();
		assertFileTable();
		assertNoAddHandlerCalls();
		assertNoRemoveHandlerCalls();
	}

	@Test
	public void testSetFiles() {

		// setup
		useMultipleStoredFileUploadDiv();

		var fooFile = insertStoredFile("foo.txt");
		var barFile = insertStoredFile("bar.txt");
		var earlierFiles = List.of(fooFile, barFile);

		var qweFile = insertStoredFile("qwe.txt");
		var asdFile = insertStoredFile("asd.txt");
		var laterFiles = List.of(qweFile, asdFile);

		// execute
		uploadDiv.setFiles(earlierFiles);

		// assert results
		assertUploadFormPresent();
		assertFileTable("foo.txt", "bar.txt");
		assertAddHandlerCalls(1);
		assertRemoveHandlerCalls(0);

		// execute more
		uploadDiv.setFiles(laterFiles);

		// assert more results
		assertUploadFormPresent();
		assertFileTable("qwe.txt", "asd.txt");
		assertAddHandlerCalls(2);
		assertRemoveHandlerCalls(1);
	}

	@Test
	public void testSetFilesWithSingleModeAndSingleFile() {

		// setup
		useSingleStoredFileUploadDiv();

		var fooFile = insertStoredFile("foo.txt");
		var barFile = insertStoredFile("bar.txt");

		// execute
		uploadDiv.setFiles(List.of(fooFile));

		// assert results
		assertUploadFormAbsent();
		assertFileTable("foo.txt");
		assertAddHandlerCalls(1);
		assertRemoveHandlerCalls(0);

		// execute more
		uploadDiv.setFiles(List.of(barFile));

		// assert more results
		assertUploadFormAbsent();
		assertFileTable("bar.txt");
		assertAddHandlerCalls(2);
		assertRemoveHandlerCalls(1);
	}

	@Test
	public void testSetFilesWithSingleModeAndMultipleFiles() {

		// setup
		useSingleStoredFileUploadDiv();

		var fooFile = insertStoredFile("foo.txt");
		var barFile = insertStoredFile("bar.txt");

		// execute
		uploadDiv.setFiles(List.of(fooFile, barFile));

		// assert results
		assertUploadFormAbsent();
		assertFileTable("foo.txt", "bar.txt");
		assertAddHandlerCalls(1);
		assertRemoveHandlerCalls(0);
	}

	@Test
	public void testSetDisabled() {

		// setup
		useMultipleStoredFileUploadDiv();

		var fooFile = insertStoredFile("foo.txt");
		var barFile = insertStoredFile("bar.txt");
		uploadDiv.setFiles(List.of(fooFile, barFile));
		assertFileTable("foo.txt", "bar.txt");

		// assert initial state
		assertUploadFormPresent();

		// execute
		uploadDiv.setDisabled(true);

		// assert results
		assertUploadFormAbsent();
		findNodes(CoreTestMarker.STORED_FILE_REMOVE_FILE_BUTTON)//
			.assertNone();
		findNodes(CoreTestMarker.STORED_FILE_DOWNLOAD_FILE_BUTTON)//
			.assertSize(2);
	}

	@Test
	public void testIsDisabled() {

		// setup
		useMultipleStoredFileUploadDiv();

		// assert initial state
		assertFalse(uploadDiv.isDisabled());

		// execute
		uploadDiv.setDisabled(true);

		// assert results
		assertTrue(uploadDiv.isDisabled());
	}

	// TODO PLAT-887 Gather more detailed information about handler calls, and improve related assertions.
	private void handleAdd(Collection<AGStoredFile> existingFiles, Collection<AGStoredFile> addedFiles) {

		DevNull.swallow(existingFiles);
		DevNull.swallow(addedFiles);
		++this.addHandlerCalls;
	}

	// TODO PLAT-887 Gather more detailed information about handler calls, and improve related assertions.
	private void handleRemove(Collection<AGStoredFile> existingFiles, Collection<AGStoredFile> removedFiles) {

		DevNull.swallow(existingFiles);
		DevNull.swallow(removedFiles);
		++this.removeHandlerCalls;
	}

	/**
	 * Performs file uploads by simulating a user interaction with
	 * {@link DomUploadForm}. Since we cannot leverage native windows while
	 * testing, this must be sufficient.
	 */
	private void uploadFiles(IDomFileUpload...fileUploads) {

		var uploadHandlerNode = findNode(DomUploadForm.class).getNode();
		var uploadHandler = (IDomFileUploadHandler) uploadHandlerNode;
		uploadHandler.handleFileUploads(Arrays.asList(fileUploads));
	}

	private void removeFile(String filename) {

		findNodes(CoreTestMarker.STORED_FILE_DOWNLOAD_FILE_BUTTON)
			.withText(filename)
			.assertOne()
			.getParentOrThrow()
			.findButton(CoreTestMarker.STORED_FILE_REMOVE_FILE_BUTTON)
			.click();
		findNode(DomTestMarker.MODAL_CONFIRM_OKAY_BUTTON).click();
	}

	private void assertUploadFormPresent() {

		findNode(DomUploadForm.class).assertContainsText(DomI18n.CHOOSE_OR_DROP_FILE);
	}

	private void assertUploadFormAbsent() {

		findNodes(DomUploadForm.class).assertNone();
	}

	private void assertFileTable(String...filenames) {

		assertRemoveFileButtons(filenames.length);
		List<DomNodeTester> downloadButtons = assertDownloadFileButtons(filenames.length);
		for (int i = 0; i < filenames.length; i++) {
			String expectedLabel = filenames[i];
			DomNodeTester button = downloadButtons.get(i);
			button.assertText(expectedLabel);
		}
	}

	private List<DomNodeTester> assertRemoveFileButtons(int size) {

		return findNodes(CoreTestMarker.STORED_FILE_REMOVE_FILE_BUTTON).assertSize(size);
	}

	private List<DomNodeTester> assertDownloadFileButtons(int size) {

		return findNodes(CoreTestMarker.STORED_FILE_DOWNLOAD_FILE_BUTTON).assertSize(size);
	}

	private void assertAddHandlerCalls(int size) {

		assertEquals(size, addHandlerCalls);
	}

	private void assertNoAddHandlerCalls() {

		assertAddHandlerCalls(0);
	}

	private void assertRemoveHandlerCalls(int size) {

		assertEquals(size, removeHandlerCalls);
	}

	private void assertNoRemoveHandlerCalls() {

		assertRemoveHandlerCalls(0);
	}

	private void useSingleStoredFileUploadDiv() {

		useStoredFileUploadDiv(false);
	}

	private void useMultipleStoredFileUploadDiv() {

		useStoredFileUploadDiv(true);
	}

	private void useStoredFileUploadDiv(boolean multiple) {

		uploadDiv = new StoredFileUploadDiv(//
			StoredFileUploadDivTest.this::handleAdd,
			StoredFileUploadDivTest.this::handleRemove,
			multiple);
		testDiv.appendChild(uploadDiv);
	}

	private static class FileUpload implements IDomFileUpload {

		private final String filename;
		private final String content;

		public FileUpload(String filename, String content) {

			this.filename = filename;
			this.content = content;
		}

		@Override
		public String getFilename() {

			return filename;
		}

		@Override
		public InputStream getStream() {

			return IOUtils.toInputStream(content, StandardCharsets.UTF_8);
		}

		@Override
		public String getContentType() {

			return MimeType.TEXT_PLAIN.getIdentifier();
		}
	}

//	private static class HandlerCall {
//
//		private final Collection<AGStoredFile> existingFiles;
//		private final Collection<AGStoredFile> changedFiles;
//
//		public HandlerCall(Collection<AGStoredFile> existingFiles, Collection<AGStoredFile> changedFiles) {
//
//			this.existingFiles = Objects.requireNonNull(existingFiles);
//			this.changedFiles = Objects.requireNonNull(changedFiles);
//		}
//
//		public Collection<AGStoredFile> getExistingFiles() {
//
//			return existingFiles;
//		}
//
//		public Collection<AGStoredFile> getChangedFiles() {
//
//			return changedFiles;
//		}
//	}
}
