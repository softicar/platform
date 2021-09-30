package com.softicar.platform.emf.matrix;

import com.softicar.platform.common.container.tuple.Tuple2;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.string.unicode.UnicodeEnum;
import com.softicar.platform.dom.element.IDomElement;
import com.softicar.platform.dom.elements.DomCell;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomHeaderCell;
import com.softicar.platform.dom.elements.DomRow;
import com.softicar.platform.dom.elements.message.DomMessageDiv;
import com.softicar.platform.dom.elements.message.style.DomMessageType;
import com.softicar.platform.dom.elements.tables.DomDataTable;
import com.softicar.platform.dom.elements.text.DomVerticalTextBox;
import com.softicar.platform.dom.elements.wiki.DomWikiDivBuilder;
import com.softicar.platform.dom.styles.CssFontStyle;
import com.softicar.platform.dom.styles.CssTextAlign;
import com.softicar.platform.dom.styles.CssVerticalAlign;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.data.table.EmfDataTableI18n;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * The primary, matrix based view of an {@link EmfSettingMatrixDiv}.
 *
 * @author Alexander Schmidt
 */
public class EmfSettingMatrixView<R, C, V> extends DomDiv implements IEmfSettingMatrixView {

	private final IEmfSettingMatrixConfiguration<R, C, V> configuration;
	private final IEmfSettingMatrixModel<R, C, V> model;
	private final IEmfSettingMatrixViewOptions options;
	private final Map<R, Map<C, IEmfSettingMatrixModelEntryInput<V>>> inputMap;
	private final Map<C, IEmfSettingMatrixModelEntryInput<V>> wildcardInputMap;
	private final DomDiv container;

	public EmfSettingMatrixView(IEmfSettingMatrixConfiguration<R, C, V> configuration, IEmfSettingMatrixModel<R, C, V> model,
			IEmfSettingMatrixViewOptions options) {

		this.configuration = configuration;
		this.model = model;
		this.options = options;
		this.inputMap = configuration.getRowStrategy().createMap();
		this.wildcardInputMap = configuration.getColumnStrategy().createMap();
		this.container = appendChild(new DomDiv());
	}

	@Override
	public void applyFromModel() {

		inputMap.clear();
		wildcardInputMap.clear();

		container.removeChildren();
		Collection<R> filteredRows = getFilteredRows();
		Collection<C> filteredColumns = getFilteredColumns();
		Collection<R> displayRows = determineDisplayRows(filteredRows, filteredColumns);
		Collection<C> displayColumns = determineDisplayColumns(displayRows, filteredColumns);
		int hiddenRows = model.getCurrentData().getRows().size() - displayRows.size();
		int hiddenColumns = model.getCurrentData().getColumns().size() - displayColumns.size();
		buildWarningMessageDiv(new WarningProblems(hiddenRows, hiddenColumns)).ifPresent(container::appendChild);
		container.appendChild(createTable(displayRows, displayColumns));
	}

	@Override
	public void applyToModel() {

		IEmfSettingMatrixModelData<R, C, V> modelData = model.getCurrentData();
		for (Entry<R, Map<C, IEmfSettingMatrixModelEntryInput<V>>> entry: inputMap.entrySet()) {
			R row = entry.getKey();
			for (Entry<C, IEmfSettingMatrixModelEntryInput<V>> innerEntry: entry.getValue().entrySet()) {
				C column = innerEntry.getKey();
				Optional<V> value = innerEntry.getValue().getValue();
				modelData.setValue(row, column, value);
			}
		}
	}

	private Table<?, ?> createTable(Collection<R> displayRows, Collection<C> displayColumns) {

		boolean flipped = options.isFlipRowsAndColumns();
		if (flipped) {
			return new Table<>(//
				displayColumns,
				displayRows,
				this::mapFlipped,
				configuration::getColumnDisplayName,
				configuration::getRowDisplayName);
		} else {
			return new Table<>(//
				displayRows,
				displayColumns,
				this::mapRegular,
				configuration::getRowDisplayName,
				configuration::getColumnDisplayName);
		}
	}

	private <A, B> Tuple2<A, B> mapRegular(A aplha, B beta) {

		return new Tuple2<>(aplha, beta);
	}

	private <A, B> Tuple2<B, A> mapFlipped(A alpha, B beta) {

		return new Tuple2<>(beta, alpha);
	}

