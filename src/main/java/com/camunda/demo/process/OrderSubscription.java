package com.camunda.demo.process;

import io.camunda.zeebe.spring.client.annotation.JobWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class OrderSubscription {

    private static Logger logger = LoggerFactory.getLogger(OrderSubscription.class);

    @JobWorker(type="order_subscription")
    public void sendOrderSubscription() {
        logger.info("Sent order subscription successfully");
    }

    @JobWorker(type="payment_method")
    public void sendPaymentMethod() {
        logger.info("Sent payment method successfully");
    }

    @JobWorker(type="process_order")
    public void processOrder() {
        logger.info("Processed order successfully");
    }

    @JobWorker(type="request_payment_gateway")
    public void handlePaymentGatewayRequest() {
        logger.info("Handle payment gateway successfully");
    }

    @JobWorker(type="inform_store")
    public void informStore() {
        logger.info("inform store successfully");
    }

    @JobWorker(type="request_logistic_vendor")
    public void requestLogisticVendor() {
        logger.info("Requested logistic vendor successfully");
    }


    @JobWorker(type="order_registration")
    public void sendOrderRegistration() {
        logger.info("Sent order registration successfully");
    }
}
