package com.softicar.platform.common.container.matrix;

import com.softicar.platform.common.string.Padding;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * A simple table that can be printed out into text, using ASCII characters.
 * <p>
 * This table has a fixed number of columns that are defined on construction.
 * Adding cells to this table fills up the current row and will automatically
 * start the next row, when the preceding row is full.
 * <p>
 * The first row is automatically treated as the headline.
 *
 * @author Oliver Richers
 */
public class AsciiTable {

	private static final String EOL = "\r\n";

	private final List<List<AsciiTableCell>> rows = new ArrayList<>();
	private final Integer colCount;
	private List<AsciiTableCell> currentRow;

	/**
	 * The alignment of a table cell.
	 *
	 * @author Oliver Richers
	 */
	public static enum Alignment {
		ALIGN_LEFT,
		ALIGN_RIGHT,
		ALIGN_CENTER;
	}

	/**
	 * Constructs a new table with the specified number of columns.
	 * <p>
	 * The first row will be treated as headline automatically.
	 *
	 * @param colCount
	 *            the number of table columns
	 */
	public AsciiTable(int colCount) {

		this.colCount = colCount;
	}

	/**
	 * Constructs a new table with the specified column names.
	 * <p>
	 * The specified columns names will be used as column headlines and the
	 * number of the names automatically determines the number of columns in
	 * this table.
	 *
	 * @param columns
	 *            the names of the table columns
	 */
	public AsciiTable(String...columns) {

		this.colCount = columns.length;
		for (String column: columns) {
			add(column, Alignment.ALIGN_CENTER);
		}
	}

	/**
	 * Returns the number of columns in this table.
	 *
	 * @return column count
	 */
	public int getColumntCount() {

		return colCount;
	}

	public Collection<List<AsciiTableCell>> getRows() {

		return Collections.unmodifiableCollection(rows);
	}

	/**
	 * Adds a new cell to this table with automatically determined alignment.
	 * <p>
	 * The specified object will be converted into string, by calling its
	 * <code>toString()</code> method. A null value will be converted into the
	 * string <i>null</i>.
	 * <p>
	 * If the specified object is inserted into the first row of the table,
	 * which is the headline row, the text will be centered. Otherwise, if the
	 * object is an instance of {@link Number}, the text will be aligned to the
	 * right, else to the left. If you need a different alignment strategy, you
	 * can call {@link #add(Object, Alignment)}, instead.
	 *
	 * @param object
	 *            the object to insert as a new table cell
	 * @return this table (for chaining)
	 */
	public AsciiTable add(Object object) {

		if (isInHeadline()) {
			return add(object, Alignment.ALIGN_CENTER);
		} else if (object instanceof Number) {
			return add(object, Alignment.ALIGN_RIGHT);
		} else {
			return add(object, Alignment.ALIGN_LEFT);
		}
	}

	private boolean isInHeadline() {

		return currentRow == null || rows.size() == 1 && currentRow.size() < colCount;
	}

	/**
	 * Adds a new cell to this table with the specified alignment.
	 * <p>
	 * The specified object will be converted into string, by calling its
	 * <code>toString()</code> method. A null value will be converted into the
	 * string <i>null</i>.
	 *
	 * @param object
	 *            the object to insert as a new table cell
	 * @param alignment
	 *            the alignment to use for the cell
	 * @return this table (for chaining)
	 */
	public AsciiTable add(Object object, Alignment alignment) {

		if (isNeedNewRow()) {
			rows.add(currentRow = new ArrayList<>());
		}

		currentRow.add(new AsciiTableCell("" + object, alignment));
		return this;
	}

	private boolean isNeedNewRow() {

		return currentRow == null || currentRow.size() == colCount;
	}

	@Override
	public String toString() {

		int rowCount = rows.size();

		// init widths to zero
		List<Integer> widths = new ArrayList<>();
		for (int c = 0; c != colCount; ++c) {
			widths.add(0);
		}

		// compute widths of columns
		for (int r = 0; r < rowCount; ++r) {
			for (int c = 0; c != colCount; ++c) {
				widths.set(c, Math.max(widths.get(c), rows.get(r).get(c).getWidth()));
			}
		}

		// generate table
		StringBuilder sb = new StringBuilder();
		generateSeparator(sb, widths);
		generateRow(sb, rows.get(0), widths);
		generateSeparator(sb, widths);
		for (int i = 1; i < rowCount; ++i) {
			generateRow(sb, rows.get(i), widths);
		}
		generateSeparator(sb, widths);

		return sb.toString();
	}

	private static void generateSeparator(StringBuilder sb, Collection<Integer> widths) {

		sb.append("+");
		for (Integer width: widths) {
			sb.append(Padding.generate('-', 2 + width));
			sb.append("+");
		}
		sb.append(EOL);
	}

	private static void generateRow(StringBuilder sb, List<AsciiTableCell> cells, List<Integer> widths) {

		sb.append("|");
		for (int i = 0; i != cells.size(); ++i) {
			sb.append(" ");
			cells.get(i).append(sb, widths.get(i));
			sb.append(" |");
		}
		sb.append(EOL);
	}
}
