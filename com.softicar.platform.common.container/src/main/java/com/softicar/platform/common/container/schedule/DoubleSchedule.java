package com.softicar.platform.common.container.schedule;

import com.softicar.platform.common.container.matrix.traits.DoubleMatrixTraits;
import java.util.Comparator;
import java.util.Map;

/**
 * A {@link SimpleSchedule} for values of type {@link Double}.
 *
 * @author Oliver Richers
 */
public class DoubleSchedule<R extends Comparable<? super R>, C extends Comparable<? super C>> extends SimpleSchedule<R, C, Double>
		implements IDoubleSchedule<R, C> {

	public DoubleSchedule(C backorderColumn) {

		super(new DoubleMatrixTraits(), backorderColumn, null, null);
	}

	public DoubleSchedule(C backorderColumn, Comparator<R> rowComparator, Comparator<C> colComparator) {

		super(new DoubleMatrixTraits(), backorderColumn, rowComparator, colComparator);
	}

	public void substractValue(R row, double value) {

		Map<C, Double> map = getRowMap(row);
		for (C key: map.keySet()) {
			Double keyValue = map.get(key);
			if (value == 0.0) {
				break;
			}
			if (keyValue == null || keyValue.doubleValue() == 0.0) {
				continue;
			}
			//

			if (keyValue.doubleValue() < 0.0) {
				value += Math.abs(keyValue);
				map.put(key, 0.0);
			} else {
				if (value <= keyValue.doubleValue()) {
					map.put(key, map.get(key) - value);
					value = 0;
				} else {
					value -= map.get(key);
					map.put(key, 0.0);
				}
			}
			//

		}
	}

	public void substractValue(Map<R, Double> keyValueMap) {

		for (Map.Entry<R, Double> entry: keyValueMap.entrySet()) {
			substractValue(entry.getKey(), entry.getValue());
		}
	}
}
