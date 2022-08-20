package org.food.ordering.system.order.service.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.food.ordering.system.order.service.domain.dto.track.TrackOrderQuery;
import org.food.ordering.system.order.service.domain.dto.track.TrackOrderResponse;
import org.food.ordering.system.order.service.domain.entity.Order;
import org.food.ordering.system.order.service.domain.exception.OrderNotFoundException;
import org.food.ordering.system.order.service.domain.mapper.OrderDataMapper;
import org.food.ordering.system.order.service.domain.ports.output.repository.OrderRepository;
import org.food.ordering.system.order.service.domain.valueobject.TrackingId;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Component
public class OrderTrackCommandHandler {

    private final OrderDataMapper orderDataMapper;

    private final OrderRepository orderRepository;

    @Transactional(readOnly = true)
    TrackOrderResponse trackOrder(TrackOrderQuery trackOrderQuery) {
        Optional<Order> order = orderRepository.findByTrackingId(new TrackingId(trackOrderQuery.getOrderTrackingId()));
        if (order.isEmpty()) {
            log.warn("Could not find order with tracking id: {}", trackOrderQuery.getOrderTrackingId());
            throw new OrderNotFoundException("Could not find order with tracking id: " + trackOrderQuery.getOrderTrackingId());
        }
        return orderDataMapper.orderToTrackOrderResponse(order.get());
    }

}
