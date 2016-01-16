package ie.magoo.payments.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StripeController {

	@RequestMapping("/stripe")
	public String stripe() {
		return "stripe";
	}

}
