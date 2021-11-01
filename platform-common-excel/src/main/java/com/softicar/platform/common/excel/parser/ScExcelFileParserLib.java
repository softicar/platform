package com.softicar.platform.common.excel.parser;

import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.common.io.StreamUtils;
import com.softicar.platform.common.io.buffer.ByteBuffer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Static helper and conversion methods for {@link ScExcelFileParser}.
 *
 * @author Alexander Schmidt
 */
public class ScExcelFileParserLib {

	public static FileInputStream getFileInputStream(File file) {

		try {
			return new FileInputStream(file);
		} catch (FileNotFoundException exception) {
			throw new RuntimeException(exception);
		}
	}

	public static String getCanonicalFilePath(File file) {

		try {
			return file.getCanonicalPath();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	public static Integer getLastPhysicalRowNumOrNull(Sheet sheet) {

		Iterator<Row> physicalRowIterator = sheet.rowIterator();
		Integer lastRowNum = null;

		while (physicalRowIterator.hasNext()) {
			lastRowNum = physicalRowIterator.next().getRowNum();
		}

		return lastRowNum;
	}

	public static List<Integer> getSheetIndexesInternal(Workbook wb, List<Integer> sheetIndexesToRead) {

		if (sheetIndexesToRead == null || sheetIndexesToRead.isEmpty()) {
			sheetIndexesToRead = new ArrayList<>();
		}

		if (sheetIndexesToRead.isEmpty()) {
			for (int i = 0; i < wb.getNumberOfSheets(); i++) {
				sheetIndexesToRead.add(i);
			}
		}

		return sheetIndexesToRead;
	}

	/**
	 * Note: Caller is expected to close the returned instance.
	 */
	@SuppressWarnings("resource")
	public static Workbook getWorkbookFromInputStream(InputStream inputStream) throws IOException {

		ByteBuffer byteBuffer = new ByteBuffer(StreamUtils.toByteArray(inputStream));
		Workbook wb = getXlsWorkbook(byteBuffer);
		if (wb == null) {
			wb = getXlsxWorkbook(byteBuffer);
		}

		if (wb == null) {
			throw new IOException("Invalid Excel file format.");
		} else {
			return wb;
		}
	}

	private static Workbook getXlsxWorkbook(ByteBuffer buffer) {

		try (InputStream inputStream = buffer.getInputStream()) {
			return new XSSFWorkbook(inputStream);
		} catch (Exception e) {
			DevNull.swallow(e);
			return null;
		}
	}

	private static Workbook getXlsWorkbook(ByteBuffer buffer) {

		try (InputStream inputStream = buffer.getInputStream()) {
			return new HSSFWorkbook(inputStream);
		} catch (Exception e) {
			Log.ferror(e);
			return null;
		}
	}
}
