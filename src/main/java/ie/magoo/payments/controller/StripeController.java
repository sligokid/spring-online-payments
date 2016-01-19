package ie.magoo.payments.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.stripe.Stripe;
import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.model.Charge;

/**
 * The Class StripeController.
 * 
 * Render the stripe page and process any submitted payment.
 */
@Controller
public class StripeController {
	private static Logger LOG = Logger.getLogger(StripeController.class);

	private static final String STRIPE_API_KEY = "sk_test_BQokikJOvBiI2HlWgH4olfQ2";

	@RequestMapping("/stripe")
	public String stripe(@RequestParam Map<String, String> request, Model model) {

		// Get the credit card details submitted by the form
		String token = request.get("stripeToken");

		if (token != null) {
			processPayment(request, token);
		}

		model.addAttribute("cur", "eur");
		model.addAttribute("amt", "100000");
		return "index";
	}

	private void processPayment(Map<String, String> request, String token) {
		// Set your secret key: remember to change this to your live secret key
		// in production
		// See your keys here https://dashboard.stripe.com/account/apikeys
		Stripe.apiKey = STRIPE_API_KEY;

		// Create the charge on Stripe's servers - this will charge the user's
		// card
		try {
			Map<String, Object> chargeParams = new HashMap<String, Object>();
			chargeParams.put("amount", 1000); // amount in cents, again
			chargeParams.put("currency", "eur");
			chargeParams.put("source", token);
			chargeParams.put("description", "Example charge");

			Charge charge = Charge.create(chargeParams);
			LOG.info("Payment charged to the following account: " + request);
			LOG.debug("Charge: " + charge);
		} catch (CardException | AuthenticationException | InvalidRequestException | APIConnectionException
				| APIException e) {
			// The card has been declined
			LOG.error("Payment declined for account: " + request);
		}
	}

}
