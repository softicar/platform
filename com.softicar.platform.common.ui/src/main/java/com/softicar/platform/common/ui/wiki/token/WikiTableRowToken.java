package com.softicar.platform.common.ui.wiki.token;

import java.util.ArrayList;
import java.util.Collection;

public class WikiTableRowToken extends WikiToken {

	private final Collection<Cell> cells;

	public WikiTableRowToken(String text) {

		super(WikiTokenType.TABLE_ROW, text);

		this.cells = new CellsParser(text).parse();
	}

	public Collection<Cell> getCells() {

		return cells;
	}

	public static class Cell {

		private final String text;
		private final boolean header;

		public Cell(String text, boolean header) {

			this.text = text;
			this.header = header;
		}

		public String getText() {

			return text;
		}

		public boolean isHeader() {

			return header;
		}
	}

	private class CellsParser {

		private final String text;
		private final Collection<Cell> parsedCells;
		private StringBuilder builder;
		private boolean header;

		public CellsParser(String text) {

			this.text = text.trim();
			this.parsedCells = new ArrayList<>();
		}

		public Collection<Cell> parse() {

			this.header = false;
			this.builder = new StringBuilder();
			for (int i = 0; i < text.length(); i++) {
				char character = text.charAt(i);
				if (character == '^' || character == '|') {
					finishCell();
					header = character == '^';
				} else {
					builder.append(character);
				}
			}
			finishCell();
			return parsedCells;
		}

		private void finishCell() {

			if (builder.length() > 0) {
				parsedCells.add(new Cell(builder.toString().trim(), header));
				builder = new StringBuilder();
			}
		}
	}
}
