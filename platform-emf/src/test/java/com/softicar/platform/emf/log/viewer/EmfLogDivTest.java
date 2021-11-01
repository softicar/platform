package com.softicar.platform.emf.log.viewer;

import com.softicar.platform.common.date.Day;
import com.softicar.platform.db.runtime.test.utils.DbTestTableRowInserter;
import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.elements.DomRow;
import com.softicar.platform.dom.elements.DomTBody;
import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.dom.elements.testing.node.tester.DomNodeTester;
import com.softicar.platform.emf.AbstractEmfTest;
import com.softicar.platform.emf.data.table.EmfDataTableI18n;
import com.softicar.platform.emf.data.table.IEmfDataTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import com.softicar.platform.emf.test.EmfTestSubObject;
import com.softicar.platform.emf.test.simple.EmfTestObject;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for {@link EmfLogDiv}.
 * <p>
 * TODO Improve "table" view tests: We currently cannot assume a deterministic
 * row order for all transactions that happened during the same second. We also
 * should not add artificial delays during test execution. Those tests are
 * currently quite coarse, and should be improved.
 * <p>
 * TODO Improve "feed" view test: View is based upon a Grid-Layout. Due to how
 * CSS grid layout works, elements displayed in one "row" do not have a
 * designated, common parent element (unlike tables, which have "tr"). This
 * makes testing the contents of displayed rows inherently tricky. This test is
 * currently quite coarse, and should be improved.
 * <p>
 * TODO Improve "feed item popup" view test
 * <p>
 * TODO Improve "feed" view tests: Background colors are not asserted in any
 * way.
 *
 * @author Alexander Schmidt
 */
public class EmfLogDivTest extends AbstractEmfTest {

	private static final Day DEFAULT_DAY = Day.fromYMD(2019, 1, 1);
	private static final Day OTHER_DAY = Day.fromYMD(2019, 2, 1);
	private static final Integer DEFAULT_VALUE = 9999;
	private static final Integer DEFAULT_NOT_NULLABLE_VALUE = 5;
	private static final Integer OTHER_VALUE = 8888;
	private static final String DEFAULT_NAME = "someName";
	private static final String BADGE_ADD = "++";
	private static final String BADGE_REMOVE = "--";
	private static final String BADGE_MODIFY = "<>";
	private static final String LABEL_ACTIVE = "Active";
	private static final String LABEL_DAY = "Day";
	private static final String LABEL_NAME = "Name";
	private static final String LABEL_VALUE = "Value";

	private EmfTestObject object;
	private EmfTestSubObject subObject;

	public EmfLogDivTest() {

		this.object = null;
		this.subObject = null;
	}

	// ---------------- simple entity tests ---------------- //

	@Test
	public void testFeedWithSimpleEntity() {

		initializeWithDefaultSimpleEntity();
		updateDay(OTHER_DAY);
		updateDay(null);

		List<DomNodeTester> feedItems = findBody()//
			.findNode(EmfLogMarker.FEED_MAIN)
			.findNodes(EmfLogMarker.FEED_ITEM)
			.assertSize(3);
		feedItems//
			.get(0)
			.assertDoesNotContainText(BADGE_ADD)
			.assertDoesNotContainText(BADGE_MODIFY)
			.assertContainsText(BADGE_REMOVE)
			.assertContainsText(LABEL_DAY)
			.assertContainsText(OTHER_DAY.toString());
		feedItems//
			.get(1)
			.assertDoesNotContainText(BADGE_ADD)
			.assertContainsText(BADGE_MODIFY)
			.assertDoesNotContainText(BADGE_REMOVE)
			.assertContainsText(LABEL_DAY)
			.assertContainsText(OTHER_DAY.toString());
		feedItems//
			.get(2)
			.assertContainsText(BADGE_ADD)
			.assertDoesNotContainText(BADGE_MODIFY)
			.assertDoesNotContainText(BADGE_REMOVE)
			.assertContainsText(LABEL_DAY)
			.assertContainsText(DEFAULT_DAY.toString());
	}

