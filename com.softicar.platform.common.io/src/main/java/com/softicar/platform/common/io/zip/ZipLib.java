package com.softicar.platform.common.io.zip;

import com.softicar.platform.common.core.exceptions.SofticarException;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.common.io.buffer.ByteBuffer;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Facilitates handling DEFLATE-compressed ("zipped") files.
 *
 * @author Alexander Schmidt
 */
public class ZipLib {

	private static final int UNZIP_BUFFER_SIZE = 2048;
	private static final Day DUMMY_FILE_TIME = Day.get1970();

	public static byte[] zipSingleFile(ByteBuffer byteBuffer, String fileName, boolean useDummyFileTime) {

		return zipSingleFile(byteBuffer.getBytes(), fileName, useDummyFileTime);
	}

	public static byte[] zipMultipleFiles(List<FilenameAndContent> files, boolean useDummyFileTime) {

		try {
			try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
				try (ZipOutputStream zipOutputStream = new ZipOutputStream(bos)) {
					for (FilenameAndContent file: files) {
						addZipEntry(zipOutputStream, file.getFilename(), useDummyFileTime, file.getContent());
					}
				}
				return bos.toByteArray();
			}
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	public static byte[] zipSingleFile(ByteArrayOutputStream fileBytes, String fileName, boolean useDummyFileTime) {

		return zipSingleFile(fileBytes.toByteArray(), fileName, useDummyFileTime);
	}

	public static byte[] zipSingleFile(byte[] fileBytes, String fileName, boolean useDummyFileTime) {

		try {
			try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
				try (ZipOutputStream zipOutputStream = new ZipOutputStream(bos)) {
					addZipEntry(zipOutputStream, fileName, useDummyFileTime, fileBytes);
				}
				return bos.toByteArray();
			}
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	public static FilenameAndContent unzipSingleFile(byte[] zippedFileBytes) {

		List<FilenameAndContent> result = unzipMultipleFiles(zippedFileBytes);
		if (result.size() == 1) {
			return result.get(0);
		} else if (result.isEmpty()) {
			// TODO: should return null
			return new FilenameAndContent(null, new byte[0]);
		} else {
			throw new SofticarException("Expected a single file but encountered %s files.", result.size());
		}
	}

	public static List<FilenameAndContent> unzipMultipleFiles(byte[] zippedFileBytes) {

		try {
			List<FilenameAndContent> result = new ArrayList<>();
			try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
				try (ZipInputStream inputStream = new ZipInputStream(new ByteArrayInputStream(zippedFileBytes))) {
					String fileName = null;
					ZipEntry entry;
					while ((entry = inputStream.getNextEntry()) != null) {
						int count;
						byte data[] = new byte[UNZIP_BUFFER_SIZE];
						fileName = entry.getName();
						try (BufferedOutputStream dest = new BufferedOutputStream(outputStream, UNZIP_BUFFER_SIZE)) {
							while ((count = inputStream.read(data, 0, UNZIP_BUFFER_SIZE)) != -1) {
								dest.write(data, 0, count);
							}
							dest.flush();
						}
						byte[] outputBytes = outputStream.toByteArray();
						result.add(new ZipLib.FilenameAndContent(fileName, outputBytes));
					}
					return result;
				}
			}
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	private static void addZipEntry(ZipOutputStream zipOutputStream, String fileName, boolean useDummyFileTime, byte[] bytes) throws IOException {

		ZipEntry zipEntry = new ZipEntry(fileName);

		if (useDummyFileTime) {
			zipEntry.setTime(DUMMY_FILE_TIME.toMillis());
		}

		zipOutputStream.putNextEntry(zipEntry);
		zipOutputStream.write(bytes);
	}

	public static class FilenameAndContent {

		private final String filename;
		private final byte[] content;

		public FilenameAndContent(String filename, byte[] content) {

			this.filename = filename;
			this.content = content;
		}

		public String getFilename() {

			return filename;
		}

		public byte[] getContent() {

			return content;
		}
	}
}
