package org.food.ordering.system.payment.service.domain.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.food.ordering.system.domain.event.DomainEvent;
import org.food.ordering.system.payment.service.domain.entity.Payment;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
@RequiredArgsConstructor
public abstract class PaymentEvent implements DomainEvent<Payment> {

    private final Payment payment;
    private final ZonedDateTime createdAt;
    private final List<String> failureMessages;

}
