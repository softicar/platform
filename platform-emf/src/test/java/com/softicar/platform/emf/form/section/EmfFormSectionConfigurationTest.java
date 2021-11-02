package com.softicar.platform.emf.form.section;

import com.softicar.platform.emf.AbstractEmfTest;
import com.softicar.platform.emf.form.IEmfFormBody;
import com.softicar.platform.emf.form.section.header.EmfFormSectionHeader;
import com.softicar.platform.emf.predicate.EmfPredicates;
import com.softicar.platform.emf.test.simple.EmfTestObject;
import java.util.Collection;
import org.junit.Test;
import org.mockito.Mockito;

public class EmfFormSectionConfigurationTest extends AbstractEmfTest {

	private final EmfTestObject object;

	private final IEmfFormBody<EmfTestObject> formBody;

	private final EmfFormSectionConfiguration<EmfTestObject> sectionConfiguration;

	public EmfFormSectionConfigurationTest() {

		this.object = new EmfTestObject().save();
		this.formBody = Mockito.mock(IEmfFormBody.class);
		this.sectionConfiguration = new EmfFormSectionConfiguration<>();
		Mockito.when(formBody.getTableRow()).thenReturn(object);
	}

	@Test
	public void testWithSingleSection() {

		sectionConfiguration.addSection(body -> new TestSection());

		Collection<EmfFormSectionDiv<EmfTestObject>> sections = sectionConfiguration.createAvailableSections(formBody);
		assertOne(sections);
		assertAll(sections, section -> section instanceof TestSection);
	}

	@Test
	public void testWithMultipleSections() {

		sectionConfiguration.addSection(body -> new TestSection());
		sectionConfiguration.addSection(body -> new TestSection());
		sectionConfiguration.addSection(body -> new TestSection());

		Collection<EmfFormSectionDiv<EmfTestObject>> sections = sectionConfiguration.createAvailableSections(formBody);

		assertCount(3, sections);
		assertAll(sections, section -> section instanceof TestSection);
	}

	@Test
	public void testWithNonAvailableSection() {

		sectionConfiguration.addSection(body -> new TestSection(), EmfPredicates.never());

		Collection<EmfFormSectionDiv<EmfTestObject>> sections = sectionConfiguration.createAvailableSections(formBody);

		assertNone(sections);
	}

	private class TestSection extends EmfFormSectionDiv<EmfTestObject> {

		public TestSection() {

			super(formBody, new EmfFormSectionHeader().setIcon(object.table().getIcon()));
		}
	}
}
