package com.softicar.platform.emf.data.table.export.chunking;

import com.softicar.platform.common.container.pair.Pair;
import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import java.util.ArrayList;
import java.util.List;

public class TableExportChunkBoundaryCalculator {

	/**
	 * Creates Pairs of lower and upper indexes. The first Pair has a lower
	 * (first) index of 1 while the last Pair has an upper (second) index equal
	 * to the given totalCount. The maximum difference between the upper and
	 * lower index of a Pair is the given chunkSize minus one.
	 * <p>
	 * E.g. totalCount=13 and chunkSize=3 will lead to the following result:
	 * 
	 * <pre>
	 * (1, 3)
	 * (4, 6)
	 * (7, 9)
	 * (10, 12)
	 * (13, 13)
	 * </pre>
	 * 
	 * @param totalCount
	 * @param chunkSize
	 * @return A List of pairs of indexes, considering the given chunk size.
	 */
	public static List<Pair<Integer, Integer>> calculateChunkBoundaries(int totalCount, int chunkSize) {

		if (chunkSize > 0) {
			if (totalCount > 0) {
				List<Pair<Integer, Integer>> chunkBoundaries = new ArrayList<>();

				for (int i = 0; i < totalCount; i += chunkSize) {
					int nextStart = Math.min(i + chunkSize, totalCount);
					chunkBoundaries.add(new Pair<>(i, nextStart - 1));
				}

				return chunkBoundaries;
			}

			else {
				return null;
			}
		}

		else {
			throw new SofticarDeveloperException("Chunk sizes must be positive.");
		}
	}
}
