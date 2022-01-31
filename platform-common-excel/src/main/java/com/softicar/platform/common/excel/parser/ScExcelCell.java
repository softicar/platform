package com.softicar.platform.common.excel.parser;

import com.softicar.platform.common.container.pair.Pair;
import com.softicar.platform.common.core.exceptions.SofticarNotImplementedYetException;
import com.softicar.platform.common.core.number.parser.DoubleParser;
import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.common.date.DayParser;
import com.softicar.platform.common.string.parsing.NumberStringCleaner;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * Provides convenience methods to access the contents of an Excel file cell.
 * <p>
 * Instances are created via {@link ScExcelFileParser}.
 *
 * @author Alexander Schmidt
 */
public class ScExcelCell {

	private final Cell cell;

	ScExcelCell(Cell cell) {

		this.cell = Objects.requireNonNull(cell);
	}

	/**
	 * Returns the coordinates of this cell in its sheet.
	 * <p>
	 * The returned values are row and column indexes. Both are zero-based.
	 *
	 * @return the coordinates of this cell (never <i>null</i>)
	 */
	public Pair<Integer, Integer> getCoordinatesInSheet() {

		return new Pair<>(cell.getRowIndex(), cell.getColumnIndex());
	}

	/**
	 * Determines whether this cell is blank.
	 *
	 * @return <i>true</i> if this cell is blank; <i>false</i> otherwise
	 */
	public boolean isBlank() {

		return isCellTypeBlank();
	}

	/**
	 * Fetches a {@link Date} value from this cell.
	 * <p>
	 * If this cell is date-formatted (according to the cell settings in Excel),
	 * the date is retrieved without any conversion.
	 * <p>
	 * If this cell is formatted differently (i.e. as a generic string cell or
	 * as a non-double numeric cell), a content-to-date conversion is attempted.
	 * <p>
	 * Supported date formats are: <b>ISO</b> ("2020-12-31"), <b>German</b>
	 * ("31.12.2020"), <b>US</b> ("12/31/2020")
	 * <p>
	 * Returns <i>null</i> if no date could be determined.
	 *
	 * @return the content if this cell as {@link Date} (may be <i>null</i>)
	 */
	public Date getDateOrNull() {

		Date result = null;

		if (isCellTypeNumeric()) {
			if (DateUtil.isCellDateFormatted(cell)) {
				result = cell.getDateCellValue();
			} else {
				double numericCellValue = cell.getNumericCellValue();
				String dateString = Double.valueOf(numericCellValue + "").intValue() + "";
				result = getDateOrNullFromString(dateString);
			}
		}

		else if (isCellTypeString()) {
			String dateString = cell.getStringCellValue();
			result = getDateOrNullFromString(dateString);
		}

		else if (isCellTypeFormula()) {
			//TODO
			throw new SofticarNotImplementedYetException();
		}

		return result;
	}

	/**
	 * Fetches an {@link Integer} value from this cell.
	 * <p>
	 * Returns <i>null</i> if no such value could be determined.
	 *
	 * @return the content if this cell as {@link Integer} (may be <i>null</i>)
	 */
	public Integer getIntegerOrNull() {

		return Optional//
			.ofNullable(getDoubleOrNull())
			.map(Double::intValue)
			.orElse(null);
	}

	/**
	 * Fetches a {@link Long} value from this cell.
	 * <p>
	 * Returns <i>null</i> if no such value could be determined.
	 *
	 * @return the content if this cell as {@link Long} (may be <i>null</i>)
	 */
	public Long getLongOrNull() {

		return Optional//
			.ofNullable(getDoubleOrNull())
			.map(Double::longValue)
			.orElse(null);
	}

	/**
	 * Fetches a {@link Double} value from this cell.
	 * <p>
	 * Returns <i>null</i> if no such value could be determined.
	 *
	 * @return the content if this cell as {@link Double} (may be <i>null</i>)
	 */
	public Double getDoubleOrNull() {

		return getNumberOrNull(Function.identity(), Double::parseDouble, () -> 0d);
	}

	/**
	 * Fetches a {@link BigDecimal} value from this cell.
	 * <p>
	 * Returns <i>null</i> if no such value could be determined.
	 *
	 * @return the content if this cell as {@link BigDecimal} (may be
	 *         <i>null</i>)
	 */
	public BigDecimal getBigDecimalOrNull() {

		return getNumberOrNull(BigDecimal::new, BigDecimal::new, () -> BigDecimal.ZERO);
	}

