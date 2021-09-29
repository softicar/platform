package com.softicar.platform.emf.attribute.field.foreign.entity.collection.list.plain;

import com.softicar.platform.db.runtime.logic.IDbObject;
import com.softicar.platform.db.runtime.test.AbstractDbTest;
import com.softicar.platform.emf.test.user.EmfTestUser;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Test;

public class EmfForeignEntityIdPlainListParserTest extends AbstractDbTest {

	private final EmfTestUser userA;
	private final EmfTestUser userB;

	public EmfForeignEntityIdPlainListParserTest() {

		userA = EmfTestUser.insert("user", "A");
		userB = EmfTestUser.insert("user", "B");
	}

	@Test
	public void testParseToIdsForward() {

		String idString = createIdString(userA, userB);

		List<Integer> idList = new ArrayList<>();
		new EmfForeignEntityIdPlainListParser(idString).parseToIds(idList::add);

		assertEquals(2, idList.size());
		assertEquals(userA.getId(), idList.get(0));
		assertEquals(userB.getId(), idList.get(1));
	}

	@Test
	public void testParseToIdsReverse() {

		String idString = createIdString(userB, userA);

		List<Integer> idList = new ArrayList<>();
		new EmfForeignEntityIdPlainListParser(idString).parseToIds(idList::add);

		assertEquals(2, idList.size());
		assertEquals(userB.getId(), idList.get(0));
		assertEquals(userA.getId(), idList.get(1));
	}

	@Test
	public void testParseToIdListForward() {

		String idString = createIdString(userA, userB);

		List<Integer> idList = new EmfForeignEntityIdPlainListParser(idString).parseToIdList();

		assertEquals(2, idList.size());
		assertEquals(userA.getId(), idList.get(0));
		assertEquals(userB.getId(), idList.get(1));
	}

	@Test
	public void testParseToIdListReverse() {

		String idString = createIdString(userB, userA);

		List<Integer> idList = new EmfForeignEntityIdPlainListParser(idString).parseToIdList();

		assertEquals(2, idList.size());
		assertEquals(userB.getId(), idList.get(0));
		assertEquals(userA.getId(), idList.get(1));
	}

	@Test
	public void testParseToEntityListForward() {

		String idString = createIdString(userA, userB);

		List<EmfTestUser> entityList = new EmfForeignEntityIdPlainListParser(idString).parseToEntityList(EmfTestUser.TABLE);

		assertEquals(2, entityList.size());
		assertEquals(userA, entityList.get(0));
		assertEquals(userB, entityList.get(1));
	}

	@Test
	public void testParseToEntityListReverse() {

		String idString = createIdString(userB, userA);

		List<EmfTestUser> entityList = new EmfForeignEntityIdPlainListParser(idString).parseToEntityList(EmfTestUser.TABLE);

		assertEquals(2, entityList.size());
		assertEquals(userB, entityList.get(0));
		assertEquals(userA, entityList.get(1));
	}

	private String createIdString(IDbObject<?>...dbObjects) {

		return Arrays.asList(dbObjects).stream().map(it -> it.getId() + "").collect(Collectors.joining(","));
	}
}