	@Test
	public void testFeedItemPopupWithSimpleEntity() {

		initializeWithDefaultSimpleEntity();
		updateDay(OTHER_DAY);
		updateDay(null);

		List<DomNodeTester> popupButtons = findBody()//
			.findNodes(EmfLogMarker.FEED_ITEM_POPUP_BUTTON)
			.assertSize(3);

		popupButtons.get(0).click();
		findOnePopup()//
			.assertContainsText(LABEL_ACTIVE)
			.assertContainsText(LABEL_DAY)
			.assertContainsText(LABEL_NAME)
			.assertDoesNotContainText(DEFAULT_DAY.toString())
			.assertDoesNotContainText(OTHER_DAY.toString())
		//
		;
		clickCloseSinglePopup();

		popupButtons.get(1).click();
		findOnePopup();
		findOnePopup()//
			.assertContainsText(LABEL_ACTIVE)
			.assertContainsText(LABEL_DAY)
			.assertContainsText(LABEL_NAME)
			.assertDoesNotContainText(DEFAULT_DAY.toString())
			.assertContainsText(OTHER_DAY.toString())
		//
		;
		clickCloseSinglePopup();

		popupButtons.get(2).click();
		findOnePopup();
		findOnePopup()//
			.assertContainsText(LABEL_ACTIVE)
			.assertContainsText(LABEL_DAY)
			.assertContainsText(LABEL_NAME)
			.assertContainsText(DEFAULT_DAY.toString())
			.assertDoesNotContainText(OTHER_DAY.toString())
		//
		;
		clickCloseSinglePopup();
	}

	@Test
	public void testTableWithSimpleEntity() {

		initializeWithDefaultSimpleEntity();
		updateDay(OTHER_DAY);
		updateDay(null);

		clickTableTab();

		List<DomNodeTester> tableRows = assertDisplayedTableRows(3);
		assertAnyContainsAll(tableRows, DEFAULT_DAY);
		assertAnyContainsAll(tableRows, OTHER_DAY);
		assertAnyLacksEach(tableRows, DEFAULT_DAY, OTHER_DAY);
	}

	// ---------------- sub-entity tests ---------------- //

	@Test
	public void testFeedWithSubEntity() {

		initializeWithDefaultSubEntity();
		updateDayAndValue(OTHER_DAY, OTHER_VALUE);
		updateDayAndValue(null, null);

		List<DomNodeTester> feedItems = findBody()//
			.findNode(EmfLogMarker.FEED_MAIN)
			.findNodes(EmfLogMarker.FEED_ITEM)
			.assertSize(3);
		feedItems//
			.get(0)
			.assertDoesNotContainText(BADGE_ADD)
			.assertDoesNotContainText(BADGE_MODIFY)
			.assertContainsText(BADGE_REMOVE)
			.assertContainsText(LABEL_DAY)
			.assertContainsText(LABEL_VALUE)
			.assertContainsText(OTHER_DAY.toString())
			.assertContainsText(OTHER_VALUE.toString());
		feedItems//
			.get(1)
			.assertDoesNotContainText(BADGE_ADD)
			.assertContainsText(BADGE_MODIFY)
			.assertDoesNotContainText(BADGE_REMOVE)
			.assertContainsText(LABEL_DAY)
			.assertContainsText(LABEL_VALUE)
			.assertContainsText(OTHER_DAY.toString())
			.assertContainsText(OTHER_VALUE.toString());
		feedItems//
			.get(2)
			.assertContainsText(BADGE_ADD)
			.assertDoesNotContainText(BADGE_MODIFY)
			.assertDoesNotContainText(BADGE_REMOVE)
			.assertContainsText(LABEL_ACTIVE)
			.assertContainsText(LABEL_DAY)
			.assertContainsText(LABEL_NAME)
			.assertContainsText(LABEL_VALUE)
			.assertContainsText(DEFAULT_DAY.toString())
			.assertContainsText(DEFAULT_NAME)
			.assertContainsText(DEFAULT_VALUE.toString());
	}

