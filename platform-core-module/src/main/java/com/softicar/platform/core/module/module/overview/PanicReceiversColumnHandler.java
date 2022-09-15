package com.softicar.platform.core.module.module.overview;

import com.softicar.platform.common.container.collection.MappingCollection;
import com.softicar.platform.common.container.map.list.IListMap;
import com.softicar.platform.common.container.map.list.ListTreeMap;
import com.softicar.platform.common.container.tuple.Tuple2;
import com.softicar.platform.core.module.event.panic.AGModulePanicReceiver;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.uuid.AGUuid;
import com.softicar.platform.db.sql.Sql;
import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.emf.data.table.IEmfDataTableCell;
import com.softicar.platform.emf.data.table.column.IEmfDataTableColumn;
import com.softicar.platform.emf.data.table.column.handler.EmfDataTableValueBasedColumnHandler;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

class PanicReceiversColumnHandler extends EmfDataTableValueBasedColumnHandler<UUID> {

	private final IListMap<UUID, AGUser> receiverMap = new ListTreeMap<>();

	@Override
	public void prefetchData(IEmfDataTableColumn<?, UUID> column, Collection<UUID> values) {

		receiverMap.clear();
		for (Tuple2<String, AGUser> tuple: Sql//
			.from(AGModulePanicReceiver.TABLE)
			.where(AGModulePanicReceiver.ACTIVE)
			.join(AGModulePanicReceiver.MODULE_UUID)
			.where(AGUuid.UUID_STRING.isIn(new MappingCollection<>(values, UUID::toString)))
			.select(AGUuid.UUID_STRING)
			.joinLeftOnTable0(AGModulePanicReceiver.USER)
			.select(AGUser.TABLE)) {
			receiverMap.addToList(UUID.fromString(tuple.get0()), tuple.get1());
		}
	}

	@Override
	public void buildCell(IEmfDataTableCell<?, UUID> cell, UUID value) {

		List<AGUser> receivers = receiverMap.getList(value);
		Collections.sort(receivers, (a, b) -> a.getFirstAndLastName().compareTo(b.getFirstAndLastName()));
		for (AGUser receiver: receivers) {
			cell.appendText(receiver.getFirstAndLastName());
			cell.appendNewChild(DomElementTag.BR);
		}
	}
}
