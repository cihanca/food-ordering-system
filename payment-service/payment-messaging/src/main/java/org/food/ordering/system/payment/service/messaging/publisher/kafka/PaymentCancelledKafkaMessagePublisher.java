package org.food.ordering.system.payment.service.messaging.publisher.kafka;

import com.food.ordering.system.kafka.order.avro.model.PaymentResponseAvroModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.food.ordering.system.kafka.producer.KafkaMessageHelper;
import org.food.ordering.system.kafka.producer.service.KafkaProducer;
import org.food.ordering.system.payment.service.domain.config.PaymentServiceConfigData;
import org.food.ordering.system.payment.service.domain.event.PaymentCancelledEvent;
import org.food.ordering.system.payment.service.domain.ports.output.message.publisher.PaymentCancelledMessagePublisher;
import org.food.ordering.system.payment.service.messaging.mapper.PaymentMessagingDataMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Component
public class PaymentCancelledKafkaMessagePublisher implements PaymentCancelledMessagePublisher {

    private final PaymentMessagingDataMapper paymentMessagingDataMapper;
    private final KafkaProducer<String, PaymentResponseAvroModel> kafkaProducer;
    private final PaymentServiceConfigData paymentServiceConfigData;
    private final KafkaMessageHelper orderKafkaMessageHelper;

    @Override
    public void publish(PaymentCancelledEvent domainEvent) {
        String orderId = domainEvent.getPayment().getOrderId().getValue().toString();
        log.info("Received PaymentCancelledEvent for order id: {}", orderId);

        try {
            PaymentResponseAvroModel paymentResponseAvroModel = paymentMessagingDataMapper
                    .paymentCancelledEventToPaymentResponseAvroModel(domainEvent);

            kafkaProducer.send(
                    paymentServiceConfigData.getPaymentResponseTopicName(),
                    orderId,
                    paymentResponseAvroModel,
                    orderKafkaMessageHelper.getKafkaCallback(
                            paymentServiceConfigData.getPaymentResponseTopicName(),
                            paymentResponseAvroModel,
                            orderId,
                            "PaymentResponseAvroModel"
                    ));

            log.info("PaymentResponseAvroModel sent to kafka for order id: {}", orderId);
        } catch (Exception e) {
            log.error("Error while sending PaymentResponseAvroModel message to kafka with order id: {}, error: {}", orderId, e.getMessage());
        }
    }

}