	private Optional<IDomElement> buildWarningMessageDiv(WarningProblems problems) {

		if (problems.isHiddenRowsOrColumns()) {
			DomWikiDivBuilder builder = new DomWikiDivBuilder();
			builder.addLine(EmfI18n.SOME_SETTINGS_ARE_HIDDEN_DUE_TO_FILTER_SETTINGS_THOSE_WILL_BE_SAVED_ANYWAY);
			return Optional.ofNullable(new DomMessageDiv(DomMessageType.WARNING, builder.build()));
		} else {
			return Optional.empty();
		}
	}

	private Collection<R> getFilteredRows() {

		return getFilteredEntries(//
			model.getCurrentData().getRows(),
			options.getRowFilterString(),
			configuration::getRowDisplayName);
	}

	private Collection<C> getFilteredColumns() {

		return getFilteredEntries(//
			model.getCurrentData().getColumns(),
			options.getColumnFilterString(),
			configuration::getColumnDisplayName);
	}

	private <X> Collection<X> getFilteredEntries(Collection<X> entries, String filterString, Function<X, IDisplayString> displayNamer) {

		return entries//
			.stream()
			.filter(entry -> applyFilter(entry, filterString, displayNamer))
			.collect(Collectors.toSet());
	}

	private <X> boolean applyFilter(X entry, String filterString, Function<X, IDisplayString> displayNamer) {

		return displayNamer//
			.apply(entry)
			.toString()
			.toLowerCase()
			.contains(filterString.toLowerCase());
	}

	private Collection<R> determineDisplayRows(Collection<R> rows, Collection<C> columns) {

		return determineDisplayEntities(//
			rows,
			columns,
			options.isHideRowsWithDefaultValues(),
			this::mapRegular,
			configuration.getRowStrategy()::createSet);
	}

	private Collection<C> determineDisplayColumns(Collection<R> rows, Collection<C> columns) {

		Collection<C> displayColumns = new ArrayList<>();
		displayColumns
			.addAll(
				determineDisplayEntities(//
					columns,
					rows,
					options.isHideColumnsWithDefaultValues(),
					this::mapFlipped,
					configuration.getColumnStrategy()::createSet));
		displayColumns.addAll(determineWildcardDisplayColumns(columns));
		return displayColumns;
	}

	private Collection<C> determineWildcardDisplayColumns(Collection<C> columns) {

		if (configuration.isWildcardRowDisplayed()) {
			return columns//
				.stream()
				.filter(column -> hasNonDefaultValue(getOrCreateWildcardInput(column)))
				.collect(Collectors.toSet());
		} else {
			return Collections.emptySet();
		}
	}

	private <A, B> Collection<A> determineDisplayEntities(Collection<A> alphas, Collection<B> betas, boolean hideDefaultValues,
			BiFunction<A, B, Tuple2<R, C>> mapper, Supplier<Set<A>> setSupplier) {

		Collection<A> displayRows = setSupplier.get();
		for (A alpha: alphas) {
			boolean display = true;
			if (hideDefaultValues) {
				display = false;
				for (B beta: betas) {
					Tuple2<R, C> mapped = mapper.apply(alpha, beta);
					if (hasNonDefaultValue(mapped.get0(), mapped.get1())) {
						display = true;
						break;
					}
				}
			}
			if (display) {
				displayRows.add(alpha);
			}
		}
		return displayRows;
	}

	private boolean hasNonDefaultValue(R row, C column) {

		return getOrCreateInput(row, column).map(this::hasNonDefaultValue).orElse(false);
	}

	private boolean hasNonDefaultValue(IEmfSettingMatrixModelEntryInput<V> input) {

		return input.getValue().isPresent() && !configuration.isDefaultValue(input.getValue().get());
	}

	private IEmfSettingMatrixModelEntryInput<V> getOrCreateWildcardInput(C column) {

		IEmfSettingMatrixModelEntryInput<V> input = wildcardInputMap//
			.computeIfAbsent(column, dummy -> new InputElementFactory().createWildcardInput(column));
		input.disappend();
		return input;
	}

	private Optional<IEmfSettingMatrixModelEntryInput<V>> getOrCreateInput(R row, C column) {

		if (configuration.isInputPossible(row, column)) {
			IEmfSettingMatrixModelEntryInput<V> input = inputMap//
				.computeIfAbsent(row, dummy -> configuration.getColumnStrategy().createMap())
				.computeIfAbsent(column, dummy -> new InputElementFactory().createInput(row, column));
			input.disappend();
			return Optional.of(input);
		} else {
			return Optional.empty();
		}
	}