	@Test
	public void testFeedItemPopupWithSubEntity() {

		initializeWithDefaultSubEntity();
		updateDayAndValue(OTHER_DAY, OTHER_VALUE);
		updateDayAndValue(null, null);

		List<DomNodeTester> popupButtons = findBody()//
			.findNodes(EmfLogMarker.FEED_ITEM_POPUP_BUTTON)
			.assertSize(3);

		popupButtons.get(0).click();
		findOnePopup()//
			.assertContainsText(LABEL_ACTIVE)
			.assertContainsText(LABEL_DAY)
			.assertContainsText(LABEL_NAME)
			.assertContainsText(LABEL_VALUE)
			.assertDoesNotContainText(DEFAULT_DAY.toString())
			.assertDoesNotContainText(DEFAULT_VALUE.toString())
			.assertDoesNotContainText(OTHER_DAY.toString())
			.assertDoesNotContainText(OTHER_VALUE.toString());
		clickCloseSinglePopup();

		popupButtons.get(1).click();
		findOnePopup();
		findOnePopup()//
			.assertContainsText(LABEL_ACTIVE)
			.assertContainsText(LABEL_DAY)
			.assertContainsText(LABEL_NAME)
			.assertContainsText(LABEL_VALUE)
			.assertDoesNotContainText(DEFAULT_DAY.toString())
			.assertDoesNotContainText(DEFAULT_VALUE.toString())
			.assertContainsText(OTHER_DAY.toString())
			.assertContainsText(OTHER_VALUE.toString());
		clickCloseSinglePopup();

		popupButtons.get(2).click();
		findOnePopup();
		findOnePopup()//
			.assertContainsText(LABEL_ACTIVE)
			.assertContainsText(LABEL_DAY)
			.assertContainsText(LABEL_NAME)
			.assertContainsText(LABEL_VALUE)
			.assertContainsText(DEFAULT_DAY.toString())
			.assertContainsText(DEFAULT_VALUE.toString())
			.assertDoesNotContainText(OTHER_DAY.toString())
			.assertDoesNotContainText(OTHER_VALUE.toString());
		clickCloseSinglePopup();
	}

	@Test
	public void testTableWithSubEntity() {

		initializeWithDefaultSubEntity();
		updateDayAndValue(OTHER_DAY, OTHER_VALUE);
		updateDayAndValue(null, null);

		clickTableTab();

		List<DomNodeTester> tableRows = assertDisplayedTableRows(3);
		assertAnyContainsAll(tableRows, DEFAULT_NAME, DEFAULT_DAY, DEFAULT_VALUE);
		assertAnyContainsAll(tableRows, DEFAULT_NAME, OTHER_DAY, OTHER_VALUE);
		assertAnyLacksEach(tableRows, DEFAULT_DAY, OTHER_DAY, DEFAULT_VALUE, OTHER_VALUE);
	}

	// ---------------- miscellaneous tests ---------------- //

	@Test
	public void testFeedTabIsDefault() {

		initializeWithDefaultSimpleEntityUnlogged();

		findBody().findNode(EmfLogMarker.FEED_MAIN);
	}

	@Test
	public void testFeedWithEmptyLog() {

		initializeWithDefaultSimpleEntityUnlogged();

		findBody()//
			.findNode(EmfLogMarker.FEED_MAIN)
			.assertContainsText(EmfDataTableI18n.NO_ENTRIES_FOUND);
	}

	@Test
	public void testTableWithEmptyLog() {

		initializeWithDefaultSimpleEntityUnlogged();

		clickTableTab();

		findBody()//
			.findNode(EmfLogMarker.TABLE_MAIN)
			.assertContainsText(EmfDataTableI18n.NO_ENTRIES_FOUND);
	}

	// ---------------- internal: initialization ---------------- //

	private void initializeWithDefaultSimpleEntity() {

		this.object = initialize(insertSimpleEntity(DEFAULT_DAY));
	}

	private void initializeWithDefaultSimpleEntityUnlogged() {

		this.object = initialize(insertSimpleEntityUnlogged());
	}

	private void initializeWithDefaultSubEntity() {

		this.object = createSimpleEntity(DEFAULT_DAY);
		this.subObject = initialize(insertSubEntity(object, DEFAULT_VALUE));
	}

