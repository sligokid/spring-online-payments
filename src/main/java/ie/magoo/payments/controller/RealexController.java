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
	public String realex(@RequestParam Map<String, String> request, Model model) {
		HppRequest hppRequest = new HppRequest();

		hppRequest.setMerchantId("kieranmcgowan");
		hppRequest.setAccount("internet");
		hppRequest.setOrderId("1231231231");
		hppRequest.setAmount("20000");
		hppRequest.setCurrency("GBP");

		hppRequest.setCommentOne("");
		hppRequest.setCommentTwo("");
		hppRequest.setPayerReference("");
		hppRequest.setProductId("123123");
		hppRequest.setCustomerNumber("234242342");
		hppRequest.setBillingCode("asdfsdf");
		hppRequest.setReturnTss("");
		hppRequest.setDccEnable("");
		hppRequest.setVariableReference("");
		hppRequest.setOfferSaveCard("");
		hppRequest.setAutoSettleFlag("");
		hppRequest.setBillingCode("");
		hppRequest.setCardPaymentButtonText("Pay");
		hppRequest.setValidateCardOnly("");
		hppRequest.setPayerExists("");
		hppRequest.setLanguage("");
		hppRequest.setShippingCode("");
		hppRequest.setShippingCountry("");
		hppRequest.setBillingCountry("");
		hppRequest.setCardStorageEnable("");
		hppRequest.setPayerReference("");
		hppRequest.setPaymentReference("");

		RealexHpp realexHpp = new RealexHpp("secret");
		String requestJson = realexHpp.requestToJson(hppRequest);
		LOG.info(requestJson);
		return requestJson;
	}

	@RequestMapping("/realex-api-response")
	public String realexApiResponse(@RequestParam Map<String, String> request, Model model) {
		return "index";
	}
}