	private class InputElementFactory {

		public IEmfSettingMatrixModelEntryInput<V> createInput(R row, C column) {

			return initialize(configuration.createInput(() -> getOriginalValueCopy(row, column)));
		}

		public IEmfSettingMatrixModelEntryInput<V> createWildcardInput(C column) {

			return initialize(configuration.createWildcardInput(() -> getOriginalWildcardValueCopy(column)));
		}

		private Optional<V> getOriginalValueCopy(R row, C column) {

			return model.getOriginalData().getValue(row, column).map(configuration::deepCopyValue);
		}

		private Optional<V> getOriginalWildcardValueCopy(C column) {

			return model.getOriginalData().getWildcardValue(column).map(configuration::deepCopyValue);
		}

		private IEmfSettingMatrixModelEntryInput<V> initialize(IEmfSettingMatrixModelEntryInput<V> input) {

			input.setValue(configuration.getDefaultValueSupplier().get());
			input.setInputEnabled(configuration.isEditable());
			return input;
		}
	}

	private class WarningProblems {

		private final int hiddenRows;
		private final int hiddenColumns;

		public WarningProblems(int hiddenRows, int hiddenColumns) {

			this.hiddenRows = hiddenRows;
			this.hiddenColumns = hiddenColumns;
		}

		public boolean isHiddenRowsOrColumns() {

			return hiddenRows > 0 || hiddenColumns > 0;
		}
	}

	private class Table<A, B> extends DomDataTable {

		private final BiFunction<A, B, Tuple2<R, C>> mapper;
		private final Function<A, IDisplayString> alphaDisplayNamer;
		private final Function<B, IDisplayString> betaDisplayNamer;

		public Table(Collection<A> alphas, Collection<B> betas, BiFunction<A, B, Tuple2<R, C>> mapper, Function<A, IDisplayString> alphaDisplayNamer,
				Function<B, IDisplayString> betaDisplayNamer) {

			this.mapper = mapper;
			this.alphaDisplayNamer = alphaDisplayNamer;
			this.betaDisplayNamer = betaDisplayNamer;

			alphas = sort(alphas, alphaDisplayNamer);
			betas = sort(betas, betaDisplayNamer);

			appendHeaderRow(alphas, betas);
			int appendedRowCount = 0;
			appendedRowCount += appendBodyWildcardRowIfNecessary(betas);
			appendedRowCount += appendBodyRows(alphas, betas);
			appendDummyRowIfNecessary(appendedRowCount, betas);
		}

		private <X> Collection<X> sort(Collection<X> collection, Function<X, IDisplayString> displayNamer) {

			return collection//
				.stream()
				.sorted(Comparator.<X, IDisplayString> comparing(displayNamer::apply))
				.collect(Collectors.toList());
		}

		private void appendHeaderRow(Collection<A> alphas, Collection<B> betas) {

			DomRow headerRow = getHead().appendRow();
			headerRow.appendChild(new TopLeftHeaderCell());
			if (isDisplayWildcard(alphas)) {
				BottomAlignedHeaderCell cell = new BottomAlignedHeaderCell(createWildcardLabel());
				cell.setColor(configuration.getWildcardRowDisplayNameColor());
				headerRow.appendChild(cell);
			}
			for (B beta: betas) {
				BottomAlignedHeaderCell headerCell = new BottomAlignedHeaderCell(betaDisplayNamer.apply(beta).toString());
				headerCell.setMarker(EmfSettingMatrixMarker.COLUMN_NAME_CELL);
				headerRow.appendChild(headerCell);
			}
		}

		private int appendBodyWildcardRowIfNecessary(Collection<B> betas) {

			int appended = 0;
			if (configuration.isWildcardRowDisplayed() && !betas.isEmpty() && !options.isFlipRowsAndColumns()) {
				DomRow wildcardRow = new DomRow();
				LeftAlignedHeaderCell cell = wildcardRow.appendChild(new LeftAlignedHeaderCell(createWildcardLabel()));
				cell.setColor(configuration.getWildcardRowDisplayNameColor());
				getBody().appendChild(wildcardRow);
				for (B beta: betas) {
					Tuple2<R, C> mapped = mapper.apply(null, beta);
					Optional<IEmfSettingMatrixModelEntryInput<V>> wildcardInput = Optional.ofNullable(getOrCreateWildcardInput(mapped.get1()));
					if (wildcardInput.isPresent()) {
						wildcardRow.appendChild(new CenteredCell()).appendChild(wildcardInput.get());
						model//
							.getCurrentData()
							.getWildcardValue(mapped.get1())
							.ifPresent(value -> wildcardInput.ifPresent(it -> it.setValue(value)));
					}
				}
				++appended;
			}
			return appended;
		}

