package com.softicar.platform.common.code.java;

import com.softicar.platform.common.core.exceptions.SofticarIOException;
import com.softicar.platform.common.string.formatting.Formatting;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * This class simplifies the generation of source code.
 * <p>
 * Automated insertion of indentation characters is provided, based on code
 * block management. For instance, a code block may be a class body, a method
 * body or a conditional code block of an if statement.
 * 
 * @author Oliver Richers
 */
public class CodePrinter {

	private final LinkedList<String> codeLines = new LinkedList<>();
	private int indentationLevel = 0;

	// -------------------- PRINTING -------------------- //

	/**
	 * Append the given text as a new line.
	 * <p>
	 * The correct indentation is added automatically.
	 * 
	 * @param text
	 *            the text to append
	 * @param args
	 *            the formatting parameters
	 */
	public void println(String text, Object...args) {

		// create indentation
		StringBuilder line = new StringBuilder();
		for (int i = 0; i < indentationLevel; ++i) {
			line.append("\t");
		}

		// append line
		line.append(Formatting.format(text, args));
		codeLines.addLast(line.toString());
	}

	/**
	 * Appends an empty line.
	 */
	public void println() {

		codeLines.addLast("");
	}

	/**
	 * Appends the specified line without modifications.
	 * <p>
	 * The given line is added as is without any additional indentation.
	 * 
	 * @param line
	 *            the line to append
	 */
	public void printlnUnformated(String line) {

		codeLines.addLast(line);
	}

	/**
	 * Appends the given line with an additional indentation.
	 * 
	 * @param text
	 *            the text to append
	 * @param args
	 *            the formatting parameters
	 */
	public void printlnIndented(String text, Object...args) {

		++indentationLevel;
		println(text, args);
		--indentationLevel;
	}

	/**
	 * Appends the given line with additional indentation.
	 * 
	 * @param n
	 *            the level of extra indentation
	 * @param text
	 *            the text to append
	 * @param args
	 *            the formatting parameters
	 */
	public void printlnIndented(int n, String text, Object...args) {

		indentationLevel += n;
		println(text, args);
		indentationLevel -= n;
	}

	// -------------------- PREPENDING -------------------- //

	/**
	 * Prepends the specified lines at the beginning of the source code.
	 * 
	 * @param lines
	 *            the lines to prepend
	 */
	public void prependLines(Collection<String> lines) {

		this.codeLines.addAll(0, lines);
	}

	// -------------------- NON-MUTATING -------------------- //

	/**
	 * Prints out all lines of code to the specified {@link Appendable} object.
	 * 
	 * @param appendable
	 *            the object to use as output
	 */
	public void writeOut(Appendable appendable) {

		try {
			for (String line: codeLines) {
				appendable.append(line).append("\n");
			}
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}

	/**
	 * Returns all printed lines.
	 * 
	 * @return list of printed lines
	 */
	public List<String> getCodeLines() {

		return codeLines;
	}

	// -------------------- BLOCK -------------------- //

	/**
	 * Starts a new code block and increases indentation level by one.
	 * <p>
	 * The given text is automatically trimmed and a space plus opening brace is
	 * added at the end.
	 * 
	 * @param text
	 *            the text to introduce the block (e.g. the method head)
	 * @param args
	 *            the formatting parameters
	 */
	public void beginBlock(String text, Object...args) {

		println("%s {", Formatting.format(text, args).trim());
		++indentationLevel;
	}

	/**
	 * Starts a new block without an introductory text.
	 */
	public void beginBlock() {

		println("{");
		++indentationLevel;
	}

	/**
	 * Closes the current block and decreases indentation level by one.
	 * <p>
	 * A preceding empty line is removed automatically, a line with a closing
	 * brace is appended and the indentation level is reduced by one. Optionally
	 * an empty line is appended after the block if requested.
	 * 
	 * @param addEmptyLine
	 *            true to add an additional empty line after the closing brace
	 */
	public void endBlock(boolean addEmptyLine) {

		// remove empty line before end of block
		if (codeLines.size() > 0 && codeLines.getLast().length() == 0) {
			codeLines.removeLast();
		}

		--indentationLevel;
		println("}");
		if (addEmptyLine) {
			println();
		}
	}

	/**
	 * Closes the current block and decreases indentation level by one.
	 * <p>
	 * This method is equivalent to {@link #endBlock(boolean)} with a parameter
	 * of <i>true</i>,
	 */
	public void endBlock() {

		endBlock(true);
	}

	// -------------------- INDENTATION -------------------- //

	public void incrementIndentation() {

		incrementIndentation(1);
	}

	public void incrementIndentation(int n) {

		indentationLevel += n;
	}

	public void decrementIndentation() {

		decrementIndentation(1);
	}

	public void decrementIndentation(int n) {

		indentationLevel -= n;
	}
}
