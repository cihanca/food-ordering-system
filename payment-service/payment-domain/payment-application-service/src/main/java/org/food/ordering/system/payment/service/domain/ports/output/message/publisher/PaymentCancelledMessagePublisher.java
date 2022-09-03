package org.food.ordering.system.payment.service.domain.ports.output.message.publisher;

import org.food.ordering.system.domain.event.publisher.DomainEventPublisher;
import org.food.ordering.system.payment.service.domain.event.PaymentCancelledEvent;
import org.food.ordering.system.payment.service.domain.event.PaymentCompletedEvent;

public interface PaymentCancelledMessagePublisher extends DomainEventPublisher<PaymentCancelledEvent> {
}
