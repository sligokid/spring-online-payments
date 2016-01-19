package ie.magoo.payments.controller;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.realexpayments.hpp.sdk.RealexHpp;
import com.realexpayments.hpp.sdk.domain.HppRequest;

/**
 * The Class StripeController.
 * 
 * Render the stripe page and process any submitted payment.
 */
@RestController
public class RealexController {
	private static Logger LOG = Logger.getLogger(RealexController.class);

	@RequestMapping("/realex")
	public String stripe(@RequestParam Map<String, String> request, Model model) {

		HppRequest hppRequest = new HppRequest();
		hppRequest.addAmount(100);
		hppRequest.addCurrency("GBP");
		hppRequest.addMerchantId("realexsandbox");

		RealexHpp realexHpp = new RealexHpp("secret");
		String requestJson = realexHpp.requestToJson(hppRequest);

		LOG.info(requestJson);
		return requestJson;
	}

}
