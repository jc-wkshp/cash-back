package com.innov.demo;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
class PaymentController {

    private static final Logger logger = Logger.getLogger(PaymentController.class.getName());

    @Autowired
    PaymentService paymentService;
	
    @RequestMapping(value="/")
	String showStatus(){
		return "Cash Back Demo Application - running for FSI.";
    }
    
    @GetMapping(value="/payments")
	List<Payment> getPayments(){
        logger.info("From GetPayments");
        return paymentService.getPayments();
    }

    @PostMapping("/payment")
	String postPayment(@RequestBody Payment payment){
        logger.info("Posting Payments to Service");
        paymentService.savePayment(payment);
		return "Payment -["+payment+"] Saved !";
    }

    @GetMapping(value="/generr")
	String doSomeProcess(){
        logger.info("Calling service for doing Some Process");
        return paymentService.doSomeProcessWithError();
    }
    
}