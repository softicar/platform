package com.softicar.platform.core.module.file.upload;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.Consumers;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.common.io.mime.CustomMimeType;
import com.softicar.platform.core.module.CoreCssClasses;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.file.stored.AGStoredFile;
import com.softicar.platform.core.module.file.stored.StoredFileBuilder;
import com.softicar.platform.core.module.user.CurrentUser;
import com.softicar.platform.db.core.transaction.DbTransaction;
import com.softicar.platform.dom.DomImages;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomFileInput;
import com.softicar.platform.dom.elements.DomForm;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.event.upload.IDomFileUpload;
import com.softicar.platform.dom.event.upload.IDomFileUploadHandler;
import com.softicar.platform.emf.EmfI18n;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Consumer;

public class FileDropArea extends DomForm implements IDomFileUploadHandler {

	private final boolean multipleFiles;
	private final boolean alwaysOpen;
	private final DomDiv dropAreaDiv;
	private final FileInput fileInput;
	private Consumer<Collection<AGStoredFile>> callback;

	public FileDropArea(IDisplayString label, boolean multipleFiles, boolean closable) {

		this(label, multipleFiles, closable, false);
	}

	public FileDropArea(IDisplayString label, boolean multipleFiles, boolean closable, boolean alwaysOpen) {

		super(true);

		this.multipleFiles = multipleFiles;
		this.alwaysOpen = alwaysOpen;
		this.callback = Consumers.noOperation();

		this.dropAreaDiv = appendChild(new DomDiv());
		getDropAreaDiv().addCssClass(CoreCssClasses.FILE_DROP_AREA_DIV);
		if (!alwaysOpen) {
			getDropAreaDiv().addCssClass(CoreCssClasses.FILE_DROP_AREA_DIV_HIDDEN);
			// TODO: do this properly
			getDropAreaDiv()
				.setAttribute(
					"ondragenter",
					"if(event.dataTransfer.types.indexOf('Files') != -1 || event.dataTransfer.types.contains('Files')) this.classList.remove('"
							+ CoreCssClasses.FILE_DROP_AREA_DIV_HIDDEN.getName() + "')");
		}
		getDropAreaDiv()
			.appendChild(
				Optional
					.ofNullable(label)
					.orElse(
						multipleFiles//
								? CoreI18n.CLICK_OR_DROP_FILES_HERE
								: CoreI18n.CLICK_OR_DROP_FILE_HERE));
		this.fileInput = getDropAreaDiv().appendChild(new FileInput());
		if (closable) {
			getDropAreaDiv()
				.appendChild(new DomButton())
				.setIcon(DomImages.DIALOG_CLOSE.getResource())
				.setClickCallback(() -> getDropAreaDiv().addCssClass(CoreCssClasses.FILE_DROP_AREA_DIV_HIDDEN))
				.addCssClass(CoreCssClasses.FILE_DROP_AREA_DIV_CLOSE_BUTTON);
		}
	}

	public FileDropArea setAfterDropCallback(Consumer<Collection<AGStoredFile>> callback) {

		this.callback = callback;
		return this;
	}

	@Override
	public void handleFileUploads(Iterable<IDomFileUpload> fileUploads) {

		if (!alwaysOpen) {
			getDropAreaDiv().addCssClass(CoreCssClasses.FILE_DROP_AREA_DIV_HIDDEN);
		}
		if (fileUploads.iterator().hasNext()) {
			Collection<AGStoredFile> files = saveFiles(fileUploads);
			if (!multipleFiles && files.size() > 1) {
				throw new SofticarUserException(EmfI18n.ONLY_ONE_SINGLE_FILE_MAY_BE_ATTACHED);
			}
			fileInput.setAttribute("value", ""); // FIXME: replace with proper method, once available
			callback.accept(files);
		}
	}

	private Collection<AGStoredFile> saveFiles(Iterable<IDomFileUpload> fileUploads) {

		Collection<AGStoredFile> files = new ArrayList<>();
		try (DbTransaction transaction = new DbTransaction()) {
			for (IDomFileUpload upload: fileUploads) {
				AGStoredFile file = new StoredFileBuilder()
					.setContentType(new CustomMimeType(upload.getContentType()))
					.setCreatedBy(CurrentUser.get())
					.setFilename(upload.getFilename())
					.setRemoveAt(DayTime.now().plusDays(1))
					.build();

				try (InputStream inputStream = upload.getStream()) {
					file.uploadFileContent(inputStream);
				} catch (IOException exception) {
					throw new RuntimeException(exception);
				}

				files.add(file);
			}
			transaction.commit();
		}
		return files;
	}

	public DomDiv getDropAreaDiv() {

		return dropAreaDiv;
	}

	private class FileInput extends DomFileInput {

		public FileInput() {

			addCssClass(CoreCssClasses.STORED_FILE_UPLOAD_INPUT);
			setTabIndex(-1);
			setMultiple(true);
			setOnChangeHandler(() -> uploadFiles());
		}
	}
}
