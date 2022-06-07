package com.softicar.platform.emf.predicate;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.emf.mapper.IEmfTableRowMapper;
import java.util.Optional;
import org.junit.Test;

public class EmfMappedTableRowPredicateTest extends AbstractTest {

	private final Delivery delivery;
	private final DeliveryItem deliveryItem;
	private final IEmfPredicate<DeliveryItem> isDonePredicate;

	public EmfMappedTableRowPredicateTest() {

		this.delivery = new Delivery();
		this.deliveryItem = new DeliveryItem(delivery);
		this.isDonePredicate = new IsDonePredicate().of(new DeliveryItemToDeliveryMapper());
	}

	@Test
	public void testGetTitle() {

		assertEquals("is done of delivery of item", isDonePredicate.getTitle().toString());
	}

	@Test
	public void testTestWithFalse() {

		delivery.setDone(false);
		assertFalse(isDonePredicate.test(deliveryItem));
	}

	@Test
	public void testTestWithTrue() {

		delivery.setDone(true);
		assertTrue(isDonePredicate.test(deliveryItem));
	}

	// ------------------------------ private ------------------------------ //

	private static class Delivery {

		private boolean done;

		public void setDone(boolean done) {

			this.done = done;
		}

		public boolean isDone() {

			return done;
		}
	}

	private static class DeliveryItem {

		private final Delivery delivery;

		public DeliveryItem(Delivery delivery) {

			this.delivery = delivery;
		}

		public Delivery getDelivery() {

			return delivery;
		}
	}

	private static class IsDonePredicate implements IEmfPredicate<Delivery> {

		@Override
		public IDisplayString getTitle() {

			return IDisplayString.create("is done");
		}

		@Override
		public boolean test(Delivery delivery) {

			return delivery.isDone();
		}
	}

	private static class DeliveryItemToDeliveryMapper implements IEmfTableRowMapper<DeliveryItem, Delivery> {

		@Override
		public IDisplayString getTitle() {

			return IDisplayString.create("delivery of item");
		}

		@Override
		public Optional<Delivery> apply(DeliveryItem item) {

			return Optional.ofNullable(item).map(DeliveryItem::getDelivery);
		}
	}
}
