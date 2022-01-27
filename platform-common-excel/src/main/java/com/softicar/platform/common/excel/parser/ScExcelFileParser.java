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
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * Facilitates fetching the contents of XLS or XLSX based Excel files.
 * <p>
 * TODO Create a builder, instead of using telescope constructors.
 *
 * @author Alexander Schmidt
 */
public class ScExcelFileParser {

	private final Supplier<InputStream> inputStreamSupplier;
	private final String fileName;
	private final List<Integer> sheetIndexes;
	private ListTreeMap<Integer, ScExcelRow> rowsBySheet;

	private static final String DEFAULT_FILE_NAME = "none";

	/**
	 * Creates an {@link ScExcelFileParser} for all sheets of the given Excel
	 * {@link File}.
	 *
	 * @param file
	 *            the Excel {@link File} to be parsed (never <i>null</i>)
	 */
	public ScExcelFileParser(File file) {

		this(file, new ArrayList<Integer>());
	}

	/**
	 * Creates an {@link ScExcelFileParser} for the given sheets of the given
	 * Excel {@link File}.
	 *
	 * @param file
	 *            the Excel {@link File} to be parsed (never <i>null</i>)
	 * @param sheetIndexes
	 *            the 0-based indexes of the sheets to be parsed (never
	 *            <i>null</i>)
	 */
	public ScExcelFileParser(File file, Integer...sheetIndexes) {

		this(file, Arrays.asList(sheetIndexes));
	}

	/**
	 * Creates an {@link ScExcelFileParser} for the given sheets of the given
	 * Excel {@link File}.
	 *
	 * @param file
	 *            the Excel {@link File} to be parsed (never <i>null</i>)
	 * @param sheetIndexes
	 *            the 0-based indexes of the sheets to be parsed (never
	 *            <i>null</i>)
	 */
	public ScExcelFileParser(File file, List<Integer> sheetIndexes) {

		this(() -> ScExcelFileParserLib.getFileInputStream(file), ScExcelFileParserLib.getCanonicalFilePath(file), sheetIndexes);
	}

	/**
	 * Creates an {@link ScExcelFileParser} for all sheets of the given Excel
	 * file content.
	 *
	 * @param byteBuffer
	 *            a {@link ByteBuffer} that provides the content of the Excel
	 *            file (never <i>null</i>)
	 */
	public ScExcelFileParser(ByteBuffer byteBuffer) {

		this(byteBuffer, null);
	}

	/**
	 * Creates an {@link ScExcelFileParser} for all sheets of the given Excel
	 * file content.
	 *
	 * @param byteBuffer
	 *            a {@link ByteBuffer} that provides the content of the Excel
	 *            file (never <i>null</i>)
	 * @param fileName
	 *            the name of the Excel file to be parsed (may be <i>null</i>)
	 */
	public ScExcelFileParser(ByteBuffer byteBuffer, String fileName) {

		this(byteBuffer, fileName, new ArrayList<>());
	}

	/**
	 * Creates an {@link ScExcelFileParser} for the given sheets of the given
	 * Excel file content.
	 *
	 * @param byteBuffer
	 *            a {@link ByteBuffer} that provides the content of the Excel
	 *            file (never <i>null</i>)
	 * @param fileName
	 *            the name of the Excel file to be parsed (may be <i>null</i>)
	 * @param sheetIndexes
	 *            the 0-based indexes of the sheets to be parsed (never
	 *            <i>null</i>)
	 */
	public ScExcelFileParser(ByteBuffer byteBuffer, String fileName, Integer...sheetIndexes) {

		this(byteBuffer, fileName, Arrays.asList(sheetIndexes));
	}

	/**
	 * Creates an {@link ScExcelFileParser} for the given sheets of the given
	 * Excel file content.
	 *
	 * @param byteBuffer
	 *            a {@link ByteBuffer} that provides the content of the Excel
	 *            file (never <i>null</i>)
	 * @param fileName
	 *            the name of the Excel file to be parsed (may be <i>null</i>)
	 * @param sheetIndexes
	 *            the 0-based indexes of the sheets to be parsed (never
	 *            <i>null</i>)
	 */
	public ScExcelFileParser(ByteBuffer byteBuffer, String fileName, List<Integer> sheetIndexes) {

		this(byteBuffer::getInputStream, fileName, sheetIndexes);
	}

	/**
	 * Creates an {@link ScExcelFileParser} for all sheets of the Excel file
	 * content provided by the given {@link InputStream} {@link Supplier}.
	 *
	 * @param inputStreamSupplier
	 *            a {@link Supplier} of an {@link InputStream} of the content of
	 *            an Excel file to be parsed (never <i>null</i>)
	 */
	public ScExcelFileParser(Supplier<InputStream> inputStreamSupplier) {

		this(inputStreamSupplier, null);
	}

	/**
	 * Creates an {@link ScExcelFileParser} for all sheets of the Excel file
	 * content provided by the given {@link InputStream} {@link Supplier}.
	 *
	 * @param inputStreamSupplier
	 *            a {@link Supplier} of an {@link InputStream} of the content of
	 *            an Excel file to be parsed (never <i>null</i>)
	 * @param fileName
	 *            the name of the Excel file to be parsed (may be <i>null</i>)
	 */
	public ScExcelFileParser(Supplier<InputStream> inputStreamSupplier, String fileName) {

		this(inputStreamSupplier, fileName, new ArrayList<>());
	}

	/**
	 * Creates an {@link ScExcelFileParser} for the given sheets of the Excel
	 * file content provided by the given {@link InputStream} {@link Supplier}.
	 *
	 * @param inputStreamSupplier
	 *            a {@link Supplier} of an {@link InputStream} of the content of
	 *            an Excel file to be parsed (never <i>null</i>)
	 * @param fileName
	 *            the name of the Excel file to be parsed (may be <i>null</i>)
	 * @param sheetIndexes
	 *            the 0-based indexes of the sheets to be parsed (never
	 *            <i>null</i>)
	 */
	public ScExcelFileParser(Supplier<InputStream> inputStreamSupplier, String fileName, List<Integer> sheetIndexes) {

		this.inputStreamSupplier = Objects.requireNonNull(inputStreamSupplier);
		this.fileName = Optional.ofNullable(fileName).orElse(DEFAULT_FILE_NAME);
		this.sheetIndexes = assertValidSheetNumbers(sheetIndexes);
		this.rowsBySheet = null;
	}

	/**
	 * Parses the Excel file to a List of {@link ScExcelRow} instances.
	 * <p>
	 * If a sheet was completely empty, its index won't occur as a key in the
	 * returned map.
	 * <p>
	 * TODO change return type to Map<Integer, List<ScExcelRow>> as soon as we
	 * have test coverage
	 *
	 * @return a map from zero-based sheet index to the corresponding
	 *         {@link List} of {@link ScExcelRow} instances (never <i>null</i>)
	 */
	public ListTreeMap<Integer, ScExcelRow> parse() {

		if (this.rowsBySheet == null) {
			try (//
					var inputStream = inputStreamSupplier.get();
					Workbook workbook = ScExcelFileParserLib.getWorkbookFromInputStream(inputStream)) {

				ListTreeMap<Integer, ScExcelRow> rowsBySheet = new ListTreeMap<>();
				List<Integer> sheetIndexes = ScExcelFileParserLib.getSheetIndexesInternal(workbook, this.sheetIndexes);

				for (int sheetIndex: sheetIndexes) {
					Sheet sheet = workbook.getSheetAt(sheetIndex);
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
