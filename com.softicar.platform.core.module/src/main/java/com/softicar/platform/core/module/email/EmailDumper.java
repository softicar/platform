package com.softicar.platform.core.module.email;

import com.softicar.platform.common.core.exceptions.SofticarException;
import com.softicar.platform.common.core.exceptions.SofticarIOException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

class EmailDumper {

	private final File dumpFolder;
	private final MimeMessage message;

	public EmailDumper(MimeMessage message) {

		this.dumpFolder = EmailSystemProperties.DUMPING_FOLDER.getValue();
		this.message = message;
	}

	public void dump() {

		createDumpFolder();

		File dumpFile = getDumpFile();

		try (OutputStream outputStream = getOutputStream(dumpFile)) {
			message.writeTo(outputStream);
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		} catch (MessagingException exception) {
			throw new SofticarException(exception);
		}
	}

	private OutputStream getOutputStream(File file) {

		try {
			return new FileOutputStream(file);
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}

	private File getDumpFile() {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss'.'SSS");
		String filename = dateFormat.format(new Date());
		return new File(dumpFolder, filename);
	}

	private void createDumpFolder() {

		dumpFolder.mkdirs();
	}
}
