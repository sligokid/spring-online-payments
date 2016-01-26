package ie.magoo.payments.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.lookfirst.wepay.WePayApi;
import com.lookfirst.wepay.WePayException;
import com.lookfirst.wepay.WePayKey;
import com.lookfirst.wepay.api.AccountUri;
import com.lookfirst.wepay.api.CheckoutUri;
import com.lookfirst.wepay.api.Constants.PaymentType;
import com.lookfirst.wepay.api.Token;
import com.lookfirst.wepay.api.req.AccountCreateRequest;
import com.lookfirst.wepay.api.req.CheckoutCreateRequest;

/**
 * The Class IndexController.
 * 
 * Render the wepay hosted page and process any submitted payment.
 */
@Controller
public class WepayController {

	private static Logger LOG = Logger.getLogger(WepayController.class);

	private boolean production = false;

	private Long clientId = (long) 173169;

	private String clientSecret = "f82df2fb75";
	private String localRedirectUrl = "http://ec2-52-16-13-114.eu-west-1.compute.amazonaws.com:8080/spring-online-payments/wepay-api-response";
	// private String localRedirectUrl =
	// "http://localhost:8080/spring-online-payments/wepay-api-response";
	private String authRedirectUrl = "https://stage.wepay.com/v2/oauth2/authorize?client_id=173169&redirect_uri="
			+ localRedirectUrl
			+ "&scope=manage_accounts,collect_payments,view_user,send_money,preapprove_payments,manage_subscriptions";

	@RequestMapping("/wepay")
	public ModelAndView wepay(@RequestParam Map<String, String> request, Model model)
			throws WePayException, IOException {

		return new ModelAndView("redirect:" + authRedirectUrl);
	}

	@RequestMapping("/wepay-api-response")
	public ModelAndView wepayApiResponse(@RequestParam Map<String, String> request, Model model)
			throws WePayException, IOException {

		// code retrieved from the gateway redirect on the qs
		String oAuthCode = request.get("code");
		WePayApi api = getWepayApi();

		Token oAthToken = api.getToken(oAuthCode, localRedirectUrl);

		// create test account
		AccountUri accountUri = getAccountUri(api, oAthToken);

		CheckoutCreateRequest checkoutRequest = getCheckoutRequest(accountUri);

		LOG.debug("Order before execution:" + checkoutRequest);

		// API throws com.lookfirst.wepay.WePayException: invalid_request:
		// Invalid parameter 'type' expected one of: 'goods', 'service',
		// 'donation', 'event', 'personal'

		CheckoutUri uri = api.execute(oAthToken.getAccessToken(), checkoutRequest);

		return new ModelAndView("redirect:" + uri);
	}

	private WePayApi getWepayApi() {
		WePayKey key = new WePayKey(production, clientId, clientSecret);
		WePayApi api = new WePayApi(key);
		return api;
	}

	private CheckoutCreateRequest getCheckoutRequest(AccountUri accountUri) {
		CheckoutCreateRequest checkoutRequest = new CheckoutCreateRequest();
		checkoutRequest.setAmount(new BigDecimal("200"));
		checkoutRequest.setAppFee(new BigDecimal("0.50"));
		checkoutRequest.setCurrency("USD");
		checkoutRequest.setAccountId(accountUri.getAccountId());
		checkoutRequest.setShortDescription("blah");
		checkoutRequest.setType(PaymentType.SERVICE);
		return checkoutRequest;
	}

	private AccountUri getAccountUri(WePayApi api, Token token) throws IOException {
		AccountCreateRequest account = new AccountCreateRequest();
		account.setName("Joe Blogs");
		account.setDescription("TEST");
		AccountUri accountUri = api.execute(token.getAccessToken(), account);
		return accountUri;
	}

}
