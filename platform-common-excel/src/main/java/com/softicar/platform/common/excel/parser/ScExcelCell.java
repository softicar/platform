package com.softicar.platform.common.excel.parser;

import com.softicar.platform.common.container.pair.Pair;
import com.softicar.platform.common.core.exceptions.SofticarNotImplementedYetException;
import com.softicar.platform.common.core.number.parser.DoubleParser;
import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.common.date.DateFormat;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.common.string.parsing.NumberStringCleaner;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * Provides convenience methods to access the contents of a POI {@link Cell}
 * instance which represents a cell of an Excel file.
 *
 * @author Alexander Schmidt
 */
public class ScExcelCell {

	private final Cell poiCell;

	/**
	 * @param cell
	 */
	public ScExcelCell(Cell cell) {

		this.poiCell = cell;
	}

	/**
	 * @return The coordinates (0-based row and column indexes, in that order)
	 *         of the cell within its sheet.
	 */
	public Pair<Integer, Integer> getCoordinatesInSheet() {

		return new Pair<>(poiCell.getRowIndex(), poiCell.getColumnIndex());
	}

	/**
	 * @return Boolean, whether or not the cell is empty/blank.
	 */
	public boolean isBlank() {

		return getPoiCellType() == CellType.BLANK;
	}

	/**
	 * @see #getDateOrNull(List)
	 */
	public Date getDateOrNull(DateFormat...fallbackDateFormats) {

		if (fallbackDateFormats != null) {
			return getDateOrNull(Arrays.asList(fallbackDateFormats));
		} else {
			return null;
		}
	}

	/**
	 * Fetches a Date object from this cell. The given {@link DateFormat}s are
	 * tried in the specified order in case this cell is not formatted as an
	 * Excel date cell. Defaults to null if no date could be determined for
	 * whatever reason.
	 * <p>
	 * More precisely, in case the cell is not a real Excel date cell but a
	 * string or non-double numeric cell containing a date in some arbitrary
	 * format, the given {@link DateFormat}s are applied in the given order to
	 * the string content, trying to derive a valid date from it.
	 *
	 * @param fallbackDateFormats
	 * @return This cell's content as a Date, or null.
	 */
	public Date getDateOrNull(List<DateFormat> fallbackDateFormats) {

		Date result = null;

		if (getPoiCellType() == CellType.NUMERIC) {
			if (DateUtil.isCellDateFormatted(poiCell)) {
				result = poiCell.getDateCellValue();
			} else {
				double numericCellValue = poiCell.getNumericCellValue();
				String dateString = Double.valueOf(numericCellValue + "").intValue() + "";
				result = getDateOrNullFromString(dateString, fallbackDateFormats);
			}
		}

		else if (getPoiCellType() == CellType.STRING) {
			String dateString = poiCell.getStringCellValue();
			result = getDateOrNullFromString(dateString, fallbackDateFormats);
		}

		else if (getPoiCellType() == CellType.FORMULA) {
			//TODO
			throw new SofticarNotImplementedYetException();
		}

		return result;
	}

	/**
	 * Fetches an Integer value from this cell. Defaults to null if no such
	 * value could be determined for whatever reason.
	 * <p>
	 * Wraps {@link #getDoubleOrNull()}, truncating (i.e. flooring) its result.
	 *
	 * @return This cell's content as an Integer, or null.
	 */
	public Integer getIntegerOrNull() {

		Integer value = null;

		Double doubleOrNull = getDoubleOrNull();
		if (doubleOrNull != null) {
			value = doubleOrNull.intValue();
		}

		return value;
	}

	/**
	 * Fetches a Long value from this cell. Defaults to null if no such value
	 * could be determined for whatever reason.
	 * <p>
	 * Wraps {@link #getDoubleOrNull()}, truncating (i.e. flooring) its result.
	 *
	 * @return This cell's content as a Long, or null.
	 */
	public Long getLongOrNull() {

		Long value = null;

		Double doubleOrNull = getDoubleOrNull();
		if (doubleOrNull != null) {
			value = doubleOrNull.longValue();
		}

		return value;
	}