	/**
	 * Fetches a {@link String} value from this cell.
	 * <p>
	 * Returns <i>null</i> if no such value could be determined, or if the
	 * trimmed content of this cell is empty.
	 *
	 * @return the content if this cell as {@link String} (may be <i>null</i>)
	 */
	public String getStringOrNull() {

		return getStringOrNull(false);
	}

	/**
	 * Fetches a {@link String} value from this cell.
	 * <p>
	 * Returns <i>null</i> if no such value could be determined, or if the
	 * content of this cell is empty.
	 * <p>
	 * If <i>true</i> is given, the content of the cell is trimmed after
	 * retrieval. If this leads to an empty {@link String}, <i>null</i> is
	 * returned.
	 *
	 * @param trimResult
	 *            <i>true</i> if the {@link String} content should be trimmed;
	 *            <i>false</i> otherwise
	 * @return the content if this cell as {@link String} (may be <i>null</i>)
	 */
	public String getStringOrNull(boolean trimResult) {

		String value = null;

		if (isCellTypeString()) {
			value = cell.getStringCellValue();
		}

		else if (isCellTypeNumeric()) {
			double cellValue = cell.getNumericCellValue();

			Double cellValueFloored = Math.floor(cellValue);

			if (cellValue == Math.floor(cellValue) && !Double.isInfinite(cellValue)) {
				Long longCellValueFloored = cellValueFloored.longValue();
				value = Long.valueOf(longCellValueFloored) + "";
			} else {
				value = cellValue + "";
			}
		}

		else if (isCellTypeFormula()) {
			try {
				CellValue cellValue = evaluateCell();
				value = cellValue.getStringValue();
			} catch (RuntimeException e) {
				DevNull.swallow(e);
				value = getCellValueFromFormulaCache();
			}
		}

		if (value != null) {
			if (trimResult) {
				value = value.trim();
			}

			if (value.isEmpty()) {
				value = null;
			}
		}

		return value;
	}

	private Date getDateOrNullFromString(String dateString) {

		Date date = null;
		try {
			if (dateString != null) {
				dateString = dateString.trim();
				if (!dateString.isEmpty()) {
					date = new DayParser(dateString).parse().map(Day::toDate).orElse(null);
				}
			}
		} catch (Exception exception) {
			// ignore to eventually return a null value
			DevNull.swallow(exception);
		}
		return date;
	}

	private <T extends Number> T getNumberOrNull(Function<Double, T> doubleConverter, Function<String, T> stringConverter, Supplier<T> zeroValueSupplier) {

		if (isCellTypeNumeric()) {
			return doubleConverter.apply(cell.getNumericCellValue());
		}

		else if (isCellTypeString()) {
			String stringCellValue = cell.getStringCellValue().trim();
			String cleanDoubleString = NumberStringCleaner.convertToCleanNumberString(stringCellValue);

			if (DoubleParser.isDouble(cleanDoubleString)) {
				return stringConverter.apply(cleanDoubleString);
			}
		}

		else if (isCellTypeFormula()) {
			CellValue cellValue = evaluateCell();
			Double numberValue = cellValue.getNumberValue();

			if (!numberValue.equals(0.0)) {
				return doubleConverter.apply(numberValue);
			}

			else {
				String stringValue = cellValue.getStringValue();

				if (stringValue != null) {
					stringValue = NumberStringCleaner.convertToCleanNumberString(stringValue.trim());

					if (DoubleParser.isDouble(stringValue)) {
						return stringConverter.apply(stringValue);
					}
				}

				return zeroValueSupplier.get();
			}
		}

		return CastUtils.cast(null);
	}

	@SuppressWarnings("resource")
	private CellValue evaluateCell() {

		Workbook workbook = cell.getSheet().getWorkbook();
		FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
		CellValue cellValue = evaluator.evaluate(cell);

		return cellValue;
	}

	private String getCellValueFromFormulaCache() {

		switch (cell.getCachedFormulaResultType()) {
		case NUMERIC:
			return cell.getNumericCellValue() + "";
		case STRING:
			return cell.getRichStringCellValue() + "";
		default:
			return null;
		}
	}

	private boolean isCellTypeNumeric() {

		return cell.getCellType() == CellType.NUMERIC;
	}

	private boolean isCellTypeString() {

		return cell.getCellType() == CellType.STRING;
	}

	private boolean isCellTypeFormula() {

		return cell.getCellType() == CellType.FORMULA;
	}

	private boolean isCellTypeBlank() {

		return cell.getCellType() == CellType.BLANK;
	}

}