		private int appendBodyRows(Collection<A> alphas, Collection<B> betas) {

			int appended = 0;
			if (betas.size() > 0 || configuration.isWildcardRowDisplayed() && options.isFlipRowsAndColumns()) {
				for (A alpha: alphas) {
					DomRow domRow = getBody().appendChild(new DomRow());
					LeftAlignedHeaderCell headerCell = new LeftAlignedHeaderCell(alphaDisplayNamer.apply(alpha).toString());
					headerCell.setMarker(EmfSettingMatrixMarker.ROW_NAME_CELL);
					domRow.appendChild(headerCell);
					if (isDisplayWildcard(alphas)) {
						C column = getColumn(alpha, null);
						DomCell domCell = domRow.appendChild(new CenteredCell());
						Optional<IEmfSettingMatrixModelEntryInput<V>> wildcardInput = Optional.ofNullable(getOrCreateWildcardInput(column));
						wildcardInput.ifPresent(domCell::appendChild);
						model//
							.getCurrentData()
							.getWildcardValue(column)
							.ifPresent(value -> wildcardInput.ifPresent(it -> it.setValue(value)));
					}
					for (B beta: betas) {
						R row = getRow(alpha, beta);
						C column = getColumn(alpha, beta);
						DomCell domCell = domRow.appendChild(new CenteredCell());
						Optional<IEmfSettingMatrixModelEntryInput<V>> input = getOrCreateInput(row, column);
						input.ifPresent(domCell::appendChild);
						input.ifPresent(it -> it.setValue(model.getCurrentData().getValue(row, column)));
					}
					++appended;
				}
			}
			return appended;
		}

		private boolean isDisplayWildcard(Collection<A> alphas) {

			return configuration.isWildcardRowDisplayed() && options.isFlipRowsAndColumns() && !alphas.isEmpty();
		}

		private R getRow(A alpha, B beta) {

			Objects.requireNonNull(beta);
			return mapper.apply(alpha, beta).get0();
		}

		private C getColumn(A alpha, B beta) {

			Objects.requireNonNull(alpha);
			return mapper.apply(alpha, beta).get1();
		}

		private void appendDummyRowIfNecessary(int appendedRowCount, Collection<B> betas) {

			if (appendedRowCount <= 0 || betas.isEmpty()) {
				DomCell cell = getBody().appendChild(new DomRow()).appendCell();
				cell.setMarker(EmfSettingMatrixMarker.DUMMY_CELL);
				cell.setColSpan(betas.size() + 1);
				cell.setStyle(CssFontStyle.ITALIC);
				cell.appendText(createEmptyTableMessage());
			}
		}

		private IDisplayString createEmptyTableMessage() {

			return EmfDataTableI18n.NO_ENTRIES_FOUND.encloseInParentheses();
		}

		private String createWildcardLabel() {

			return "(" + configuration.getWildcardRowDisplayName().toString().toUpperCase() + ")";
		}

		private class TopLeftHeaderCell extends DomHeaderCell {

			public TopLeftHeaderCell() {

				// avoid collapsing
				appendText(UnicodeEnum.NO_BREAK_SPACE.toString());
			}
		}

		private class LeftAlignedHeaderCell extends DomHeaderCell {

			public LeftAlignedHeaderCell(String text) {

				setStyle(CssTextAlign.LEFT);
				appendText(text);
			}
		}

		private class BottomAlignedHeaderCell extends DomHeaderCell {

			public BottomAlignedHeaderCell(String text) {

				setStyle(CssVerticalAlign.BOTTOM);
				DomVerticalTextBox textBox = appendChild(new DomVerticalTextBox());
				textBox.appendText(text);
			}
		}

		private class CenteredCell extends DomCell {

			public CenteredCell() {

				setStyle(CssTextAlign.CENTER);
			}
		}
	}
}
