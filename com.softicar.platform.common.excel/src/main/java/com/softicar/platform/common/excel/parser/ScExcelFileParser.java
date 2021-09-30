package com.softicar.platform.common.excel.parser;

import com.softicar.platform.common.container.map.list.ListTreeMap;
import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.excel.CommonExcelI18n;
import com.softicar.platform.common.io.buffer.ByteBuffer;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * Facilitates fetching the contents of XLS or XLSX based Excel files in a truly
 * type safe and simple manner.
 *
 * @author Alexander Schmidt
 */
public class ScExcelFileParser {

	private final InputStream inputStream;
	private final String fileName;
	private final List<Integer> onlySheetIndexes;
	private ListTreeMap<Integer, ScExcelRow> rowsBySheet;

	private static final String DEFAULT_FILE_NAME = "none";

	/**
	 * Prepares all sheets of the given file for being parsed.
	 *
	 * @param file
	 *            The Excel file to be parsed.
	 */
	public ScExcelFileParser(File file) {

		this(file, new ArrayList<Integer>());
	}

	/**
	 * Prepares the given sheet of the given file for being parsed.
	 *
	 * @param file
	 *            The Excel file to be parsed.
	 * @param onlySheetIndexes
	 *            The 0-based index of the only sheet to be read.
	 */
	public ScExcelFileParser(File file, Integer...onlySheetIndexes) {

		this(file, Arrays.asList(onlySheetIndexes));
	}

	/**
	 * Prepares the given sheets of the given file for being parsed.
	 *
	 * @param file
	 *            The Excel file to be parsed.
	 * @param onlySheetIndexes
	 *            A List of 0-based indexes of the only sheets to be read.
	 */
	public ScExcelFileParser(File file, List<Integer> onlySheetIndexes) {

		this(() -> ScExcelFileParserLib.getFileInputStream(file), ScExcelFileParserLib.getCanonicalFilePath(file), onlySheetIndexes);
	}

	/**
	 * @param byteBuffer
	 * @param fileName
	 */
	public ScExcelFileParser(ByteBuffer byteBuffer, String fileName) {

		this(byteBuffer, fileName, new ArrayList<>());
	}

	/**
	 * @param byteBuffer
	 * @param fileName
	 * @param onlySheetIndexes
	 */
	public ScExcelFileParser(ByteBuffer byteBuffer, String fileName, Integer...onlySheetIndexes) {

		this(byteBuffer, fileName, Arrays.asList(onlySheetIndexes));
	}

	/**
	 * @param byteBuffer
	 * @param fileName
	 * @param onlySheetIndexes
	 */
	public ScExcelFileParser(ByteBuffer byteBuffer, String fileName, List<Integer> onlySheetIndexes) {

		this(byteBuffer::getInputStream, fileName, onlySheetIndexes);
	}

	/**
	 * Prepares the given sheet of the given uploaded file for being parsed.
	 *
	 * @param streamSupplier
	 *            A {@link Supplier} for a stream of an Excel file to be parsed.
	 *            Make sure it has not been processed before.
	 */
	public ScExcelFileParser(Supplier<InputStream> streamSupplier, String fileName) {

		this(streamSupplier, fileName, new ArrayList<>());
	}

	/**
	 * @param streamSupplier
	 *            A {@link Supplier} for a stream of an Excel file to be parsed.
	 *            Make sure it has not been processed before.
	 * @param fileName
	 * @param onlySheetIndexes
	 */
	public ScExcelFileParser(Supplier<InputStream> streamSupplier, String fileName, List<Integer> onlySheetIndexes) {

		this.inputStream = streamSupplier.get();
		this.fileName = Optional.ofNullable(fileName).orElse(DEFAULT_FILE_NAME);
		this.onlySheetIndexes = assertValidSheetNumbers(onlySheetIndexes);
		this.rowsBySheet = null;
	}

