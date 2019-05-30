package com.innov.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

@Service
class PaymentService {
    
    private static final Logger logger = Logger.getLogger(PaymentService.class.getName());

    List<Payment> payments = new ArrayList<>();
    
	List<Payment> getPayments(){
        logger.info("From Service -> Retrieving Payments");
        return payments;
    }

	Payment savePayment(Payment payment){
        logger.info("From Service -> Saving Payments");
        payment.setId(payments.size()+1);
        payments.add(payment);
        logger.info("From Service -> Payments Saved");
		return payment;
    }

    String doSomeProcessWithError() {
        logger.info("From Service -> Begin Process for Error");
        Payment payment = payments.get(payments.size()+1);
        logger.info("payment processed . . ." + payment.getId());
        return "Done";
    }
}