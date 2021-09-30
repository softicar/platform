package com.softicar.platform.emf.log.viewer.item;

import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.log.viewer.EmfLogAttributeFilter;
import com.softicar.platform.emf.log.viewer.EmfLogStructuralMapper;
import com.softicar.platform.emf.sub.object.IEmfSubObject;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Makes {@link EmfLogItem}s "dense" (as in "dense matrix"), by
 * supplementing non-logged base entity attribute values from previous
 * transactions. Can be omitted if E is not an {@link IEmfSubObject}.
 * <p>
 * Suppose:
 * <ul>
 * <li>There is an entity E with a base entity B</li>
 * <li>There are two subsequent transactions, T1 and T2</li>
 * <li>T1 alters fields of E and B</li>
 * <li>T2 only alter fields of E (and not of B)</li>
 * </ul>
 * Hence:
 * <ul>
 * <li>For T1, there is a log record for E (EL1) and B (BL1)</li>
 * <li>For T2, there is only a log record for E (EL2), and no log record for
 * B</li>
 * </ul>
 * Now, we try to display what happened in T2. If we fail to supplement the
 * values of BL1 to the {@link EmfLogItem} of T2, it would wrongly appear
 * that the attribute values of the base entity were removed in T2 - while in
 * fact those attribute values were simply not touched in T2, and therefore not
 * logged (so there is no BL2 record).
 *
 * @author Alexander Schmidt
 */
public class EmfLogItemDensifier<R extends IEmfTableRow<R, ?>> {

	private final R tableRow;
	private final EmfLogStructuralMapper<R> mapper;

	public EmfLogItemDensifier(R tableRow, EmfLogStructuralMapper<R> mapper) {

		this.tableRow = tableRow;
		this.mapper = mapper;
	}

	/**
	 * Densifies the given log items. Has a side effect on them.
	 *
	 * @param logItems
	 *            the log items to densify (never null)
	 */
	public void densify(Collection<EmfLogItem<R>> logItems) {

		Collection<IEmfAttribute<R, ?>> allLoggerAttributes = mapper.getAllLoggerAttributes();
		Map<IEmfAttribute<R, ?>, Object> lastValueMap = new HashMap<>();
		for (EmfLogItem<R> logItem: logItems) {
			Collection<IEmfAttribute<R, ?>> loggedAttributes = logItem.getLoggedAttributes();
			for (IEmfAttribute<R, ?> attribute: getDisplayableAttributes()) {
				if (allLoggerAttributes.contains(attribute)) {
					if (loggedAttributes.contains(attribute)) {
						lastValueMap.put(attribute, logItem.getValue(attribute));
					} else {
						logItem.setValue(attribute, CastUtils.cast(lastValueMap.get(attribute)));
					}
				}
			}
		}
	}

	private Collection<IEmfAttribute<R, ?>> getDisplayableAttributes() {

		return new EmfLogAttributeFilter<>(tableRow).getDisplayableAttributes();
	}
}