	private <R extends IEmfTableRow<R, ?>> R initialize(R tableRow) {

		setNodeSupplier(() -> new EmfLogDiv<>(tableRow));
		return tableRow;
	}

	private EmfTestObject insertSimpleEntity(Day day) {

		return createSimpleEntity(day).save();
	}

	private EmfTestObject createSimpleEntity(Day day) {

		return new EmfTestObject()//
			.setActive(true)
			.setDay(day)
			.setName(DEFAULT_NAME);
	}

	private EmfTestSubObject insertSubEntity(EmfTestObject base, Integer value) {

		return new EmfTestSubObject()//
			.setSimpleEntity(base)
			.setName(DEFAULT_NAME)
			.setValue(value)
			.setNotNullableValue(DEFAULT_NOT_NULLABLE_VALUE)
			.save();
	}

	private EmfTestObject insertSimpleEntityUnlogged() {

		return new DbTestTableRowInserter<>(EmfTestObject.TABLE)//
			.set(EmfTestObject.ACTIVE, true)
			.set(EmfTestObject.DAY, DEFAULT_DAY)
			.set(EmfTestObject.NAME, DEFAULT_NAME)
			.insert();
	}

	private void updateDay(Day day) {

		object.setDay(day).save();
	}

	private void updateDayAndValue(Day day, Integer value) {

		object.setDay(day);
		subObject.setValue(value).save();
	}

	// ---------------- internal: execution ---------------- //

	private void clickTableTab() {

		findBody().clickNode(EmfLogMarker.TABLE_TAB);
	}

	private void clickCloseSinglePopup() {

		findBody().findNode(DomPopup.class).clickNode(DomI18n.CLOSE);
		findBody().findNodes(DomPopup.class).assertNone();
	}

	// ---------------- internal: assertions ---------------- //

	private DomNodeTester findOnePopup() {

		return findBody().findNodes(DomPopup.class).assertOne();
	}

	private List<DomNodeTester> assertDisplayedTableRows(int size) {

		return findBody()//
			.findNode(IEmfDataTable.class)
			.findNode(DomTBody.class)
			.findNodes(DomRow.class)
			.assertSize(size);
	}

	/**
	 * Asserts that the given collection contains at least one
	 * {@link DomNodeTester} that contains all of the textual representations of
	 * the given objects.
	 */
	private void assertAnyContainsAll(Collection<DomNodeTester> testers, Object...texts) {

		Collection<String> strings = toStrings(texts);
		if (anyContainsAllTexts(testers, strings)) {
			Assert
				.fail(
					String
						.format(//
							"Failed to find a node that contains all of: '%s'.",
							strings.stream().collect(Collectors.joining(", "))));
		}
	}

	/**
	 * Asserts that the given collection contains at least one
	 * {@link DomNodeTester} that lacks <b>each of the textual representations
	 * of the given objects.
	 */
	private void assertAnyLacksEach(Collection<DomNodeTester> testers, Object...texts) {

		Collection<String> strings = toStrings(texts);
		if (anyLacksAllTexts(testers, strings)) {
			Assert
				.fail(
					String
						.format(//
							"Failed to find a node that lacks all of: '%s'.",
							strings.stream().collect(Collectors.joining(", "))));
		}
	}

	private boolean anyContainsAllTexts(Collection<DomNodeTester> testers, Collection<String> texts) {

		return testers.stream().noneMatch(tester -> containsAllTexts(tester, texts));
	}

	private boolean containsAllTexts(DomNodeTester tester, Collection<String> texts) {

		for (String text: texts) {
			if (!tester.containsText(text)) {
				return false;
			}
		}
		return true;
	}

	private boolean anyLacksAllTexts(Collection<DomNodeTester> testers, Collection<String> texts) {

		return testers.stream().noneMatch(tester -> lacksAllTexts(tester, texts));
	}

	private boolean lacksAllTexts(DomNodeTester tester, Collection<String> texts) {

		for (String text: texts) {
			if (tester.containsText(text)) {
				return false;
			}
		}
		return true;
	}

	private Collection<String> toStrings(Object...texts) {

		return Arrays//
			.asList(texts)
			.stream()
			.map(Object::toString)
			.collect(Collectors.toList());
	}
}
