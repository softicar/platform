package com.softicar.platform.emf.log.viewer.feed;

import com.softicar.platform.emf.table.row.IEmfTableRow;

enum EmfLogFeedItemType {

	ADDITION("++"),
	REMOVAL("--"),
	UPDATE("<>");

	private final String badgeString;

	private EmfLogFeedItemType(String badgeString) {

		this.badgeString = badgeString;
	}

	public String getBadgeString() {

		return badgeString;
	}

	public <R extends IEmfTableRow<R, ?>> R getRelevantEntity(R oldEntity, R newEntity) {

		return this == REMOVAL? oldEntity : newEntity;
	}
}
