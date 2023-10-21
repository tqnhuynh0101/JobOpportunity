package com.jot.JobOpportunity.paypal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
public class PaypalController {

	@Autowired
	PaypalService service;

	public static final String SUCCESS_URL = "jot/management/checkout";
	public static final String CANCEL_URL = "pay/cancel";

	@GetMapping("/home")
	public String home() {
		return "home";
	}

	//localhost:9090/pay
	@PostMapping("/pay")
	public ResponseEntity<String> payment(@RequestBody Order order) {
		try {
			Payment payment = service.createPayment(order.getPrice(), order.getCurrency(), order.getMethod(),
					order.getIntent(), order.getDescription(), "http://localhost:9080/" + CANCEL_URL,
					"http://localhost:81/" + SUCCESS_URL);

			for(Links link:payment.getLinks()) {
				if(link.getRel().equals("approval_url")) {
					System.out.println(payment.getPayer());
					return ResponseEntity.status(HttpStatus.OK).body(link.getHref());
				}
			}

		} catch (PayPalRESTException e) {

			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Payment creation failed.");
	}
	
	 @GetMapping(value = CANCEL_URL)
	    public String cancelPay() {
	        return "cancel";
	    }

	@GetMapping("pay/success")
	public ResponseEntity<String> successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
		try {
			Payment payment = service.executePayment(paymentId, payerId);
			if (payment.getState().equals("approved")) {
				System.out.println("OrderId "+ payment.getTransactions().get(0).getDescription());
				return ResponseEntity.ok(payment.getTransactions().get(0).getDescription());
			}
		} catch (PayPalRESTException e) {
			System.out.println(e.getMessage());
		}
		return ResponseEntity.badRequest().body("failed");
	}
}
//@Controller
//@RequestMapping(value = "pay/success")
//class ViewPaypalController{
//	@Autowired
//	PaypalService service;
//	@GetMapping
//	public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId, HttpSession session) {
//		try {
//			Payment payment = service.executePayment(paymentId, payerId);
//			if (payment.getState().equals("approved")) {
//				return "success";
//			}
//		} catch (PayPalRESTException e) {
//			System.out.println(e.getMessage());
//		}
//		return "redirect:/";
//	}
//	private void sendPaymentSuccessToGolang(String jwtToken, String orderId) {
//		System.out.println("jwtToken"+ jwtToken);
//		System.out.println("orderId"+ orderId);
//		RestTemplate restTemplate =  new RestTemplate();
//		HttpHeaders headers = new HttpHeaders();
//		headers.set("token", jwtToken);
//		headers.setContentType(MediaType.APPLICATION_JSON);
//		Map<String, String> requestBody = new HashMap<>();
//		requestBody.put("order_id", orderId);
//
//		HttpEntity<Map<String, String>> httpEntity = new HttpEntity<>(requestBody, headers);
//
//		// Make the HTTP POST request to the Fiber Golang API
//		ResponseEntity<String> response = restTemplate.exchange(
//				"http://localhost:8080/client/payment/success",
//				HttpMethod.POST,
//				httpEntity,
//				String.class
//		);
//		String responseBody = response.getBody();
//		System.out.println("Response from Fiber API: " + responseBody);
//	}
//}