	/**
	 * Fetches a Double value from this cell. Defaults to null if no such value
	 * could be determined for whatever reason.
	 *
	 * @return This cell's content as a Double, or null.
	 */
	public Double getDoubleOrNull() {

		return getNumberOrNull(this, Function.identity(), it -> Double.parseDouble(it), () -> 0d);
	}

	public BigDecimal getBigDecimalOrNull() {

		return getNumberOrNull(this, it -> new BigDecimal(it), it -> new BigDecimal(it), () -> BigDecimal.ZERO);
	}

	private static <T extends Number> T getNumberOrNull(ScExcelCell cell, Function<Double, T> doubleConverter, Function<String, T> stringConverter,
			Supplier<T> zeroValueSupplier) {

		if (cell.getPoiCellType() == CellType.NUMERIC) {
			return doubleConverter.apply(cell.poiCell.getNumericCellValue());
		}

		else if (cell.getPoiCellType() == CellType.STRING) {
			String stringCellValue = cell.poiCell.getStringCellValue().trim();
			String cleanDoubleString = NumberStringCleaner.convertToCleanNumberString(stringCellValue);

			if (DoubleParser.isDouble(cleanDoubleString)) {
				return stringConverter.apply(cleanDoubleString);
			}
		}

		else if (cell.getPoiCellType() == CellType.FORMULA) {
			CellValue cellValue = cell.evaluateCell();
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

	/**
	 * Fetches a String value from this cell. Defaults to null if the cell is
	 * empty after trimming, or if no such value could be determined for
	 * whatever reason.
	 *
	 * @return This cell's content as a String, or null.
	 */
	public String getStringOrNull() {

		return getStringOrNull(false);
	}

	/**
	 * Fetches a String value from this cell. Defaults to null if the cell is
	 * empty after (optional) trimming, or if no such value could be determined
	 * for whatever reason.
	 *
	 * @param trimResult
	 *            Whether or not the resulting String should be trimmed.
	 * @return This cell's content as a String, or null.
	 */
	public String getStringOrNull(boolean trimResult) {

		String value = null;

		if (getPoiCellType() == CellType.STRING) {
			value = poiCell.getStringCellValue();
		}

		else if (getPoiCellType() == CellType.NUMERIC) {
			double cellValue = poiCell.getNumericCellValue();

			Double cellValueFloored = Math.floor(cellValue);

			if (cellValue == Math.floor(cellValue) && !Double.isInfinite(cellValue)) {
				Long longCellValueFloored = cellValueFloored.longValue();
				value = Long.valueOf(longCellValueFloored) + "";
			} else {
				value = cellValue + "";
			}
		}

		else if (getPoiCellType() == CellType.FORMULA) {
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

	private Date getDateOrNullFromString(String dateString, List<DateFormat> fallbackDateFormats) {

		Date date = null;

		if (fallbackDateFormats != null) {
			for (DateFormat dateFormat: fallbackDateFormats) {
				date = getDateOrNullFromString(dateString, dateFormat);

				if (date != null) {
					break;
				}
			}
		}

		return date;
	}

	private Date getDateOrNullFromString(String dateString, DateFormat dateFormat) {

		Date date = null;

		try {
			if (dateString != null) {
				dateString = dateString.trim();

				if (!dateString.isEmpty()) {
					date = Day.parse(dateFormat, dateString).toDate();
				}
			}
		} catch (Exception e) {
			DevNull.swallow(e);
			//ignore to eventually return a null value
		}

		return date;
	}

	public CellType getPoiCellType() {

		return poiCell.getCellType();
	}

	public Cell getPoiCell() {

		return poiCell;
	}

	@SuppressWarnings("resource")
	private CellValue evaluateCell() {

		Workbook workbook = poiCell.getSheet().getWorkbook();
		FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
		CellValue cellValue = evaluator.evaluate(poiCell);

		return cellValue;
	}

	private String getCellValueFromFormulaCache() {

		switch (poiCell.getCachedFormulaResultType()) {
		case NUMERIC:
			return poiCell.getNumericCellValue() + "";
		case STRING:
			return poiCell.getRichStringCellValue() + "";
		default:
			return null;
		}
	}
}
