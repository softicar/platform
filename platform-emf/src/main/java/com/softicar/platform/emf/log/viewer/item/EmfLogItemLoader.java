package com.softicar.platform.emf.log.viewer.item;

import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.log.EmfPlainChangeLogger;
import com.softicar.platform.emf.log.IEmfChangeLogger;
import com.softicar.platform.emf.log.viewer.EmfLogStructuralMapper;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import com.softicar.platform.emf.transaction.IEmfTransactionObject;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class EmfLogItemLoader<R extends IEmfTableRow<R, ?>> {

	private R tableRow;
	private EmfLogStructuralMapper<R> mapper;
	private final Map<IEmfTransactionObject<?>, EmfLogItem<R>> logItemMap;

	public EmfLogItemLoader() {

		this.tableRow = null;
		this.mapper = null;
		this.logItemMap = new TreeMap<>();
	}

	public EmfLogItemLoader<R> load(R tableRow) {

		this.tableRow = Objects.requireNonNull(tableRow);
		this.mapper = new EmfLogStructuralMapper<>(tableRow.table());
		this.logItemMap.clear();

		tableRow//
			.getAttributeOwners()
			.forEach(this::addLogItems);

		new EmfLogItemDensifier<>(tableRow, mapper).densify(logItemMap.values());

		return this;
	}

	public R getTableRow() {

		return tableRow;
	}

	public Collection<EmfLogItem<R>> getLogItems() {

		return logItemMap.values();
	}

	public EmfLogItem<R> getLogItem(IEmfTransactionObject<?> transactionObject) {

		return logItemMap.get(transactionObject);
	}

	public Collection<IEmfAttribute<R, ?>> getLoggedAttributes() {

		return logItemMap//
			.values()
			.stream()
			.map(it -> it.getLoggedAttributes())
			.flatMap(Collection::stream)
			.collect(Collectors.toCollection(HashSet::new));
	}

	@SuppressWarnings("unchecked")
	private <F extends IEmfTableRow<F, ?>> void addLogItems(IEmfTableRow<F, ?> attributeOwner) {

		for (IEmfChangeLogger<F> logger: attributeOwner.table().getChangeLoggers()) {
			Optional<EmfPlainChangeLogger<?, F, ?>> plainChangeLogger = mapper.getAsPlainChangeLogger(logger);
			if (plainChangeLogger.isPresent()) {
				addLoggerRecords(plainChangeLogger.get(), (F) attributeOwner);
			}
		}
	}

	private <L, F extends IEmfTableRow<F, ?>> void addLoggerRecords(EmfPlainChangeLogger<L, F, ?> logger, F attributeOwner) {

		logger//
			.getLogRecords(attributeOwner)
			.forEach(logRecord -> addRecord(logger, logRecord));
	}

	private <L> void addRecord(EmfPlainChangeLogger<L, ?, ?> logger, L logRecord) {

		logItemMap//
			.computeIfAbsent(getTransactionObject(logger, logRecord), this::createLogItem)
			.setValues(logger, logRecord);
	}

	private <L> IEmfTransactionObject<?> getTransactionObject(EmfPlainChangeLogger<L, ?, ?> logger, L logRecord) {

		return logger.getTransactionField().getValue(logRecord);
	}

	private EmfLogItem<R> createLogItem(IEmfTransactionObject<?> key) {

		return new EmfLogItem<>(key, createImpermanentEntity(), mapper);
	}

	private R createImpermanentEntity() {

		return tableRow.table().getTableSpecialization().createImpermanentCopy(tableRow);
	}
}