	/**
	 * Converts the Excel file contents to a List of {@link ScExcelRow}s.
	 * <p>
	 * If a sheet was empty, its index won't occur as a key in the returned map.
	 *
	 * @return A Map from zero-based sheet indexes to associated Lists of
	 *         {@link ScExcelRow}s, representing the Excel file's contents.
	 */
	public ListTreeMap<Integer, ScExcelRow> getRowsBySheetIndex() {

		if (this.rowsBySheet == null) {
			try (Workbook wb = ScExcelFileParserLib.getWorkbookFromInputStream(this.inputStream)) {
				ListTreeMap<Integer, ScExcelRow> rowsBySheet = new ListTreeMap<>();
				List<Integer> sheetIndexes = ScExcelFileParserLib.getSheetIndexesInternal(wb, this.onlySheetIndexes);

				for (int sheetIndex: sheetIndexes) {
					Sheet sheet = wb.getSheetAt(sheetIndex);
					Integer lastPhysicalRowNumOrNull = ScExcelFileParserLib.getLastPhysicalRowNumOrNull(sheet);

					if (lastPhysicalRowNumOrNull != null) {
						for (int i = 0; i <= lastPhysicalRowNumOrNull; i++) {
							Row row = sheet.getRow(i);
							ScExcelRow excelRow = new ScExcelRow();

							if (row != null) {
								Iterator<Cell> cellIterator = row.cellIterator();

								while (cellIterator.hasNext()) {
									Cell cell = cellIterator.next();
									excelRow.put(cell.getColumnIndex(), new ScExcelCell(cell));
								}
							}

							rowsBySheet.addToList(sheetIndex, excelRow);
						}
					}
				}

				this.rowsBySheet = rowsBySheet;
			} catch (IOException exception) {
				throw new SofticarUserException(exception, createParsingErrorMessage(this.fileName));
			}
		}

		return this.rowsBySheet;
	}

	/**
	 * Converts the Excel file contents to a List of {@link ScExcelRow}s.
	 * <p>
	 * In case
	 * {@link ScExcelFileParserLib#getSheetIndexesInternal(Workbook, List)}
	 * returns more than one sheet index, the contents of the respective sheets
	 * are continuously appended to the resulting matrix, in the given order.
	 *
	 * @return A Map from sheet indexes to Lists of {@link ScExcelRow}s with
	 *         empty cells, representing the Excel file's contents.
	 */
	public ListTreeMap<Integer, ScExcelRow> getRowsBySheetIndexWithEmptyCells(int headerRow) {

		int lastColumn = 0;
		if (this.rowsBySheet == null) {

			try (Workbook wb = ScExcelFileParserLib.getWorkbookFromInputStream(this.inputStream)) {
				ListTreeMap<Integer, ScExcelRow> rowsBySheet = new ListTreeMap<>();
				List<Integer> sheetIndexes = ScExcelFileParserLib.getSheetIndexesInternal(wb, this.onlySheetIndexes);

				for (int sheetIndex: sheetIndexes) {
					Sheet sheet = wb.getSheetAt(sheetIndex);
					Integer lastPhysicalRowNumOrNull = ScExcelFileParserLib.getLastPhysicalRowNumOrNull(sheet);

					if (lastPhysicalRowNumOrNull != null) {
						for (int i = 0; i <= lastPhysicalRowNumOrNull; i++) {
							Row row = sheet.getRow(i);
							ScExcelRow excelRow = new ScExcelRow();

							if (row != null) {
								if (i == headerRow) {
									lastColumn = row.getPhysicalNumberOfCells();
								}
								for (int cellIndex = 0; cellIndex < lastColumn; cellIndex++) {
									Cell cell = row.getCell(cellIndex);
									excelRow.put(cell.getColumnIndex(), new ScExcelCell(cell));
								}
							}

							rowsBySheet.addToList(sheetIndex, excelRow);
						}
					}
				}

				this.rowsBySheet = rowsBySheet;
			} catch (IOException exception) {
				throw new SofticarUserException(exception, createParsingErrorMessage(this.fileName));
			}
		}

		return this.rowsBySheet;
	}

	private static List<Integer> assertValidSheetNumbers(List<Integer> onlySheetIndexes) {

		if (onlySheetIndexes != null) {
			for (Integer sheetNumber: onlySheetIndexes) {
				if (sheetNumber < 0) {
					throw new IllegalArgumentException("Negative sheet numbers given.");
				}
			}
		}
		return onlySheetIndexes;
	}

	private IDisplayString createParsingErrorMessage(String filename) {

		return CommonExcelI18n.FAILED_TO_PARSE_EXCEL_FILE_ARG1.toDisplay(filename);
	}
}
