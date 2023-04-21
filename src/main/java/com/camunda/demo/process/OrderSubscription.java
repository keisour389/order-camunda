package com.camunda.demo.process;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.response.ProcessInstanceEvent;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class OrderSubscription {

    private final ZeebeClient zeebeClient;

    private final String URL = "http://localhost:3001/api/v1";

    private static Logger logger = LoggerFactory.getLogger(OrderSubscription.class);

    public OrderSubscription(ZeebeClient zeebeClient) {
        this.zeebeClient = zeebeClient;
    }

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

    @JobWorker(type="request_payment_gateway", autoComplete = true)
    public Map<String, String> handlePaymentGatewayRequest(final ActivatedJob activatedJob) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(URL + "/request-payment-gateway", String.class, String.class);
        logger.info(String.valueOf(response.getBody()));

        logger.info("Handle payment gateway successfully");
        zeebeClient.newSetVariablesCommand(activatedJob.getProcessInstanceKey())
                .variables(Map.of("paymentGateway", String.valueOf(response.getBody())))
                .send().join();
        return Map.of("paymentType", String.valueOf(response.getBody()));
    }

    @JobWorker(type="inform_store")
    public void informStore() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(URL + "/inform-store", String.class, String.class);
        logger.info(String.valueOf(response.getBody()));
    }

    @JobWorker(type="request_logistic_vendor")
    public void requestLogisticVendor() {
        logger.info("Requested logistic vendor successfully");
    }

    @JobWorker(type="order_registration")
    public void sendOrderRegistration() {
        logger.info("Sent order registration successfully");
    }

    @JobWorker(type="order_cancellation")
    public void sendOrderCancellation() {
        logger.info("Order has been cancelled successfully");
    }

    @JobWorker(type="response_from_delivery")
    public void respondFromDelivery() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(URL + "/response-from-delivery",
                String.class, String.class);
        logger.info(String.valueOf(response.getBody()));
    }

    @JobWorker(type="inform_to_customer")
    public void informToCustomer() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(URL + "/inform-customer",
                String.class, String.class);
        logger.info(String.valueOf(response.getBody()));
    }

}
