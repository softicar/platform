package com.softicar.platform.emf.data.table.export.chunking;

import com.softicar.platform.common.container.pair.Pair;
import java.util.Iterator;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class TableExportChunkBoundaryCalculatorTest extends Assert {

	@Test
	public void test() {

		assertNull(TableExportChunkBoundaryCalculator.calculateChunkBoundaries(-1, 1000));

		assertNull(TableExportChunkBoundaryCalculator.calculateChunkBoundaries(0, 1000));

		List<Pair<Integer, Integer>> chunkBoundaries1 = TableExportChunkBoundaryCalculator.calculateChunkBoundaries(1, 1000);
		assertEquals(1, chunkBoundaries1.size());
		Pair<Integer, Integer> solePair1 = chunkBoundaries1.iterator().next();
		assertEquals(0, (int) solePair1.getFirst());
		assertEquals(0, (int) solePair1.getSecond());

		List<Pair<Integer, Integer>> chunkBoundaries2 = TableExportChunkBoundaryCalculator.calculateChunkBoundaries(2, 1000);
		assertEquals(1, chunkBoundaries2.size());
		Pair<Integer, Integer> solePair2 = chunkBoundaries2.iterator().next();
		assertEquals(0, (int) solePair2.getFirst());
		assertEquals(1, (int) solePair2.getSecond());

		List<Pair<Integer, Integer>> chunkBoundaries3 = TableExportChunkBoundaryCalculator.calculateChunkBoundaries(345, 1000);
		assertEquals(1, chunkBoundaries3.size());
		Pair<Integer, Integer> solePair3 = chunkBoundaries3.iterator().next();
		assertEquals(0, (int) solePair3.getFirst());
		assertEquals(344, (int) solePair3.getSecond());

		List<Pair<Integer, Integer>> chunkBoundaries4 = TableExportChunkBoundaryCalculator.calculateChunkBoundaries(999, 1000);
		assertEquals(1, chunkBoundaries4.size());
		Pair<Integer, Integer> solePair4 = chunkBoundaries4.iterator().next();
		assertEquals(0, (int) solePair4.getFirst());
		assertEquals(998, (int) solePair4.getSecond());

		List<Pair<Integer, Integer>> chunkBoundaries5 = TableExportChunkBoundaryCalculator.calculateChunkBoundaries(1000, 1000);
		assertEquals(1, chunkBoundaries5.size());
		Pair<Integer, Integer> solePair5 = chunkBoundaries5.iterator().next();
		assertEquals(0, (int) solePair5.getFirst());
		assertEquals(999, (int) solePair5.getSecond());

		List<Pair<Integer, Integer>> chunkBoundaries6 = TableExportChunkBoundaryCalculator.calculateChunkBoundaries(1001, 1000);
		assertEquals(2, chunkBoundaries6.size());
		Iterator<Pair<Integer, Integer>> it6 = chunkBoundaries6.iterator();
		Pair<Integer, Integer> firstPair6 = it6.next();
		assertEquals(0, (int) firstPair6.getFirst());
		assertEquals(999, (int) firstPair6.getSecond());
		Pair<Integer, Integer> secondPair6 = it6.next();
		assertEquals(1000, (int) secondPair6.getFirst());
		assertEquals(1000, (int) secondPair6.getSecond());

		List<Pair<Integer, Integer>> chunkBoundaries7 = TableExportChunkBoundaryCalculator.calculateChunkBoundaries(2345, 1000);
		assertEquals(3, chunkBoundaries7.size());
		Iterator<Pair<Integer, Integer>> it7 = chunkBoundaries7.iterator();
		Pair<Integer, Integer> pair1 = it7.next();
		assertEquals(0, (int) pair1.getFirst());
		assertEquals(999, (int) pair1.getSecond());
		Pair<Integer, Integer> pair2 = it7.next();
		assertEquals(1000, (int) pair2.getFirst());
		assertEquals(1999, (int) pair2.getSecond());
		Pair<Integer, Integer> pair3 = it7.next();
		assertEquals(2000, (int) pair3.getFirst());
		assertEquals(2344, (int) pair3.getSecond());
	}
}
