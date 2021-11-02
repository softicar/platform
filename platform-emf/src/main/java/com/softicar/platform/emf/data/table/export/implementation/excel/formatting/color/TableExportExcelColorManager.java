package com.softicar.platform.emf.data.table.export.implementation.excel.formatting.color;

import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.common.ui.color.HslColor;
import com.softicar.platform.common.ui.color.IColor;
import com.softicar.platform.common.ui.color.RgbColor;
import com.softicar.platform.dom.style.CssColorEnum;
import java.util.Map;
import java.util.TreeMap;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.DefaultIndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TableExportExcelColorManager {

	private static final int HSSF_COLOR_INDEX_OFFSET = 8;
	private static final int HSSF_COLOR_INDEX_MAX = 64;

	private final Workbook workbook;
	private final Map<String, Short> map = new TreeMap<>();

	private static final IColor DEFAULT_COLOR = CssColorEnum.BLACK;

	// HSSF specific
	private HSSFPalette hssfPalette = null;

	// ----

	public TableExportExcelColorManager(Workbook workbook) {

		this.workbook = workbook;
	}

	public void applyFontColorToFont(Font targetFont, IColor htmlColor) {

		if (this.workbook instanceof HSSFWorkbook) {
			Short colorIndex = hssfGetColorIndex(htmlColor);

			if (colorIndex != null) {
				targetFont.setColor(colorIndex);
			}
		}

		else if (this.workbook instanceof XSSFWorkbook || this.workbook instanceof SXSSFWorkbook) {

			if (targetFont instanceof XSSFFont) {
				XSSFFont xssfFont = (XSSFFont) targetFont;
				XSSFColor xssfColor = xssfGetColor(htmlColor);
				xssfFont.setColor(xssfColor);
			}
		}

		else {
			Log.finfo("WARNING: Don't know how to apply font colors for workbook type: \"%s\"", this.workbook.getClass().getSimpleName());
		}
	}

	/**
	 * Applies the given {@link IColor} to the given target {@link CellStyle}.
	 * <p>
	 * WARNING: Has a side effect on the given target {@link CellStyle}.
	 *
	 * @param targetCellStyle
	 * @param htmlColor
	 */
	public void applyBackgroundColorToCellStyle(CellStyle targetCellStyle, IColor htmlColor) {

		if (this.workbook instanceof HSSFWorkbook) {
			Short colorIndex = hssfGetColorIndex(htmlColor);

			if (colorIndex != null) {
				targetCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				targetCellStyle.setFillForegroundColor(colorIndex);
			}
		}

		else if (this.workbook instanceof XSSFWorkbook || this.workbook instanceof SXSSFWorkbook) {

			XSSFCellStyle targetXssfCellStyle = (XSSFCellStyle) targetCellStyle;
			targetXssfCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			targetXssfCellStyle.setFillForegroundColor(xssfGetColor(htmlColor));
		}

		else {
			Log.finfo("WARNING: Don't know how to background apply colors for workbook type: \"%s\"", this.workbook.getClass().getSimpleName());
		}
	}

	/**
	 * Gets a HSSF color index corresponding to the given {@link IColor}.
	 * Generates the corresponding indexed color in a custom palette, if
	 * necessary.
	 *
	 * @param htmlColor
	 * @return The HSSF palette index of the given color.
	 */
	private Short hssfGetColorIndex(IColor htmlColor) {

		if (htmlColor == null) {
			htmlColor = DEFAULT_COLOR;
		}

		if (htmlColor != null) {
			String colorString = htmlColor.toHtml();
			Short colorIndex = this.map.get(colorString);

			if (colorIndex == null) {
				colorIndex = hssfGenerateColorIndex(htmlColor);

				if (colorIndex != null) {
					this.map.put(colorString, colorIndex);
				}
			}

			return colorIndex;
		}

		else {
			return null;
		}
	}

	/**
	 * Creates an indexed HSSF palette color from the given {@link IColor}.
	 * <p>
	 * Creates a custom HSSF palette upon first call. Hence, overwrites default
	 * palette colors.
	 * <p>
	 * FIXME: Will this approach eventually override the default text color?
	 *
	 * @param htmlColor
	 * @return The HSSF palette index of the given color.
	 */
	private Short hssfGenerateColorIndex(IColor htmlColor) {

		if (htmlColor instanceof HslColor) {
			htmlColor = ((HslColor) htmlColor).getRgbColor();
		}

		String colorString = htmlColor.toHtml();
		RgbColor color = RgbColor.parseHtmlCode(colorString);

		Short colorIndex = null;

		if (this.hssfPalette == null) {
			this.hssfPalette = ((HSSFWorkbook) workbook).getCustomPalette();
		}

		int targetIndex = this.map.size() + HSSF_COLOR_INDEX_OFFSET;

		if (targetIndex <= HSSF_COLOR_INDEX_MAX) {
			this.hssfPalette
				.setColorAtIndex(
					(byte) targetIndex,
					intComponentToByte(color.getRed()),
					intComponentToByte(color.getGreen()),
					intComponentToByte(color.getBlue()));
			colorIndex = this.hssfPalette.getColor(targetIndex).getIndex();
		}

		else {
			Log.finfo("WARNING: Cannot specify more than %s colors in HSSF format.", HSSF_COLOR_INDEX_MAX - HSSF_COLOR_INDEX_OFFSET);
		}

		if (colorIndex == null) {
			Log.finfo("WARNING: Index for color \"%s\" could not be generated.", colorString);
		}

		return colorIndex;
	}

	/**
	 * Creates an XSSF color from the given {@link IColor}.
	 *
	 * @param color
	 * @return The XSSF equivalent of the given color.
	 */
	private XSSFColor xssfGetColor(IColor color) {

		return new XSSFColor(getColorAsComponentBytes(color), new DefaultIndexedColorMap());
	}

	private byte[] getColorAsComponentBytes(IColor color) {

		if (color == null) {
			color = DEFAULT_COLOR;
		}

		RgbColor rgbColor = RgbColor.parseHtmlCode(color.toHtml());

		return new byte[] {//
				intComponentToByte(rgbColor.getRed()),
				intComponentToByte(rgbColor.getGreen()),
				intComponentToByte(rgbColor.getBlue()) };
	}

	private static byte intComponentToByte(int component) {

		return Integer.valueOf(component).byteValue();
	}
}
